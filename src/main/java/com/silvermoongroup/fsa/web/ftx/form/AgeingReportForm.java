/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.ftx.form;

import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import com.silvermoongroup.ftx.domain.AgeingReport;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Form bean for the trial balance action.
 */
public class AgeingReportForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;
    @RedirectParameter
    private EnumerationReference companyCode;
    @RedirectParameter
    private Long rootAccountTypeId;
    @RedirectParameter
    private AgeingReport ageingReport;
    private String exportFilename;

    public AgeingReportForm() {
    }

    public AgeingReport getAgeingReport() {
        return ageingReport;
    }

    public void setAgeingReport(AgeingReport ageingReport) {
        this.ageingReport = ageingReport;
    }

    public EnumerationReference getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(EnumerationReference companyCode) {
        this.companyCode = companyCode;
    }

    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return !actionName.toLowerCase().equals("defaultexecute");
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
}
