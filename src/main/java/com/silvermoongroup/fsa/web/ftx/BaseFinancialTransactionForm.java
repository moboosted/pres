/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.ftx;

import com.silvermoongroup.businessservice.financialmanagement.dto.FinancialTransactionDTO;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import org.apache.struts.action.ActionForm;

/**
 * Base class for Payment and PaymentDue forms
 *
 * @author Justin Walsh
 */
public abstract class BaseFinancialTransactionForm<T extends FinancialTransactionDTO> extends ActionForm {

    private static final long serialVersionUID = 1L;

    private T financialTransaction;

    @RedirectParameter
    private ObjectReference financialTransactionObjectReference;

    public T getFinancialTransaction() {
        return financialTransaction;
    }

    public void setFinancialTransaction(T financialTransaction) {
        this.financialTransaction = financialTransaction;
    }


    public ObjectReference getFinancialTransactionObjectReference() {
        return financialTransactionObjectReference;
    }

    public void setFinancialTransactionObjectReference(ObjectReference financialTransactionObjectReference) {
        this.financialTransactionObjectReference = financialTransactionObjectReference;
    }
}
