/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.ftx.moneyprovision.vo;

import com.silvermoongroup.common.domain.ObjectReference;

import java.io.Serializable;

public class MoneyProvisionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private ObjectReference objectReference;
    private String type;
    private String status;
    private String statusStart;
    private String statusEnd;
    private String startDate;
    private String endDate;
    private String internalCompanyCode;
    private String description;
    private int numberOfMPElements;

    private String accountBalance;

    public MoneyProvisionVO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfMPElements() {
        return numberOfMPElements;
    }

    public void setNumberOfMPElements(int numberOfMPElements) {
        this.numberOfMPElements = numberOfMPElements;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInternalCompanyCode() {
        return internalCompanyCode;
    }

    public void setInternalCompanyCode(String internalCompanyCode) {
        this.internalCompanyCode = internalCompanyCode;
    }

    public ObjectReference getObjectReference() {
        return objectReference;
    }
    
    public void setObjectReference(ObjectReference objectReference) {
        this.objectReference = objectReference;
    }

    public String getStatusStart() {
        return statusStart;
    }

    public void setStatusStart(String statusStart) {
        this.statusStart = statusStart;
    }

    public String getStatusEnd() {
        return statusEnd;
    }

    public void setStatusEnd(String statusEnd) {
        this.statusEnd = statusEnd;
    }
    
    public String getAccountBalance() {
        return accountBalance;
    }
    
    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }
}
