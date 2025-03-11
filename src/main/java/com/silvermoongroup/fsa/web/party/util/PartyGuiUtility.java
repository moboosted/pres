/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.util;

import com.silvermoongroup.businessservice.customerrelationshipmanagement.service.intf.ICustomerRelationshipService;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.common.ext.enumeration.TypeReference;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import com.silvermoongroup.fsa.web.party.vo.PartySearchResultVO;
import com.silvermoongroup.party.criteria.ContactPointSearchCriteria;
import com.silvermoongroup.party.criteria.OrganisationSearchCriteria;
import com.silvermoongroup.party.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.silvermoongroup.base.util.BeanFactory.getBean;

public class PartyGuiUtility {

    public PartyGuiUtility() {
    }

    public static String PARTY_ROLE_CREATION_OVERRIDE_KEY = "OverridePartyRoleCreation";
    /**
     * Should a party role be created for the party when the party is operating in linking mode (i.e. has been invoked
     * from an external GUI, and therefore may expect a party role back)
     * <p>
     * This logic is triggered from one of two places:
     * <ul>
     * <li>When new party information is entered, and the user clicks to 'Save and Link'</li>
     * <li>When an existing party is selected from a search result - at this stage we need to create the party role
     * information so that other information (e.g. banking details) can added to the party role if required</li>
     * </ul>
     *
     *
     * @param applicationContext The application context
     * @param productDevelopmentService The product development service bean
     * @param request The servlet request.
     * @param roleKind The name of the kind that the party is linking a party to.
     * @return true if the role player should be created, otherwise false.
     */
    public boolean shouldPartyRoleBeCreated(ApplicationContext applicationContext,
            IProductDevelopmentService productDevelopmentService, HttpServletRequest request, String roleKind) {

        String override = (String)request.getSession().getAttribute(PARTY_ROLE_CREATION_OVERRIDE_KEY);
        if("TRUE".equals(override))
        {
            request.getSession().removeAttribute(PARTY_ROLE_CREATION_OVERRIDE_KEY);
            return false;
        }

        Set<Type> typesToExclude = productDevelopmentService.getAllSubTypes(applicationContext,
                CoreTypeReference.ORGANISATION.getValue());
        typesToExclude.addAll(productDevelopmentService.getAllSubTypes(applicationContext,
                CoreTypeReference.INSURER.getValue()));

        Set<String> typeNames = new HashSet<>(typesToExclude.size());
        typeNames.add(CoreTypeReference.ORGANISATION.getName());
        typeNames.add(CoreTypeReference.INSURER.getName());
        for (Type type : typesToExclude) {
            typeNames.add(type.getName());
        }

        return !typeNames.contains(roleKind);
    }

    /**
     * Create a party role for a given role kind, and optional context
     *
     * @param applicationContext The application context
     * @param customerRelationshipService The customer relationship service bean
     * @param rolePlayerObjectRef The party that the role needs to be linked to
     * @param type The type of the role player
     * @param context The optional context of the party role
     * @return The newly created party role
     */
    public PartyRole createPartyRoleForParty(ApplicationContext applicationContext,
            ICustomerRelationshipService customerRelationshipService, ObjectReference rolePlayerObjectRef, Type type,
            ObjectReference context) {
        Assert.notNull(type, "The type of the PartyRole is null");
        PartyRole partyRole = new PartyRole();

        partyRole.setTypeId(type.getId());
        partyRole.setEffectivePeriod(DatePeriod.startingOn(Date.today()));
        partyRole.setContext(context);

        partyRole = customerRelationshipService.establishPartyRole(applicationContext, rolePlayerObjectRef, partyRole);
        return partyRole;
    }

    public List<PartySearchResultVO> retrieveOrganisations(ApplicationContext applicationContext,
            ICustomerRelationshipService customerRelationshipService) {
        OrganisationSearchCriteria organisationSearchCriteria = new OrganisationSearchCriteria();
        return buildOrganisationSearchResults(applicationContext, customerRelationshipService,
                organisationSearchCriteria);
    }

    public List<PartySearchResultVO> buildOrganisationSearchResults(ApplicationContext applicationContext,
            ICustomerRelationshipService customerRelationshipService, OrganisationSearchCriteria criteria) {
        List<Organisation> searchResults = customerRelationshipService.retrieveParties(applicationContext, criteria);

        List<PartySearchResultVO> organisationList = new ArrayList<>();
        for (Organisation org : searchResults) {
            PartySearchResultVO matchSelect = createMatchSelectBeanForOrganisation(org);
            organisationList.add(matchSelect);
        }
        return organisationList;
    }

