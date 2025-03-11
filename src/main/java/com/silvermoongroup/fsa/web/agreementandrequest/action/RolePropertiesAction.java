/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.action;

import com.silvermoongroup.businessservice.policymanagement.dto.RoleDTO;
import com.silvermoongroup.businessservice.policymanagement.enumeration.ElementType;
import com.silvermoongroup.fsa.web.agreementandrequest.form.RolePropertiesForm;
import com.silvermoongroup.fsa.web.agreementandrequest.util.PathTraversalUtil;
import org.apache.commons.lang3.Validate;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mubeen
 * 
 *         This will handle the Properties for a given Role
 */
public class RolePropertiesAction extends AbstractViewAction<RolePropertiesForm> {

    /**
     * Entry point into the action. The following request parameters are required:
     * 
     * 1) contextObjectReference - The context object reference (request or tla) 2) contextPath - the path that we need
     * to return to to display the context object 3) path - The path to the role for which the properties are being
     * displayed.
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        RolePropertiesForm roleForm = (RolePropertiesForm) actionForm;

        Assert.notNull(roleForm.getContextObjectReference(), "contextObjectReference is required");
        Assert.notNull(roleForm.getContextPath(), "contextPath is required");
        Assert.notNull(roleForm.getPath(), "path is required");

        RoleDTO roleDTO = getPolicyAdminService().getRoleView(getApplicationContext(),
                roleForm.getContextObjectReference(), roleForm.isTlaRelativePath(), roleForm.getPath());
        roleForm.setStructuredActualDTO(roleDTO);

        preparePageForDisplay(roleForm, httpServletRequest);
        return actionMapping.getInputForward();
    }

    public ActionForward validateAndBindProperties(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        RolePropertiesForm roleForm = (RolePropertiesForm) actionForm;
        String path = roleForm.getPath();
        Assert.notNull(path, "Path is required to retrieve the Role.");

        RoleDTO roleDTO = getPolicyAdminService().getRoleView(getApplicationContext(),
                roleForm.getContextObjectReference(), roleForm.isTlaRelativePath(), path);
        roleForm.setStructuredActualDTO(roleDTO);

        return super.validateAndBindProperties(actionMapping, (RolePropertiesForm) actionForm, httpServletRequest,
                httpServletResponse);
    }

    public ActionForward back(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        RolePropertiesForm roleForm = (RolePropertiesForm) actionForm;
        String pathToRedirectTo = PathTraversalUtil.trimTraversalPath(roleForm.getPath(), ElementType.Role);

        ActionRedirect redirect = new ActionRedirect(roleForm.getContextPath() + ".do");
        redirect.addParameter("contextObjectReference", roleForm.getContextObjectReference());
        redirect.addParameter("path", pathToRedirectTo);

        return redirect;
    }

    @Override
    public ActionForward submit(ActionMapping mapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        RolePropertiesForm roleForm = (RolePropertiesForm) actionForm;

        String path = roleForm.getPath();
        RoleDTO roleDTO = roleForm.getStructuredActualDTO();

        getPolicyAdminService().updateRoleInOnlyActualProperties(getApplicationContext(),
                roleForm.getContextObjectReference(), roleDTO.getProperties(), roleForm.isTlaRelativePath(), null, path);

        String pathToRedirectTo = PathTraversalUtil.trimTraversalPath(roleForm.getPath(), ElementType.Role);

        ActionRedirect redirect = new ActionRedirect(roleForm.getContextPath() + ".do");
        redirect.addParameter("contextObjectReference", roleForm.getContextObjectReference());
        redirect.addParameter("path", pathToRedirectTo);

        return redirect;
    }

    /* Utility Methods */

    @Override
    protected void handlePropertyValidationFailurePath(ActionForm actionForm) {

    }

    @Override
    protected void saveStructuredActual(RolePropertiesForm form, String initialMethod) {

    }

    @Override
    protected void preparePageForDisplay(RolePropertiesForm form, HttpServletRequest request) {
    }
}