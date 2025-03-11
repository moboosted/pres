/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.fund;

import com.silvermoongroup.account.domain.intf.IAssetPrice;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;

/**
 * @author Justin Walsh
 */
public class AssetPriceTableDecorator extends AbstractTableDecorator {

    public Object getId() {
        return generateIdentityDivWithObjectReferenceTooltip(getAssetPrice().getObjectReference());
    }

    public Object getType() {
        return generateTypeDivWithObjectReferenceTooltip(getAssetPrice().getObjectReference());
    }

    public Object getStartDate() {
        return formatDateTime(getAssetPrice().getStartDate());
    }

    public Object getEndDate() {
        return formatDateTime(getAssetPrice().getEndDate());
    }

    public String getFundCode()
    {
        return getAssetPrice().getFinancialAsset().getFundCode();
    }

    public Object getPriceType() {
        EnumerationReference priceType = getAssetPrice().getPriceType();
        return formatEnum(priceType);
    }

    public Object getPrice() {
        EnumerationReference currencyCode = getAssetPrice().getPrice().getCurrencyCode();
        return formatEnum(currencyCode) + " " + String.valueOf(getAssetPrice().getPrice().getAmount().doubleValue());
    }

    public Object getFinancialAssetName() {
        return getAssetPrice().getFinancialAsset().getName();
    }

    public Object getFinancialAssetDescription() {
        return getAssetPrice().getFinancialAsset().getDescription();
    }

    private IAssetPrice getAssetPrice() {
        return (IAssetPrice) getCurrentRowObject();
    }
}
