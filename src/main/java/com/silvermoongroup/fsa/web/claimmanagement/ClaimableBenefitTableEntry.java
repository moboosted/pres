package com.silvermoongroup.fsa.web.claimmanagement;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.policysearch.dto.ClaimableBenefit;

import java.io.Serializable;

/**
 * Wrapper for claimable benefit table entries in UI
 * Created by Laurens on 2/24/2017.
 */
public class ClaimableBenefitTableEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean checked;
    private ClaimableBenefit claimableBenefit;

    public ClaimableBenefitTableEntry() {
    }

    public ClaimableBenefitTableEntry(ClaimableBenefit claimableBenefit) {
        this.claimableBenefit = claimableBenefit;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public ClaimableBenefit getClaimableBenefit() {
        return claimableBenefit;
    }

    public void setClaimableBenefit(ClaimableBenefit claimableBenefit) {
        this.claimableBenefit = claimableBenefit;
    }

    //passthrough
    public ObjectReference getTopLevelAgreement() {
        return claimableBenefit.getTopLevelAgreement();
    }

    public ObjectReference getClaimableCoverage() {
        return claimableBenefit.getClaimableCoverage();
    }

    public Long getClaimableCoverageKindId() {
        return claimableBenefit.getClaimableCoverageKindId();
    }

    public String getClaimableCoverageKindName() {
        return claimableBenefit.getClaimableCoverageKindName();
    }

    public Date getStartDate() {
        return claimableBenefit.getEffectivePeriod().getStart();
    }

    public Date getEndDate() {
        return claimableBenefit.getEffectivePeriod().getEnd();
    }

    public String getId() {
        return claimableBenefit.getClaimableCoverage().toString();
    }


    @Override
    public String toString() {
        return claimableBenefit.getClaimableCoverageKindName() + "=" + checked;
    }
}
