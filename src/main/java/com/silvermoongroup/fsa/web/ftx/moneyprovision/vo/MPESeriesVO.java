/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.ftx.moneyprovision.vo;

import com.silvermoongroup.common.domain.ObjectReference;

import java.io.Serializable;
import java.math.BigDecimal;

public class MPESeriesVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private ObjectReference objectReference;

    private String startDate;
    private String endDate;
    private String type;
    private String frequency;
    private String advanceArrearsIndicator;
    private String nextDueDate;
    private String runDate;
    private String anniversaryDate;
    private String currencyCode;
    private BigDecimal baseAmount;
    private String targetFinancialTransactionType;

    public MPESeriesVO() {
    }


    public String getAdvanceArrearsIndicator() {
        return advanceArrearsIndicator;
    }

    public void setAdvanceArrearsIndicator(String advanceArrearsIndicator) {
        this.advanceArrearsIndicator = advanceArrearsIndicator;
    }

    public String getAnniversaryDate() {
        return anniversaryDate;
    }

    public void setAnniversaryDate(String anniversaryDate) {
        this.anniversaryDate = anniversaryDate;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFrequency() {
        return frequency;
    }


    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(String nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public ObjectReference getObjectReference() {
        return objectReference;
    }
    
    public void setObjectReference(ObjectReference objectReference) {
        this.objectReference = objectReference;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTargetFinancialTransactionType() {
        return targetFinancialTransactionType;
    }

    public void setTargetFinancialTransactionType(String targetFinancialTransactionType) {
        this.targetFinancialTransactionType = targetFinancialTransactionType;
    }
}
