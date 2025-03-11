package com.silvermoongroup.fsa.web.ftx.moneyprovision;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.ftx.moneyprovision.vo.MPESeriesVO;
import com.silvermoongroup.fsa.web.ftx.moneyprovision.vo.MPESingleVO;
import com.silvermoongroup.fsa.web.ftx.moneyprovision.vo.MoneyProvisionVO;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Form bean for the money provision action
 */
public class MoneyProvisionForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    @RedirectParameter
    private ObjectReference moneyProvisionObjectReference;
    
    @RedirectParameter
    private ObjectReference moneyProvisionContextObjectReference;
    
    @RedirectParameter
    private String filterType;
    
    @RedirectParameter
    private String filterDateFrom;
    
    @RedirectParameter
    private String filterDateTo;
    
    @RedirectParameter
    private String groupBy;
    
    @RedirectParameter
    private boolean showTotals;
    
    // when we group by a column, we need to set the default sort column to the same column
    private int seriesDefaultSortColumn;
    private int singleDefaultSortColumn;
    
    private List<LabelValueBean> filterTypeOptions = new ArrayList<>();
    private List<LabelValueBean> groupByOptions = new ArrayList<>();

    private Date statusDate;
    private MoneyProvisionVO moneyProvision;
    private boolean accountBasedProvision; 
    private List<MPESingleVO> singleCashFlows = new ArrayList<>();
    private List<MPESeriesVO> seriesOfCashFlows = new ArrayList<>();
    
    // for account based money provision
    private String debitCreditIndicator;
    private String accountBalance;
    
    public MoneyProvisionForm() {
    }
    
    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return "applyfilter".equals(actionName);
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String minimumAmount) {
        this.accountBalance = minimumAmount;
    }

    public ObjectReference getMoneyProvisionObjectReference() {
        return moneyProvisionObjectReference;
    }

    public ObjectReference getMoneyProvisionContextObjectReference() {
        return moneyProvisionContextObjectReference;
    }

    public void setMoneyProvisionObjectReference(ObjectReference rolePlayer) {
        this.moneyProvisionObjectReference = rolePlayer;
    }

    public void setMoneyProvisionContextObjectReference(ObjectReference rolePlayerContext) {
        this.moneyProvisionContextObjectReference = rolePlayerContext;
    }
    
    public void setMoneyProvision(MoneyProvisionVO moneyProvision) {
        this.moneyProvision = moneyProvision;
    }
    public MoneyProvisionVO getMoneyProvision() {
        return moneyProvision;
    }
    
    public List<MPESeriesVO> getSeriesOfCashFlows() {
        return seriesOfCashFlows;
    }
    public void setSeriesOfCashFlows(List<MPESeriesVO> seriesOfCashFlows) {
        this.seriesOfCashFlows = seriesOfCashFlows;
    }
    
    public List<MPESingleVO> getSingleCashFlows() {
        return singleCashFlows;
    }
    public void setSingleCashFlows(List<MPESingleVO> singleCashFlows) {
        this.singleCashFlows = singleCashFlows;
    }
    
    public void setAccountBasedProvision(boolean accountBasedProvision) {
        this.accountBasedProvision = accountBasedProvision;
    }
    
    /**
     * @return Is this provision an account based provision
     */
    public boolean isAccountBasedProvision() {
        return accountBasedProvision;
    }
    
    /**
     * The date at which temporal elements in the view are viewed at.  For example, we provide the status
     * at a specific date.
     * @return The date at which temporal elements in the view are rendered at
     * 
     */
    public Date getStatusDate() {
        return statusDate;
    }
    
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }
    
    public String getDebitCreditIndicator() {
        return debitCreditIndicator;
    }
    
    public void setDebitCreditIndicator(String debitCreditIndicator) {
        this.debitCreditIndicator = debitCreditIndicator;
    }

    /**
     * @return If set, MPEs with a date period overlapping with this date are included.
     */
    public String getFilterDateFrom() {
        return filterDateFrom;
    }

    public void setFilterDateFrom(String filterDateFrom) {
        this.filterDateFrom = filterDateFrom;
    }

    /**
     * @return If set, MPEs with a date period overlapping with this date are included.
     */
    public String getFilterDateTo() {
        return filterDateTo;
    }

    public void setFilterDateTo(String filterDateTo) {
        this.filterDateTo = filterDateTo;
    }

    /**
     * @return Which field is the selection grouped by
     */
    public String getGroupBy() {
        return groupBy;
    }
    
    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    /**
     * @return Should totals be shown?
     */
    public boolean isShowTotals() {
        return showTotals;
    }

    public void setShowTotals(boolean showTotals) {
        this.showTotals = showTotals;
    }
    
    public String getFilterType() {
        return filterType;
    }
    
    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }
    
    public void setGroupByOptions(List<LabelValueBean> groupByOptions) {
        this.groupByOptions = groupByOptions;
    }
    
    public List<LabelValueBean> getGroupByOptions() {
        return groupByOptions;
    }

    public List<LabelValueBean> getFilterTypeOptions() {
        return filterTypeOptions;
    }

    public void setFilterTypeOptions(List<LabelValueBean> filterTypeOptions) {
        this.filterTypeOptions = filterTypeOptions;
    }
    
    public void setSeriesDefaultSortColumn(int defaultSortColumn) {
        this.seriesDefaultSortColumn = defaultSortColumn;
    }
    
    public int getSeriesDefaultSortColumn() {
        return seriesDefaultSortColumn;
    }

    public int getSingleDefaultSortColumn() {
        return singleDefaultSortColumn;
    }
    
    public void setSingleDefaultSortColumn(int singleDefaultSortColumn) {
        this.singleDefaultSortColumn = singleDefaultSortColumn;
    }
}
