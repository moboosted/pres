/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.account;

import com.silvermoongroup.common.datatype.Amount;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import org.apache.struts.util.LabelValueBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Justin Walsh
 */
public class FindAccountEntriesForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private ObjectReference accountObjectReference;

    @RedirectParameter
    private String debitCreditIndicator;

    // posted date
    //
    @RedirectParameter
    private String postedDateOption;
    @RedirectParameter
    private String postedDateOn;
    @RedirectParameter
    private String postedDateBetweenStart;
    @RedirectParameter
    private String postedDateBetweenEnd;


    // value date
    //
    @RedirectParameter
    private String valueDateOption;
    @RedirectParameter
    private String valueDateOn;
    @RedirectParameter
    private String valueDateBetweenStart;
    @RedirectParameter
    private String valueDateBetweenEnd;


    @RedirectParameter
    private BigDecimal maximumAmount;

    @RedirectParameter
    private BigDecimal minimumAmount;

    @RedirectParameter
    private Long[] selectedAccountEntryTypes = new Long[0];

    private List<LabelValueBean> accountEntryTypes = new ArrayList<>();

    private FindAccountEntriesList results;

    private Amount debitTotal;
    private Amount creditTotal;

    // information about the table being displayed
    private String tableId;
    private int rowsPerPage;
    private int startRecord;
    
    public FindAccountEntriesForm() {
    }

    public ObjectReference getAccountObjectReference() {
        return accountObjectReference;
    }

    public void setAccountObjectReference(ObjectReference accountObjectReference) {
        this.accountObjectReference = accountObjectReference;
    }

    public String getDebitCreditIndicator() {
        return debitCreditIndicator;
    }

    public void setDebitCreditIndicator(String debitCreditIndicator) {
        this.debitCreditIndicator = debitCreditIndicator;
    }

    public BigDecimal getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(BigDecimal maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public BigDecimal getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(BigDecimal minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public String getPostedDateBetweenEnd() {
        return postedDateBetweenEnd;
    }

    public void setPostedDateBetweenEnd(String postedDateBetweenEnd) {
        this.postedDateBetweenEnd = postedDateBetweenEnd;
    }

    public String getPostedDateBetweenStart() {
        return postedDateBetweenStart;
    }

    public void setPostedDateBetweenStart(String postedDateBetweenStart) {
        this.postedDateBetweenStart = postedDateBetweenStart;
    }

    public String getPostedDateOn() {
        return postedDateOn;
    }

    public void setPostedDateOn(String postedDateOn) {
        this.postedDateOn = postedDateOn;
    }

    public String getPostedDateOption() {
        return postedDateOption;
    }

    public void setPostedDateOption(String postedDateOption) {
        this.postedDateOption = postedDateOption;
    }

    public String getValueDateBetweenEnd() {
        return valueDateBetweenEnd;
    }

    public void setValueDateBetweenEnd(String valueDateBetweenEnd) {
        this.valueDateBetweenEnd = valueDateBetweenEnd;
    }

    public String getValueDateBetweenStart() {
        return valueDateBetweenStart;
    }

    public void setValueDateBetweenStart(String valueDateBetweenStart) {
        this.valueDateBetweenStart = valueDateBetweenStart;
    }

    public String getValueDateOn() {
        return valueDateOn;
    }

    public void setValueDateOn(String valueDateOn) {
        this.valueDateOn = valueDateOn;
    }

    public String getValueDateOption() {
        return valueDateOption;
    }

    public void setValueDateOption(String valueDateOption) {
        this.valueDateOption = valueDateOption;
    }

    public FindAccountEntriesList getResults() {
        return results;
    }

    public void setResults(FindAccountEntriesList entries) {
        this.results = entries;
    }

    public Long[] getSelectedAccountEntryTypes() {
        return selectedAccountEntryTypes;
    }

    public void setSelectedAccountEntryTypes(Long[] selectedAccountEntryTypes) {
        this.selectedAccountEntryTypes = selectedAccountEntryTypes;
    }

    public List<LabelValueBean> getAccountEntryTypes() {
        return accountEntryTypes;
    }

    public void setAccountEntryTypes(List<LabelValueBean> accountEntryTypes) {
        this.accountEntryTypes = accountEntryTypes;
    }

    public void addAccountEntryType(LabelValueBean toAdd) {
        accountEntryTypes.add(toAdd);
    }

    public Amount getCreditTotal() {
        return creditTotal;
    }

    public void setCreditTotal(Amount creditTotal) {
        this.creditTotal = creditTotal;
    }

    public Amount getDebitTotal() {
        return debitTotal;
    }

    public void setDebitTotal(Amount debitTotal) {
        this.debitTotal = debitTotal;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableId() {
        return tableId;
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
