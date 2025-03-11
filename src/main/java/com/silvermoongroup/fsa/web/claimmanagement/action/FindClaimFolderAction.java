/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement.action;

import com.silvermoongroup.claim.criteria.ClaimFolderSearchCriteria;
import com.silvermoongroup.claim.domain.intf.IClaimFolder;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.claimmanagement.form.FindClaimFolderForm;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Justin Walsh
 */
public class FindClaimFolderAction extends AbstractLookupDispatchAction {

    /**
     * GET: The page is rendered for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        FindClaimFolderForm form = (FindClaimFolderForm) actionForm;
        populateStaticPageElements(form, request);
        return actionMapping.getInputForward();
    }

    /**
     * POST: The user searches for claim folder(s)
     */
    public ActionForward find(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return ActionRedirectFactory.createRedirect(actionMapping.findForward("findAndDisplay"), actionForm);
    }

    /**
     * GET: Redirect after post.  Find and display the results
     */
    public ActionForward findAndDisplay(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        FindClaimFolderForm form = (FindClaimFolderForm) actionForm;
        populateStaticPageElements(form, request);

        ClaimFolderSearchCriteria criteria = new ClaimFolderSearchCriteria();

        criteria.setClaimFolderId(form.getClaimFolderId());
        criteria.setExternalReference(form.getExternalReference());
        for (String claimFolderState : form.getClaimFolderStates()) {
            criteria.addClaimFolderState(EnumerationReference.convertFromString(claimFolderState));
        }

        String dateOption = form.getDateOption();
        if (dateOption != null) {
            if (dateOption.equals("on")) {
                criteria.setEffectiveOn(parseDate(form.getEffectiveOn()));
            } else if (dateOption.equals("between")) {
                Date dateFrom = getTypeParser().parseDate(form.getEffectiveFrom());
                Date dateTo = getTypeParser().parseDate(form.getEffectiveTo());
                criteria.setEffectivePeriod(new DatePeriod(dateFrom, dateTo));
            }
        }

        criteria.setTopLevelClaimFoldersOnly(form.isTopLevelClaimFoldersOnly());

        List<IClaimFolder> claimFolders = getClaimManagementService().findClaimFolders(getApplicationContext(), criteria);
        Collections.sort(claimFolders, new Comparator<IClaimFolder>() {
            @Override
            public int compare(IClaimFolder o1, IClaimFolder o2) {
                return o1.getExternalReference().toLowerCase().compareTo(o2.getExternalReference().toLowerCase());
            }
        });
        form.setResults(claimFolders);

        return actionMapping.getInputForward();
    }

    private void populateStaticPageElements(FindClaimFolderForm form, HttpServletRequest request) {
        if (form.getDateOption() == null) {
            form.setDateOption("any");
        }
    }
}
