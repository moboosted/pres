/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.action;

import com.silvermoongroup.base.exception.ApplicationRuntimeException;
import com.silvermoongroup.businessservice.customerrelationshipmanagement.service.intf.ICustomerRelationshipService;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.datatype.Period;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.locale.ITypeParser;
import com.silvermoongroup.fsa.web.party.form.PartyForm;
import com.silvermoongroup.party.domain.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ContactActionHelper {

    ICustomerRelationshipService customerRelationshipService;
    IProductDevelopmentService productDevelopmentService;
    ITypeParser typeParser;
    
    public ContactActionHelper(ICustomerRelationshipService customerRelationshipService,
            IProductDevelopmentService productDevelopmentService, ITypeParser typeParser) {
        this.customerRelationshipService = customerRelationshipService;
        this.productDevelopmentService = productDevelopmentService;
        this.typeParser = typeParser;
    }

    public RolePlayer addPrefToRolePlayer(ApplicationContext applicationContext, PartyForm partyForm,
            RolePlayer rolePlayer) throws Exception {
        ContactPreference newPreference = new ContactPreference();
        Date effectiveFrom;
        Date effectiveTo;
        if (partyForm.getTxtStartDateContactPref().equals("")) {
            effectiveFrom = Date.today();
        } else {
            effectiveFrom = typeParser.parseDate(partyForm.getTxtStartDateContactPref());
        }
        if (!partyForm.getTxtEndDateContactPref().equals("")) {
            effectiveTo = typeParser.parseDate(partyForm.getTxtEndDateContactPref());
        } else {
            effectiveTo = Date.FUTURE;
        }
        newPreference.setEffectivePeriod(new DatePeriod(effectiveFrom, effectiveTo));
        EnumerationReference language = EnumerationReference.convertFromString(partyForm.getSelPreferredLanguage());
        newPreference.setPreferredLanguage(language);

        newPreference.setTypeId(partyForm.getSelContactPreferenceTypeId());

        customerRelationshipService.establishContactPreference(applicationContext, rolePlayer.getObjectReference(), newPreference,
                partyForm.isDefContPref());
        return rolePlayer;
    }

    public void editPrefOfRolePlayer(ApplicationContext applicationContext, PartyForm partyForm, RolePlayer rolePlayer)
            throws Exception {

        Set<ContactPreference> preferences = null;
        if (rolePlayer instanceof Party) {
            preferences = partyForm.getContactPrefsForParty();
        } else {
            if (rolePlayer instanceof PartyRole) {
                preferences = (Set<ContactPreference>) customerRelationshipService
                        .findContactPreferencesAndPoints(applicationContext, rolePlayer.getObjectReference());            }
        }

        Long preferenceId = partyForm.getPreferenceSelected();
        if (preferenceId == null) {
            return;
        }

        ContactPreference preference = null;
        for (ContactPreference candidate : preferences) {
            if (partyForm.getPreferenceSelected().equals(candidate.getId())) {
                preference = candidate;
                break;
            }
        }

        if (preference == null) {
            throw new IllegalStateException("Unable to find ContactPreference with id: "
                    + partyForm.getPreferenceSelected());
        }

        Date dateFrom = typeParser.parseDate(partyForm.getTxtStartDateContactPref());
        Date dateTo = typeParser.parseDate(partyForm.getTxtEndDateContactPref());
        if(dateFrom == null) {
            dateFrom = Date.today();
        }
        if(dateTo == null) {
            dateTo = Date.FUTURE;
        }

        preference.setEffectivePeriod(new DatePeriod(dateFrom, dateTo));

        EnumerationReference language = EnumerationReference.convertFromString(partyForm.getSelPreferredLanguage());
        preference.setPreferredLanguage(language);

        customerRelationshipService.modifyContactPreference(applicationContext, preference, partyForm.isDefContPref());
    }

    public RolePlayer addContactToContactPref(ApplicationContext applicationContext, PartyForm partyForm,
                                              RolePlayer rolePlayer)
            throws Exception {
        Set<ContactPreference> preferences = null;
        if (rolePlayer instanceof Party) {
            preferences = partyForm.getContactPrefsForParty();
        } else {
            if (rolePlayer instanceof PartyRole) {
                preferences = (Set<ContactPreference>) customerRelationshipService
                        .findContactPreferencesAndPoints(applicationContext, rolePlayer.getObjectReference());
            }
        }

        ContactPreference preference = null;
        for (ContactPreference cp : preferences) {
            if (partyForm.getPreferenceSelected().equals(cp.getId())) {
                preference = cp;
                break;
            }
        }

        if (preference == null) {
            throw new IllegalStateException(
                    "Did not find the contact preference with the identifier: " + partyForm.getPreferenceSelected() +
                            " to add the contact point to."
            );
        }

        Long contactPointTypeId = partyForm.getSelContactPointTypeId();
        ContactPoint newPoint = createContactPointForType(applicationContext, contactPointTypeId);

        Date effectiveFrom;
        Date effectiveTo;
        if (newPoint instanceof TelephoneNumber) {
            TelephoneNumber number = (TelephoneNumber) newPoint;
            effectiveFrom = typeParser.parseDate(partyForm.getTxtStartDateNumber());
            effectiveTo = typeParser.parseDate(partyForm.getTxtEndDateNumber());
            populateTelephoneNumberFromForm(applicationContext, number, partyForm);

        } else if (newPoint instanceof ElectronicAddress) {
            ElectronicAddress email = (ElectronicAddress) newPoint;

            effectiveFrom = typeParser.parseDate(partyForm.getTxtStartDateEmail());
            effectiveTo = typeParser.parseDate(partyForm.getTxtEndDateEmail());
            populateElectronicAddressFromForm(email, partyForm);

        } else if (newPoint instanceof PhysicalAddress) {
            PhysicalAddress physical = (PhysicalAddress) newPoint;

            effectiveFrom = typeParser.parseDate(partyForm.getTxtStartDatePhysicalAddress());
            effectiveTo = typeParser.parseDate(partyForm.getTxtEndDatePhysicalAddress());
            populatePhysicalAddressFromForm(physical, partyForm);

        } else if (contactPointTypeId.equals(CoreTypeReference.POSTALADDRESS.getValue())) {
            PostalAddress postal = (PostalAddress) newPoint;

            effectiveFrom = typeParser.parseDate(partyForm.getTxtStartDatePostalAddress());
            effectiveTo = typeParser.parseDate(partyForm.getTxtEndDatePostalAddress());
            populatePostalAddressFromForm(postal, partyForm);

        } else if (contactPointTypeId.equals(CoreTypeReference.UNSTRUCTUREDADDRESS.getValue())) {
            UnstructuredAddress unstructured = (UnstructuredAddress) newPoint;

            effectiveFrom = typeParser.parseDate(partyForm.getTxtStartDatePostalAddress());
            effectiveTo = typeParser.parseDate(partyForm.getTxtEndDatePostalAddress());
            populateUnstructuredAddressFromForm(unstructured, partyForm);

        }else {
            throw new UnsupportedOperationException("Unknown contact point type: " + contactPointTypeId);
        }

        if(effectiveFrom == null) {
            effectiveFrom = Date.today();
        }
        newPoint.setEffectivePeriod(new DatePeriod(effectiveFrom, effectiveTo));
        newPoint.setTypeId(contactPointTypeId);

        partyForm.resetContactPointFields();

        // creates and saves the new contactPoint
        customerRelationshipService.attachContactPoint(
                applicationContext, rolePlayer.getObjectReference(), preference,
                newPoint
        );
        if (partyForm.isDefContPoint()) {
            customerRelationshipService.setDefaultContactPoint(applicationContext, rolePlayer.getObjectReference(),
                    preference, newPoint);
        }
        return rolePlayer;
    }

    private void populateElectronicAddressFromForm(ElectronicAddress electronicAddress, PartyForm partyForm) {
        electronicAddress.setAddress(StringUtils.trimToNull(partyForm.getTxtEmail()));
    }

    private void populatePhysicalAddressFromForm(PhysicalAddress physicalAddress, PartyForm partyForm) {
        physicalAddress.setUnitNumber(StringUtils.trimToNull(partyForm.getTxtUnitNumber()));
        physicalAddress.setFloorNumber(StringUtils.trimToNull(partyForm.getTxtFloorNumber()));
        physicalAddress.setBuildingName(StringUtils.trimToNull(partyForm.getTxtBuildingName()));
        physicalAddress.setHouseNumber(StringUtils.trimToNull(partyForm.getTxtHouseNumber()));
        physicalAddress.setStreet(StringUtils.trimToNull(partyForm.getTxtStreet()));
        physicalAddress.setCity(StringUtils.trimToNull(partyForm.getTxtPhysicalAddressCity()));
        physicalAddress.setPostalCode(StringUtils.trimToNull(partyForm.getTxtPhysicalAddressPostalCode()));
        physicalAddress.setSubregion(StringUtils.trimToNull(partyForm.getTxtPhysicalAddressSubRegion()));
        physicalAddress.setRegion(StringUtils.trimToNull(partyForm.getTxtPhysicalAddressRegion()));
        physicalAddress.setCountry(EnumerationReference.convertFromString(partyForm.getSelPhysicalAddressCountryName()));
    }

    private void populateTelephoneNumberFromForm(ApplicationContext applicationContext, TelephoneNumber telephoneNumber, PartyForm partyForm) {
        telephoneNumber.setAreaCode(StringUtils.trimToNull(partyForm.getTxtLandLineAreaCode()));
        telephoneNumber.setCountryPhoneCode(StringUtils.trimToNull(partyForm.getTxtLandLineCountryCode()));
        telephoneNumber.setLocalNumber(StringUtils.trimToNull(partyForm.getTxtLandLineLocalNumber()));
        telephoneNumber.setExtension(StringUtils.trimToNull(partyForm.getTxtLandLineExt()));
        
        EnumerationReference electronicType = EnumerationReference.convertFromString(partyForm.getTxtTelephoneElectronicType());
        telephoneNumber.setElectronicType(electronicType);
    }

    private void populatePostalAddressFromForm(PostalAddress postalAddress, PartyForm partyForm) {
        postalAddress.setPostnetSuite(StringUtils.trimToNull(partyForm.getTxtPostnetSuite()));
        postalAddress.setBoxNumber(StringUtils.trimToNull(partyForm.getTxtBoxNumber()));
        postalAddress.setSubregion(StringUtils.trimToNull(partyForm.getTxtPostalAddressSubRegion()));
        postalAddress.setCity(StringUtils.trimToNull(partyForm.getTxtPostalAddressCity()));
        postalAddress.setPostalCode(StringUtils.trimToNull(partyForm.getTxtPostalAddressPostalCode()));
        postalAddress.setRegion(StringUtils.trimToNull(partyForm.getTxtPostalAddressRegion()));
        postalAddress.setCountry(EnumerationReference.convertFromString(partyForm.getSelPostalAddressCountryName()));
    }

    private void populateUnstructuredAddressFromForm(UnstructuredAddress unstructuredAddress, PartyForm partyForm) {
        unstructuredAddress.setAddressLine1(StringUtils.trimToNull(partyForm.getTxtAddressLine1()));
        unstructuredAddress.setAddressLine2(StringUtils.trimToNull(partyForm.getTxtAddressLine2()));
        unstructuredAddress.setAddressLine3(StringUtils.trimToNull(partyForm.getTxtAddressLine3()));
        unstructuredAddress.setAddressLine4(StringUtils.trimToNull(partyForm.getTxtAddressLine4()));
        unstructuredAddress.setSubregion(StringUtils.trimToNull(partyForm.getTxtUnstructuredAddressSubRegion()));
        unstructuredAddress.setCity(StringUtils.trimToNull(partyForm.getTxtUnstructuredAddressCity()));
        unstructuredAddress.setPostalCode(StringUtils.trimToNull(partyForm.getTxtUnstructuredAddressPostalCode()));
        unstructuredAddress.setRegion(StringUtils.trimToNull(partyForm.getTxtUnstructuredAddressRegion()));
        unstructuredAddress.setCountry(EnumerationReference.convertFromString(partyForm.getSelUnstructuredAddressCountryName()));
    }

    public void editContactOfContactPref(ApplicationContext applicationContext, PartyForm partyForm,
            RolePlayer rolePlayer) throws Exception {

        Long selectedContactPointId = partyForm.getContactSelected();
        if (selectedContactPointId == null) {
            return;
        }

        Set<ContactPreference> preferences;
        if (Party.class.isAssignableFrom(rolePlayer.getClass())) {
            preferences = partyForm.getContactPrefsForParty();
        } else {
            preferences = (Set<ContactPreference>) customerRelationshipService
                    .findContactPreferencesAndPoints(applicationContext, rolePlayer.getObjectReference());
        }

        ContactPreference preference = null;
        ContactPoint point = null;
        for (ContactPreference candidatePreference : preferences) {
            for (ContactPoint candidatePoint : candidatePreference.getContactPoints()) {
                if (selectedContactPointId.equals(candidatePoint.getId())) {
                    preference = candidatePreference;
                    point = candidatePoint;
                    break;
                }
            }
        }

        if (point == null) {
            throw new IllegalStateException("Unable to find a contact point with id: " + selectedContactPointId);
        }

        Date effectiveFrom;
        Date effectiveTo;
        if (point instanceof TelephoneNumber) {
            TelephoneNumber t = (TelephoneNumber) point;

            effectiveFrom = typeParser.parseDate(partyForm.getTxtStartDateNumber());
            effectiveTo = typeParser.parseDate(partyForm.getTxtEndDateNumber());
            populateTelephoneNumberFromForm(applicationContext, t, partyForm);

        } else if (point instanceof Address) {

            if (point instanceof PostalAddress) {
                PostalAddress p = (PostalAddress) point;

                effectiveFrom = typeParser.parseDate(partyForm.getTxtStartDatePostalAddress());
                effectiveTo = typeParser.parseDate(partyForm.getTxtEndDatePostalAddress());
                populatePostalAddressFromForm(p, partyForm);

            } else if (point instanceof PhysicalAddress) {
                PhysicalAddress p = (PhysicalAddress) point;

                effectiveFrom = typeParser.parseDate(partyForm.getTxtStartDatePhysicalAddress());
                effectiveTo = typeParser.parseDate(partyForm.getTxtEndDatePhysicalAddress());
                populatePhysicalAddressFromForm(p, partyForm);

            } else if (point instanceof UnstructuredAddress) {
                UnstructuredAddress p = (UnstructuredAddress) point;

                effectiveFrom = typeParser.parseDate(partyForm.getTxtStartDateUnstructuredAddress());
                effectiveTo = typeParser.parseDate(partyForm.getTxtEndDateUnstructuredAddress());
                populateUnstructuredAddressFromForm(p, partyForm);
            }
            else {
                throw new IllegalStateException("Unknown address type: " + point.getClass());
            }

        } else if (point instanceof ElectronicAddress) {
            ElectronicAddress e = (ElectronicAddress) point;

            effectiveFrom = typeParser.parseDate(partyForm.getTxtStartDateEmail());
            effectiveTo = typeParser.parseDate(partyForm.getTxtEndDateEmail());
            populateElectronicAddressFromForm(e, partyForm);
        } else {
            throw new IllegalStateException("Unknown contact point type: " + point.getClass());
        }

        if(effectiveFrom == null) {
            effectiveFrom = Date.today();
        }
        point.setEffectivePeriod(new DatePeriod(effectiveFrom, effectiveTo));

        partyForm.resetContactPointFields();
        customerRelationshipService.modifyContactPoint(applicationContext, point);
    }

    public PartyForm contactsDisplay(HttpServletRequest httpServletRequest,
            ApplicationContext applicationContext, RolePlayer rolePlayer, PartyForm partyForm)
            throws Exception {
        Set<ContactPreference> preferences = null;
        if (rolePlayer instanceof Party) {
            preferences = partyForm.getContactPrefsForParty();
        } else {
            if (rolePlayer instanceof PartyRole) {
                preferences = (Set<ContactPreference>) customerRelationshipService
                        .findContactPreferencesAndPoints(applicationContext, rolePlayer.getObjectReference());
            }
        }
        partyForm.setDefContPref(false);

        Map<ContactPreference, SortedMap<String, SortedMap<Long, ContactPoint>>> prefsPointsMap =
                new HashMap<>();

        int totContacts = 0;

        if (preferences != null) {

            Random random = new Random();

            for (ContactPreference contactPreference : preferences) {

                // don't show "Banking" contact preference on ContactDetils
                //   window AccountDetails shows banking details
                if (!contactPreference.getTypeId().equals(CoreTypeReference.BANKING.getValue())) {

                    SortedMap<String, SortedMap<Long, ContactPoint>> contactPointsGuiDisplayMap =
                            new TreeMap<String, SortedMap<Long, ContactPoint>>();
                    SortedMap<Long, ContactPoint> emailsGuiDisplayMap = new TreeMap<Long, ContactPoint>();
                    SortedMap<Long, ContactPoint> telephoneNumbersGuiDisplayMap = new TreeMap<Long, ContactPoint>();
                    SortedMap<Long, ContactPoint> physicalAddressesGuiDisplayMap = new TreeMap<Long, ContactPoint>();
                    SortedMap<Long, ContactPoint> postalAddressesGuiDisplayMap = new TreeMap<Long, ContactPoint>();
                    SortedMap<Long, ContactPoint> unstructuredAddressesGuiDisplayMap = new TreeMap<Long, ContactPoint>();

                    for (ContactPoint contactPoint : contactPreference.getContactPoints()) {
                        // if delegating setup, generate a random id for the contact point
                        if (partyForm.getIsDelegating() && contactPoint.getId() == null){
                            contactPoint.setId(random.nextLong());
                        }

                        final Long key = contactPoint.getId();
                        if (contactPoint instanceof ElectronicAddress) {
                            emailsGuiDisplayMap.put(key, contactPoint);
                        } else if (contactPoint instanceof TelephoneNumber) {
                            telephoneNumbersGuiDisplayMap.put(key, contactPoint);
                        } else if (contactPoint instanceof PhysicalAddress) {
                            physicalAddressesGuiDisplayMap.put(key, contactPoint);
                        } else if (contactPoint instanceof PostalAddress) {
                            postalAddressesGuiDisplayMap.put(key, contactPoint);
                        } else if (contactPoint instanceof UnstructuredAddress) {
                            unstructuredAddressesGuiDisplayMap.put(key, contactPoint);
                        }
                        totContacts++;
                    }
                    contactPointsGuiDisplayMap.put("emails", emailsGuiDisplayMap);
                    contactPointsGuiDisplayMap.put("phones", telephoneNumbersGuiDisplayMap);
                    contactPointsGuiDisplayMap.put("physicals", physicalAddressesGuiDisplayMap);
                    contactPointsGuiDisplayMap.put("postals", postalAddressesGuiDisplayMap);
                    contactPointsGuiDisplayMap.put("unstructureds", unstructuredAddressesGuiDisplayMap);
                    prefsPointsMap.put(contactPreference, contactPointsGuiDisplayMap);
                }
            }
        }
        partyForm.setPrefsPointsMap(prefsPointsMap);
        partyForm.setContactPointSize(totContacts);

        return partyForm;
    }

    public Party addExistingContactToContactPref(ApplicationContext applicationContext, ContactPoint point,
            PartyForm partyForm, RolePlayer rolePlayer) throws Exception {
        Party party = partyForm.getParty();
        Set<ContactPreference> contactPrefs = partyForm.getContactPrefsForParty();
        ContactPreference contactPref = null;
        for (ContactPreference contactPreference : contactPrefs) {
            contactPref = contactPreference;
            if (contactPref.getTypeId().equals(partyForm.getPreferenceSelected())) {
                customerRelationshipService.attachContactPoint(applicationContext, rolePlayer.getObjectReference(),
                        contactPref, point);
                break;
            }
        }
        return party;
    }

    /**
     * Return the concrete contact point that the user wishes to add
     */
    private ContactPoint createContactPointForType(ApplicationContext applicationContext, Long selectedContactPointType) {

        Type type = productDevelopmentService.getType(applicationContext, selectedContactPointType);
        if (type == null) {
            throw new IllegalStateException("Unknown type: " + selectedContactPointType);
        }
        String className = type.getQualifiedClassName();

        try {
            Class clazz = Class.forName(className);
            return (ContactPoint) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            throw new ApplicationRuntimeException("Could not find class [" + className + "]: " + e.getMessage(), e);
        } catch (InstantiationException e) {
            throw new ApplicationRuntimeException("Could not create class [" + className + "]: " + e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new ApplicationRuntimeException("Could not create class [" + className + "]: " + e.getMessage(), e);
        }
    }

}
