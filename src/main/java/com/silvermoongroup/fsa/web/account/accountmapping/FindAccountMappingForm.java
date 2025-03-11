/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.account.accountmapping;

import com.silvermoongroup.account.domain.intf.IAccountMapping;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import org.apache.struts.util.LabelValueBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Form bean for the {@link FindAccountMappingAction}.
 *
 * @author Justin Walsh
 */
public class FindAccountMappingForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private Long financialTransactionType;

    @RedirectParameter
    private String currencyCode;

    @RedirectParameter
    private String meansOfPayment;

    @RedirectParameter
    private String companyCode;

    @RedirectParameter
    private String mappingDirection;

    @RedirectParameter
    private Long accountId;

    private ObjectReference accountMappingObjectReference;

    private List<LabelValueBean> financialTransactionTypeOptions = new ArrayList<>();

    private List<IAccountMapping> results = new ArrayList<>();

    public FindAccountMappingForm() {
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getMappingDirection() {
        return mappingDirection;
    }

    public void setMappingDirection(String mappingDirection) {
        this.mappingDirection = mappingDirection;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public List<IAccountMapping> getResults() {
        return results;
    }

    public void setResults(List<IAccountMapping> searchResults) {
        this.results = searchResults;
    }

    public Long getFinancialTransactionType() {
        return financialTransactionType;
    }

    public void setFinancialTransactionType(Long financialTransactionType) {
        this.financialTransactionType = financialTransactionType;
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

    public List<LabelValueBean> getFinancialTransactionTypeOptions() {
        return financialTransactionTypeOptions;
    }

    public void setFinancialTransactionTypeOptions(List<LabelValueBean> financialTranactionTypeOptions) {
        this.financialTransactionTypeOptions = financialTranactionTypeOptions;
    }

    public ObjectReference getAccountMappingObjectReference() {
        return accountMappingObjectReference;
    }

    public void setAccountMappingObjectReference(ObjectReference accountMappingObjectReference) {
        this.accountMappingObjectReference = accountMappingObjectReference;
    }
}
