/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.user.profile;

import java.security.Principal;

/**
 * The profile of a user
 * 
 * @author Justin Walsh
 */
public interface IUserProfile {

    Principal getPrincipal();

    void setPrincipal(Principal principal);

    String getPreference(String key);

    void setPreference(String key, String value);
}
