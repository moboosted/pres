package com.silvermoongroup.config.azuread;

import com.silvermoongroup.fsa.web.security.service.AuthProviderCondition;
import com.silvermoongroup.fsa.web.security.service.AuthProviderEnum;

public class AzureADCondition extends AuthProviderCondition {

    public AzureADCondition() {
        super(AuthProviderEnum.AZUREAD);
    }
}
