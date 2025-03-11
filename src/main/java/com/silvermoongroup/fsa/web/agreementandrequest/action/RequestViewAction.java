/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.action;

import com.silvermoongroup.businessservice.policymanagement.dto.RequestDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.RuleResultDTO;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.agreementandrequest.action.intf.RoleSupportive;
import com.silvermoongroup.fsa.web.agreementandrequest.form.RequestViewForm;
import com.silvermoongroup.fsa.web.agreementandrequest.helpers.RequestRoleInActualStrategy;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * This action class is responsible for the management (view, update) of a request.
 * 
 */
public class RequestViewAction extends BaseRequestViewAction implements RoleSupportive {

    /**
     * Done button has been clicked. We can either raise and execute the request or just raise it in the event of
     * authorization requirements.
     */
    public ActionForward done(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        RequestViewForm requestViewForm = (RequestViewForm) actionForm;
        ObjectReference requestObjectReference = requestViewForm.getContextObjectReference();
        ApplicationContext applicationContext = getApplicationContext();
        ActionMessages actionMessages = new ActionMessages();
        requestViewForm.setContextPath(actionMapping.getPath());

        // rule results
        RequestDTO requestDTO = getPolicyAdminService().raiseAndExecuteRequest(applicationContext,
                requestObjectReference);

        List<RuleResultDTO> ruleResultList = requestDTO.getRuleResultDTO();
        if (!ruleResultList.isEmpty()) {
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "page.agreement.ruleresult.message.failure"));
            saveErrorMessages(httpServletRequest, actionMessages);
            return redirectToRequestDisplay(actionMapping, requestObjectReference);
        }

        actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.request.message.raisedsuccessfully",
                formatKind(requestDTO.getKind())));
        saveInformationMessages(httpServletRequest, actionMessages);

        return redirectToRequestDisplay(actionMapping, requestObjectReference);
    }

    protected ActionRedirect redirectToRequestDisplay(ActionMapping actionMapping, ObjectReference requestObjectReference) {
        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", requestObjectReference);
        return redirect;
    }

    /**
     * Cancel the request and display an appropriate message to the user on re-display of the page
     */
    public ActionForward cancel(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        
        RequestViewForm requestViewForm = (RequestViewForm) actionForm;
        RequestDTO requestDTO = getPolicyAdminService().cancelRequest(getApplicationContext(), requestViewForm.getContextObjectReference());
        
        ActionMessages actionMessages = requestViewForm.getActionMessages();
        actionMessages.add(ActionMessages.GLOBAL_MESSAGE,
                new ActionMessage("page.request.message.cancelled", formatKind(requestDTO.getKind())));
        saveInformationMessages(httpServletRequest, actionMessages);

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", requestDTO.getObjectReference());
        return redirect;
    }

    public ActionForward save(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        RequestViewForm requestViewForm = (RequestViewForm) actionForm;

        // the request properties have been saved already (see validate and bind properties)
        RequestDTO requestDTO = requestViewForm.getStructuredActualDTO();

        ActionMessages actionMessages = requestViewForm.getActionMessages();
        actionMessages.add(ActionMessages.GLOBAL_MESSAGE,
                new ActionMessage("page.request.saved", formatKind(requestDTO.getKind())));
        saveInformationMessages(httpServletRequest, actionMessages);

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", requestDTO.getObjectReference());
        return redirect;
    }

    public ActionForward authorise(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        RequestViewForm requestViewForm = (RequestViewForm) actionForm;
        ObjectReference contextObjectReference = requestViewForm.getContextObjectReference();
        ApplicationContext applicationContext = getApplicationContext();
        requestViewForm.setContextPath(actionMapping.getPath());

        RequestDTO requestDTO = getPolicyAdminService().authoriseRequest(applicationContext, contextObjectReference);

        ActionMessages actionMessages = requestViewForm.getActionMessages();
        actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.request.message.authorised",
                formatKind(requestDTO.getKind())));
        saveInformationMessages(httpServletRequest, actionMessages);

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", requestDTO.getObjectReference());
        return redirect;
    }

    public ActionForward decline(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        RequestViewForm requestViewForm = (RequestViewForm) actionForm;
        ObjectReference contextObjectReference = requestViewForm.getContextObjectReference();
        ApplicationContext applicationContext = getApplicationContext();
        requestViewForm.setContextPath(actionMapping.getPath());

        RequestDTO requestDTO = getPolicyAdminService().declineRequest(applicationContext, contextObjectReference);

        ActionMessages actionMessages = requestViewForm.getActionMessages();
        actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.request.message.declined",
                formatKind(requestDTO.getKind()), formatRequestState(requestDTO.getState())));
        saveInformationMessages(httpServletRequest, actionMessages);

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", requestDTO.getObjectReference());
        return redirect;
    }

    public ActionForward override(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        RequestViewForm requestViewForm = (RequestViewForm) actionForm;
        ObjectReference contextObjectReference = requestViewForm.getContextObjectReference();
        ApplicationContext applicationContext = getApplicationContext();
        requestViewForm.setContextPath(actionMapping.getPath());

        RequestDTO requestDTO = getPolicyAdminService().overrideRequest(applicationContext, contextObjectReference);

        ActionMessages actionMessages = requestViewForm.getActionMessages();
        actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.request.message.overridden",
                formatKind(requestDTO.getKind()), formatRequestState(requestDTO.getState())));
        saveInformationMessages(httpServletRequest, actionMessages);

        ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
        redirect.addParameter("contextObjectReference", requestDTO.getObjectReference());
        return redirect;
    }

    @Override
    public void setupRoleInActualStrategy() {
        setRoleInActualStrategy((RequestRoleInActualStrategy) getBean("RequestStrategy"));
    }

    @Override
    protected RequestDTO getRequestDTO(ApplicationContext applicationContext, ObjectReference contextObjectReference, HttpServletRequest httpServletRequest) {
        return getPolicyAdminService().getRequestView(applicationContext, contextObjectReference);
    }

}
