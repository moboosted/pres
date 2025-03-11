/**
 ** Licensed Materials
 ** (C) Copyright Silvermoon Business Systems BVBA, Belgium
 ** (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 ** All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.action;

import com.silvermoongroup.businessservice.policymanagement.dto.*;
import com.silvermoongroup.businessservice.policymanagement.enumeration.ElementType;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.agreementandrequest.constants.TopLevelAgreementContextEnum;
import com.silvermoongroup.fsa.web.agreementandrequest.constants.ViewActionEnum;
import com.silvermoongroup.fsa.web.agreementandrequest.form.RequestViewForm;
import com.silvermoongroup.fsa.web.agreementandrequest.helpers.RoleInActualStrategy;
import com.silvermoongroup.fsa.web.agreementandrequest.helpers.ViewActionHelper;
import com.silvermoongroup.fsa.web.agreementandrequest.util.PathTraversalUtil;
import com.silvermoongroup.fsa.web.common.callback.CallBackAction;
import com.silvermoongroup.fsa.web.common.constant.CallbackConstants;
import com.silvermoongroup.fsa.web.dto.ActionHelper;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import com.silvermoongroup.spflite.specframework.common.exceptions.RequestAlreadyInProgressException;
import org.apache.commons.lang3.Validate;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public abstract class BaseRequestViewAction extends AbstractViewAction<RequestViewForm> {

    private RoleInActualStrategy<RequestViewForm> roleInActualStrategy;

    @Override
    protected void onInit() {
        super.onInit();
        setupRoleInActualStrategy();
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = super.getKeyMethodMap();
        map.put("page.request.action.cancel", "cancel");
        map.put("action.handleComponentRequestCallBack", "handleComponentRequestCallBack");
        return map;
    }

    @Override
    public ActionForward submit(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        throw new UnsupportedOperationException("Submit is not supported in " + getClass().getName());
    }

    public ActionForward validateAndBindProperties(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        RequestViewForm requestViewForm = (RequestViewForm) actionForm;
        RequestDTO requestDTO = getPolicyAdminService().getRequestView(getApplicationContext(),
                requestViewForm.getContextObjectReference());
        requestViewForm.setStructuredActualDTO(requestDTO);
        requestViewForm.setAgreementDTO(requestDTO.getAgreement());

        // by this time the date should be validated
        requestDTO.setEffectiveDate(new DateTime(parseDate(requestViewForm.getEffectiveDate())));

        return validateAndBindProperties(actionMapping, (RequestViewForm) actionForm, httpServletRequest,
                httpServletResponse);
    }

    public ActionForward viewRolePlayer(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        RequestViewForm requestViewForm = (RequestViewForm) actionForm;
        ObjectReference contextObjectReference = requestViewForm.getContextObjectReference();
        String path = requestViewForm.getPath();
        Assert.notNull(contextObjectReference, "The Request ObjectReference is required to view a Role.");

        // TODO if we pass in the object reference, we won't need to look up the role
        RoleDTO roleDTO = getPolicyAdminService().getRoleView(getApplicationContext(), contextObjectReference, false,
                path);

        ViewActionHelper<RequestViewForm> actionHelper = new ViewActionHelper<RequestViewForm>();
        actionHelper.pushActionHelperDTO(requestViewForm, httpServletRequest, path);

        return roleInActualStrategy.roleInActualViewCallback(actionMapping, httpServletRequest, httpServletResponse,
                requestViewForm, getPolicyAdminService(), getProductDevelopmentService(), roleDTO,
                TopLevelAgreementContextEnum.REQUEST);
    }

    public ActionForward viewRoleProperties(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        RequestViewForm agreementForm = (RequestViewForm) actionForm;
        ObjectReference contextObjectReference = agreementForm.getContextObjectReference();
        String path = agreementForm.getPath();

        Assert.notNull(contextObjectReference, "A ContextObjectReference is required to view the Role Properties.");
        Assert.notNull(path, "A path is required to navigate to the Role Properties.");

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("roleProperties"));
        redirect.addParameter("contextObjectReference", contextObjectReference.toString());
        redirect.addParameter("contextPath", actionMapping.getPath());
        redirect.addParameter("path", path);
        redirect.addParameter("tlaRelativePath", "false");
        return redirect;
    }

        /* Utility Methods */

    @Override
    protected void handlePropertyValidationFailurePath(ActionForm actionForm) {
        RequestViewForm requestViewForm = (RequestViewForm) actionForm;
        ObjectReference contextObjectReference = requestViewForm.getContextObjectReference();
        Assert.notNull(contextObjectReference, "The Request ObjectReference is required to navigate the Agreement.");

        ApplicationContext applicationContext = getApplicationContext();
        RequestDTO requestDTO = getPolicyAdminService().getRequestView(applicationContext, contextObjectReference);
        requestViewForm.setStructuredActualDTO(requestDTO);
    }

    @Override
    protected boolean isStructuredActualDirty(HttpServletRequest request, RequestViewForm form) {
        return true; // the requested date could have changed
    }

    @Override
    protected void saveStructuredActual(RequestViewForm form, String initialMethod) {
        if (form.getInitialUrl() != null || // we have clicked a link - always save
                (!(initialMethod.equalsIgnoreCase("back")) && !(initialMethod.equalsIgnoreCase("cancel"))
                        && !initialMethod.equalsIgnoreCase("authorise") && !initialMethod.equalsIgnoreCase("decline")
                        && !initialMethod
                        .equalsIgnoreCase("override"))) {

            RequestDTO requestDTO = getPolicyAdminService().updateRequest(getApplicationContext(),
                    form.getStructuredActualDTO());
            form.setStructuredActualDTO(requestDTO);
        }
    }

    /* CallBack Handlers */
    public ActionForward handleRoleCallBack(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        CallBackAction action = CallBackAction.valueOf(httpServletRequest.getParameter(CallbackConstants.ACTION));
        if (action == CallBackAction.ADD) {
            return handleAddRoleCallBack(actionMapping, actionForm, httpServletRequest, httpServletResponse);
        } else if (action == CallBackAction.UPDATE) {
            return handleUpdateRoleCallBack(actionMapping, actionForm, httpServletRequest, httpServletResponse);
        } else {
            throw new IllegalStateException("Unknown action: " + action);
        }
    }

    protected ActionForward handleUpdateRoleCallBack(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        ViewActionHelper<RequestViewForm> viewActionHelper = new ViewActionHelper<RequestViewForm>();
        ActionHelper actionHelperDTO = viewActionHelper.popActionHelperDTO(httpServletRequest);
        Assert.notNull(actionHelperDTO, "Requires ActionHelper to update the Role.");

        // The Path needs to come with the submission eg: Role(Kind)[id]
        String path = actionHelperDTO.getPath();
        ObjectReference contextObjectReference = actionHelperDTO.getContextObjectReference();
        ObjectReference rolePlayerObjectReference = (ObjectReference) httpServletRequest
                .getAttribute(ViewActionEnum.ROLE_PLAYER_CALLBACK_KEY.getName());

        // an update without an object reference is equivalent to just going 'back'
        if (rolePlayerObjectReference != null) {

            KindDTO roleKindDTO = actionHelperDTO.getKindDTO();

            RoleDTO roleDTO = getPolicyAdminService().updateRoleInOnlyActual(getApplicationContext(),
                    contextObjectReference, roleKindDTO, rolePlayerObjectReference, Boolean.FALSE, null, path);

            if (!roleDTO.getProperties().isEmpty()
                    && getApplicationProperties().displayRolePropertiesOnCallBack(roleKindDTO.getId())) {

                ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("roleProperties"));
                redirect.addParameter("contextObjectReference", contextObjectReference.toString());
                redirect.addParameter("contextPath", actionMapping.getPath());
                redirect.addParameter("path", roleDTO.getPath());
                redirect.addParameter("tlaRelativePath", "false");
                return redirect;
            }
        }

        // redirect to display the page which has the role
        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", contextObjectReference);
        redirect.addParameter("path", PathTraversalUtil.trimTraversalPath(path, ElementType.Role));

        return redirect;
    }

    protected ActionForward handleAddRoleCallBack(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        ViewActionHelper<RequestViewForm> viewActionHelper = new ViewActionHelper<RequestViewForm>();
        ActionHelper actionHelperDTO = viewActionHelper.popActionHelperDTO(httpServletRequest);
        Assert.notNull(actionHelperDTO, "Requires ActionHelper to update the Role.");

        // in this case the path is the path of the actual that the role is being added to
        String path = actionHelperDTO.getPath();
        ObjectReference contextObjectReference = actionHelperDTO.getContextObjectReference();

        ObjectReference rolePlayerObjectReference = (ObjectReference) httpServletRequest
                .getAttribute(ViewActionEnum.ROLE_PLAYER_CALLBACK_KEY.getName());

        if (rolePlayerObjectReference != null) {

            KindDTO roleKindDTO = actionHelperDTO.getKindDTO();
            RoleDTO roleDTO = getPolicyAdminService().addRoleToOnlyActual(getApplicationContext(),
                    contextObjectReference, rolePlayerObjectReference, roleKindDTO, Boolean.FALSE, path);

            addInformationMessage(httpServletRequest, "page.agreement.message.roleplayeradded", roleKindDTO.getName());

            if (!roleDTO.getProperties().isEmpty()
                    && getApplicationProperties().displayRolePropertiesOnCallBack(roleKindDTO.getId())) {

                ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("roleProperties"));
                redirect.addParameter("contextObjectReference", contextObjectReference.toString());
                redirect.addParameter("contextPath", actionMapping.getPath());
                redirect.addParameter("path", roleDTO.getPath());
                redirect.addParameter("tlaRelativePath", "false");
                return redirect;
            }
        }

        // redirect to display the page which has the role
        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", contextObjectReference);
        redirect.addParameter("path", path);

        return redirect;
    }

    /* Role In Actual Facilitators */

    public ActionForward addRole(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        RequestViewForm requestViewForm = (RequestViewForm) actionForm;
        ObjectReference contextObjectReference = requestViewForm.getContextObjectReference();
        String kindString = requestViewForm.getKind();
        String path = requestViewForm.getPath();
        Assert.notNull(contextObjectReference, "The Request ObjectReference is required to add a Role.");
        Assert.notNull(kindString, "The Role Kind is required to add a Role.");

        ViewActionHelper<RequestViewForm> actionHelper = new ViewActionHelper<RequestViewForm>();
        actionHelper.pushActionHelperDTO(requestViewForm, httpServletRequest, path);

        return roleInActualStrategy.roleInActualAddCallback(getApplicationContext(), actionMapping, httpServletRequest, httpServletResponse,
                requestViewForm);
    }

    public ActionForward deleteRolePlayer(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        RequestViewForm requestViewForm = (RequestViewForm) actionForm;

        ObjectReference contextObjectReference = requestViewForm.getContextObjectReference();
        String path = requestViewForm.getPath();
        String kind = requestViewForm.getKind();
        Assert.notNull(contextObjectReference, "The Request ObjectReference is required to delete a Role.");
        Assert.notNull(kind, "A Role kind is required to delete a Role.");

        RequestDTO requestDTO = (RequestDTO) getPolicyAdminService().removeRoleFromOnlyActual(getApplicationContext(),
                contextObjectReference, Boolean.FALSE, null, path);

        KindDTO roleKindDTO = KindDTO.convertFromString(kind);
        addInformationMessage(httpServletRequest, "page.agreement.message.roleplayerdeleted", roleKindDTO.getName());

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", requestDTO.getObjectReference());
        return redirect;
    }

    public ActionForward updateRoleProperties(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        RequestViewForm requestViewForm = (RequestViewForm) actionForm;

        ObjectReference contextObjectReference = requestViewForm.getContextObjectReference();
        String path = requestViewForm.getPath();

        Assert.notNull(contextObjectReference,
                "The Request ObjectReference is required to update the Role Properties.");
        Assert.notNull(path, "A path is required to navigate to Role for Property updates.");

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("roleProperties"));
        redirect.addParameter("contextObjectReference", contextObjectReference.toString());
        redirect.addParameter("contextPath", actionMapping.getPath());
        redirect.addParameter("path", path);
        return redirect;
    }

    /**
     * Entry point for display of a request.
     * <p>
     * A contextObjectReference (the request object reference) is required.
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        RequestViewForm requestViewForm = (RequestViewForm) actionForm;
        RequestDTO requestDTO = populateForm(requestViewForm, httpServletRequest);

        // we wish to represent the effective date/time as a DATE, ignoring the time component for the GUI
        requestViewForm.setEffectiveDate(formatDate(requestDTO.getEffectiveDate() == null ? null : requestDTO
                .getEffectiveDate().getDate()));
        requestViewForm.setContextPath(actionMapping.getPath());

        preparePageForDisplay(requestViewForm, httpServletRequest);
        return actionMapping.getInputForward();
    }

    protected RequestDTO populateForm(RequestViewForm requestViewForm, HttpServletRequest httpServletRequest) {
        ObjectReference contextObjectReference = requestViewForm.getContextObjectReference();
        ApplicationContext applicationContext = getApplicationContext();

        RequestDTO requestDTO = getRequestDTO(applicationContext, contextObjectReference, httpServletRequest);
        Assert.notNull(requestDTO);
        requestViewForm.setStructuredActualDTO(requestDTO);
        requestViewForm.setAgreementDTO(requestDTO.getAgreement());
        return requestDTO;
    }

    /**
     * Used to create a request from an agreement. TODO: Own action?
     */
    public ActionForward createRequest(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String path = httpServletRequest.getParameter("path");
        String contextObjRefString = httpServletRequest.getParameter("context");
        ObjectReference contextObjectReference = ObjectReference.convertFromString(contextObjRefString);

        String kindString = httpServletRequest.getParameter("requestKind");
        KindDTO requestKind = KindDTO.convertFromString(kindString);

        Assert.notNull(contextObjectReference, "Unable to create a Request without an Agreement ObjectReference");
        Assert.notNull(requestKind, "Request Kind required to create the Request");

        RequestDTO requestDTO;
        AgreementDTO agreement = getPolicyAdminService().getAgreementView(getApplicationContext(),
                contextObjectReference, path);

        if (TopLevelAgreementDTO.class.isAssignableFrom(agreement.getClass())) {
            try {
                requestDTO = getPolicyAdminService().createRequest(getApplicationContext(),
                        agreement.getObjectReference(), requestKind);
            } catch (RequestAlreadyInProgressException exception) {
                // Add an information message with a hyperlink to the request in progress.
                ActionRedirect requestRedirect = new ActionRedirect(actionMapping.findForward("agreementGUI"));
                requestRedirect.addParameter("requestObjectReference", exception.getRequest());
                requestRedirect.addParameter("method", ".viewRequest");
                addInformationMessage(httpServletRequest, "page.request.message.requestalreadyinprogress",
                        exception.getRequestKind().getName(), formatKind(requestKind));

                ActionRedirect redirect = new ActionRedirect(actionMapping.findForward("agreementGUI"));
                redirect.addParameter("contextObjectReference", contextObjectReference);
                return redirect;
            }
            
        } else {
            return createComponentRequest(actionMapping, actionForm, httpServletRequest, httpServletResponse,
                    agreement, contextObjectReference, requestKind, path);
        }

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", requestDTO.getObjectReference());
        return redirect;
    }

    public ActionForward createComponentRequest(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AgreementDTO agreement,
            ObjectReference contextObjectReference, KindDTO requestKind, String path) {
        Assert.notNull(agreement.getObjectReference(), "Unable to create a Request without an Agreement ObjectReference");
        Assert.notNull(requestKind, "Request Kind required to create the Request");
        Assert.notNull(path, "Path is required to be able to traverse back to Agreement.");

        RequestViewForm requestViewForm = (RequestViewForm) actionForm;
        requestViewForm.setContextObjectReference(contextObjectReference);
        requestViewForm.setKind(requestKind.toString());
        requestViewForm.setPath(path);

        ViewActionHelper<RequestViewForm> actionHelper = new ViewActionHelper<RequestViewForm>();
        actionHelper.pushActionHelperDTO(requestViewForm, httpServletRequest, path);
        RequestDTO requestDTO = getPolicyAdminService().createRequestForComponent(getApplicationContext(),
                agreement.getObjectReference(), requestKind);

        ActionRedirect redirect = new ActionRedirect("/secure/crequest.do");
        redirect.addParameter("contextObjectReference", requestDTO.getObjectReference());
        return redirect;
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm form, HttpServletRequest request) {
        RequestViewForm requestViewForm = (RequestViewForm) form;
        populateForm(requestViewForm, request);
        preparePageForDisplay(requestViewForm, request);
    }

    public abstract void setupRoleInActualStrategy();

    protected abstract RequestDTO getRequestDTO(ApplicationContext applicationContext,
            ObjectReference contextObjectReference, HttpServletRequest httpServletRequest);

    @Override
    protected void preparePageForDisplay(RequestViewForm form, HttpServletRequest request) {
        sortRoleListsAndRoles(form.getStructuredActualDTO());
    }

    public RoleInActualStrategy<RequestViewForm> getRoleInActualStrategy() {
        return roleInActualStrategy;
    }

    public void setRoleInActualStrategy(RoleInActualStrategy<RequestViewForm> roleInActualStrategy) {
        this.roleInActualStrategy = roleInActualStrategy;
    }

    public ActionForward handleComponentRequestCallBack(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        ViewActionHelper<RequestViewForm> viewActionHelper = new ViewActionHelper<RequestViewForm>();
        ActionHelper actionHelperDTO = viewActionHelper.popActionHelperDTO(httpServletRequest);
        Assert.notNull(actionHelperDTO, "Requires ActionHelper on completion of creating a Component Request");

        // redirect to agreement from which we created the component request
        ActionRedirect redirect = new ActionRedirect("/secure/request/tla.do");
        redirect.addParameter("contextObjectReference", actionHelperDTO.getContextObjectReference());
        redirect.addParameter("path", actionHelperDTO.getPath());
        return redirect;
    }
}
