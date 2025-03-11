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
 * @author Justin Walsh
 * @deprecated Include the core scripts manually, and then use {@link com.silvermoongroup.fsa.web.common.taglib.JQueryDatePickerScriptTag}
 * to include the specific langage for the date picker
 */
@Deprecated
public class JQueryScriptsTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private String core;
    private String ui;
    private String theme;

    @Override
    public int doStartTag() throws JspException {

        String contextPath = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
        String language = getUserProfileManager().getLanguage();

        StringBuilder sb = new StringBuilder(1024);

        // javascript
        sb.append("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery-" + getCore() + ".min.js\"></script>");
        sb.append("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery-ui-" + getUi() + ".min.js\"></script>");

        // for the english language, we don't need to include the script
        if (!Locale.ENGLISH.getLanguage().equals(language)) {
            sb.append("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.ui.datepicker-" + language + ".js\"></script>");
        }

        // stylesheet
        sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + contextPath + "/css/" + getTheme() + "/jquery-ui-" + getUi() + ".custom.css\" />");

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

    public String getCore() {
        return core;
    }
    public void setCore(String core) {
        this.core = core;
    }

    public String getUi() {
        return ui;
    }
    public void setUi(String ui) {
        this.ui = ui;
    }

    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }

}
