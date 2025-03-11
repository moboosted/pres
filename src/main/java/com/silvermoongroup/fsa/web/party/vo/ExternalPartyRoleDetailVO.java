package com.silvermoongroup.fsa.web.party.vo;

import com.silvermoongroup.common.domain.ValueObject;

public class ExternalPartyRoleDetailVO extends ValueObject implements Comparable<Object> {

    private static final long serialVersionUID = 1L;

    private Long partyRoleId;

    private String partyRole;

    private String party;

    private String externalSystemName;

    private String externalContextReference;

    private String externalTypeName;

    private String partyRoleDescription;

    public Long getPartyRoleId() {
        return partyRoleId;
    }

    public void setPartyRoleId(Long partyRoleId) {
        this.partyRoleId = partyRoleId;
    }

    public String getPartyRole() {
        return partyRole;
    }

    public void setPartyRole(String partyRole) {
        this.partyRole = partyRole;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getExternalSystemName() {
        return externalSystemName;
    }

    public void setExternalSystemName(String externalSystemName) {
        this.externalSystemName = externalSystemName;
    }

    public String getExternalContextReference() {
        return externalContextReference;
    }

    public void setExternalContextReference(String externalContextReference) {
        this.externalContextReference = externalContextReference;
    }

    public String getExternalTypeName() { return externalTypeName; }

    public void setExternalTypeName(String externalTypeName) { this.externalTypeName = externalTypeName; }

    public String getPartyRoleDescription() {
        return partyRoleDescription;
    }

    public void setPartyRoleDescription(String partyRoleDescription) {
        this.partyRoleDescription = partyRoleDescription;
    }

    public int compareTo(ExternalPartyRoleDetailVO externalPartyRoleDetailVO) {

        // compare partyRole name
        int result = partyRole.compareTo(externalPartyRoleDetailVO.getPartyRole());
        if (result == 0) {
            // if equal, compare partyRole external context reference.
            result = externalContextReference.compareTo(externalPartyRoleDetailVO.getExternalContextReference());
        }
        return result;
    }

    @Override
    public int compareTo(Object object) {
        ExternalPartyRoleDetailVO externalPartyRoleDetailVO = (ExternalPartyRoleDetailVO) object;
        return compareTo(externalPartyRoleDetailVO);
    }
}
