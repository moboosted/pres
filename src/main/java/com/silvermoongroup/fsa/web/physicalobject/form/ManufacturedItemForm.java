package com.silvermoongroup.fsa.web.physicalobject.form;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.physicalobject.action.ManufacturedItemAction;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Struts form for the {@link ManufacturedItemAction} page.
 */
public class ManufacturedItemForm extends ValidatorForm {
    
    private static final long serialVersionUID = 1L;
    
    // null if we are dealing with a new manufactured item
    private ObjectReference rolePlayerObjectReference;
    
    private String startDate;
    private String endDate;
    private String name;
    private String description;
    private String externalReference;
    private boolean inspectionCompleted;
    private Long typeId;
    private String currencyCode;
    private String value;
    
    private List<LabelValueBean> typeOptions = new ArrayList<>();
    private List<LabelValueBean> inspectionCompletedOptions = new ArrayList<>();
    
    public ManufacturedItemForm() {
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

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String serialNumber) {
        this.externalReference = serialNumber;
    }

    public boolean isInspectionCompleted() {
        return inspectionCompleted;
    }

    public void setInspectionCompleted(Boolean inspectionCompleted) {
        if (inspectionCompleted == null){
            this.inspectionCompleted = false;
        } else {
            this.inspectionCompleted = inspectionCompleted;
        }
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public ObjectReference getRolePlayerObjectReference() {
        return rolePlayerObjectReference;
    }
    
    public void setRolePlayerObjectReference(ObjectReference physicalObjectObjectReference) {
        this.rolePlayerObjectReference = physicalObjectObjectReference;
    }
    
    public String getCurrencyCode() {
        return currencyCode;
    }
    
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    
    public List<LabelValueBean> getTypeOptions() {
        return typeOptions;
    }
    
    public void setTypeOptions(List<LabelValueBean> types) {
        this.typeOptions = types;
    }

    public void setInspectionCompletedOptions(List<LabelValueBean> inspectionCompletedOptions) {
        this.inspectionCompletedOptions = inspectionCompletedOptions;
    }
    
    public List<LabelValueBean> getInspectionCompletedOptions() {
        return inspectionCompletedOptions;
    }

    @Override
    public boolean needsValidation(ActionMapping mapping, HttpServletRequest request) {
        String name = getActionName(mapping, request);
        if (name.toLowerCase().equals("save")) {
            return true;
        }
        return false;
    }
}
