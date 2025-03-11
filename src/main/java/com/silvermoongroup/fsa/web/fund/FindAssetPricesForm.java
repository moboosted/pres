/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.fund;

import com.silvermoongroup.account.domain.intf.IAssetPrice;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;

import java.util.List;

/**
 * @author Justin Walsh
 */
public class FindAssetPricesForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private Long financialAssetId;
    @RedirectParameter
    private String priceTypeId;
    @RedirectParameter
    private boolean latestPriceOnly;
    @RedirectParameter
    private String financialAssetName;
    @RedirectParameter
    private String dateOption;
    @RedirectParameter
    private String effectiveOn;
    @RedirectParameter
    private String effectiveFrom;
    @RedirectParameter
    private String effectiveTo;
    @RedirectParameter
    private String probableGrowthRate;
    @RedirectParameter
    private String optimisticGrowthRate;
    @RedirectParameter
    private String fundCode;

    private List<IAssetPrice> results;

    public FindAssetPricesForm() {
    }

    public String getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(String priceTypeId) {
        this.priceTypeId = priceTypeId;
    }

    public boolean isLatestPriceOnly() {
        return latestPriceOnly;
    }

    public void setLatestPriceOnly(boolean latestPriceOnly) {
        this.latestPriceOnly = latestPriceOnly;
    }

    public String getFinancialAssetName() {
        return financialAssetName;
    }

    public void setFinancialAssetName(String financialAssetName) {
        this.financialAssetName = financialAssetName;
    }

    public List<IAssetPrice> getResults() {
        return results;
    }

    public void setResults(List<IAssetPrice> results) {
        this.results = results;
    }

    public Long getFinancialAssetId() {
        return financialAssetId;
    }

    public void setFinancialAssetId(Long financialAssetId) {
        this.financialAssetId = financialAssetId;
    }

    public String getDateOption() {
        return dateOption;
    }

    public void setDateOption(String dateOption) {
        this.dateOption = dateOption;
    }

    public String getEffectiveOn() {
        return effectiveOn;
    }

    public void setEffectiveOn(String effectiveOn) {
        this.effectiveOn = effectiveOn;
    }

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public String getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(String effectiveTo) {
        this.effectiveTo = effectiveTo;
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

    public String getFundCode()
    {
        return fundCode;
    }

    public void setFundCode(String fundCode)
    {
        this.fundCode = fundCode;
    }
}
