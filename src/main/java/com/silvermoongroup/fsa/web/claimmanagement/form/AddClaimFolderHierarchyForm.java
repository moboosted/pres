package com.silvermoongroup.fsa.web.claimmanagement.form;


import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.claimmanagement.ClaimableBenefitTableEntry;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;

import java.util.ArrayList;

/**
 * Created by Laurens on 2/14/2017.
 */
public class AddClaimFolderHierarchyForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private String claimFolderExternalReference;
    @RedirectParameter
    private String lossEventExternalReference;
    @RedirectParameter
    private String victimName;
    @RedirectParameter
    private String claimantName;
    @RedirectParameter
    private ArrayList<ClaimableBenefitTableEntry> claimableBenefits;

    @RedirectParameter
    private String _chk[];

    private ObjectReference tlaObjectReference;

    public AddClaimFolderHierarchyForm() {
    }

    public String getClaimFolderExternalReference() {
        return claimFolderExternalReference;
    }

    public void setClaimFolderExternalReference(String claimFolderExternalReference) {
        this.claimFolderExternalReference = claimFolderExternalReference;
    }

    public ArrayList<ClaimableBenefitTableEntry> getClaimableBenefits() {
        return claimableBenefits;
    }

    public void setClaimableBenefits(ArrayList<ClaimableBenefitTableEntry> claimableBenefits) {
        this.claimableBenefits = claimableBenefits;
    }

    public String getLossEventExternalReference() {
        return lossEventExternalReference;
    }

    public void setLossEventExternalReference(String lossEventExternalReference) {
        this.lossEventExternalReference = lossEventExternalReference;
    }

    public String getVictimName() {
        return victimName;
    }

    public void setVictimName(String victimName) {
        this.victimName = victimName;
    }

    public String getClaimantName() {
        return claimantName;
    }

    public void setClaimantName(String claimantName) {
        this.claimantName = claimantName;
    }

    public ObjectReference getTlaObjectReference() {
        return tlaObjectReference;
    }

    public void setTlaObjectReference(ObjectReference tlaObjectReference) {
        this.tlaObjectReference = tlaObjectReference;
    }

    public String[] get_chk() {
        return _chk;
    }

    public void set_chk(String _chk[]) {
        this._chk = _chk;
    }
}
