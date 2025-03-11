/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.account;

import com.silvermoongroup.account.domain.intf.IAccount;
import com.silvermoongroup.account.domain.intf.IMonetaryAccount;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;

/**
 * Decorator for the account form.
 * 
 * @author Justin Walsh
 */
public class AccountTableDecorator extends AbstractTableDecorator {

    public Object getId() {
        return generateIdentityDivWithObjectReferenceTooltip(getAccount().getObjectReference());
    }

    public Object getType() {
        return generateTypeDivWithObjectReferenceTooltip(getAccount().getObjectReference());
    }

    public Object getOpeningDate() {
        return formatDate(getAccount().getOpeningDate());
    }

    public Object getClosingDate() {
        return formatDate(getAccount().getClosingDate());
    }

    public Object getCurrencyCode() {
        if (IMonetaryAccount.class.isAssignableFrom(getAccount().getClass())) {
            EnumerationReference currencyCode = ((IMonetaryAccount) getAccount()).getCurrencyCode();
            return formatEnum(currencyCode);
        } else {
            return "";
        }
    }

    public Object getInternalCompanyCode() {
        EnumerationReference internalCoCode = getAccount().getInternalCompanyCode();
        return formatEnum(internalCoCode);
    }

    private IAccount getAccount() {
        return (IAccount) getCurrentRowObject();
    }
}
