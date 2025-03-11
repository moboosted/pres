/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.moneyscheduler.action;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.common.callback.CallBack;
import com.silvermoongroup.fsa.web.common.callback.CallBackUtility;
import com.silvermoongroup.fsa.web.moneyscheduler.form.MoneySchedulerForm;
import com.silvermoongroup.fsa.web.party.util.PartyNameUtil;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import com.silvermoongroup.ftx.criteria.MoneySchedulerExecutionSearchCriteria;
import com.silvermoongroup.ftx.domain.intf.IMoneyProvision;
import com.silvermoongroup.ftx.domain.intf.IMoneyScheduler;
import com.silvermoongroup.party.domain.PartyName;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The action which drives the display of the money provision
 */
public class MoneySchedulerAction extends AbstractLookupDispatchAction {

    public MoneySchedulerAction() {
    }

    /**
     * This is the entry method to view the current state and information of our moneyscheduler.
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpRequest,
            HttpServletResponse httpServletResponse) throws Exception {

        populateFormElements(actionForm, httpRequest);
        return actionMapping.getInputForward();
    }

    /**
     * Goes back to the previous page.
     */
    public ActionForward back(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpRequest,
            HttpServletResponse response) throws Exception {
        CallBack callBack = CallBackUtility.getCallBack(httpRequest, response);
        return CallBackUtility.getForwardAction(callBack);
    }

    private void populateFormElements(ActionForm actionForm, HttpServletRequest httpRequest) {
        MoneySchedulerForm form = (MoneySchedulerForm) actionForm;
        IMoneyScheduler ms = getFinancialManagementService().getMoneyScheduler(getApplicationContext(), form.getMoneySchedulerObjectReference());
        List<IMoneyProvision> moneyProvisions = getFinancialManagementService().findMoneyProvisionsForMoneyScheduler(getApplicationContext(), form.getMoneySchedulerObjectReference());
        form.setMoneySchedulerObjectReference(ms.getObjectReference());
        form.setMoneyProvisions(moneyProvisions);
        loadFormForMoneyScheduler(form, ms, httpRequest);
    }

    /*
     * This is an internal utility method to return all the moneyScheduler information for viewing and modifying.
     */
    private void loadFormForMoneyScheduler(MoneySchedulerForm form, IMoneyScheduler ms, HttpServletRequest httpRequest) {
        ApplicationContext appContext = new ApplicationContext();

        Date statusDate = appContext.getSystemDate().getDate();
        if (statusDate.isAfterOrEqual(ms.getEndDate())) {
            statusDate = ms.getEndDate().minus(1);
        } else if (statusDate.isBefore(ms.getStartDate())) {
            statusDate = ms.getStartDate();
        }

        com.silvermoongroup.ftx.domain.MoneySchedulerStatus moneySchedulerStatus = ms.getStatus(statusDate);
        if (moneySchedulerStatus != null) {
            form.setMoneySchedulerStatus(formatEnum(moneySchedulerStatus.getState()));
            form.setMoneySchedulerStatusEnd(formatDate(moneySchedulerStatus.getEffectivePeriod().getEnd()));
            form.setMoneySchedulerStatusStart(formatDate(moneySchedulerStatus.getEffectivePeriod().getStart()));
        } else {
            form.setMoneySchedulerStatus(null);
            form.setMoneySchedulerStatusEnd(null);
            form.setMoneySchedulerStatusStart(null);

            ActionMessages actionMessages = new ActionMessages();
            actionMessages.add(
                    ActionMessages.GLOBAL_MESSAGE,
                    new ActionMessage("errors.detail")
            );
            saveErrorMessages(httpRequest, actionMessages);
            return;
        }

        form.setType(formatType(ms.getObjectReference()));
        form.setAnniversaryDate(formatDate(ms.getAnniversaryDate()));
        form.setStartDate(formatDate(ms.getStartDate()));
        form.setEndDate(formatDate(ms.getEndDate()));

        IEnumeration frequency = getProductDevelopmentService().getEnumeration(appContext,
                ms.getFrequency());
        form.setFrequency(frequency.getName());

        IEnumeration meansOfPayment = getProductDevelopmentService().getEnumeration(appContext,
                ms.getPaymentMethod().getTheMethod()
        );
        form.setPaymentMethod(meansOfPayment.getEnumerationReference());

        form.setInternalCompanyCode(ms.getInternalCompanyCode());

        form.setAnniversaryFixingType(ms.getAnniversaryFixingType());
        form.setRollup(ms.isRollup());
        if (ms.getNextCycleStartDate() != null) {
            form.setNextCycleStartDate(formatDate(ms.getNextCycleStartDate()));
        }
        if (ms.getNextRunDate() != null) {
            form.setNextRunDate(formatDate(ms.getNextRunDate()));
        }
        if (ms.getSourceAccount() != null) {
            form.setSourceAccountName(getFinancialManagementService().getAccountName(
                    appContext, ms.getSourceAccount().getObjectReference()));
            form.setSourceAccountObjectReference(ms.getSourceAccount().getObjectReference());
        }

        if (ms.getTargetAccount() != null) {
            form.setTargetAccountName(getFinancialManagementService().getAccountName(
                    appContext, ms.getTargetAccount().getObjectReference()));
            form.setTargetAccountObjectReference(ms.getTargetAccount().getObjectReference());
        }

        if (ms.getPayer() != null) {
            PartyName partyName = getCustomerRelationshipService().getDefaultPartyNameForRolePlayer(appContext, ms.getPayer());
            form.setPayerName(PartyNameUtil.getPartyFullName(partyName, getProductDevelopmentService()));
        }

        if (ms.getReceiver() != null) {
            PartyName partyName = getCustomerRelationshipService().getDefaultPartyNameForRolePlayer(appContext, ms.getReceiver());
            form.setReceiverName(PartyNameUtil.getPartyFullName(partyName, getProductDevelopmentService()));
        }

        // recent money scheduler executions
        MoneySchedulerExecutionSearchCriteria criteria = new MoneySchedulerExecutionSearchCriteria();
        criteria.setMoneySchedulerObjectReference(ms.getObjectReference());
        criteria.setOrderBy(MoneySchedulerExecutionSearchCriteria.OrderBy.ENDED_AT_DESC);
        criteria.getQueryDetails().setMaximumRecordsRequested(5);
        form.setRecentExecutions(getFinancialManagementService().findMoneySchedulerExecutions(getApplicationContext(), criteria));
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm form, HttpServletRequest request) {
        populateFormElements(form, request);
    }

}
