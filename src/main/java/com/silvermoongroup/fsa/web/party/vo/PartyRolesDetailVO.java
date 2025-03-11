/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.vo;

import com.silvermoongroup.common.domain.ValueObject;

public class PartyRolesDetailVO extends ValueObject implements Comparable<Object> {

    private static final long serialVersionUID = 1L;

    private String partyRole;

    private String party;

    /*
     * The object reference of the party role
     */
    private String partyRoleObjectReference;

    private long partyRoleId;

    private String agreementRole;

    private String agreementKind;

    private String agreementPart;

    private String agreementHistory;

    private String agreementNumber;

    /*
     * The anchor object reference of the agreement.
     */
    private String partyRoleContext;

    /*
     * The TopLevelAgreement object reference of the agreement.
     */
    private String agreementPartContext;
    
    /*
     * The object reference of the agreement.
     */
    private String agreementPartObjectReference;

    private boolean relationshipRole;

    // relationship roles only
    private String partyRoleEffectiveFrom;

    private String partyRoleEffectiveTo;

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPartyRoleObjectReference() {
        return partyRoleObjectReference;
    }

    public void setPartyRoleObjectReference(String partyRoleObjectReference) {
        this.partyRoleObjectReference = partyRoleObjectReference;
    }

    public String getPartyRole() {
        return partyRole;
    }

    public void setPartyRole(String partyRole) {
        this.partyRole = partyRole;
    }

    public long getPartyRoleId() {
        return partyRoleId;
    }

    public void setPartyRoleId(long partyRoleId) {
        this.partyRoleId = partyRoleId;
    }

    public String getAgreementKind() {
        return agreementKind;
    }

    public void setAgreementKind(String agreementKind) {
        this.agreementKind = agreementKind;
    }

    public String getAgreementHistory() {
        return agreementHistory;
    }

    public void setAgreementHistory(String agreementHistory) {
        this.agreementHistory = agreementHistory;
    }

    public String getAgreementPart() {
        return agreementPart;
    }

    public void setAgreementPart(String agreementPart) {
        this.agreementPart = agreementPart;
    }

    public String getAgreementRole() {
        return agreementRole;
    }

    public void setAgreementRole(String agreementRole) {
        this.agreementRole = agreementRole;
    }

    public int compareTo(PartyRolesDetailVO partyRolesDetailVO) {

        // compare partyRole name
        int result = partyRole.compareTo(partyRolesDetailVO.getPartyRole());
        if (result > 0) {
            return 1;
        }
        if (result < 0) {
            return -1;
        }

        // compare partyRole context
        result = partyRole.compareTo(partyRolesDetailVO.getPartyRoleContext());
        if (result > 0) {
            return 1;
        }
        if (result < 0) {
            return -1;
        }

        // compare agreement fields
        if (this.relationshipRole == false) {
            result = agreementNumber.compareTo(partyRolesDetailVO.agreementNumber);
            if (result > 0) {
                return 1;
            }
            if (result < 0) {
                return -1;
            }
            result = agreementKind.compareTo(partyRolesDetailVO.agreementKind);
            if (result > 0) {
                return 1;
            }
            if (result < 0) {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public int compareTo(Object object) {
        PartyRolesDetailVO partyRolesDetailVO = (PartyRolesDetailVO) object;
        return compareTo(partyRolesDetailVO);
    }

    public String getPartyRoleContext() {
        return partyRoleContext;
    }

    public void setPartyRoleContext(String partyRoleContext) {
        this.partyRoleContext = partyRoleContext;
    }

    public String getAgreementPartContext() {
        return agreementPartContext;
    }

    public void setAgreementPartContext(String agreementPartContext) {
        this.agreementPartContext = agreementPartContext;
    }

    public String getAgreementPartObjectReference() {
        return agreementPartObjectReference;
    }

    public void setAgreementPartObjectReference(String agreementPartObjectReference) {
        this.agreementPartObjectReference = agreementPartObjectReference;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PartyRolesDetailVO)) {
            return false;
        }
        PartyRolesDetailVO pr = (PartyRolesDetailVO) o;
        return (partyRole == null ? pr.getPartyRole() == null : partyRole.equals(pr.getPartyRole()))
                && (party == null ? pr.getParty() == null : party.equals(pr.getParty()))
                && (agreementRole == null ? pr.getAgreementRole() == null : agreementRole.equals(pr.getAgreementRole()))
                && (agreementKind == null ? pr.getAgreementKind() == null : agreementKind.equals(pr.getAgreementKind()))
                && (partyRoleContext == null ? pr.getPartyRoleContext() == null : partyRoleContext.equals(pr
                        .getPartyRoleContext())) && (partyRoleId == pr.getPartyRoleId());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (partyRole == null ? 0 : partyRole.hashCode());
        result = 31 * result + (party == null ? 0 : party.hashCode());
        result = 31 * result + (agreementRole == null ? 0 : agreementRole.hashCode());
        result = 31 * result + (agreementKind == null ? 0 : agreementKind.hashCode());
        result = 31 * result + (partyRoleContext == null ? 0 : partyRoleContext.hashCode());
        result = 31 * result + ((int) (partyRoleId ^ (partyRoleId >>> 32)));
        return result;
    }

    public String getPartyRoleEffectiveFrom() {
        return partyRoleEffectiveFrom;
    }

    public void setPartyRoleEffectiveFrom(String partyRoleEffectiveFrom) {
        this.partyRoleEffectiveFrom = partyRoleEffectiveFrom;
    }

    public String getPartyRoleEffectiveTo() {
        return partyRoleEffectiveTo;
    }

    public void setPartyRoleEffectiveTo(String partyRoleEffectiveTo) {
        this.partyRoleEffectiveTo = partyRoleEffectiveTo;
    }

    public boolean isRelationshipRole() {
        return relationshipRole;
    }

    public void setRelationshipRole(boolean relationshipRole) {
        this.relationshipRole = relationshipRole;
    }

}