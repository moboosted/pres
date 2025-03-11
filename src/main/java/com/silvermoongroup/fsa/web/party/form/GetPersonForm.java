/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.form;

import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.party.vo.PartySearchResultVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan Olsen on 2017/04/20.
 */
public class GetPersonForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    private String txtSearchSurname;
    private String txtSearchFirstName;
    private String txtSearchMiddleName;
    private List<PartySearchResultVO> personLists = new ArrayList<>();

    public String getTxtSearchSurname() {
        return txtSearchSurname;
    }

    public void setTxtSearchSurname(String txtSearchSurname) {
        this.txtSearchSurname = txtSearchSurname;
    }

    public String getTxtSearchFirstName() {
        return txtSearchFirstName;
    }

    public void setTxtSearchFirstName(String txtSearchFirstName) {
        this.txtSearchFirstName = txtSearchFirstName;
    }

    public String getTxtSearchMiddleName() {
        return txtSearchMiddleName;
    }

    public void setTxtSearchMiddleName(String txtSearchMiddleName) {
        this.txtSearchMiddleName = txtSearchMiddleName;
    }

    public List<PartySearchResultVO> getPersonLists() {
        return personLists;
    }

    public void setPersonLists(List<PartySearchResultVO> personLists) {
        this.personLists = personLists;
    }
}
