/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.constants;

/**
 * @author mubeen
 */
public enum TopLevelAgreementContextEnum {
    REQUEST("Request"), AGREEMENT("Agreement");

    private String name;

    private TopLevelAgreementContextEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
