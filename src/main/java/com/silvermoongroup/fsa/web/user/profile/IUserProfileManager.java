/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.user.profile;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Locale;

/**
 * Manage the profile of a user.
 * 
 * @author Justin Walsh
 */
public interface IUserProfileManager {

    /**
     * @return The principal associated with the profile, or null if one is not bound.
     */
    Principal getPrincipal();

    /**
     * @param principal Set the principal for the session being managed by this manager
     */
    void setPrincipal(Principal principal);

    Locale getLocale();

    String getLanguage();

    /**
     * @return The format that the user expects a date to be represented as.
     */
    String getDateFormat();
    
    String getNumberFormat();
    
    /**
     * @return The the character used for decimal sign.
     */
    char getDecimalSeparator();
    
    /**
     * @param separator the character used for decimal sign.
     */
    void saveDecimalSeparator(HttpServletResponse response, char separator);
    
    /**
     * @return The character used for thousands separator.
     */
    char getGroupingSeparator();
    
    /**
     * @param separator  The character used for thousands separator.
     */
    void saveGroupingSeparator(HttpServletResponse response, char separator);    

    /**
     * @return The format that the user expects a date with a time component to be represented as.
     */
    String getDateTimeFormat();

    void saveLocale(HttpServletResponse response, Locale locale);

    void saveDateFormat(HttpServletResponse response, String dateFormat);
    
    void saveNumberFormat(HttpServletResponse response, String numberFormat);

    /**
     * Load the user preferences for the user
     */
    void loadUserPreferences();
}
