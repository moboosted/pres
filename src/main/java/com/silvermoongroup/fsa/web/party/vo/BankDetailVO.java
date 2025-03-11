/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.vo;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.ValueObject;
import com.silvermoongroup.party.domain.RolePlayer;

public class BankDetailVO extends ValueObject {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long typeId;

    private String key;

    private String partyRole;

    private String name;

    private String externalReference;

    private String preference;

    private String accountNumber;

    private String accountHolder;

    private String accountType;

    private String bank;

    private String bankBranchReference;

    private String bankBranchCode;

    private String bankBranchName;

    private Integer bankId;

    private String branch;

    private Integer branchCode;

    private String countryCode;

    private String kind;

    private Long kindId;

    private RolePlayer rolePlayer;

    private Date startDate;

    private Date endDate;

    private String expirtyDate;

    private boolean def;

    private boolean searchedBankAccIsDef;

    private ObjectReference context;

    private String contextReference;

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {

        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartyRole() {
        return partyRole;
    }

    public void setPartyRole(String partyRole) {
        this.partyRole = partyRole;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankBranchReference() {
        return bankBranchReference;
    }

    public void setBankBranchReference(String bankBranchReference) {
        this.bankBranchReference = bankBranchReference;
    }

    public String getBankBranchCode() {
        return bankBranchCode;
    }

    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public RolePlayer getRolePlayer() {
        return rolePlayer;
    }

    public void setRolePlayer(RolePlayer rolePlayer) {
        this.rolePlayer = rolePlayer;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(Integer branchCode) {
        this.branchCode = branchCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Long getKindId() {
        return kindId;
    }

    public void setKindId(Long kindId) {
        this.kindId = kindId;
    }

    public boolean isSearchedBankAccIsDef() {
        return searchedBankAccIsDef;
    }

    public void setSearchedBankAccIsDef(boolean searchedBankAccIsDef) {
        this.searchedBankAccIsDef = searchedBankAccIsDef;
    }

    public String getExpirtyDate() {
        return expirtyDate;
    }

    public void setExpirtyDate(String expirtyDate) {
        this.expirtyDate = expirtyDate;
    }

    /**
     * @return the context
     */
    public ObjectReference getContext() {
        return context;
    }

    /**
     * @param context
     *            the context to set
     */
    public void setContext(ObjectReference context) {
        this.context = context;
    }

    /**
     * @return the contextReference
     */
    public String getContextReference() {
        return contextReference;
    }

    public void setContextReference(String contextObjectReference) {
        this.contextReference = contextObjectReference;
    }

}