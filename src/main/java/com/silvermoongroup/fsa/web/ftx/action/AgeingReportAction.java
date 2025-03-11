/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.ftx.action;

import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.ftx.form.AgeingReportForm;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import com.silvermoongroup.ftx.criteria.AgeingReportCalculationCriteria;
import com.silvermoongroup.ftx.domain.AgeingReport;
import com.silvermoongroup.ftx.domain.AgeingReportEntry;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ageing report action.
 */
public class AgeingReportAction extends AbstractLookupDispatchAction {

    public AgeingReportAction() {
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.ageingreport.action.calculate", "calculate");
        return map;
    }

    /**
     * GET: Display the form for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
                                        HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        AgeingReportForm form = (AgeingReportForm) actionForm;
        populateStaticPageElements(form, httpRequest);
        return actionMapping.getInputForward();
    }

    /**
     * POST: Redirect to display the results
     */
    public ActionForward calculate(ActionMapping actionMapping, ActionForm actionForm,
                                   HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        return ActionRedirectFactory.createRedirect(actionMapping.findForwardConfig("calculateAndDisplay"), actionForm);
    }

    /**
     * Perform the calculation and display the results
     */
    public ActionForward calculateAndDisplay(ActionMapping actionMapping, ActionForm actionForm,
                                             HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        AgeingReportForm form = (AgeingReportForm) actionForm;

        AgeingReportCalculationCriteria calculationCriteria = new AgeingReportCalculationCriteria();
        calculationCriteria.setCompanyCodeId(form.getCompanyCode());
        calculationCriteria.setRootAccountTypeId(form.getRootAccountTypeId());

        ApplicationContext applicationContext = getApplicationContext();
        AgeingReport result =
                getFinancialManagementService().calculateAgeingReport(applicationContext, calculationCriteria);
        form.setAgeingReport(result);

        List<AgeingReportEntry> entries = result.getEntries();
        List<AgeingReportEntry> filteredList = new ArrayList<>();
        for (AgeingReportEntry entry : entries) {
            filteredList.add(entry);
        }
        result.setEntries(filteredList);

        // generate a file name
        form.setExportFilename("AgeingReportEntry-" + (DateTime.now()).format("yyyyMMdd"));

        return actionMapping.getInputForward();
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm form, HttpServletRequest request) {
        populateStaticPageElements((AgeingReportForm) form, request);
    }

    public void populateStaticPageElements(AgeingReportForm form, HttpServletRequest httpRequest) {
        if ( form.getRootAccountTypeId() == null ) {
            form.setRootAccountTypeId(6001L);
        }
    }
}
