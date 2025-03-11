/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.fund.form;

import com.silvermoongroup.account.domain.intf.IFund;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.fund.action.LinkFundAction;
import org.apache.struts.action.ActionForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Form bean supporting the {@link LinkFundAction}
 * 
 * @author Justin Walsh
 */
public class LinkFundForm extends ActionForm {
    
    private static final long serialVersionUID = 1L;
    
    private String fundName;
    private List<IFund> searchResults = new ArrayList<>();
    
    private ObjectReference selectedFundObjectReference;
    
    public LinkFundForm() {
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
    
    public void setSelectedFundObjectReference(ObjectReference selectedFundId) {
        this.selectedFundObjectReference = selectedFundId;
    }
    
    public ObjectReference getSelectedFundObjectReference() {
        return selectedFundObjectReference;
    }
}

