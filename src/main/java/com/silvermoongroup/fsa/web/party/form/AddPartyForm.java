/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.form;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * The struts form bean backing the PartyAction
 */
public class AddPartyForm extends AbstractPartyForm {

    private static final long serialVersionUID = 1L;

    /**
     * Indicates whether the party that we are liare working with was created during this interaction with the GUI.
     */
    private boolean newParty;

    // duplicate search
    private boolean duplicatesFound = false;
    private boolean overRideDups = false;

    // person simple details
    private String selPartyType = CoreTypeReference.PERSON.getName();
    private String selTitle;
    private String txtFirstName;
    private String selNameType;
    private String txtSurname;
    private String txtMiddleName;
    private String selGender;
    private String txtBirthDate;

    // organisation name
    private String txtOrgFullName;
    private String txtOrgType;

    public void resetPartyInfo() {
        // current party set on form
        setParty(null);
        setPartyObjectRef(null);
        setRoleInContext(null);
    }

    public void resetFields() {

        selTitle = null;
        selNameType = null;
        txtFirstName = null;
        txtMiddleName = null;
        txtSurname = null;
        selGender = null;
        txtBirthDate = null;

        txtOrgFullName = null;

        duplicatesFound = false;
        overRideDups = false;
    }

    /**
     * Reset <em>EVERYTHING</em>. Typically called when exiting the GUI
     */
    public void resetAll() {

        resetFields();
        resetPartyInfo();

        setNewParty(false);

        setDuplicatesFound(false);
        setOverRideDups(false);
    }

    // getters and setters start

    public boolean isDuplicatesFound() {
        return duplicatesFound;
    }

    public void setDuplicatesFound(boolean duplicatesFound) {
        this.duplicatesFound = duplicatesFound;
    }

    public boolean isOverRideDups() {
        return overRideDups;
    }

    public void setOverRideDups(boolean overRideDups) {
        this.overRideDups = overRideDups;
    }

    public String getSelPartyType() {
        return selPartyType;
    }

    public void setSelPartyType(String selPartyType) {
        this.selPartyType = selPartyType;
    }

    public String getSelNameType() {
        return selNameType;
    }

    public void setSelNameType(String selNameType) {
        this.selNameType = selNameType;
    }

    public String getSelTitle() {
        return selTitle;
    }

    public void setSelTitle(String selTitle) {
        this.selTitle = selTitle;
    }

    public String getTxtFirstName() {
        return txtFirstName;
    }

    public void setTxtFirstName(String txtFirstName) {
        this.txtFirstName = txtFirstName;
    }

    public String getTxtOrgType() {
        return txtOrgType;
    }

    public void setTxtOrgType(String txtOrgType) {
        this.txtOrgType = txtOrgType;
    }

    public String getTxtMiddleName() {
        return txtMiddleName;
    }

    public void setTxtMiddleName(String txtMiddleName) {
        this.txtMiddleName = txtMiddleName;
    }

    public String getTxtSurname() {
        return txtSurname;
    }

    public void setTxtSurname(String txtSurname) {
        this.txtSurname = txtSurname;
    }


    public String getTxtOrgFullName() {
        return txtOrgFullName;
    }

    public void setTxtOrgFullName(String txtOrgFullName) {
        this.txtOrgFullName = txtOrgFullName;
    }

    public String getSelGender() {
        return selGender;
    }

    public void setSelGender(String selGender) {
        this.selGender = selGender;
    }

    public String getTxtBirthDate() {
        return txtBirthDate;
    }

    public void setTxtBirthDate(String txtBirthDate) {
        this.txtBirthDate = txtBirthDate;
    }


    @Override
    public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        if (needsValidation(actionMapping, httpServletRequest)) {

            ActionErrors actionMessages = super.validate(actionMapping, httpServletRequest);

            String validateType = StringUtils.trimToNull(httpServletRequest.getParameter("validate"));

            if ("validatePerson".equals(validateType)) {
                if (this.selPartyType.equalsIgnoreCase(CoreTypeReference.PERSON.getName())) {
                    if (GenericValidator.isBlankOrNull(selTitle)) {
                        actionMessages.add("selTitle", new ActionMessage("errors.required",
                                getFieldName("page.party.label.title")));
                    }
                    if (GenericValidator.isBlankOrNull(txtSurname)) {
                        actionMessages.add("txtSurname", new ActionMessage("errors.required",
                                getFieldName("page.party.label.surname")));
                    }
                    if (GenericValidator.isBlankOrNull(txtFirstName)) {
                        actionMessages.add("txtFirstName", new ActionMessage("errors.required",
                                getFieldName("page.party.label.firstname")));
                    }
                    if (!GenericValidator.isBlankOrNull(txtBirthDate)) {
                        Date tmpStartDate = new Date(txtBirthDate);
                        if (tmpStartDate.isAfter(Date.today())) {
                            actionMessages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
                                    "message.party.nameStartdateAfterCurrentDate"));
                        }
                    }
                    if (GenericValidator.isBlankOrNull(txtBirthDate)) {
                        actionMessages.add("txtBirthDate", new ActionMessage("errors.required",
                                getFieldName("page.party.label.birthdate")));
                    }
                    if (GenericValidator.isBlankOrNull(selGender)) {
                        actionMessages.add("selGender", new ActionMessage("errors.required",
                                getFieldName("page.party.generalparty.label.gender")));
                    }
                    return actionMessages;
                } 
            } else if (validateType.equals("validateOrganisation")) {
                return validateUpdateOrganisation();
            }
        }
        return super.validate(actionMapping, httpServletRequest);
    }

    /**
     * Validate the update of an organisation
     */
    private ActionErrors validateUpdateOrganisation() {
        if (GenericValidator.isBlankOrNull(txtOrgFullName)) {
            ActionErrors actionErrors = new ActionErrors();
            // actionMessages.add("txtFirstName", new ActionMessage("errors.required",
            // getFieldName("page.party.label.firstname")));
            actionErrors.add("txtOrgFullName", new ActionMessage("errors.required",
                    getFieldName("page.party.label.organisationname")));
            return actionErrors;
        }
        return null;
    }
    

    public boolean isNewParty() {
        return newParty;
    }

    public void setNewParty(boolean newParty) {
        this.newParty = newParty;
    }

    private String getFieldName(String key) {
        return getTypeFormatter().formatMessage(key);
    }

}