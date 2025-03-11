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
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.common.enumeration.Gender;
import com.silvermoongroup.fsa.web.locale.ITypeParser;
import com.silvermoongroup.fsa.web.party.form.AddPartyForm;
import com.silvermoongroup.party.domain.*;
import org.apache.commons.lang3.StringUtils;

public class AddPartyActionHelper {

    public AddPartyActionHelper() {
    }

    /**
     * Add a Party by details added on the form
     * 
     * @param form The add party form
     * @param IPartyService
     * @param typeParser
     * @param productDevelopmentService
     * @return The Party object
     */
    public Party addPersonFromForm(AddPartyForm form, ICustomerRelationshipService IPartyService,
            ITypeParser typeParser, IProductDevelopmentService productDevelopmentService) {

        Person person = new Person();

        PartyNamesActionHelper iPartyNamesActionHelper = new PartyNamesActionHelper();
        PersonName personName = new PersonName();
        iPartyNamesActionHelper.setMinimumPersonValues(form, personName, typeParser, productDevelopmentService);
        person.setDefaultName(personName);
        person.setGender(Gender.fromCode(Long.valueOf(form.getSelGender())));
        if (!form.getTxtBirthDate().equals("")) {
            person.setBirthDate(typeParser.parseDate(form.getTxtBirthDate()));
        }
        return person;
    }

    /**
     * Add a new organization from the details filled in on the form.
     * 
     * @param applicationContext
     *            The application context
     * @param typeService
     * @param form
     *            The add party form
     * @return The Organization object
     */
    public Organisation addOrganisationFromForm(ApplicationContext applicationContext, AddPartyForm form,
            IProductDevelopmentService typeService) {

        Type organisationType = typeService.getType(applicationContext, form.getTxtOrgType());
        if (organisationType == null) {
            throw new IllegalStateException("Unknown organisation type: " + form.getTxtOrgType());
        }

        Organisation organisation = new Organisation();
        organisation.setTypeId(organisationType.getId());

        // include a default name
        UnstructuredName defaultName = new UnstructuredName();
        defaultName.setType(CoreTypeReference.UNSTRUCTUREDNAME);
        defaultName.setFullName(StringUtils.trimToEmpty(form.getTxtOrgFullName()));
        defaultName.setEffectivePeriod(DatePeriod.startingOn(new Date()));
        organisation.setDefaultName(defaultName);

        return organisation;
    }
}
