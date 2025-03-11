package com.silvermoongroup.fsa.web.claimmanagement.form;


import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;

/**
 * Created by Laurens on 3/06/2017.
 */
public class ValidateCoverageForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private String externalReference;
    @RedirectParameter
    private String insuredName;
    @RedirectParameter
    private String beneficiaryName;

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }
}
