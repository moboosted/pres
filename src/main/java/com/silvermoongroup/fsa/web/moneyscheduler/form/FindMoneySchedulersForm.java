/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.moneyscheduler.form;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import com.silvermoongroup.ftx.domain.intf.IMoneyScheduler;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Model for the 'find money schedulers' functionality.
 *
 * @author Justin Walsh
 */
public class FindMoneySchedulersForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private Long moneySchedulerId;

    @RedirectParameter
    private Long schedulerTypeId;

    @RedirectParameter
    private boolean restrictSchedulerType;

    @RedirectParameter
    private String frequency;

    @RedirectParameter
    private String lastExecutedOption;

    @RedirectParameter
    private String lastExecutedOnDate;

    @RedirectParameter
    private String lastExecutedBetweenDateStart;

    @RedirectParameter
    private String lastExecutedBetweenDateEnd;

    @RedirectParameter
    private String nextExecutionOption;

    @RedirectParameter
    private String nextExecutionOnDate;

    @RedirectParameter
    private String nextExecutionBetweenDateStart;

    @RedirectParameter
    private String nextExecutionBetweenDateEnd;

    private List<IMoneyScheduler> results;
    private ObjectReference selectedMoneyScheduler;

    public FindMoneySchedulersForm() {

    }

    public Long getSchedulerTypeId() {
        return schedulerTypeId;
    }

    public void setSchedulerTypeId(Long schedulerTypeId) {
        this.schedulerTypeId = schedulerTypeId;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getLastExecutedBetweenDateEnd() {
        return lastExecutedBetweenDateEnd;
    }

    public void setLastExecutedBetweenDateEnd(String lastExecutedBetweenDateEnd) {
        this.lastExecutedBetweenDateEnd = lastExecutedBetweenDateEnd;
    }

    public String getLastExecutedBetweenDateStart() {
        return lastExecutedBetweenDateStart;
    }

    public void setLastExecutedBetweenDateStart(String lastExecutedBetweenDateStart) {
        this.lastExecutedBetweenDateStart = lastExecutedBetweenDateStart;
    }

    public String getLastExecutedOnDate() {
        return lastExecutedOnDate;
    }

    public void setLastExecutedOnDate(String lastExecutedOnDate) {
        this.lastExecutedOnDate = lastExecutedOnDate;
    }

    public String getLastExecutedOption() {
        return lastExecutedOption;
    }

    public void setLastExecutedOption(String lastExecutedOption) {
        this.lastExecutedOption = lastExecutedOption;
    }

    public boolean getRestrictSchedulerType() {
        return restrictSchedulerType;
    }

    public void setRestrictSchedulerType(boolean restrictSchedulerType) {
        this.restrictSchedulerType = restrictSchedulerType;
    }

    public String getNextExecutionBetweenDateEnd() {
        return nextExecutionBetweenDateEnd;
    }

    public void setNextExecutionBetweenDateEnd(String nextExecutionBetweenDateEnd) {
        this.nextExecutionBetweenDateEnd = nextExecutionBetweenDateEnd;
    }

    public String getNextExecutionBetweenDateStart() {
        return nextExecutionBetweenDateStart;
    }

    public void setNextExecutionBetweenDateStart(String nextExecutionBetweenDateStart) {
        this.nextExecutionBetweenDateStart = nextExecutionBetweenDateStart;
    }

    public String getNextExecutionOnDate() {
        return nextExecutionOnDate;
    }

    public void setNextExecutionOnDate(String nextExecutionOnDate) {
        this.nextExecutionOnDate = nextExecutionOnDate;
    }

    public String getNextExecutionOption() {
        return nextExecutionOption;
    }

    public void setNextExecutionOption(String nextExecutionOption) {
        this.nextExecutionOption = nextExecutionOption;
    }

    public void setResults(List<IMoneyScheduler> results) {
        this.results = results;
    }

    public List<IMoneyScheduler> getResults() {
        return results;
    }

    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return "find".equals(actionName);
    }

    public ObjectReference getSelectedMoneyScheduler() {
        return selectedMoneyScheduler;
    }

    public void setSelectedMoneyScheduler(ObjectReference selectedMoneyScheduler) {
        this.selectedMoneyScheduler = selectedMoneyScheduler;
    }

    public Long getMoneySchedulerId() {
        return moneySchedulerId;
    }

    public void setMoneySchedulerId(Long moneySchedulerId) {
        this.moneySchedulerId = moneySchedulerId;
    }
}
