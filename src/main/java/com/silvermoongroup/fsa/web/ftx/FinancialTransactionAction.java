/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.ftx;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.ftx.domain.intf.IFinancialTransaction;
import com.silvermoongroup.ftx.domain.intf.IPayment;
import com.silvermoongroup.ftx.domain.intf.IPaymentDue;
import org.apache.commons.lang3.Validate;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller class which delegates to the specific action ({@code PaymentDueAction} or {@code PaymentAction}).
 * Useful when you don't know the runtime type of the financial transaction.
 *
 * @author Justin Walsh
 */
public class FinancialTransactionAction extends AbstractLookupDispatchAction {

    /**
     * GET:  Pull out the financial transaction object reference and delegate as required
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        FinancialTransactionForm form = (FinancialTransactionForm)actionForm;
        ObjectReference objectReference = form.getFinancialTransactionObjectReference();
        Assert.notNull(objectReference, "A financial transaction ObjectReference is required");

        IFinancialTransaction ftx =
                getFinancialManagementService().getFinancialTransaction(getApplicationContext(), objectReference);

        ActionRedirect redirect;
        if (ftx instanceof IPayment) {
                                redirect = new ActionRedirect(actionMapping.findForward("payment"));
        }
        else if (ftx instanceof IPaymentDue) {
            redirect = new ActionRedirect(actionMapping.findForward("paymentDue"));
        }
        else {
            throw new IllegalStateException("Unknown type of FinancialTransaction: " + ftx);
        }
        redirect.addParameter("financialTransactionObjectReference", ftx.getObjectReference());
        return redirect;
    }
}
