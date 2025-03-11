/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.fund;

import com.silvermoongroup.account.domain.intf.IAssetOperatingFee;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;

/**
 * @author Justin Walsh
 */
public class AssetOperatingFeeTableDecorator extends AbstractTableDecorator {

    public Object getId() {
        return generateIdentityDivWithObjectReferenceTooltip(getAssetOperatingFee().getObjectReference());
    }

    public Object getType() {
        return generateTypeDivWithObjectReferenceTooltip(getAssetOperatingFee().getObjectReference());
    }

    public Object getStartDate() {
        return formatDate(getAssetOperatingFee().getStartDate());
    }

    public Object getEndDate() {
        return formatDate(getAssetOperatingFee().getEndDate());
    }

    public Object getFrequency() {
        return formatEnum(getAssetOperatingFee().getFrequency());
    }

    public Object getPercentage() {
        return formatAmount(getAssetOperatingFee().getPercentage());
    }

    private IAssetOperatingFee getAssetOperatingFee() {
        return (IAssetOperatingFee) getCurrentRowObject();
    }
}
