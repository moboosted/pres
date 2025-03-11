/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.user;

import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.struts.Globals;
import org.apache.struts.action.*;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Struts action class for the preferences action.
 * 
 * @author Justin Walsh
 */
public class PreferencesAction extends AbstractLookupDispatchAction {

    public PreferencesAction() {
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.userpreferences.save", "savePreferences");
        return map;
    }

    /**
     * Entry point: Display the page
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        PreferencesForm form = (PreferencesForm) actionForm;
        form.setLocale(getUserProfileManager().getLocale().toString());
        form.setDateFormat(getUserProfileManager().getDateFormat());
        form.setNumberFormat(getUserProfileManager().getNumberFormat());
        form.setDecimalSeparator(String.valueOf(getUserProfileManager().getDecimalSeparator()));
        form.setGroupingSeparator(String.valueOf(getUserProfileManager().getGroupingSeparator()));

        populateLookupFormData(form);
        
        return actionMapping.getInputForward();
    }

    /**
     * Entry point: Save the preferences for the user
     */
    public ActionForward savePreferences(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        PreferencesForm form = (PreferencesForm) actionForm;
        getUserProfileManager().saveLocale(response, LocaleUtils.toLocale(form.getLocale()));
        getUserProfileManager().saveDateFormat(response, form.getDateFormat());
        getUserProfileManager().saveNumberFormat(response, form.getNumberFormat());
        getUserProfileManager().saveGroupingSeparator(response, form.getGroupingSeparator().charAt(0));
        getUserProfileManager().saveDecimalSeparator(response, form.getDecimalSeparator().charAt(0));

        Locale locale = getUserProfileManager().getLocale();
        request.getSession().setAttribute(Globals.LOCALE_KEY, locale); // struts
        // the following line is the equivalent of
        // Config.set(request.getSession(), Config.FMT_LOCALE, locale);
        // but we don't want to include the library as a compile time library.
        request.getSession().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", locale); // jstl
        
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.userpreferences.message.success"));
        saveInformationMessages(request, messages);

        return actionMapping.findForward("home");
    }
    
    @OnFormValidationFailure
    public void onFormValidationError(ActionForm actionForm, HttpServletRequest request) throws Exception {
        
        PreferencesForm form = (PreferencesForm) actionForm;
        populateLookupFormData(form);
    }

    private void populateLookupFormData(PreferencesForm form) {
        
        List<Locale> supportedLocales = getApplicationProperties().getSupportedLocales();
        List<LabelValueBean> availableLocales = new ArrayList<>();
        for (Locale locale : supportedLocales) {
            availableLocales.add(new LabelValueBean(formatMessage("page.userpreferences.label.locale."
                    + locale.toString()), locale.toString()));
        }

        Collections.sort(availableLocales, LabelValueBean.CASE_INSENSITIVE_ORDER);
        form.setAvailableLocales(availableLocales);

        List<String> supportedDateFormats = getApplicationProperties().getSupportedDateFormats();
        List<LabelValueBean> availableDateFormats = new ArrayList<>();

        for (String dateFormat : supportedDateFormats) {

            FastDateFormat fdf = FastDateFormat.getInstance(dateFormat);
            String formattedDate = fdf.format(new Date());
            String label = formatMessage("page.userpreferences.dateformatexample", dateFormat, formattedDate);

            availableDateFormats.add(new LabelValueBean(label, dateFormat));
        }
        form.setAvailableDateFormats(availableDateFormats);
        
        form.getAvailableDecimalSeparators().add(new LabelValueBean(".", "."));
        form.getAvailableDecimalSeparators().add(new LabelValueBean(",", ","));
        
        
        form.getAvailableGroupingSeparators().add(new LabelValueBean(",", ","));
        form.getAvailableGroupingSeparators().add(new LabelValueBean(" ", " "));
        form.getAvailableGroupingSeparators().add(new LabelValueBean(".", "."));
    }
}
