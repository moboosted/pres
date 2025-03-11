/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.callback;

import java.io.Serializable;

/**
 * 
 * @author greddy
 *
 */
public class CallBack implements Serializable {

    private static final long serialVersionUID = 1L;

    private String page;
    private String key;
    private String scope;
    private Class<?> type;
    private String matchKey;
    private boolean redirect;
    private String intendedMapping;

    /**
     * matchKey is a key that needs to be matched against the key which is returned.
     * Return the matchkey value.
     */
    public String getMatchKey() {
        return matchKey;
    }

    /**
     * Set the matchKey value.
     * @param matchKey
     */
    public void setMatchKey(String matchKey) {
        this.matchKey = matchKey;
    }

    /**
     * Return the key value.
     */
    public String getKey() {
        return key;
    }

    /**
     * Set the key value.
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }
    /**
     * Return the page value.
     */
    public String getPage() {
        return page;
    }
    /**
     * Set the page value.
     * @param page
     */

    public void setPage(String page) {
        this.page = page;
    }
    /**
     * Return the scope value.
     */
    public String getScope() {
        return scope;
    }
    /**
     * Set the scope value.
     * @param scope
     */

    public void setScope(String scope) {
        this.scope = scope;
    }
    /**
     * Return the type value.
     */
    public Class<?> getType() {
        return type;
    }
    /**
     * Set the type value.
     * @param type
     */

    public void setType(Class<?> type) {
        this.type = type;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public String getIntendedMapping() {
        return intendedMapping;
    }

    public void setIntendedMapping(String intendedMapping) {
        this.intendedMapping = intendedMapping;
    }
}
