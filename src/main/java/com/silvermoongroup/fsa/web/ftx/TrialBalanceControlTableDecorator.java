/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.ftx;

import com.silvermoongroup.common.enumeration.UnitOfMeasure;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;
import com.silvermoongroup.ftx.domain.TrialBalanceControlEntry;

/**
 * Decorator for the trial balance control table.
 * 
 * @author Justin Walsh
 */
public class TrialBalanceControlTableDecorator extends AbstractTableDecorator {
    
    public Object getUnitOfMeasure() {
        TrialBalanceControlEntry currentRowObject = (TrialBalanceControlEntry) getCurrentRowObject();
        // TODO until we have a common enum parent class (LUNOS-4), we have to work this out manually
        if (currentRowObject.getUnitOfMeasure() != null) {
            return formatEnum(currentRowObject.getUnitOfMeasure());
        }
        else {
            return formatEnum(UnitOfMeasure.UNITS);
        }
    }
    
    public Object getDebitMovement() {
        TrialBalanceControlEntry currentRowObject = (TrialBalanceControlEntry) getCurrentRowObject();
        return formatAmount(currentRowObject.getDebitMovement());
    }

    public Object getCreditMovement() {
        TrialBalanceControlEntry currentRowObject = (TrialBalanceControlEntry) getCurrentRowObject();
        return formatAmount(currentRowObject.getCreditMovement());
    }
    
    public Object getNetMovement() {
        TrialBalanceControlEntry currentRowObject = (TrialBalanceControlEntry) getCurrentRowObject();
        return formatAmount(currentRowObject.getNetMovement());
    }
}
