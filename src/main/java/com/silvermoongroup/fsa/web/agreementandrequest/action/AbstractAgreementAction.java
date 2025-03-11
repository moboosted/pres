/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.action;

import com.silvermoongroup.businessservice.policymanagement.dto.AgreementDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.ConstantRoleDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.RoleDTO;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.agreementandrequest.constants.TopLevelAgreementContextEnum;
import com.silvermoongroup.fsa.web.agreementandrequest.form.AgreementForm;
import com.silvermoongroup.fsa.web.agreementandrequest.helpers.AgreementRoleInActualStrategy;
import com.silvermoongroup.fsa.web.agreementandrequest.helpers.RoleInActualStrategy;
import com.silvermoongroup.fsa.web.agreementandrequest.helpers.ViewActionHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Base class for the {@link AgreementAction} and {@link AgreementViewAction} classes.
 * 
 * @author Justin Walsh
 */
public abstract class AbstractAgreementAction extends AbstractViewAction<AgreementForm> {

    protected RoleInActualStrategy<AgreementForm> roleInActualStrategy;

    @Override
    protected void onInit() {
        super.onInit();
        roleInActualStrategy = getBean(AgreementRoleInActualStrategy.class);
    }

    /**
     * @return The context in which the agreement is being viewed, either from within a request, or directly from the
     *         agreement.
     */
    public abstract TopLevelAgreementContextEnum getTopLevelAgreementContext();

    /**
     * Display an agreement, either the top level agreement or a component agreement.
     * 
     * The following parameters are used: 1) contextObjectReference - The object reference of the request that this
     * agreement is being modified within (for {@link AgreementAction}) or the object reference of the agreement (for
     * {@link AgreementViewAction}). 2) path - The path to the agreement. (empty or null for the top level agreement)
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        AgreementForm agreementForm = (AgreementForm) actionForm;

        String path = StringUtils.trimToNull(agreementForm.getPath());

        AgreementDTO agreement = getPolicyAdminService().getAgreementView(getApplicationContext(),
                agreementForm.getContextObjectReference(), path);
        agreementForm.setStructuredActualDTO(agreement);

        // This is to cater for Role Agreement Part Back Navigation
        if (StringUtils.isEmpty(path)) {
            agreementForm.setInRoleContext(!(ViewActionHelper.isEmpty(httpServletRequest)));
        }

        // now set the url path for this mapping
        agreementForm.setContextPath(actionMapping.getPath());

        preparePageForDisplay(agreementForm, httpServletRequest);
        return actionMapping.getInputForward();
    }

    /**
     * View the details of a component (i.e. drill down)
     */
    public ActionForward viewComponent(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        AgreementForm agreementForm = (AgreementForm) actionForm;
        ObjectReference contextObjectRef = agreementForm.getContextObjectReference();
        String path = agreementForm.getPath();
        Assert.notNull(contextObjectRef, "A ContextObjectReference is required to drill down into a component.");

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", contextObjectRef.toString());
        redirect.addParameter("path", path);
        return redirect;
    }

    public ActionForward viewRoleProperties(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        AgreementForm agreementForm = (AgreementForm) actionForm;
        ObjectReference contextObjectReference = agreementForm.getContextObjectReference();
        String path = agreementForm.getPath();

        Assert.notNull(contextObjectReference, "A ContextObjectReference is required to view the Role Properties.");
        Assert.notNull(path, "A path is required to navigate to the Role Properties.");

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("roleProperties"));
        redirect.addParameter("contextObjectReference", contextObjectReference.toString());
        redirect.addParameter("contextPath", actionMapping.getPath());
        redirect.addParameter("path", path);
        redirect.addParameter("tlaRelativePath", getTopLevelAgreementContext().equals(TopLevelAgreementContextEnum.REQUEST));
        return redirect;
    }

    public ActionForward viewRolePlayer(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        AgreementForm agreementForm = (AgreementForm) actionForm;
        ObjectReference contextObjectReference = agreementForm.getContextObjectReference();
        String path = agreementForm.getPath();
        Assert.notNull(contextObjectReference, "The Agreement ObjectReference is required to view a Role.");

        // TODO if we pass in the object reference, we won't need to look up the role
        boolean tlaInRequestContext = getTopLevelAgreementContext().equals(TopLevelAgreementContextEnum.REQUEST);
        RoleDTO roleDTO = getPolicyAdminService().getRoleView(getApplicationContext(), contextObjectReference, tlaInRequestContext, path);

        ViewActionHelper<AgreementForm> actionHelper = new ViewActionHelper<AgreementForm>();
        actionHelper.pushActionHelperDTO(agreementForm, httpServletRequest, path);

        return roleInActualStrategy.roleInActualViewCallback(actionMapping, httpServletRequest, httpServletResponse,
                agreementForm, getPolicyAdminService(), getProductDevelopmentService(), roleDTO, getTopLevelAgreementContext());
    }

    public ActionForward viewConstantRolePlayer(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        AgreementForm agreementForm = (AgreementForm) actionForm;
        ObjectReference contextObjectReference = agreementForm.getContextObjectReference();
        String path = agreementForm.getPath();
        Assert.notNull(contextObjectReference, "The Agreement ObjectReference is required to view a Constant Role.");

        // TODO if we pass in the object reference, we won't need to look up the role
        ConstantRoleDTO constantRoleDTO = getPolicyAdminService().getConstantRoleView(getApplicationContext(),
                contextObjectReference, path);

        ViewActionHelper<AgreementForm> actionHelper = new ViewActionHelper<AgreementForm>();
        actionHelper.pushActionHelperDTO(agreementForm, httpServletRequest, path);

        return roleInActualStrategy.roleInActualViewCallback(actionMapping, httpServletRequest, httpServletResponse,
                agreementForm, getPolicyAdminService(), getProductDevelopmentService(), constantRoleDTO,
                getTopLevelAgreementContext());
    }

    /**
     * View a request
     */
    public ActionForward viewRequest(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        AgreementForm agreementForm = (AgreementForm) actionForm;

        ObjectReference requestObjectReference = agreementForm.getRequestObjectReference();
        Assert.notNull(requestObjectReference, "An ObjectReference is required to view the Request.");

        String forward = findForwardForRequest(agreementForm, httpServletRequest);
        ActionRedirect redirect = new ActionRedirect(actionMapping.findForward(forward));
        redirect.addParameter("contextObjectReference", requestObjectReference);
        return redirect;
    }

    @Override
    protected void preparePageForDisplay(AgreementForm form, HttpServletRequest request) {
        sortRoleListsAndRoles(form.getStructuredActualDTO());
    }
	
    /**
     * Find the correct forward based on the 'path' in order to determine if a component request or top level request is
     * being viewed.
     * 
     * In the case of a component request the agreement form is pushed onto the ViewActionHelper stack in order to track
     * back to the agreement from the request view.
     * 
     * @param agreementForm The current agreement form.
     * @param httpServletRequest The request
     * @return The input forward
     */
    private String findForwardForRequest(AgreementForm agreementForm, HttpServletRequest httpServletRequest) {
        String path = agreementForm.getPath();
        String forward = null;

        if (path == null || path.isEmpty()) {
            forward = "request";
        } else {
            forward = "selectComponentRequest";

            // This is required to return to the component agreement from which we clicked view request
            ViewActionHelper<AgreementForm> actionHelper = new ViewActionHelper<AgreementForm>();
            actionHelper.pushActionHelperDTO(agreementForm, httpServletRequest, agreementForm.getPath());
        }
        return forward;
    }

}
