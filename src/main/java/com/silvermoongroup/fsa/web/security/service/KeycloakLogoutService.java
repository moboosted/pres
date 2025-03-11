package com.silvermoongroup.fsa.web.security.service;

import com.silvermoongroup.config.keycloak.KeycloakAuthCondition;
import com.silvermoongroup.fsa.web.security.service.intf.ILogoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Conditional(KeycloakAuthCondition.class)
public class KeycloakLogoutService implements ILogoutService {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakLogoutService.class);
    private final WebClient webClient;

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUri;

    public KeycloakLogoutService() {
        this.webClient = WebClient.create();
    }
    @Override
    public void logout(OAuth2AuthenticationToken authentication) {
        if(authentication.getPrincipal() instanceof OidcUser oidcUser) {
            logoutFromKeycloak(oidcUser);
        } else {
            logger.error("Could not propagate logout to Keycloak %s".formatted(authentication.getPrincipal().toString()));
        }
    }

    private void logoutFromKeycloak(OidcUser user) {
        String endSessionEndpoint = issuerUri + "/protocol/openid-connect/logout";
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(endSessionEndpoint)
                .queryParam("id_token_hint", user.getIdToken().getTokenValue());

        ResponseEntity<String> logoutResponse = webClient.get()
                .uri(builder.toUriString())
                .retrieve()
                .toEntity(String.class)
                .block();

        if (logoutResponse != null && logoutResponse.getStatusCode().is2xxSuccessful()) {
            logger.info("Successfully logged out from Keycloak");
        } else {
            logger.error("Could not propagate logout to Keycloak");
        }
    }

}