/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.security.action;

import com.silvermoongroup.fsa.web.security.service.intf.ILogoutService;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Log a user out, invalidating the session
 */
public class LogoutAction extends AbstractLookupDispatchAction {

    @Override
    protected void onInit() {
        super.onInit();
    }

    /**
     * GET:  The logout link is clicked
     */
    public ActionForward defaultExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauth2AuthenticationToken) {
            ILogoutService logoutService = getBean(ILogoutService.class);
            logoutService.logout(oauth2AuthenticationToken);
        }

        // look up this variable first BEFORE we invalidate the session
        String logoutUrl = StringUtils.trimToNull(getApplicationProperties().getLogoutUrl());

        if (session != null) {
            session.invalidate();
        }

        if (logoutUrl != null) {
            if (logoutUrl.startsWith("/")) {
                response.sendRedirect(logoutUrl);
            } else {
                response.sendRedirect(request.getContextPath() + "/" + logoutUrl);
            }
            return null;
        } else {
            return new ActionRedirect(mapping.findForward("home"));
        }
    }
}
