/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreement.action;

import com.silvermoongroup.businessservice.policymanagement.dto.AgreementSearchResultsDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.AgreementVersionDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.VersionedAgreementDTO;
import com.silvermoongroup.fsa.web.agreement.AgreementVO;
import com.silvermoongroup.fsa.web.agreement.PreviousAgreementVO;
import com.silvermoongroup.fsa.web.agreement.form.FindAgreementForm;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.common.callback.CallBack;
import com.silvermoongroup.fsa.web.common.callback.CallBackUtility;
import com.silvermoongroup.fsa.web.common.util.SessionUtil;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import com.silvermoongroup.spflite.specframework.criteria.VersionedAgreementSearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Action class used to search for and retrieve agreements
 *
 * @author Justin Walsh
 */
public class FindAgreementAction extends AbstractLookupDispatchAction {

    public FindAgreementAction() {
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.findagreement.find", "find");
        map.put("page.findagreement.retrieve", "retrieve");
        map.put("page.findagreement.view", "view");
        return map;
    }

    /**
     * GET: The default execute action - simply display the page
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {

        FindAgreementForm form = (FindAgreementForm) actionForm;

        // this is is often the starting point of a conversation (e.g. from the menu)
        if (request.getParameter("ccb") != null) {
            SessionUtil.clearConversationalState(request);
            form.setLinkingMode(false);
        }

        return actionMapping.getInputForward();
    }

    /**
     * GET: Execute the search and display the results
     */
    public ActionForward findAndDisplay(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        FindAgreementForm form = (FindAgreementForm) actionForm;

        // this is is often the starting point of a conversation when coming straight in (e.g. from the dashboard)
        if (request.getParameter("ccb") != null) {
            SessionUtil.clearConversationalState(request);
            form.setLinkingMode(false);
        }

        VersionedAgreementSearchCriteria criteria = new VersionedAgreementSearchCriteria();
        criteria.setAgreementId(form.getAgreementId());
        criteria.setAgreementExternalReference(StringUtils.trimToNull(form.getExternalReference()));
        criteria.setTopLevelAgreementKindId(form.getTlaKind());
        criteria.setRestrictToTopLevelAgreement(form.isRestrictToTLA());

        AgreementSearchResultsDTO searchResults = getPolicyAdminService().findAgreementsByCriteria(getApplicationContext(), criteria);

        ActionMessages actionMessages = new ActionMessages();
        if (searchResults.getSearchResults().isEmpty()) {
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.findagreement.notfound"));
            saveInformationMessages(request, actionMessages);
            return actionMapping.getInputForward();
        }

        List<AgreementVO> agreementList = buildAgreementVOListFromSearchResults(searchResults, true);

        form.setSearchResults(agreementList);

        if (!CallBackUtility.isCallBackEmpty(request, response)) {
            // check if the callback stack is actually intended for us (we could be reading the callback stack of another operation, e.g. linking a party)
            // If so, we're in linking mode.
            if(CallBackUtility.isCallBackIntendedForMethod(request, response, "findAgreement")) {
                form.setLinkingMode(true);
            }else {
                form.setLinkingMode(false);
                SessionUtil.clearConversationalState(request);
            }
        }

        return actionMapping.getInputForward();
    }

    /**
     * The find button is clicked
     */
    public ActionForward find(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

        return ActionRedirectFactory.createRedirect(actionMapping.findForwardConfig("findAndDisplay"), actionForm);
    }

    /**
     * Retrieve the current version of the agreement
     */
    public ActionForward retrieve(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {

        FindAgreementForm form = (FindAgreementForm) actionForm;

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForward("agreementUI"));
        redirect.addParameter("contextObjectReference", form.getSelectedAgreementObjectReference());
        return redirect;
    }

    /**
     * Link is clicked, link Agreement to a Role
     */
    public ActionForward link(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

        FindAgreementForm form = (FindAgreementForm) actionForm;
        CallBack callBack = CallBackUtility.getCallBack(request, response);
        if(!callBack.getIntendedMapping().equals("findAgreement"))
            throw new IllegalStateException("Callback no longer valid -- cannot link agreement");//safety measure

        CallBackUtility.setAttribute(request, form.getSelectedAgreementObjectReference(), callBack);
        return CallBackUtility.getForwardAction(callBack);
    }

    /**
     * View a previous version of an agreement
     */
    public ActionForward view(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        FindAgreementForm form = (FindAgreementForm) actionForm;

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForward("agreementUI"));
        redirect.addParameter("contextObjectReference", form.getSelectedAgreementObjectReference());
        return redirect;
    }

    /**
     * Build a list of {@link AgreementVO} objects from the agreement search results.
     *
     * @param searchResults           The search results
     * @param includePreviousVersions Should the list {@link PreviousAgreementVO} be built.
     * @return A list of {@link AgreementVO} objects, sorted by start date.
     */
    private List<AgreementVO> buildAgreementVOListFromSearchResults(AgreementSearchResultsDTO searchResults,
                                                                    boolean includePreviousVersions) {
        List<AgreementVO> agreementList = new ArrayList<>();

        for (VersionedAgreementDTO searchResult : searchResults.getSearchResults()) {

            AgreementVO agreementVO = new AgreementVO();

            agreementVO.setVersionedAgreementObjectReference(searchResult.getVersionedAgreementObjectReference());
            agreementVO
                    .setId(String.valueOf(searchResult.getEffectiveTopLevelAgreementObjectReference().getObjectId()));
            agreementVO
                    .setTopLevelAgreementObjectReference(searchResult.getEffectiveTopLevelAgreementObjectReference());
            agreementVO.setNumber(searchResult.getEffectiveAgreementNumber());
            agreementVO.setStartDate(searchResult.getAgreementStartDate());
            agreementVO.setEndDate(searchResult.getAgreementEndDate());
            agreementVO.setVersionNumber(searchResult.getEffectiveVersionNumber());
            agreementVO.setState(searchResult.getState());
            agreementVO.setKind(searchResult.getAgreementKind());

            // build a list of previous versions if requested to do so
            //
            List<PreviousAgreementVO> previousAgreementVOList = new ArrayList<>();
            agreementVO.setPreviousAgreementVersionsList(previousAgreementVOList);

            if (includePreviousVersions) {
                List<AgreementVersionDTO> agreements = searchResult.getAgreements();
                for (AgreementVersionDTO searchDTO : agreements) {
                    PreviousAgreementVO previousAgreement = new PreviousAgreementVO();
                    previousAgreement.setVersionNumber(searchDTO.getVersionNumber());
                    previousAgreement.setId(searchDTO.getAgreementVersionId().toString());
                    previousAgreement.setObjectReference(searchDTO.getTopLevelAgreementObjectReference());
                    previousAgreement.setState(searchDTO.getCurrentState());
                    previousAgreement.setEndDate(searchDTO.getEndDate());
                    previousAgreement.setStartDate(searchDTO.getStartDate());
                    previousAgreement.setCanCreateNextVersion(searchDTO.isCanCreateNextVersion());

                    previousAgreementVOList.add(previousAgreement);
                }

                Collections.sort(previousAgreementVOList);
            }

            agreementList.add(agreementVO);
        }

        // sort the AgreementVOs on start date
        Collections.sort(agreementList, new Comparator<AgreementVO>() {
            @Override
            public int compare(AgreementVO o1, AgreementVO o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });

        return agreementList;
    }

}
