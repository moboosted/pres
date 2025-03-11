/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement.form;

import com.silvermoongroup.claim.domain.intf.IClaimFolder;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;

import java.util.List;

/**
 * @author Justin Walsh
 */
public class FindClaimFolderForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private Long claimFolderId;
    @RedirectParameter
    private String dateOption;
    @RedirectParameter
    private String effectiveFrom;
    @RedirectParameter
    private String effectiveTo;
    @RedirectParameter
    private String effectiveOn;
    @RedirectParameter
    private boolean topLevelClaimFoldersOnly;

    @RedirectParameter
    private String[] claimFolderStates = new String[0];
    @RedirectParameter
    private String externalReference;

    private List<IClaimFolder> results;

    public FindClaimFolderForm() {
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

    public String[] getClaimFolderStates() {
        return claimFolderStates;
    }

    public void setClaimFolderStates(String[] claimFolderStates) {
        this.claimFolderStates = claimFolderStates;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public Long getClaimFolderId() {
        return claimFolderId;
    }

    public void setClaimFolderId(Long claimFolderId) {
        this.claimFolderId = claimFolderId;
    }

    public String getDateOption() {
        return dateOption;
    }

    public void setDateOption(String dateOption) {
        this.dateOption = dateOption;
    }

    public List<IClaimFolder> getResults() {
        return results;
    }

    public void setResults(List<IClaimFolder> results) {
        this.results = results;
    }

    public boolean isTopLevelClaimFoldersOnly() {
        return topLevelClaimFoldersOnly;
    }

    public void setTopLevelClaimFoldersOnly(boolean topLevelClaimFoldersOnly) {
        this.topLevelClaimFoldersOnly = topLevelClaimFoldersOnly;
    }
}
