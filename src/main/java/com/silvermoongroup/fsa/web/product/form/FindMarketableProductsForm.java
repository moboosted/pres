/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.product.form;

import com.silvermoongroup.businessservice.policymanagement.dto.ProductVersionDTO;
import com.silvermoongroup.common.domain.ObjectReference;
import org.apache.struts.action.ActionForm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Justin Walsh
 */
public class FindMarketableProductsForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    /**
     * Placeholder 'normal' search field. At least one seems to be required for dynamic fields to work (?)
     * TODO replace with useful field
     */
    private String txtSearchName = null;

    /**
     * Key of product criteria name, value of value to search for (blank/null = any)
     * Value is 'Object' because Struts/JSPs returns responses with a String[]{value} instead of intended input String.
     */
    private Map<String, Object> searchProductCriteria = new LinkedHashMap<>();

    private List<ProductVersionDTO> searchResults = new ArrayList<>();

    private boolean linkingMode;

    private ObjectReference selectedProductObjectReference;//for linking response

    public FindMarketableProductsForm() {
    }

    public List<ProductVersionDTO> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<ProductVersionDTO> searchResults) {
        this.searchResults = searchResults;
    }

    public String getTxtSearchName() {
        return txtSearchName;
    }

    public void setTxtSearchName(String txtSearchName) {
        this.txtSearchName = txtSearchName;
    }

    public Map<String, Object> getSearchProductCriteria() {
        return searchProductCriteria;
    }

    public void setSearchProductCriteria(Map<String, Object> searchProductCriteria) {
        this.searchProductCriteria = searchProductCriteria;
    }

    public void setLinkingMode(boolean linkingMode) {
        this.linkingMode = linkingMode;
    }

    public boolean getLinkingMode() {
        return linkingMode;
    }

    public ObjectReference getSelectedProductObjectReference() {
        return selectedProductObjectReference;
    }

    public void setSelectedProductObjectReference(ObjectReference selectedProductObjectReference) {
        this.selectedProductObjectReference = selectedProductObjectReference;
    }
}
