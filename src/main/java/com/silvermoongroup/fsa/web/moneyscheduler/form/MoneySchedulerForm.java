/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.moneyscheduler.form;

import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.enumeration.intf.IInternalCompanyCode;
import com.silvermoongroup.common.enumeration.intf.IMeansOfPayment;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.ftx.domain.MoneySchedulerExecution;
import com.silvermoongroup.ftx.domain.enumeration.AnniversaryFixingType;
import com.silvermoongroup.ftx.domain.intf.IMoneyProvision;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Form bean for the money scheduler
 */
public class MoneySchedulerForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;


    private ObjectReference moneySchedulerObjectReference;
    private String type;
    private String frequency;
    private EnumerationReference paymentMethod;
    private EnumerationReference internalCompanyCode;
    private String anniversaryDate;
    private AnniversaryFixingType anniversaryFixingType;
    private String startDate;
    private String endDate;
    private String nextCycleStartDate;
    private String nextRunDate;
    private String sourceAccountName;
    private ObjectReference sourceAccountObjectReference;
    private String targetAccountName;
    private ObjectReference targetAccountObjectReference;
    private String receiverName;
    private String payerName;
    private String moneySchedulerStatus;
    private String moneySchedulerStatusStart;
    private String moneySchedulerStatusEnd;
    private boolean rollup;

    private List<MoneySchedulerExecution> recentExecutions = new ArrayList<>();
    private List<IMoneyProvision> moneyProvisions;

    public MoneySchedulerForm() {
    }

    /**
     * If the value of <link>mapping.getParameter()</link> is "enquire" return true;
     */
    @Override
    public boolean needsValidation(ActionMapping mapping, HttpServletRequest request) {

        String name = getActionName(mapping, request);
        if (GenericValidator.isBlankOrNull(name)) {
            return false;
        }

        name = name.toLowerCase().trim();
        return ("enquire".equals(name));
    }

    public String getAnniversaryDate() {
        return anniversaryDate;
    }

    public void setAnniversaryDate(String anniversaryDate) {
        this.anniversaryDate = anniversaryDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNextCycleStartDate() {
        return nextCycleStartDate;
    }

    public void setNextCycleStartDate(String nextCycleStartDate) {
        this.nextCycleStartDate = nextCycleStartDate;
    }

    public String getNextRunDate() {
        return nextRunDate;
    }

    public void setNextRunDate(String nextRunDate) {
        this.nextRunDate = nextRunDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payer) {
        this.payerName = payer;
    }

    public EnumerationReference getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(IMeansOfPayment paymentMethod) {
        this.paymentMethod = paymentMethod != null ? paymentMethod.getEnumerationReference() : null;
    }

    public void setPaymentMethod(EnumerationReference paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiver) {
        this.receiverName = receiver;
    }

    public String getSourceAccountName() {
        return sourceAccountName;
    }

    public void setSourceAccountName(String sourceAccountName) {
        this.sourceAccountName = sourceAccountName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTargetAccountName() {
        return targetAccountName;
    }

    public void setTargetAccountName(String targetAccountName) {
        this.targetAccountName = targetAccountName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoneySchedulerStatus() {
        return moneySchedulerStatus;
    }

    public void setMoneySchedulerStatus(String moneySchedulerStatus) {
        this.moneySchedulerStatus = moneySchedulerStatus;
    }

    public String getMoneySchedulerStatusStart() {
        return moneySchedulerStatusStart;
    }

    public void setMoneySchedulerStatusStart(String moneySchedulerStatusStart) {
        this.moneySchedulerStatusStart = moneySchedulerStatusStart;
    }

    public String getMoneySchedulerStatusEnd() {
        return moneySchedulerStatusEnd;
    }

    public void setMoneySchedulerStatusEnd(String moneySchedulerStatusEnd) {
        this.moneySchedulerStatusEnd = moneySchedulerStatusEnd;
    }

    public ObjectReference getMoneySchedulerObjectReference() {
        return moneySchedulerObjectReference;
    }

    public void setMoneySchedulerObjectReference(ObjectReference moneySchedulerObjectReference) {
        this.moneySchedulerObjectReference = moneySchedulerObjectReference;
    }

    public void setInternalCompanyCode(IInternalCompanyCode internalCompanyCode) {
        this.internalCompanyCode = internalCompanyCode != null ? internalCompanyCode.getEnumerationReference() : null;
    }

    public void setInternalCompanyCode(EnumerationReference internalCompanyCode) {
        this.internalCompanyCode = internalCompanyCode;
    }

    public EnumerationReference getInternalCompanyCode() {
        return internalCompanyCode;
    }

    public List<MoneySchedulerExecution> getRecentExecutions() {
        return recentExecutions;
    }

    public void setRecentExecutions(List<MoneySchedulerExecution> recentExecutions) {
        this.recentExecutions = recentExecutions;
    }

    public AnniversaryFixingType getAnniversaryFixingType() {
        return anniversaryFixingType;
    }

    public void setAnniversaryFixingType(AnniversaryFixingType anniversaryFixingType) {
        this.anniversaryFixingType = anniversaryFixingType;
    }

    public boolean isRollup() {
        return rollup;
    }

    public void setRollup(boolean rollup) {
        this.rollup = rollup;
    }

    public ObjectReference getSourceAccountObjectReference() {
        return sourceAccountObjectReference;
    }

    public void setSourceAccountObjectReference(ObjectReference sourceAccountObjectReference) {
        this.sourceAccountObjectReference = sourceAccountObjectReference;
    }

    public ObjectReference getTargetAccountObjectReference() {
        return targetAccountObjectReference;
    }

    public void setTargetAccountObjectReference(ObjectReference targetAccountObjectReference) {
        this.targetAccountObjectReference = targetAccountObjectReference;
    }

    public void setMoneyProvisions(List<IMoneyProvision> moneyProvisions) {
        this.moneyProvisions = moneyProvisions;
    }

    public List<IMoneyProvision> getMoneyProvisions() {
        return moneyProvisions;
    }
}