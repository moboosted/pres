/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.agreementandrequest.constants;

public enum InputPropertiesEnum {

    CURRENCY_CODE("ICurrencyCode"), CURRENCY_VALUE("CurrencyValue");

    private String name;

    InputPropertiesEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
