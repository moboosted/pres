/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.fund.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionRedirect;

import com.silvermoongroup.account.domain.Fund;
import com.silvermoongroup.account.domain.intf.IFund;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import com.silvermoongroup.fsa.web.fund.form.CreateFundForm;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;

/**
 * @author Justin Walsh
 */
public class CreateFundAction extends AbstractLookupDispatchAction {

    public CreateFundAction() {
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> kmm = new HashMap<>();
        kmm.put("button.add", "add");
        return kmm;
    }

    /**
     * The default action - execute the search and display the results
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        populateFormElements(actionForm, request);
        return actionMapping.getInputForward();
    }

    public ActionForward add(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

        CreateFundForm form = (CreateFundForm) actionForm;
        IFund fund = new Fund();
        fund.setName(form.getName());
        fund.setFundCode(form.getFundCode());
        fund.setDescription(form.getDescription());
        fund.setStartDate(parseDate(form.getStartDate()));
        fund.setEndDate(parseDate(form.getEndDate()));


        if (!GenericValidator.isBlankOrNull(form.getCompanyCode())) {
            EnumerationReference internalCoCodeEnumRef = EnumerationReference.convertFromString(form.getCompanyCode());
            fund.setInternalCompanyCode(internalCoCodeEnumRef);
        }

        if (!GenericValidator.isBlankOrNull(form.getCurrencyCode())) {
            EnumerationReference currencyCodeEnumRef = EnumerationReference.convertFromString(form.getCurrencyCode());
            fund.setCurrencyCode(currencyCodeEnumRef);
        }

        getFinancialManagementService().establishFinancialAsset(getApplicationContext(), fund);

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.createfund.message.success", form.getName()));
        saveInformationMessages(request, messages);

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("displayFund"));
        redirect.addParameter("fundName", form.getName());
        return redirect;
    }
    @OnFormValidationFailure
    public void onFormValidationFailure(ActionForm actionForm, HttpServletRequest httpRequest) {
        populateFormElements(actionForm, httpRequest);
    }

    private void populateFormElements(ActionForm actionForm, HttpServletRequest httpRequest) {

        ITypeFormatter typeFormatter = FormatUtil.getTypeFormatter(httpRequest);
        CreateFundForm form = (CreateFundForm)actionForm;

    }
}
