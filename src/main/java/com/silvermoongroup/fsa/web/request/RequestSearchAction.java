/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.request;

import com.silvermoongroup.businessservice.policymanagement.criteria.AgreementRequestSearchCriteria;
import com.silvermoongroup.businessservice.policymanagement.criteria.TargetOrResultActualOption;
import com.silvermoongroup.businessservice.policymanagement.dto.KindDTO;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.datatype.DateTimePeriod;
import com.silvermoongroup.common.datatype.Period;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.common.util.SessionUtil;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import com.silvermoongroup.spflite.enumeration.RequestStateNames;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Action class for the request search action.
 *
 * @author Naeem Ally
 * @author Justin Walsh
 */
public class RequestSearchAction extends AbstractLookupDispatchAction {

    public RequestSearchAction() {
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.requestsearch.action.search", "search");
        return map;
    }

    /**
     * The default execute action - simply display the page
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        // if we have come from the left menu, we need to ensure that all callbacks are cleared so that we don't
        // end up with lingering conversation state.
        if (httpServletRequest.getParameter("ccb") != null) {
            SessionUtil.clearConversationalState(httpServletRequest);
        }

        RequestSearchForm form = (RequestSearchForm) actionForm;
        populateFormElements(httpServletRequest, form);

        return actionMapping.getInputForward();
    }

    /**
     * POST: Invoked when the user click the search button
     */
    public ActionForward search(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        return ActionRedirectFactory.createRedirect(actionMapping.findForward("findAndDisplay"), actionForm);
    }

    /**
     * GET: Redirect after post
     */
    public ActionForward findAndDisplay(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        RequestSearchForm searchForm = (RequestSearchForm) actionForm;

        populateFormElements(httpServletRequest, searchForm);

        AgreementRequestSearchCriteria criteria = buildCriteria(httpServletRequest, searchForm);
        String sortCriterion = httpServletRequest.getParameter("sort");
        RequestSearchList requestSearchList = buildRequestSearchList(criteria, sortCriterion);

        requestSearchList.setObjectsPerPage(searchForm.getRowsPerPage());
        requestSearchList.setPageNumber(getPage((httpServletRequest)));

        searchForm.setResults(requestSearchList);

        return actionMapping.getInputForward();
    }

    private RequestSearchList buildRequestSearchList(AgreementRequestSearchCriteria criteria,
            String sortCriterion) {
        RequestSearchList requestSearchList = new RequestSearchList();

        Long numberOfRequests = getPolicyAdminService().getNumberOfRequests(getApplicationContext(), criteria);
        requestSearchList.setFullListSize(numberOfRequests.intValue());
        requestSearchList.setList(getPolicyAdminService().findRequests(getApplicationContext(), criteria, sortCriterion));
        return requestSearchList;
    }

