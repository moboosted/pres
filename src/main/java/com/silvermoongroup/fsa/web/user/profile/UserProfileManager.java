/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.user.profile;

import com.silvermoongroup.fsa.web.common.WebApplicationProperties;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Principal;
import java.text.DateFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

/**
 * Manage the profile of a user.
 *
 * @author Justin Walsh
 */
@Component
public class UserProfileManager implements IUserProfileManager {

    private static Logger log = LoggerFactory.getLogger(WebApplicationProperties.class);

    private static final String LOCALE_KEY = "com.silvermoongroup.fsa.web.locale";
    private static final String DATEFORMAT_KEY = "com.silvermoongroup.fsa.web.dateformat";
    private static final String NUMBERFORMAT_KEY = "com.silvermoongroup.fsa.web.numberformat";
    private static final String DECIMALSEPARATOR_KEY = "com.silvermoongroup.fsa.web.decimalseparator";
    private static final String GROUPINGSEPARATOR_KEY = "com.silvermoongroup.fsa.web.groupingseparator";

    private static final String TIME_FORMAT = "HH:mm"; // 24 hour standard

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private WebApplicationProperties applicationProperties;

    // NB - this is a session scoped bean
    @Autowired
    private IUserProfile userProfile;

    public UserProfileManager() {
    }

    @Override
    public Principal getPrincipal() {
        return getUserProfile().getPrincipal();
    }

    @Override
    public void setPrincipal(Principal principal) {
        getUserProfile().setPrincipal(principal);
    }

    /**
     * Derive the locale of the user from (in order of preference)
     * 1) The user profile
     * 2) Locale settings in the request
     * 3) Default settings
     * <p>
     * For a locale to be returned, it must match one of the supported locales.  In other words, if the request's locale
     * is ru (russian) and we don't support russian, we ignore russian and continue looking.
     *
     * @see com.silvermoongroup.fsa.web.user.profile.IUserProfileManager#getLocale()
     */
    @Override
    public Locale getLocale() {

        Locale locale = getUserPreferredLocale();
        if (locale != null) {
            return locale;
        }

        locale = getLocaleFromRequest();

        if (locale == null) {
            locale = getApplicationProperties().getDefaultLocale();
        }

        return locale;
    }

    private Locale getUserPreferredLocale() {

        Locale locale = getLocaleFromPreference();
        if (locale != null) {
            return locale;
        }

        locale = getLocaleFromCookie();
        if (locale != null) {
            return locale;
        }

        return null;
    }

    /**
     * Derive the date format for the user from (in order of preference)
     * 1) The user profile
     * 2) Locale settings in the request
     * 3) Default settings
     * <p>
     * For a locale to be returned, it must match one of the supported locales.  In other words, if the request's locale
     * is ru (russian) and we don't support russian, we ignore russian and continue looking.
     *
     * @see com.silvermoongroup.fsa.web.user.profile.IUserProfileManager#getLocale()
     */
    @Override
    public String getDateFormat() {

        String dateFormat = getDateFormatFromPreference();
        if (dateFormat != null) {
            return dateFormat;
        }

        dateFormat = getDateFormatFromCookie();

        if (dateFormat == null) {
            dateFormat = getDateFormatFromRequest();
        }

        if (dateFormat == null) {
            dateFormat = getApplicationProperties().getDefaultDateFormat();
        }

        return dateFormat;
    }

