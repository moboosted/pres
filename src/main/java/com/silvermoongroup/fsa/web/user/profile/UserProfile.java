/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.user.profile;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * The profile of a user.
 * 
 * @author Justin Walsh
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class UserProfile implements IUserProfile, Serializable {

    private static final long serialVersionUID = 1L;

    private Principal principal;
    private Map<String, String> preferences = new HashMap<>();

    public UserProfile() {
    }

    @Override
    public String getPreference(String key) {
        return preferences.get(key);
    }

    @Override
    public void setPreference(String key, String value) {
        preferences.put(key, value);
    }

    /**
     * @return The username to display on the GUI.  Null if the user has not been authenticated
     */
    public String getUsername() {
        return (principal == null ? null : principal.getName());
    }

    @Override
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @Override
    public Principal getPrincipal() {
        return principal;
    }
}
