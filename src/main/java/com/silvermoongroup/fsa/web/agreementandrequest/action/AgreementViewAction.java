/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.action;

import com.silvermoongroup.businessservice.policymanagement.dto.AgreementDTO;
import com.silvermoongroup.businessservice.policymanagement.enumeration.ElementType;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.agreementandrequest.constants.TopLevelAgreementContextEnum;
import com.silvermoongroup.fsa.web.agreementandrequest.form.AgreementForm;
import com.silvermoongroup.fsa.web.agreementandrequest.helpers.ViewActionHelper;
import com.silvermoongroup.fsa.web.agreementandrequest.util.PathTraversalUtil;
import com.silvermoongroup.fsa.web.common.callback.CallBack;
import com.silvermoongroup.fsa.web.common.callback.CallBackUtility;
import com.silvermoongroup.fsa.web.dto.ActionHelper;
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
 * This action handles the navigation and traversals of Agreements in a VIEW ONLY mode. It allows the ability to Raise
 * new Requests
 * 
 * @author mubeen
 */
public class AgreementViewAction extends AbstractAgreementAction {

    public AgreementViewAction() {
    }

    @Override
    public TopLevelAgreementContextEnum getTopLevelAgreementContext() {
        return TopLevelAgreementContextEnum.AGREEMENT;
    }
    
    public ActionForward viewAnchorAgreementPart(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        AgreementForm agreementForm = (AgreementForm) actionForm;

        // Versioned Agreement Object Reference
        ObjectReference contextObjectReference = agreementForm.getContextObjectReference();
        
        // Anchor Object Reference for Agreement Part
        ObjectReference anchorObjectReference = agreementForm.getRelativeAgreementPartObjectReference();
        AgreementDTO agreement  = getPolicyAdminService().findLatestLegallyBindingAgreementForAgreementAnchor(getApplicationContext(), anchorObjectReference);
        if (agreement == null){
            agreement = getPolicyAdminService().findLatestNonLegallyBindingAgreementForAgreementAnchor(getApplicationContext(), anchorObjectReference);
        }
        
        AgreementDTO agreementDTO = getPolicyAdminService().getAgreementView(getApplicationContext(), contextObjectReference, agreement.getObjectReference());
        StringBuilder agreementTraversalPath = buildAgreementTraversalPath(agreementDTO, contextObjectReference);

        ActionRedirect actionRedirect = new ActionRedirect(actionMapping.getPath() + ".do");
        actionRedirect.addParameter("path", agreementTraversalPath.toString());
        actionRedirect.addParameter("contextObjectReference", contextObjectReference);

        return actionRedirect;
    }
    
    public ActionForward viewAgreementPart(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        AgreementForm agreementForm = (AgreementForm) actionForm;

        // Versioned Agreement Object Reference
        ObjectReference contextObjectReference = agreementForm.getContextObjectReference();
        
        // Anchor Object Reference for Agreement Part
        ObjectReference agreementPartObjectReference = agreementForm.getRelativeAgreementPartObjectReference();
        
        AgreementDTO agreementDTO = getPolicyAdminService().getAgreementView(getApplicationContext(), contextObjectReference, agreementPartObjectReference);
        StringBuilder agreementTraversalPath = buildAgreementTraversalPath(agreementDTO, contextObjectReference);

        ActionRedirect actionRedirect = new ActionRedirect(actionMapping.getPath() + ".do");
        actionRedirect.addParameter("path", agreementTraversalPath.toString());
        actionRedirect.addParameter("contextObjectReference", contextObjectReference);

        return actionRedirect;
    }

    /**
     * In order for the user to traverse the agreement hierarchy we need to build up the agreement path.
     */
    private StringBuilder buildAgreementTraversalPath(AgreementDTO agreementDTO, ObjectReference agreementVersionContext){
        StringBuilder sb = new StringBuilder();
        while (agreementDTO.getCompositeObjectReference() != null) {
            AgreementDTO parentAgreement = getPolicyAdminService().getAgreementView(getApplicationContext(),
                    agreementVersionContext, agreementDTO.getCompositeObjectReference());
            sb = PathTraversalUtil.generateTraversalPath(sb, parentAgreement.getComponentLists(), agreementDTO);
            agreementDTO = parentAgreement;
        }
        return sb;
    }
    
