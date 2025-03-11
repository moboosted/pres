/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement.form;

import com.silvermoongroup.businessservice.claimmanagement.dto.RoleInClaim;
import com.silvermoongroup.common.domain.ObjectReference;
import org.apache.struts.action.ActionForm;

import java.util.List;

/**
 * Roles for a claim folder or elementary claim
 *
 * @author Justin Walsh
 */
public class RolesInClaimForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    /**
     * The object reference of the claim
     */
    private ObjectReference objectReference;

    private List<RoleInClaim> rolesInClaim;

    public RolesInClaimForm() {
    }

    public ObjectReference getObjectReference() {
        return objectReference;
    }

    public void setObjectReference(ObjectReference objectReference) {
        this.objectReference = objectReference;
    }

    public List<RoleInClaim> getRolesInClaim() {
        return rolesInClaim;
    }

    public void setRolesInClaim(List<RoleInClaim> rolesInClaim) {
        this.rolesInClaim = rolesInClaim;
    }
}
