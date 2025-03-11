package com.silvermoongroup.fsa.web.security.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Backs the login page.
 * 
 * @author Justin Walsh
 * @since 27 May 2008
 */
public class LoginForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private boolean loginFailure;
    private String locale;
    private List<LabelValueBean> availableLocales = new ArrayList<>();

    public LoginForm() {
    }

    public boolean isFailure() {
        return loginFailure;
    }
    public void setFailure(boolean failure) {
        this.loginFailure = failure;
    }

    public List<LabelValueBean> getAvailableLocales() {
        return availableLocales;
    }
    public void setAvailableLocales(List<LabelValueBean> availableLocales) {
        this.availableLocales = availableLocales;
    }

    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }
}
