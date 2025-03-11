/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.fund.action;

import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.fund.form.SearchFundForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Allows the user to search for a fund
 * 
 * @author Justin Walsh
 */
public class SearchFundAction extends AbstractLookupDispatchAction {
    
    public SearchFundAction() {
    }
    
    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> kmm = new HashMap<>();
        kmm.put("button.search", "search");
        return kmm;
    }
    
    /**
     * The default action - display all funds
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        SearchFundForm form = (SearchFundForm)actionForm;
        form.setFundName(""); // to avoid NPE
        return displaySearchResults(actionMapping, actionForm, request, response);
    }
    
    /**
     * Find funds given a name
     */
    public ActionForward search(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        SearchFundForm form = (SearchFundForm)actionForm;
        ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("searchResults"));
        redirect.addParameter("fundName", form.getFundName());
        return redirect;
    }
    
    /**
     * Execute the search and display the results
     */
    public ActionForward displaySearchResults(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        SearchFundForm form = (SearchFundForm)actionForm;
        form.setSearchResults(getFinancialManagementService().findFundsWithNameLike(getApplicationContext(), form.getFundName()));
        return actionMapping.getInputForward();
    }
}
