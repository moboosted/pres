package com.silvermoongroup.fsa.web.party.vo;

import com.silvermoongroup.common.domain.ValueObject;

public class PartyRolePartyDetailVO extends ValueObject implements Comparable<Object> {

    private static final long serialVersionUID = 1l;

    private String contextPartyName;

    private String roleType;

    private String contextTypeName;

    private String contextObjectReference;

    private String partyRole;

    private String partyRoleContext;

    public String getContextPartyName() {
        return contextPartyName;
    }

    public void setContextPartyName(String contextPartyName) {
        this.contextPartyName = contextPartyName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getContextTypeName() {
        return contextTypeName;
    }

    public void setContextTypeName(String contextTypeName) {
        this.contextTypeName = contextTypeName;
    }

    public String getContextObjectReference() { return contextObjectReference; }

    public void setContextObjectReference(String contextObjectReference) { this.contextObjectReference = contextObjectReference; }

    public String getPartyRole() { return partyRole; }

    public void setPartyRole(String partyRole) { this.partyRole = partyRole; }

    public String getPartyRoleContext() { return partyRoleContext; }

    public void setPartyRoleContext(String partyRoleContext) { this.partyRoleContext = partyRoleContext; }

    public int compareTo(PartyRolePartyDetailVO partyRolePartyDetailVO) {

        // compare partyRole name
        int result = partyRole.compareTo(partyRolePartyDetailVO.getPartyRole());
        if (result == 0) {
            // if equal, compare partyRole context.
            result = partyRoleContext.compareTo(partyRolePartyDetailVO.getPartyRoleContext());
        }
        return result;
    }

    @Override
    public int compareTo(Object object) {
        PartyRolePartyDetailVO partyRolePartyDetailVO = (PartyRolePartyDetailVO) object;
        return compareTo(partyRolePartyDetailVO);
    }
}
