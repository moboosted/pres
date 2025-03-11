/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.moneyscheduler.execution;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.datatype.DateTimePeriod;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import com.silvermoongroup.ftx.criteria.MoneySchedulerExecutionSearchCriteria;
import com.silvermoongroup.ftx.domain.MoneySchedulerExecution;
import com.silvermoongroup.ftx.domain.enumeration.MoneySchedulerExecutionReturnCode;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Controller class to find money scheduler executions
 *
 * @author Justin Walsh
 */
public class FindMoneySchedulerExecutionsAction extends AbstractLookupDispatchAction {

    public FindMoneySchedulerExecutionsAction() {
    }

    /**
     * GET: Default
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        FindMoneySchedulerExecutionsForm form = (FindMoneySchedulerExecutionsForm) actionForm;
        populateStaticPageElements(form, httpRequest);

        return actionMapping.getInputForward();
    }

    /**
     * POST: Invoked when the user click the find button
     */
    public ActionForward find(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        return ActionRedirectFactory.createRedirect(actionMapping.findForward("findAndDisplay"), actionForm);
    }

    /**
     * GET: When a money scheduler execution is selected
     */
    public ActionForward select(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        FindMoneySchedulerExecutionsForm form = (FindMoneySchedulerExecutionsForm) actionForm;
        ActionRedirect redirect = new ActionRedirect(actionMapping.findForward("moneyScheduler"));
        redirect.addParameter("moneySchedulerExecutionObjectReference", form.getSelectedMoneySchedulerExecution());
        return redirect;
    }

    /**
     * GET: Redirect after post
     */
    public ActionForward findAndDisplay(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        FindMoneySchedulerExecutionsForm form = (FindMoneySchedulerExecutionsForm) actionForm;
        populateStaticPageElements(form, httpServletRequest);

        MoneySchedulerExecutionSearchCriteria criteria = buildCriteria(form);
        List<MoneySchedulerExecution> executions = getFinancialManagementService().findMoneySchedulerExecutions(
                getApplicationContext(), criteria);
        form.setResults(executions);

        return actionMapping.getInputForward();
    }

    private MoneySchedulerExecutionSearchCriteria buildCriteria(FindMoneySchedulerExecutionsForm form) {

        MoneySchedulerExecutionSearchCriteria criteria = new MoneySchedulerExecutionSearchCriteria();
        criteria.setMoneySchedulerExecutionId(form.getMoneySchedulerExecutionId());

        String moneySchedulerOption = form.getMoneySchedulerOption();
        if (moneySchedulerOption != null) {
            if (moneySchedulerOption.equals("type")) {
                criteria.setMoneySchedulerType(form.getMoneySchedulerTypeId());
                criteria.setRestrictMoneySchedulerType(form.isRestrictMoneySchedulerType());
            } else if (moneySchedulerOption.equals("specific")) {
                criteria.setMoneySchedulerObjectReference(form.getMoneySchedulerObjectReference());
            }
        }

        String requestedExecutionDateOption = form.getRequestedExecutionDateOption();
        if (requestedExecutionDateOption != null) {
            if (requestedExecutionDateOption.equals("on")) {
                Date requestedExecutionOn = getTypeParser().parseDate(form.getRequestedExecutionOnDate());
                criteria.setRequestedExecutionPeriod(new DatePeriod(requestedExecutionOn, requestedExecutionOn.plus(1)));
            } else if (requestedExecutionDateOption.equals("between")) {
                Date requestedExecutionDateStart = getTypeParser().parseDate(form.getRequestedExecutionBetweenDateStart());
                Date requestedExecutionDateEnd = getTypeParser().parseDate(form.getRequestedExecutionBetweenDateEnd());
                criteria.setRequestedExecutionPeriod(new DatePeriod(requestedExecutionDateStart, requestedExecutionDateEnd.plus(1)));
            }
        }

        if (form.getExecutionStartedDateOption() != null) {
            if (form.getExecutionStartedDateOption().equals("on")) {
                Date executionStartedOn = getTypeParser().parseDate(form.getExecutionStartedOnDate());
                criteria.setStartAtPeriod(new DateTimePeriod(new DateTime(executionStartedOn), new DateTime(executionStartedOn.plus(1))));
            } else if (form.getExecutionStartedDateOption().equals("between")) {
                Date executionStartedDateStart = getTypeParser().parseDate(form.getExecutionStartedBetweenDateStart());
                Date executionStartedDateEnd = getTypeParser().parseDate(form.getExecutionStartedBetweenDateEnd());
                criteria.setStartAtPeriod(new DateTimePeriod(new DateTime(executionStartedDateStart), new DateTime(executionStartedDateEnd.plus(1))));
            }
        }

        Integer[] returnCodes = form.getReturnCodes();
        for (Integer returnCode : returnCodes) {
            MoneySchedulerExecutionReturnCode returnCodeValue = MoneySchedulerExecutionReturnCode.fromCode(returnCode
                    .longValue());
            if (returnCodeValue != null) {
                criteria.addReturnCode(returnCodeValue);
            }
        }

        criteria.setOrderBy(MoneySchedulerExecutionSearchCriteria.OrderBy.STARTED_AT_ASC);

        return criteria;
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm form, HttpServletRequest request) {
        populateStaticPageElements((FindMoneySchedulerExecutionsForm) form, request);
    }

    private void populateStaticPageElements(FindMoneySchedulerExecutionsForm form, HttpServletRequest httpRequest) {

        form.setTableId("moneySchedulerExecutionsTable");
        form.setRowsPerPage(25);
        form.setStartRecord(getStartRecordForTable(httpRequest, form.getTableId(), form.getRowsPerPage()));

        if (form.getExecutionStartedDateOption() == null) {
            form.setExecutionStartedDateOption("any");
        }
        if (form.getRequestedExecutionDateOption() == null) {
            form.setRequestedExecutionDateOption("any");
        }
        if (form.getMoneySchedulerOption() == null) {
            if (form.getMoneySchedulerObjectReference() != null) {
                form.setMoneySchedulerOption("specific");
            } else {
                form.setMoneySchedulerOption("any");
            }
        }
    }

    /**
     * Return the (zero based) index of the first record displayed in the given table.
     */
    private int getStartRecordForTable(HttpServletRequest httpServletRequest, String tableId, int rowsPerPage) {
        int startRecord = 0;
        String pageParam = httpServletRequest.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
        if (pageParam != null) {
            startRecord = (Integer.parseInt(pageParam) - 1) * rowsPerPage;
        }
        return startRecord;
    }
}
