/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.moneyscheduler.action;

import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.moneyscheduler.form.FindMoneySchedulersToExecuteForm;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import com.silvermoongroup.ftx.criteria.MoneySchedulersToExecuteSearchCriteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller component for finding and displaying the money schedulers to execute.
 *
 * @author Justin Walsh
 */
public class FindMoneySchedulersToExecuteAction extends AbstractLookupDispatchAction {

    /**
     * GET: Page is rendered for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        FindMoneySchedulersToExecuteForm form = (FindMoneySchedulersToExecuteForm) actionForm;
        return actionMapping.getInputForward();
    }

    /**
     * POST: The user clicks the find button
     */
    public ActionForward find(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
        return ActionRedirectFactory.createRedirect(actionMapping.findForward("findAndDisplay"), actionForm);
    }

    /**
     * GET: Perform the search and render the search results
     */
    public ActionForward findAndDisplay(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        FindMoneySchedulersToExecuteForm form = (FindMoneySchedulersToExecuteForm) actionForm;

        MoneySchedulersToExecuteSearchCriteria criteria = new MoneySchedulersToExecuteSearchCriteria();
        criteria.setNextRunDate(parseDate(form.getNextRunDate()));
        criteria.setMoneySchedulerTypeId(form.getMoneySchedulerTypeId());
        criteria.setRestrictMoneySchedulerType(form.isRestrictMoneySchedulerType());

        form.setResults(getBillingAndCollectionService().findMoneySchedulersToExecute(getApplicationContext(), criteria));

        return actionMapping.getInputForward();
    }
}
