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
import com.silvermoongroup.common.datatype.Period;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.common.ext.enumeration.TypeReference;
import com.silvermoongroup.fsa.web.party.form.PartyForm;
import com.silvermoongroup.fsa.web.party.vo.PartyRelationshipVO;
import com.silvermoongroup.party.domain.Party;
import com.silvermoongroup.party.domain.RolePlayerRelationship;
import org.apache.commons.lang3.Validate;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Jordan Olsen on 19.04.17.
 */
public class PartyRelationshipActionHelper {

    public PartyRelationshipActionHelper() {
    }

    public PartyForm relationshipDisplay(HttpServletRequest request, PartyForm partyForm, ApplicationContext applicationContext,
             ICustomerRelationshipService relationshipService, IProductDevelopmentService typeService, Party party) throws Exception {

        SortedMap<Long, PartyRelationshipVO> partyRelationshipsFrom = new TreeMap<Long, PartyRelationshipVO>();
        SortedMap<Long, PartyRelationshipVO> partyRelationshipsTo = new TreeMap<Long, PartyRelationshipVO>();
        Collection<RolePlayerRelationship> rolePlayerRelationships = relationshipService.findRolePlayerRelationshipsFor(applicationContext, party);

        populateRelationshipMap(applicationContext, partyForm, relationshipService, typeService,
                partyRelationshipsFrom, rolePlayerRelationships, true);
        populateRelationshipMap(applicationContext, partyForm, relationshipService, typeService,
                partyRelationshipsTo, rolePlayerRelationships, false);

        partyForm.setPartyRelationshipsFromMap(partyRelationshipsFrom);
        partyForm.setPartyRelationshipsToMap(partyRelationshipsTo);
        return partyForm;
    }

    private void populateRelationshipMap(ApplicationContext applicationContext, PartyForm partyForm,
                                         ICustomerRelationshipService relationshipService, IProductDevelopmentService typeService,
                                         SortedMap<Long, PartyRelationshipVO> partyRelationships,
                                         Collection<RolePlayerRelationship> rolePlayerRelationships, boolean isFrom) {
        String[] propertiesToLoad = new String[] { "partyNames" };

        for (RolePlayerRelationship relationship : rolePlayerRelationships) {
            if (isFrom && partyForm.getPartyObjectRefAsObjectReference()
                    .equals(relationship.getRelatedTo().getObjectReference())) {
                continue;
            } else if (!isFrom && partyForm.getPartyObjectRefAsObjectReference()
                    .equals(relationship.getRelatedFrom().getObjectReference())) {
                continue;
            }

            PartyRelationshipVO partyRelationshipVO = new PartyRelationshipVO();
            partyRelationshipVO.setId(relationship.getId());

            Type type = typeService.getType(applicationContext, relationship.getTypeId());
            partyRelationshipVO.setType(type.getName());
            partyRelationshipVO.setTypeId(type.getId());

            partyRelationshipVO.setDescription(relationship.getDescription());
            partyRelationshipVO.setStartDate(relationship.getEffectivePeriod().getStart().toString());
            partyRelationshipVO.setEndDate(relationship.getEffectivePeriod().getEnd().toString());

            Party partyFrom = relationshipService.getPartyForPartyOrPartyRole(applicationContext,
                    relationship.getRelatedFrom().getObjectReference(), propertiesToLoad);
            partyRelationshipVO.setRelatedFrom(relationshipService.getFullNameForParty(applicationContext, partyFrom));

            Party partyTo = relationshipService.getPartyForPartyOrPartyRole(applicationContext,
                    relationship.getRelatedTo().getObjectReference(), propertiesToLoad);
            partyRelationshipVO.setRelatedTo(relationshipService.getFullNameForParty(applicationContext, partyTo));

            partyRelationships.put(relationship.getId(), partyRelationshipVO);
        }
    }

    void addRelationship(ApplicationContext applicationContext, PartyForm partyForm, ActionMessages messages,
                         ICustomerRelationshipService iPartyService, IProductDevelopmentService productDevelopmentService) {
        ObjectReference partyObjRef = partyForm.getPartyObjectRefAsObjectReference();
        Long selectedRelationshipTypeId = partyForm.getSelRelationshipTypeFrom();
        RolePlayerRelationship newRelationship = createRelationshipForType(applicationContext, productDevelopmentService,
                selectedRelationshipTypeId);

        newRelationship.setDescription(partyForm.getTxtPartyRelationshipFromDescription());

        Party partyFrom = iPartyService.getPartyForPartyOrPartyRole(applicationContext, partyObjRef, null);
        newRelationship.setRelatedFrom(partyFrom);

        String objRefAsString = partyForm.getTxtPartyRelationshipToObjRef();
        if (objRefAsString == null || objRefAsString.length() == 0) {
            messages.add("msg", new ActionMessage("page.party.message.relatedToEmpty"));
            return;
        }

        ObjectReference objectReference = ObjectReference.convertFromString(objRefAsString);

        Party partyTo = iPartyService.getPartyForPartyOrPartyRole(applicationContext, objectReference, null);
        newRelationship.setRelatedTo(partyTo);

        if (partyObjRef.equals(partyTo.getObjectReference())) {
            messages.add("msg", new ActionMessage("page.party.message.cannotBeSameParty"));
            return;
        }

        if (!checkRelationshipAndRelatedToPartyType(applicationContext, messages, productDevelopmentService,
                selectedRelationshipTypeId, partyTo.getTypeId())) {
            return;
        }

        Date endDate = Date.FUTURE;
        Date startDate = Date.today();

        if (partyForm.getTxtPartyRelationshipFromEndDate() != null && !partyForm.getTxtPartyRelationshipFromEndDate().isEmpty()) {
            endDate = new Date(partyForm.getTxtPartyRelationshipFromEndDate());
        }

        if (partyForm.getTxtPartyRelationshipFromStartDate() != null && !partyForm.getTxtPartyRelationshipFromStartDate().isEmpty()) {
            startDate = new Date(partyForm.getTxtPartyRelationshipFromStartDate());
        }

        if (endDate.isBefore(startDate)) {
            messages.add("msg", new ActionMessage("page.party.message.endDateBeforeStartDate"));
            return;
        }

        Period<Date> effectivePeriod = new DatePeriod(startDate, endDate);
        newRelationship.setEffectivePeriod(effectivePeriod);

        if (messages.size() > 0) {
            return;
        }

        newRelationship.setTypeId(selectedRelationshipTypeId);
        iPartyService.establishRolePlayerRelationship(applicationContext, newRelationship);
    }

