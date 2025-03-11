/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement;

import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;

/**
 * Created by damir on 2/25/2017.
 */
public class ClaimFolderHierarchyTableDecorator extends AbstractTableDecorator {

    public Object getStartDate() {
        return formatDate(getClaimableBenefitTableEntry().getStartDate());
    }

    public Object getEndDate() {
        return formatDate(getClaimableBenefitTableEntry().getEndDate());
    }

    public Object getTopLevelAgreement() {
        return generateDivWithObjectReferenceTooltip(getClaimableBenefitTableEntry().getTopLevelAgreement(), null);
    }

    public Object getClaimableCoverage() {
        return generateDivWithObjectReferenceTooltip(getClaimableBenefitTableEntry().getClaimableCoverage(), null);
    }

    private ClaimableBenefitTableEntry getClaimableBenefitTableEntry() {
        return (ClaimableBenefitTableEntry) getCurrentRowObject();
    }

}
