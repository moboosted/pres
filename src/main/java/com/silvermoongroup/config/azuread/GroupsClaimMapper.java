package com.silvermoongroup.config.azuread;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.ClaimAccessor;

import java.util.*;

public class GroupsClaimMapper  {
    private final Map<String, String> groupToAuthorities;

    public GroupsClaimMapper(Map<String, String> groupToAuthorities) {
        this.groupToAuthorities = Collections.unmodifiableMap(groupToAuthorities);
    }

    public Collection<SimpleGrantedAuthority> mapAuthorities(ClaimAccessor source) {

        List<String> groups = source.getClaimAsStringList("groups");
        if ( groups == null || groups.isEmpty()) {
            return Collections.emptyList();
        }

        List<SimpleGrantedAuthority> result = new ArrayList<>();
        for( String g : groups) {
            String authName = groupToAuthorities.get(g);
            if ( authName == null ) {
                continue;
            }
            result.add(new SimpleGrantedAuthority(authName));
        }

        return result;
    }

}