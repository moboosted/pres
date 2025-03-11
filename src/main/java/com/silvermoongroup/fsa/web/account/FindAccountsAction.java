/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.account;

import com.silvermoongroup.account.criteria.AccountSearchCriteria;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Justin Walsh
 */
public class FindAccountsAction extends AbstractLookupDispatchAction {

    /**
     * GET: Page is rendered for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        FindAccountForm form = (FindAccountForm) actionForm;
        populateStaticPageElements(form, httpRequest);
        return actionMapping.getInputForward();
    }

    /**
     * POST: The user clicks the find button
     */
    public ActionForward find(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
        return ActionRedirectFactory.createRedirect(actionMapping.findForward("findAndDisplay"), actionForm);
    }

    /**
     * GET: Perform the search and render the search results
     */
    public ActionForward findAndDisplay(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        FindAccountForm form = (FindAccountForm) actionForm;

        populateStaticPageElements(form, httpRequest);

        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setAccountId(form.getAccountId());
        criteria.setName(form.getName());
        criteria.setAccountTypeId(form.getAccountTypeId());
        criteria.setRestrictOnAccountType(form.isRestrictAccountType());
        
        String currencyCode = StringUtils.trimToNull(form.getCurrencyCode());
        if (!GenericValidator.isBlankOrNull(form.getCurrencyCode())) {
            EnumerationReference currencyCodeEnumRef = EnumerationReference.convertFromString(form.getCurrencyCode());
            criteria.setCurrencyCode(currencyCodeEnumRef);
        }
        
        String companyCode = StringUtils.trimToNull(form.getCompanyCode());
        if (!GenericValidator.isBlankOrNull(companyCode)) {
            EnumerationReference companyCodeEnumRef = EnumerationReference.convertFromString(companyCode);
            criteria.setInternalCompanyCode(companyCodeEnumRef);
        }

        if ("on".equals(form.getOpenedDateOption())) {
            criteria.setOpeningOn(parseDate(form.getOpenedOnDate()));
        }
        else if ("between".equals(form.getOpenedDateOption())) {
            criteria.setOpeningDatePeriod(new DatePeriod(
                    parseDate(form.getOpenedBetweenDateStart()), parseDate(form.getOpenedBetweenDateEnd())
            ));
        }

        for (Long accountEntryTypeId: form.getAccountEntryTypes()) {
            criteria.addAccountEntryTypeId(accountEntryTypeId);
        }

        form.setResults(getFinancialManagementService().findAccounts(getApplicationContext(), criteria));

        return actionMapping.getInputForward();
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm form, HttpServletRequest request) {
        populateStaticPageElements((FindAccountForm) form, request);
    }

    public void populateStaticPageElements(FindAccountForm form, HttpServletRequest httpRequest) {

        if (form.getOpenedDateOption() == null) {
            form.setOpenedDateOption("any");
        }
    }
}
