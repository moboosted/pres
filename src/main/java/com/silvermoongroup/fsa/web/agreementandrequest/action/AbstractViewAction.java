/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.action;

import com.silvermoongroup.base.exception.ApplicationRuntimeException;
import com.silvermoongroup.businessservice.policymanagement.dto.KindDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.OnlyActualDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.RoleListDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.StructuredActualDTO;
import com.silvermoongroup.fsa.web.agreementandrequest.constants.InputPropertiesEnum;
import com.silvermoongroup.fsa.web.agreementandrequest.form.AbstractViewForm;
import com.silvermoongroup.fsa.web.agreementandrequest.util.RoleListByConformanceTypeAndKindComparator;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.common.format.PropertyInputName;
import com.silvermoongroup.fsa.web.common.property.converter.InputPropertiesConverter;
import com.silvermoongroup.fsa.web.common.property.validator.InputPropertiesValidator;
import com.silvermoongroup.kindmanager.exceptions.DuplicateKindException;
import com.silvermoongroup.kindmanager.exceptions.KindNotFoundException;
import com.silvermoongroup.spflite.enumeration.ConformanceType;
import com.silvermoongroup.spflite.specframework.common.exceptions.ConformanceTypeViolationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.*;

/**
 * @author mubeen
 * @author Justin Walsh
 */
public abstract class AbstractViewAction<T extends AbstractViewForm<?>> extends AbstractLookupDispatchAction {

    private InputPropertiesConverter inputPropertiesConverter;
    private InputPropertiesValidator inputPropertiesValidator;

    @Override
    protected void onInit() {
        super.onInit();
        inputPropertiesConverter = getBean(InputPropertiesConverter.class);
        inputPropertiesValidator = getBean(InputPropertiesValidator.class);
    }

    public abstract ActionForward submit(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
                                         HttpServletResponse response);

    /**
     * Is the structured actual dirty? This check should return true if any of the <em>attributes</em> of the structured
     * actual have been modified and require persistence (for example, the requested date of the request). This check
     * should exclude any changes to the properties of the structured actual which are checked separately.
     * <p>
     * If this method returns true (or any of the properties have changed) an invocation to
     * {@link #saveStructuredActual(AbstractViewForm, String)} is made
     *
     * <p>
     * The default implementation of this method returns false.
     *
     *
     * @param request The request
     * @param form    The form
     * @return true if the structured actual is dirty and requires saving.
     */
    protected boolean isStructuredActualDirty(HttpServletRequest request, T form) {
        return false;
    }

    /**
     * Save the structured actual and any properties associated with it.
     *
     * @param form          The form
     * @param initialMethod The method invoked.
     */
    protected abstract void saveStructuredActual(T form, String initialMethod);

    protected abstract void handlePropertyValidationFailurePath(ActionForm actionForm);

    protected ActionForward invokeInitialMethod(ActionMapping mapping, T form, HttpServletRequest request,
                                                HttpServletResponse response, String initialMethod) {

        ActionForward actionForward = null;
        Class<?> classWithMethodToInvoke = getClass();

        try {
            Class<?>[] parameterTypes = new Class[4];
            parameterTypes[0] = ActionMapping.class;
            parameterTypes[1] = ActionForm.class;
            parameterTypes[2] = HttpServletRequest.class;
            parameterTypes[3] = HttpServletResponse.class;

            String translatedMethodName;
            // if a method is specified with a . (dot) prefix, strip off the dot
            // and use AS IS (don't localise)
            // this logic means that we don't need to internationalise method
            // parameters in urls (links)
            // and allows us to put two buttons with the same name on the same
            // form
            if (initialMethod.startsWith(".")) {
                translatedMethodName = initialMethod.substring(1);
            } else {
                translatedMethodName = getLookupMapName(request, initialMethod, mapping);
            }

            Method method = classWithMethodToInvoke.getMethod(translatedMethodName, parameterTypes);

            Object[] argumentList = new Object[4];
            argumentList[0] = mapping;
            argumentList[1] = form;
            argumentList[2] = request;
            argumentList[3] = response;

            actionForward = (ActionForward) method.invoke(this, argumentList);

        } catch (Exception ex) {
            throw new ApplicationRuntimeException("Error invoking initial method '" + initialMethod + "': "
                    + ex.getMessage(), ex);
        }

        if (actionForward == null) {
            actionForward = mapping.getInputForward();
        }

        return actionForward;
    }

