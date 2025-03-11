/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.vo;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.enumeration.intf.ITypeReference;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class PartySearchResultVO  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Deprecated
    private String partyId;
    private String objectReference;

    private String fullName;
    private String tradingName;
    private String surname;
    private String firstname;
    private String middlename;
    private String initials;
    private String title;
    private String id;
    private String birthDate;
    private String partyType;

    // contacts
    private String contactId;
    private ObjectReference contactObjRef;
    private String externalReference;
    private String contactTypeName;
    private ITypeReference contactType;
    // email
    private String email;
    // telephone
    private String number;
    private String extension;
    // finicial
    private String accountNumber;
    private String accountHolderName;
    private String branchCode;
    private String branchName;

    // physical
    private String buildingName;
    private String streetNumber;
    private String street;
    private String physicalAddressPostalCode;
    private String physicalAddressSubRegion;
    private String physicalAddressRegion;
    private String physicalAddressCity;
    private String physicalAddressCountryName;

    // postal
    private String boxNumber;
    private String postnetSuite;
    private String postalAddressPostalCode;
    private String postalAddressSubRegion;
    private String postalAddressRegion;
    private String postalAddressCity;
    private String postalAddressCountryName;

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Deprecated
    public String getPartyId() {
        return partyId;
    }

    @Deprecated
    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhysicalAddressPostalCode() {
        return physicalAddressPostalCode;
    }

    public void setPhysicalAddressPostalCode(String physicalAddressPostalCode) {
        this.physicalAddressPostalCode = physicalAddressPostalCode;
    }

    public String getPhysicalAddressSubRegion() {
        return physicalAddressSubRegion;
    }

    public void setPhysicalAddressSubRegion(String physicalAddressSubRegion) {
        this.physicalAddressSubRegion = physicalAddressSubRegion;
    }

    public String getPhysicalAddressRegion() {
        return physicalAddressRegion;
    }

    public void setPhysicalAddressRegion(String physicalAddressRegion) {
        this.physicalAddressRegion = physicalAddressRegion;
    }

    public String getPhysicalAddressCity() {
        return physicalAddressCity;
    }

    public void setPhysicalAddressCity(String physicalAddressCity) {
        this.physicalAddressCity = physicalAddressCity;
    }

    public String getPhysicalAddressCountryName() {
        return physicalAddressCountryName;
    }

    public void setPhysicalAddressCountryName(String physicalAddressCountryName) {
        this.physicalAddressCountryName = physicalAddressCountryName;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getPostnetSuite() {
        return postnetSuite;
    }

    public void setPostnetSuite(String postnetSuite) {
        this.postnetSuite = postnetSuite;
    }

    public String getPostalAddressPostalCode() {
        return postalAddressPostalCode;
    }

    public void setPostalAddressPostalCode(String postalAddressPostalCode) {
        this.postalAddressPostalCode = postalAddressPostalCode;
    }

    public String getPostalAddressSubRegion() {
        return postalAddressSubRegion;
    }

    public void setPostalAddressSubRegion(String postalAddressSubRegion) {
        this.postalAddressSubRegion = postalAddressSubRegion;
    }

    public String getPostalAddressRegion() {
        return postalAddressRegion;
    }

    public void setPostalAddressRegion(String postalAddressRegion) {
        this.postalAddressRegion = postalAddressRegion;
    }

    public String getPostalAddressCity() {
        return postalAddressCity;
    }

    public void setPostalAddressCity(String postalAddressCity) {
        this.postalAddressCity = postalAddressCity;
    }

    public String getPostalAddressCountryName() {
        return postalAddressCountryName;
    }

    public void setPostalAddressCountryName(String postalAddressCountryName) {
        this.postalAddressCountryName = postalAddressCountryName;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public ObjectReference getContactObjRef() {
        return contactObjRef;
    }

    public void setContactObjRef(ObjectReference contactObjRef) {
        this.contactObjRef = contactObjRef;
    }

    public ITypeReference getContactType() {
        return contactType;
    }

    public void setContactType(ITypeReference contactType) {
        this.contactType = contactType;
    }

    public String getContactTypeName() {
        return contactTypeName;
    }

    public void setContactTypeName(String contactTypeName) {
        this.contactTypeName = contactTypeName;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getObjectReference() {
        return objectReference;
    }

    public void setObjectReference(String objectReference) {
        this.objectReference = objectReference;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number
     *            the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension
     *            the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return the accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param accountNumber
     *            the accountNumber to set
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return the accountHolderName
     */
    public String getAccountHolderName() {
        return accountHolderName;
    }

    /**
     * @param accountHolderName
     *            the accountHolderName to set
     */
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    /**
     * @return the branchCode
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * @param branchCode
     *            the branchCode to set
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    /**
     * @return the branchName
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * @param branchName
     *            the branchName to set
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }
}