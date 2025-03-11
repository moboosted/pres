/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.category.action;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.domain.intf.ICategory;
import com.silvermoongroup.fsa.web.category.form.SelectCategoryForm;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.common.callback.CallBack;
import com.silvermoongroup.fsa.web.common.callback.CallBackUtility;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import org.apache.commons.lang3.Validate;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Justin Walsh
 */
public class SelectCategoryAction extends AbstractLookupDispatchAction {

    /**
     * Display a selection of categories, one of which can be selected by the user
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        
        setupForm(actionForm, httpServletRequest);
        return actionMapping.getInputForward();
    }
    
    /**
     * Display a selection of categories, one of which can be selected by the user
     */
    public ActionForward submit(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        
        SelectCategoryForm form = (SelectCategoryForm) actionForm;
        Assert.notNull(form.getSelectedCategoryObjectReference());
        
        CallBack callBack = CallBackUtility.getCallBack(httpServletRequest, httpServletResponse);
        CallBackUtility.setAttribute(httpServletRequest, form.getSelectedCategoryObjectReference(), callBack);
        return CallBackUtility.getForwardAction(callBack);
    }
    
    /**
     * Back button clicked
     */
    public ActionForward back(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        CallBack callBack = CallBackUtility.getCallBack(httpServletRequest, httpServletResponse);
        return CallBackUtility.getForwardAction(callBack);
    }
    
    @SuppressWarnings("unchecked")
    @OnFormValidationFailure
    public void setupForm(ActionForm actionForm, HttpServletRequest httpRequest) {
        
        SelectCategoryForm form = (SelectCategoryForm) actionForm;
        form.setCategoryScheme(getProductDevelopmentService().getCategoryScheme(getApplicationContext(), form.getCategorySchemeObjectReference()));
        Set<ICategory> categories =
                getProductDevelopmentService().getCategoriesForScheme(getApplicationContext(), form.getCategorySchemeObjectReference(), Date.today());
        
        List<LabelValueBean> categoryBeans = new ArrayList<>();
        for (ICategory category: categories) {
            categoryBeans.add(new LabelValueBean(category.getName(), String.valueOf(category.getObjectReference())));
        }
        Collections.sort(categoryBeans, LabelValueBean.CASE_INSENSITIVE_ORDER);
        form.setCategories(categoryBeans);
    }
    
}