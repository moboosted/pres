/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.ftx.moneyprovision;

import com.silvermoongroup.account.domain.intf.IAccountBalance;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.common.callback.CallBack;
import com.silvermoongroup.fsa.web.common.callback.CallBackUtility;
import com.silvermoongroup.fsa.web.ftx.moneyprovision.vo.MPESeriesVO;
import com.silvermoongroup.fsa.web.ftx.moneyprovision.vo.MPESingleVO;
import com.silvermoongroup.fsa.web.ftx.moneyprovision.vo.MoneyProvisionVO;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import com.silvermoongroup.ftx.domain.MoneyProvisionElement;
import com.silvermoongroup.ftx.domain.SeriesOfCashFlows;
import com.silvermoongroup.ftx.domain.SingleCashFlow;
import com.silvermoongroup.ftx.domain.intf.IAccountBasedMoneyProvision;
import com.silvermoongroup.ftx.domain.intf.IMoneyProvision;
import com.silvermoongroup.ftx.domain.intf.IParticularMoneyProvision;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MoneyProvisionAction extends AbstractLookupDispatchAction {

    private static final String ACCOUNT_BASED_MONEY_PROVISION = "accountBasedMoneyProvision";
    private static final String PARTICULAR_MONEY_PROVISION = "particularMoneyProvision";

    public MoneyProvisionAction() {
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.moneyprovision.action.applyfilter", "applyFilter");
        return map;
    }

    /**
     * Back to the agreement GUI
     */
    public ActionForward back(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        CallBack callBack = CallBackUtility.getCallBack(httpServletRequest, httpServletResponse);
        return CallBackUtility.getForwardAction(callBack);
    }

    public ActionForward defaultExecute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        MoneyProvisionForm form = (MoneyProvisionForm) actionForm;

        IMoneyProvision moneyProvision = getFinancialManagementService()
                .getMoneyProvision(getApplicationContext(), form.getMoneyProvisionObjectReference());

        populateFormData(form, moneyProvision);
        populateMoneyProvisionAndElements(form, moneyProvision);

        if (form.isAccountBasedProvision()) {
            return mapping.findForward(ACCOUNT_BASED_MONEY_PROVISION);
        } else {
            return mapping.findForward(PARTICULAR_MONEY_PROVISION);
        }
    }

    /**
     * Apply filtering of the money provision elements
     */
    public ActionForward applyFilter(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ActionRedirectFactory.createRedirect(mapping.findForwardConfig("redirect"), actionForm);
    }

    private void populateMoneyProvisionAndElements(MoneyProvisionForm form, IMoneyProvision moneyProvision) {

        DatePeriod filterPeriod =
                new DatePeriod(
                        StringUtils.trimToNull(form.getFilterDateFrom()) == null ? Date.PAST : parseDate(form.getFilterDateFrom()),
                        StringUtils.trimToNull(form.getFilterDateTo()) == null ? Date.FUTURE : parseDate(form.getFilterDateTo()).plus(1));

        FilterByType selectedFilterType = (form.getFilterType() == null ? null : FilterByType.valueOf(form.getFilterType()));

        if (moneyProvision instanceof IParticularMoneyProvision) {

            form.setAccountBasedProvision(false);
            IParticularMoneyProvision mp = (IParticularMoneyProvision) moneyProvision;
            List<MoneyProvisionElement> mpes = mp.getMoneyProvisionElements();

            for (MoneyProvisionElement mpe : mpes) {

                boolean includeMPE;
                if (mpe instanceof SeriesOfCashFlows) {

                    SeriesOfCashFlows series = (SeriesOfCashFlows) mpe;

                    DatePeriod datePeriod = DatePeriod.FOREVER;
                    if (FilterByType.STARTENDDATE.equals(selectedFilterType)) {
                        datePeriod = new DatePeriod(series.getStartDate(), series.getEndDate());

                    } else if (FilterByType.DUEDATE.equals(selectedFilterType)) {
                        datePeriod = new DatePeriod(series.getNextDueDate(), series.getNextDueDate().plus(1));
                    }

                    includeMPE = filterPeriod.overlaps(datePeriod);

                    if (includeMPE) {

                        MPESeriesVO vo = new MPESeriesVO();
                        vo.setObjectReference(series.getObjectReference());
                        vo.setType(formatType(series.getObjectReference()));
                        vo.setTargetFinancialTransactionType(
                                String.valueOf(
                                        getTypeFormatter().formatType(series.getTargetFinancialTransactionTypeId())
                        ));
                        vo.setStartDate(formatDate(series.getStartDate()));
                        vo.setEndDate(formatDate(series.getEndDate()));
                        EnumerationReference frequency = series.getFrequency();
                        vo.setFrequency(formatEnum(frequency));
                        vo.setAdvanceArrearsIndicator(formatEnum(series.getAdvanceArrearsIndicator()));
                        vo.setNextDueDate(formatDate(series.getNextDueDate()));
                        vo.setRunDate(formatDate(series.getRunDate()));
                        vo.setAnniversaryDate(formatDate(series.getAnniversaryDate()));

                        vo.setBaseAmount(series.getBaseAmount().getAmount());

                        EnumerationReference currencyCode = series.getBaseAmount().getCurrencyCode();
                        vo.setCurrencyCode(formatEnum(currencyCode));
                        form.getSeriesOfCashFlows().add(vo);
                    }

                } else if (mpe instanceof SingleCashFlow) {

                    SingleCashFlow single = (SingleCashFlow) mpe;

                    if (FilterByType.DUEDATE.equals(selectedFilterType)) {
                        DatePeriod datePeriod = new DatePeriod(single.getDueDate(), single.getDueDate().plus(1));
                        includeMPE = filterPeriod.overlaps(datePeriod);
                    } else {
                        includeMPE = true;
                    }

                    if (includeMPE) {

                        MPESingleVO vo = new MPESingleVO();
                        vo.setObjectReference(single.getObjectReference());
                        vo.setType(formatType(single.getObjectReference()));
                        vo.setTargetFinancialTransactionType(
                                String.valueOf(
                                        getTypeFormatter().formatType(single.getTargetFinancialTransactionTypeId())
                        ));
                        vo.setContextType(formatType(mp.getContext()));
                        vo.setDueDate(formatDate(single.getDueDate()));
                        vo.setRunDate(formatDate((single.getRunDate())));
                        vo.setBaseAmount(single.getBaseAmount().getAmount());

                        EnumerationReference currencyCode = single.getBaseAmount().getCurrencyCode();
                        vo.setCurrencyCode(formatEnum(currencyCode));
                        vo.setAdvanceArrearsIndicator(formatEnum(single.getAdvanceArrearsIndicator()));

                        form.getSingleCashFlows().add(vo);
                    }

                } else {
                    throw new IllegalStateException("Unknown MoneyProvisionElement type: " + mp);
                }
            }
        } else if (moneyProvision instanceof IAccountBasedMoneyProvision) {

            IAccountBasedMoneyProvision mp = (IAccountBasedMoneyProvision) moneyProvision;

            form.setAccountBasedProvision(true);

            if (mp.getAccount() != null) {
                IAccountBalance balance =
                        getFinancialManagementService().getAccountBalanceOnPostedDate(getApplicationContext(), mp.getAccount().getObjectReference());

                form.setAccountBalance(formatAmount(balance.getAmount()));
                form.setDebitCreditIndicator(form.getDebitCreditIndicator());
            }
        } else {
            throw new IllegalStateException("Unknown MoneyProvision type");
        }
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm actionForm, HttpServletRequest request) throws Exception {

        MoneyProvisionForm form = (MoneyProvisionForm) actionForm;

        IMoneyProvision moneyProvision = getFinancialManagementService()
                .getMoneyProvision(getApplicationContext(), form.getMoneyProvisionObjectReference());

        populateFormData(actionForm, moneyProvision);
    }

    private void populateFormData(ActionForm actionForm, IMoneyProvision moneyProvision) {

        MoneyProvisionForm form = (MoneyProvisionForm) actionForm;

        form.setStatusDate(Date.today());

        MoneyProvisionVO mpVO = new MoneyProvisionVO();
        mpVO.setObjectReference(moneyProvision.getObjectReference());
        mpVO.setStartDate(formatDate(moneyProvision.getStartDate()));
        mpVO.setEndDate(formatDate(moneyProvision.getEndDate()));
        mpVO.setDescription(moneyProvision.getDescription());
        EnumerationReference internalCoCode = moneyProvision.getInternalCompanyCode();
        mpVO.setInternalCompanyCode(formatEnum(internalCoCode));
        com.silvermoongroup.ftx.domain.MoneyProvisionStatus status = moneyProvision.getStatus(form.getStatusDate());
        if (status != null) {
            mpVO.setStatus(formatEnum(status.getState()));
        }
        form.setMoneyProvision(mpVO);


        LabelValueBean filterByStartEndDate = new LabelValueBean(
                formatMessage("page.moneyprovision.label.filterbystartenddate"), FilterByType.STARTENDDATE.name());
        LabelValueBean filterByDueDate = new LabelValueBean(
                formatMessage("page.moneyprovision.label.filterbyduedate"), FilterByType.DUEDATE.name());
        form.getFilterTypeOptions().add(filterByStartEndDate);
        form.getFilterTypeOptions().add(filterByDueDate);

        LabelValueBean groupByFrequency = new LabelValueBean(
                formatMessage("page.moneyprovision.label.groupbyfrequency"), GroupByType.FREQUENCY.name());
        LabelValueBean groupByDueDate = new LabelValueBean(
                formatMessage("page.moneyprovision.label.groupbyduedate"), FilterByType.DUEDATE.name());
        form.getGroupByOptions().add(new LabelValueBean("", ""));
        form.getGroupByOptions().add(groupByFrequency);
        form.getGroupByOptions().add(groupByDueDate);

        // for series of cash flows
        int defaultSeriesSortColumn = 1;
        int defaultSingleSortColumn = 1;
        if (GroupByType.FREQUENCY.name().equals(form.getGroupBy())) {
            defaultSeriesSortColumn = 2;
            defaultSingleSortColumn = 1;
        } else if (GroupByType.DUEDATE.name().equals(form.getGroupBy())) {
            defaultSeriesSortColumn = 8;
            defaultSingleSortColumn = 3;
        }
        form.setSeriesDefaultSortColumn(defaultSeriesSortColumn);
        form.setSingleDefaultSortColumn(defaultSingleSortColumn);
    }

    private enum FilterByType {STARTENDDATE, DUEDATE}

    private enum GroupByType {FREQUENCY, DUEDATE}
}
