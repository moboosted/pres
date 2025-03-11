/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.agreement.form;

import com.silvermoongroup.fsa.statistics.AgreementStatistics;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by damir on 8/27/2015.
 */
public class AgreementStatisticsForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;
    @RedirectParameter
    private String dateFrom;
    @RedirectParameter
    private String dateTo;
    @RedirectParameter
    private String companyCode;
    private AgreementStatistics agreementStatistics;
    private String exportFilename;

    public AgreementStatisticsForm() {
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

    public AgreementStatistics getAgreementStatistics() {
        return agreementStatistics;
    }

    public void setAgreementStatistics(AgreementStatistics agreementStatistics) {
        this.agreementStatistics = agreementStatistics;
    }

    public String getExportFilename() {
        return exportFilename;
    }

    public void setExportFilename(String exportFilename) {
        this.exportFilename = exportFilename;
    }

    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return !actionName.toLowerCase().equals("defaultexecute");
    }

    @Override
    public String toString() {
        return "AgreementStatisticsForm{" +
                "dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", agreementStatistics=" + agreementStatistics +
                ", exportFilename='" + exportFilename + '\'' +
                '}';
    }
}
