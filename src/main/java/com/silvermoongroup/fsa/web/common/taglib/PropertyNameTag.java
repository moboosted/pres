package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.businessservice.policymanagement.dto.PropertyDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.StructuredActualDTO;
import com.silvermoongroup.fsa.web.common.format.PropertyInputName;
import com.silvermoongroup.fsa.web.common.format.PropertyInputNameUtils;
import com.silvermoongroup.fsa.web.common.property.IPropertyTypeRegistry;
import com.silvermoongroup.fsa.web.common.property.renderer.IPropertyRenderer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Map;

/**
 * Render a property name, either in read only or editable mode.
 */
public class PropertyNameTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private PropertyDTO property;
    private StructuredActualDTO structuredActual;
    private Map<PropertyInputName,Object> inputValues;
    private Map<PropertyInputName,String> errorMessages;

    public PropertyNameTag() {
    }

    @Override
    public int doStartTag() throws JspException {

        WebApplicationContext wac =
                WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());

        IPropertyTypeRegistry registry = wac.getBean(IPropertyTypeRegistry.class);

        PropertyInputName propertyInputName = PropertyInputNameUtils.extractPropertyInputNameFromProperty(getProperty());

        String inputValue = getInputValueForPropertyInputName(propertyInputName);

        IPropertyRenderer propertyRenderer =
                registry.getRendererForType(property.getConformanceType().getName());

        if (propertyRenderer != null) {
            if (property.isReadOnly()) {
                propertyRenderer.renderReadOnlyPropertyName(pageContext, property, inputValue);
            }
            else {
                propertyRenderer.renderReadOnlyPropertyName(pageContext, property, inputValue);
            }
        }

        return Tag.SKIP_BODY;
    }

    public PropertyDTO getProperty() {
        return property;
    }

    public void setProperty(PropertyDTO property) {
        this.property = property;
    }

    public Map<PropertyInputName, Object> getInputValues() {
        return inputValues;
    }

    public void setInputValues(Map<PropertyInputName, Object> inputValues) {
        this.inputValues = inputValues;
    }

    public Map<PropertyInputName, String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<PropertyInputName, String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public StructuredActualDTO getStructuredActual() {
        return structuredActual;
    }

    public void setStructuredActual(StructuredActualDTO structuredActual) {
        this.structuredActual = structuredActual;
    }

    private String getInputValueForPropertyInputName(PropertyInputName propertyInputName) {
        return (String) getInputValues().get(propertyInputName);
    }
}
