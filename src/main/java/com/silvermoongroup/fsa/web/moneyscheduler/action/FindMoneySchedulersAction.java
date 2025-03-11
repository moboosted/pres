/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.moneyscheduler.action;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.moneyscheduler.form.FindMoneySchedulersForm;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import com.silvermoongroup.ftx.criteria.MoneySchedulerSearchCriteria;
import com.silvermoongroup.ftx.domain.intf.IMoneyScheduler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Controller for finding money schedulers.
 * 
 * @author Justin Walsh
 */
public class FindMoneySchedulersAction extends AbstractLookupDispatchAction {

    /**
     * GET: Default
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        FindMoneySchedulersForm form = (FindMoneySchedulersForm) actionForm;
        populateStaticPageElements(form, httpRequest);

        return actionMapping.getInputForward();
    }

    /**
     * POST: Invoked when the user click the find button
     */
    public ActionForward find(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) throws Exception {

        return ActionRedirectFactory.createRedirect(actionMapping.findForward("findAndDisplay"), actionForm);
    }

    /**
     * GET: Redirect after post
     */
    public ActionForward findAndDisplay(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        FindMoneySchedulersForm form = (FindMoneySchedulersForm) actionForm;
        populateStaticPageElements(form, httpServletRequest);

        MoneySchedulerSearchCriteria criteria = buildCriteria(form);
        List<IMoneyScheduler> moneySchedulers = getFinancialManagementService().findMoneySchedulers(
                getApplicationContext(), criteria);
        form.setResults(moneySchedulers);

        return actionMapping.getInputForward();
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm form, HttpServletRequest request) {
        populateStaticPageElements((FindMoneySchedulersForm) form, request);
    }

    private MoneySchedulerSearchCriteria buildCriteria(FindMoneySchedulersForm form) {

        MoneySchedulerSearchCriteria criteria = new MoneySchedulerSearchCriteria();

        criteria.setMoneySchedulerId(form.getMoneySchedulerId());
        criteria.setSchedulerTypeId(form.getSchedulerTypeId());
        criteria.setRestrictSchedulerType(form.getRestrictSchedulerType());
        String frequency = StringUtils.trimToNull(form.getFrequency());
        if (!GenericValidator.isBlankOrNull(frequency)) {
            EnumerationReference frequencyEnumRef = EnumerationReference.convertFromString(frequency);
            criteria.setFrequency(frequencyEnumRef);
        }
        if (form.getLastExecutedOption() != null) {
            if (form.getLastExecutedOption().equals("on")) {
                Date lastExecutedOnDate = getTypeParser().parseDate(form.getLastExecutedOnDate());
                criteria.setLastExecutedOnPeriod(new DatePeriod(lastExecutedOnDate, lastExecutedOnDate.plus(1)));
            } else if (form.getLastExecutedOption().equals("between")) {

                Date lastExecutedOnDateStart = getTypeParser().parseDate(form.getLastExecutedBetweenDateStart());
                Date lastExecutedOnDateEnd = getTypeParser().parseDate(form.getLastExecutedBetweenDateEnd());
                criteria.setLastExecutedOnPeriod(new DatePeriod(lastExecutedOnDateStart, lastExecutedOnDateEnd.plus(1)));
            }
        }

        if (form.getNextExecutionOption() != null) {
            if (form.getNextExecutionOption().equals("on")) {
                Date nextExecutionOnDate = getTypeParser().parseDate(form.getNextExecutionOnDate());
                criteria.setNextCycleStartPeriod(new DatePeriod(nextExecutionOnDate, nextExecutionOnDate.plus(1)));
            } else if (form.getNextExecutionOption().equals("between")) {

                Date nextExecutionOnDateStart = getTypeParser().parseDate(form.getNextExecutionBetweenDateStart());
                Date nextExecutionOnDateEnd = getTypeParser().parseDate(form.getNextExecutionBetweenDateEnd());
                criteria.setNextCycleStartPeriod(new DatePeriod(nextExecutionOnDateStart, nextExecutionOnDateEnd.plus(1)));
            }
        }

        return criteria;
    }

    public void populateStaticPageElements(FindMoneySchedulersForm form, HttpServletRequest httpRequest) {

        if (form.getLastExecutedOption() == null) {
            form.setLastExecutedOption("any");
        }
        if (form.getNextExecutionOption() == null) {
            form.setNextExecutionOption("any");
        }
    }
}
