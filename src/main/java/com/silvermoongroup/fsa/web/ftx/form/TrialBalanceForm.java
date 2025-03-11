/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.ftx.form;

import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import com.silvermoongroup.ftx.domain.TrialBalance;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Form bean for the trial balance action.
 *
 * @author Justin Walsh
 */
public class TrialBalanceForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;
    @RedirectParameter
    private String dateFrom;
    @RedirectParameter
    private String dateTo;
    @RedirectParameter
    private String companyCode;
    @RedirectParameter
    private boolean hideEntriesWithNoMovement;
    @RedirectParameter
    private Long rootAccountTypeId;
    @RedirectParameter
    private String groupBy;
    @RedirectParameter
    private String balanceType;
    private TrialBalance trialBalance;
    private String exportFilename;

    public TrialBalanceForm() {
    }

    public TrialBalance getTrialBalance() {
        return trialBalance;
    }

    public void setTrialBalance(TrialBalance trialBalance) {
        this.trialBalance = trialBalance;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return !actionName.toLowerCase().equals("defaultexecute");
    }

    public boolean getHideEntriesWithNoMovement() {
        return hideEntriesWithNoMovement;
    }

    public void setHideEntriesWithNoMovement(boolean hideEntriesWithNoMovement) {
        this.hideEntriesWithNoMovement = hideEntriesWithNoMovement;
    }

    public String getExportFilename() {
        return exportFilename;
    }

    public void setExportFilename(String exportFilename) {
        this.exportFilename = exportFilename;
    }

    public Long getRootAccountTypeId() {
        return rootAccountTypeId;
    }

    public void setRootAccountTypeId(Long rootAccountTypeId) {
        this.rootAccountTypeId = rootAccountTypeId;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }
}
