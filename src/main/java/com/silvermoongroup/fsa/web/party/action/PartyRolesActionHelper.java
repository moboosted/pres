/**
 * Licensed Materials * (C) Copyright Silvermoon Business Systems BVBA, Belgium
 * 2006, 2007 * (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006,
 * 2007 * All Rights Reserved.
 */
package com.silvermoongroup.fsa.web.party.action;

import com.silvermoongroup.base.exception.ApplicationRuntimeException;
import com.silvermoongroup.businessservice.claimmanagement.dto.LossEvent;
import com.silvermoongroup.businessservice.claimmanagement.service.intf.IClaimManagementService;
import com.silvermoongroup.businessservice.customerrelationshipmanagement.service.intf.ICustomerRelationshipService;
import com.silvermoongroup.businessservice.policymanagement.dto.AgreementDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.KindDTO;
import com.silvermoongroup.businessservice.policymanagement.service.intf.IPolicyAdminService;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.claim.domain.intf.IClaim;
import com.silvermoongroup.common.constants.Components;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import com.silvermoongroup.fsa.web.party.form.PartyForm;
import com.silvermoongroup.fsa.web.party.util.PartyGuiHttpConstants;
import com.silvermoongroup.fsa.web.party.vo.ExternalPartyRoleDetailVO;
import com.silvermoongroup.fsa.web.party.vo.PartyRolePartyDetailVO;
import com.silvermoongroup.fsa.web.party.vo.PartyRolesDetailVO;
import com.silvermoongroup.kindmanager.domain.KindCategory;
import com.silvermoongroup.kindmanager.exceptions.KindNotFoundException;
import com.silvermoongroup.party.domain.*;
import com.silvermoongroup.party.exception.PartyException;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class PartyRolesActionHelper {

    private static Logger log = LoggerFactory.getLogger(PartyRolesActionHelper.class);

    public PartyRolesActionHelper() {
    }

    private void getObjectsLinkedToPartyRole(IPolicyAdminService fsaBusinessService, String roleTypeName,
                                                                              PartyRole partyRole, PartyRolesDetailVO partyRolesDetailVO,
                                                                              SortedMap<PartyRolesDetailVO, String> partyRoleAgmtsMap)
            throws KindNotFoundException {

        ApplicationContext applicationContext = new ApplicationContext();
        String agmtExtRefs = "";
        partyRolesDetailVO.setRelationshipRole(false);
        if (partyRole.getContext() != null && partyRole.getContext().getObjectId() != null) {
            //BasicAgreementData basicAgreementData = fsaBusinessService.getBasicAgreementDataForVersionedAgreement(applicationContext, partyRole.getContext());
            AgreementDTO agreement = fsaBusinessService.findLatestLegallyBindingAgreementForAgreementAnchor(applicationContext, partyRole.getContext());
            if (agreement == null){
                agreement = fsaBusinessService.findLatestNonLegallyBindingAgreementForAgreementAnchor(applicationContext, partyRole.getContext());
            }
            if (agreement != null) {
                AgreementDTO topLevelAgreement = fsaBusinessService.getTopLevelAgreementForAgreementPart(
                        applicationContext, agreement.getObjectReference());

                partyRolesDetailVO.setPartyRoleContext(ObjectReference.convertToString(partyRole.getContext()));
                partyRolesDetailVO.setAgreementRole(roleTypeName);
                partyRolesDetailVO.setAgreementKind(topLevelAgreement.getKind().getName());
                partyRolesDetailVO.setAgreementPart(agreement.getKind().getName());
                partyRolesDetailVO.setAgreementNumber(topLevelAgreement.getExternalReference());
                partyRolesDetailVO.setAgreementPartObjectReference(ObjectReference.convertToString(agreement.getObjectReference()));
                partyRolesDetailVO.setPartyRoleObjectReference(ObjectReference.convertToString(partyRole
                        .getObjectReference()));
                partyRolesDetailVO.setAgreementPartContext(ObjectReference.convertToString(topLevelAgreement.getObjectReference()));
            } else {
                emptyPartyRolesDetailVO(partyRolesDetailVO);
            }

            partyRoleAgmtsMap.put(partyRolesDetailVO, agmtExtRefs);
        }
    }

    private void getPartyObjectsLinkedToPartyRole(ApplicationContext applicationContext, ICustomerRelationshipService partyService,
                                                  PartyRole partyRole, PartyRolesDetailVO partyRolesDetailVO,
                                                  SortedMap<PartyRolePartyDetailVO, String> partyRolePartiesMap,
                                                  IProductDevelopmentService typeService) throws KindNotFoundException{
//        ApplicationContext applicationContext = new ApplicationContext();

        String agmtExtRefs = "";
        ObjectReference partyRoleContext = partyRole.getContext();
        if(partyRoleContext !=null) {
            Party linkedParty = partyService.getPartyForPartyOrPartyRole(applicationContext,
                    partyRoleContext, new String[]{"partyNames"});
            PartyRolePartyDetailVO partyRolePartyDetailVO = new PartyRolePartyDetailVO();
            if(linkedParty != null) {
                Type contextType = typeService.getType(applicationContext, linkedParty.getTypeId());
                partyRolePartyDetailVO.setContextObjectReference(partyRole.getObjectReference().toString());
                partyRolePartyDetailVO.setContextPartyName(getPartyDisplayName(linkedParty));
                partyRolePartyDetailVO.setRoleType(partyRole.getDescription());
                partyRolePartyDetailVO.setContextTypeName(contextType.getName());
                partyRolePartyDetailVO.setPartyRoleContext(partyRole.getContext().toString());
                partyRolePartyDetailVO.setPartyRole(partyRolesDetailVO.getPartyRole());
            } else {
                emptyPartyRolesDetailVO(partyRolesDetailVO);
            }
            partyRolePartiesMap.put(partyRolePartyDetailVO, agmtExtRefs);
        }
    }

    private String getPartyDisplayName(Party party) {
        if (party instanceof Person) {
            PersonName personName = (PersonName) party.getDefaultName();
            String personFullName = personName.getFullName();
            if(StringUtils.isEmpty(personFullName)) {
                personFullName = personName.getFirstName()
                        + (StringUtils.isNotEmpty(personName.getMiddleNames())?" " + personName.getMiddleNames() + " ":" ")
                        + personName.getLastName();
            }
            return personFullName;
        } else if (party instanceof Organisation) {
            UnstructuredName orgName = (UnstructuredName) party.getDefaultName();
            return orgName.getFullName();
        }
        return null;
    }

    private void getExternalRolesDetails( ApplicationContext applicationContext, ExternalPartyRole externalPartyRole,
            PartyRolesDetailVO partyRolesDetailVO, SortedMap<ExternalPartyRoleDetailVO, String> externalPartyRolesMap,
            IProductDevelopmentService typeService) {

        String agmtExtRefs = "";
        //use the externalObjectTypeId here

        //getSystemName from systemId
        ExternalPartyRoleDetailVO externalPartyRoleDetailVO = new ExternalPartyRoleDetailVO();

        externalPartyRoleDetailVO.setPartyRoleId(partyRolesDetailVO.getPartyRoleId());
        externalPartyRoleDetailVO.setExternalContextReference(externalPartyRole.getExternalContextReference());
        externalPartyRoleDetailVO.setPartyRole(partyRolesDetailVO.getPartyRole());
        externalPartyRoleDetailVO.setPartyRoleDescription(externalPartyRole.getDescription());

        if(externalPartyRole.getExternalObjectTypeId() != null) {
            Type externalType = typeService.getType(applicationContext, externalPartyRole.getExternalObjectTypeId());
            if(externalType != null) {
                externalPartyRoleDetailVO.setExternalTypeName(externalType.getName());
            }
        }

        if(externalPartyRole.getExternalSystemId() != null) {
            try {
                IEnumeration externalSystemId = typeService.getEnumeration(applicationContext, externalPartyRole.getExternalSystemId());

                if (externalSystemId != null) {
                    externalPartyRoleDetailVO.setExternalSystemName(externalSystemId.getName());
                }
            } catch(Exception e) {
                log.error(e.getMessage(), e);
                throw new PartyException("Corrupt Data: ExternalPartyRole external system ID cannot be retrieved with the following error: " + e.getMessage());
            }
        }


        externalPartyRolesMap.put(externalPartyRoleDetailVO, agmtExtRefs);
        
    }

    private void getClaimLinkedToPartyRole(IClaimManagementService claimManagementService,
            IProductDevelopmentService typeService, String roleTypeName,
            PartyRole partyRole, PartyRolesDetailVO partyRolesDetailVO,
            SortedMap<PartyRolesDetailVO, String> partyRoleClaimsMap,
            CoreTypeReference claimType)
            throws KindNotFoundException {

        ApplicationContext applicationContext = new ApplicationContext();
        String claimsExtRefs = "";
        partyRolesDetailVO.setRelationshipRole(false);

        if (partyRole.getContext() != null && partyRole.getContext().getObjectId() != null) {

            if (claimType.equals(CoreTypeReference.LOSSEVENT)) {
                LossEvent lossEvent = claimManagementService
                        .getLossEvent(applicationContext, partyRole.getContext());
                if (lossEvent != null) {
                    partyRolesDetailVO.setPartyRoleContext(ObjectReference.convertToString(partyRole.getContext()));
                    partyRolesDetailVO.setAgreementRole(roleTypeName);
                    Type type = typeService.getType(new ApplicationContext(), lossEvent.getTypeId());
                    partyRolesDetailVO.setAgreementKind(type.getName());
                    partyRolesDetailVO.setAgreementPart(type.getName());
                    partyRolesDetailVO.setAgreementNumber(lossEvent.getExternalReference());
                    partyRolesDetailVO.setAgreementPartObjectReference(
                        ObjectReference.convertToString(partyRole.getContext()));
                    partyRolesDetailVO.setPartyRoleObjectReference(ObjectReference.convertToString(partyRole
                        .getObjectReference()));
                } else {
                    emptyPartyRolesDetailVO(partyRolesDetailVO);
                }

            } else { IClaim claim = null;

                if (typeService.isSameOrSubType(applicationContext,
                    CoreTypeReference.CLAIMFOLDER.getValue(), partyRole.getContext().getTypeId())){
                    claim = claimManagementService.getClaimFolder(applicationContext, partyRole.getContext());
                } else {
                    claim = claimManagementService.getElementaryClaim(applicationContext, partyRole.getContext());
                }

                partyRolesDetailVO.setPartyRoleContext(ObjectReference.convertToString(partyRole.getContext()));
                partyRolesDetailVO.setAgreementRole(roleTypeName);
                Type type = typeService.getType(new ApplicationContext(), claim.getTypeId());
                partyRolesDetailVO.setAgreementKind(type.getName());
                partyRolesDetailVO.setAgreementPart(type.getName());
                partyRolesDetailVO.setAgreementNumber(claim.getExternalReference());
                partyRolesDetailVO.setAgreementPartObjectReference(ObjectReference.convertToString(partyRole.getContext()));
                partyRolesDetailVO.setPartyRoleObjectReference(ObjectReference.convertToString(partyRole
                        .getObjectReference()));
            }

            partyRoleClaimsMap.put(partyRolesDetailVO, claimsExtRefs);
        }
    }

    private void emptyPartyRolesDetailVO(PartyRolesDetailVO partyRolesDetailVO) {
        partyRolesDetailVO.setPartyRoleContext("");
        partyRolesDetailVO.setAgreementNumber("");
        partyRolesDetailVO.setAgreementKind("");
    }

    private KindDTO getEquivAgmtRoleKindForPartyRoleType(
            IProductDevelopmentService typeService, IPolicyAdminService fsaBusinessService, ApplicationContext applicationContext,
            Long partyRoleTypeId) {
        // look up the agreement kind based on the party role type
        try {
            Type type = typeService.getType(new ApplicationContext(), partyRoleTypeId);
            return fsaBusinessService.getKind(null, KindCategory.AGREEMENT, type.getName());
        } catch (ApplicationRuntimeException ex) {
            return null;
        }
    }

    public void setGuiDefaultsForRolesLinking(String roleKind, PartyForm form) {
        if (roleKind.equalsIgnoreCase(CoreTypeReference.BENEFICIARY.getName())) {
            form.setSelRoleType(CoreTypeReference.BENEFICIARY.getName());
        } else if (roleKind.equalsIgnoreCase(CoreTypeReference.CLAIMCLAIMANT.getName())) {
            form.setSelRoleType(CoreTypeReference.CLAIMCLAIMANT.getName());
        } else if (roleKind.equalsIgnoreCase(CoreTypeReference.INSURED.getName())) {
            form.setSelRoleType(CoreTypeReference.INSURED.getName());
        } else if (roleKind.equalsIgnoreCase(CoreTypeReference.LIFEINSUREDINCLAIM.getName())) {
            form.setSelRoleType(CoreTypeReference.LIFEINSUREDINCLAIM.getName());
        } else if (roleKind.equalsIgnoreCase(CoreTypeReference.POLICYHOLDER.getName())) {
            form.setSelRoleType(CoreTypeReference.POLICYHOLDER.getName());
        } else {
            form.setSelRoleType(PartyGuiHttpConstants.SELECT);
        }
    }

    public void partyRolesDisplay(HttpServletRequest request, IProductDevelopmentService typeService, ApplicationContext applicationContext, RolePlayer rolePlayer,
                                  PartyForm partyForm, ICustomerRelationshipService partyService, IPolicyAdminService fsaBusinessService,
                                  IClaimManagementService claimManagementService) throws Exception {
        SortedMap<PartyRolesDetailVO, String> partyRoleAgmtsMap = new TreeMap<>();
        SortedMap<PartyRolesDetailVO, String> partyRoleLossEventsMap = new TreeMap<>();
        SortedMap<PartyRolesDetailVO, String> partyRoleClaimsMap = new TreeMap<>();
        SortedMap<PartyRolesDetailVO, String> partyRoleRelationshipsMap = new TreeMap<>();
        SortedMap<PartyRolesDetailVO, String> partyRoleNonLinkingMap = new TreeMap<>();
        SortedMap<PartyRolePartyDetailVO, String> partyRolePartiesMap = new TreeMap<>();
        SortedMap<ExternalPartyRoleDetailVO, String> externalPartyRolesMap = new TreeMap<>();
        for (PartyRole partyRole : rolePlayer.getPartyRoles()) {
            PartyRolesDetailVO  partyRolesDetailVO = new PartyRolesDetailVO();
            partyRolesDetailVO.setRelationshipRole(false);
            partyRolesDetailVO.setPartyRoleId(partyRole.getId());
            if (partyRole.getEffectivePeriod().getStart() != null) {
                partyRolesDetailVO
                        .setPartyRoleEffectiveFrom(FormatUtil.getTypeFormatter(request).formatDate(partyRole.getEffectivePeriod().getEnd()));
            }
            if (partyRole.getEffectivePeriod().getEnd() != null) {
                partyRolesDetailVO.setPartyRoleEffectiveTo(FormatUtil.getTypeFormatter(request).formatDate(partyRole.getEffectivePeriod().getEnd()));
            }
            if (rolePlayer instanceof Person) {
                partyRolesDetailVO.setParty(partyForm.getFullName());
            } else if (rolePlayer instanceof Organisation) {
                partyRolesDetailVO.setParty(partyForm.getFullName());
            }
            if (partyRole.getTypeId() != null) {
                partyRolesDetailVO.setPartyRole(FormatUtil.getTypeFormatter(request).formatType(partyRole.getTypeId()));
            } else {
                throw new PartyException("Corrupt Data: PartyRole Type is Null. Contact Systems");
            }

            Set<Type> relationshipTypes = Collections.emptySet();
            if (rolePlayer instanceof Person) {
                Type parentType = typeService.getType(applicationContext, "Person To Role Player Relationship");
                if (parentType != null) {
                    relationshipTypes = typeService.getAllSubTypes(applicationContext, parentType.getId());
                }
            } else if (rolePlayer instanceof Organisation) {
                Type parentType = typeService.getType(applicationContext, "Organisation To Role Player Relationship");
                if (parentType != null) {
                    relationshipTypes = typeService.getAllSubTypes(applicationContext, parentType.getId());
                }
            }

            // build up the label value collection for display
            List<LabelValueBean> labelValueTypes = new ArrayList<>();
            for (Type type : relationshipTypes) {
                labelValueTypes.add(new LabelValueBean(type.getName(), type.getName())); // TODO localise
            }

            partyForm.setRelationshipTypes(labelValueTypes);

            for (Type relationshipType : relationshipTypes) {
                partyRolesDetailVO.setRelationshipRole(true);
                if (partyRole.getTypeId().equals(relationshipType.getId())) {
                    ObjectReference contextObjRef = partyRole.getContext();
                    populateContextDefaultName(applicationContext, partyService, partyRoleRelationshipsMap,
                                               partyRolesDetailVO,
                                               contextObjRef);
                    break;
                }
            }
            if (partyRole.getTypeId().equals(CoreTypeReference.SYSTEMUSER.getValue())) {
                // a System User role is defined to define the Party who is authorised to use the system through
                // this system User Role. The System User PartyRole is identified through its default UnstructuredName

                ObjectReference userObjectReference = partyRole.getContext();
                if (userObjectReference != null){
                    if (Components.PARTY == userObjectReference.getComponentId()){
                        populateContextDefaultName(applicationContext, partyService, partyRoleRelationshipsMap, partyRolesDetailVO, userObjectReference);
                    }
                } else {
                    partyRolesDetailVO.setPartyRoleContext("null"); //System User Context null
                    partyRoleRelationshipsMap.put(partyRolesDetailVO, "null");
                }
                PartyName systemUserName = partyService
                        .getDefaultPartyNameForRolePlayer(applicationContext, partyRole.getObjectReference());

                if (systemUserName != null) {
                    partyRolesDetailVO
                            .setParty( systemUserName.getFullName().trim());
                } else {
                    partyRolesDetailVO.setParty("No System User Name");
                }
            } else if (partyRole.getTypeId() != null) {
                Type type = typeService.getType(new ApplicationContext(), partyRole.getTypeId());
                ObjectReference contextPartyRoleObjRef = partyRole.getContext();

                if (contextPartyRoleObjRef == null || Components.SPFLITE.equals(contextPartyRoleObjRef.getComponentId())) {
                    if(partyRole instanceof ExternalPartyRole) {
                        ExternalPartyRole externalPartyRole = (ExternalPartyRole)partyRole;
                        getExternalRolesDetails(applicationContext, externalPartyRole, partyRolesDetailVO, externalPartyRolesMap, typeService);
                    } else {
                        getObjectsLinkedToPartyRole(fsaBusinessService, type.getName(), partyRole,
                                partyRolesDetailVO, partyRoleAgmtsMap);
                    }
                } else if (Components.CLAIM == contextPartyRoleObjRef.getComponentId()
                        && typeService.isSameOrSubType(applicationContext, CoreTypeReference.LOSSEVENT.getValue(), contextPartyRoleObjRef.getTypeId())) {
                    getClaimLinkedToPartyRole(claimManagementService, typeService, type.getName(),
                            partyRole, partyRolesDetailVO, partyRoleLossEventsMap, CoreTypeReference.LOSSEVENT);
                } else if (Components.CLAIM == contextPartyRoleObjRef.getComponentId()) {
                    getClaimLinkedToPartyRole(claimManagementService, typeService, type.getName(),
                            partyRole, partyRolesDetailVO, partyRoleClaimsMap, CoreTypeReference.CLAIM);
                } else if (Components.PARTY.equals(contextPartyRoleObjRef.getComponentId())) {
                    getPartyObjectsLinkedToPartyRole(applicationContext, partyService, partyRole, partyRolesDetailVO, partyRolePartiesMap, typeService);
                }
            }
        }

        partyForm.setPartyRoleAgmtsMap(partyRoleAgmtsMap);
        partyForm.setPartyRoleLossEventsMap(partyRoleLossEventsMap);
        partyForm.setPartyRoleClaimsMap(partyRoleClaimsMap);
        partyForm.setPartyRoleRelationshipsMap(partyRoleRelationshipsMap);
        partyForm.setPartyRoleNonLinkingMap(partyRoleNonLinkingMap);
        partyForm.setPartyRolePartiesMap(partyRolePartiesMap);
        partyForm.setExternalPartyRolesMap(externalPartyRolesMap);
    }

    private void populateContextDefaultName(ApplicationContext applicationContext,
            ICustomerRelationshipService partyService, SortedMap<PartyRolesDetailVO, String> partyRoleRelationshipsMap,
            PartyRolesDetailVO partyRolesDetailVO, ObjectReference contextObjRef) {
        Party linkedParty = partyService.getPartyForPartyOrPartyRole(applicationContext,
                                                                     contextObjRef, new String[]{"partyNames"});
        if (linkedParty instanceof Person) {
            PersonName persName = (PersonName) linkedParty.getDefaultName();
            String personFullName = persName.getFullName();
            partyRolesDetailVO.setPartyRoleContext(personFullName);
            partyRoleRelationshipsMap.put(partyRolesDetailVO, personFullName);
        } else if (linkedParty instanceof Organisation) {
            UnstructuredName orgName = (UnstructuredName) linkedParty.getDefaultName();
            String orgFullName = orgName.getFullName();
            partyRolesDetailVO.setPartyRoleContext(orgFullName);
            partyRoleRelationshipsMap.put(partyRolesDetailVO, orgFullName);
        }
    }

    public void endPartyRole(PartyForm partyForm, ActionMessages messages, ICustomerRelationshipService iPartyManager,
                             HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            Party party = partyForm.getParty();
            Set<PartyRole> partyRoles = partyForm.getPartyRolesForParty();
            Iterator<PartyRole> iterRoles = partyRoles.iterator();
            PartyRole foundPartyRole = null;
            while (iterRoles.hasNext()) {
                PartyRole role = iterRoles.next();
                if (partyForm.getRelationshipRoleSelected() == null) {
                    break;
                } else {
                    if (partyForm.getRelationshipRoleSelected().intValue() == role.getId().intValue()) {
                        foundPartyRole = role;
                        break;
                    }
                }
            }

            if (foundPartyRole != null) {
                foundPartyRole.setEffectivePeriod(DatePeriod.startingToday());
                ApplicationContext applicationContext = new ApplicationContext();
                iPartyManager.modifyPartyRole(applicationContext, foundPartyRole);
                Set<PartyRole> partyRolesForRolePlayer = iPartyManager.getPartyRolesForRolePlayer(applicationContext, party.getObjectReference());
                partyForm.setPartyRolesForParty(partyRolesForRolePlayer);
                partyForm.setPartyRolesSize(partyRolesForRolePlayer.size());
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.party.error", e.getMessage()));
        }
    }

    private Collection<Long> getNonLinkingRoles() {
        Collection<Long> invalidPartyRolesForCPs = new LinkedList<Long>();
        invalidPartyRolesForCPs.add(CoreTypeReference.RETIREE.getValue());
        invalidPartyRolesForCPs.add(CoreTypeReference.SYSTEMUSER.getValue());
        return invalidPartyRolesForCPs;
    }
}