/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.ftx.moneyprovision;

import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;
import com.silvermoongroup.ftx.domain.intf.IMoneyProvision;

/**
 * Decorates money provisions for use within a table.
 *
 * @author Justin Walsh
 */
public class MoneyProvisionTableDecorator extends AbstractTableDecorator {

    public Object getId() {
        return generateIdentityDivWithObjectReferenceTooltip(getMoneyProvision().getObjectReference());
    }

    public Object getType() {
        return generateTypeDivWithObjectReferenceTooltip(getMoneyProvision().getObjectReference());
    }

    public Object getStartDate() {
        return formatDate(getMoneyProvision().getStartDate());
    }

    public Object getEndDate() {
        return formatDate(getMoneyProvision().getEndDate());
    }

    public Object getCompanyCode() {
        EnumerationReference internalCoCode = getMoneyProvision().getInternalCompanyCode();
        return formatEnum(internalCoCode);
    }

    public Object getContext() {
        return generateTypeDivWithObjectReferenceTooltip(getMoneyProvision().getContext());
    }

    private IMoneyProvision getMoneyProvision() {
        return (IMoneyProvision) getCurrentRowObject();
    }
}
