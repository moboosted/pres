/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.moneyscheduler.action;

import com.silvermoongroup.businessservice.financialmanagement.dto.SchedulerExecutionMessage;
import com.silvermoongroup.businessservice.financialmanagement.dto.SchedulerExecutionMessage.MessageType;
import com.silvermoongroup.businessservice.financialmanagement.dto.SchedulerExecutionResult;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.moneyscheduler.form.ExecuteMoneySchedulerForm;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Justin Walsh
 */
public class ExecuteMoneySchedulerAction extends AbstractLookupDispatchAction {

    public ExecuteMoneySchedulerAction() {
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.executemoneyscheduler.action.execute", "executeScheduler");
        return map;
    }

    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        ExecuteMoneySchedulerForm form = (ExecuteMoneySchedulerForm) actionForm;
        populateStaticPageElements(form, httpRequest);

        return actionMapping.getInputForward();
    }

    public ActionForward executeScheduler(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        ExecuteMoneySchedulerForm form = (ExecuteMoneySchedulerForm) actionForm;
        Date executionDate = parseDate(form.getExecutionDate());

        SchedulerExecutionResult result;
        if ("agreement".equals(form.getExecutionOption())) {
            result = getBillingAndCollectionService().executeScheduler(getApplicationContext(), form.getSchedulerTypeId(),
                    executionDate, form.getAgreementNumber(), form.getAgreementKindId());
        } else if ("objectreference".equals(form.getExecutionOption())) {
            result = getBillingAndCollectionService().executeScheduler(
                    getApplicationContext(),
                    form.getMoneySchedulerObjectReference(),
                    executionDate);
        } else {
            throw new IllegalStateException("Unknown option: " + form.getExecutionOption());
        }

        List<SchedulerExecutionMessage> messages = result.getMessages();

        ActionMessages errors = new ActionMessages();
        ActionMessages info = new ActionMessages();
        for (SchedulerExecutionMessage message : messages) {

            ActionMessage formattedMessage = new ActionMessage(message.getMessage(), formatObjects(message.getParameters()));
            if (message.getMessageType().equals(MessageType.ERROR)) {
                errors.add(ActionMessages.GLOBAL_MESSAGE, formattedMessage);
            } else {
                info.add(ActionMessages.GLOBAL_MESSAGE, formattedMessage);
            }
        }

        saveErrorMessages(httpRequest, errors);
        saveInformationMessages(httpRequest, info);

        // if we have a money scheduler execution - forward to that page
        ObjectReference moneySchedulerExecutionObjectReference = result.getMoneySchedulerExecutionObjectReference();
        if (moneySchedulerExecutionObjectReference != null) {
            ActionRedirect redirect = new ActionRedirect(actionMapping.findForward("moneySchedulerExecution"));
            redirect.addParameter("moneySchedulerExecutionObjectReference", moneySchedulerExecutionObjectReference);
            return redirect;
        }

        populateStaticPageElements(form, httpRequest);

        return actionMapping.getInputForward();
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm actionForm, HttpServletRequest request) throws Exception {

        ExecuteMoneySchedulerForm form = (ExecuteMoneySchedulerForm) actionForm;
        populateStaticPageElements(form, request);
    }

    public void populateStaticPageElements(ExecuteMoneySchedulerForm form, HttpServletRequest httpRequest) {

        if (form.getExecutionOption() == null) {
            if (form.getMoneySchedulerObjectReference() != null) {
                form.setExecutionOption("objectreference");
            } else {
                form.setExecutionOption("agreement");
            }
        }
    }
}
