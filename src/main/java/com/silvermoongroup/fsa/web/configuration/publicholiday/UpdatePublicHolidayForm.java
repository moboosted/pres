/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.configuration.publicholiday;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Justin Walsh
 */
public class UpdatePublicHolidayForm extends ValidatorForm {

    private ObjectReference publicHolidayObjectReference;

    private String date;
    private String description;

    public ObjectReference getPublicHolidayObjectReference() {
        return publicHolidayObjectReference;
    }

    public void setPublicHolidayObjectReference(ObjectReference publicHolidayObjectReference) {
        this.publicHolidayObjectReference = publicHolidayObjectReference;
    }

    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return "update".equalsIgnoreCase(actionName);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
