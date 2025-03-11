/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.ftx;

import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import com.silvermoongroup.ftx.domain.intf.IFinancialTransaction;

import java.util.Collection;

/**
 * @author Justin Walsh
 */
public class FindFinancialTransactionsForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private Long financialTransactionId;

    @RedirectParameter
    private Long financialTransactionTypeId;

    @RedirectParameter
    private boolean restrictType;

    @RedirectParameter
    private String amountOption;

    @RedirectParameter
    private String amount;

    @RedirectParameter
    private String minimumAmount;

    @RedirectParameter
    private String maximumAmount;

    @RedirectParameter
    private String postedDateOption;

    @RedirectParameter
    private String postedDate;

    @RedirectParameter
    private String postedDateFrom;

    @RedirectParameter
    private String postedDateTo;

    @RedirectParameter
    private String meansOfPayment;

    @RedirectParameter
    private String companyCode;

    @RedirectParameter
    private String externalReference;

    private Collection<IFinancialTransaction> results;

    public FindFinancialTransactionsForm() {
    }

    public Long getFinancialTransactionId() {
        return financialTransactionId;
    }

    public void setFinancialTransactionId(Long financialTransactionId) {
        this.financialTransactionId = financialTransactionId;
    }

    public Long getFinancialTransactionTypeId() {
        return financialTransactionTypeId;
    }

    public void setFinancialTransactionTypeId(Long financialTransactionTypeId) {
        this.financialTransactionTypeId = financialTransactionTypeId;
    }

    public boolean isRestrictType() {
        return restrictType;
    }

    public void setRestrictType(boolean restrictType) {
        this.restrictType = restrictType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(String minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public String getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(String maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getPostedDateFrom() {
        return postedDateFrom;
    }

    public void setPostedDateFrom(String postedDateFrom) {
        this.postedDateFrom = postedDateFrom;
    }

    public String getPostedDateTo() {
        return postedDateTo;
    }

    public void setPostedDateTo(String postedDateTo) {
        this.postedDateTo = postedDateTo;
    }

    public String getMeansOfPayment() {
        return meansOfPayment;
    }

    public void setMeansOfPayment(String meansOfPayment) {
        this.meansOfPayment = meansOfPayment;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public Collection<IFinancialTransaction> getResults() {
        return results;
    }

    public void setResults(Collection<IFinancialTransaction> results) {
        this.results = results;
    }

    public String getAmountOption() {
        return amountOption;
    }

    public void setAmountOption(String amountOption) {
        this.amountOption = amountOption;
    }

    public String getPostedDateOption() {
        return postedDateOption;
    }

    public void setPostedDateOption(String postedDateOption) {
        this.postedDateOption = postedDateOption;
    }
}
