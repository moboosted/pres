/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.account.accountmapping;

import com.silvermoongroup.account.criteria.AccountMappingSearchCriteria;
import com.silvermoongroup.account.domain.intf.IAccountMapping;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.*;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Struts action controller to find accountmappings.
 *
 * @author Justin Walsh
 */
public class FindAccountMappingAction extends AbstractLookupDispatchAction {

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.findaccountmapping.action.applyfilter", "filter");
        map.put("page.findaccountmapping.action.clearfilter", "clearFilter");
        map.put("page.findaccountmapping.action.add", "add");
        return map;
    }

    /**
     * The default action
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {

        FindAccountMappingForm form = (FindAccountMappingForm) actionForm;

        populateFormData(form);

        AccountMappingSearchCriteria criteria = new AccountMappingSearchCriteria();
        if (!GenericValidator.isBlankOrNull(form.getCompanyCode())) {
            EnumerationReference enumerationRef = EnumerationReference.convertFromString(form.getCompanyCode());
            criteria.setInternalCompanyCode(enumerationRef);
        }

        criteria.setTransactionTypeId(form.getFinancialTransactionType());

        List<IAccountMapping> searchResults = getFinancialManagementService().findAccountMappings(getApplicationContext(), criteria);
        form.setResults(searchResults);

        return actionMapping.getInputForward();
    }

    /**
     * A filter is applied to the results
     */
    public ActionForward filter(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        return ActionRedirectFactory.createRedirect(mapping.findForwardConfig("redirect"), actionForm);
    }

    /**
     * A filter is applied to the results
     */
    public ActionForward clearFilter(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        return new ActionRedirect(mapping.findForwardConfig("redirect"));
    }

    /**
     * The user clicks the delete button for an Account Mapping - we handle this directly in this class.
     */
    public ActionForward delete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {

        FindAccountMappingForm form = (FindAccountMappingForm) actionForm;
        getFinancialManagementService().deleteAccountMapping(getApplicationContext(), form.getAccountMappingObjectReference());

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.findaccountmapping.message.mappingdeleted"));
        saveInformationMessages(request, messages);

        return ActionRedirectFactory.createRedirect(mapping.findForwardConfig("redirect"), actionForm);
    }

    /**
     * The user clicks the update button for an Account Mapping - redirect with the relevant information
     */
    public ActionForward update(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {

        FindAccountMappingForm form = (FindAccountMappingForm) actionForm;
        ActionRedirect rd = new ActionRedirect(mapping.findForwardConfig("update"));
        rd.addParameter("accountMappingObjectReference", form.getAccountMappingObjectReference());
        return rd;
    }

    /**
     * The user wishes to add an account mapping
     */
    public ActionForward add(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        return new ActionRedirect(mapping.findForward("add"));
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm actionForm, HttpServletRequest request) throws Exception {

        FindAccountMappingForm form = (FindAccountMappingForm) actionForm;
        populateFormData(form);
    }

    private void populateFormData(FindAccountMappingForm form) {
        
        Set<Type> financialTransactionTypes =
                getProductDevelopmentService().getAllSubTypes(getApplicationContext(), CoreTypeReference.FINANCIALTRANSACTION.getValue());
        form.getFinancialTransactionTypeOptions().add(new LabelValueBean("", ""));
        for (Type ftt : financialTransactionTypes) {
            form.getFinancialTransactionTypeOptions().add(new LabelValueBean(formatType(ftt), String.valueOf(ftt.getId())));
        }

    }
}
