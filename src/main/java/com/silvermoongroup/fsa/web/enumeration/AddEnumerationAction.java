package com.silvermoongroup.fsa.web.enumeration;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.datatype.Period;
import com.silvermoongroup.common.domain.Enumeration;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.InternalCompanyCodeEnumeration;
import com.silvermoongroup.common.domain.UnitOfMeasureEnumeration;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.domain.intf.IUnitOfMeasureEnumeration;
import com.silvermoongroup.common.enumeration.EnumerationType;
import com.silvermoongroup.fsa.web.struts.RedirectException;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddEnumerationAction extends AbstractEnumerationAction {

    public AddEnumerationAction() {

    }

    /**
     * The default action - display the form
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        AddEnumerationForm form = (AddEnumerationForm) actionForm;
        if (form.getEnumerationTypeId() == null) {
            log.error("Enumeration Id required");
            throw new RedirectException(new ActionRedirect(actionMapping.findForwardConfig("enumerationTypes")));
        }

        populateFormElements(form, request);

        if (form.getEnumerationTypeId().equals(EnumerationType.INTERNAL_COMPANY_CODE.getValue())
                && form.getOrganisationOptions().isEmpty()) {
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.addenumeration.message.noorganisation"));
            addErrors(request, messages);
        }

        String enumerationTypeName = EnumerationType.fromValue(form.getEnumerationTypeId()).getName();
        form.setEnumerationTypeName(enumerationTypeName);

        return actionMapping.getInputForward();
    }

    /**
     * Add an Enumeration
     */
    public ActionForward add(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        AddEnumerationForm form = (AddEnumerationForm) actionForm;
        IEnumeration enumeration;

        switch (EnumerationType.fromValue(form.getEnumerationTypeId())) {
        case UNIT_OF_MEASURE:
            enumeration = new UnitOfMeasureEnumeration();
            setMeasure((IUnitOfMeasureEnumeration) enumeration, form);
            break;
        case INTERNAL_COMPANY_CODE:
            enumeration = new InternalCompanyCodeEnumeration();
            setOrganisationObjectReference((InternalCompanyCodeEnumeration) enumeration, form);
            break;
        default:
            enumeration = new Enumeration();
            break;
        }
        enumeration.setEnumerationTypeId(form.getEnumerationTypeId());
        enumeration.setId(form.getCode());
        enumeration.setName(form.getName());
        enumeration.setDescription(form.getDescription());
        enumeration.setAbbreviation(form.getAbbreviation());
        Period<Date> effectivePeriod = new DatePeriod(parseDate(form.getStartDate()), parseDate(form.getEndDate()));
        enumeration.setEffectivePeriod(effectivePeriod);

        // Is there already an existing enumeration.
        IEnumeration result = getProductDevelopmentService().getEnumeration(getApplicationContext(),
                new EnumerationReference(form.getCode(), form.getEnumerationTypeId()));

        if (result != null) {
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.addenumeration.message.duplicate",
                    result.getEnumerationReference(), result.getDescription()));
            addErrors(request, messages);

            return actionMapping.getInputForward();
        }

        getProductDevelopmentService().establishEnumeration(getApplicationContext(), enumeration);

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE,
                new ActionMessage("page.addenumeration.message.created", form.getName()));
        saveInformationMessages(request, messages);

        return redirectToViewPage(form, actionMapping);
    }
}
