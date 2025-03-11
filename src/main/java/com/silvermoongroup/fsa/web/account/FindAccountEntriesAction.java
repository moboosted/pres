/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.account;

import com.silvermoongroup.account.criteria.AccountEntrySearchCriteria;
import com.silvermoongroup.account.enumeration.DebitCreditIndicator;
import com.silvermoongroup.businessservice.financialmanagement.dto.AccountEntryDTO;
import com.silvermoongroup.common.datatype.*;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Controller class to find Account Entries
 *
 * @author Justin Walsh
 */
public class FindAccountEntriesAction extends AbstractLookupDispatchAction {

    public FindAccountEntriesAction() {
    }

    /**
     * GET: Default
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        FindAccountEntriesForm form = (FindAccountEntriesForm) actionForm;
        populateStaticPageElements(form, httpRequest);

        return actionMapping.getInputForward();
    }

    /**
     * POST: Invoked when the user click the find button
     */
    public ActionForward find(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        return ActionRedirectFactory.createRedirect(actionMapping.findForward("findAndDisplay"), actionForm);
    }

    /**
     * GET: Redirect after post
     */
    public ActionForward findAndDisplay(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        FindAccountEntriesForm form = (FindAccountEntriesForm) actionForm;
        populateStaticPageElements(form, httpServletRequest);

        AccountEntrySearchCriteria criteria = buildCriteria(form);
        FindAccountEntriesList accountEntriesList = buildAccountEntriesList(criteria);
        accountEntriesList.setObjectsPerPage(form.getRowsPerPage());
        accountEntriesList.setPageNumber(getPage(httpServletRequest));

        form.setResults(accountEntriesList);

        calculateTotals(form);

        return actionMapping.getInputForward();
    }

    private FindAccountEntriesList buildAccountEntriesList(AccountEntrySearchCriteria criteria) {
        FindAccountEntriesList accountEntriesList = new FindAccountEntriesList();
        Long numberOfAccountEntries = getFinancialManagementService().getNumberOfAccountEntries(getApplicationContext(), criteria);
        accountEntriesList.setFullListSize(numberOfAccountEntries.intValue());
        List<AccountEntryDTO> entries = getFinancialManagementService().findAccountEntries(
                getApplicationContext(), criteria);
        accountEntriesList.setList(entries);
        return accountEntriesList;
    }

    private void calculateTotals(FindAccountEntriesForm form) {

        // the totals are based on the entire result set returned
        // The implication of this is that if we page, we should only calculate the total based on the view
        Amount debitTotal = null;
        Amount creditTotal = null;

        for (AccountEntryDTO ae : form.getResults().getPartialAccountEntryList()) {
            if (DebitCreditIndicator.DEBIT.equals(ae.getDebitCreditIndicator())) {
                debitTotal = debitTotal == null ? ae.getAmount() : debitTotal.add(ae.getAmount());
            } else if (DebitCreditIndicator.CREDIT.equals(ae.getDebitCreditIndicator())) {
                creditTotal = creditTotal == null ? ae.getAmount() : creditTotal.add(ae.getAmount());
            }
        }
        form.setDebitTotal(debitTotal == null ? new Amount(BigDecimal.ZERO) : debitTotal);
        form.setCreditTotal(creditTotal == null ? new Amount(BigDecimal.ZERO) : creditTotal);
    }


    private AccountEntrySearchCriteria buildCriteria(FindAccountEntriesForm form) {

        AccountEntrySearchCriteria criteria = new AccountEntrySearchCriteria();
        criteria.setAccountObjectReference(form.getAccountObjectReference());

        String postedDateOption = form.getPostedDateOption();
        if ("on".equals(postedDateOption)) {
            Date postedDateOn = getTypeParser().parseDate(form.getPostedDateOn());
            criteria.setPostedDatePeriod(new DateTimePeriod(new DateTime(postedDateOn), new DateTime(postedDateOn.plus(1))));
        } else if (postedDateOption.equals("between")) {
            Date postedDateStart = getTypeParser().parseDate(form.getPostedDateBetweenStart());
            Date postedDateEnd = getTypeParser().parseDate(form.getPostedDateBetweenEnd());
            criteria.setPostedDatePeriod(new DateTimePeriod(new DateTime(postedDateStart), new DateTime(postedDateEnd.plus(1))));

        }

        String valueDateOption = form.getValueDateOption();
        if ("on".equals(valueDateOption)) {
            Date valueDateOn = getTypeParser().parseDate(form.getValueDateOn());
            criteria.setValueDatePeriod(new DatePeriod(valueDateOn, new Date(valueDateOn.plus(1))));
        } else if ("between".equals(valueDateOption)) {
            Date valueDateStart = getTypeParser().parseDate(form.getValueDateBetweenStart());
            Date valueDateEnd = getTypeParser().parseDate(form.getValueDateBetweenEnd());
            criteria.setValueDatePeriod(new DatePeriod(valueDateStart, valueDateEnd.plus(1)));
        }
        if (form.getDebitCreditIndicator() != null && !form.getDebitCreditIndicator().isEmpty()) {
            DebitCreditIndicator indicator = DebitCreditIndicator.fromCode(Long.parseLong(form
                    .getDebitCreditIndicator()));
            criteria.setDebitCreditIndicator(indicator);
        }

        criteria.setMaximumAmount(form.getMaximumAmount());
        criteria.setMinimumAmount(form.getMinimumAmount());

        for (Long accountEntryTypeId : form.getSelectedAccountEntryTypes()) {
            criteria.addAccountEntryType(accountEntryTypeId);
        }

        criteria.setOrderBy(AccountEntrySearchCriteria.OrderBy.POSTED_DATE_ASC);

        criteria.getQueryDetails().setMaximumRecordsRequested(form.getRowsPerPage());
        criteria.getQueryDetails().setStartRecord(form.getStartRecord());

        return criteria;
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm form, HttpServletRequest request) {
        populateStaticPageElements((FindAccountEntriesForm) form, request);
    }

    private void populateStaticPageElements(FindAccountEntriesForm form, HttpServletRequest httpRequest) {

        form.setTableId("recentAccountEntryPostingsTable");
        form.setRowsPerPage(50);
        form.setStartRecord(getStartRecordForTable(httpRequest, form));

        if (form.getPostedDateOption() == null) {
            form.setPostedDateOption("any");
        }
        if (form.getValueDateOption() == null) {
            form.setValueDateOption("any");
        }

        Set<Long> accountEntryTypes = getFinancialManagementService().getAccountEntryTypes(
                getApplicationContext(), form.getAccountObjectReference()
        );
        for (Long accountEntryTypeId : accountEntryTypes) {
            form.addAccountEntryType(new LabelValueBean(getTypeFormatter().formatType(accountEntryTypeId), String.valueOf(accountEntryTypeId)));
        }
    }

    /**
     * Return the (zero based) index of the first record displayed in the given table.
     */
    private int getStartRecordForTable(HttpServletRequest httpServletRequest, FindAccountEntriesForm form) {
        int page = getPage(httpServletRequest);
        int startRecord = (page - 1) * form.getRowsPerPage();
        return (startRecord < 0) ? 0 : startRecord;
    }

    private int getPage(HttpServletRequest httpServletRequest){
        int pageParameterValue = ( null== httpServletRequest.getParameter("page")) ? 1 : Integer.parseInt(httpServletRequest.getParameter("page"));
        return pageParameterValue;
    }


}
