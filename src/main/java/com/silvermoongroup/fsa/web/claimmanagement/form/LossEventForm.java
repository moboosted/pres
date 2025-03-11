package com.silvermoongroup.fsa.web.claimmanagement.form;


import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.claimmanagement.ClaimableBenefitTableEntry;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Laurens on 2/14/2017.
 */
public class LossEventForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private ObjectReference objectReference;
    @RedirectParameter
    private String startDate;
    @RedirectParameter
    private String endDate;
    @RedirectParameter
    private String lossEventTime;
    @RedirectParameter
    private String lossEventName;
    @RedirectParameter
    private String description;
    @RedirectParameter
    private Long typeId;
    @RedirectParameter
    private String externalReference;

    @RedirectParameter
    private String victimName;
    @RedirectParameter
    private String claimClaimantName;
    @RedirectParameter
    private String agreementNumber;
    @RedirectParameter
    private ObjectReference claimObjectReference;

    //@RedirectParameter
    private ArrayList<ClaimableBenefitTableEntry> claimableBenefits;
    //@RedirectParameter
    private String _chk[];


    private String claimExternalReference;

    private boolean editing;

    @RedirectParameter
    private String roleKindToLink;

    /**
     * @return the objectReference of the lossEvent.
     */
    public ObjectReference getObjectReference() {
        return objectReference;
    }

    public void setObjectReference(ObjectReference objectReference) {
        this.objectReference = objectReference;
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

    public String getLossEventTime() {
        return lossEventTime;
    }

    public void setLossEventTime(String lossEventTime) {
        this.lossEventTime = lossEventTime;
    }

    public String getLossEventName() {
        return lossEventName;
    }

    public void setLossEventName(String lossEventName) {
        this.lossEventName = lossEventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public String getVictimName() {
        return victimName;
    }

    public void setVictimName(String insuredName) {
        this.victimName = insuredName;
    }

    public String getClaimClaimantName() {
        return claimClaimantName;
    }

    public void setClaimClaimantName(String claimClaimantName) {
        this.claimClaimantName = claimClaimantName;
    }

    public String getRoleKindToLink() {
        return roleKindToLink;
    }

    public void setRoleKindToLink(String roleKindToLink) {
        this.roleKindToLink = roleKindToLink;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public ObjectReference getClaimObjectReference() {
        return claimObjectReference;
    }

    public void setClaimObjectReference(ObjectReference claimObjectReference) {
        this.claimObjectReference = claimObjectReference;
    }

    public String getClaimExternalReference() {
        return claimExternalReference;
    }

    public void setClaimExternalReference(String claimExternalReference) {
        this.claimExternalReference = claimExternalReference;
    }

    public ArrayList<ClaimableBenefitTableEntry> getClaimableBenefits() {
        return claimableBenefits;
    }

    public void setClaimableBenefits(
            ArrayList<ClaimableBenefitTableEntry> claimableBenefits) {
        this.claimableBenefits = claimableBenefits;
    }

    public String[] get_chk() {
        return _chk;
    }

    public void set_chk(String[] _chk) {
        this._chk = _chk;
    }

    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return !actionName.toLowerCase().equals("defaultexecute");
    }
}
