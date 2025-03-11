/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement.action;

import com.silvermoongroup.businessservice.claimmanagement.dto.RoleInLossEventDTO;
import com.silvermoongroup.businessservice.customerrelationshipmanagement.service.intf.ICustomerRelationshipService;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.claim.domain.RoleInClaim;
import com.silvermoongroup.claim.domain.RoleInLossEvent;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.party.util.PartyNameUtil;
import com.silvermoongroup.party.domain.PartyName;
import com.silvermoongroup.party.domain.PartyRole;
import com.silvermoongroup.party.domain.RolePlayer;

import java.util.List;
import java.util.Set;

/**
 * Created by damir on 3/14/2017.
 */
public class ClaimFunctionalityUtil {

    public RoleInLossEventDTO findRoleOfType(List<RoleInLossEventDTO> roleInLossEvents, Long typeId) {
        for (RoleInLossEventDTO roleInLossEvent : roleInLossEvents) {
            if (roleInLossEvent.getTypeId() == typeId) {
                return roleInLossEvent;
            }
        }
        return null;
        //throw new IllegalStateException("RoleInLossEventDTO of type " + typeId + " not found");
    }

    public String getPartyNameForRoleInLossEvent(ApplicationContext applicationContext,
            ICustomerRelationshipService customerRelationshipService,
            IProductDevelopmentService productDevelopmentService, RoleInLossEvent roleInLossEvent ) {

        RolePlayer rolePlayer = getRolePlayerFromRoleInLossEvent(applicationContext, customerRelationshipService, roleInLossEvent);

        if (null == rolePlayer) return "";

        PartyName name = customerRelationshipService.
            getDefaultPartyNameForRolePlayer(applicationContext, rolePlayer.getObjectReference());

        String externalReference = customerRelationshipService.
            findPartyExternalReferenceByObjectReference(applicationContext, rolePlayer.getObjectReference());

        return PartyNameUtil.getPartyFullName(name, productDevelopmentService) + " (Ref: " + externalReference + ")";
    }

    private RolePlayer getRolePlayerFromRoleInLossEvent(ApplicationContext applicationContext,
            ICustomerRelationshipService customerRelationshipService, RoleInLossEvent roleInLossEvent) {
        if (null == roleInLossEvent.getRolePlayer().getObjectId()) return null;
        return customerRelationshipService.getPartyRole(applicationContext, roleInLossEvent.getRolePlayer()).getRolePlayer();
    }

    public String getPartyNameForRoleInClaim(ApplicationContext applicationContext,
            ICustomerRelationshipService customerRelationshipService,
            IProductDevelopmentService productDevelopmentService, RoleInClaim roleInClaim ) {

        RolePlayer rolePlayer = getRolePlayerFromRoleInClaim(applicationContext, customerRelationshipService, roleInClaim);

        PartyName name = customerRelationshipService.
            getDefaultPartyNameForRolePlayer(applicationContext, rolePlayer.getObjectReference());

        String externalReference = customerRelationshipService.
            findPartyExternalReferenceByObjectReference(applicationContext, rolePlayer.getObjectReference());

        return PartyNameUtil.getPartyFullName(name, productDevelopmentService) + " (Ref: " + externalReference + ")";
    }

    private RolePlayer getRolePlayerFromRoleInClaim(ApplicationContext applicationContext,
            ICustomerRelationshipService customerRelationshipService, RoleInClaim roleInClaim) {
        return customerRelationshipService.getPartyRole(applicationContext, roleInClaim.getRolePlayer()).getRolePlayer();
    }


    public PartyRole updatePartyRole(ApplicationContext applicationContext,
            ICustomerRelationshipService customerRelationshipService,
            ObjectReference context,
            ObjectReference selectedPerson, CoreTypeReference partyRoleType) {

        PartyRole foundPartyRole = null;
        Set<PartyRole> partyRoles = customerRelationshipService
                .getPartyRolesForRolePlayer(applicationContext, selectedPerson);
        for (PartyRole partyRole : partyRoles) {
            if (context.equals(partyRole.getContext()))
                if (partyRole.getTypeId() == partyRoleType.getValue()) {
                    foundPartyRole = partyRole;
                    break;
                }
        }

        if (null == foundPartyRole) {
            //if it doesn't exist
            PartyRole lossEventPartyRole = new PartyRole();
            lossEventPartyRole.setType(partyRoleType);
            lossEventPartyRole.setContext(context);
            return customerRelationshipService.establishPartyRole(applicationContext, selectedPerson, lossEventPartyRole);
        }
        else return foundPartyRole;
    }

}
