/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common;

import antlr.CSharpCodeGenerator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Justin Walsh
 */
@Component
public class WebApplicationProperties {

    private static Logger log = LoggerFactory.getLogger(WebApplicationProperties.class);
    private static final String FALLBACK_NUMBER_FORMAT                    = "#,##0.00##########";


    @Value("${locales:#{T(java.util.Collections).singletonList({T(java.util.Locale).ENGLISH})}}")
    private List<Locale> locales;
    @Value("#{'${dateformats:yyyy MM dd}'}")
    private List<String> supportedDateFormats;
    @Value("#{'${timeformats:HH:mm}'}")
    private List<String> timeformats;
    @Value("#{${display.role.properties.on.callback:null}}")
    private Map<Long,Boolean> displayPropertiesOnCallback;
    @Value("#{${logout.url:'logout'}}")
    private String logoutUrl;
    @Value("${product.upload.server:localhost}")
    private String productUploadServer;
    @Value("${product.upload.port:9020}")
    private String productUploadPort;
    @Value("${search.product.criteria:}")
    private String searchProductCriteria;

    /**
     * Should the role properties of the given kind be displayed on callback?
     * @param kindId The kind id
     * @return true, if the role kind is configured to display on callback, otherwise false (default)
     */
    public boolean displayRolePropertiesOnCallBack(long kindId) {
        if(displayPropertiesOnCallback == null) {
            return false;
        }
        return displayPropertiesOnCallback.get(kindId) != null ? displayPropertiesOnCallback.get(kindId) : false;
    }

    /**
     * @return A list of locales in the order that they are provided.  This ordering is important, as the first locale
     * is the 'fallback' locale.  This method is guaranteed to return a list with <em>at least</em> one {@link Locale}.
     */
    public List<Locale> getSupportedLocales() {
        return Collections.unmodifiableList(locales);
    }

    /**
     * @return The date formats supported by this system.  The ordering is important as the first format is
     * considered the 'default format'.
     */
    public List<String> getSupportedDateFormats() {
        return supportedDateFormats;
    }

    /**
     * @return The default locale to use for the system.  This is the first locale in the list provided.
     */
    public Locale getDefaultLocale() {
        Locale locale = getSupportedLocales().get(0);
        return locale;
    }

    /**
     * @return The default date format to use for the system.  This is the first date format in this list.
     */
    public String getDefaultDateFormat() {
        return getSupportedDateFormats().get(0);
    }

    public String getDefaultNumberFormat() {
        return FALLBACK_NUMBER_FORMAT;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public String getProductUploadServer() {
        return productUploadServer;
    }

    public String getProductUploadPort() {
        return productUploadPort;
    }

    public String getSearchProductCriteria() {
        return searchProductCriteria;
    }
}
