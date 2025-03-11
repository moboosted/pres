/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.account.accountmapping;

import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import org.apache.struts.util.LabelValueBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for the add/update account mapping forms .
 * 
 * @author Justin
 */
public abstract class AbstractAccountMappingForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private Long financialTransactionTypeId;
    @RedirectParameter
    private Long contextTypeId;
    @RedirectParameter
    private String currencyCode;
    @RedirectParameter
    private String meansOfPayment;
    @RedirectParameter
    private String companyCode;
    @RedirectParameter
    private Long mappingDirection;
    @RedirectParameter
    private Long accountId;

    private List<LabelValueBean> contextTypeOptions = new ArrayList<>();

    public Long getFinancialTransactionTypeId() {
        return financialTransactionTypeId;
    }

    public void setFinancialTransactionTypeId(Long financialTransactionTypeId) {
        this.financialTransactionTypeId = financialTransactionTypeId;
    }

    public Long getContextTypeId() {
        return contextTypeId;
    }

    public void setContextTypeId(Long contextTypeId) {
        this.contextTypeId = contextTypeId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

    public Long getMappingDirection() {
        return mappingDirection;
    }

    public void setMappingDirection(Long mappingDirection) {
        this.mappingDirection = mappingDirection;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public List<LabelValueBean> getContextTypeOptions() {
        return contextTypeOptions;
    }

    public void setContextTypeOptions(List<LabelValueBean> contextTypeOptions) {
        this.contextTypeOptions = contextTypeOptions;
    }

}
