/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.action;

import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.common.callback.CallBack;
import com.silvermoongroup.fsa.web.common.callback.CallBackUtility;
import com.silvermoongroup.fsa.web.party.form.AbstractPartyForm;
import com.silvermoongroup.fsa.web.party.util.PartyGuiUtility;
import com.silvermoongroup.fsa.web.party.vo.PartySearchResultVO;
import com.silvermoongroup.party.criteria.OrganisationSearchCriteria;
import com.silvermoongroup.party.domain.Organisation;
import org.apache.commons.lang3.Validate;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The PartyAction - essentially the entire party GUI.
 */
public class AbstractPartyAction extends AbstractLookupDispatchAction {

    protected static final String FORWARD_DISPLAY_PARTY = "displayParty";
    protected static final String FORWARD_FIND_PARTY = "findParty";
    protected static final String PARAM_PARTY_OBJECT_REF = "partyObjectRef";
    protected static final PartyGuiUtility partyGuiUtility = new PartyGuiUtility();

    private transient final Logger logger = LoggerFactory.getLogger(getClass());

    protected Map<String, String> map = new HashMap<>();
    
    public AbstractPartyAction() {
    }

    public static PartyGuiUtility getPartyGuiUtility() {
        return partyGuiUtility;
    }

    // ------------------------------------ START: Entry points from the GUI ----------------------------------------

    /**
     * Entry Point: An existing party is found, the link button is clicked and control needs to be passed back to the
     * calling GUI
     * <p>
     * Exit Point: Back to the calling GUI
     */
    public ActionForward link(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        AbstractPartyForm partyForm = (AbstractPartyForm) actionForm;
        return handleCallBack(partyForm, httpServletRequest, httpServletResponse);
    }

    protected ActionForward handleCallBack(AbstractPartyForm partyForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        // first make sure that we are in the correct mode
        Validate.isTrue(partyForm.isEmbeddedMode(), "Party GUI needs to have a role kind to link to");

        // we need to either pass back the party role we have created (e.g. the policy holder role), or in the
        // case when a role is not created, pass back the party's object reference

        CallBack callBack = CallBackUtility.getCallBack(httpServletRequest, httpServletResponse);
        if (partyForm.getRoleInContext() != null) {
            Assert.notNull(partyForm.getRoleInContext().getId(), "The party role is context must have been saved");
            CallBackUtility.setAttribute(httpServletRequest, partyForm.getRoleInContext().getObjectReference(),
                    callBack);
        } else if (partyForm.getPartyObjectRef() != null) {
            CallBackUtility.setAttribute(httpServletRequest, partyForm.getPartyObjectRefAsObjectReference(), callBack);
        } else {
            throw new IllegalStateException("Neither a roleInContext or partyObjectRef was found.");
        }

        return CallBackUtility.getForwardAction(callBack);
    }

    /**
     * Entry point: When the 'Cancel' button is clicked on the GUI
     */
    public ActionForward cancel(ActionMapping actionMapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {

        if (!CallBackUtility.isCallBackEmpty(request, response)) {
            CallBack callBack = CallBackUtility.getCallBack(request, response);
            return CallBackUtility.getForwardAction(callBack);
        }

        logger.warn("CallBack is empty.  Returning input forward");
        return new ActionRedirect(actionMapping.findForward("home"));
    }

    // ------------------------------------ END: Entry points from the GUI ----------------------------------------

    @Override
    protected void onInit() {
        super.onInit();
    }

    protected ActionRedirect createRedirect(AbstractPartyForm partyForm, ActionForward forward) {
        return createRedirect(partyForm, forward, null);
    }

    protected ActionRedirect createRedirect(AbstractPartyForm partyForm, ActionForward forward, String parameterName,
            Object parameterValue) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put(parameterName, parameterValue);
        return createRedirect(partyForm, forward, parameterMap);
    }

    /**
     * Create a redirect with the given parameters (optional), storing the messages in the session for later use.
     */
    
    protected ActionRedirect createRedirect(AbstractPartyForm partyForm, ActionForward forward, Map<String, Object> parameters) {
        ActionRedirect redirect = new ActionRedirect(forward);
        if (parameters != null) {
            for (Map.Entry<String, Object> entrySet : parameters.entrySet()) {
                redirect.addParameter(entrySet.getKey(), entrySet.getValue());
            }
        }

        return redirect;
    }

    protected List<PartySearchResultVO> retrieveOrganisations() {
        OrganisationSearchCriteria organisationSearchCriteria = new OrganisationSearchCriteria();
        return buildOrganisationSearchResults(organisationSearchCriteria);
    }

    private List<PartySearchResultVO> buildOrganisationSearchResults(OrganisationSearchCriteria criteria) {
        List<Organisation> searchResults = getCustomerRelationshipService().retrieveParties(getApplicationContext(),
                criteria);

        List<PartySearchResultVO> organisationList = new ArrayList<>();
        for (Organisation org : searchResults) {
            PartySearchResultVO matchSelect = getPartyGuiUtility().createMatchSelectBeanForOrganisation(org);
            organisationList.add(matchSelect);
        }
        return organisationList;
    }

    protected Type getTypeForName(String typeName) {
        Type type = getProductDevelopmentService().getType(getApplicationContext(), typeName);
        if (type == null) {
            throw new IllegalStateException("Unable to find a Type matching the name [" + typeName);
        }
        return type;
    }
}