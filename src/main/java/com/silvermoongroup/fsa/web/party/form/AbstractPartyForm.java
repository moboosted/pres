/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.form;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.party.util.PartyGuiHttpConstants;
import com.silvermoongroup.fsa.web.party.vo.PartySearchResultVO;
import com.silvermoongroup.fsa.web.struts.RedirectParameter;
import com.silvermoongroup.party.domain.Party;
import com.silvermoongroup.party.domain.PartyRole;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * The struts form bean backing the PartyAction
 */
public class AbstractPartyForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    /**
     * The object reference of the 'root' party (i.e. person or organisation) that we are working with, which may be
     * derived from the {@link #getRolePlayerObjectRef()} when invoked from another GUI.
     * <p>
     * We store this variable as a string so that struts can auto-populate it (custom type coersion is not supported in
     * struts 1.2).
     */
    private String partyObjectRef;

    /**
     * Ascertains whether fsa web is in delegating mode or not.
     */
    private boolean isDelegating = false;

    /**
     * When entering from another GUI (e.g. the agreement GUI), we may pass through a party or a party role's object
     * reference. For example, when working on the agreement GUI, if we:
     * <ul>
     * <li>Select an existing role player of kind 'Policy Holder' role, we would pass through the object reference of
     * the <code>PartyRole</code> (playing the role of kind 'Policy Holder').</li>
     * <li>Select an existing role player of kind 'Insurer' we will pass through the party's (organisation's) object
     * reference since we don't create a <code>PartyRole</code> for each agreement.</li>
     * </ul>
     * <p>
     * This field therefore contains the object reference of the party role that was <em>originally</em> passed across
     * by the other GUI. It should <b>NEVER</b> be manually modified by custom code since it may be required to compare
     * this object to determine whether the role has been replaced.
     *
     */
    private String rolePlayerObjectRef;

    /**
     * Store the specific {@link com.silvermoongroup.party.domain.PartyRole} we are working in context of.
     * <p>
     * This is the {@link com.silvermoongroup.party.domain.PartyRole} that was either passed in to us or selected. In the context of an IAgreement this
     * {@link com.silvermoongroup.party.domain.PartyRole} will be created if it does not exist or was not passed on the request. Agreements always work
     * within the context of a PartyRole unless otherwise chosen.
     *
     */
    private PartyRole roleInContext;

    /**
     * 
     */
    private String tlaContextObjectReference;

    /**
     * 
     */
    private String anchorObjectReference;

    /**
     * When entering from the another GUI, the name of the role type that we ultimately need to link to TODO this is
     * badly named since it is the role type and not the kind.
     */
    @RedirectParameter
    private String roleKindToLink;

    /**
     * Is this party GUI being used in an embedded mode, or has is the GUI being used as a standalone party GUI
     */
    @RedirectParameter
    private boolean embeddedMode;

    // =======================================================================
    // party store values start
    // party
    private Party party;
    
    private int pageNumber;
    private int pageSize = PartyGuiHttpConstants.PERSON_LIST_PAGE_SIZE;
    
    // this is the complete set of people that were returned in the search
    private List<PartySearchResultVO> personList = new ArrayList<>();

    // which are then sorted/paginated into a partial list for display
    private List<PartySearchResultVO> personPartialList = new ArrayList<>();

    private List<PartySearchResultVO> organisationList = new ArrayList<>();

    @Override
    public boolean needsValidation(ActionMapping mapping, HttpServletRequest request) {
        return (StringUtils.trimToNull(request.getParameter("validate")) != null);
    }

    // ======================================================================
    // party store values getters and setters start
    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public String getRoleKindToLink() {
        return roleKindToLink;
    }

    public void setRoleKindToLink(String roleKindToLink) {
        this.roleKindToLink = roleKindToLink;
    }

    public PartyRole getRoleInContext() {
        return roleInContext;
    }

    public void setRoleInContext(PartyRole roleInContext) {
        this.roleInContext = roleInContext;
    }

    public String getTlaContextObjectReference() {
        return tlaContextObjectReference;
    }

    public void setTlaContextObjectReference(String tlaContextObjectReference) {
        this.tlaContextObjectReference = tlaContextObjectReference;
    }

    public String getAnchorObjectReference() {
        return anchorObjectReference;
    }

    public void setAnchorObjectReference(String anchorObjectReference) {
        this.anchorObjectReference = anchorObjectReference;
    }

    public String getPartyObjectRef() {
        return partyObjectRef;
    }

    public ObjectReference getPartyObjectRefAsObjectReference() {
        if (getPartyObjectRef() == null) {
            return null;
        }
        return ObjectReference.convertFromString(getPartyObjectRef());
    }

    // Note that the string is trimmed to null
    public void setPartyObjectRef(String partyObjectRef) {
        this.partyObjectRef = StringUtils.trimToNull(partyObjectRef);
    }

    public String getRolePlayerObjectRef() {
        return rolePlayerObjectRef;
    }

    public ObjectReference getRolePlayerObjectRefAsObjectReference() {
        if (getRolePlayerObjectRef() == null) {
            return null;
        }
        return ObjectReference.convertFromString(getRolePlayerObjectRef());
    }

    // Note that the string is trimmed to null
    public void setRolePlayerObjectRef(String rolePlayerObjectRef) {
        this.rolePlayerObjectRef = StringUtils.trimToNull(rolePlayerObjectRef);
    }

    public boolean isEmbeddedMode() {
        return embeddedMode;
    }

    public void setEmbeddedMode(boolean embeddedMode) {
        this.embeddedMode = embeddedMode;
    }
    
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public List<PartySearchResultVO> getPersonPartialList() {
        return personPartialList;
    }

    public void setPersonPartialList(List<PartySearchResultVO> personPartialList) {
        this.personPartialList = personPartialList;
    }

    public List<PartySearchResultVO> getPersonList() {
        return personList;
    }

    public void setPersonList(List<PartySearchResultVO> personList) {
        this.personList = personList;
    }

    public Integer getPersonSearchResultSize() {
        if (getPersonList() == null) {
            return 0;
        }
        return getPersonList().size();
    }

    public List<PartySearchResultVO> getOrganisationList() {
        return organisationList;
    }

    public void setOrganisationList(List<PartySearchResultVO> organisationList) {
        this.organisationList = organisationList;
    }

    public boolean getIsDelegating() {
        return isDelegating;
    }

    public void setIsDelegating(boolean delegating) {
        isDelegating = delegating;
    }
}