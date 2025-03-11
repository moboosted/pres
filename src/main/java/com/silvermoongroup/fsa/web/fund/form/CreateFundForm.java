/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.fund.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.fund.util.FundGuiUtility;

/**
 * @author Justin Walsh
 */
public class CreateFundForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    private String name;
    private String fundCode;
    private String description;
    private String shortName;
    private String startDate;
    private String endDate;
    private String currencyCode;
    private String companyCode;
    private String probableGrowthRate;
    private String optimisticGrowthRate;

    public CreateFundForm() {
    }
    

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFundCode() {
        return fundCode;
    }
    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getShortName() {
        return shortName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return ("add").equals(actionName);
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getProbableGrowthRate() {
        return probableGrowthRate;
    }

    public void setProbableGrowthRate(String probableGrowthRate) {
        this.probableGrowthRate = probableGrowthRate;
    }

    public String getOptimisticGrowthRate() {
        return optimisticGrowthRate;
    }

    public void setOptimisticGrowthRate(String optimisticGrowthRate) {
        this.optimisticGrowthRate = optimisticGrowthRate;
    }

    @Override
    public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        if (needsValidation(actionMapping, httpServletRequest)) {

            if (!GenericValidator.isBlankOrNull(getProbableGrowthRate())) {
                if (!FundGuiUtility.isAmountValid(probableGrowthRate)) {
                    ActionErrors actionErrors = new ActionErrors();
                    actionErrors.add("probableGrowthRate", new ActionMessage("message.fund.invalidProbableGrowthRate"));
                    return actionErrors;
                }
            }

            if (!GenericValidator.isBlankOrNull(getOptimisticGrowthRate())) {
                if (!FundGuiUtility.isAmountValid(optimisticGrowthRate)) {
                    ActionErrors actionErrors = new ActionErrors();
                    actionErrors.add("optimisticGrowthRate", new ActionMessage("message.fund.invalidOptimisticGrowthRate"));
                    return actionErrors;
                }
            }
        }
        return super.validate(actionMapping, httpServletRequest);
    }
}
