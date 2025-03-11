/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.ftx;

import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;
import com.silvermoongroup.ftx.domain.intf.IFinancialTransaction;

/**
 * Decorator for a table containing financial transactions.
 *
 * @author Justin Walsh
 */
public class FinancialTransactionTableDecorator extends AbstractTableDecorator {

    public Object getId() {
        return generateIdentityDivWithObjectReferenceTooltip(getFinancialTransaction().getObjectReference());
    }

    public Object getType() {
        IFinancialTransaction ftx = getFinancialTransaction();
        return generateTypeDivWithObjectReferenceTooltip(ftx.getObjectReference());
    }

    public Object getAmount() {
        return formatAmount(getFinancialTransaction().getAmount());
    }

    public Object getPostedDate() {
        return formatDateTime(getFinancialTransaction().getPostedDate());
    }

    public Object getPaymentMethod() {
        if (getFinancialTransaction().getPaymentMethod() != null) {
            EnumerationReference meansOfPaymentEnumRef = getFinancialTransaction().getPaymentMethod().getTheMethod();
            return formatEnum(meansOfPaymentEnumRef);
        }
        return null;
    }

    public Object getContextType() {
        IFinancialTransaction ftx = getFinancialTransaction();
        if (ftx.getContext() != null) {
            return generateTypeDivWithObjectReferenceTooltip(ftx.getContext());
        }
        return null;
    }

    private IFinancialTransaction getFinancialTransaction() {
        return (IFinancialTransaction) getCurrentRowObject();
    }
}
