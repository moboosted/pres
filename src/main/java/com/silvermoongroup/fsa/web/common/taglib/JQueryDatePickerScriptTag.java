/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.fsa.web.user.profile.IUserProfileManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;

/**
 * Includes the datepicker script relevant to the locale of the user
 *
 * @author Justin Walsh
 */
public class JQueryDatePickerScriptTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    @Override
    public int doStartTag() throws JspException {

        String contextPath = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
        String language = getUserProfileManager().getLanguage();

        StringBuilder sb = new StringBuilder(256);

        // for the english language, we don't need to include the script
        if (!Locale.ENGLISH.getLanguage().equals(language)) {
            sb.append("<script type=\"text/javascript\" src=\"").append(contextPath).append("/js/jquery.ui.datepicker-").append(language).append(".js\"></script>");
        }

        JspWriter writer = pageContext.getOut();
        try {
            writer.append(sb);
        }
        catch (IOException ex) {
            throw new JspException(ex);
        }

        return SKIP_BODY;
    }

    private IUserProfileManager getUserProfileManager() {
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        return ac.getBean("userProfileManager", IUserProfileManager.class);
    }

}
