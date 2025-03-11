/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.servlet.filter;

import com.silvermoongroup.fsa.web.user.profile.IUserProfileManager;
import org.apache.struts.Globals;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Locale;

/**
 * Responsible for creating the user context (information about a user) both for known users (i.e those that have
 * authenticated) and anonymous users.
 * 
 * This logic resides in a filter as the J2EE security mechanism doesn't allow you to provide a 'post-login' hook.
 * 
 * @author Justin Walsh
 */
@Component
public class UserProfileFilter extends OncePerRequestFilter {

    private IUserProfileManager userProfileManager;

    public UserProfileFilter(IUserProfileManager userProfileManager) {
        this.userProfileManager = userProfileManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final Principal requestPrincipal = request.getUserPrincipal();

        if (requestPrincipal == null) {
            Locale locale = getUserProfileManager().getLocale();
            getUserProfileManager().saveLocale(response, locale);

            String dateFormat = getUserProfileManager().getDateFormat();
            getUserProfileManager().saveDateFormat(response, dateFormat);

            updateRenderEngineSettings(request, locale);
        }
        else {

            if (userProfileManager.getPrincipal() == null) {

                // and we have an authenticated user which we haven't seen before. For example the
                // first page requested after a login - we need to load the information for the user

                getUserProfileManager().loadUserPreferences();
                getUserProfileManager().setPrincipal(requestPrincipal);

                // the settings may have changed
                updateRenderEngineSettings(request, getUserProfileManager().getLocale());

                // redirect to the home page - a workaround for the lack of a post-login hook...
                response.sendRedirect(request.getContextPath() + "/secure/user/dashboard.do");
                return;
            }
        }

        // expose the session bean as a request attribute for use by JSP's
        request.setAttribute("userProfile", getUserProfileManager());


        filterChain.doFilter(request, response);
    }

    public static void updateRenderEngineSettings(HttpServletRequest request, Locale locale) {

        if (locale != null) {
            // locale
            request.getSession().setAttribute(Globals.LOCALE_KEY, locale); // struts


            // the following line is the equivalent of
            // Config.set(request.getSession(), Config.FMT_LOCALE, locale);
            // but we don't want to include the library as a compile time library.
            request.getSession().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", locale); // jstl
        }

    }

    public IUserProfileManager getUserProfileManager() {
        return userProfileManager;
    }

    public void setUserProfileManager(IUserProfileManager userProfileManager) {
        this.userProfileManager = userProfileManager;
    }
}
