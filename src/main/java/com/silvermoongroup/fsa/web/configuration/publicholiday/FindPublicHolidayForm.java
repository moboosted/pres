/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.configuration.publicholiday;

import com.silvermoongroup.common.domain.intf.IPublicHoliday;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Form bean supporting the {@link FindPublicHolidayAction}
 */
public class FindPublicHolidayForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private String dateFrom;
    @RedirectParameter
    private String dateTo;

    private List<IPublicHoliday> searchResults;

    private String firstOfCurrentYear;
    private String lastOfCurrentYear;

    public FindPublicHolidayForm() {
    }

    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return actionName.equalsIgnoreCase("search");
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public List<IPublicHoliday> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<IPublicHoliday> publicHolidays) {
        this.searchResults = publicHolidays;
    }
    
    public void setFirstOfCurrentYear(String firstOfCurrentYear) {
        this.firstOfCurrentYear = firstOfCurrentYear;
    }

    public String getFirstOfCurrentYear() {
        return firstOfCurrentYear;
    }

    public void setLastOfCurrentYear(String lastOfCurrentYear) {
        this.lastOfCurrentYear = lastOfCurrentYear;
    }

    public String getLastOfCurrentYear() {
        return lastOfCurrentYear;
    } 
}

