/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.account;

import com.silvermoongroup.account.domain.intf.IAccount;
import com.silvermoongroup.account.domain.intf.IAssetHoldingAccount;
import com.silvermoongroup.account.domain.intf.IMonetaryAccount;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import org.apache.commons.lang3.Validate;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller class which delegates (re-directs) to either the {@link MonetaryAccountAction} or
 *
 * @author Justin Walsh
 */
public class AccountAction extends AbstractLookupDispatchAction {

    /**
     * GET: Default action
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        AccountForm form = (AccountForm)actionForm;
        Assert.notNull(form.getAccountObjectReference(), "The accountObjectReference is required");
        IAccount account = getFinancialManagementService().getAccount(getApplicationContext(), form.getAccountObjectReference());

        ActionRedirect redirect;
        if (account instanceof IMonetaryAccount) {
            redirect = new ActionRedirect(actionMapping.findForward("monetaryAccount"));
        }
        else if (account instanceof IAssetHoldingAccount) {
            redirect = new ActionRedirect(actionMapping.findForward("assetHoldingAccount"));
        }
        else {
            throw new IllegalStateException("Expecting a MonetaryAccount of AssetHoldingAccount, but got: " + account);
        }

        redirect.addParameter("accountObjectReference", form.getAccountObjectReference());
        return redirect;

    }
}
