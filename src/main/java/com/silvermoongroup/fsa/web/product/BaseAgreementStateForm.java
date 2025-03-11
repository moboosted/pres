/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.product;

import com.silvermoongroup.fsa.web.common.form.ValidatorForm;

/**
 * @author Justin Walsh
 */
public abstract class BaseAgreementStateForm extends ValidatorForm {

    private Long id;
    private String name;
    private boolean legallyBinding;
    private boolean riskBearing;

    public boolean isLegallyBinding() {
        return legallyBinding;
    }

    public void setLegallyBinding(boolean legallyBinding) {
        this.legallyBinding = legallyBinding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRiskBearing() {
        return riskBearing;
    }

    public void setRiskBearing(boolean riskBearing) {
        this.riskBearing = riskBearing;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
