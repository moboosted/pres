package com.silvermoongroup.fsa.web.enumeration;

import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import org.apache.struts.util.LabelValueBean;

import java.util.ArrayList;
import java.util.List;


public class EnumerationTypesForm extends ValidatorForm {
    
    private List<LabelValueBean> enumerationTypes= new ArrayList<>();
    
    private Long enumerationTypeId;
    private String enumerationTypeName;
    
    public List<LabelValueBean> getEnumerationTypes() {
        return enumerationTypes;
    }

    public void setEnumerationTypes(List<LabelValueBean> enumerationTypes) {
        this.enumerationTypes = enumerationTypes;
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
}