    /**
     * Select a request
     */
    public ActionForward select(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        RequestSearchForm searchForm = (RequestSearchForm) actionForm;
        ObjectReference requestObjectReference = searchForm.getSelectedRequestObjectReference();

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForward("request"));
        redirect.addParameter("contextObjectReference", requestObjectReference);
        return redirect;
    }

    private AgreementRequestSearchCriteria buildCriteria(HttpServletRequest httpServletRequest, RequestSearchForm form) {

        AgreementRequestSearchCriteria criteria = new AgreementRequestSearchCriteria();
        criteria.setAgreementExternalReference(StringUtils.trimToNull(form.getAgreementExternalRef()));
        criteria.setAgreementId(form.getAgreementId());

        if (form.getTargetOrResultActualOption() != null) {
            criteria.setTargetOrResultActual(TargetOrResultActualOption.valueOf(form.getTargetOrResultActualOption()));
        }

        Date requestStartDate = Date.PAST;
        form.setRequestStartDate(StringUtils.trimToNull(form.getRequestStartDate()));
        if (form.getRequestStartDate() != null) {
            requestStartDate = parseDate(form.getRequestStartDate());
        }
        Date requestEndDate = Date.FUTURE;
        form.setRequestEndDate(StringUtils.trimToNull(form.getRequestEndDate()));
        if (form.getRequestEndDate() != null) {
            requestEndDate = parseDate(form.getRequestEndDate());
        }
        criteria.setRequestPeriod(new DateTimePeriod(new DateTime(requestStartDate), new DateTime(requestEndDate)));

        Date requestedStartDate = Date.PAST;
        form.setRequestedStartDate(StringUtils.trimToNull(form.getRequestedStartDate()));
        if (form.getRequestedStartDate() != null) {
            requestedStartDate = parseDate(form.getRequestedStartDate());
        }
        Date requestedEndDate = Date.FUTURE;
        form.setRequestedEndDate(StringUtils.trimToNull(form.getRequestedEndDate()));
        if (form.getRequestedEndDate() != null) {
            requestedEndDate = parseDate(form.getRequestedEndDate());
        }
        criteria.setRequestedPeriod(new DateTimePeriod(new DateTime(requestedStartDate), new DateTime(requestedEndDate)));

        criteria.setRequestId(form.getRequestId());
        criteria.setAgreementKinds(getSelectedAgreementKinds(form));
        criteria.setRequestKinds(getSelectedRequestKinds(form));
        criteria.setRequestLifeCycleStatuses(form.getSelectedLifeCycleStatusses());

        criteria.getQueryDetails().setMaximumRecordsRequested(form.getRowsPerPage());
        criteria.getQueryDetails().setStartRecord(form.getStartRecord());

        return criteria;
    }

    private Set<KindDTO> getSelectedRequestKinds(RequestSearchForm form) {
        Long agreementKind = form.getRequestKind();
        return getSelectedKinds(agreementKind);
    }

    private Set<KindDTO> getSelectedAgreementKinds(RequestSearchForm form) {
        Long agreementKind = form.getAgreementKind();
        return getSelectedKinds(agreementKind);
    }

    private Set<KindDTO> getSelectedKinds(Long agreementKind) {
        KindDTO kind;
        Set<KindDTO> kinds = new HashSet<>();

        if (agreementKind != null) {
            kind = getKindById(agreementKind);
            kinds.add(kind);
        }
        return kinds;
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm form, HttpServletRequest request) {
        populateFormElements(request, (RequestSearchForm) form);
    }

    @SuppressWarnings("unchecked")
    private void populateFormElements(HttpServletRequest request, RequestSearchForm form) {

        form.setTableId("requestSearchResults");
        form.setRowsPerPage(25);
        form.setStartRecord(getStartRecordForTable(request, form));

        List<LabelValueBean> requestStatuses = new ArrayList<>();
        requestStatuses.add(new LabelValueBean(formatRequestState(RequestStateNames.CANCELLED.getName()), RequestStateNames.CANCELLED.getName()));
        requestStatuses.add(new LabelValueBean(formatRequestState(RequestStateNames.RAISED.getName()), RequestStateNames.RAISED.getName()));
        requestStatuses.add(new LabelValueBean(formatRequestState(RequestStateNames.EXECUTED.getName()), RequestStateNames.EXECUTED.getName()));
        requestStatuses.add(new LabelValueBean(formatRequestState(RequestStateNames.AUTHORISED.getName()), RequestStateNames.AUTHORISED.getName()));
        requestStatuses.add(new LabelValueBean(formatRequestState(RequestStateNames.DECLINED.getName()), RequestStateNames.DECLINED.getName()));
        requestStatuses.add(new LabelValueBean(formatRequestState(RequestStateNames.REQUIRESAUTHORISATION.getName()), RequestStateNames.REQUIRESAUTHORISATION.getName()));
        requestStatuses.add(new LabelValueBean(formatRequestState(RequestStateNames.OVERRIDABLE_RULE_FAILED.getName()), RequestStateNames.OVERRIDABLE_RULE_FAILED.getName()));
        requestStatuses.add(new LabelValueBean(formatRequestState(RequestStateNames.REQUEST_CANNOT_BE_EXECUTED.getName()), RequestStateNames.REQUEST_CANNOT_BE_EXECUTED.getName()));
        requestStatuses.add(new LabelValueBean(formatRequestState(RequestStateNames.SAVED.getName()), RequestStateNames.SAVED.getName()));

        Collections.sort(requestStatuses, LabelValueBean.CASE_INSENSITIVE_ORDER);
        requestStatuses.add(0, new LabelValueBean(formatMessage("page.requestsearch.label.anystatus"), ""));
        form.setRequestStatuses(requestStatuses);
        
        List<LabelValueBean> requestOptions = new ArrayList<>();
        requestOptions.add(new LabelValueBean(formatMessage("page.requestsearch.label.actedon"), TargetOrResultActualOption.TARGET_ACTUAL.name()));
        requestOptions.add(new LabelValueBean(formatMessage("page.requestsearch.label.created"), TargetOrResultActualOption.RESULT_ACTUAL.name()));
        requestOptions.add(0, new LabelValueBean(formatMessage("page.requestsearch.createdoractedon"), TargetOrResultActualOption.TARGET_OR_RESULT_ACTUAL.name()));
        form.setRequestOptions(requestOptions);
    }


    /**
     * Return the (zero based) index of the first record displayed in the given table.
     */
    private int getStartRecordForTable(HttpServletRequest httpServletRequest, RequestSearchForm form) {
        int page = getPage(httpServletRequest);
        int startRecord = (page - 1) * form.getRowsPerPage();
        return (startRecord < 0) ? 0 : startRecord;
    }

    private int getPage(HttpServletRequest httpServletRequest){
        int pageParameterValue = ( null== httpServletRequest.getParameter("page")) ? 1 : Integer.parseInt(httpServletRequest.getParameter("page"));
        return pageParameterValue;
    }
}
