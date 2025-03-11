package com.silvermoongroup.fsa.web.enumeration;

import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;

import java.util.Collection;

public class ViewEnumerationsForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;
    
    private EnumerationReference enumerationReference;
    @RedirectParameter
    private Long enumerationTypeId;
    private String enumerationTypeName;
    
    private Collection<IEnumeration> enumerations = null;
    
    public ViewEnumerationsForm(){
        
    }
    
    public EnumerationReference getEnumerationReference() {
        return enumerationReference;
    }

    public void setEnumerationReference(EnumerationReference enumerationReference) {
        this.enumerationReference = enumerationReference;
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

    public Collection<IEnumeration> getEnumerations() {
        return enumerations;
    }

    public void setEnumerations(Collection<IEnumeration> enumerations) {
        this.enumerations = enumerations;
    }
    
}
