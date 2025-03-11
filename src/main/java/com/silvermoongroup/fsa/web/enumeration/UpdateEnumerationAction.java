package com.silvermoongroup.fsa.web.enumeration;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.datatype.Period;
import com.silvermoongroup.common.domain.InternalCompanyCodeEnumeration;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.UnitOfMeasureEnumeration;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.EnumerationType;
import com.silvermoongroup.common.enumeration.Measure;
import com.silvermoongroup.fsa.web.struts.RedirectException;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateEnumerationAction extends AbstractEnumerationAction {
    
    public UpdateEnumerationAction() {
        
    }
    
    /**
     * The default action - display the form
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        UpdateEnumerationForm form = (UpdateEnumerationForm) actionForm;
        if (form.getEnumerationReference() == null) {
            log.error("Enumeration Reference required");
            throw new RedirectException(new ActionRedirect(actionMapping.findForwardConfig("enumerationTypes")));
        }
        
        form.setEnumerationTypeId(form.getEnumerationReference().getEnumerationTypeId());        
        String enumerationTypeName = EnumerationType.fromValue(form.getEnumerationReference().getEnumerationTypeId())
                .getName();
        form.setEnumerationTypeName(enumerationTypeName);
        
        IEnumeration enumeration = getProductDevelopmentService().getEnumeration(getApplicationContext(),
                form.getEnumerationReference());
        
        switch (EnumerationType.fromValue(enumeration.getEnumerationTypeId())) {
        case UNIT_OF_MEASURE:
            UnitOfMeasureEnumeration unitOfMeasure = (UnitOfMeasureEnumeration) enumeration;
            form.setMeasure(unitOfMeasure.getMeasure().name());
            break;
            
        case INTERNAL_COMPANY_CODE:
            InternalCompanyCodeEnumeration companyCode = (InternalCompanyCodeEnumeration) enumeration;
            form.setOrganisationObjectReference(ObjectReference.convertToString(companyCode.getObjectReference()));
            break;

        default:
            break;
        }
        populateFormElements(form, request);
        
        form.setCode(enumeration.getId());
        form.setName(enumeration.getName());
        form.setDescription(enumeration.getDescription());
        form.setAbbreviation(enumeration.getAbbreviation());
        form.setStartDate(formatDate(enumeration.getEffectivePeriod().getStart()));
        form.setEndDate(formatDate(enumeration.getEffectivePeriod().getEnd()));
        
        return actionMapping.getInputForward();
    }
    
    /**
     * Update an Enumeration
     */
    public ActionForward update(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
     
        UpdateEnumerationForm form = (UpdateEnumerationForm) actionForm;
        
        IEnumeration enumeration = getProductDevelopmentService().getEnumeration(getApplicationContext(),
                form.getEnumerationReference());
 
        enumeration.setName(form.getName());
        enumeration.setDescription(form.getDescription());
        enumeration.setAbbreviation(form.getAbbreviation());
        Period<Date> effectivePeriod = new DatePeriod(parseDate(form.getStartDate()), parseDate(form.getEndDate()));
        enumeration.setEffectivePeriod(effectivePeriod);
        
        switch (EnumerationType.fromValue(enumeration.getEnumerationTypeId())) {
        case UNIT_OF_MEASURE:
            UnitOfMeasureEnumeration unitOfMeasure = (UnitOfMeasureEnumeration) enumeration;
            unitOfMeasure.setMeasure(Measure.valueOf(form.getMeasure()));
            enumeration = unitOfMeasure;
            break;

        case INTERNAL_COMPANY_CODE:
            InternalCompanyCodeEnumeration companyCode = (InternalCompanyCodeEnumeration) enumeration;
            companyCode.setObjectReference(ObjectReference.convertFromString(form.getOrganisationObjectReference()));
            enumeration = companyCode;
            break;

        default:
            break;
        }
        
        getProductDevelopmentService().modifyEnumeration(getApplicationContext(), enumeration);
        
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE,
                new ActionMessage("page.addenumeration.message.modify", form.getName()));
        saveInformationMessages(request, messages);

        return redirectToViewPage(form, actionMapping);
    }
}