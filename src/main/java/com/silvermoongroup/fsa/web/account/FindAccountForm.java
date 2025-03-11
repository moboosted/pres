/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.account;

import com.silvermoongroup.account.domain.intf.IAccount;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Justin Walsh
 */
public class FindAccountForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private String name;
    @RedirectParameter
    private Long accountId;
    @RedirectParameter
    private Long accountTypeId;
    @RedirectParameter
    private boolean restrictAccountType;
    @RedirectParameter
    private String companyCode;
    @RedirectParameter
    private String currencyCode;

    @RedirectParameter
    private String openedDateOption;
    @RedirectParameter
    private String openedOnDate;
    @RedirectParameter
    private String openedBetweenDateStart;
    @RedirectParameter
    private String openedBetweenDateEnd;

    @RedirectParameter
    private Long[] accountEntryTypes = new Long[0];

    private List<IAccount> results;

    public FindAccountForm() {
    }

    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return "find".equals(actionName);
    }

    public Long[] getAccountEntryTypes() {
        return accountEntryTypes;
    }

    public void setAccountEntryTypes(Long[] accountEntryTypes) {
        this.accountEntryTypes = accountEntryTypes;
    }

    public Long getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenedBetweenDateEnd() {
        return openedBetweenDateEnd;
    }

    public void setOpenedBetweenDateEnd(String openedBetweenDateEnd) {
        this.openedBetweenDateEnd = openedBetweenDateEnd;
    }

    public String getOpenedBetweenDateStart() {
        return openedBetweenDateStart;
    }

    public void setOpenedBetweenDateStart(String openedBetweenDateStart) {
        this.openedBetweenDateStart = openedBetweenDateStart;
    }

    public String getOpenedOnDate() {
        return openedOnDate;
    }

    public void setOpenedOnDate(String openedOnDate) {
        this.openedOnDate = openedOnDate;
    }

    public String getOpenedDateOption() {
        return openedDateOption;
    }

    public void setOpenedDateOption(String openedDateOption) {
        this.openedDateOption = openedDateOption;
    }

    public boolean isRestrictAccountType() {
        return restrictAccountType;
    }

    public void setRestrictAccountType(boolean restrictAccountType) {
        this.restrictAccountType = restrictAccountType;
    }

    public List<IAccount> getResults() {
        return results;
    }

    public void setResults(List<IAccount> results) {
        this.results = results;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
