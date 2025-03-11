package com.silvermoongroup.fsa.web.security.service.intf;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface ILogoutService {
    void logout(OAuth2AuthenticationToken authentication);
}