    @Override
    public ActionForward submit(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        throw new UnsupportedOperationException("The Submit function is not supported for: " + getClass().getName());
    }

    /**
     * @see com.silvermoongroup.fsa.web.agreementandrequest.action.AbstractViewAction#validateAndBindProperties(org.apache.struts.action.ActionMapping,
     *      com.silvermoongroup.fsa.web.agreementandrequest.form.AbstractViewForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward validateAndBindProperties(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        throw new UnsupportedOperationException("The ValidateAndBindProperties function is not supported for: "
                + getClass().getName());
    }

    /**
     * Go one level up in the agreement tree
     */
    public ActionForward back(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        AgreementForm agreementForm = (AgreementForm) actionForm;

        ObjectReference topLevelAgreementObjectRef = agreementForm.getContextObjectReference();
        Assert.notNull(topLevelAgreementObjectRef,
                "The TopLevelAgreement ObjectReference is required to navigate the Agreement.");

        String path = agreementForm.getPath();

        if (StringUtils.isEmpty(path)) {
            CallBack callBack = CallBackUtility.getCallBack(httpServletRequest, httpServletResponse);
            if (callBack != null) {
                return CallBackUtility.getForwardAction(callBack);
            }

            throw new IllegalStateException("Trying to go back at the top of the agreement tree");
        }

        path = PathTraversalUtil.trimTraversalPath(path, ElementType.Agreement);

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", topLevelAgreementObjectRef.toString());
        redirect.addParameter("path", path);
        return redirect;
    }

    /**
     * Go up to the top level agreement, regardless of where we are in the 'tree'
     */
    public ActionForward backToTopLevelAgreement(ActionMapping actionMapping, ActionForm actionForm,
                              HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        AgreementForm agreementForm = (AgreementForm) actionForm;

        ObjectReference topLevelAgreementObjectRef = agreementForm.getContextObjectReference();
        Assert.notNull(topLevelAgreementObjectRef,
                "The TopLevelAgreement ObjectReference is required to navigate the Agreement.");

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", topLevelAgreementObjectRef.toString());
        redirect.addParameter("path", "");
        return redirect;
    }

    /* Utility Methods */

    @Override
    protected void handlePropertyValidationFailurePath(ActionForm actionForm) {
    }

    @Override
    protected void saveStructuredActual(AgreementForm form, String initialMethod) {
    }

    /* CallBack Handlers */
    public ActionForward handleRoleCallBack(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        // The Path needs to come with the submission eg: Role(Kind)[id]
        // Id needs to be stored role needs to be updated accordingly
        AgreementForm agreementForm = (AgreementForm) actionForm;
        ViewActionHelper<AgreementForm> viewActionHelper = new ViewActionHelper<AgreementForm>();
        ActionHelper actionHelperDTO = viewActionHelper.popActionHelperDTO(httpServletRequest);
        Assert.notNull(actionHelperDTO, "Requires ActionHelper to update the Role.");

        ObjectReference contextObjectReference = actionHelperDTO.getContextObjectReference();
        String path = PathTraversalUtil.trimTraversalPath(actionHelperDTO.getPath(), ElementType.Role);

        ActionRedirect actionRedirect = new ActionRedirect(actionMapping.getPath() + ".do");
        actionRedirect.addParameter("path", path);
        actionRedirect.addParameter("contextObjectReference", contextObjectReference);

        return actionRedirect;
    }

    public ActionForward handleComponentRequestCallBack(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        ViewActionHelper<AgreementForm> viewActionHelper = new ViewActionHelper<AgreementForm>();
        ActionHelper actionHelperDTO = viewActionHelper.popActionHelperDTO(httpServletRequest);
        Assert.notNull(actionHelperDTO, "Requires ActionHelper on completion of creating a Component Request");

        // redirect to agreement from which we created the component request
        ActionRedirect redirect = new ActionRedirect(actionMapping.findForward("agreementGUI"));
        redirect.addParameter("contextObjectReference", actionHelperDTO.getContextObjectReference());
        redirect.addParameter("path", actionHelperDTO.getPath());
        return redirect;
    }

}
