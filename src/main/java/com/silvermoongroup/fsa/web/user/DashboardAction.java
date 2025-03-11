/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.user;

import com.silvermoongroup.businessservice.dashboard.dto.DashboardQueryResult;
import com.silvermoongroup.businessservice.dashboard.service.intf.IDashboardService;
import com.silvermoongroup.businessservice.policymanagement.dto.AgreementRequestSearchResultDTO;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.ftx.domain.MoneySchedulerExecution;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The action backing the dashboard page.
 * 
 * @author Justin Walsh
 */
public class DashboardAction extends AbstractLookupDispatchAction {

    private IDashboardService dashboardService;

    public DashboardAction() {
    }

    @Override
    protected void onInit() {
        super.onInit();
        dashboardService = getBean(IDashboardService.class);
    }

    public ActionForward defaultExecute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        DashboardForm form = (DashboardForm) actionForm;

        DashboardQueryResult<MoneySchedulerExecution> recentlyExecutedMoneySchedulers =
                dashboardService.findRecentlyExecutedMoneySchedulers(getApplicationContext());
        form.setRecentlyExecutedSchedulers(recentlyExecutedMoneySchedulers);

        DashboardQueryResult<AgreementRequestSearchResultDTO> recentlyExecutedRequests =
                dashboardService.findRecentlyExecutedRequests(getApplicationContext());
        form.setRecentlyExecutedRequests(recentlyExecutedRequests);

        return mapping.getInputForward();
    }
}
