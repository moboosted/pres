/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.product.action;

import com.silvermoongroup.businessservice.policymanagement.dto.ProductVersionDTO;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.fsa.web.agreement.form.FindAgreementForm;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.common.callback.CallBack;
import com.silvermoongroup.fsa.web.common.callback.CallBackUtility;
import com.silvermoongroup.fsa.web.common.util.SessionUtil;
import com.silvermoongroup.fsa.web.product.form.FindMarketableProductsForm;
import com.silvermoongroup.spflite.specframework.criteria.ProductVersionSearchCriteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Justin Walsh
 */
public class FindMarketableProductsAction extends AbstractLookupDispatchAction {

    public FindMarketableProductsAction() {
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.findmarktetableproducts.find", "findAndDisplay");
        map.put("page.findmarktetableproducts.link", "link");
        return map;
    }


    /**
     * Default entry point
     */
    public ActionForward defaultExecute(ActionMapping mapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return findAndDisplay(mapping, actionForm, httpServletRequest, httpServletResponse);
    }

    public ActionForward findAndDisplay(ActionMapping mapping, ActionForm actionForm,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {

        FindMarketableProductsForm form = (FindMarketableProductsForm) actionForm;
        convertCriteriaToStrings(form);//'clean up' input

        ProductVersionSearchCriteria searchCriteria = searchCriteriaFromFormInput(form);
        Collection<ProductVersionDTO> results = getPolicyAdminService().findMarketableProductsByCriteria(getApplicationContext(), searchCriteria);

        List<ProductVersionDTO> sortedResults = new ArrayList<>(results);
        sortedResults.sort(Comparator.comparing(ProductVersionDTO::getName));

        form.setSearchResults(sortedResults);

        populateProductCriteriaOptionsFromConfig(form);//in case any keys are missing (all are missing on first load of page)


        if (!CallBackUtility.isCallBackEmpty(request, response)) {
            // check if the callback stack is actually intended for us (we could be reading the callback stack of another operation, e.g. linking a party)
            // If so, we're in linking mode.
            if(CallBackUtility.isCallBackIntendedForMethod(request, response, "findProduct")) {
                form.setLinkingMode(true);
            }else {
                form.setLinkingMode(false);
                SessionUtil.clearConversationalState(request);
            }
        }

        return mapping.getInputForward();

    }

    /**
     * Link is clicked, link Product to a Role
     */
    public ActionForward link(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

        FindMarketableProductsForm form = (FindMarketableProductsForm) actionForm;
        CallBack callBack = CallBackUtility.getCallBack(request, response);
        if(!callBack.getIntendedMapping().equals("findProduct"))
            throw new IllegalStateException("Callback no longer valid -- cannot link product");//safety measure

        CallBackUtility.setAttribute(request, form.getSelectedProductObjectReference(), callBack);
        return CallBackUtility.getForwardAction(callBack);
    }

    private void populateProductCriteriaOptionsFromConfig(FindMarketableProductsForm form) {
        Map<String, Object> searchProductCriteria = form.getSearchProductCriteria();
        if(searchProductCriteria == null)
            searchProductCriteria = new LinkedHashMap<>();


        for(String availableCriteria : getApplicationProperties().getSearchProductCriteria().split(",")) {
            searchProductCriteria.putIfAbsent(availableCriteria, "");
        }
        form.setSearchProductCriteria(searchProductCriteria);
    }

    private ProductVersionSearchCriteria searchCriteriaFromFormInput(FindMarketableProductsForm form) {
        ProductVersionSearchCriteria searchCriteria = new ProductVersionSearchCriteria();
        searchCriteria.setEffectiveDate(Date.today());

        if(form.getSearchProductCriteria() != null) {
            form.getSearchProductCriteria().forEach((key, value) -> {
                String strValue = value.toString();;
                if(strValue != null && !strValue.isEmpty()) {
                    searchCriteria.addDefinitionCriteria(key, strValue);
                }
            });
        }

        return searchCriteria;
    }

    /**
     * For some reason, instead of returning results in a String, Struts/JSP outputs them as String[], always having one element.
     * However, Struts/JSP also requires that the input be a String, not String[], so if we want search values to persist we'll have to convert our result every time.
     *
     * This method performs this conversion.
     * @param form the form gotten as a result from Struts/JSP
     */
    private void convertCriteriaToStrings(FindMarketableProductsForm form) {
        //if it's a string array, get element 0, else leave it as is.
        form.getSearchProductCriteria().replaceAll(
                (key, value) -> value instanceof String[] ? ((String[])value)[0] : value
        );
    }

}
