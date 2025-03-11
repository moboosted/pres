/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.account;

import com.silvermoongroup.account.domain.MonetaryAccountBalance;
import com.silvermoongroup.account.domain.intf.IAccountBalance;
import com.silvermoongroup.account.domain.intf.IMonetaryAccount;
import com.silvermoongroup.businessservice.financialmanagement.dto.AccountEntryDTO;
import com.silvermoongroup.common.datatype.CurrencyAmount;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Justin Walsh
 */
public class MonetaryAccountForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private ObjectReference accountObjectReference;

    private IMonetaryAccount account;
    private MonetaryAccountBalance currentAccountBalance;
    private List<AccountEntryDTO> accountEntries;
    private List<IAccountBalance<CurrencyAmount>> accountAgeing = new ArrayList<>();

    public MonetaryAccountForm() {
    }

    public IMonetaryAccount getAccount() {
        return account;
    }

    public void setAccount(IMonetaryAccount account) {
        this.account = account;
    }

    public ObjectReference getAccountObjectReference() {
        return accountObjectReference;
    }

    public void setAccountObjectReference(ObjectReference accountObjectReference) {
        this.accountObjectReference = accountObjectReference;
    }

    public MonetaryAccountBalance getCurrentAccountBalance() {
        return currentAccountBalance;
    }

    public void setCurrentAccountBalance(MonetaryAccountBalance currentAccountBalance) {
        this.currentAccountBalance = currentAccountBalance;
    }

    public List<IAccountBalance<CurrencyAmount>> getAccountAgeing() {
        return accountAgeing;
    }

    public void setAccountAgeing(List<IAccountBalance<CurrencyAmount>> accountAgeing) {
        this.accountAgeing = accountAgeing;
    }

    public List<AccountEntryDTO> getAccountEntries() {
        return accountEntries;
    }

    public void setAccountEntries(List<AccountEntryDTO> accountEntries) {
        this.accountEntries = accountEntries;
    }
}
