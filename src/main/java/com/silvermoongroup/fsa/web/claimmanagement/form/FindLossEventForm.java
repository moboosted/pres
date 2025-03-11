package com.silvermoongroup.fsa.web.claimmanagement.form;


import com.silvermoongroup.businessservice.claimmanagement.dto.LossEvent;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;

import java.util.List;

/**
 * Created by Laurens on 2/14/2017.
 */
public class FindLossEventForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private String externalReference;
    @RedirectParameter
    private String lossEventDate;
    @RedirectParameter
    private String effectiveDate;

    private String lossEventExternalReference;

    private List<LossEvent> results;

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getLossEventDate() {
        return lossEventDate;
    }

    public void setLossEventDate(String lossEventDate) {
        this.lossEventDate = lossEventDate;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public List<LossEvent> getResults() {
        return results;
    }

    public void setResults(List<LossEvent> results) {
        this.results = results;
    }

    public String getLossEventExternalReference() {
        return lossEventExternalReference;
    }

    public void setLossEventExternalReference(String lossEventExternalReference) {
        this.lossEventExternalReference = lossEventExternalReference;
    }
}
