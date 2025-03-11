package com.silvermoongroup.config.keycloak;

import com.silvermoongroup.businessservice.SecurityRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled = true)
@Conditional(KeycloakAuthCondition.class)
class SecurityConfigKeycloak {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(ClientRegistration clientRegistration) {
        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    @Bean
    public ClientRegistration createClientRegistration(@Value("${spring.security.oauth2.client.registration.keycloak.client-id}") String clientId,
                                                        @Value("${spring.security.oauth2.client.registration.keycloak.client-name}") String clientName,
                                                        @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}") String clientSecret,
                                                        @Value("${spring.security.oauth2.client.registration.keycloak.authorization-grant-type}") AuthorizationGrantType authorizationGrantType,
                                                        @Value("${spring.security.oauth2.client.registration.keycloak.client-authentication-method}") ClientAuthenticationMethod clientAuthenticationMethod,
                                                        @Value("${spring.security.oauth2.client.registration.keycloak.redirect-uri}") String redirectUri,
                                                        @Value("${spring.security.oauth2.client.registration.keycloak.scope}") String scope,
                                                        @Value("${spring.security.oauth2.client.provider.keycloak.authorization-uri}") String authorisationUri,
                                                        @Value("${spring.security.oauth2.client.provider.keycloak.user-name-attribute}") String userNameAttribute,
                                                        @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}") String tokenUri,
                                                        @Value("${spring.security.oauth2.client.provider.keycloak.user-info-uri}") String userInfoUri,
                                                        @Value("${spring.security.oauth2.client.provider.keycloak.jwk-set-uri}") String jwkSetUri
    ) {
        // split scope by comma and trim each value then add it to a Collection
        var scopes = List.of(scope.split(",")).stream().map(String::trim).collect(Collectors.toSet());
        return ClientRegistration.withRegistrationId("keycloak")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .clientName(clientName)
                .authorizationGrantType(authorizationGrantType)
                .clientAuthenticationMethod(clientAuthenticationMethod)
                .redirectUri(redirectUri)
                .scope(scopes)
                .authorizationUri(authorisationUri)
                .userNameAttributeName(userNameAttribute)
                .tokenUri(tokenUri)
                .userInfoUri(userInfoUri)
                .jwkSetUri(jwkSetUri)
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/secure/product/upload.do").hasRole(SecurityRole.UPLOAD_PRODUCT.name())
                .antMatchers("/secure/**").hasRole(SecurityRole.APPLICATION_USER.name())
                .anyRequest().permitAll();
        http.oauth2Login(o2lc ->
                    o2lc.userInfoEndpoint().userAuthoritiesMapper(userAuthoritiesMapper()).and().defaultSuccessUrl("/index.jsp", true)
                );

        return http.build();
    }

    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return authorities -> {
            var mappedAuthorities = new HashSet<GrantedAuthority>();

            authorities.forEach(authority -> {
                if (authority instanceof OidcUserAuthority oidcUserAuthority) {

                    var userInfo = oidcUserAuthority.getUserInfo();

                    List<SimpleGrantedAuthority> groupAuthorities = Optional.ofNullable(userInfo.getClaimAsStringList("groups"))
                            .map(groups -> groups.stream()
                                    .map(g -> new SimpleGrantedAuthority(g.toUpperCase()))
                                    .toList())
                            .orElse(Collections.emptyList());
                    mappedAuthorities.addAll(groupAuthorities);
                }
            });

            return mappedAuthorities;
        };
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

}