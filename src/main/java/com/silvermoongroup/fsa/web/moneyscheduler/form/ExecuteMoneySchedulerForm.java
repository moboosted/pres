/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.moneyscheduler.form;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Justin Walsh
 */
public class ExecuteMoneySchedulerForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    public ExecuteMoneySchedulerForm() {
    }

    private String executionOption; // agreement or objectreference

    // execution for an agreement
    private String agreementNumber;
    private Long agreementKindId;
    private Long schedulerTypeId;

    // execution for an specific money scheduler
    private ObjectReference moneySchedulerObjectReference;

    private String executionDate;

    private List<LabelValueBean> agreementKinds = new ArrayList<>();

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public Long getAgreementKindId() {
        return agreementKindId;
    }

    public void setAgreementKindId(Long agreementKind) {
        this.agreementKindId = agreementKind;
    }

    public String getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }

    public Long getSchedulerTypeId() {
        return schedulerTypeId;
    }

    public void setSchedulerTypeId(Long schedulerTypeId) {
        this.schedulerTypeId = schedulerTypeId;
    }

    public List<LabelValueBean> getAgreementKinds() {
        return agreementKinds;
    }

    public void setAgreementKinds(List<LabelValueBean> agreementKinds) {
        this.agreementKinds = agreementKinds;
    }

    public String getExecutionOption() {
        return executionOption;
    }

    public void setExecutionOption(String executionOption) {
        this.executionOption = executionOption;
    }

    public ObjectReference getMoneySchedulerObjectReference() {
        return moneySchedulerObjectReference;
    }

    public void setMoneySchedulerObjectReference(ObjectReference moneySchedulerObjectReference) {
        this.moneySchedulerObjectReference = moneySchedulerObjectReference;
    }

    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return "executeScheduler".equalsIgnoreCase(actionName);
    }
}
