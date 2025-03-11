/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.moneyscheduler.execution;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.ftx.domain.MoneySchedulerExecution;
import com.silvermoongroup.ftx.domain.intf.IFinancialTransaction;
import org.apache.commons.lang3.Validate;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * Controller class for the detail of a money scheduler execution
 *
 * @author Justin Walsh
 */
public class MoneySchedulerExecutionAction extends AbstractLookupDispatchAction {

    public MoneySchedulerExecutionAction() {
    }

    /**
     * GET: Default
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        MoneySchedulerExecutionForm form = (MoneySchedulerExecutionForm) actionForm;
        ObjectReference mseObjectReference = form.getMoneySchedulerExecutionObjectReference();
        Assert.notNull(mseObjectReference, "The moneySchedulerExecutionObjectReference is required");
        MoneySchedulerExecution mse =
                getFinancialManagementService().getMoneySchedulerExecution(getApplicationContext(), mseObjectReference);
        form.setMoneySchedulerExecution(mse);

        Collection<IFinancialTransaction> transactions =
                getFinancialManagementService().findFinancialTransactionsGeneratedByMoneySchedulerExecution(getApplicationContext(), mseObjectReference);
        form.setFinancialTransactions(transactions);


        return actionMapping.getInputForward();
    }
}
