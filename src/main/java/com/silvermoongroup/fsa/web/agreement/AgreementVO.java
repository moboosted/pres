/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.agreement;

import com.silvermoongroup.businessservice.policymanagement.dto.AgreementStateDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.KindDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.VersionNumberDTO;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.ValueObject;

import java.util.List;

public class AgreementVO extends ValueObject {

    private static final long serialVersionUID = 1L;

    private Date startDate;
    private Date endDate;
    private Date statusStartDate;
    private Date statusEndDate;
    private List<PreviousAgreementVO> previousAgreementVersionsList;
    private VersionNumberDTO versionNumber;

    private String id;

    private ObjectReference topLevelAgreementObjectReference;

    private ObjectReference versionedAgreementObjectReference;

    private String number;

    private AgreementStateDTO state;

    private ObjectReference objectReference;

    private String primaryParty;

    private String primaryPartyObjectReference;

    private KindDTO kind;

    public VersionNumberDTO getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(VersionNumberDTO versionNumber) {
        this.versionNumber = versionNumber;
    }

    public List<PreviousAgreementVO> getPreviousAgreementVersionsList() {
        return previousAgreementVersionsList;
    }

    public void setPreviousAgreementVersionsList(List<PreviousAgreementVO> previousAgreementVersionsList) {
        this.previousAgreementVersionsList = previousAgreementVersionsList;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStatusEndDate() {
        return statusEndDate;
    }

    public void setStatusEndDate(Date statusEndDate) {
        this.statusEndDate = statusEndDate;
    }

    public Date getStatusStartDate() {
        return statusStartDate;
    }

    public void setStatusStartDate(Date statusStartDate) {
        this.statusStartDate = statusStartDate;
    }

    /**
     * @return The object reference of the versioned agreeement.
     */
    public ObjectReference getVersionedAgreementObjectReference() {
        return versionedAgreementObjectReference;
    }

    public void setVersionedAgreementObjectReference(ObjectReference versionedAgreementObjectReference) {
        this.versionedAgreementObjectReference = versionedAgreementObjectReference;
    }

    public void setTopLevelAgreementObjectReference(ObjectReference topLevelAgreementObjectReference) {
        this.topLevelAgreementObjectReference = topLevelAgreementObjectReference;
    }

    public ObjectReference getTopLevelAgreementObjectReference() {
        return topLevelAgreementObjectReference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ObjectReference getObjectReference() {
        return objectReference;
    }

    public void setObjectReference(ObjectReference objectReference) {
        this.objectReference = objectReference;
    }

    public String getPrimaryParty() {
        return primaryParty;
    }

    public void setPrimaryParty(String primaryParty) {
        this.primaryParty = primaryParty;
    }

    public String getPrimaryPartyObjectReference() {
        return primaryPartyObjectReference;
    }

    public void setPrimaryPartyObjectReference(String primaryPartyObjectReference) {
        this.primaryPartyObjectReference = primaryPartyObjectReference;
    }

    public AgreementStateDTO getState() {
        return state;
    }

    public void setState(AgreementStateDTO state) {
        this.state = state;
    }

    public KindDTO getKind() {
        return kind;
    }

    public void setKind(KindDTO kind) {
        this.kind = kind;
    }

}
