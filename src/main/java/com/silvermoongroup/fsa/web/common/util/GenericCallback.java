/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.util;

import com.silvermoongroup.spflite.specframework.spfliteinterfaces.IOnlyActual;


public class GenericCallback {
    private IOnlyActual onlyActual;
    private String callbackUrl;
    private String kind;

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public IOnlyActual getOnlyActual() {
        return onlyActual;
    }

    public void setOnlyActual(IOnlyActual onlyActual) {
        this.onlyActual = onlyActual;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}