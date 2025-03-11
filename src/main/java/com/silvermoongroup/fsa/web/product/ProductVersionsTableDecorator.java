/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.product;

import com.silvermoongroup.businessservice.policymanagement.dto.ProductVersionDTO;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.datatype.Period;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;

/**
 * Decorates product version information
 *
 * @author Justin Walsh
 */
public class ProductVersionsTableDecorator extends AbstractTableDecorator {

    public Object getId() {
        return generateIdentityDivWithObjectReferenceTooltip(getProductVersion().getObjectReference());
    }

    public Object getType() {
        return generateTypeDivWithObjectReferenceTooltip(getProductVersion().getObjectReference());
    }

    public Object getBusinessEffectivePeriod() {
        Period<Date> businessEffectivePeriod = getProductVersion().getBusinessEffectivePeriod();
        return formatDate(businessEffectivePeriod.getStart()) + " &rarr; " + formatDate(businessEffectivePeriod.getEnd());
    }

    public Object getTransactionEffectivePeriod() {
        Period<DateTime> txPeriod = getProductVersion().getTransactionEffectivePeriod();
        return formatDateTime(txPeriod.getStart()) + " &rarr; " + formatDateTime(txPeriod.getEnd());
    }

    private ProductVersionDTO getProductVersion() {
        return (ProductVersionDTO) getCurrentRowObject();
    }
}