    private boolean checkRelationshipAndRelatedToPartyType(ApplicationContext applicationContext, ActionMessages messages,
                                                        IProductDevelopmentService productDevelopmentService, Long selectedRelationshipTypeId,
                                                        Long partyTypeId) {

        if ((productDevelopmentService.isSubType(applicationContext, TypeReference.FROMPERSONTOORGANISATIONRELATIONSHIP.getValue(), selectedRelationshipTypeId)
            || productDevelopmentService.isSubType(applicationContext, TypeReference.FROMORGANISATIONTOORGANISATIONRELATIONSHIP.getValue(), selectedRelationshipTypeId)) &&
                !productDevelopmentService.isSameOrSubType(applicationContext, CoreTypeReference.ORGANISATION.getValue(), partyTypeId)) {
            messages.add("msg", new ActionMessage("page.party.message.personTypeForOrganisationRelationship"));
            return false;
        } else if ((productDevelopmentService.isSubType(applicationContext, TypeReference.FROMORGANISATIONTOPERSONRELATIONSHIP.getValue(), selectedRelationshipTypeId)
                    || productDevelopmentService.isSubType(applicationContext, TypeReference.FROMPERSONTOPERSONRELATIONSHIP.getValue(), selectedRelationshipTypeId)) &&
                !productDevelopmentService.isSameOrSubType(applicationContext, CoreTypeReference.PERSON.getValue(), partyTypeId)) {
            messages.add("msg", new ActionMessage("page.party.message.organisationTypeForPersonRelationship"));
            return false;
        }

        return true;
    }

    /**
     * Return the concrete DTO class that should be used for this relationship.
     */
    private RolePlayerRelationship createRelationshipForType(ApplicationContext applicationContext,
                                                             IProductDevelopmentService typeService,
                                                             Long selectedRelationshipTypeId) {

        Type type = typeService.getType(applicationContext, selectedRelationshipTypeId);

        if (type == null) {
            throw new IllegalStateException("Unknown type: " + selectedRelationshipTypeId);
        }

        String className = type.getQualifiedClassName();

        try {
            Class clazz = Class.forName(className);
            RolePlayerRelationship relationship = (RolePlayerRelationship) clazz.newInstance();
            relationship.setTypeId(selectedRelationshipTypeId);

            return relationship;
        } catch (ClassNotFoundException e) {
            throw new ApplicationRuntimeException("Could not find class [" + className + "]: " + e.getMessage(), e);
        } catch (InstantiationException e) {
            throw new ApplicationRuntimeException("Could not create class [" + className + "]: " + e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new ApplicationRuntimeException("Could not create class [" + className + "]: " + e.getMessage(), e);
        }
    }

    public void editRelationship(PartyForm partyForm, ICustomerRelationshipService iPartyService,  ActionMessages messages) {

        ApplicationContext applicationContext = new ApplicationContext();

        Long selectedRelationshipId = partyForm.getRelFromSelected();
        RolePlayerRelationship relationship = iPartyService.findRolePlayerRelationshipById(applicationContext,
                selectedRelationshipId);

        Assert.notNull(relationship, "A RolePlayerRelationship with id " + selectedRelationshipId
                + " does not exist.");

        relationship.setDescription(partyForm.getTxtPartyRelationshipFromDescription());

        //Start Date of Party Relationship cannot be edited
        Date startDate = relationship.getEffectivePeriod().getStart();
        Date endDate = Date.FUTURE;

        if (partyForm.getTxtPartyRelationshipFromEndDate() != null && !partyForm.getTxtPartyRelationshipFromEndDate().isEmpty()) {
            endDate = new Date(partyForm.getTxtPartyRelationshipFromEndDate());
        }

        if (endDate.isBefore(startDate)) {
            messages.add("msg", new ActionMessage("page.party.message.endDateBeforeStartDate"));
            return;
        }

        Period<Date> effectivePeriod = new DatePeriod(startDate, endDate);
        relationship.setEffectivePeriod(effectivePeriod);

        iPartyService.modifyRolePlayerRelationship(applicationContext, relationship);
    }
}