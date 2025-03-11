/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.moneyscheduler.form;

import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import com.silvermoongroup.ftx.domain.intf.IMoneyScheduler;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Justin Walsh
 */
public class FindMoneySchedulersToExecuteForm extends ValidatorForm {

    @RedirectParameter
    private Long moneySchedulerTypeId;

    @RedirectParameter
    private boolean restrictMoneySchedulerType;

    @RedirectParameter
    private String nextRunDate;

    private List<IMoneyScheduler> results;

    public FindMoneySchedulersToExecuteForm() {
    }

    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return "find".equals(actionName);
    }

    public String getNextRunDate() {
        return nextRunDate;
    }

    public void setNextRunDate(String nextRunDate) {
        this.nextRunDate = nextRunDate;
    }

    public Long getMoneySchedulerTypeId() {
        return moneySchedulerTypeId;
    }

    public void setMoneySchedulerTypeId(Long moneySchedulerTypeId) {
        this.moneySchedulerTypeId = moneySchedulerTypeId;
    }

    public boolean isRestrictMoneySchedulerType() {
        return restrictMoneySchedulerType;
    }

    public void setRestrictMoneySchedulerType(boolean restrictMoneySchedulerType) {
        this.restrictMoneySchedulerType = restrictMoneySchedulerType;
    }

    public List<IMoneyScheduler> getResults() {
        return results;
    }

    public void setResults(List<IMoneyScheduler> results) {
        this.results = results;
    }
}