    /**
     * Create a search results representation for a person.
     */
    public PartySearchResultVO createMatchSelectBeanForPerson(HttpServletRequest request,
            Person person, IProductDevelopmentService productDevelopmentService) {
        PartySearchResultVO matchSelect = new PartySearchResultVO();
        matchSelect.setObjectReference(String.valueOf(person.getObjectReference()));
        matchSelect.setExternalReference(person.getExternalReference());
        if (person.getBirthDate() != null) {
            matchSelect.setBirthDate(FormatUtil.getTypeFormatter(request).formatDate(person.getBirthDate()));
        }
        PersonName personName;
        PersonName defaultName = (PersonName) person.getDefaultName();
        if (defaultName != null) {
            personName = defaultName;
        }
        else {
            if (!person.getAllNames().isEmpty()) {
                personName = (PersonName) person.getAllNames().iterator().next();
            }
            else {
                // this should not occur
                matchSelect.setFullName("Unknown");
                matchSelect.setSurname("Unknown");
                matchSelect.setFirstname("Unknown");
                return matchSelect;
            }
        }
        matchSelect.setFullName(personName.getFullName());
        matchSelect.setSurname(personName.getLastName());
        matchSelect.setFirstname(personName.getFirstName());
        matchSelect.setMiddlename(personName.getMiddleNames());
        matchSelect.setInitials(personName.getInitials());
        if (personName.getPrefixTitles() != null) {
            IEnumeration prefixTitle = productDevelopmentService.getEnumeration(
                    new ApplicationContext(), personName.getPrefixTitles());
            matchSelect.setTitle(prefixTitle.getName());
        }
        // Set Physical Address params
        PhysicalAddress physicalAddress = findPhysicalAddress(new ApplicationContext(), person, getBean(ICustomerRelationshipService.class));
        if(physicalAddress != null) {
            if(StringUtils.isNotEmpty(physicalAddress.getCity())) {
                matchSelect.setPhysicalAddressCity(physicalAddress.getCity());
            } else {
                matchSelect.setPhysicalAddressCity("Unknown");
            }
            if(StringUtils.isNotEmpty(physicalAddress.getPostalCode())) {
                matchSelect.setPhysicalAddressPostalCode(physicalAddress.getPostalCode());
            } else {
                matchSelect.setPhysicalAddressPostalCode("Unknown");
            }
            if(StringUtils.isNotEmpty(physicalAddress.getStreet())) {
                matchSelect.setStreet(physicalAddress.getStreet());
            } else {
                matchSelect.setStreet("Unknown");
            }
            if(StringUtils.isNotEmpty(physicalAddress.getHouseNumber())) {
                matchSelect.setStreetNumber(physicalAddress.getHouseNumber());
            } else {
                matchSelect.setStreetNumber("Unknown");
            }
        } else {
            matchSelect.setPhysicalAddressCity("-");
            matchSelect.setPhysicalAddressPostalCode("-");
            matchSelect.setStreet("-");
            matchSelect.setStreetNumber("-");
        }

        return matchSelect;
    }

    /**
     * Create a search results representation for an organisation.
     */
    public PartySearchResultVO createMatchSelectBeanForOrganisation(Organisation org) {
        PartySearchResultVO matchSelect = new PartySearchResultVO();
        matchSelect.setObjectReference(String.valueOf(org.getObjectReference()));
        matchSelect.setExternalReference(org.getExternalReference());
        UnstructuredName name = (UnstructuredName) org.getDefaultName();
        if (name != null) {
            matchSelect.setFullName(name.getFullName());
        }

        for (PartyName organisationName : org.getAllNames()) {
            UnstructuredName un = (UnstructuredName) organisationName;

            if (name == null) {
                matchSelect.setFullName(un.getFullName());
            }
        }
        if (org.getTypeId() != null) {
            matchSelect.setPartyType(TypeReference.fromValue(org.getTypeId()).getName());
        }
        return matchSelect;
    }


    public PartySearchResultVO searchEmailAddressMatchInfo(ElectronicAddress email) {
        PartySearchResultVO matchSelect = new PartySearchResultVO();
        matchSelect.setContactId(email.getObjectReference().getObjectId().toString());
        matchSelect.setContactObjRef(email.getObjectReference());
        matchSelect.setContactObjRef(email.getObjectReference());
        matchSelect.setEmail(email.getAddress());
        return matchSelect;
    }

