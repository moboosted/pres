package com.silvermoongroup.fsa.web.security.action;

import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.security.form.LoginForm;
import com.silvermoongroup.fsa.web.servlet.filter.UserProfileFilter;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.util.LabelValueBean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Backs the login page.
 * <p>
 * The POST is directed at the spring security framework which provides the
 * security infrastructure.
 *
 *
 * @author Justin Walsh
 * @since 27 May 2008
 */
public class LoginAction extends AbstractLookupDispatchAction {

    public LoginAction() {
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        return new HashMap<>();
    }

    /**
     * The default method to execute
     */
    @SuppressWarnings("unchecked")
    public ActionForward defaultExecute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {

        LoginForm form = (LoginForm) actionForm;
        handlePossibleFailedAuthentication(request, form);

        /**
         * This 'hack' is present to allow the code to run on a container which does not apply filtering
         * to requests which are intercepted by the container and forwarded to the login page. WebLogic exhibits
         * such behaviour and this results in the filters being skipped (specifically the RequestContextFilter)
         * and ultimately errors when accessing session scoped beans.
         */
        // start-hack
        ServletRequestAttributes requestAttributesToCleanUp = null;
        if (RequestContextHolder.getRequestAttributes() == null) {
            requestAttributesToCleanUp = new ServletRequestAttributes(request);
            LocaleContextHolder.setLocale(request.getLocale(), false);
            RequestContextHolder.setRequestAttributes(requestAttributesToCleanUp, false);
        }
        // end-hack
        try {
            List<Locale> supportedLocales = getApplicationProperties().getSupportedLocales();
            Locale currentLocale = getUserProfileManager().getLocale();

            // the login page may not go through the filter as it is 'container managed'
            UserProfileFilter.updateRenderEngineSettings(request, currentLocale);

            List<LabelValueBean> availableLocales = (List<LabelValueBean>) supportedLocales.stream()
                    .filter(l -> !l.equals(currentLocale))
                    .map(l -> new LabelValueBean(formatMessage("page.login.label.locale." + l.toString()), l.toString()))
                    .sorted(LabelValueBean.CASE_INSENSITIVE_ORDER).collect(Collectors.toList());
            form.setAvailableLocales(availableLocales);
        }
        // start-hack
        finally {
            if (requestAttributesToCleanUp != null) {
                LocaleContextHolder.resetLocaleContext();
                RequestContextHolder.resetRequestAttributes();
                requestAttributesToCleanUp.requestCompleted();
            }
        }
        // end-hack
        return mapping.findForward("success");
    }

    public ActionForward changeLocale(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        LoginForm form = (LoginForm) actionForm;
        Locale locale = LocaleUtils.toLocale(form.getLocale());
        getUserProfileManager().saveLocale(response, locale); // master locale setting (may be overwritten by saved settings)
        UserProfileFilter.updateRenderEngineSettings(request, locale);

        return new ActionRedirect(mapping.findForwardConfig("refresh"));
    }

    private void handlePossibleFailedAuthentication(HttpServletRequest request, LoginForm form) {

        if (request.getParameter("loginFailure") != null) {

            // for the moment we just record whether the login failed or not
            form.setFailure(true);
        }
    }
}
