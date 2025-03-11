/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.moneyscheduler.execution;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import com.silvermoongroup.ftx.domain.MoneySchedulerExecution;

import java.util.Collection;

/**
 * @author Justin Walsh
 */
public class FindMoneySchedulerExecutionsForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    // a specific money scheduler execution
    @RedirectParameter
    private Long moneySchedulerExecutionId;

    // a specific money scheduler
    @RedirectParameter
    private String moneySchedulerOption; // specific, type or any
    @RedirectParameter
    private ObjectReference moneySchedulerObjectReference;
    @RedirectParameter
    private Long moneySchedulerTypeId;
    @RedirectParameter
    private boolean restrictMoneySchedulerType;

    // filter by execution start date
    @RedirectParameter
    private String executionStartedDateOption; // either on, between, or any
    @RedirectParameter
    private String executionStartedOnDate;
    @RedirectParameter
    private String executionStartedBetweenDateStart;
    @RedirectParameter
    private String executionStartedBetweenDateEnd;

    // filter by requested execution date
    @RedirectParameter
    private String requestedExecutionDateOption; // either on, between or any
    @RedirectParameter
    private String requestedExecutionOnDate;
    @RedirectParameter
    private String requestedExecutionBetweenDateStart;
    @RedirectParameter
    private String requestedExecutionBetweenDateEnd;

    // filter by return code
    @RedirectParameter
    private Integer[] returnCodes = new Integer[0];

    // the results
    private Collection<MoneySchedulerExecution> results;

    // information about the table being displayed
    private String tableId;
    private int rowsPerPage;
    private int startRecord;

    private ObjectReference selectedMoneySchedulerExecution;

    public FindMoneySchedulerExecutionsForm() {
    }

    public Collection<MoneySchedulerExecution> getResults() {
        return results;
    }

    public void setResults(Collection<MoneySchedulerExecution> results) {
        this.results = results;
    }

    public String getExecutionStartedBetweenDateEnd() {
        return executionStartedBetweenDateEnd;
    }

    public void setExecutionStartedBetweenDateEnd(String executionStartedBetweenDateEnd) {
        this.executionStartedBetweenDateEnd = executionStartedBetweenDateEnd;
    }

    public String getExecutionStartedBetweenDateStart() {
        return executionStartedBetweenDateStart;
    }

    public void setExecutionStartedBetweenDateStart(String executionStartedBetweenDateStart) {
        this.executionStartedBetweenDateStart = executionStartedBetweenDateStart;
    }

    public String getExecutionStartedOnDate() {
        return executionStartedOnDate;
    }

    public void setExecutionStartedOnDate(String executionStartedOnDate) {
        this.executionStartedOnDate = executionStartedOnDate;
    }

    public String getExecutionStartedDateOption() {
        return executionStartedDateOption;
    }

    public void setExecutionStartedDateOption(String executionStartedDateOption) {
        this.executionStartedDateOption = executionStartedDateOption;
    }

    public ObjectReference getMoneySchedulerObjectReference() {
        return moneySchedulerObjectReference;
    }

    public void setMoneySchedulerObjectReference(ObjectReference moneySchedulerObjectReference) {
        this.moneySchedulerObjectReference = moneySchedulerObjectReference;
    }

    public String getMoneySchedulerOption() {
        return moneySchedulerOption;
    }

    public void setMoneySchedulerOption(String moneySchedulerOption) {
        this.moneySchedulerOption = moneySchedulerOption;
    }

    public Long getMoneySchedulerTypeId() {
        return moneySchedulerTypeId;
    }

    public void setMoneySchedulerTypeId(Long moneySchedulerTypeId) {
        this.moneySchedulerTypeId = moneySchedulerTypeId;
    }

    public String getRequestedExecutionBetweenDateEnd() {
        return requestedExecutionBetweenDateEnd;
    }

    public void setRequestedExecutionBetweenDateEnd(String requestedExecutionBetweenDateEnd) {
        this.requestedExecutionBetweenDateEnd = requestedExecutionBetweenDateEnd;
    }

    public String getRequestedExecutionBetweenDateStart() {
        return requestedExecutionBetweenDateStart;
    }

    public void setRequestedExecutionBetweenDateStart(String requestedExecutionBetweenDateStart) {
        this.requestedExecutionBetweenDateStart = requestedExecutionBetweenDateStart;
    }

    public String getRequestedExecutionOnDate() {
        return requestedExecutionOnDate;
    }

    public void setRequestedExecutionOnDate(String requestedExecutionOnDate) {
        this.requestedExecutionOnDate = requestedExecutionOnDate;
    }

    public String getRequestedExecutionDateOption() {
        return requestedExecutionDateOption;
    }

    public void setRequestedExecutionDateOption(String requestedExecutionDateOption) {
        this.requestedExecutionDateOption = requestedExecutionDateOption;
    }

    public boolean isRestrictMoneySchedulerType() {
        return restrictMoneySchedulerType;
    }

    public void setRestrictMoneySchedulerType(boolean restrictMoneySchedulerType) {
        this.restrictMoneySchedulerType = restrictMoneySchedulerType;
    }

    public Integer[] getReturnCodes() {
        return returnCodes;
    }

    public void setReturnCodes(Integer[] returnCodes) {
        this.returnCodes = returnCodes;
    }

    public ObjectReference getSelectedMoneySchedulerExecution() {
        return selectedMoneySchedulerExecution;
    }

    public void setSelectedMoneySchedulerExecution(ObjectReference selectedMoneySchedulerExecution) {
        this.selectedMoneySchedulerExecution = selectedMoneySchedulerExecution;
    }

    public Long getMoneySchedulerExecutionId() {
        return moneySchedulerExecutionId;
    }

    public void setMoneySchedulerExecutionId(Long moneySchedulerExecutionId) {
        this.moneySchedulerExecutionId = moneySchedulerExecutionId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public int getStartRecord() {
        return startRecord;
    }

    public void setStartRecord(int startRecord) {
        this.startRecord = startRecord;
    }
}
