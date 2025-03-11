/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.agreement;

import com.silvermoongroup.businessservice.policymanagement.dto.AgreementStateDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.VersionNumberDTO;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.ValueObject;

public class PreviousAgreementVO extends ValueObject implements Comparable<PreviousAgreementVO> {

    private static final long serialVersionUID = 1L;

    private String id;
    private VersionNumberDTO versionNumber;
    private String kindName;
    private Date startDate;
    private Date endDate;
    private AgreementStateDTO state;
    private ObjectReference objectReference;
    private boolean canCreateNextVersion;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VersionNumberDTO getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(VersionNumberDTO versionNumber) {
        this.versionNumber = versionNumber;
    }

    public ObjectReference getObjectReference() {
        return objectReference;
    }

    public void setObjectReference(ObjectReference objectReference) {
        this.objectReference = objectReference;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public AgreementStateDTO getState() {
        return state;
    }

    public void setState(AgreementStateDTO state) {
        this.state = state;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public boolean isCanCreateNextVersion() {
        return canCreateNextVersion;
    }

    public void setCanCreateNextVersion(boolean canCreateNextVersion) {
        this.canCreateNextVersion = canCreateNextVersion;
    }

    @Override
    public int compareTo(PreviousAgreementVO o) {
        return getVersionNumber().compareTo(o.getVersionNumber());
    }
}
