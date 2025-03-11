/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.fund;

import com.silvermoongroup.account.domain.intf.IFund;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;

/**
 * Decorator for the display of fund search results
 * 
 * @author Justin Walsh
 */
public class FundSearchResultsTableDecorator extends AbstractTableDecorator {
    
    public Object getName() {
        IFund currentRowObject = (IFund) getCurrentRowObject();
        return currentRowObject.getName();
    }
    
    public Object getDescription() {
        IFund currentRowObject = (IFund) getCurrentRowObject();
        return currentRowObject.getDescription();
    }
    
    public Object getStartDate() {
        IFund currentRowObject = (IFund) getCurrentRowObject();
        return formatDate(currentRowObject.getStartDate());
    }
    
    public Object getEndDate() {
        IFund currentRowObject = (IFund) getCurrentRowObject();
        if (currentRowObject.getEndDate() == null) {
            return "&infin;";
        }
        return formatDate(currentRowObject.getEndDate());
    }

    public Object getCurrencyCode() {
        IFund currentRowObject = (IFund) getCurrentRowObject();
        EnumerationReference currencyCode = currentRowObject.getCurrencyCode();
        return formatEnum(currencyCode);
    }

}
