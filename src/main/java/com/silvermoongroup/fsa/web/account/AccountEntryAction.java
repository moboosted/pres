/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.account;

import com.silvermoongroup.account.domain.intf.IAccountEntry;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.ftx.domain.intf.IFinancialTransaction;
import com.silvermoongroup.ftx.domain.intf.IUnitTransaction;
import org.apache.commons.lang3.Validate;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Justin Walsh
 */
public class AccountEntryAction extends AbstractLookupDispatchAction {


    /**
     * GET: Default display
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        AccountEntryForm form = (AccountEntryForm)actionForm;
        ObjectReference accountEntryObjectReference = form.getAccountEntryObjectReference();
        Assert.notNull(accountEntryObjectReference);

        IAccountEntry accountEntry = getFinancialManagementService().getAccountEntry(getApplicationContext(), accountEntryObjectReference);
        form.setAccountEntry(accountEntry);

        String description = accountEntry.getDescription();
        form.setDescription(description);

        // is the account entry linked to a financial transaction or unit transaction?
        //
        IFinancialTransaction ftx = getFinancialManagementService().findFinancialTransactionForAccountEntry(
                getApplicationContext(), accountEntry.getObjectReference());
        if (ftx != null) {
            form.setFinancialTransaction(ftx);
        } else {
            IUnitTransaction utx = getFinancialManagementService().findUnitTransactionForAccountEntry(
                    getApplicationContext(), accountEntry.getObjectReference());
            form.setUnitTransaction(utx);
        }

        return actionMapping.getInputForward();
    }
}
