/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.form;

import com.silvermoongroup.businessservice.policymanagement.dto.AgreementDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.RequestDTO;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * This Form is intended to cater for the Request View
 * 
 * @author mubeen
 */
public class RequestViewForm extends AbstractViewForm<RequestDTO> {

    private static final long serialVersionUID = 1L;

    private String effectiveDate;

    /**
     * The TopLevelAgreement that we are currently administering
     */
    private AgreementDTO agreementDTO;

    /**
     * @return the effectiveDate
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @param effectiveDate
     *            the effectiveDate to set
     */
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * @return the resultActual
     */
    public AgreementDTO getAgreementDTO() {
        return agreementDTO;
    }

    /**
     * @param agreementDTO
     *            the resultActual to set
     */
    public void setAgreementDTO(AgreementDTO agreementDTO) {
        this.agreementDTO = agreementDTO;
    }
    
    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        if ("validateandbindproperties".equals(actionName)) {
            return true;
        }
        return false;
    }

}