/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.fund;

import com.silvermoongroup.account.domain.intf.IAssetOperatingFee;
import com.silvermoongroup.account.domain.intf.IFund;
import com.silvermoongroup.businessservice.financialmanagement.dto.RecentAssetPrices;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;

import java.util.List;

/**
 * Form bean for the Fund page
 *
 * @author Justin Walsh
 */
public class FundForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private ObjectReference fundObjectReference;
    private IFund fund;
    private RecentAssetPrices recentAssetPrices;
    private List<IAssetOperatingFee> assetOperatingFees;

    public FundForm() {
    }

    public IFund getFund() {
        return fund;
    }

    public void setFund(IFund fund) {
        this.fund = fund;
    }

    public ObjectReference getFundObjectReference() {
        return fundObjectReference;
    }

    public void setFundObjectReference(ObjectReference fundObjectReference) {
        this.fundObjectReference = fundObjectReference;
    }

    public RecentAssetPrices getRecentAssetPrices() {
        return recentAssetPrices;
    }

    public void setRecentAssetPrices(RecentAssetPrices recentAssetPrices) {
        this.recentAssetPrices = recentAssetPrices;
    }

    public void setAssetOperatingFees(List<IAssetOperatingFee> assetOperatingFees) {
        this.assetOperatingFees = assetOperatingFees;
    }

    public List<IAssetOperatingFee> getAssetOperatingFees() {
        return assetOperatingFees;
    }
}
