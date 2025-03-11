/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreement.form;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.agreement.AgreementVO;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Form bean for the 'Find Agreement' action
 * 
 * @author Justin Walsh
 */
public class FindAgreementForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    // flag to indicate indicate whether we are in linking mode or not
    private boolean linkingMode;

    // search criteria
    @RedirectParameter private Long agreementId;
    @RedirectParameter private String externalReference;
    @RedirectParameter private Long tlaKind;
    @RedirectParameter private boolean restrictToTLA;

    // search results
    private List<AgreementVO> searchResults = new ArrayList<>();

    // selected search result
    private ObjectReference selectedAgreementObjectReference;

    public FindAgreementForm() {
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public Long getTlaKind() {
        return tlaKind;
    }

    public void setTlaKind(Long tlaKind) {
        this.tlaKind = tlaKind;
    }

    public List<AgreementVO> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<AgreementVO> searchResults) {
        this.searchResults = searchResults;
    }

    public ObjectReference getSelectedAgreementObjectReference() {
        return selectedAgreementObjectReference;
    }

    public void setSelectedAgreementObjectReference(ObjectReference selectedAgreementObjectReference) {
        this.selectedAgreementObjectReference = selectedAgreementObjectReference;
    }

    public boolean isLinkingMode() {
        return linkingMode;
    }

    public void setLinkingMode(boolean linkingMode) {
        this.linkingMode = linkingMode;
    }

    public boolean isRestrictToTLA() {
        return restrictToTLA;
    }

    public void setRestrictToTLA(boolean restrictToTLA) {
        this.restrictToTLA = restrictToTLA;
    }

    public Long getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Long agreementId) {
        this.agreementId = agreementId;
    }

    @Override
    public boolean needsValidation(ActionMapping mapping, HttpServletRequest request) {

        String name = getActionName(mapping, request);
        if (GenericValidator.isBlankOrNull(name)) {
            return false;
        }

        name = name.toLowerCase().trim();
        if ("defaultexecute".equals(name) || "view".equals(name) || "retrieve".equals(name)) {
            return false;
        }

        return true;
    }
}
