/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement.form;

import com.silvermoongroup.claim.domain.intf.IElementaryClaim;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;

import java.util.List;

/**
 * @author Justin Walsh
 */
public class FindElementaryClaimForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private Long elementaryClaimId;
    @RedirectParameter
    private String dateOption;
    @RedirectParameter
    private String effectiveFrom;
    @RedirectParameter
    private String effectiveTo;
    @RedirectParameter
    private String effectiveOn;

    @RedirectParameter
    private String[] elementaryClaimStates = new String[0];
    @RedirectParameter
    private String externalReference;

    private String claimExternalReference;

    private List<IElementaryClaim> results;

    public FindElementaryClaimForm() {
    }

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public String getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(String effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public String getEffectiveOn() {
        return effectiveOn;
    }

    public void setEffectiveOn(String effectiveOn) {
        this.effectiveOn = effectiveOn;
    }

    public String[] getElementaryClaimStates() {
        return elementaryClaimStates;
    }

    public void setElementaryClaimStates(String[] elementaryClaimStates) {
        this.elementaryClaimStates = elementaryClaimStates;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public Long getElementaryClaimId() {
        return elementaryClaimId;
    }

    public void setElementaryClaimId(Long elementaryClaimId) {
        this.elementaryClaimId = elementaryClaimId;
    }

    public String getDateOption() {
        return dateOption;
    }

    public void setDateOption(String dateOption) {
        this.dateOption = dateOption;
    }

    public List<IElementaryClaim> getResults() {
        return results;
    }

    public void setResults(List<IElementaryClaim> results) {
        this.results = results;
    }

    public String getClaimExternalReference() {
        return claimExternalReference;
    }

    public void setClaimExternalReference(String claimExternalReference) {
        this.claimExternalReference = claimExternalReference;
    }
}
