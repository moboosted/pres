/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.account.accountmapping;

import com.silvermoongroup.account.domain.intf.IAccountMapping;
import com.silvermoongroup.common.domain.ObjectReference;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Form bean for the addition of an {@link IAccountMapping} object.
 * 
 * @author Justin Walsh
 */
public class UpdateAccountMappingForm extends AbstractAccountMappingForm {

    private static final long serialVersionUID = 1L;
    
    public UpdateAccountMappingForm() {
    }
    
    private ObjectReference accountMappingObjectReference;
    
    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return "update".equals(actionName);
    }
    
    public ObjectReference getAccountMappingObjectReference() {
        return accountMappingObjectReference;
    }
    
    public void setAccountMappingObjectReference(ObjectReference accountMappingObjectReference) {
        this.accountMappingObjectReference = accountMappingObjectReference;
    }
}
