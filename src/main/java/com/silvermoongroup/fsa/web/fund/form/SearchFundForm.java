/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.fund.form;

import com.silvermoongroup.account.domain.intf.IFund;
import com.silvermoongroup.fsa.web.fund.action.SearchFundAction;
import org.apache.struts.action.ActionForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Form bean supporting the {@link SearchFundAction}
 * 
 * @author Justin Walsh
 */
public class SearchFundForm extends ActionForm {
    
    private static final long serialVersionUID = 1L;
    
    private String fundName;
    private List<IFund> searchResults = new ArrayList<>();
    
    public SearchFundForm() {
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }
    
    public List<IFund> getSearchResults() {
        return searchResults;
    }
    
    public void setSearchResults(List<IFund> funds) {
        this.searchResults = funds;
    }
}

