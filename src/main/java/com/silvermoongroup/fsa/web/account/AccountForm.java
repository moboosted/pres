/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.account;

import com.silvermoongroup.common.domain.ObjectReference;
import org.apache.struts.action.ActionForm;

/**
 * @author Justin Walsh
 */
public class AccountForm extends ActionForm {

    private ObjectReference accountObjectReference;

    public ObjectReference getAccountObjectReference() {
        return accountObjectReference;
    }

    public void setAccountObjectReference(ObjectReference accountObjectReference) {
        this.accountObjectReference = accountObjectReference;
    }
}
