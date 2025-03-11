/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.account;

import com.silvermoongroup.account.domain.intf.IAccountEntry;
import com.silvermoongroup.businessservice.financialmanagement.dto.AccountEntryDTO;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import com.silvermoongroup.ftx.domain.intf.IFinancialTransaction;
import com.silvermoongroup.ftx.domain.intf.IUnitTransaction;

/**
 * @author Justin Walsh
 */
public class AccountEntryForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private ObjectReference accountEntryObjectReference;

    private IAccountEntry accountEntry;
    private AccountEntryDTO accountEntryDTO;
    private IFinancialTransaction financialTransaction;
    private IUnitTransaction unitTransaction;
    private String description;

    public IAccountEntry getAccountEntry() {
        return accountEntry;
    }

    public void setAccountEntry(IAccountEntry accountEntry) {
        this.accountEntry = accountEntry;
    }

    public ObjectReference getAccountEntryObjectReference() {
        return accountEntryObjectReference;
    }

    public void setAccountEntryObjectReference(ObjectReference accountEntryObjectReference) {
        this.accountEntryObjectReference = accountEntryObjectReference;
    }

    public void setFinancialTransaction(IFinancialTransaction financialTransaction) {
        this.financialTransaction = financialTransaction;
    }

    public IFinancialTransaction getFinancialTransaction() {
        return financialTransaction;
    }

    public void setUnitTransaction(IUnitTransaction unitTransaction) {
        this.unitTransaction = unitTransaction;
    }

    public IUnitTransaction getUnitTransaction() {
        return unitTransaction;
    }

    public AccountEntryDTO getAccountEntryDTO() {
        return accountEntryDTO;
    }

    public void setAccountEntryDTO(AccountEntryDTO accountEntryDTO) {
        this.accountEntryDTO = accountEntryDTO;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
