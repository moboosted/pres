/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.fund;

import com.silvermoongroup.account.criteria.AssetOperatingFeeSearchCriteria;
import com.silvermoongroup.account.domain.intf.IAssetOperatingFee;
import com.silvermoongroup.account.domain.intf.IAssetPrice;
import com.silvermoongroup.account.domain.intf.IFund;
import com.silvermoongroup.businessservice.financialmanagement.criteria.RecentAssetPricesSearchCriteria;
import com.silvermoongroup.businessservice.financialmanagement.dto.RecentAssetPrices;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.common.callback.CallBack;
import com.silvermoongroup.fsa.web.common.callback.CallBackUtility;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Justin Walsh
 */
public class FundAction extends AbstractLookupDispatchAction {

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("button.backtoagreement", "backToAgreement");
        return map;
    }

    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        FundForm form = (FundForm)actionForm;

        IFund fund = getFinancialManagementService().getFund(getApplicationContext(), form.getFundObjectReference());
        form.setFund(fund);

        RecentAssetPricesSearchCriteria criteria = new RecentAssetPricesSearchCriteria();
        criteria.getQueryDetails().setMaximumRecordsRequested(10);
        criteria.setFinancialAssetObjectReference(fund.getObjectReference());
        RecentAssetPrices assetPrices = getFinancialManagementService().findRecentAssetPrices(getApplicationContext(), criteria);

        // sort the asset prices start date desc
        for (List<IAssetPrice> assetPricesForType: assetPrices.getAssetPrices().values()) {
            Collections.sort(assetPricesForType, new Comparator<IAssetPrice>() {

                @Override
                public int compare(IAssetPrice o1, IAssetPrice o2) {
                    return o1.getStartDate().compareTo(o2.getStartDate());
                }
            });
        }

        form.setRecentAssetPrices(assetPrices);

        AssetOperatingFeeSearchCriteria feeSearchCriteria = new AssetOperatingFeeSearchCriteria();
        feeSearchCriteria.getQueryDetails().setMaximumRecordsRequested(20);
        feeSearchCriteria.setOrderBy(AssetOperatingFeeSearchCriteria.OrderBy.START_DATE_DESC);
        feeSearchCriteria.setFinancialAssetId(form.getFundObjectReference().getObjectId());
        List<IAssetOperatingFee> assetOperatingFees = getFinancialManagementService().findAssetOperatingFees(getApplicationContext(), feeSearchCriteria);

        // sort the asset operating fees
        Collections.sort(assetOperatingFees, new Comparator<IAssetOperatingFee>() {
            @Override
            public int compare(IAssetOperatingFee o1, IAssetOperatingFee o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });

        form.setAssetOperatingFees(assetOperatingFees);

        return actionMapping.getInputForward();
    }

    /**
     * Back to the agreement GUI
     */
    public ActionForward back(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        CallBack callBack = CallBackUtility.getCallBack(httpServletRequest, httpServletResponse);
        return CallBackUtility.getForwardAction(callBack);
    }
}
