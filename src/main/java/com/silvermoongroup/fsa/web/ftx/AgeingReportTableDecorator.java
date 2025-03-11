/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.ftx;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.enumeration.UnitOfMeasure;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;
import com.silvermoongroup.ftx.domain.AgeingReportEntry;

/**
 * Decorator for the trial balance table.
 *
 * @author Justin Walsh
 */
public class AgeingReportTableDecorator extends AbstractTableDecorator {

    public Object getUnitOfMeasure() {
        AgeingReportEntry currentRowObject = (AgeingReportEntry) getCurrentRowObject();
        // TODO until we have a common enum parent class (LUNOS-4), we have to work this out manually
        if (currentRowObject.getUnitOfMeasure() != null) {
            return formatEnum(currentRowObject.getUnitOfMeasure());
        }
        else {
            return formatEnum(UnitOfMeasure.UNITS);
        }
    }

    public Object getBalanceNinetyDays() {
        AgeingReportEntry currentRowObject = (AgeingReportEntry) getCurrentRowObject();
        return formatAmount(currentRowObject.getBalanceNinetyDays());
    }

    public Object getOpenNinetyDays() {
        AgeingReportEntry currentRowObject = (AgeingReportEntry) getCurrentRowObject();
        return formatAmount(currentRowObject.getOpenNinetyDays());
    }

    public Object getOpenSixtyDays() {
        AgeingReportEntry currentRowObject = (AgeingReportEntry) getCurrentRowObject();
        return formatAmount(currentRowObject.getOpenSixtyDays());
    }

    public Object getOpenThirtyDays() {
        AgeingReportEntry currentRowObject = (AgeingReportEntry) getCurrentRowObject();
        return formatAmount(currentRowObject.getOpenThirtyDays());
    }

    public Object getOpenCurrent() {
        AgeingReportEntry currentRowObject = (AgeingReportEntry) getCurrentRowObject();
        return formatAmount(currentRowObject.getOpenCurrent());
    }

    public Object getAccountId() {
        AgeingReportEntry currentRowObject = (AgeingReportEntry) getCurrentRowObject();
        return currentRowObject.getAccountId();
    }

    public Object getId() {
        return generateIdentityDivWithObjectReferenceTooltip(getAccountObjectReference());
    }

    public Object getAccountType() {
        AgeingReportEntry currentRowObject = (AgeingReportEntry) getCurrentRowObject();
        return formatType(currentRowObject.getAccountTypeId());
    }

    private ObjectReference getAccountObjectReference() {
        AgeingReportEntry currentRowObject = (AgeingReportEntry) getCurrentRowObject();
        return currentRowObject.getAccountObjectReference();
    }

}
