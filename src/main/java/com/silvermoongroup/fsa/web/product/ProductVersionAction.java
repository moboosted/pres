/**
 * Licensed Materials
 * *  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 * *  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 * *  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.product;

import com.silvermoongroup.businessservice.policymanagement.dto.ProductVersionDTO;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.spflite.specframework.domain.versionedproduct.ProductVersion;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Displays information about a particular version of a product
 *
 * @author Justin Walsh
 */
public class ProductVersionAction extends AbstractLookupDispatchAction {

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.productversion.action.save", "save");
        map.put("page.productversion.action.replace", "replaceFind");
        map.put("page.productversion.action.remove", "remove");
        return map;
    }

    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {

        ProductVersionForm form = (ProductVersionForm) actionForm;
        ProductVersionDTO productVersion = getPolicyAdminService().getProductVersion(getApplicationContext(), form.getObjectReference());
        if (form.isSelected()) {
            ProductVersionDTO contextVersion = getPolicyAdminService().getProductVersion(getApplicationContext(), form.getContextReference());
            contextVersion.setReplacedWith(productVersion);
            form.setProductVersion(contextVersion);
            form.setProductObjectReference(contextVersion.getObjectReference());
            form.setReplaced(contextVersion.isReplaced());
        } else {
            form.setProductVersion(productVersion);
            form.setProductObjectReference(form.getObjectReference());
            form.setReplaced(productVersion.isReplaced());
        }

        return actionMapping.getInputForward();
    }

    public ActionForward save(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

        ProductVersionForm form = (ProductVersionForm) actionForm;

        ProductVersionDTO productVersionDTO = getPolicyAdminService().getProductVersion(getApplicationContext(), form.getProductObjectReference());
        productVersionDTO.setReplaced(form.getReplaced());
        if (!form.getReplaced()) {
            productVersionDTO.setReplacedWith(null);
        }

        if (form.getLinkedReference() != null) {
            ProductVersionDTO linkedDTO = getPolicyAdminService().getProductVersion(getApplicationContext(), form.getLinkedReference());
            productVersionDTO.setReplacedWith(linkedDTO);
        }

        productVersionDTO = getPolicyAdminService().modifyProductVersion(getApplicationContext(), productVersionDTO);
        form.setProductVersion(productVersionDTO);
        form.setObjectReference(form.getProductObjectReference());
        form.setReplaced(productVersionDTO.isReplaced());

        addInformationMessage(request, "page.productversion.message.save.success");

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("objectReference", form.getProductObjectReference());
        redirect.addParameter("context", form.getContext());

        return redirect;
    }

    public ActionForward remove(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        ProductVersionForm form = (ProductVersionForm) actionForm;

        ProductVersionDTO productVersionDTO = getPolicyAdminService().getProductVersion(getApplicationContext(), form.getProductObjectReference());
        productVersionDTO.setReplacedWith(null);

        productVersionDTO = getPolicyAdminService().modifyProductVersion(getApplicationContext(), productVersionDTO);
        form.setProductVersion(productVersionDTO);
        form.setObjectReference(form.getProductObjectReference());
        form.setReplaced(productVersionDTO.isReplaced());

        addInformationMessage(request, "page.productversion.message.remove.success");

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("objectReference", form.getProductObjectReference());
        redirect.addParameter("context", form.getContext());

        return redirect;
    }

    public ActionForward replaceFind(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        ProductVersionForm form = (ProductVersionForm) actionForm;

        ActionRedirect redirect = new ActionRedirect("product.do");
        redirect.addParameter("objectReference", form.getContext());
        redirect.addParameter("versionReference", form.getProductObjectReference());

        return redirect;
    }

}
