package com.silvermoongroup.fsa.web.enumeration;

import com.silvermoongroup.common.domain.InternalCompanyCodeEnumeration;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.domain.intf.IUnitOfMeasureEnumeration;
import com.silvermoongroup.common.enumeration.EnumerationType;
import com.silvermoongroup.common.enumeration.Measure;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import com.silvermoongroup.party.domain.Organisation;
import com.silvermoongroup.party.domain.Party;
import com.silvermoongroup.party.domain.PartyName;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * Base class for add/update enumeration.
 * 
 * 
 */
public abstract class AbstractEnumerationAction extends AbstractLookupDispatchAction {

    public AbstractEnumerationAction() {

    }
    
    @OnFormValidationFailure
    public void onFormValidationError(AbstractEnumerationForm actionForm, HttpServletRequest request) throws Exception {
        populateFormElements(actionForm, request);
    }

    public ActionForward cancel(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return redirectToViewPage((AbstractEnumerationForm)actionForm, actionMapping);
    }

    protected IEnumeration setMeasure(IUnitOfMeasureEnumeration enumeration, AddEnumerationForm form) {
        enumeration.setMeasure(Measure.valueOf(form.getMeasure()));
        return enumeration;
    }

    protected IEnumeration setOrganisationObjectReference(InternalCompanyCodeEnumeration enumeration,
            AddEnumerationForm form) {
        enumeration.setObjectReference(ObjectReference.convertFromString(form.getOrganisationObjectReference()));
        return enumeration;
    }

    protected void populateFormElements(AbstractEnumerationForm actionForm, HttpServletRequest httpRequest) {

        if (actionForm.getEnumerationTypeId().equals(EnumerationType.UNIT_OF_MEASURE.getValue())) {
            List<LabelValueBean> measureOptions = new ArrayList<>();
            for (Measure measure : Measure.values()) {
                measureOptions.add(new LabelValueBean(measure.name(), measure.name()));
            }
            actionForm.setMeasureOptions(measureOptions);

        } else if (actionForm.getEnumerationTypeId().equals(EnumerationType.INTERNAL_COMPANY_CODE.getValue())) {
            List<LabelValueBean> organisationOption = new ArrayList<>();
            String objectReference = actionForm.getOrganisationObjectReference();
            if (objectReference != null) {
                organisationOption.add(new LabelValueBean(getOrganisationNameAndReference(objectReference),
                        objectReference));
            }

            List<Organisation> organisations = getCustomerRelationshipService()
                    .findOrganisationsNotLinkedToInternalCompanyCode(getApplicationContext());
            for (Organisation organisation : organisations) {
                objectReference = organisation.getObjectReference().toString();
                String partyAndORef = getOrganisationNameAndReference(objectReference);
                organisationOption.add(new LabelValueBean(partyAndORef, objectReference));
            }
            actionForm.setOrganisationOptions(organisationOption);
        }
    }
    
    /**
     * Get a parties name by an object reference and concatenate it to the object reference
     */
    private String getOrganisationNameAndReference(String partyObjectReference) {
        Party party = getCustomerRelationshipService().getParty(getApplicationContext(),
                ObjectReference.convertFromString(partyObjectReference));
        PartyName partyName = getCustomerRelationshipService().getDefaultPartyNameForRolePlayer(
                getApplicationContext(), (party.getObjectReference()));
        String fullName = partyName.getFullName();
        return fullName + " (" + partyObjectReference + ")";
    }

    protected ActionForward redirectToViewPage(AbstractEnumerationForm actionForm, ActionMapping actionMapping) {
        ActionRedirect rd = new ActionRedirect(actionMapping.findForwardConfig("view"));

        rd.addParameter("enumerationTypeId", actionForm.getEnumerationTypeId());
        return rd;
    }
}
