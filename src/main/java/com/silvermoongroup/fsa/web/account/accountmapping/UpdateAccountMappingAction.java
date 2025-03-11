/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.account.accountmapping;

import com.silvermoongroup.account.criteria.AccountSearchCriteria;
import com.silvermoongroup.account.domain.intf.IAccount;
import com.silvermoongroup.account.domain.intf.IAccountMapping;
import com.silvermoongroup.account.enumeration.AccountMappingDirection;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.fsa.web.struts.RedirectException;
import org.apache.struts.action.*;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Action for adding an account mapping
 *
 * @author Justin Walsh
 */
public class UpdateAccountMappingAction extends AbstractAccountMappingAction {

    public UpdateAccountMappingAction() {
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.updateaccountmapping.action.update", "update");
        return map;
    }

    /**
     * The default action - display an empty form for the user to complete
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        UpdateAccountMappingForm form = (UpdateAccountMappingForm) actionForm;
        populateFormData(actionMapping, form);
        populateLookupFormData(form);

        return actionMapping.getInputForward();
    }

    public ActionForward update(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        UpdateAccountMappingForm form = (UpdateAccountMappingForm) actionForm;

        if (form.getAccountMappingObjectReference() == null) {
            log.error("An accountMappingObjectReference is required");
            throw new RedirectException(new ActionRedirect(actionMapping.findForwardConfig("findAccountMapping")));
        }
        List<IAccount> accounts;
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setAccountId(form.getAccountId());
        accounts = getFinancialManagementService().findAccounts(getApplicationContext(), criteria);
        if (accounts.isEmpty()) {
            // the account object reference is not valid
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "page.updateaccountmapping.message.invalidaccount", form.getAccountId()));
            saveErrorMessages(request, messages);
            populateLookupFormData(form);
            return actionMapping.getInputForward();
        }
        IAccountMapping accountMapping = getFinancialManagementService().getAccountMapping(getApplicationContext(),
                form.getAccountMappingObjectReference());
        accountMapping.setAccount(accounts.get(0));
        accountMapping.setContextTypeId(form.getContextTypeId());
        accountMapping.setCurrencyCode(parseEnum(form.getCurrencyCode()));
        accountMapping.setTransactionTypeId(form.getFinancialTransactionTypeId());
        accountMapping.setInternalCompanyCode(parseEnum(form.getCompanyCode()));
        accountMapping.setMeansOfPayment(parseEnum(form.getMeansOfPayment()));
        accountMapping.setMappingDirection(AccountMappingDirection.fromCode(form.getMappingDirection()));

        getFinancialManagementService().modifyAccountMapping(getApplicationContext(), accountMapping);

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.updateaccountmapping.message.success"));
        saveInformationMessages(request, messages);

        return new ActionRedirect(actionMapping.findForwardConfig("success"));
    }

    private void populateFormData(ActionMapping actionMapping, UpdateAccountMappingForm form) {

        if (form.getAccountMappingObjectReference() == null) {
            log.error("An accountMappingObjectReference is required");
            throw new RedirectException(new ActionRedirect(actionMapping.findForwardConfig("findAccountMapping")));
        }

        IAccountMapping accountMapping = getFinancialManagementService().getAccountMapping(getApplicationContext(),
                form.getAccountMappingObjectReference());
        populateFormFromAccountMapping(form, accountMapping);
    }

    private void populateFormFromAccountMapping(UpdateAccountMappingForm form, IAccountMapping am) {

        form.setAccountId(am.getAccount().getId());
        form.setCompanyCode(am.getInternalCompanyCode() == null ? null : am.getInternalCompanyCode().toString());
        form.setCurrencyCode(am.getCurrencyCode() == null ? null : am.getCurrencyCode().toString());
        form.setFinancialTransactionTypeId(am.getTransactionTypeId());
        form.setContextTypeId(am.getContextTypeId());
        form.setMappingDirection(am.getMappingDirection() == null ? null : am.getMappingDirection().getCode());
        form.setMeansOfPayment(am.getMeansOfPayment() == null ? null : am.getMeansOfPayment().toString());
    }
    
    protected void populateLookupFormData(UpdateAccountMappingForm form) {
        super.populateLookupFormData(form);
    }

    /**
     * Add an enumeration to a LabelValueBean data list
     * 
     * @param enumerationReference 
     * @param dataList 
     */
    private void addTerminatedEnumeration(EnumerationReference enumerationReference, List<LabelValueBean> dataList) {
        IEnumeration enumeration = getProductDevelopmentService().getEnumeration(getApplicationContext(),
                enumerationReference);
        LabelValueBean labelValueBean = new LabelValueBean(formatEnum(enumeration), String.valueOf(enumeration.getId()));
        if (!dataList.contains(labelValueBean)) {
            dataList.add(labelValueBean);
            Collections.sort(dataList, LabelValueBean.CASE_INSENSITIVE_ORDER);
        }
    }
}
