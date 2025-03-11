/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.ftx.moneyprovision.vo;

import com.silvermoongroup.common.domain.ObjectReference;

import java.io.Serializable;
import java.math.BigDecimal;

public class MPESingleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private ObjectReference ObjectReference;
    private String runDate;
    private String dueDate;
    private BigDecimal baseAmount;
    private String currencyCode;
    private String advanceArrearsIndicator;
    private String contextType;
    private String type;
    private String targetFinancialTransactionType;

    public MPESingleVO() {
    }

    public ObjectReference getObjectReference() {
        return ObjectReference;
    }
    
    public void setObjectReference(ObjectReference objectReference) {
        ObjectReference = objectReference;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
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

    public String getAdvanceArrearsIndicator() {
        return advanceArrearsIndicator;
    }

    public void setAdvanceArrearsIndicator(String advanceArrearsIndicator) {
        this.advanceArrearsIndicator = advanceArrearsIndicator;
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

    public String getContextType() {
        return contextType;
    }
    public void setContextType(String contextType) {
        this.contextType = contextType;
    }
}
