package com.silvermoongroup.config.azuread;

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
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled = true)
@Conditional(AzureADCondition.class)
class SecurityConfigAzureAD {

    @Value("${fsa.jwt.authorization.group-to-authorities}")
    private String groupToAuthoritiesList;

    /**
     * This attribute is also available in the ClientRegistration.ProviderDetails but since we create a new DefaultOidcUser with the mapped authorities, we need to know the name of the attribute here.
     * @return
     */
    @Value("${spring.security.oauth2.client.provider.azure.user-name-attribute}")
    private String userNameAttributeName;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(ClientRegistration clientRegistration) {
        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    @Bean
    public ClientRegistration createClientRegistration( @Value("${spring.security.oauth2.client.registration.azure.client-id}") String clientId,
                                                        @Value("${spring.security.oauth2.client.registration.azure.client-secret}") String clientSecret,
                                                        @Value("${spring.security.oauth2.client.registration.azure.scope}") String scope,
                                                        @Value("${spring.security.oauth2.client.provider.azure.issuer-uri}") String issuerUri,
                                                        @Value("${spring.security.oauth2.client.registration.azure.redirect-uri}") String redirectUri,
                                                        @Value("${spring.security.oauth2.client.provider.azure.authorization-uri}") String authorizationUri,
                                                        @Value("${spring.security.oauth2.client.provider.azure.user-name-attribute}") String userNameAttribute,
                                                        @Value("${spring.security.oauth2.client.provider.azure.token-uri}") String tokenUri,
                                                        @Value("${spring.security.oauth2.client.provider.azure.jwk-set-uri}") String jwkSetUri,
                                                        @Value("${spring.security.oauth2.client.registration.azure.authorization-grant-type}") AuthorizationGrantType authorizationGrantType
    ) {
        // split scope by comma and trim each value then add it to a Collection
        var scopes = List.of(scope.split(",")).stream().map(String::trim).collect(Collectors.toSet());
        return ClientRegistration.withRegistrationId("azure")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .authorizationGrantType(authorizationGrantType)
                .issuerUri(issuerUri)
                .jwkSetUri(jwkSetUri)
                .authorizationUri(authorizationUri)
                .tokenUri(tokenUri)
                .redirectUri(redirectUri)
                .userNameAttributeName(userNameAttribute)
                .scope(scopes)
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, Map<String, String> groupToAuthoritiesMap) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/secure/product/upload.do").hasRole(SecurityRole.UPLOAD_PRODUCT.name())
                .antMatchers("/secure/**").hasRole(SecurityRole.APPLICATION_USER.name())
                .anyRequest().permitAll();
        http.oauth2Login(oauth2 ->  oauth2.userInfoEndpoint(ep ->
                            ep.oidcUserService(customOidcUserService(groupToAuthoritiesMap)))
                );

        return http.build();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> customOidcUserService(Map<String, String> groupToAuthoritiesMap) {
        final OidcUserService delegate = new OidcUserService();
        final GroupsClaimMapper mapper = new GroupsClaimMapper(groupToAuthoritiesMap);

        return userRequest -> {
            OidcUser oidcUser = delegate.loadUser(userRequest);
            // Enrich standard authorities with groups
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            mappedAuthorities.addAll(oidcUser.getAuthorities());
            mappedAuthorities.addAll(mapper.mapAuthorities(oidcUser));
            return new DefaultOidcUser(mappedAuthorities, userRequest.getIdToken(), oidcUser.getUserInfo(), userNameAttributeName);
        };
    }

    @Bean(name = "groupToAuthoritiesMap")
    public Map<String, String> groupToAuthoritiesMap() {
        // use Stream to split groupToAuthoritiesList by comma and each entry then by = and use the first part as key and the second part as value so that we can add them all to a map
        return Arrays.stream(groupToAuthoritiesList.split(",")).map(String::trim).map(s -> s.split("=")).collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

}