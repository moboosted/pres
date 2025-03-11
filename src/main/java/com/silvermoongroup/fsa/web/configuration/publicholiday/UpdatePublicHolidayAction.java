/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.configuration.publicholiday;

import com.silvermoongroup.common.criteria.PublicHolidaySearchCriteria;
import com.silvermoongroup.common.domain.intf.IPublicHoliday;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Justin Walsh
 */
public class UpdatePublicHolidayAction extends AbstractLookupDispatchAction {

    /**
     * Display the public holiday for update (GET)
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        UpdatePublicHolidayForm form = (UpdatePublicHolidayForm) actionForm;
        if (form.getPublicHolidayObjectReference() == null) {
            return new ActionRedirect(actionMapping.findForwardConfig("searchPublicHoliday"));
        }

        IPublicHoliday publicHoliday = getConfigurationService().getPublicHoliday(getApplicationContext(),
                form.getPublicHolidayObjectReference());

        form.setDate(formatDate(publicHoliday.getDateOfHoliday()));
        form.setDescription(publicHoliday.getDescription());
        form.setPublicHolidayObjectReference(publicHoliday.getObjectReference());

        return actionMapping.getInputForward();
    }

    /**
     * Update the public holiday (POST)
     */
    public ActionForward update(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        UpdatePublicHolidayForm form = (UpdatePublicHolidayForm) actionForm;

        // is there another public holiday on this day?
        //
        PublicHolidaySearchCriteria criteria = new PublicHolidaySearchCriteria();
        criteria.setDate(parseDate(form.getDate()));
        List<IPublicHoliday> publicHolidays = getConfigurationService().findPublicHolidays(getApplicationContext(),
                criteria);
        for (IPublicHoliday possibleDuplicate : publicHolidays) {
            if (!possibleDuplicate.getObjectReference().equals(form.getPublicHolidayObjectReference())) {
                ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.publicholiday.message.duplicate", possibleDuplicate.getDescription()));
                addErrors(request, messages);

                return actionMapping.getInputForward();
            }
        }

        // otherwise update
        IPublicHoliday publicHoliday = getConfigurationService().getPublicHoliday(getApplicationContext(),
                form.getPublicHolidayObjectReference());
        publicHoliday.setDateOfHoliday(parseDate(form.getDate()));
        publicHoliday.setDescription(form.getDescription());

        getConfigurationService().modifyPublicHoliday(getApplicationContext(), publicHoliday);
        addInformationMessage(request, "page.publicholiday.message.updated", publicHoliday.getDescription());

        FindPublicHolidayForm searchForm = new FindPublicHolidayForm();
        searchForm.setDateFrom(form.getDate());
        searchForm.setDateTo(form.getDate());
        return ActionRedirectFactory.createRedirect(actionMapping.findForwardConfig("searchPublicHoliday"), searchForm);
    }

    /**
     * Delete this public holiday
     */
    public ActionForward delete(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        UpdatePublicHolidayForm form = (UpdatePublicHolidayForm) actionForm;

        IPublicHoliday publicHoliday = getConfigurationService().getPublicHoliday(getApplicationContext(),
                form.getPublicHolidayObjectReference());
        getConfigurationService().deletePublicHoliday(getApplicationContext(), publicHoliday);

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.publicholiday.message.deleted"));
        saveInformationMessages(request, messages);

        return new ActionRedirect(actionMapping.findForwardConfig("searchPublicHoliday"));
    }

    public ActionForward cancel(ActionMapping actionMapping, ActionForm actionForm,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ActionRedirect(actionMapping.findForwardConfig("searchPublicHoliday"));
    }
}
