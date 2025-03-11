package com.silvermoongroup.fsa.web.security.service;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class AuthProviderCondition implements Condition {

    private final AuthProviderEnum authProvider;

    public AuthProviderCondition(AuthProviderEnum authProvider) {
        this.authProvider = authProvider;
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String provider = context.getEnvironment().getProperty("auth.provider");
        return authProvider.name().equals(provider);
    }
}
