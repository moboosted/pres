/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.account.util;

import com.silvermoongroup.account.criteria.AccountEntrySearchCriteria;
import com.silvermoongroup.account.domain.MonetaryAccountBalance;
import com.silvermoongroup.account.domain.intf.IAccountBalance;
import com.silvermoongroup.account.domain.intf.IMonetaryAccount;
import com.silvermoongroup.account.enumeration.DebitCreditIndicator;
import com.silvermoongroup.businessservice.financialmanagement.dto.AccountEntryDTO;
import com.silvermoongroup.businessservice.financialmanagement.service.intf.IFinancialManagementService;
import com.silvermoongroup.common.datatype.CurrencyAmount;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.ObjectReference;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountEnquiryAgeing {

    public AccountEnquiryAgeing(IFinancialManagementService financialManager) {
        this.financialManager = financialManager;
    }

    private IFinancialManagementService financialManager;
    boolean flag = false;

    private IFinancialManagementService getFinancialManager() {
        return financialManager;
    }

    public List<IAccountBalance<CurrencyAmount>> accountAgingList(ApplicationContext appContext, ObjectReference objectReference,
            Date postedDateTo) {

        List<IAccountBalance<CurrencyAmount>> accountAgingList = new ArrayList<>();
        IAccountBalance<CurrencyAmount> accountBalance;
        flag = false;

        IMonetaryAccount account = getFinancialManager().getMonetaryAccount(appContext, objectReference);

        CurrencyAmount zeroCurrencyAmount = new CurrencyAmount(String.valueOf(0), account.getCurrencyCode());

        Date dateRangeTo;
        if (postedDateTo != null) {
            dateRangeTo = postedDateTo;

        } else {
            dateRangeTo = appContext.getKnowledgeDate().getDate();
        }

        AccountEntrySearchCriteria criteria = new AccountEntrySearchCriteria();
        criteria.setAccountObjectReference(account.getObjectReference());

        List<AccountEntryDTO> accountEntries = getFinancialManager().findAccountEntries(
                appContext, criteria);

        // Current Ageing
        for (int days = 30; days <= 120; days = days + 30) {
            accountBalance = getAgingBalance(account, accountEntries, dateRangeTo);
            dateRangeTo = dateRangeTo.minus(30);
            accountAgingList.add(getAccountBalanceToTwoDecimal(accountBalance));
        }

        // 120 Days Ageing
        if (!flag) {
            appContext.setPeriodOfInterest(null);
            appContext.setKnowledgeDate(new DateTime(dateRangeTo));
            accountBalance = getBalanceToTillDate(account, accountEntries, DatePeriod.upTo(dateRangeTo));
            accountAgingList.add(getAccountBalanceToTwoDecimal(accountBalance));
        } else {
            accountAgingList.add(new MonetaryAccountBalance(zeroCurrencyAmount, DebitCreditIndicator.DEBIT));
        }
        return accountAgingList;
    }

    private IAccountBalance<CurrencyAmount> getAgingBalance(IMonetaryAccount account, List<AccountEntryDTO> accountEntries, Date dateRangeTo) {

        IAccountBalance<CurrencyAmount> accountBalance = null;

        CurrencyAmount zerocurrencyAmount = new CurrencyAmount(String.valueOf(0), account.getCurrencyCode());
        if (flag) {
            accountBalance = new MonetaryAccountBalance(zerocurrencyAmount, DebitCreditIndicator.DEBIT);
            return accountBalance;
        }
        for (int i = 0; i < 30; i++) {
            accountBalance = getBalanceToTillDate(account, accountEntries, DatePeriod.upTo(dateRangeTo.minus(i + 1))); //
            if (accountBalance.getDebitCreditIndicator().equals(DebitCreditIndicator.CREDIT)
                    || accountBalance.getAmount().equals(zerocurrencyAmount)) {
                accountBalance = getDebitAccountBalance(account, accountEntries, dateRangeTo.minus(i));
                flag = true;
                break;
            }
        }

        if (!flag) {
            DatePeriod datePeriod = new DatePeriod(dateRangeTo.minus(29), dateRangeTo.plus(1)); //
            accountBalance = getBalanceToTillDate(account, accountEntries, datePeriod);
        }

        return accountBalance;
    }

    private IAccountBalance<CurrencyAmount> getDebitAccountBalance(IMonetaryAccount account, List<AccountEntryDTO> accountEntries, Date dateRangeTo) {
        IAccountBalance<CurrencyAmount> accountBalance = null;
        for (int i = 1; i <= 30; i++) {
            accountBalance = getBalanceToTillDate(account, accountEntries, DatePeriod.upTo(dateRangeTo.plus(i)));
            if (accountBalance.getDebitCreditIndicator().equals(DebitCreditIndicator.DEBIT)) {
                break;
            }
        }
        return accountBalance;
    }

    private IAccountBalance<CurrencyAmount> getBalanceToTillDate(IMonetaryAccount account, List<AccountEntryDTO> accountEntries, DatePeriod period) {
        double sum;
        double dbAmount = 0;
        double crAmount = 0;
        EnumerationReference currencyCode = account.getCurrencyCode();

        for (AccountEntryDTO entry : accountEntries) {
            if (period.contains(entry.getPostedDate().getDate())) {
                if (DebitCreditIndicator.DEBIT.equals(entry.getDebitCreditIndicator())) {
                    dbAmount += entry.getAmount().getAmount().doubleValue();
                } else {
                    crAmount += entry.getAmount().getAmount().doubleValue();
                }
            }
        }
        sum = crAmount - dbAmount;
        DebitCreditIndicator debitCreditIndicator = sum > 0 ? DebitCreditIndicator.CREDIT : DebitCreditIndicator.DEBIT;
        return new MonetaryAccountBalance(new CurrencyAmount(String.valueOf(Math.abs(sum)), currencyCode), debitCreditIndicator);
    }

    private IAccountBalance<CurrencyAmount> getAccountBalanceToTwoDecimal(IAccountBalance<CurrencyAmount> accountbalance) {

        BigDecimal amount = accountbalance.getAmount().getAmount();
        accountbalance.setAmount(new CurrencyAmount(amount, accountbalance.getAmount()
                .getCurrencyCode()));
        return accountbalance;
    }

}
