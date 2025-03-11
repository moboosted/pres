/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.constants;

/**
 * @author mubeen
 */
public enum ViewActionEnum {

    ACTION_HELPER_DTO("actionHelper"),
    TOP_LEVEL_AGREEMENT_CALLBACK_KEY("topLevelAgreementCallBackKey"),
    ROLE_PLAYER_CALLBACK_KEY("rolePlayerCallBackKey");

    private String name;

    private ViewActionEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