    /**
     * This handler is invoked by most form submissions so that property validation and binding can occur before the
     * original 'action' is invoked.
     * <p>
     * The handler validate and binds the properties, and then delegates to the method which was targeted by the
     * submission of the form.
     */
    public ActionForward validateAndBindProperties(ActionMapping mapping, T form, HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {
        // validate the properties
        organiseInputProperties(form);
        Map<PropertyInputName, Object> allInputProperties = form.getAllInputProperties();

        form.setInputPropertiesValidationMessages(inputPropertiesValidator.getInputPropertiesValidationMessages(
                request, allInputProperties, form.getActionMessages()));

        ActionForward forward;
        if (form.getInputPropertiesValidationMessages().isEmpty()) {

            // convert the properties to their object representations
            Map<KindDTO, Object> convertedValues = inputPropertiesConverter.convertValuesToCorrectType(request,
                    form.getAllInputProperties());

            String initialMethod = form.getInitialMethod();
            String initialUrl = form.getInitialUrl();

            // bind the properties to the structured actual
            boolean dirtyProperties = saveConvertedValuesToStructuredActualDTO(request, form, convertedValues);
            if (dirtyProperties || isStructuredActualDirty(request, form)) {
                saveStructuredActual(form, initialMethod);
            }

            // reset the validation attributes
            form.resetPropertyValidationAttributes();

            // invoke the original method/url
            if (initialUrl != null) {
                forward = invokeInitialUrl(request, initialUrl);
            } else if (initialMethod != null) {
                forward = invokeInitialMethod(mapping, form, request, response, initialMethod);
            } else {
                forward = submit(mapping, form, request, response);
            }
        } else {
            saveErrorMessages(request, form.getActionMessages());

            handlePropertyValidationFailurePath(form);
            preparePageForDisplay(form, request);
            forward = mapping.getInputForward();
        }

        return forward;
    }

    protected abstract void preparePageForDisplay(T form, HttpServletRequest request);

    protected void sortRoleListsAndRoles(OnlyActualDTO onlyActual) {

        // sort the role lists by conformance type and kind
        if (onlyActual.getRoleLists() != null) {
            List<RoleListDTO> roleLists = new ArrayList<>(onlyActual.getRoleLists());
            Collections.sort(roleLists, RoleListByConformanceTypeAndKindComparator.getInstance());
            onlyActual.setRoleLists(roleLists);
        }
    }

    // helper
    private ActionForward invokeInitialUrl(HttpServletRequest request, String url) {
        ActionForward actionForward = new ActionForward();

        if (log.isDebugEnabled()) {
            log.debug("Invoking initialUrl '" + url + "'");
        }

        try {
            actionForward.setPath(RequestUtils.serverURL(request).toString() + url);
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Error invoking initial url '" + url + "': " + ex.getMessage(), ex);
        }

        // we always redirect this actionforward
        actionForward.setRedirect(true);

        return actionForward;
    }

    /**
     * Save the converted values to the {@link StructuredActualDTO}, indicating whether any of the values have changed
     */
    private boolean saveConvertedValuesToStructuredActualDTO(HttpServletRequest request, T form,
                                                             Map<KindDTO, Object> convertedValues) throws KindNotFoundException, DuplicateKindException,
            ConformanceTypeViolationException {
        StructuredActualDTO structuredActual = form.getStructuredActualDTO();

        boolean dirty = false;

        for (KindDTO kind : convertedValues.keySet()) {

            Object originalValue = structuredActual.getPropertyOfKind(kind).getValue();
            Object value = convertedValues.get(kind);

            // if both are null - they have not changed
            if (originalValue == null && value == null) {
                continue;
            }
            else if (originalValue != null && value != null) {

                // first check if they are comparable
                if (Comparable.class.isAssignableFrom(value.getClass())) {
                    Comparable c1 = (Comparable)value;
                    Comparable c2 = (Comparable)originalValue;
                    if (c1.compareTo(c2) == 0) {
                        continue;
                    }
                }
                else if (originalValue.equals(value)) {
                    continue;
                }
            }

            if (log.isDebugEnabled()) {
                log.debug("Writing value: " + value + " into " + kind.getName());
            }
            structuredActual.setValueOfPropertyOfKind(kind, value);
            dirty = true;
        }

        return dirty;
    }

    private void organiseInputProperties(T form) {
        Map<PropertyInputName, Object> allInputProperties = form.getAllInputProperties();
        Map<PropertyInputName, Object> updatedInputProperties = new HashMap<>();

        for (PropertyInputName propertyInputName : allInputProperties.keySet()) {
            String value = (String) allInputProperties.get(propertyInputName);

            if (InputPropertiesEnum.CURRENCY_VALUE.getName().equals(propertyInputName.getType())) {
                // This is already taken care of in the ICurrencyCode check. It will assemble a CurrencyAmount object
                continue;
            }

            if (InputPropertiesEnum.CURRENCY_CODE.getName().equals(propertyInputName.getType())) {
                // Construct new value, Reset the conformance Type to CurrencyAmount, add back to the Map
                value = assembleCurrencyAmountForCurrencyCode(value, propertyInputName.getKindId(), allInputProperties);
                propertyInputName.setType(ConformanceType.CURRENCYAMOUNT.getName());
            }

            updatedInputProperties.put(propertyInputName, value);
        }

        allInputProperties.clear();
        allInputProperties.putAll(updatedInputProperties);
    }

    private static String assembleCurrencyAmountForCurrencyCode(String value, String kindId,
                                                                Map<PropertyInputName, Object> inputProperties) {
        PropertyInputName propertyInputName = new PropertyInputName();
        propertyInputName.setKindId(kindId);
        propertyInputName.setType(InputPropertiesEnum.CURRENCY_VALUE.getName());

        String amountValue = (String) inputProperties.get(propertyInputName);

        if (GenericValidator.isBlankOrNull(amountValue)) {
            return StringUtils.EMPTY;
        }

        return new StringBuilder().append(amountValue).append(":").append(value).toString();
    }
}
