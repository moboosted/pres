/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.request;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The form backing the request search page
 *
 * @author Naeem Ally
 * @author Justin Walsh
 */
public class RequestSearchForm extends ValidatorForm {

    private static final long serialVersionUID = 1l;

    @RedirectParameter
    private Long requestId;
    @RedirectParameter
    private String agreementExternalRef;
    @RedirectParameter
    private Long agreementId;
    @RedirectParameter
    private Long agreementKind;
    @RedirectParameter
    private String targetOrResultActualOption;
    @RedirectParameter
    private Long requestKind;
    @RedirectParameter
    private String requestStatus;
    @RedirectParameter
    private String requestStartDate;
    @RedirectParameter
    private String requestEndDate;
    @RedirectParameter
    private String requestedStartDate;
    @RedirectParameter
    private String requestedEndDate;
    @RedirectParameter
    private Integer maxResults;

    private ObjectReference selectedRequestObjectReference;

    private RequestSearchList results;

    // lookup data
    private List<LabelValueBean> requestOptions = new ArrayList<>();
    private List<LabelValueBean> maxResultsOptions = new ArrayList<>();
    private List<LabelValueBean> requestStatuses = new ArrayList<>();

    // information about the table being displayed
    private String tableId;
    private int rowsPerPage;
    private int startRecord;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.agreementExternalRef = null;
        this.agreementKind = null;
        this.requestStatus = null;
        this.requestKind = null;
        this.selectedRequestObjectReference = null;
    }

    /**
     * @see com.silvermoongroup.fsa.web.common.form.ValidatorForm#needsValidation(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public boolean needsValidation(ActionMapping mapping, HttpServletRequest request) {

        String name = getActionName(mapping, request);
        return "search".equalsIgnoreCase(name);
    }

    public Set<String> getSelectedLifeCycleStatusses() {

        Set<String> statuses = new HashSet<>();
        String requestLifeCycleState = StringUtils.trimToNull(getRequestStatus());
        if (requestLifeCycleState != null) {
            statuses.add(requestLifeCycleState);
        }

        return statuses;
    }

    public Long getAgreementKind() {
        return agreementKind;
    }

    public void setAgreementKind(Long agreementKind) {
        this.agreementKind = agreementKind;
    }

    public String getTargetOrResultActualOption() {
        return targetOrResultActualOption;
    }

	public void setTargetOrResultActualOption(String targetOrResultActualOption) {
		this.targetOrResultActualOption = targetOrResultActualOption;
	}
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public void setRequestEndDate(String requestEndDate) {
        this.requestEndDate = requestEndDate;
    }

    public Long getRequestKind() {
        return requestKind;
    }

    public void setRequestKind(Long requestKind) {
        this.requestKind = requestKind;
    }

    public String getRequestStartDate() {
        return requestStartDate;
    }

    public void setRequestStartDate(String requestStartDate) {
        this.requestStartDate = requestStartDate;
    }

    public String getRequestedStartDate() {
        return requestedStartDate;
    }

    public void setRequestedStartDate(String requestedStartDate) {
        this.requestedStartDate = requestedStartDate;
    }

    public String getRequestedEndDate() {
        return requestedEndDate;
    }

    public void setRequestedEndDate(String requestedEndDate) {
        this.requestedEndDate = requestedEndDate;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getAgreementExternalRef() {
        return agreementExternalRef;
    }

    public void setAgreementExternalRef(String agreementExternalRef) {
        this.agreementExternalRef = agreementExternalRef;
    }

    public Long getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Long agreementId) {
        this.agreementId = agreementId;
    }

    public String getRequestEndDate() {
        return requestEndDate;
    }

    public RequestSearchList getResults() {
        return results;
    }

    public void setResults(RequestSearchList entries) {
        this.results = entries;
    }

    public ObjectReference getSelectedRequestObjectReference() {
        return selectedRequestObjectReference;
    }

    public void setSelectedRequestObjectReference(ObjectReference selectedRequestObjectReference) {
        this.selectedRequestObjectReference = selectedRequestObjectReference;
    }

    public List<LabelValueBean> getMaxResultsOptions() {
        return maxResultsOptions;
    }

    public void setMaxResultsOptions(List<LabelValueBean> maxResultsOptions) {
        this.maxResultsOptions = maxResultsOptions;
    }

    public List<LabelValueBean> getRequestStatuses() {
        return requestStatuses;
    }

    public void setRequestStatuses(List<LabelValueBean> requestStatuses) {
        this.requestStatuses = requestStatuses;
    }

    public List<LabelValueBean> getRequestOptions() {
        return requestOptions;
    }

    public void setRequestOptions(List<LabelValueBean> requestOptions) {
        this.requestOptions = requestOptions;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public int getStartRecord() {
        return startRecord;
    }

    public void setStartRecord(int startRecord) {
        this.startRecord = startRecord;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }
}
