/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.product;

import com.silvermoongroup.businessservice.productmanagement.dto.AgreementState;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;

import java.util.Collection;

/**
 * @author Justin Walsh
 */
public class FindAgreementStateForm extends ValidatorForm {

    private Collection<AgreementState> agreementStates;

    public FindAgreementStateForm() {
    }

    public Collection<AgreementState> getAgreementStates() {
        return agreementStates;
    }

    public void setAgreementStates(Collection<AgreementState> agreementStates) {
        this.agreementStates = agreementStates;
    }
}
