/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.action;

import com.silvermoongroup.businessservice.customerrelationshipmanagement.service.intf.ICustomerRelationshipService;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.datatype.CurrencyAmount;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.common.enumeration.EnumerationType;
import com.silvermoongroup.common.enumeration.Gender;
import com.silvermoongroup.common.enumeration.intf.ITypeReference;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import com.silvermoongroup.fsa.web.locale.ITypeParser;
import com.silvermoongroup.fsa.web.party.form.PartyForm;
import com.silvermoongroup.fsa.web.party.util.PartyGuiHttpConstants;
import com.silvermoongroup.fsa.web.party.util.PartyGuiUtility;
import com.silvermoongroup.fsa.web.party.util.PartyNameUtil;
import com.silvermoongroup.party.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class GeneralActionHelper {

    public GeneralActionHelper() {
        
    }

    public void loadGeneralPartyData(HttpServletRequest request, ActionForm actionForm,
            ICustomerRelationshipService partyService, IProductDevelopmentService productDevelopmentService)
            throws Exception {
        PartyForm partyForm = (PartyForm) actionForm;
        Party party = partyForm.getParty();
        DecimalFormat df = new DecimalFormat(PartyGuiHttpConstants.DECIMAL_PATTERN);

        if (party != null) {
            partyForm.setExternalReference(party.getExternalReference());
        }

        if (party instanceof Person) {
            determinePersonSelected(partyForm, party);
            Person person = (Person) party;
            PersonName name = (PersonName) person.getDefaultName();
            if (name != null) {
                partyForm.setTxtFirstName(name.getFirstName());
                partyForm.setTxtMiddleName(name.getMiddleNames());
                if (name.getPrefixTitles() != null) {
                    partyForm.setSelTitle(
                            getEnumerationReferenceAsString(name.getPrefixTitles(), productDevelopmentService));
                }
                if (name.getSuffixTitles() != null) {
                    partyForm.setSelSuffix(name.getSuffixTitles());
                }
                partyForm.setTxtSurname(name.getLastName());
                partyForm.setFullName(PartyNameUtil.getPartyFullName(name, productDevelopmentService));

                String typeName = FormatUtil.getTypeFormatter(request).formatType(name.getTypeId());
                partyForm.setSelNameType(typeName);
                partyForm.setSelSuffix(name.getSuffixTitles());
                if (name.getEffectiveFrom() != null) {
                    partyForm.setNameStartDate(FormatUtil.getTypeFormatter(request).formatDate(name.getEffectiveFrom
                            ()));
                }
                if (name.getEffectiveTo() != null) {
                    partyForm.setNameEndDate(FormatUtil.getTypeFormatter(request).formatDate(name.getEffectiveTo()));
                }
                if (name.getInitials() == null) {
                    StringBuilder initials = new StringBuilder("");
                    if (name.getFirstName().length() > 0) {
                        initials.append(partyForm.getTxtFirstName().substring(0, 1));
                    }
                    if (!GenericValidator.isBlankOrNull(name.getMiddleNames()) && name.getMiddleNames().length() > 0) {
                        initials.append(" ").append(partyForm.getTxtMiddleName().substring(0, 1));
                    }
                    partyForm.setTxtInitials(initials.toString());
                } else {
                    partyForm.setTxtInitials(name.getInitials());
            }

            partyForm.setNameStartDate2(FormatUtil.getTypeFormatter(request).formatDate(person.getBirthDate())); // birthDate

            }
            // note: deathdate is on the general page, but value is picked up
            // thru registration
            if (person.getGrossIncome() != null) {
                String frmatdDoubleGross = df.format(person.getGrossIncome().getAmount());
                partyForm.setTxtGrossIncome(frmatdDoubleGross);
            }
            if (person.getDisposableIncome() != null) {
                String frmatdDoubleDisposable = df.format(person.getDisposableIncome().getAmount());
                partyForm.setTxtDisposableIncome(frmatdDoubleDisposable);
            }
            if (person.getGender() != null) {
                partyForm.setSelGender(person.getGender().getCode());
            }
            if (person.getMaritalStatus() != null) {
                partyForm.setSelMaritalStatus(getEnumerationReferenceAsString(person.getMaritalStatus(),
                                                                              productDevelopmentService
                                              ));
            }
            if (person.getHomeLanguage() != null) {
                partyForm.setSelLanguage(getEnumerationReferenceAsString(person.getHomeLanguage(),
                                                                         productDevelopmentService
                                         ));
            }
            if (person.getEducationLevel() != null) {
                partyForm
                        .setSelEducationLevel(getEnumerationReferenceAsString(person.getEducationLevel(),
                                                                              productDevelopmentService
                                              ));
            }
            if (person.getEmploymentStatus() != null) {
                partyForm.setSelEmploymentStatus(getEnumerationReferenceAsString(person.getEmploymentStatus(),
                                                                                 productDevelopmentService
                                                 ));
            }
        } else {
            determineOrgSelected(partyForm, party);
            UnstructuredName defaultName = (UnstructuredName) party.getDefaultName();
            if (defaultName != null) {
                partyForm.setFullName(defaultName.getFullName());
                partyForm.setTxtFullName(defaultName.getFullName()); // for search
            }
        }
    }

    public Party createPersonFromForm(PartyForm partyForm, ICustomerRelationshipService IPartyService,
            IProductDevelopmentService productDevelopmentService, ITypeParser typeParser) {

        Person person = new Person();

        PartyNamesActionHelper iPartyNamesActionHelper = new PartyNamesActionHelper();
        PersonName personName = new PersonName();
        iPartyNamesActionHelper.setPersonNameValues(partyForm, personName, typeParser, productDevelopmentService);
        person.setDefaultName(personName);
        populateGeneralPersonValuesFromForm(person, partyForm, IPartyService, productDevelopmentService, typeParser);
        new RegistrationActionHelper().addBirthCertificationFromForm(person, partyForm, typeParser);

        return person;
    }

    public void populateGeneralPersonValuesFromForm(Person person, PartyForm partyForm,
            ICustomerRelationshipService IPartyService, IProductDevelopmentService productDevelopmentService,
            ITypeParser typeParser) {
        PartyGuiUtility iPartyGuiUtility = new PartyGuiUtility();
        RegistrationActionHelper iRegistrationActionHelper = new RegistrationActionHelper();

        person.setType(savePersonType(partyForm.getSelPartyType()));

        if (!partyForm.getNameStartDate2().equals("")) {
            person.setBirthDate(typeParser.parseDate(partyForm.getNameStartDate2()));
        }
        if (!partyForm.getTxtDateOfDeath().equals("")) {
            person.setDeathDate(typeParser.parseDate(partyForm.getTxtDateOfDeath()));
        }

        // TODO we should default to this code, but allow the user to specify this on the UI
        String localeCurrencyCode = NumberFormat.getCurrencyInstance().getCurrency().getCurrencyCode();
        IEnumeration enumeration = productDevelopmentService.findEnumerationByNameAndType(new ApplicationContext(), localeCurrencyCode,
                EnumerationType.CURRENCY_CODE.getValue());
        if (enumeration == null) {
            throw new IllegalStateException("Unable to derive ICurrencyCode for: " + localeCurrencyCode);
        }


        if (GenericValidator.isBlankOrNull(partyForm.getTxtDisposableIncome())) {
            person.setDisposableIncome(new CurrencyAmount(Double.toString(0), enumeration.getEnumerationReference()));
        } else {
            Double disposableIncDbl = new Double(partyForm.getTxtDisposableIncome().replace(",", ""));
            person.setDisposableIncome(new CurrencyAmount(disposableIncDbl.toString(), enumeration
                    .getEnumerationReference()));
        }
        if (GenericValidator.isBlankOrNull(partyForm.getTxtGrossIncome())) {
            person.setGrossIncome(new CurrencyAmount(Double.toString(0), enumeration.getEnumerationReference()));
        } else {
            Double grossIncDbl = new Double(partyForm.getTxtGrossIncome().replace(",", ""));
            person.setGrossIncome(new CurrencyAmount(grossIncDbl.toString(), enumeration.getEnumerationReference()));
        }

        if (!GenericValidator.isBlankOrNull(partyForm.getSelEducationLevel())) {
            person.setEducationLevel(EnumerationReference.convertFromString(partyForm.getSelEducationLevel()));
        }

        if (!GenericValidator.isBlankOrNull(partyForm.getSelEmploymentStatus())) {
            person.setEmploymentStatus(EnumerationReference.convertFromString(partyForm.getSelEmploymentStatus()));
        }

        if (!GenericValidator.isBlankOrNull(partyForm.getSelEthnicity())) {
            person.setEthnicity(EnumerationReference.convertFromString(partyForm.getSelEthnicity()));
        }

        if (!GenericValidator.isBlankOrNull(partyForm.getSelLanguage())) {
            person.setHomeLanguage(EnumerationReference.convertFromString(partyForm.getSelLanguage()));
        }
        if (!GenericValidator.isBlankOrNull(partyForm.getSelMaritalStatus())) {
            person.setMaritalStatus(EnumerationReference.convertFromString(partyForm.getSelMaritalStatus()));
        }
        person.setGender(Gender.fromCode(partyForm.getSelGender()));


        if (!GenericValidator.isBlankOrNull(partyForm.getNameStartDate2())) {
            if (person.getBirthDate() == null) { // new person
                person.setBirthDate(typeParser.parseDate(partyForm.getNameStartDate2()));
            } else { // updating existing
                boolean birthDateUpdated = iPartyGuiUtility.dateUpdated(typeParser.parseDate(partyForm.getNameStartDate2()),
                        person.getBirthDate());
                if (birthDateUpdated) {
                    person.setBirthDate(typeParser.parseDate(partyForm.getNameStartDate2()));
                    if (partyForm.getPartyObjectRef() != null) {
                        iRegistrationActionHelper.editBirthRegBirthDate(partyForm, IPartyService, typeParser);
                    }

                }
            }
        }
        if (!GenericValidator.isBlankOrNull(partyForm.getTxtDateOfDeath())) {
            if (person.getDeathDate() == null) { // new person
                person.setDeathDate(typeParser.parseDate(partyForm.getTxtDateOfDeath()));
            } else { // updating existing
                boolean deathDateUpdated = iPartyGuiUtility.dateUpdated(typeParser.parseDate(partyForm.getTxtDateOfDeath()),
                        person.getDeathDate());
                if (deathDateUpdated) {
                    person.setDeathDate(typeParser.parseDate(partyForm.getTxtDateOfDeath()));
                }
            }
        }
        if (GenericValidator.isBlankOrNull(partyForm.getTxtDisposableIncome())) {
            person.setDisposableIncome(new CurrencyAmount(Double.toString(0), enumeration.getEnumerationReference()));
        } else {
            Double disposableIncDbl = new Double(partyForm.getTxtDisposableIncome().replace(",", ""));
            person.setDisposableIncome(new CurrencyAmount(disposableIncDbl.toString(), enumeration
                    .getEnumerationReference()));
        }
        if (GenericValidator.isBlankOrNull(partyForm.getTxtGrossIncome())) {
            person.setGrossIncome(new CurrencyAmount(Double.toString(0), enumeration.getEnumerationReference()));
        } else {
            Double grossIncDbl = new Double(partyForm.getTxtGrossIncome().replace(",", ""));
            person.setGrossIncome(new CurrencyAmount(grossIncDbl.toString(), enumeration.getEnumerationReference()));
        }
    }


    /**
     * Create a new organisation from the details filled in on the form.
     */
    public Organisation createOrganisationFromForm(ApplicationContext applicationContext,
                                                   IProductDevelopmentService typeService, PartyForm partyForm) {

        Type organisationType =
                typeService.getType(applicationContext, partyForm.getSelPartyType());
        if (organisationType == null) {
            throw new IllegalStateException("Unknown organisation type: " + partyForm.getSelPartyType());
        }

        Organisation organisation = new Organisation();
        organisation.setTypeId(organisationType.getId());

        // include a default name
        UnstructuredName defaultName = new UnstructuredName();
        defaultName.setType(CoreTypeReference.UNSTRUCTUREDNAME);
        defaultName.setFullName(StringUtils.trimToEmpty(partyForm.getFullName()));
        defaultName.setEffectivePeriod(DatePeriod.startingOn(new Date()));
        organisation.setDefaultName(defaultName);

        return organisation;
    }

    private void determineOrgSelected(PartyForm form, Party party) {
        if (party.getTypeId().equals(CoreTypeReference.ORGANISATION.getValue())) {
            form.setSelPartyType(CoreTypeReference.ORGANISATION.getName());
        } else if (party.getTypeId().equals(CoreTypeReference.COMPANY.getValue())) {
            form.setSelPartyType(CoreTypeReference.COMPANY.getName());
        } else if (party.getTypeId().equals(CoreTypeReference.TRUST.getValue())) {
            form.setSelPartyType(CoreTypeReference.TRUST.getName());
        } else if (party.getTypeId().equals(CoreTypeReference.PROFESSIONGROUP.getValue())) {
            form.setSelPartyType(CoreTypeReference.PROFESSIONGROUP.getName());
        }
    }

    private void determinePersonSelected(PartyForm form, Party party) {
        if (party.getTypeId().equals(CoreTypeReference.PERSON.getValue())) {
            form.setSelPartyType(CoreTypeReference.PERSON.getName());
        }
    }

    private ITypeReference savePersonType(String selPersonValue) {
        ITypeReference updatedTypeRef = null;
        if (selPersonValue.equalsIgnoreCase(CoreTypeReference.PERSON.getName())) {
            updatedTypeRef = CoreTypeReference.PERSON;
        }
        return updatedTypeRef;
    }

    private String getEnumerationReferenceAsString(EnumerationReference enumerationReference,
            IProductDevelopmentService productDevelopmentService) {
        IEnumeration enumeration = productDevelopmentService.getEnumeration(new ApplicationContext(),
                enumerationReference);
        return (enumeration == null ? null : enumeration.getEnumerationReference().toString());
    }

    public void populatePartyName(HttpServletRequest request, PartyForm partyForm, PartyName partyName, IProductDevelopmentService productDevelopmentService) {
        if (partyForm.getPartyObjectRef().contains("8700")) {
            PersonName name = (PersonName) partyName;
            if (name != null) {
                partyForm.setTxtFirstName(name.getFirstName());
                partyForm.setTxtMiddleName(name.getMiddleNames());
                if (name.getPrefixTitles() != null) {
                    partyForm.setSelTitle(
                            getEnumerationReferenceAsString(name.getPrefixTitles(), productDevelopmentService));
                }
                if (name.getSuffixTitles() != null) {
                    partyForm.setSelSuffix(name.getSuffixTitles());
                }
                partyForm.setTxtSurname(name.getLastName());
                partyForm.setFullName(PartyNameUtil.getPartyFullName(name, productDevelopmentService));

                String typeName = FormatUtil.getTypeFormatter(request).formatType(name.getTypeId());
                partyForm.setSelNameType(typeName);
                partyForm.setSelSuffix(name.getSuffixTitles());
                if (name.getEffectiveFrom() != null) {
                    partyForm.setNameStartDate(FormatUtil.getTypeFormatter(request).formatDate(name.getEffectiveFrom
                            ()));
                }
                if (name.getEffectiveTo() != null) {
                    partyForm.setNameEndDate(FormatUtil.getTypeFormatter(request).formatDate(name.getEffectiveTo()));
                }
                if (name.getInitials() == null) {
                    StringBuilder initials = new StringBuilder("");
                    if (name.getFirstName().length() > 0) {
                        initials.append(partyForm.getTxtFirstName().substring(0, 1));
                    }
                    if (!GenericValidator.isBlankOrNull(name.getMiddleNames()) && name.getMiddleNames().length() > 0) {
                        initials.append(" ").append(partyForm.getTxtMiddleName().substring(0, 1));
                    }
                    partyForm.setTxtInitials(initials.toString());
                } else {
                    partyForm.setTxtInitials(name.getInitials());
                }

                partyForm.setNameStartDate2(partyForm.getTxtBirthDate()); // birthDate

            }
        } else {
            UnstructuredName defaultName = (UnstructuredName) partyName;
            if (defaultName != null) {
                partyForm.setFullName(defaultName.getFullName());
                partyForm.setTxtFullName(defaultName.getFullName()); // for search
            }
        }
    }

}
