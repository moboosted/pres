/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.account;

import com.silvermoongroup.account.criteria.AccountEntrySearchCriteria;
import com.silvermoongroup.account.domain.intf.IAssetHoldingAccount;
import com.silvermoongroup.businessservice.financialmanagement.dto.AccountEntryDTO;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import org.apache.commons.lang3.Validate;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * @author Justin Walsh
 */
public class AssetHoldingAccountAction extends AbstractLookupDispatchAction {

    /**
     * GET: Default display
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        AssetHoldingAccountForm form = (AssetHoldingAccountForm) actionForm;
        ObjectReference accountObjectReference = form.getAccountObjectReference();
        Assert.notNull(accountObjectReference);

        IAssetHoldingAccount account = getFinancialManagementService().getAssetHoldingAccount(getApplicationContext(), accountObjectReference);
        form.setAccount(account);

        AccountEntrySearchCriteria criteria = new AccountEntrySearchCriteria();
        criteria.setAccountObjectReference(account.getObjectReference());
        criteria.setOrderBy(AccountEntrySearchCriteria.OrderBy.POSTED_DATE_DESC);
        criteria.getQueryDetails().setMaximumRecordsRequested(10);

        List<AccountEntryDTO> accountEntries = getFinancialManagementService().findAccountEntries(
                getApplicationContext(), criteria);

        // now reverse the list, since we want them in order of posted date
        Collections.reverse(accountEntries);
        form.setAccountEntries(accountEntries);

        return actionMapping.getInputForward();
    }
}