    /**
     * Derive the number format for the user from (in order of preference)
     * 1) The user profile
     * 2) Number settings in the request
     * 3) Default settings
     *
     * @see com.silvermoongroup.fsa.web.user.profile.IUserProfileManager#getNumberFormat()
     */
    @Override
    public String getNumberFormat() {

        String numberFormat = getNumberFormatFromPreference();
        if (numberFormat != null) {
            return numberFormat;
        }

        numberFormat = getNumberFormatFromCookie();

        if (numberFormat == null) {
            numberFormat = getApplicationProperties().getDefaultNumberFormat();
        }

        try {
            return URLDecoder.decode(numberFormat, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return numberFormat;
        }

    }

    @Override
    public char getDecimalSeparator() {

        // first choice is the user selected preference
        Character value = getFirstCharacter(getDecimalSeparatorFromPreference());
        if (value != null) {
            return value;
        }

        // which may be stored in the cookie on the machine
        value = getFirstCharacter(getDecimalSeparatorFromCookie());
        if (value != null) {
            return value;
        }

        // next, take into consideration the user preferred locale and use this
        Locale locale = getUserPreferredLocale();
        if (locale != null) {
            return new DecimalFormatSymbols(locale).getDecimalSeparator();
        }

        // next consider the locale which is part of the request to derive the separator
        locale = request.getLocale();
        if (locale != null) {
            DecimalFormatSymbols sym = new DecimalFormatSymbols(locale);
            return sym.getDecimalSeparator();
        }

        // otherwise use the default locale
        locale = getApplicationProperties().getDefaultLocale();
        if (locale != null) {
            DecimalFormatSymbols sym = new DecimalFormatSymbols(locale);
            return sym.getDecimalSeparator();
        }

        // absolute fallback
        return '.';
    }

    @Override
    public char getGroupingSeparator() {

        // first choice is the user selected preference
        Character value = getFirstCharacter(getGroupingSeparatorFromPreference());
        if (value != null) {
            return value;
        }

        // which may be stored in the cookie on the machine
        value = getFirstCharacter(getGroupingSeparatorFromCookie());
        if (value != null) {
            return value;
        }

        // next, take into consideration the user preferred locale and use this
        Locale locale = getUserPreferredLocale();
        if (locale != null) {
            return new DecimalFormatSymbols(locale).getGroupingSeparator();
        }

        // next consider the locale which is part of the request to derive the separator
        locale = request.getLocale();
        if (locale != null) {
            DecimalFormatSymbols sym = new DecimalFormatSymbols(locale);
            return sym.getGroupingSeparator();
        }

        // otherwise use the default locale
        locale = getApplicationProperties().getDefaultLocale();
        if (locale != null) {
            DecimalFormatSymbols sym = new DecimalFormatSymbols(locale);
            return sym.getGroupingSeparator();
        }

        // absolute fallback
        return ' ';
    }

    private Character getFirstCharacter(String decimalSeparator) {
        if (decimalSeparator == null) {
            return null;
        }
        if (decimalSeparator.length() > 0) {
            return decimalSeparator.charAt(0);
        }
        return '\u0000'; // empty character
    }

    /**
     * @see com.silvermoongroup.fsa.web.user.profile.IUserProfileManager#getDateTimeFormat()
     */
    @Override
    public String getDateTimeFormat() {
        return getDateFormat() + " " + TIME_FORMAT;
    }

    /**
     * Return the language code for this context
     *
     * @return The language code
     * @see Locale
     */
    @Override
    public String getLanguage() {
        Locale locale = getLocale();
        if (locale == null) {
            return Locale.ENGLISH.getLanguage();
        }
        return locale.getLanguage();
    }

    /**
     * @see com.silvermoongroup.fsa.web.user.profile.IUserProfileManager#saveLocale(javax.servlet.http.HttpServletResponse, java.util.Locale)
     */
    @Override
    public void saveLocale(HttpServletResponse response, Locale locale) {
        getUserProfile().setPreference(LOCALE_KEY, locale.toString());
        if (response != null) {
            saveLocaleCookie(response, locale);
        }
    }

    @Override
    public void saveDateFormat(HttpServletResponse response, String dateFormat) {
        getUserProfile().setPreference(DATEFORMAT_KEY, dateFormat);
        if (response != null) {
            saveCookie(response, DATEFORMAT_KEY, dateFormat);
        }
    }

    @Override
    public void saveNumberFormat(HttpServletResponse response, String numberFormat) {
        getUserProfile().setPreference(NUMBERFORMAT_KEY, numberFormat);
        if (response != null) {
            saveCookie(response, NUMBERFORMAT_KEY, numberFormat);
        }
    }

    @Override
    public void saveDecimalSeparator(HttpServletResponse response, char separator) {
        getUserProfile().setPreference(DECIMALSEPARATOR_KEY, String.valueOf(separator));
        if (response != null) {
            saveCookie(response, DECIMALSEPARATOR_KEY, String.valueOf(separator));
        }
    }

    @Override
    public void saveGroupingSeparator(HttpServletResponse response, char separator) {
        getUserProfile().setPreference(GROUPINGSEPARATOR_KEY, String.valueOf(separator));
        if (response != null) {
            saveCookie(response, GROUPINGSEPARATOR_KEY, String.valueOf(separator));
        }
    }

    /**
     * @see com.silvermoongroup.fsa.web.user.profile.IUserProfileManager#loadUserPreferences()
     */
    @Override
    public void loadUserPreferences() {
        // nothing for now, but we could load information from the database
    }

    private Locale getLocaleFromPreference() {
        String localeKey = getUserProfile().getPreference(LOCALE_KEY);
        if (localeKey == null) {
            return null;
        }
        return LocaleUtils.toLocale(localeKey);
    }

    private String getDateFormatFromPreference() {
        return getUserProfile().getPreference(DATEFORMAT_KEY);
    }

    private String getNumberFormatFromPreference() {
        return getUserProfile().getPreference(NUMBERFORMAT_KEY);
    }

    private String getDecimalSeparatorFromPreference() {
        return getUserProfile().getPreference(DECIMALSEPARATOR_KEY);
    }

    private String getGroupingSeparatorFromPreference() {
        return getUserProfile().getPreference(GROUPINGSEPARATOR_KEY);
    }

    private Locale getLocaleFromCookie() {
        Cookie cookie = lookupCookieByKey(request, LOCALE_KEY);
        Locale locale = parseLocaleFromCookie(cookie);
        return matchLocale(getApplicationProperties().getSupportedLocales(), locale);
    }

    private String getDateFormatFromCookie() {
        Cookie cookie = lookupCookieByKey(request, DATEFORMAT_KEY);
        if (cookie == null) {
            return null;
        }
        return matchDateFormat(getApplicationProperties().getSupportedDateFormats(), cookie.getValue());
    }

    private String getNumberFormatFromCookie() {
        Cookie cookie = lookupCookieByKey(request, NUMBERFORMAT_KEY);
        if (cookie == null) {
            return null;
        }
        return StringUtils.trimToNull(cookie.getValue());
    }

    private String getDecimalSeparatorFromCookie() {
        Cookie cookie = lookupCookieByKey(request, DECIMALSEPARATOR_KEY);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    private String getGroupingSeparatorFromCookie() {
        Cookie cookie = lookupCookieByKey(request, GROUPINGSEPARATOR_KEY);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    private Locale getLocaleFromRequest() {
        // try to match a local against one of the supported locales
        return matchLocale(new HashSet<>(getApplicationProperties().getSupportedLocales()), request.getLocale());
    }

    private String getDateFormatFromRequest() {

        Locale locale = request.getLocale();
        if (locale == null) {
            return null;
        }

        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        if (df instanceof SimpleDateFormat) {
            String pattern = ((SimpleDateFormat) df).toPattern();
            return matchDateFormat(getApplicationProperties().getSupportedDateFormats(), pattern);
        }

        return null;
    }

    private void saveLocaleCookie(HttpServletResponse response, Locale locale) {
        if (locale == null) {
            return;
        }
        saveCookie(response, LOCALE_KEY, locale.toString());
    }

    private void saveCookie(HttpServletResponse response, String key, String value) {
        if (key == null || value == null) {
            return;
        }
        Cookie cookie;
        try {
            cookie = new Cookie(key, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            cookie = new Cookie(key, value);
        }
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
    }

    private Locale parseLocaleFromCookie(Cookie localeCookie) {

        if (localeCookie == null) {
            return null;
        }
        String[] tokens = localeCookie.getValue().split("_");

        Locale locale = null;
        switch (tokens.length) {
            case 1:
                locale = new Locale(tokens[0].toLowerCase());
                break;
            case 2:
                locale = new Locale(tokens[0].toLowerCase(), tokens[1].toUpperCase());
                break;
            case 3:
                locale = new Locale(tokens[0].toLowerCase(), tokens[1].toUpperCase(), tokens[3]);
                break;
        }
        return locale;
    }

    private Cookie lookupCookieByKey(HttpServletRequest request, String cookieKey) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieKey)) {
                return cookie;
            }
        }
        return null;
    }

    // return the best match for the given locale when provided with a locale
    private Locale matchLocale(Collection<Locale> supportedLocales, Locale locale) {

        if (locale == null) {
            return null;
        }

        // simplist case - we have a
        if (supportedLocales.contains(locale)) {
            return locale;
        }

        return matchLocale(supportedLocales, peelAwayLocale(locale));
    }

    private String matchDateFormat(Collection<String> supportedDateFormats, String dateFormat) {
        if (dateFormat == null) {
            return null;
        }
        if (supportedDateFormats.contains(dateFormat)) {
            return dateFormat;
        }
        return null;
    }

    private Locale peelAwayLocale(Locale locale) {

        String variant = StringUtils.trimToNull(locale.getVariant());
        if (variant != null) {
            return new Locale(locale.getLanguage(), locale.getCountry());
        }
        String country = StringUtils.trimToNull(locale.getCountry());
        if (country != null) {
            return new Locale(locale.getLanguage());
        }
        return null;
    }


    public IUserProfile getUserProfile() {
        return userProfile;
    }

    public WebApplicationProperties getApplicationProperties() {
        return applicationProperties;
    }
}
