/**
 * Licensed Materials
 * *  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 * *  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 * *  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.product;

import com.silvermoongroup.businessservice.policymanagement.dto.ProductVersionDTO;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import org.apache.struts.action.ActionForm;

/**
 * @author Justin Walsh
 */
public class ProductVersionForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private ObjectReference objectReference;

    @RedirectParameter
    private ObjectReference context;

    @RedirectParameter
    private Boolean selected = Boolean.FALSE;
    @RedirectParameter
    private ObjectReference contextReference;

    private ProductVersionDTO productVersion;
    private ObjectReference productObjectReference;
    private ObjectReference linkedReference;
    private Boolean replaced;

    public ObjectReference getObjectReference() {
        return objectReference;
    }

    public void setObjectReference(ObjectReference objectReference) {
        this.objectReference = objectReference;
    }

    public ObjectReference getProductObjectReference() {
        return productObjectReference;
    }

    public void setProductObjectReference(ObjectReference productObjectReference) {
        this.productObjectReference = productObjectReference;
    }

    public ProductVersionDTO getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(ProductVersionDTO productVersion) {
        this.productVersion = productVersion;
    }

    public Boolean getReplaced() {
        return replaced;
    }

    public void setReplaced(Boolean replaced) {
        this.replaced = replaced;
    }

    public ObjectReference getContext() {
        return context;
    }

    public void setContext(ObjectReference context) {
        this.context = context;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public ObjectReference getContextReference() {
        return contextReference;
    }

    public void setContextReference(ObjectReference contextReference) {
        this.contextReference = contextReference;
    }

    public ObjectReference getLinkedReference() {
        return linkedReference;
    }

    public void setLinkedReference(ObjectReference linkedReference) {
        this.linkedReference = linkedReference;
    }
}