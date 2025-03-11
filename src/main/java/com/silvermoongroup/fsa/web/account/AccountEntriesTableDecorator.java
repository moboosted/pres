/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.account;

import com.silvermoongroup.account.domain.intf.IAccountEntry;
import com.silvermoongroup.account.enumeration.DebitCreditIndicator;
import com.silvermoongroup.businessservice.financialmanagement.dto.AccountEntryDTO;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;

/**
 * Decorator for the account entries table
 * 
 * @author Justin Walsh
 */
public class AccountEntriesTableDecorator extends AbstractTableDecorator {

    public String getTransactionType() {
        AccountEntryDTO accountEntry = getAccountEntryDTO();
        ObjectReference generatingTransaction = null;
        if (accountEntry.getFinancialTransaction() != null) {
            generatingTransaction = accountEntry.getFinancialTransaction();
        }
        else if (accountEntry.getUnitTransaction() != null) {
            generatingTransaction = accountEntry.getUnitTransaction();
        }

        if (generatingTransaction != null) {
            return generateTypeDivWithObjectReferenceTooltip(generatingTransaction);
        }
        return null;
    }

    public Object getPostedDate() {
        AccountEntryDTO currentRowObject = getAccountEntryDTO();
        return formatDateTime(currentRowObject.getPostedDate());
    }

    public Object getValueDate() {
        AccountEntryDTO currentRowObject = getAccountEntryDTO();
        return formatDate(currentRowObject.getValueDate());
    }

    public Object getDebitCreditIndicator() {
        AccountEntryDTO currentRowObject = getAccountEntryDTO();
        return currentRowObject.getDebitCreditIndicator();
    }

    public Object getId() {
        return generateIdentityDivWithObjectReferenceTooltip(getAccountEntryDTO().getObjectReference());
    }

    public Object getType() {
        AccountEntryDTO currentRowObject = getAccountEntryDTO();
        return generateTypeDivWithObjectReferenceTooltip(currentRowObject.getObjectReference());
    }

    public Object getAccountType() {
        AccountEntryDTO currentRowObject = getAccountEntryDTO();
        return generateTypeDivWithObjectReferenceTooltip(currentRowObject.getAccount());
    }

    public Object getAccountName() {
        AccountEntryDTO currentRowObject = getAccountEntryDTO();
        return generateDivWithObjectReferenceTooltip(currentRowObject.getAccount(),
                currentRowObject.getAccountName());

    }

    public Object getDebitEntry() {
        AccountEntryDTO currentRowObject = getAccountEntryDTO();
        if (currentRowObject.getDebitCreditIndicator().equals(DebitCreditIndicator.DEBIT)) {
            return currentRowObject.getAmount();
        }
        return null;
    }

    public Object getCreditEntry() {
        AccountEntryDTO currentRowObject = getAccountEntryDTO();
        if (currentRowObject.getDebitCreditIndicator().equals(DebitCreditIndicator.CREDIT)) {
            return currentRowObject.getAmount();
        }
        return null;
    }

    private IAccountEntry getAccountEntry() {
        return (IAccountEntry) getCurrentRowObject();
    }

    private AccountEntryDTO getAccountEntryDTO() {
        return (AccountEntryDTO) getCurrentRowObject();
    }

}
