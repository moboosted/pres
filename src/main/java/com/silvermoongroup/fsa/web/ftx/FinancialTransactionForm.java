/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.ftx;

import com.silvermoongroup.common.domain.ObjectReference;
import org.apache.struts.action.ActionForm;

/**
 * @author Justin Walsh
 */
public class FinancialTransactionForm extends ActionForm{

    private ObjectReference financialTransactionObjectReference;

    public ObjectReference getFinancialTransactionObjectReference() {
        return financialTransactionObjectReference;
    }

    public void setFinancialTransactionObjectReference(ObjectReference financialTransactionObjectReference) {
        this.financialTransactionObjectReference = financialTransactionObjectReference;
    }
}
