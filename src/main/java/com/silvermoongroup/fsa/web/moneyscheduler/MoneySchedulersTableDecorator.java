/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.moneyscheduler;

import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;
import com.silvermoongroup.ftx.domain.intf.IMoneyScheduler;

/**
 * @author Justin Walsh
 */
public class MoneySchedulersTableDecorator extends AbstractTableDecorator {

    public Object getId() {
        return generateIdentityDivWithObjectReferenceTooltip(getMoneyScheduler().getObjectReference());
    }

    public Object getType() {
        return generateTypeDivWithObjectReferenceTooltip(getMoneyScheduler().getObjectReference());
    }

    public Object getStartDate() {
        return formatDate(getMoneyScheduler().getStartDate());
    }

    public Object getEndDate() {
        return formatDate(getMoneyScheduler().getEndDate());
    }

    public Object getAnniversaryDate() {
        return formatDate(getMoneyScheduler().getAnniversaryDate());
    }

    public Object getNextRunDate() {
        return formatDate(getMoneyScheduler().getNextRunDate());
    }

    public Object getAnniversaryFixingType() {
        return formatEnum(getMoneyScheduler().getAnniversaryFixingType());
    }

    public Object getFrequency() {
        EnumerationReference frequency = getMoneyScheduler().getFrequency();
        return formatEnum(frequency);
    }

    private IMoneyScheduler getMoneyScheduler() {
        return ((IMoneyScheduler) getCurrentRowObject());
    }
}
