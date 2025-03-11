/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.account.accountmapping;

import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Set;

/**
 * Base class for the add/update account mapping actions.
 *
 * @author Justin Walsh
 */
public abstract class AbstractAccountMappingAction extends AbstractLookupDispatchAction {

    /**
     * The user clicks the back button and wishes to return to the account mappings
     */
    public ActionForward back(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        return new ActionRedirect(actionMapping.findForwardConfig("findAccountMapping"));
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm actionForm, HttpServletRequest request) throws Exception {

        AbstractAccountMappingForm form = (AbstractAccountMappingForm) actionForm;
        populateLookupFormData(form);
    }

    @SuppressWarnings("unchecked")
    protected void populateLookupFormData(AbstractAccountMappingForm form) {

        Set<Type> contextTypes =
                getProductDevelopmentService().getAllSubTypes(getApplicationContext(), CoreTypeReference.TOPLEVELFINANCIALSERVICESAGREEMENT.getValue());
        contextTypes.addAll(getProductDevelopmentService().getAllSubTypes(getApplicationContext(), CoreTypeReference.FINANCIALSERVICESAGREEMENTCOMPONENT.getValue()));
        form.getContextTypeOptions().add(new LabelValueBean("", ""));
        for (Type contextType : contextTypes) {
            form.getContextTypeOptions().add(new LabelValueBean(formatType(contextType), String.valueOf(contextType.getId())));
        }
        Collections.sort(form.getContextTypeOptions(), LabelValueBean.CASE_INSENSITIVE_ORDER);
    }
}
