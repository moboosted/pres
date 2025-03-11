/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.form;

import com.silvermoongroup.businessservice.policymanagement.dto.AgreementDTO;
import com.silvermoongroup.common.domain.ObjectReference;

/**
 * @author mubeen
 */
public class AgreementForm extends AbstractViewForm<AgreementDTO> {

    private static final long serialVersionUID = 1L;

    private Boolean inRoleContext; // This flag is to determine if the TopLevelAgreement being viewed is within a Role
                                   // Context
    private String requestKind;

    private ObjectReference requestObjectReference; // Utilise to Navigate to correct raised, executed Request on Agreement

    /**
     * This is utilised when navigating from a external component (party, phyiscal object) into an agreement part.
     * This could point to the ObjectReference of an Agreement Part or an Agreement Part's anchor
     */
    private ObjectReference relativeAgreementPartObjectReference;

    public Boolean getInRoleContext() {
        return inRoleContext;
    }

    public void setInRoleContext(Boolean inRoleContext) {
        this.inRoleContext = inRoleContext;
    }

    /**
     * @return the requestKind
     */
    public String getRequestKind() {
        return requestKind;
    }

    /**
     * @param requestKind
     *            the requestKind to set
     */
    public void setRequestKind(String requestKind) {
        this.requestKind = requestKind;
    }

    public ObjectReference getRequestObjectReference() {
        return requestObjectReference;
    }

    public void setRequestObjectReference(ObjectReference requestObjectReference) {
        this.requestObjectReference = requestObjectReference;
    }

    /**
     * This returns the ObjectReference of the Anchor to an AgreementPart (Component)
     * 
     * @return The ObjectReference of the Anchor to an AgreementPart (Component)
     */
    public ObjectReference getRelativeAgreementPartObjectReference() {
        return relativeAgreementPartObjectReference;
    }

    /**
     * Set the ObjectReference of the Anchor to an AgreementPart (Component)
     * 
     * @param relativeAgreementPartObjectReference
     *            The Anchor ObjectReference of an AgreementPart (Component)
     */
    public void setRelativeAgreementPartObjectReference(ObjectReference relativeAgreementPartObjectReference) {
        this.relativeAgreementPartObjectReference = relativeAgreementPartObjectReference;
    }

}