/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.action;

import com.silvermoongroup.businessservice.policymanagement.dto.*;
import com.silvermoongroup.businessservice.policymanagement.enumeration.ElementType;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.agreementandrequest.action.intf.RoleSupportive;
import com.silvermoongroup.fsa.web.agreementandrequest.constants.TopLevelAgreementContextEnum;
import com.silvermoongroup.fsa.web.agreementandrequest.constants.ViewActionEnum;
import com.silvermoongroup.fsa.web.agreementandrequest.form.AgreementForm;
import com.silvermoongroup.fsa.web.agreementandrequest.helpers.ViewActionHelper;
import com.silvermoongroup.fsa.web.agreementandrequest.util.PathTraversalUtil;
import com.silvermoongroup.fsa.web.common.callback.CallBackAction;
import com.silvermoongroup.fsa.web.common.constant.CallbackConstants;
import com.silvermoongroup.fsa.web.dto.ActionHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.struts.action.*;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * This action handles Agreement navigation and traversals in modifiable state.
 * 
 * @author mubeen
 * @author Justin Walsh
 */
public class AgreementAction extends AbstractAgreementAction implements RoleSupportive {

    @Override
    public TopLevelAgreementContextEnum getTopLevelAgreementContext() {
        return TopLevelAgreementContextEnum.REQUEST;
    }

    @Override
    public ActionForward submit(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        AgreementForm agreementForm = (AgreementForm) actionForm;
        ObjectReference contextObjectRef = agreementForm.getContextObjectReference();
        Assert.notNull(contextObjectRef, "A 'Context ObjectReference' is required to navigate the Agreement.");

        String path = agreementForm.getPath();

        //------------evaluate Data Validity Rules ----------
        ObjectReference requestObjectReference = contextObjectRef;
        List<RuleResultDTO> failedRules = getPolicyAdminService().evaluateDataValityRule(
                getApplicationContext(), requestObjectReference, path, "Data Validity Rules"
        );
        ActionMessages actionMessages = new ActionMessages();
        Boolean dataValidationRulesPassed = true;
        for(RuleResultDTO failedRule : failedRules){
            actionMessages.add(
                    ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "page.agreement.ruleresult.failed", failedRule.getMessage()));
                                                          //"page.agreement.ruleresult.message.failure"));
            dataValidationRulesPassed = false;
        }

        if (dataValidationRulesPassed == false){

            saveErrorMessages(httpServletRequest, actionMessages);
            ActionRedirect redirect = new ActionRedirect(actionMapping.getPath()+".do");
            redirect.addParameter("contextObjectReference", contextObjectRef.toString());
            redirect.addParameter("path", path);
            return redirect;
        }
        //------------- end evaluate product eligibilityRules.

