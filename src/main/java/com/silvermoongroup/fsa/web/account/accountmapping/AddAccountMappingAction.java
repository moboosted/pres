/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.account.accountmapping;

import com.silvermoongroup.account.criteria.AccountSearchCriteria;
import com.silvermoongroup.account.domain.AccountMapping;
import com.silvermoongroup.account.domain.intf.IAccount;
import com.silvermoongroup.account.enumeration.AccountMappingDirection;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Action for adding an account mapping
 *
 * @author Justin Walsh
 */
public class AddAccountMappingAction extends AbstractAccountMappingAction {

    public AddAccountMappingAction() {
    }
    
    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.addaccountmapping.action.add", "add");
        return map;
    }

    /**
     * The default action - display an empty form for the user to complete
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        AbstractAccountMappingForm form = (AbstractAccountMappingForm) actionForm;
        populateLookupFormData(form);

        return actionMapping.getInputForward();
    }

    public ActionForward add(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        AbstractAccountMappingForm form = (AbstractAccountMappingForm) actionForm;
        
        List<IAccount> accounts;
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setAccountId(form.getAccountId());
        accounts = getFinancialManagementService().findAccounts(getApplicationContext(), criteria);
        if (accounts.isEmpty()) {
            // the account object reference is not valid
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE,
                    new ActionMessage("page.updateaccountmapping.message.invalidaccount", form.getAccountId())
            );
            saveErrorMessages(request, messages);
            populateLookupFormData(form);
            return actionMapping.getInputForward();
        }

        AccountMapping am = new AccountMapping();
        am.setAccount(accounts.get(0));
        am.setContextTypeId(form.getContextTypeId());
        am.setCurrencyCode(parseEnum(form.getCurrencyCode()));
        am.setTransactionTypeId(form.getFinancialTransactionTypeId());
        am.setInternalCompanyCode(parseEnum(form.getCompanyCode()));
        am.setMeansOfPayment(parseEnum(form.getMeansOfPayment()));
        am.setMappingDirection(AccountMappingDirection.fromCode(form.getMappingDirection()));
        
        getFinancialManagementService().establishAccountMapping(getApplicationContext(), am);

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.addaccountmapping.message.success"));
        saveInformationMessages(request, messages);

        return new ActionRedirect(actionMapping.findForwardConfig("success"));
    }
}
