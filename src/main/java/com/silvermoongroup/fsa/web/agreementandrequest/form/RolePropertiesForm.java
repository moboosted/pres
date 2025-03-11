/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.form;

import com.silvermoongroup.businessservice.policymanagement.dto.RoleDTO;

/**
 * @author mubeen
 */
public class RolePropertiesForm extends AbstractViewForm<RoleDTO> {

    private static final long serialVersionUID = 1L;
    
    // if the context object reference is a request, does the path refer to the tla associated 
    // with the top level agreement (true) or the request itself (false)
    private boolean tlaRelativePath;

    private String contextPath;

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
    
    public boolean isTlaRelativePath() {
        return tlaRelativePath;
    }
    
    public void setTlaRelativePath(boolean tlaRelativePath) {
        this.tlaRelativePath = tlaRelativePath;
    }
}