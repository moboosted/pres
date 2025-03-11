/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.form;

import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.party.vo.PartySearchResultVO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jordan Olsen on 2017/04/26.
 */
public class GetOrganisationForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    private String txtSearchOrgFullName;
    private String txtSearchOrgType;
    private Set<Type> organisationTypes = new HashSet<>();
    private List<PartySearchResultVO> organisationLists = new ArrayList<>();

    public String getTxtSearchOrgFullName() {
        return txtSearchOrgFullName;
    }

    public void setTxtSearchOrgFullName(String txtSearchOrgFullName) {
        this.txtSearchOrgFullName = txtSearchOrgFullName;
    }

    public String getTxtSearchOrgType() {
        return txtSearchOrgType;
    }

    public void setTxtSearchOrgType(String txtSearchOrgType) {
        this.txtSearchOrgType = txtSearchOrgType;
    }

    public List<PartySearchResultVO> getOrganisationLists() {
        return organisationLists;
    }

    public void setOrganisationLists(List<PartySearchResultVO> organisationLists) {
        this.organisationLists = organisationLists;
    }

    public Set<Type> getOrganisationTypes() {
        return organisationTypes;
    }

    public void setOrganisationTypes(Set<Type> organisationTypes) {
        this.organisationTypes = organisationTypes;
    }
}
