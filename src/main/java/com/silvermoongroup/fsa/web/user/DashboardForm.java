/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.user;

import com.silvermoongroup.businessservice.dashboard.dto.DashboardQueryResult;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;

/**
 * @author Justin Walsh
 */
public class DashboardForm extends ValidatorForm {

    private DashboardQueryResult<?> recentlyExecutedRequests;
    private DashboardQueryResult<?> recentlyExecutedSchedulers;

    public DashboardForm() {
    }

    public DashboardQueryResult<?> getRecentlyExecutedRequests() {
        return recentlyExecutedRequests;
    }

    public void setRecentlyExecutedRequests(DashboardQueryResult<?> recentlyExecutedRequests) {
        this.recentlyExecutedRequests = recentlyExecutedRequests;
    }

    public DashboardQueryResult<?> getRecentlyExecutedSchedulers() {
        return recentlyExecutedSchedulers;
    }

    public void setRecentlyExecutedSchedulers(DashboardQueryResult<?> recentlyExecutedSchedulers) {
        this.recentlyExecutedSchedulers = recentlyExecutedSchedulers;
    }
}
