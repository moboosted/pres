/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.ftx;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.datatype.DateTimePeriod;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import com.silvermoongroup.ftx.criteria.FinancialTransactionSearchCriteria;
import com.silvermoongroup.ftx.domain.intf.IFinancialTransaction;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Justin Walsh
 */
public class FindFinancialTransactionsAction extends AbstractLookupDispatchAction {

    /**
     * GET: Default
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        FindFinancialTransactionsForm form = (FindFinancialTransactionsForm) actionForm;
        populateStaticPageElements(form, httpRequest);

        return actionMapping.getInputForward();
    }

    /**
     * POST: Invoked when the user click the find button
     */
    public ActionForward find(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

//        getFinancialManagementService().find

        return ActionRedirectFactory.createRedirect(actionMapping.findForward("findAndDisplay"), actionForm);
    }

    /**
     * GET: Redirect after post
     */
    public ActionForward findAndDisplay(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        FindFinancialTransactionsForm form = (FindFinancialTransactionsForm) actionForm;

        FinancialTransactionSearchCriteria criteria = new FinancialTransactionSearchCriteria();
        if (form.getFinancialTransactionId() != null){
            criteria.addFinancialTransactionId(form.getFinancialTransactionId());
        }
        if (form.getFinancialTransactionTypeId() != null) {
            criteria.addFinancialTransactionTypeId(form.getFinancialTransactionTypeId());
        }
        criteria.setRestrictFinancialTransactionType(form.isRestrictType());
        String externalReference = StringUtils.trimToNull(form.getExternalReference());
        if (externalReference != null) {
            criteria.setExternalReference(externalReference);
        }

        // posted date
        String postedDateOption = form.getPostedDateOption();
        if (postedDateOption != null) {
            if (postedDateOption.equals("on")) {
                criteria.setPostedOnDay(parseDate(form.getPostedDate()));
            } else if (postedDateOption.equals("between")) {
                DateTime dateTimeFrom = new DateTime(getTypeParser().parseDate(form.getPostedDateFrom()));
                Date dateTo = getTypeParser().parseDate(form.getPostedDateTo());
                DateTime dateTimeTo = new DateTime(dateTo.plus(1));
                criteria.setPostedPeriod(new DateTimePeriod(dateTimeFrom, dateTimeTo));
            }
        }

        String amountOption = form.getAmountOption();
        if (amountOption != null) {
            if (amountOption.equals("exact")) {
                String amount = StringUtils.trimToNull(form.getAmount());
                criteria.setMinimumAmount(amount == null ? null : new BigDecimal(amount));
                criteria.setMaximimAmount(amount == null ? null : new BigDecimal(amount));
            }
            else if (amountOption.equals("between")) {
                String maximumAmount = StringUtils.trimToNull(form.getMaximumAmount());
                String minimumAmount = StringUtils.trimToNull(form.getMinimumAmount());
                criteria.setMaximimAmount(maximumAmount == null ? null : new BigDecimal(maximumAmount));
                criteria.setMinimumAmount(minimumAmount == null ? null : new BigDecimal(minimumAmount));
            }
        }

        if (!GenericValidator.isBlankOrNull(form.getCompanyCode())) {
            criteria.setCompanyCode(EnumerationReference.convertFromString(form.getCompanyCode()));
        }
        if (!GenericValidator.isBlankOrNull(form.getMeansOfPayment())) {
            criteria.setMeansOfPayment(EnumerationReference.convertFromString(form.getMeansOfPayment()));
        }

        List<IFinancialTransaction> financialTransactions = getFinancialManagementService().findFinancialTransactions(getApplicationContext(), criteria);
        form.setResults(financialTransactions);

        return actionMapping.getInputForward();
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm form, HttpServletRequest request) {
        populateStaticPageElements((FindFinancialTransactionsForm) form, request);
    }

    private void populateStaticPageElements(FindFinancialTransactionsForm form, HttpServletRequest httpRequest) {

        if (form.getAmountOption() == null) {
            form.setAmountOption("any");
        }
        if (form.getPostedDateOption() == null) {
            form.setPostedDateOption("any");
        }

    }
}
