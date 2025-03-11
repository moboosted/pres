/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.ftx;

import com.silvermoongroup.businessservice.financialmanagement.dto.PaymentDTO;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Justin Walsh
 */
public class PaymentAction extends AbstractLookupDispatchAction {

    /**
     * GET: Display the form for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        PaymentForm form = (PaymentForm) actionForm;

        PaymentDTO paymentDTO = getFinancialManagementService().getPaymentDetail(getApplicationContext(), form.getFinancialTransactionObjectReference());
        form.setFinancialTransaction(paymentDTO);
        
        return actionMapping.getInputForward();
    }

    public ActionForward reversePayment(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        PaymentForm form = (PaymentForm) actionForm;
        form.getFinancialTransactionObjectReference();

        ObjectReference reversingFinancialTransaction =
                getFinancialManagementService().reverseFinancialTransaction(getApplicationContext(), form.getFinancialTransactionObjectReference());

        ActionMessages actionMessages = getMessages(httpRequest);
        actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.payment.message.reversed"));
        saveInformationMessages(httpRequest, actionMessages);

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForward("financialTransaction"));
        redirect.addParameter("financialTransactionObjectReference", reversingFinancialTransaction);

        return redirect;
    }
}
