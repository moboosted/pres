/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.action;

import com.silvermoongroup.businessservice.customerrelationshipmanagement.service.intf.ICustomerRelationshipService;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.common.enumeration.EnumerationType;
import com.silvermoongroup.fsa.web.locale.ITypeParser;
import com.silvermoongroup.fsa.web.party.form.AddPartyForm;
import com.silvermoongroup.fsa.web.party.form.PartyForm;
import com.silvermoongroup.fsa.web.party.util.PartyGuiUtility;
import com.silvermoongroup.fsa.web.party.util.PartyNameUtil;
import com.silvermoongroup.party.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;

import java.util.*;

public class PartyNamesActionHelper {

    public PartyNamesActionHelper() {
    }

    public void namesDisplay(Party party, PartyForm partyForm) throws Exception {
        SortedMap<Long, PartyName> partyNamesMap = new TreeMap<Long, PartyName>();
        Set<PartyName> displayNames = party.getAllNames();
        for (PartyName name : displayNames) {

            if (Person.class.isAssignableFrom(party.getClass()) && UnstructuredName.class.isAssignableFrom(name.getClass())) {
                // the 'person' UI cannot display unstructured names
                continue;
            }
            if(!displayNames.isEmpty() && name.getId() == null){
                // this name came from the External system, it does not need to be persisted, we simply generate an ID for it.
                name.setId(new Random().nextLong());
            }
            partyNamesMap.put(name.getId(), name);
        }
        partyForm.setPartyNamesMap(partyNamesMap);
    }

    public String getPartyFullName(PartyName partyName) {
        String fullName = "";
        if (partyName != null) {
            if (partyName instanceof UnstructuredName) {
                fullName = ((UnstructuredName) partyName).getFullName();
            } else if (partyName instanceof PersonName) {
                fullName = ((PersonName) partyName).getFullName();
            }
        } else {
            fullName = "Data error, notify systems"; // should never get
            // here!
        }
        return fullName;
    }

    public PersonName setPersonNameValues(PartyForm partyForm, PersonName personName, ITypeParser typeParser,
            IProductDevelopmentService productDevelopmentService) {
        PartyGuiUtility iPartyGuiUtility = new PartyGuiUtility();
        Date fromDate = new Date();
        Date toDate = Date.FUTURE;
        if (!GenericValidator.isBlankOrNull(partyForm.getNameStartDate())) {
            boolean ddmm = iPartyGuiUtility.dateUpdated(partyForm.getNameStartDate());
            if (ddmm) {
                fromDate = typeParser.parseDate(partyForm.getNameStartDate());
            }
        }
        if (!GenericValidator.isBlankOrNull(partyForm.getNameEndDate())) {
            boolean ddmm = iPartyGuiUtility.dateUpdated(partyForm.getNameEndDate());
            if (ddmm) {
                toDate = typeParser.parseDate(partyForm.getNameEndDate());
            }
        }
        personName.setEffectivePeriod(new DatePeriod(fromDate, toDate));
        personName.setFirstName(partyForm.getTxtFirstName());
        personName.setMiddleNames(partyForm.getTxtMiddleName());
        personName.setShortFirstName(partyForm.getTxtKnownAs());
        personName.setLastName(partyForm.getTxtSurname());
        partyForm.setFullName(PartyNameUtil.getPartyFullName(personName, productDevelopmentService));

        EnumerationReference prefixTitles = EnumerationReference.convertFromString(partyForm.getSelTitle());
        personName.setPrefixTitles(prefixTitles);
        personName.setSuffixTitles(partyForm.getSelSuffix());

        StringBuilder initials = new StringBuilder();
        if (partyForm.getTxtFirstName().length() > 0) {
            initials.append(partyForm.getTxtFirstName().substring(0, 1));
        }
        if (partyForm.getTxtMiddleName().length() > 0) {
            initials.append(" ").append(partyForm.getTxtMiddleName().substring(0, 1));
        }
        personName.setInitials(initials.toString());

        String nameType = partyForm.getSelNameType();

        Type nameTypeObject = productDevelopmentService.getType(new ApplicationContext(), nameType);
        personName.setTypeId(nameTypeObject.getId());


        return personName;
    }
    
    public PersonName setMinimumPersonValues(AddPartyForm partyForm, PersonName personName, ITypeParser typeParser,
            IProductDevelopmentService productDevelopmentService) {
        personName.setEffectivePeriod(DatePeriod.startingOn(new Date()));
        personName.setFirstName(partyForm.getTxtFirstName());
        personName.setMiddleNames(partyForm.getTxtMiddleName());
        personName.setLastName(partyForm.getTxtSurname());
        personName.setLastName(partyForm.getTxtSurname());

        EnumerationReference prefixTitles = new EnumerationReference(Long.valueOf(partyForm.getSelTitle()), EnumerationType.PREFIX_TITLES);
        if (partyForm.getSelTitle() != null) {
            personName.setPrefixTitles(prefixTitles);
        }

        StringBuilder initials = new StringBuilder();
        if (partyForm.getTxtFirstName().length() > 0) {
            initials.append(partyForm.getTxtFirstName().substring(0, 1));
        }
        if (partyForm.getTxtMiddleName().length() > 0) {
            initials.append(" ").append(partyForm.getTxtMiddleName().substring(0, 1));
        }
        personName.setInitials(initials.toString());

        String nameType = partyForm.getSelNameType();

        Type nameTypeObject = productDevelopmentService.getType(new ApplicationContext(), nameType);
        personName.setTypeId(nameTypeObject.getId());
        return personName;
    }

    /**
     * Update the organisation name
     */
    public void updateOrganisationName(PartyForm partyForm, ICustomerRelationshipService partyService) {

        ApplicationContext applicationContext = new ApplicationContext();

        // update the default name
        String defaultNameFromForm = StringUtils.trimToNull(partyForm.getTxtFullName());
        for (PartyName partyName : partyForm.getPartyNames()) {

            UnstructuredName unstructuredName = (UnstructuredName) partyName;

            // we will always have an unstructured name
            if (unstructuredName.getTypeId().equals(CoreTypeReference.UNSTRUCTUREDNAME.getValue())) {
                if (!defaultNameFromForm.equals(unstructuredName.getFullName())) {
                    unstructuredName.setFullName(defaultNameFromForm);
                    partyService.modifyPartyName(applicationContext, unstructuredName, false);
                }
                break;
            }
        }
    }
}
