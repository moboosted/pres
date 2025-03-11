/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.product;

import com.silvermoongroup.businessservice.productmanagement.dto.AgreementState;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Justin Walsh
 */
public class FindAgreementStateAction extends AbstractLookupDispatchAction {

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.findagreementstate.action.add", "add");
        return map;
    }

    /**
     * The default action
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        FindAgreementStateForm form = (FindAgreementStateForm) actionForm;
        Collection<AgreementState> agreementStates = getProductDevelopmentService().findAgreementStates(getApplicationContext());

        List<AgreementState> sortedOnName = new ArrayList<>(agreementStates.size());
        sortedOnName.addAll(agreementStates);
        Collections.sort(sortedOnName, new Comparator<AgreementState>() {
            @Override
            public int compare(AgreementState o1, AgreementState o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        form.setAgreementStates(sortedOnName);


        return actionMapping.getInputForward();
    }

    /**
     * POST: Direct to the find page
     */
    public ActionForward add(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return new ActionRedirect(actionMapping.findForward("add"));
    }
}
