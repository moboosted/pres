/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.account;

import com.silvermoongroup.account.domain.intf.IAssetHoldingAccount;
import com.silvermoongroup.businessservice.financialmanagement.dto.AccountEntryDTO;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;

import java.util.List;

/**
 * @author Justin Walsh
 */
public class AssetHoldingAccountForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private ObjectReference accountObjectReference;

    private IAssetHoldingAccount account;
    
    private List<AccountEntryDTO> accountEntries;

    public AssetHoldingAccountForm() {
    }

    public IAssetHoldingAccount getAccount() {
        return account;
    }

    public void setAccount(IAssetHoldingAccount account) {
        this.account = account;
    }

    public ObjectReference getAccountObjectReference() {
        return accountObjectReference;
    }

    public void setAccountObjectReference(ObjectReference accountObjectReference) {
        this.accountObjectReference = accountObjectReference;
    }

    public List<AccountEntryDTO> getAccountEntries() {
        return accountEntries;
    }

    public void setAccountEntries(List<AccountEntryDTO> accountEntries) {
        this.accountEntries = accountEntries;
    }
}
