/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.account.accountmapping;

import com.silvermoongroup.account.domain.intf.IAccountMapping;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;

/**
 * Decorator for the account mapping table
 * 
 * @author Justin Walsh
 */
public class AccountMappingTableDecorator extends AbstractTableDecorator {
    
    public Object getCompanyCode() {
        IAccountMapping currentRowObject = (IAccountMapping)getCurrentRowObject();
        EnumerationReference internalCoCode = currentRowObject.getInternalCompanyCode();
        return matchesAllIfNull(formatEnum(internalCoCode));
    }
    
    public Object getContextType() {
        IAccountMapping currentRowObject = (IAccountMapping)getCurrentRowObject();
        return matchesAllIfNull(formatType(currentRowObject.getContextTypeId()));
    }
    
    public Object getFinancialTransactionType() {
        IAccountMapping currentRowObject = (IAccountMapping)getCurrentRowObject();
        return matchesAllIfNull(formatType(currentRowObject.getTransactionTypeId()));
    }
    
    public Object getCurrencyCode() {
        IAccountMapping currentRowObject = (IAccountMapping)getCurrentRowObject();
        
        EnumerationReference currencyCode = currentRowObject.getCurrencyCode();
        return matchesAllIfNull(formatEnum(currencyCode));
    }
    
    public Object getMeansOfPayment() {
        IAccountMapping currentRowObject = (IAccountMapping)getCurrentRowObject();
        
        EnumerationReference meansOfPayment = currentRowObject.getMeansOfPayment();
        return matchesAllIfNull(formatEnum(meansOfPayment));
    }
    
    public Object getFrequency() {
        IAccountMapping currentRowObject = (IAccountMapping) getCurrentRowObject();

        EnumerationReference frequency = currentRowObject.getFrequency();
        return matchesAllIfNull(formatEnum(frequency));
    }
    
    public Object getMappingDirection() {
        IAccountMapping currentRowObject = (IAccountMapping)getCurrentRowObject();
        return matchesAllIfNull(formatEnum(currentRowObject.getMappingDirection()));
    }
    
    public Object getAccount() {
        IAccountMapping currentRowObject = (IAccountMapping)getCurrentRowObject();
        return currentRowObject.getAccount().getId();
    }
    
    public Object matchesAllIfNull(Object rowValue) {
        if (rowValue == null) {
            return formatMessage("page.findaccountmapping.label.matchesall");
        }
        return rowValue;
    }
}
