package com.silvermoongroup.config.keycloak;

import com.silvermoongroup.fsa.web.security.service.AuthProviderCondition;
import com.silvermoongroup.fsa.web.security.service.AuthProviderEnum;

public class KeycloakAuthCondition extends AuthProviderCondition {

    public KeycloakAuthCondition() {
        super(AuthProviderEnum.KEYCLOAK);
    }
}
