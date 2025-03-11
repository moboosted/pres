/**
 *
 */
package com.silvermoongroup.fsa.web.common.form;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import com.silvermoongroup.fsa.web.locale.ITypeParser;
import com.silvermoongroup.fsa.web.user.profile.IUserProfileManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.joda.time.LocalDate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Huzaifah Ally
 */
public abstract class ValidatorForm extends org.apache.struts.validator.ValidatorForm {

    private static final long serialVersionUID = 1L;
    private boolean readOnly = false;

    protected IUserProfileManager getUserProfileManager() {
        return WebApplicationContextUtils.getRequiredWebApplicationContext(
                getServlet().getServletContext()).getBean(IUserProfileManager.class);
    }

    protected ITypeFormatter getTypeFormatter() {
        return WebApplicationContextUtils.getRequiredWebApplicationContext(
                getServlet().getServletContext()).getBean(ITypeFormatter.class);
    }

    protected ITypeParser getTypeParser() {
        return WebApplicationContextUtils.getRequiredWebApplicationContext(
                getServlet().getServletContext()).getBean(ITypeParser.class);
    }

    /**
     * Validate the properties that have been set from this HTTP request, and return an <code>ActionErrors</code> object
     * that encapsulates any validation errors that have been found. The extended version does not validate if the value
     * of <link>mapping.getParameter()</link> is "empty", "cancel" or "undo". If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no recorded error messages.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     * @return <code>ActionErrors</code> object that encapsulates any validation errors
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if (needsValidation(mapping, request)) {
            errors = super.validate(mapping, request);
        }

        return errors;
    }

    /**
     * Does the submission of the form require validation?
     */
    public boolean needsValidation(ActionMapping mapping, HttpServletRequest request) {

        String name = getActionName(mapping, request);
        if (GenericValidator.isBlankOrNull(name)) {
            return false;
        }

        name = name.toLowerCase();
        return !("undo".equals(name) || "cancel".equals(name) || "back".equals(name) || "load".equals(name)) && needsValidation(mapping, request, name);

    }

    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return true;
    }

    protected String getActionName(ActionMapping mapping, HttpServletRequest request) {
        String parameter = mapping.getParameter();

        String name;
        if (!GenericValidator.isBlankOrNull(request.getParameter(parameter))) {
            // Identify the method name to be dispatched to.
            name = request.getParameter(parameter);
        } else if (!GenericValidator.isBlankOrNull(request.getParameter("hiddenMethod"))) {
            name = request.getParameter("hiddenMethod");
        } else {
            name = "defaultExecute";
        }

        name = StringUtils.trimToNull(name);

        // if a method is specified with a . (dot) prefix, strip off the dot
        if (name.startsWith(".")) {
            return name.substring(1);
        }

        return name.trim();
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * Format a date based on a user's preference. Typically used before stashing the date string in a form bean for
     * display
     *
     * @param date The date to format
     * @return The formatted date.
     */
    protected String formatDate(Date date) {
        return getTypeFormatter().formatDate(date);
    }

    /**
     * Parse a string into a {@link Date}
     *
     * @param date The representation to parse.
     * @return The parsed date
     */
    protected Date parseDate(String date) {
        return getTypeParser().parseDate(date);
    }

    /**
     * This format is utilised during the Validation process on the Form beans
     *
     * @return The Date format of from the Users profile
     */
    protected String getDateFormat() {
        return getUserProfileManager().getDateFormat();
    }

    /**
     * @return A string, formatted as per the user's profile representing yesterday's date
     */
    public String getYesterday() {
        return formatDate(Date.today().minus(1));
    }

    /**
     * * @return A string, formatted as per the user's profile representing today's date
     */
    public String getToday() {
        return formatDate(Date.today());
    }

    public String getTomorrow() {
        return formatDate(Date.today().plus(1));
    }

    public String getFirstOfPreviousMonth() {
        return formatDate(new Date(new LocalDate().withDayOfMonth(1).minusMonths(1).toDateMidnight().toDate()));
    }

    public String getFirstOfCurrentMonth() {
        return formatDate(new Date(new LocalDate().withDayOfMonth(1).toDateMidnight().toDate()));

    }

    public String getFirstOfNextMonth() {
        return formatDate(new Date(new LocalDate().withDayOfMonth(1).plusMonths(1).toDateMidnight().toDate()));
    }

    public String getLastOfPreviousMonth() {
        return formatDate(new Date(new LocalDate().withDayOfMonth(1).minusDays(1).toDateMidnight().toDate()));

    }

    public String getLastOfCurrentMonth() {
        return formatDate(new Date(new LocalDate().withDayOfMonth(1).plusMonths(1).minusDays(1).toDateMidnight().toDate()));
    }

    public String getLastOfNextMonth() {
        return formatDate(new Date(new LocalDate().withDayOfMonth(1).plusMonths(2).minusDays(1).toDateMidnight().toDate()));
    }
}