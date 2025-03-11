/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.action;

import com.silvermoongroup.businessservice.policymanagement.dto.RequestDTO;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.agreementandrequest.form.RequestViewForm;
import com.silvermoongroup.fsa.web.agreementandrequest.helpers.ComponentRequestRoleInActualStrategy;
import com.silvermoongroup.fsa.web.agreementandrequest.helpers.ViewActionHelper;
import com.silvermoongroup.fsa.web.dto.ActionHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This action class is responsible for the management (view, update) of a component request.
 * 
 */
public class ComponentRequestViewAction extends BaseRequestViewAction {

    /**
     * Done button has been clicked. We can either raise and execute the request or just raise it in the event of
     * authorization requirements.
     * 
     */
    public ActionForward done(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String path = (inRequestContext(httpServletRequest)) ? "request" :  "agreementGUI";
        
        ActionRedirect redirect = new ActionRedirect(actionMapping.findForward(path));
        redirect.addParameter("method", ".handleComponentRequestCallBack");
        return redirect;
    }



    // Overriding RequestViewAction implementations
    public void setupRoleInActualStrategy() {
        setRoleInActualStrategy((ComponentRequestRoleInActualStrategy) getBean("ComponentRequestStrategy"));
    }

    protected RequestDTO getRequestDTO(ApplicationContext applicationContext, ObjectReference contextObjectReference, HttpServletRequest httpServletRequest) {
        return getPolicyAdminService().getComponentRequestView(applicationContext, contextObjectReference, !(inRequestContext(httpServletRequest)));
    }

    /**
     * Utility method to determine if we have navigated to this component request within the context of a
     * created(raised) request or within the context of an AgreementVersion
     * 
     * @param httpServletRequest
     * @return boolean indicating whether we are in the context of a created(raised) request
     */
    private boolean inRequestContext(HttpServletRequest httpServletRequest) {
        ApplicationContext applicationContext = new ApplicationContext();
        ViewActionHelper<RequestViewForm> viewActionHelper = new ViewActionHelper<RequestViewForm>();
        ActionHelper actionHelper = viewActionHelper.peekAtActionHelperDTO(httpServletRequest);

        ObjectReference contextObjectReference = actionHelper.getContextObjectReference();
        Long typeReferenceId = contextObjectReference.getTypeId();
        Type type = getProductDevelopmentService().getType(applicationContext, typeReferenceId);
        Type requestType = getProductDevelopmentService().getType(applicationContext,
                CoreTypeReference.REQUEST.getValue());

        return getProductDevelopmentService().isSameOrSubType(applicationContext, requestType.getId(), type.getId());
    }

}