        if (StringUtils.isEmpty(path)) { // we are at the top level agreement

            // back to the request
            Long contextTypeId = contextObjectRef.getTypeId();
            Assert.notNull(contextTypeId, "Requires a context TypeId to determine the context Path");

            ActionRedirect redirect = new ActionRedirect("/secure/request.do");
            redirect.addParameter("contextObjectReference", contextObjectRef.toString());
            return redirect;
        } else {
            // We need to go up the path again
            path = PathTraversalUtil.trimTraversalPath(path, ElementType.Agreement);

            ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
            redirect.addParameter("contextObjectReference", contextObjectRef.toString());
            redirect.addParameter("path", path);
            return redirect;
        }
    }

    /**
     * @see com.silvermoongroup.fsa.web.agreementandrequest.action.AbstractViewAction#validateAndBindProperties(org.apache.struts.action.ActionMapping,
     *      com.silvermoongroup.fsa.web.agreementandrequest.form.AbstractViewForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward validateAndBindProperties(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        AgreementForm agreementForm = (AgreementForm) actionForm;
        agreementForm.setContextPath(actionMapping.getPath());

        String path = agreementForm.getPath();
        String initialMethod = agreementForm.getInitialMethod();

        if (initialMethod == null) {
            initialMethod = "Submit";
        }

        if (initialMethod.equalsIgnoreCase(".viewComponent") || initialMethod.equalsIgnoreCase(".deleteComponent")) {
            path = PathTraversalUtil.trimTraversalPath(path, ElementType.Agreement);
        } else if (!initialMethod.equals(".addRole") && initialMethod.contains("Role")) {
            path = PathTraversalUtil.trimTraversalPath(path, ElementType.Role);
        }

        AgreementDTO agreementDTO = getPolicyAdminService().getAgreementView(getApplicationContext(),
                agreementForm.getContextObjectReference(), path);
        agreementForm.setStructuredActualDTO(agreementDTO);

        return super.validateAndBindProperties(actionMapping, agreementForm, httpServletRequest, httpServletResponse);
    }

    public ActionForward back(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return submit(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    /**
     * Go up to the top level agreement, regardless of where we are in the 'tree'
     */
    public ActionForward backToTopLevelAgreement(ActionMapping actionMapping, ActionForm actionForm,
                              HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        AgreementForm agreementForm = (AgreementForm) actionForm;
        ObjectReference contextObjectRef = agreementForm.getContextObjectReference();
        Assert.notNull(contextObjectRef, "A 'Context ObjectReference' is required to navigate the Agreement.");

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", contextObjectRef.toString());
        return redirect;
    }

    /* Utility Methods */

    @Override
    protected void handlePropertyValidationFailurePath(ActionForm actionForm) {
        AgreementForm agreementForm = (AgreementForm) actionForm;

        String path = agreementForm.getPath();
        String initialMethod = agreementForm.getInitialMethod();

        if (!StringUtils.isEmpty(path)) {
            if (initialMethod.equalsIgnoreCase(".updateComponent")) {
                path = PathTraversalUtil.trimTraversalPath(path, ElementType.Agreement);
            } else if (!initialMethod.equals(".addRole") && initialMethod.contains("Role")) {
                path = PathTraversalUtil.trimTraversalPath(path, ElementType.Role);
            }
            agreementForm.setPath(path);
        }
    }

    @Override
    protected void saveStructuredActual(AgreementForm form, String initialMethod) {
        String path = form.getPath();
        initialMethod = StringUtils.trimToEmpty(initialMethod);

        if (initialMethod.equalsIgnoreCase(".viewComponent") || initialMethod.equalsIgnoreCase(".deleteComponent")) {
            path = PathTraversalUtil.trimTraversalPath(path, ElementType.Agreement);
        } else if (!initialMethod.equals(".addRole") && initialMethod.contains("Role")) {
            path = PathTraversalUtil.trimTraversalPath(path, ElementType.Role);
        }

        if (!initialMethod.equalsIgnoreCase("back")) {
            OnlyActualDTO onlyActual = getPolicyAdminService().updateAgreementProperties(getApplicationContext(),
                    form.getContextObjectReference(), form.getStructuredActualDTO().getProperties(), true, path);
            form.setStructuredActualDTO((AgreementDTO) onlyActual);
        }
    }

    /* CallBack Handlers */

    @Override
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

        ViewActionHelper<AgreementForm> viewActionHelper = new ViewActionHelper<AgreementForm>();
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
                    contextObjectReference, roleKindDTO, rolePlayerObjectReference, Boolean.TRUE, null, path);

            if (!roleDTO.getProperties().isEmpty()
                    && getApplicationProperties().displayRolePropertiesOnCallBack(roleKindDTO.getId())) {

                ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("roleProperties"));
                redirect.addParameter("contextObjectReference", contextObjectReference.toString());
                redirect.addParameter("contextPath", actionMapping.getPath());
                redirect.addParameter("path", roleDTO.getPath());
                redirect.addParameter("tlaRelativePath", getTopLevelAgreementContext().equals(TopLevelAgreementContextEnum.REQUEST));

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

        ViewActionHelper<AgreementForm> viewActionHelper = new ViewActionHelper<AgreementForm>();
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
                    contextObjectReference, rolePlayerObjectReference, roleKindDTO, Boolean.TRUE, path);
            
            addInformationMessage(httpServletRequest, "page.request.message.roleplayeradded", roleKindDTO.getName());

            if (!roleDTO.getProperties().isEmpty()
                    && getApplicationProperties().displayRolePropertiesOnCallBack(roleKindDTO.getId())) {

                ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("roleProperties"));
                redirect.addParameter("contextObjectReference", contextObjectReference.toString());
                redirect.addParameter("contextPath", actionMapping.getPath());
                redirect.addParameter("path", roleDTO.getPath());
                redirect.addParameter("tlaRelativePath", getTopLevelAgreementContext().equals(TopLevelAgreementContextEnum.REQUEST));

                return redirect;
            }
        }

        // redirect to display the page which has the role
        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", contextObjectReference);
        redirect.addParameter("path", path);

        return redirect;
    }

    /* Component Facilitators */
    /**
     * Handle a REST POST action against a componentList
     * 
     * @param actionMapping
     * @param actionForm
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     *     possible object is
     *     {@link ActionForward }
     */
    public ActionForward addComponent(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        AgreementForm agreementForm = (AgreementForm) actionForm;
        ObjectReference requestObjectRef = agreementForm.getContextObjectReference();
        String kindString = agreementForm.getKind();
        String path = agreementForm.getPath();

        Assert.notNull(requestObjectRef, "The Agreement ObjectReference is required to add a Component.");
        Assert.notNull(kindString, "The Component Kind is required to add a Component.");

        KindDTO kind = KindDTO.convertFromString(kindString);
        
        addInformationMessage(httpServletRequest, "page.agreement.message.componentadded", kind.getName());

        getPolicyAdminService().addComponentToOnlyActual(getApplicationContext(), requestObjectRef, kind, Boolean.TRUE,
                path);

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", requestObjectRef.toString());
        redirect.addParameter("path", path);

        return redirect;
    }

    /**
     * Delete a component. Once deleted we display the parent component.
     */
    public ActionForward deleteComponent(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        AgreementForm agreementForm = (AgreementForm) actionForm;

        ObjectReference requestObjectRef = agreementForm.getContextObjectReference();
        String componentKind = agreementForm.getKind();
        Assert.notNull(requestObjectRef, "The Agreement ObjectReference is required to delete a Component.");
        Assert.notNull(componentKind, "The component kind is required to delete a Component.");
        String componentPath = agreementForm.getPath();

        getPolicyAdminService().removeComponentFromOnlyActual(getApplicationContext(), requestObjectRef, Boolean.TRUE,
                null, componentPath);

        String parentPath = PathTraversalUtil.trimTraversalPath(componentPath, ElementType.Agreement);
        
        KindDTO componentKindDTO = KindDTO.convertFromString(componentKind);
        addInformationMessage(httpServletRequest, "page.agreement.message.componentdeleted", componentKindDTO.getName());

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", requestObjectRef.toString());
        redirect.addParameter("path", parentPath);

        return redirect;
    }

    /* Role In Actual Facilitators */
    @Override
    public ActionForward addRole(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        AgreementForm agreementForm = (AgreementForm) actionForm;
        ObjectReference contextObjectReference = agreementForm.getContextObjectReference();
        String kindString = agreementForm.getKind();
        String path = agreementForm.getPath();
        Assert.notNull(contextObjectReference, "The Agreement ObjectReference is required to add a Role.");
        Assert.notNull(kindString, "The Role Kind is required to add a Role.");

        ViewActionHelper<AgreementForm> actionHelper = new ViewActionHelper<AgreementForm>();
        actionHelper.pushActionHelperDTO(agreementForm, httpServletRequest, path);

        return roleInActualStrategy.roleInActualAddCallback(getApplicationContext(), actionMapping, httpServletRequest, httpServletResponse,
                agreementForm);
    }

    /**
     * Delete a role player from this agreement.
     */
    @Override
    public ActionForward deleteRolePlayer(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        AgreementForm agreementForm = (AgreementForm) actionForm;

        ObjectReference requestObjectRef = agreementForm.getContextObjectReference();
        String path = agreementForm.getPath();
        String kind = agreementForm.getKind();
        Assert.notNull(requestObjectRef, "The Agreement ObjectReference is required to delete a Role.");
        Assert.notNull(kind, "A Role kind is required to delete a Role.");

        getPolicyAdminService().removeRoleFromOnlyActual(getApplicationContext(), requestObjectRef, Boolean.TRUE, null,
                path);
        
        KindDTO roleKindDTO = KindDTO.convertFromString(kind);
        addInformationMessage(httpServletRequest, "page.request.message.roleplayerdeleted", roleKindDTO.getName());
        
        
        String componentPath = PathTraversalUtil.trimTraversalPath(path, ElementType.Role);

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", requestObjectRef.toString());
        redirect.addParameter("path", componentPath);

        return redirect;
    }

    @Override
    public ActionForward updateRoleProperties(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        AgreementForm agreementForm = (AgreementForm) actionForm;
        ObjectReference contextObjectReference = agreementForm.getContextObjectReference();
        String path = agreementForm.getPath();

        Assert.notNull(contextObjectReference,
                "The Agreement ObjectReference is required to update the Role Properties.");
        Assert.notNull(path, "A path is required to navigate to Role for Property updates.");

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("roleProperties"));
        redirect.addParameter("contextObjectReference", contextObjectReference.toString());
        redirect.addParameter("contextPath", actionMapping.getPath());
        redirect.addParameter("path", path);
        redirect.addParameter("tlaRelativePath", getTopLevelAgreementContext().equals(TopLevelAgreementContextEnum.REQUEST));
        
        return redirect;
    }
}
