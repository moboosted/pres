/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.ftx;

import com.silvermoongroup.common.enumeration.UnitOfMeasure;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;
import com.silvermoongroup.ftx.domain.TrialBalanceEntry;

/**
 * Decorator for the trial balance table.
 * 
 * @author Justin Walsh
 */
public class TrialBalanceTableDecorator extends AbstractTableDecorator {

    public Object getUnitOfMeasure() {
        TrialBalanceEntry currentRowObject = (TrialBalanceEntry) getCurrentRowObject();
        // TODO until we have a common enum parent class (LUNOS-4), we have to work this out manually
        if (currentRowObject.getUnitOfMeasure() != null) {
            return formatEnum(currentRowObject.getUnitOfMeasure());
        }
        else {
            return formatEnum(UnitOfMeasure.UNITS);
        }
    }

    public Object getOpeningBalance() {
        TrialBalanceEntry currentRowObject = (TrialBalanceEntry) getCurrentRowObject();
        return formatAmount(currentRowObject.getOpeningBalance());
    }

    public Object getClosingBalance() {
        TrialBalanceEntry currentRowObject = (TrialBalanceEntry) getCurrentRowObject();
        return formatAmount(currentRowObject.getClosingBalance());
    }

    public Object getDebitMovement() {
        TrialBalanceEntry currentRowObject = (TrialBalanceEntry) getCurrentRowObject();
        return formatAmount(currentRowObject.getDebitMovement());
    }

    public Object getCreditMovement() {
        TrialBalanceEntry currentRowObject = (TrialBalanceEntry) getCurrentRowObject();
        return formatAmount(currentRowObject.getCreditMovement());
    }

    public Object getAccountType() {
        TrialBalanceEntry currentRowObject = (TrialBalanceEntry) getCurrentRowObject();
        return formatType(currentRowObject.getAccountTypeId());
    }
}
