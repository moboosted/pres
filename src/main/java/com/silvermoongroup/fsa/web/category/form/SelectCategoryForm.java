/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.category.form;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.intf.ICategoryScheme;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Form bean backing the category action.
 * 
 * @author Justin Walsh
 */
public class SelectCategoryForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    private ObjectReference categorySchemeObjectReference;
    private ICategoryScheme categoryScheme;
    private List<LabelValueBean> categories;
    private ObjectReference selectedCategoryObjectReference;
    
    public SelectCategoryForm() {
    }
    
    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        if ("defaultexecute".equalsIgnoreCase(actionName)) {
            return false;
        }
        return true;
    }

    public ObjectReference getCategorySchemeObjectReference() {
        return categorySchemeObjectReference;
    }
    
    public void setCategorySchemeObjectReference(ObjectReference categorySchemeObjectReference) {
        this.categorySchemeObjectReference = categorySchemeObjectReference;
    }

    public ICategoryScheme getCategoryScheme() {
        return categoryScheme;
    }

    public void setCategoryScheme(ICategoryScheme categoryScheme) {
        this.categoryScheme = categoryScheme;
    }

    public List<LabelValueBean> getCategories() {
        return categories;
    }

    public void setCategories(List<LabelValueBean> categories) {
        this.categories = categories;
    }

    public ObjectReference getSelectedCategoryObjectReference() {
        return selectedCategoryObjectReference;
    }

    public void setSelectedCategoryObjectReference(ObjectReference selectedCategoryObjectReference) {
        this.selectedCategoryObjectReference = selectedCategoryObjectReference;
    }
    
}