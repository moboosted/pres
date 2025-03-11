/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.dto;

import com.silvermoongroup.businessservice.policymanagement.dto.KindDTO;
import com.silvermoongroup.common.domain.ObjectReference;

import java.io.Serializable;

/**
 * This is utilised to store the basic information required to reinstate the Agreement or Request screen when we have
 * been routed to an external view (Party, PhysicalObject etc...)
 * 
 * @author mubeen
 */
public class ActionHelper implements Serializable {
    private static final long serialVersionUID = 1L;
    private ObjectReference contextObjectReference;
    private KindDTO kindDTO;
    private String path;

    /**
     * @return the contextObjectReference
     */
    public ObjectReference getContextObjectReference() {
        return contextObjectReference;
    }

    /**
     * @param contextObjectReference
     *            the contextObjectReference to set
     */
    public void setContextObjectReference(ObjectReference contextObjectReference) {
        this.contextObjectReference = contextObjectReference;
    }

    /**
     * @return the kindDTO
     */
    public KindDTO getKindDTO() {
        return kindDTO;
    }

    /**
     * @param kindDTO
     *            the kindDTO to set
     */
    public void setKindDTO(KindDTO kindDTO) {
        this.kindDTO = kindDTO;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }
}