    public PartySearchResultVO searchPhysicalAddressMatchInfo(PhysicalAddress physical) {
        PartySearchResultVO matchSelect = new PartySearchResultVO();
        matchSelect.setContactId(physical.getObjectReference().getObjectId().toString());
        matchSelect.setContactObjRef(physical.getObjectReference());
        matchSelect.setBuildingName(physical.getBuildingName());
        matchSelect.setStreetNumber(physical.getHouseNumber());
        matchSelect.setStreet(physical.getStreet());
        matchSelect.setPhysicalAddressPostalCode(physical.getPostalCode());
        matchSelect.setPhysicalAddressSubRegion(physical.getSubregion());
        matchSelect.setPhysicalAddressRegion(physical.getRegion());
        matchSelect.setPhysicalAddressCity(physical.getCity());
        matchSelect.setPhysicalAddressCountryName(physical.getCountry().toString());
        return matchSelect;
    }

    public PartySearchResultVO searchPostalAddressMatchInfo(PostalAddress postal) {
        PartySearchResultVO matchSelect = new PartySearchResultVO();
        matchSelect.setContactId(postal.getObjectReference().getObjectId().toString());
        matchSelect.setContactObjRef(postal.getObjectReference());
        matchSelect.setBoxNumber(postal.getBoxNumber());
        matchSelect.setPostnetSuite(postal.getPostnetSuite());
        matchSelect.setPostalAddressPostalCode(postal.getPostalCode());
        matchSelect.setPostalAddressSubRegion(postal.getSubregion());
        matchSelect.setPostalAddressRegion(postal.getRegion());
        matchSelect.setPostalAddressCity(postal.getCity());
        matchSelect.setPostalAddressCountryName(postal.getCountry().toString());
        return matchSelect;
    }

    public PartySearchResultVO searchTelePhoneNumbersMatchInfo(TelephoneNumber number) {
        StringBuilder telephoneNumber = new StringBuilder();
        telephoneNumber.append(number.getCountryPhoneCode());
        telephoneNumber.append(number.getAreaCode());
        telephoneNumber.append(number.getLocalNumber());

        PartySearchResultVO matchSelect = new PartySearchResultVO();
        matchSelect.setContactId(number.getObjectReference().getObjectId().toString());
        matchSelect.setContactObjRef(number.getObjectReference());
        matchSelect.setNumber(telephoneNumber.toString());
        matchSelect.setExtension(number.getExtension());
        return matchSelect;
    }

    public PartySearchResultVO searchFinancialAccountDetailsMatchInfo(FinancialAccountDetail detail) {
        PartySearchResultVO matchSelect = new PartySearchResultVO();
        matchSelect.setContactId(detail.getObjectReference().getObjectId().toString());
        matchSelect.setContactObjRef(detail.getObjectReference());
        matchSelect.setAccountNumber(detail.getAccountNumber());
        matchSelect.setAccountHolderName(detail.getAccountHolderName());
        matchSelect.setBranchCode(detail.getBankBranchCode());
        matchSelect.setBranchName(detail.getBankBranchName());
        return matchSelect;
    }

    public boolean dateUpdated(String inputDate) {
        boolean updated = false;
        if (inputDate.length() == 10) {
            updated = true;
        }
        return updated;
    }

    public boolean dateUpdated(Date objectDate, Date formDate) {
        boolean updated = false;
        if (!objectDate.isEqual(formDate)) {
            updated = true;
        }
        return updated;
    }

    public static boolean isAmountValid(String amount) {
        for (int i = 0; i < amount.length(); i++) {
            char a = amount.charAt(i);
            if (a >= '0' && a <= '9' || a == '.') {
                // Cannot return from here
            } else {
                return false;
            }
        }
        return true;
    }

    private PhysicalAddress findPhysicalAddress(ApplicationContext applicationContext, Person person, ICustomerRelationshipService iPartyManager) {

        Collection<ContactPreference> contactPreferences = iPartyManager.findContactPreferencesAndPoints(applicationContext, person.getObjectReference());

        for(ContactPreference contactPreference: contactPreferences){
            if(contactPreference.isDefault() && contactPreference.getDefaultContactPoint() != null){
                if((PhysicalAddress.class.isAssignableFrom(contactPreference.getDefaultContactPoint().getClass())))
                    return (PhysicalAddress) contactPreference.getDefaultContactPoint();
            }
            for (ContactPoint contactPoint : contactPreference.getContactPoints()) {
                if (!PhysicalAddress.class.isAssignableFrom(contactPoint.getClass()))
                    continue;
                return (PhysicalAddress) contactPoint;
            }
        }

        return null;
    }
}
