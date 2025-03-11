package com.silvermoongroup.fsa.web.dto;

import com.silvermoongroup.common.domain.ObjectReference;

/**
 * This is required to store the information about the RoleInActual Object that we are currently modifying before moving
 * off to the Role Properties Screen
 * 
 * @author mubeen
 */
public class RoleActionHelper extends ActionHelper {

    private static final long serialVersionUID = 1L;

    private String contextPath; // We need to know we are required to return to
    private ObjectReference rolePlayerObjectReference;

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public ObjectReference getRolePlayerObjectReference() {
        return rolePlayerObjectReference;
    }

    public void setRolePlayerObjectReference(ObjectReference rolePlayerObjectReference) {
        this.rolePlayerObjectReference = rolePlayerObjectReference;
    }
}