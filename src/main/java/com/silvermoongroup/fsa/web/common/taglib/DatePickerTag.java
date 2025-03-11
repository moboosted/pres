/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.fsa.web.user.profile.IUserProfileManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Generate a localised JQuery datepicker
 * @author Justin Walsh
 */
public class DatePickerTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    // the JQuery selector to use
    private String selector;

    public DatePickerTag() {
    }

    @Override
    public int doStartTag() throws JspException {

        String dateFormat = getUserProfileManager().getDateFormat();
        String jQueryDateFormat = convertToJQueryFormat(dateFormat);

        StringBuilder sb = new StringBuilder();

        sb.append("<script>");
        sb.append("$(function() {");

        // note that for some reason if we use the options method to setup the calendar, it clears the
        // value of the input that is is being used for
        sb.append("   $(\"" + getSelector() + "\").datepicker({");
        sb.append("      dateFormat : \"" + jQueryDateFormat + "\",");
        sb.append("      yearRange : \"1901:2050\",");
        sb.append("      changeYear : true,");
        sb.append("      showButtonPanel: true");
        sb.append("   });");

        sb.append("});"); // end function
        sb.append("</script>");

        JspWriter out = pageContext.getOut();
        try {
            out.print(sb);
        } catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

    // see http://docs.jquery.com/UI/DatePickerTag/formatDate
    // and
    private String convertToJQueryFormat(String dateFormat) {
        dateFormat = dateFormat.replace("yyyy", "yy");
        dateFormat = dateFormat.replace("MM", "mm");
        return dateFormat;
    }

    public String getSelector() {
        return selector;
    }
    public void setSelector(String selector) {
        this.selector = selector;
    }

    private IUserProfileManager getUserProfileManager() {
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        return ac.getBean("userProfileManager", IUserProfileManager.class);
    }
}
