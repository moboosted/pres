/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.user;

import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Form bean for the preferences page
 * 
 * @author Justin Walsh
 */
public class PreferencesForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    private String locale;
    private String dateFormat;
    private String timeFormat;
    private String numberFormat;
    private String decimalSeparator;
    private String groupingSeparator;

    private List<LabelValueBean> availableLocales = new ArrayList<>();
    private List<LabelValueBean> availableDateFormats = new ArrayList<>();
    private List<LabelValueBean> availableTimeFormats = new ArrayList<>();
    private List<LabelValueBean> availableDecimalSeparators = new ArrayList<>();
    private List<LabelValueBean> availableGroupingSeparators = new ArrayList<>();

    public PreferencesForm() {
    }
    
    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return !"defaultexecute".equals(actionName);
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<LabelValueBean> getAvailableLocales() {
        return availableLocales;
    }

    public void setAvailableLocales(List<LabelValueBean> availableLocales) {
        this.availableLocales = availableLocales;
    }

    @Override
    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }

    public List<LabelValueBean> getAvailableDateFormats() {
        return availableDateFormats;
    }

    public void setAvailableDateFormats(List<LabelValueBean> availableDateFormats) {
        this.availableDateFormats = availableDateFormats;
    }

    public List<LabelValueBean> getAvailableTimeFormats() {
        return availableTimeFormats;
    }

    public void setAvailableTimeFormats(List<LabelValueBean> availableTimeFormats) {
        this.availableTimeFormats = availableTimeFormats;
    }

    public String getGroupingSeparator() {
        return groupingSeparator;
    }

    public void setGroupingSeparator(String groupingSeparator) {
        this.groupingSeparator = groupingSeparator;
    }

    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    public void setDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    public List<LabelValueBean> getAvailableGroupingSeparators() {
        return availableGroupingSeparators;
    }

    public void setAvailableGroupingSeparators(List<LabelValueBean> availableGroupingSeparators) {
        this.availableGroupingSeparators = availableGroupingSeparators;
    }

    public List<LabelValueBean> getAvailableDecimalSeparators() {
        return availableDecimalSeparators;
    }

    public void setAvailableDecimalSeparators(List<LabelValueBean> availableDecimalSeparators) {
        this.availableDecimalSeparators = availableDecimalSeparators;
    }
}
