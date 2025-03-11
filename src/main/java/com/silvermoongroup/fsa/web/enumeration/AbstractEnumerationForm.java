package com.silvermoongroup.fsa.web.enumeration;

import com.silvermoongroup.common.enumeration.EnumerationType;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for add/update enumeration forms
 */
public class AbstractEnumerationForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    protected Long code;
    @RedirectParameter
    protected Long enumerationTypeId;
    protected String enumerationTypeName;
    protected String name;
    protected String description;
    protected String abbreviation;
    protected String startDate;
    protected String endDate;
    protected String measure;
    protected String organisationObjectReference;
    
    protected List<LabelValueBean> enumerationTypesOption = new ArrayList<>();
    protected List<LabelValueBean> measureOptions = new ArrayList<>();
    protected List<LabelValueBean> organisationOptions = new ArrayList<>();
    
    @Override
    public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        ActionErrors errors = new ActionErrors();
        if (needsValidation(actionMapping, httpServletRequest)) {
            errors = super.validate(actionMapping, httpServletRequest);         
            switch (EnumerationType.fromValue(enumerationTypeId)) {
            case INTERNAL_COMPANY_CODE:
                if (GenericValidator.isBlankOrNull(organisationObjectReference)) {
                    errors.add("organisationObjectReference", new ActionMessage("errors.required", getTypeFormatter()
                            .formatMessage("page.enumeration.label.organisation")));
                }
                break;

            case UNIT_OF_MEASURE:
                if (GenericValidator.isBlankOrNull(measure)) {
                    errors.add("measure", new ActionMessage("errors.required", getTypeFormatter().formatMessage(
                            "page.enumeration.label.measure")));
                }
                break;
                
            default:
            }
        }
        
        return errors;
    }
    
    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getEnumerationTypeId() {
        return enumerationTypeId;
    }

    public void setEnumerationTypeId(Long enumerationTypeId) {
        this.enumerationTypeId = enumerationTypeId;
    }

    public String getEnumerationTypeName() {
        return enumerationTypeName;
    }

    public void setEnumerationTypeName(String enumerationTypeName) {
        this.enumerationTypeName = enumerationTypeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getStartDate() {
        return startDate;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
        
    public List<LabelValueBean> getMeasureOptions() {
        return measureOptions;
    }

    public void setMeasureOptions(List<LabelValueBean> measureOptions) {
        this.measureOptions = measureOptions;
    }
    
    public List<LabelValueBean> getEnumerationTypesOption() {
        return enumerationTypesOption;
    }

    public void setOrganisationObjectReference(String organisationObjectReference) {
        this.organisationObjectReference = organisationObjectReference;
    }
    
    public String getOrganisationObjectReference() {
        return organisationObjectReference;
    }
    
    public List<LabelValueBean> getOrganisationOptions() {
        return organisationOptions;
    }

    public void setOrganisationOptions(List<LabelValueBean> organisationOptions) {
        this.organisationOptions = organisationOptions;
    }    
}
