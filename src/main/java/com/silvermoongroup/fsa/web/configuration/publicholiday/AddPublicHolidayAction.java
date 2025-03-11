/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.configuration.publicholiday;

import com.silvermoongroup.common.criteria.PublicHolidaySearchCriteria;
import com.silvermoongroup.common.domain.PublicHoliday;
import com.silvermoongroup.common.domain.intf.IPublicHoliday;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddPublicHolidayAction extends AbstractLookupDispatchAction {

    public AddPublicHolidayAction() {
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> kmm = new HashMap<>();
        kmm.put("button.add", "add");
        return kmm;
    }

    /**
     * The default action - display the form
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        return actionMapping.getInputForward();
    }

    /**
     * Add a public holiday
     */
    public ActionForward add(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

        AddPublicHolidayForm form = (AddPublicHolidayForm) actionForm;
        IPublicHoliday publicHoliday = new PublicHoliday();
        publicHoliday.setDescription(form.getDescription());
        publicHoliday.setDateOfHoliday(parseDate(form.getDate()));

        // is there already a public holiday on that date?
        PublicHolidaySearchCriteria criteria = new PublicHolidaySearchCriteria();
        criteria.setDate(parseDate(form.getDate()));
        List<IPublicHoliday> publicHolidays = getConfigurationService().findPublicHolidays(getApplicationContext(),
                criteria);
        if (!publicHolidays.isEmpty()) {
            IPublicHoliday existingHoliday = publicHolidays.get(0);
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.publicholiday.message.duplicate", existingHoliday.getDescription()));
            addErrors(request, messages);

            return actionMapping.getInputForward();
        }

        getConfigurationService().establishPublicHoliday(getApplicationContext(), publicHoliday);

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.publicholiday.message.created", form.getDescription()));
        saveInformationMessages(request, messages);

        FindPublicHolidayForm searchForm = new FindPublicHolidayForm();
        searchForm.setDateFrom(form.getDate());
        searchForm.setDateTo(form.getDate());
        return ActionRedirectFactory.createRedirect(actionMapping.findForwardConfig("searchPublicHoliday"), searchForm);
    }

    public ActionForward cancel(ActionMapping actionMapping, ActionForm actionForm,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ActionRedirect(actionMapping.findForwardConfig("searchPublicHoliday"));
    }
}
