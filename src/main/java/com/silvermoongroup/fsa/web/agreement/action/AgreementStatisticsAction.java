/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreement.action;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.ext.enumeration.InternalCompanyCode;
import com.silvermoongroup.fsa.criteria.AgreementStatisticsSearchCriteria;
import com.silvermoongroup.fsa.statistics.AgreementStatistics;
import com.silvermoongroup.fsa.statistics.AgreementStatisticsEntry;
import com.silvermoongroup.fsa.web.agreement.form.AgreementStatisticsForm;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Agreement Statistics action.
 *
 * @author Justin Walsh
 */
public class AgreementStatisticsAction extends AbstractLookupDispatchAction {

    public AgreementStatisticsAction() {
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.agreementstatistics.action.calculate", "calculate");
        return map;
    }

    /**
     * GET: Display the form for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        AgreementStatisticsForm form = (AgreementStatisticsForm) actionForm;
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

        AgreementStatisticsForm form = (AgreementStatisticsForm) actionForm;

        populateStaticPageElements(form, httpRequest);

        AgreementStatisticsSearchCriteria searchCriteria = new AgreementStatisticsSearchCriteria();
        Date dateFrom = parseDate(form.getDateFrom());
        Date dateTo = parseDate(form.getDateTo()).plus(1);

        if (!GenericValidator.isBlankOrNull(form.getCompanyCode())) {
            searchCriteria.setCompanyCode(EnumerationReference.convertFromString(form.getCompanyCode()));
        }
        searchCriteria.setPeriodOfInterest(new DatePeriod(dateFrom, dateTo));

        ApplicationContext applicationContext = getApplicationContext();

        AgreementStatistics result =
                getPolicyAdminService().calculateAgreementStatistics(applicationContext, searchCriteria);
        form.setAgreementStatistics(result);

        List<AgreementStatisticsEntry> entries = result.getEntries();
        result.setEntries(entries);

        // generate a file name
        form.setExportFilename("AgreementStatisticsReport-" + (DateTime.now()).format("yyyyMMdd"));

        return actionMapping.getInputForward();
    }

    @OnFormValidationFailure
    public void onFormValidationError(ActionForm form, HttpServletRequest request) {
        populateStaticPageElements((AgreementStatisticsForm) form, request);
    }

    public void populateStaticPageElements(AgreementStatisticsForm form, HttpServletRequest httpRequest) {
        if ( form.getCompanyCode() == null ) {
            form.setCompanyCode(InternalCompanyCode.SMG_LUNOS_INSCO.getEnumerationReference().toString());
        }
    }

}
