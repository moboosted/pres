/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.account;

import com.silvermoongroup.account.criteria.AccountEntrySearchCriteria;
import com.silvermoongroup.account.domain.MonetaryAccountBalance;
import com.silvermoongroup.account.domain.intf.IAccountBalance;
import com.silvermoongroup.account.domain.intf.IMonetaryAccount;
import com.silvermoongroup.businessservice.financialmanagement.dto.AccountEntryDTO;
import com.silvermoongroup.common.datatype.CurrencyAmount;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.account.util.AccountEnquiryAgeing;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.common.callback.CallBack;
import com.silvermoongroup.fsa.web.common.callback.CallBackUtility;
import org.apache.commons.lang3.Validate;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Justin Walsh
 */
public class MonetaryAccountAction extends AbstractLookupDispatchAction {

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("button.backtoagreement", "backToAgreement");
        return map;
    }

    /**
     * GET: Default display
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        MonetaryAccountForm form = (MonetaryAccountForm) actionForm;
        ObjectReference accountObjectReference = form.getAccountObjectReference();
        Assert.notNull(accountObjectReference);

        IMonetaryAccount account = getFinancialManagementService().getMonetaryAccount(getApplicationContext(), accountObjectReference);
        form.setAccount(account);

        MonetaryAccountBalance balance =
                getFinancialManagementService().getMonetaryAccountBalanceOnPostedDate(getApplicationContext(), accountObjectReference);
        form.setCurrentAccountBalance(balance);

        AccountEntrySearchCriteria criteria = new AccountEntrySearchCriteria();
        criteria.setAccountObjectReference(account.getObjectReference());
        criteria.setOrderBy(AccountEntrySearchCriteria.OrderBy.POSTED_DATE_DESC);
        criteria.getQueryDetails().setMaximumRecordsRequested(10);

        List<AccountEntryDTO> accountEntries = getFinancialManagementService().findAccountEntries(
                getApplicationContext(), criteria);

        // now reverse the list, since we want them in order of posted date
        Collections.reverse(accountEntries);
        form.setAccountEntries(accountEntries);

        // account ageing
        //
        AccountEnquiryAgeing accountEnquiryAging = new AccountEnquiryAgeing(getFinancialManagementService());
        List<IAccountBalance<CurrencyAmount>> accountAgingList = accountEnquiryAging.accountAgingList(
                getApplicationContext(),
                accountObjectReference,
                null);

        List<IAccountBalance<CurrencyAmount>> accountAgeing = new ArrayList<>();
        for (IAccountBalance<CurrencyAmount> element : accountAgingList) {
            IAccountBalance<CurrencyAmount> accountBalance = new MonetaryAccountBalance(element.getAmount(), element.getDebitCreditIndicator());
            accountAgeing.add(accountBalance);
        }
        Collections.reverse(accountAgeing);
        form.setAccountAgeing(accountAgeing);

        return actionMapping.getInputForward();
    }

    /**
     * Back to the agreement GUI
     */
    public ActionForward back(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        CallBack callBack = CallBackUtility.getCallBack(httpServletRequest, httpServletResponse);
        return CallBackUtility.getForwardAction(callBack);
    }
}
