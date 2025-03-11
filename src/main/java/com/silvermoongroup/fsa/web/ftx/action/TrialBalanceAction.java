/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.ftx.action;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.ftx.form.TrialBalanceForm;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import com.silvermoongroup.ftx.criteria.TrialBalanceCalculationCriteria;
import com.silvermoongroup.ftx.domain.TrialBalance;
import com.silvermoongroup.ftx.domain.TrialBalanceControlEntry;
import com.silvermoongroup.ftx.domain.TrialBalanceEntry;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Trial balance action.
 *
 * @author Justin Walsh
 */
public class TrialBalanceAction extends AbstractLookupDispatchAction {

    public TrialBalanceAction() {
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.trialbalance.action.calculate", "calculate");
        return map;
    }

    /**
     * GET: Display the form for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        TrialBalanceForm form = (TrialBalanceForm) actionForm;
        form.setHideEntriesWithNoMovement(true);
        populateStaticPageElements(form, httpRequest);

        return actionMapping.getInputForward();
    }

    /**
     * POST: Redirect to display the results
     */
    public ActionForward calculate(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        return ActionRedirectFactory.createRedirect(actionMapping.findForwardConfig("calculateAndDisplay"), actionForm);
    }

    /**
     * Perform the calculation and display the results
     */
    public ActionForward calculateAndDisplay(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        TrialBalanceForm form = (TrialBalanceForm) actionForm;

        populateStaticPageElements(form, httpRequest);

        Date dateFrom = parseDate(form.getDateFrom());
        Date dateTo = parseDate(form.getDateTo()).plus(1);
        TrialBalanceCalculationCriteria.GroupBy groupBy = TrialBalanceCalculationCriteria.GroupBy.valueOf(form.getGroupBy());
        TrialBalanceCalculationCriteria.BalanceType balanceType = TrialBalanceCalculationCriteria.BalanceType.valueOf(form.getBalanceType());

        TrialBalanceCalculationCriteria calculationCriteria = new TrialBalanceCalculationCriteria();

        if (!GenericValidator.isBlankOrNull(form.getCompanyCode())) {
            calculationCriteria.setCompanyCode(EnumerationReference.convertFromString(form.getCompanyCode()));
        }

        calculationCriteria.setPeriodOfInterest(new DatePeriod(dateFrom, dateTo));
        calculationCriteria.setRootAccountTypeId(form.getRootAccountTypeId());
        calculationCriteria.setGroupBy(groupBy);
        calculationCriteria.setBalanceType(balanceType);

        ApplicationContext applicationContext = getApplicationContext();
        TrialBalance result =
                getFinancialManagementService().calculateTrialBalance(applicationContext, calculationCriteria);
        form.setTrialBalance(result);


        if (Boolean.TRUE.equals(form.getHideEntriesWithNoMovement())) {

            List<TrialBalanceEntry> entries = result.getEntries();
            List<TrialBalanceEntry> filteredList = new ArrayList<>();
            for (TrialBalanceEntry entry : entries) {
                if (entry.getDebitMovement().getAmount().compareTo(BigDecimal.ZERO) != 0 ||
                        entry.getCreditMovement().getAmount().compareTo(BigDecimal.ZERO) != 0) {
                    filteredList.add(entry);
                }
            }
            result.setEntries(filteredList);

            List<TrialBalanceControlEntry> controlEntries = result.getControlEntries();
            List<TrialBalanceControlEntry> filteredControlEntries = new ArrayList<>();
            for (TrialBalanceControlEntry controlEntry : controlEntries) {
                if (controlEntry.getDebitMovement().getAmount().compareTo(BigDecimal.ZERO) != 0 ||
                        controlEntry.getCreditMovement().getAmount().compareTo(BigDecimal.ZERO) != 0) {
                    filteredControlEntries.add(controlEntry);
                }
            }
            result.setControlEntries(filteredControlEntries);

        }

        // generate a file name
        String fileName = "TrialBalance-groupBy-" + groupBy.toString() + "-balanceType-" + balanceType.toString() + "-" ;
        if (dateFrom.equals(dateTo)) {
            form.setExportFilename(fileName + dateFrom.format("yyyyMMdd"));
        } else {
            form.setExportFilename(fileName + dateFrom.format("yyyyMMdd") + "-" + dateTo.format("yyyyMMdd"));
        }

        return actionMapping.getInputForward();
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm form, HttpServletRequest request) {
        populateStaticPageElements((TrialBalanceForm) form, request);
    }

    public void populateStaticPageElements(TrialBalanceForm form, HttpServletRequest httpRequest) {
        if (form.getGroupBy() == null) {
            form.setGroupBy(TrialBalanceCalculationCriteria.GroupBy.NAME.name());
        }
        if (form.getBalanceType() == null) {
            form.setBalanceType(TrialBalanceCalculationCriteria.BalanceType.POSTED_DATE.name());
        }
    }
}
