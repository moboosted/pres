/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.ftx;

import com.silvermoongroup.businessservice.financialmanagement.dto.UnitTransactionDTO;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class UnitTransactionAction extends AbstractLookupDispatchAction {

    /**
     * GET: Display the form for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        UnitTransactionForm form = (UnitTransactionForm) actionForm;

        UnitTransactionDTO unitTransactionDTO = getFinancialManagementService()
                .getUnitTransactionDetail(getApplicationContext(), form.getUnitTransactionObjectReference());
        form.setUnitTransaction(unitTransactionDTO);

        return actionMapping.getInputForward();
    }
}
