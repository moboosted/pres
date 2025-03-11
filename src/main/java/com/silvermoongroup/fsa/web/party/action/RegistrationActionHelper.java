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
import com.silvermoongroup.common.constants.Components;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.datatype.Period;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import com.silvermoongroup.fsa.web.locale.ITypeParser;
import com.silvermoongroup.fsa.web.party.form.PartyForm;
import com.silvermoongroup.party.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class RegistrationActionHelper {

    public RegistrationActionHelper() {
    }

    public void addBirthCertificationFromForm(Person party, PartyForm partyForm, ITypeParser typeParser) {

        // Birth Reg is created when the party is created
        BirthCertificate birthCertificate = new BirthCertificate();
        birthCertificate.setType(CoreTypeReference.BIRTHCERTIFICATE);
        birthCertificate.setRegisteredBirthDate(typeParser.parseDate(StringUtils.trimToNull(partyForm
                .getNameStartDate2())));
        birthCertificate.setIssueDate(typeParser.parseDate(StringUtils.trimToNull(partyForm.getTxtBDIssueDate())));

        PartyName name = party.getDefaultName();
        if (name.getTypeId().equals(CoreTypeReference.BIRTHNAME.getValue())) {
            birthCertificate.setRegisteredPartyName(name);
        } else {
            birthCertificate.setRegisteredPartyName(null);
        }
        birthCertificate.setDescription(StringUtils.trimToNull(partyForm.getTxtBDRegisteringAuthority()));
        party.addPartyRegistration(birthCertificate);
    }

    public void editBirthRegBirthDate(PartyForm partyForm, ICustomerRelationshipService IPartyService,
            ITypeParser typeParser) {
        if (partyForm.getPartyRegistrations() == null) {
            Party p = IPartyService.getPartyForPartyOrPartyRole(new ApplicationContext(), partyForm
                    .getPartyObjectRefAsObjectReference(), new String[] { "partyRegistrations" });
            partyForm.setPartyRegistrations(p.getPartyRegistrations());
        }
        for (PartyRegistration partyRegistration : partyForm.getPartyRegistrations()) {
            if (partyRegistration instanceof BirthCertificate
                    && partyRegistration.getTypeId().equals(CoreTypeReference.BIRTHCERTIFICATE.getValue())) {
                BirthCertificate birth = (BirthCertificate) partyRegistration;
                birth.setRegisteredBirthDate(typeParser.parseDate(StringUtils.trimToNull(partyForm.getNameStartDate2
                        ())));
                IPartyService.modifyPartyRegistration(new ApplicationContext(), partyRegistration);
            }
        }
    }

    public void editBirthRegBirthName(PartyForm partyForm, com.silvermoongroup.party.domain.PartyName personName,
            ICustomerRelationshipService IPartyService) {
        Party party = IPartyService.getPartyForPartyOrPartyRole(new ApplicationContext(), partyForm
                .getPartyObjectRefAsObjectReference(), new String[] { "partyRegistrations" });
        Set<PartyRegistration> registrations = party.getPartyRegistrations();

        for (PartyRegistration partyRegistration : registrations) {

            if (partyRegistration instanceof BirthCertificate birth
                    && partyRegistration.getTypeId().equals(CoreTypeReference.BIRTHCERTIFICATE.getValue())) {
                birth.setRegisteredPartyName(personName);
                IPartyService.modifyPartyRegistration(new ApplicationContext(), birth);
                break;
            }
        }
    }

    public void editRegistration(PartyForm partyForm, ActionMessages messages,
            ICustomerRelationshipService IPartyService, IProductDevelopmentService productDevelopmentService, ITypeParser typeParser) {

        PartyRegistration registration = IPartyService.getPartyRegistration(new ApplicationContext(),
                new ObjectReference(
                Components.PARTY, partyForm.getSelRegistrationTypeForEdit(), partyForm.getRegSelected()));

        if (registration instanceof BirthCertificate birth) {
            birth.setRegisteredBirthDate(typeParser.parseDate(StringUtils.trimToNull(partyForm.getTxtBirthDate())));
            birth.setIssueDate(typeParser.parseDate(StringUtils.trimToNull(partyForm.getTxtBDIssueDate())));
            birth.setDescription(StringUtils.trimToNull(partyForm.getTxtBDRegisteringAuthority()));
        } else if (registration instanceof DeathCertificate death) {
            death.setRegisteredDeathDate(typeParser.parseDate(StringUtils.trimToNull(partyForm.getTxtDateOfDeath())));
            death.setIssueDate(typeParser.parseDate(StringUtils.trimToNull(partyForm.getTxtDateOfDeathIssueDate())));
            death.setExternalReference(StringUtils.trimToNull(partyForm.getTxtDateOfDeathRegistrationNumber()));
            death.setDescription(partyForm.getTxtDateOfDeathRegisteringAuthority());
        } else if (registration instanceof DriversLicence driversLicenceReg) {
            driversLicenceReg.setIssueDate(typeParser.parseDate(StringUtils.trimToNull(partyForm
                    .getTxtDriversLicenceIssueDate())));
            Date endDate = typeParser.parseDate(StringUtils.trimToNull(partyForm
                    .getTxtDriversLicenceEndDate()));
            driversLicenceReg.setEffectivePeriod(getPeriod(Date.today(), endDate));
            driversLicenceReg.setRenewalDate(typeParser.parseDate(StringUtils.trimToNull(partyForm
                    .getTxtDriversLicenceRenewalDate())));
            driversLicenceReg.setExternalReference(partyForm.getTxtDriversLicenceExternalReference());
        } else if (registration instanceof EducationCertificate education) {
            Date startDate = typeParser.parseDate(StringUtils.trimToNull(partyForm.getTxtEducationDateOfRegistration()));
            // use new method to get period RequestSpecHandler
            education.setEffectivePeriod(getPeriod(startDate, Date.FUTURE));
            education.setIssueDate(typeParser.parseDate(partyForm.getTxtEducationIssueDate()));
            education.setExternalReference(StringUtils.trimToNull(partyForm.getTxtEducationRegistrationNumber()));
            education.setDescription(partyForm.getTxtEducationRegisteringAuthority());
        } else if (registration instanceof MarriageRegistration marriage) {
            Date startDate = typeParser.parseDate(StringUtils.trimToNull(partyForm.getTxtMaritalStartDate()));
            Date endDate = typeParser.parseDate(StringUtils.trimToNull(partyForm.getTxtMaritalEndDate()));
            marriage.setEffectivePeriod(getPeriod(startDate, endDate));
            marriage.setExternalReference(StringUtils.trimToNull(partyForm.getTxtMRRegistrationNumber()));
            marriage.setDescription(StringUtils.trimToNull(partyForm.getTxtMRRegisteringAuthority()));
        } else if (registration instanceof NationalRegistration national) {
            national.setIssueDate(typeParser.parseDate(StringUtils.trimToNull(partyForm.getTxtNRIssueDate())));
            national.setDescription(StringUtils.trimToNull(partyForm.getTxtNRRegisteringAuthority()));
            national.setExternalReference(StringUtils.trimToNull(partyForm.getTxtIdentityNumber()));
            national.setCountryCode(getCountryCodeEnum(partyForm.getSelNationalRegCountryName(), productDevelopmentService));
        } else if (registration instanceof Accreditation accreditation) {
            Date startDate = typeParser.parseDate(StringUtils.trimToNull(partyForm
                    .getTxtAccreditationRegistrationStartDate()));
            Date endDate = typeParser.parseDate(StringUtils.trimToNull(partyForm
                    .getTxtAccreditationRegistrationEndDate()));
            Period effectivePeriod = getPeriod(startDate, endDate);
            accreditation.setEffectivePeriod(effectivePeriod);
            accreditation.setIssueDate(typeParser.parseDate(StringUtils.trimToNull(partyForm
                    .getTxtAccreditationRegistrationIssueDate())));
        } else {
            Date startDate = typeParser.parseDate(StringUtils.trimToNull(partyForm
                    .getTxtAccreditationRegistrationStartDate()));
            Date endDate = typeParser.parseDate(StringUtils.trimToNull(partyForm
                    .getTxtPartyRegistrationEndDate()));
            registration.setEffectivePeriod(getPeriod(startDate, endDate));
            registration.setIssueDate(typeParser.parseDate(StringUtils.trimToNull(partyForm
                    .getTxtPartyRegistrationIssueDate())));
            registration.setDescription(StringUtils.trimToNull(partyForm.getTxtPartyRegistrationDescription()));
        }
        IPartyService.modifyPartyRegistration(new ApplicationContext(), registration);
    }

    public PartyForm registrationDisplay(HttpServletRequest request, PartyForm partyForm,
            Party party)  {
        SortedMap<Long, BirthCertificate> birthRegDisplayMap = new TreeMap<>();
        SortedMap<Long, DeathCertificate> deathRegDisplayMap = new TreeMap<>();
        SortedMap<Long, DriversLicence> licenceRegsDisplayMap = new TreeMap<>();
        SortedMap<Long, PartyRegistration> educationRegsDisplayMap = new TreeMap<>();
        SortedMap<Long, PartyRegistration> marriageRegsDisplayMap = new TreeMap<>();
        SortedMap<Long, NationalRegistration> nationalRegPersonDisplayMap = new TreeMap<>();
        SortedMap<Long, Accreditation> accreditationRegsDisplayMap = new TreeMap<>();
        SortedMap<Long, PartyRegistration> partyRegsDisplayMap = new TreeMap<>();

        // If delegating setup, generate party registration IDs.
        if(partyForm.getIsDelegating()){
            Random random = new Random();
            party.getPartyRegistrations().forEach(partyRegistration -> {
                if(partyRegistration.getId() == null){
                    partyRegistration.setId(random.nextLong());
                }
            });
        }

        for (PartyRegistration registration : party.getPartyRegistrations()) {

            if (registration instanceof BirthCertificate birthCert) {
                birthRegDisplayMap.put(birthCert.getId(), birthCert);
            } else if (registration instanceof DeathCertificate deathCert) {
                if (deathCert.getRegisteredDeathDate() != null) {
                    partyForm.setTxtDateOfDeath(FormatUtil.getTypeFormatter(request).formatDate(deathCert
                            .getRegisteredDeathDate()));
                    partyForm.setTxtDeathDateDisplay(partyForm.getTxtDateOfDeath());
                }
                deathRegDisplayMap.put(deathCert.getId(), deathCert);
            } else if (registration instanceof EducationCertificate) {
                educationRegsDisplayMap.put(registration.getId(), registration);
            } else if (registration instanceof DriversLicence driversLicence) {
                licenceRegsDisplayMap.put(driversLicence.getId(), driversLicence);
            } else if (registration instanceof MarriageRegistration) {
                marriageRegsDisplayMap.put(registration.getId(), registration);
            } else if (registration instanceof NationalRegistration national) {
                nationalRegPersonDisplayMap.put(national.getId(), national);
            } else if (registration instanceof Accreditation accreditation) {
                accreditationRegsDisplayMap.put(registration.getId(), accreditation);
            } else {
                partyRegsDisplayMap.put(registration.getId(), registration);
            }
        }
        partyForm.setBirthRegDisplayMap(birthRegDisplayMap);
        partyForm.setDeathRegDisplayMap(deathRegDisplayMap);
        partyForm.setLicenceRegsDisplayMap(licenceRegsDisplayMap);
        partyForm.setEducationRegsDisplayMap(educationRegsDisplayMap);
        partyForm.setMarriageRegsDisplayMap(marriageRegsDisplayMap);
        partyForm.setNationalRegPersonDisplayMap(nationalRegPersonDisplayMap);
        partyForm.setAccreditationRegsDisplayMap(accreditationRegsDisplayMap);
        partyForm.setPartyRegsDisplayMap(partyRegsDisplayMap);
        return partyForm;
    }

    private EnumerationReference getCountryCodeEnum(String countryCodeName, IProductDevelopmentService
            productDevelopmentService) {
        if (GenericValidator.isBlankOrNull(countryCodeName)) {
            return null;
        }

        IEnumeration cc = productDevelopmentService.getEnumeration(new ApplicationContext(),
                EnumerationReference.convertFromString(countryCodeName));

        return cc != null ? cc.getEnumerationReference() : null;
    }

    public void addRegistration(ApplicationContext applicationContext, PartyForm partyForm, ActionMessages messages,
            ICustomerRelationshipService IPartyService, IProductDevelopmentService productDevelopmentService,
            ITypeParser typeParser) {

        ObjectReference partyObjRef = partyForm.getPartyObjectRefAsObjectReference();
        Long selectedRegistrationTypeId = partyForm.getSelRegistrationType();
        PartyRegistration newRegistration = createRegistrationForType(applicationContext, productDevelopmentService,
                selectedRegistrationTypeId);

        if (newRegistration instanceof BirthCertificate birth) {
            birth.setRegisteredBirthDate(typeParser.parseDate(StringUtils.trim(
                    partyForm.getTxtBirthDate())));
            birth.setIssueDate(typeParser.parseDate(StringUtils.trim(partyForm.getTxtBDIssueDate())));
            birth.setDescription(StringUtils.trim(partyForm.getTxtBDRegisteringAuthority()));

        } else if (newRegistration instanceof DeathCertificate death) {
            Date dateOfDeath = typeParser.parseDate(partyForm.getTxtDateOfDeath());

            if (dateOfDeath != null) {
                Date birthDate = typeParser.parseDate(partyForm.getNameStartDate2());
                if (dateOfDeath.isBefore(birthDate)) {
                    messages.add("msg", new ActionMessage("page.party.message.birthDateAfterDeathDate"));
                    return;
                }
            }
            death.setRegisteredDeathDate(typeParser.parseDate(StringUtils.trim(partyForm.getTxtDateOfDeath())));
            death.setIssueDate(typeParser.parseDate(StringUtils.trim(partyForm.getTxtDateOfDeathIssueDate())));
            death.setExternalReference(StringUtils.trim(partyForm.getTxtDateOfDeathRegistrationNumber()));
            death.setDescription(StringUtils.trim(partyForm.getTxtDateOfDeathRegisteringAuthority()));

        } else if (newRegistration instanceof DriversLicence drivers) {
            drivers.setExternalReference(StringUtils.trim(partyForm.getTxtDriversLicenceExternalReference()));
            Date issueDate = typeParser.parseDate(StringUtils.trim(partyForm.getTxtDriversLicenceIssueDate()));
            if (issueDate != null && issueDate.isAfter(Date.today())) {
                messages.add("msg", new ActionMessage("page.party.message.driversLicenseIssueDateAfterCurrent"));
            }
            drivers.setIssueDate(issueDate);
            Date endDate = typeParser.parseDate(StringUtils.trim(partyForm.getTxtDriversLicenceEndDate()));
            drivers.setEffectivePeriod(getPeriod(Date.today(), endDate));
            drivers.setRenewalDate(typeParser.parseDate(StringUtils.trim(partyForm.getTxtDriversLicenceRenewalDate())));
            if (drivers.getRenewalDate() != null && drivers.getIssueDate() != null) {
                if (drivers.getRenewalDate().isBefore(drivers.getIssueDate())) {
                    messages.add("msg", new ActionMessage("page.party.message" +
                            ".driversLicenseRenewalDateBeforeIssueDate"));
                }
            }
        } else if (newRegistration instanceof EducationCertificate education) {
            education.setIssueDate(typeParser.parseDate(StringUtils.trimToNull(partyForm.getTxtEducationIssueDate())));
            Date startDate = typeParser.parseDate(StringUtils.trimToNull(partyForm.getTxtEducationDateOfRegistration()));
            Period effectivePeriod = getPeriod(startDate, Date.FUTURE);
            education.setEffectivePeriod(effectivePeriod);
            education.setDescription(StringUtils.trimToNull(partyForm.getTxtEducationRegisteringAuthority()));
            education.setExternalReference(StringUtils.trimToNull(partyForm.getTxtEducationRegistrationNumber()));

        } else if (newRegistration instanceof MarriageRegistration marriage) {

            Date startDate = typeParser.parseDate(StringUtils.trimToNull(partyForm.getTxtMaritalStartDate()));
            Date endDate = typeParser.parseDate(StringUtils.trimToNull(partyForm.getTxtMaritalEndDate()));
            Period effectivePeriod = getPeriod(startDate, endDate);
            marriage.setEffectivePeriod(effectivePeriod);
            marriage.setExternalReference(StringUtils.trimToNull(partyForm.getTxtMRRegistrationNumber()));
            marriage.setDescription(StringUtils.trimToNull(partyForm.getTxtMRRegisteringAuthority()));
        }
        // National
        else if (newRegistration instanceof NationalRegistration national) {

            national.setIssueDate(typeParser.parseDate(StringUtils.trimToNull(partyForm.getTxtNRIssueDate())));
            national.setExternalReference(StringUtils.trimToNull(partyForm.getTxtIdentityNumber()));
            national.setDescription(StringUtils.trimToNull(partyForm.getTxtNRRegisteringAuthority()));
            national.setCountryCode(getCountryCodeEnum(StringUtils.trimToNull(partyForm.getSelNationalRegCountryName()), productDevelopmentService));

        } else if (newRegistration instanceof Accreditation accreditation) {

            Date startDate = typeParser.parseDate(StringUtils.trimToNull(partyForm
                    .getTxtAccreditationRegistrationStartDate()));
            Date endDate = typeParser.parseDate(StringUtils.trimToNull(partyForm
                    .getTxtAccreditationRegistrationEndDate()));
            Period effectivePeriod = getPeriod(startDate, endDate);
            accreditation.setEffectivePeriod(effectivePeriod);
            accreditation.setIssueDate(startDate);
            newRegistration = accreditation;
        } else {
            Date startDate = typeParser.parseDate(StringUtils.trimToNull(partyForm
                    .getTxtAccreditationRegistrationStartDate()));
            Date endDate = typeParser.parseDate(StringUtils.trimToNull(partyForm
                    .getTxtAccreditationRegistrationEndDate()));
            Period effectivePeriod = getPeriod(startDate, endDate);
            newRegistration.setEffectivePeriod(effectivePeriod);
            newRegistration.setIssueDate(typeParser.parseDate(StringUtils.trimToNull(partyForm
                    .getTxtPartyRegistrationIssueDate())));
            newRegistration.setDescription(StringUtils.trimToNull(partyForm.getTxtPartyRegistrationDescription()));
        }

        if (messages.size() > 0) {
            return;
        }

        newRegistration.setTypeId(selectedRegistrationTypeId);
        IPartyService.establishPartyRegistration(new ApplicationContext(), partyObjRef, newRegistration);
    }

    private static Period getPeriod(Date startDate, Date endDate) {
        if(endDate == null){
            endDate = Date.FUTURE;
        }
        if(startDate == null){
            if(Date.today().isAfter(endDate)) {
                startDate = endDate;
            } else {
                startDate = Date.today();
            }
        }
        Period effectivePeriod = new DatePeriod(startDate, endDate);
        return effectivePeriod;
    }

    /**
     * Return the concrete DTO class that should be used for this registration.
     */
    private PartyRegistration createRegistrationForType(ApplicationContext applicationContext,
            IProductDevelopmentService typeService,
            Long selectedRegistrationTypeId) {

        Type type = typeService.getType(applicationContext, selectedRegistrationTypeId);
        if (type == null) {
            throw new IllegalStateException("Unknown type: " + selectedRegistrationTypeId);
        }
        String className = type.getQualifiedClassName();
        try {
            Class clazz = Class.forName(className);
            return (PartyRegistration) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            throw new ApplicationRuntimeException("Could not find class [" + className + "]: " + e.getMessage(), e);
        } catch (InstantiationException e) {
            throw new ApplicationRuntimeException("Could not create class [" + className + "]: " + e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new ApplicationRuntimeException("Could not create class [" + className + "]: " + e.getMessage(), e);
        }
    }
}
