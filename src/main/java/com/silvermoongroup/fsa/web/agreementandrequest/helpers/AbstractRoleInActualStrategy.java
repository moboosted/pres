/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.helpers;

import com.silvermoongroup.businessservice.policymanagement.dto.KindDTO;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.callback.CallBackAction;
import com.silvermoongroup.fsa.web.common.callback.CallBackUtility;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mubeen
 */
public abstract class AbstractRoleInActualStrategy<T extends ValidatorForm> implements RoleInActualStrategy<T> {

    private static final String CALLBACK_METHOD = "?method=.handleRoleCallBack";

    /* Part of the legacy constants. Trying to clean up so that we may destroy unnecessary stuff - MLA */
    protected static final String ROLE_KIND = "roleKind";
    protected static final String ROLE_UUID = "roleUUID";
    protected static final String ROLE_PLAYER_CALLBACK_KEY = "rolePlayerCallBackKey";
    protected static final String SCOPE_REQUEST = "request";
    private static final String ACTION = "action";

    public abstract String getCallBackPath(String context);

    public abstract KindDTO getRoleKind(T form);

    public abstract String getRoleUniqueIdentifier(T form);

    // helper: Add a callback and redirect to the given mapping
    protected ActionRedirect addCallBackAndRedirectTo(ActionMapping actionMapping, HttpServletRequest request,
            HttpServletResponse response, T form, String mappingName, CallBackAction action, String context) {

        return addCallBackAndRedirectTo(actionMapping, request, response, form, mappingName, ObjectReference.class, action, context);
    }

    protected ActionRedirect addCallBackAndRedirectTo(ActionMapping actionMapping, HttpServletRequest request,
            HttpServletResponse response, T form, String mappingName, Class<?> callBackTypeClass, CallBackAction action, String context) {

        addCallBackToRequest(request, response, form, callBackTypeClass, action, context, mappingName);

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForward(mappingName));
        redirect.addParameter(ROLE_KIND, getRoleKind(form).getId());
        String roleUUID = StringUtils.trimToNull(getRoleUniqueIdentifier(form));
        if (roleUUID != null) {
            redirect.addParameter(ROLE_UUID, roleUUID);
        }

        return redirect;
    }

    protected void addCallBackToRequest(HttpServletRequest request, HttpServletResponse response, T form,
            Class<?> callBackType, CallBackAction action, String context, String mappingName) {

        StringBuilder callBackPage = new StringBuilder(getCallBackPath(context));
        callBackPage.append(CALLBACK_METHOD);
        callBackPage.append("&").append(ACTION).append("=").append(action.name());
        String roleUUID = StringUtils.trimToNull(getRoleUniqueIdentifier(form));
        if (roleUUID != null) {
            callBackPage.append("&").append(ROLE_UUID).append("=").append(getRoleUniqueIdentifier(form));
        }

        request.setAttribute(CallBackUtility.PAGE, callBackPage.toString());
        request.setAttribute(CallBackUtility.KEY, ROLE_PLAYER_CALLBACK_KEY);
        request.setAttribute(CallBackUtility.TYPE, callBackType);
        request.setAttribute(CallBackUtility.SCOPE, SCOPE_REQUEST);
        request.setAttribute(CallBackUtility.INTENDED_MAPPING, mappingName);

        CallBackUtility.addCallBack(request, response);
    }
}