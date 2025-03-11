/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.renderer;

import com.silvermoongroup.businessservice.policymanagement.dto.PropertyDTO;
import com.silvermoongroup.fsa.web.common.format.PropertyInputName;
import com.silvermoongroup.fsa.web.common.format.PropertyInputNameUtils;
import com.silvermoongroup.fsa.web.common.property.renderer.input.AbstractInputRenderer;
import com.silvermoongroup.fsa.web.common.property.renderer.input.SelectInputRenderer;
import com.silvermoongroup.fsa.web.common.property.renderer.input.TextInputRenderer;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import com.silvermoongroup.spflite.specframework.domain.versionedproduct.versionedattributespec.PropertySpecValue;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.Collection;

public abstract class AbstractPropertyRenderer implements IPropertyRenderer {

    private static Logger log = LoggerFactory.getLogger(AbstractPropertyRenderer.class);

    public AbstractPropertyRenderer() {
    }

    protected void renderInputComponent(PageContext pageContext, PropertyDTO propertyDTO, String inputValue, String errorMessage) {

        AbstractInputRenderer inputRenderer = createInputRenderer(pageContext, propertyDTO, inputValue);

        configureInputStyles(pageContext, inputRenderer, propertyDTO, inputValue, errorMessage);

        writeContent(pageContext, inputRenderer.getRenderedInputField(pageContext));
        renderAfterInputField(pageContext, propertyDTO, inputValue, errorMessage);
    }

    protected void configureInputStyles(PageContext pageContext, AbstractInputRenderer inputRenderer, PropertyDTO propertyDTO,
            String inputValue, String errorMessage) {

        if (errorMessage != null) {
            inputRenderer.setStyleClass("form-control input-sm has-error");
        }
        else {
            inputRenderer.setStyleClass("form-control input-sm");
        }
    }

    protected AbstractInputRenderer createInputRenderer(PageContext pageContext, PropertyDTO propertyDTO, String inputValue) {
        final Collection<?> allowedPropertyValues = getAllowedPropertyValues(pageContext, propertyDTO);
        if (allowedPropertyValues != null && allowedPropertyValues.size() > 0) {
            return new SelectInputRenderer(
                    StringEscapeUtils.escapeHtml3("property-kind-" + propertyDTO.getKind().getName()),
                    getPropertyInputName(propertyDTO),
                    getFormattedPropertyValue(pageContext, propertyDTO, inputValue),
                    allowedPropertyValues,
                    getDefaultPropertyValue(propertyDTO));
        }

        return new TextInputRenderer(
                StringEscapeUtils.escapeHtml3("property-kind-" + propertyDTO.getKind().getName()),
                getPropertyInputName(propertyDTO),
                getFormattedPropertyValue(pageContext, propertyDTO, inputValue)
        );
    }

    protected abstract String formatPropertyValue(PageContext pageContext, Object propertyValue);

    @Override
    public void renderEditablePropertyValue(PageContext pageContext,
            PropertyDTO property, String inputValue, String errorMessage) {
        renderInputComponent(pageContext, property, inputValue, errorMessage);

    }

    @Override
    public final void renderReadOnlyPropertyValue(PageContext pageContext,
            PropertyDTO property, String inputValue) {

        String id = StringEscapeUtils.escapeHtml3("property-kind-" + property.getKind().getName());
        writeContent(
                pageContext,
                "<span id=\"" + id + "\">");
        doRenderPropertyValue(pageContext, property, inputValue);
        writeContent(
                pageContext,
                "</span>");
    }

    @Override
    public void renderReadOnlyPropertyName(PageContext pageContext,
            PropertyDTO property, String inputValue) {
        renderPropertyName(pageContext, property);

    }

    private void renderPropertyName(PageContext pageContext, PropertyDTO property) {
        writeContent(pageContext, FormatUtil.getTypeFormatter(pageContext).formatSpecificationObjectName(
                property.getKind(), property.getDefaultDisplayName()));
    }

    /**
     * Hook method which allows subclasses to render their own property value.
     */
    public void doRenderPropertyValue(PageContext pageContext,
            PropertyDTO property, String inputValue) {

        writeContent(
                pageContext,
                getFormattedPropertyValue(pageContext, property, inputValue));
    }

    /**
     * Returns the formatted property value that should be displayed.  If the
     * input value is a non-null value, it is returned as is, otherwise the
     * values of the property is formatted.
     *
     * @param pageContext The page context.
     * @param propertyDTO The property.
     * @param inputValue  The value input.
     * @return A formatted property value.
     */
    protected String getFormattedPropertyValue(PageContext pageContext, PropertyDTO propertyDTO, String inputValue) {

        if (inputValue != null) {
            return inputValue;
        }
        /* We are repeating the formatting here that is present in the respective property mappers */
        return formatPropertyValue(pageContext, propertyDTO.getValue());
    }

    protected Collection<?> getAllowedPropertyValues(PageContext pageContext, PropertyDTO propertyDTO) {
        return propertyDTO.getAllowedValues();
    }

    protected Object getDefaultPropertyValue(PropertyDTO propertyDTO) {
        PropertySpecValue propertySpecValue = (PropertySpecValue) propertyDTO.getDefaultValue();
        if (propertySpecValue == null) {
            return null;
        }
        return propertySpecValue.getValue();
    }

    protected String getPropertyInputName(PropertyDTO propertyDTO) {
        PropertyInputName propertyInputName =
                PropertyInputNameUtils.extractPropertyInputNameFromProperty(propertyDTO);
        return propertyInputName.toString();
    }

    protected void writeContent(PageContext pageContext, String content) {
        try {
            pageContext.getOut().write(content);
        } catch (IOException ioe) {
            AbstractPropertyRenderer.log.error(ioe.getMessage(), ioe);
        }
    }

    protected void renderAfterInputField(PageContext pageContext, PropertyDTO propertyDTO, String inputValue,
            String errorMessage) {
    }

    protected ITypeFormatter getTypeFormatter(PageContext pageContext) {
        return FormatUtil.getTypeFormatter((HttpServletRequest) pageContext.getRequest());
    }
}
