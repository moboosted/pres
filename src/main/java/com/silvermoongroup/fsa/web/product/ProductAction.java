/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.product;

import com.silvermoongroup.businessservice.policymanagement.dto.ProductVersionDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.ProductDTO;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Justin Walsh
 */
public class ProductAction extends AbstractLookupDispatchAction {

    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ProductForm form = (ProductForm) actionForm;

        ProductDTO productDTO = getPolicyAdminService().getProduct(getApplicationContext(), form.getObjectReference());
        if (form.getVersionReference() != null) {
            Collection<ProductVersionDTO> updatedVersions = new ArrayList<>();
            for (ProductVersionDTO productVersion : productDTO.getProductVersions()) {
                if (form.getVersionReference().equals(productVersion.getObjectReference())) {
                    continue;
                }

                updatedVersions.add(productVersion);
            }

            productDTO.setProductVersions(updatedVersions);
        }

        form.setProduct(productDTO);
        return actionMapping.getInputForward();
    }

    /**
     * Back to the product version page
     */
    public ActionForward back(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        ProductForm form = (ProductForm) actionForm;

        ActionRedirect redirect = new ActionRedirect("productversion.do");
        redirect.addParameter("objectReference", form.getVersionReference());
        redirect.addParameter("context", form.getContext());

        return redirect;
    }
}
