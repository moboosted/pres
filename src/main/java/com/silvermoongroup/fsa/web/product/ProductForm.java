/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.product;

import com.silvermoongroup.businessservice.policymanagement.dto.ProductDTO;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import org.apache.struts.action.ActionForm;

/**
 * @author Justin Walsh
 */
public class ProductForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private ObjectReference objectReference;
    @RedirectParameter
    private ObjectReference versionReference;
    @RedirectParameter
    private ObjectReference context;

    private ProductDTO product;

    public ObjectReference getObjectReference() {
        return objectReference;
    }

    public void setObjectReference(ObjectReference objectReference) {
        this.objectReference = objectReference;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public ObjectReference getVersionReference() {
        return versionReference;
    }

    public void setVersionReference(ObjectReference versionReference) {
        this.versionReference = versionReference;
    }

    public ObjectReference getContext() {
        return context;
    }

    public void setContext(ObjectReference context) {
        this.context = context;
    }
}
