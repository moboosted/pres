/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.ftx;

import com.silvermoongroup.businessservice.financialmanagement.dto.UnitTransactionDTO;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import org.apache.struts.action.ActionForm;

/**
 * Unit transaction form
 */
public class UnitTransactionForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private UnitTransactionDTO unitTransaction;

    @RedirectParameter
    private ObjectReference unitTransactionObjectReference;

    public UnitTransactionDTO getUnitTransaction() {
        return unitTransaction;
    }

    public void setUnitTransaction(UnitTransactionDTO unitTransaction) {
        this.unitTransaction = unitTransaction;
    }

    public ObjectReference getUnitTransactionObjectReference() {
        return unitTransactionObjectReference;
    }

    public void setUnitTransactionObjectReference(ObjectReference unitTransactionObjectReference) {
        this.unitTransactionObjectReference = unitTransactionObjectReference;
    }
}
