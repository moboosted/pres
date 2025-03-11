/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.fund;

import com.silvermoongroup.account.criteria.AssetPriceSearchCriteria;
import com.silvermoongroup.account.domain.intf.IAssetPrice;
import com.silvermoongroup.account.domain.intf.IFinancialAsset;
import com.silvermoongroup.common.constants.Components;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.datatype.DateTimePeriod;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Controller for the find asset prices functionality.
 *
 * @author Justin Walsh
 */
public class FindAssetPricesAction extends AbstractLookupDispatchAction {

    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        FindAssetPricesForm form = (FindAssetPricesForm) actionForm;
        populateStaticPageElements(form, request);
        return actionMapping.getInputForward();
    }

    public ActionForward find(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ActionRedirectFactory.createRedirect(actionMapping.findForward("findAndDisplay"), actionForm);
    }

    /**
     * GET: Redirect after post
     */
    public ActionForward findAndDisplay(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        FindAssetPricesForm form = (FindAssetPricesForm) actionForm;
        populateStaticPageElements(form, httpServletRequest);

        AssetPriceSearchCriteria criteria = buildCriteria(form);
        List<IAssetPrice> assetPrices = getFinancialManagementService().findAssetPrices(getApplicationContext(), criteria);

        // sort
        Collections.sort(assetPrices, new Comparator<IAssetPrice>() {

            @Override
            public int compare(IAssetPrice ap1, IAssetPrice ap2) {
                IFinancialAsset fa1 = ap1.getFinancialAsset();
                IFinancialAsset fa2 = ap2.getFinancialAsset();

                // first sort on financial asset name
                int result = fa1.getName().compareTo(fa2.getName());
                if (result != 0) {
                    return result;
                }

                // then sort on price type
                result = ap1.getPriceType().compareTo(ap2.getPriceType());
                if (result != 0) {
                    return result;
                }

                // then start date
                return ap1.getStartDate().compareTo(ap2.getStartDate());

            }
        });

        form.setResults(assetPrices);

        return actionMapping.getInputForward();
    }

    private AssetPriceSearchCriteria buildCriteria(FindAssetPricesForm form) {
        AssetPriceSearchCriteria criteria = new AssetPriceSearchCriteria();
        if (form.getFinancialAssetId() != null) {
            criteria.setFinancialAssetObjectReference(new ObjectReference(Components.FTX,
                    CoreTypeReference.FINANCIALASSET.getValue(), form.getFinancialAssetId()));
        }
        criteria.setFinancialAssetId(form.getFinancialAssetId());

        if (!GenericValidator.isBlankOrNull(form.getPriceTypeId())) {
            criteria.setPriceType(EnumerationReference.convertFromString(form.getPriceTypeId()));
        }

        criteria.setFinancialAssetName(form.getFinancialAssetName());
        criteria.setFundCode(form.getFundCode());

        String dateOption = form.getDateOption();
        if (dateOption != null) {
            if (dateOption.equals("on")) {
                criteria.setEffectiveOn(parseDate(form.getEffectiveOn()));
            } else if (dateOption.equals("between")) {
                DateTime dateTimeFrom = new DateTime(getTypeParser().parseDate(form.getEffectiveFrom()));
                Date dateTo = getTypeParser().parseDate(form.getEffectiveTo());
                DateTime dateTimeTo = new DateTime(dateTo.plus(1));
                criteria.setEffectivePeriod(new DateTimePeriod(dateTimeFrom, dateTimeTo));
            }
        }

        criteria.setLatestPriceOnly(form.isLatestPriceOnly());

        return criteria;
    }

    private void populateStaticPageElements(FindAssetPricesForm form, HttpServletRequest httpRequest) {

        if (form.getDateOption() == null) {
            form.setDateOption("any");
        }
    }
}
