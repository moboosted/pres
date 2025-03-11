/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.configuration.publicholiday;

import com.silvermoongroup.common.criteria.PublicHolidaySearchCriteria;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.datatype.Period;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Allows the user to search for a public holiday
 */

public class FindPublicHolidayAction extends AbstractLookupDispatchAction {

    public FindPublicHolidayAction() {
    }

    /**
     * The default action - display all public holidays
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        FindPublicHolidayForm form = (FindPublicHolidayForm) actionForm;

        populateStaticPageElements(form, request);

        // default to the current year if nothing is supplied
        if (form.getDateFrom() == null) {
            form.setDateFrom(form.getFirstOfCurrentYear());
            form.setDateTo(form.getLastOfCurrentYear());
        }

        return displaySearchResults(actionMapping, actionForm, request, response);
    }

    /**
     * Find public holidays given from-to dates
     */
    public ActionForward search(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        FindPublicHolidayForm form = (FindPublicHolidayForm) actionForm;
        return ActionRedirectFactory.createRedirect(actionMapping.findForwardConfig("searchResults"), form);
    }

    /**
     * Execute the search and display the results
     */
    public ActionForward displaySearchResults(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        FindPublicHolidayForm form = (FindPublicHolidayForm) actionForm;

        populateStaticPageElements(form, request);

        PublicHolidaySearchCriteria searchCriteria = new PublicHolidaySearchCriteria();
        Period<Date> inPeriod = new DatePeriod(parseDate(form.getDateFrom()), parseDate(form.getDateTo()).plus(1));
        searchCriteria.setPeriod(inPeriod);

        form.setSearchResults(getConfigurationService().findPublicHolidays(getApplicationContext(), searchCriteria));
        return actionMapping.getInputForward();
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm form, HttpServletRequest request) {
        populateStaticPageElements((FindPublicHolidayForm) form, request);
    }

    public void populateStaticPageElements(FindPublicHolidayForm form, HttpServletRequest httpRequest) {

        Date firstOfCurrentYear = new Date(new LocalDate().withMonthOfYear(1).withDayOfMonth(1).toDateMidnight().toDate());
        Date lastOfCurrentYear = new Date(new LocalDate().withMonthOfYear(1).withDayOfMonth(1).plusYears(1).minusDays(1).toDateMidnight().toDate());

        form.setFirstOfCurrentYear(formatDate(firstOfCurrentYear));
        form.setLastOfCurrentYear(formatDate(lastOfCurrentYear));

    }
}
