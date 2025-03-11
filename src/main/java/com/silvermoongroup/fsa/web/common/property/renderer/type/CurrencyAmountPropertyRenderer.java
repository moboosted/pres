/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.renderer.type;

import com.silvermoongroup.businessservice.policymanagement.dto.PropertyDTO;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.datatype.CurrencyAmount;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.EnumerationType;
import com.silvermoongroup.common.enumeration.intf.ICurrencyCode;
import com.silvermoongroup.fsa.web.common.format.PropertyInputName;
import com.silvermoongroup.fsa.web.common.format.PropertyInputNameUtils;
import com.silvermoongroup.fsa.web.common.property.converter.type.CurrencyAmountPropertyConverter;
import com.silvermoongroup.fsa.web.common.property.renderer.AbstractPropertyRenderer;
import com.silvermoongroup.fsa.web.common.property.renderer.input.AbstractInputRenderer;
import com.silvermoongroup.fsa.web.common.property.renderer.input.CurrencyAmountSelectInputRenderer;
import com.silvermoongroup.fsa.web.common.property.renderer.input.TextInputRenderer;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CurrencyAmountPropertyRenderer extends AbstractPropertyRenderer {

    private static final String AMOUNT_COMPONENT_NAME = "CurrencyAmount";
    private static final String CURRENCY_CODE = "ICurrencyCode";
    private static final String CURRENCY_VALUE = "CurrencyValue";
    private static final String DELIMITER = "|";
    private static final String ESCAPE = "\\";

    private boolean omitCode = false;

    public CurrencyAmountPropertyRenderer() {
    }

    @Override
    protected AbstractInputRenderer createInputRenderer(PageContext pageContext, PropertyDTO propertyDTO, String inputValue) {

        return new TextInputRenderer(
                StringEscapeUtils.escapeHtml3("property-kind-" + propertyDTO.getKind().getName()),
                getPropertyInputName(propertyDTO),
                getFormattedPropertyValue(pageContext, propertyDTO, inputValue)
        );
    }

    @Override
    protected void renderInputComponent(PageContext pageContext, PropertyDTO propertyDTO, String inputValue,
                                        String errorMessage) {

        setOmitCode(true);

        @SuppressWarnings("unchecked")
        Collection<ICurrencyCode> currencyCodes = (Collection<ICurrencyCode>)getAllowedPropertyValues(pageContext, propertyDTO);

        if (currencyCodes != null && !currencyCodes.isEmpty()) {
            EnumerationReference currencyCode = null;
            String currencyAmount = StringUtils.EMPTY;

            // currency amount allowed values
            if (errorMessage == null) {
                CurrencyAmount propertyValue = formatForSelectInput(pageContext, propertyDTO, inputValue);
                if (propertyValue != null) {
                    currencyCode = propertyValue.getCurrencyCode();
                    currencyAmount = formatPropertyValue(pageContext, propertyValue);
                }
            } else {
                String[] splitString = inputValue.split(":");
                currencyAmount = splitString[0];

                if (splitString.length > 1) {
                    currencyCode = EnumerationReference.convertFromString(splitString[1] + ":" + splitString[2]);
                }
            }

            String propertyInputName = getPropertyInputName(propertyDTO);
            String[] splitString = propertyInputName.split(ESCAPE + DELIMITER);

            final String currencyCodeString = splitString[0] + DELIMITER + CURRENCY_CODE;
            final String currencyAmountString = splitString[0] + DELIMITER + CURRENCY_VALUE;

            CurrencyAmountSelectInputRenderer selInputRenderer = new CurrencyAmountSelectInputRenderer(
                    StringEscapeUtils.escapeHtml3("property-kind-" + propertyDTO.getKind().getName() + "-code"), currencyCodeString, currencyCode,
                    currencyCodes);
            selInputRenderer.setStyleClass("form-control input-sm");
            TextInputRenderer txtInputRenderer = (TextInputRenderer) createInputRenderer(pageContext, propertyDTO,
                    currencyAmount);
            txtInputRenderer.setName(currencyAmountString);
            txtInputRenderer.setStyleClass("form-control input-sm");

            if (errorMessage != null) {
                txtInputRenderer.setStyleClass("form-control input-sm has-error");
                selInputRenderer.setStyleClass("form-control input-sm has-error");
            }

            // Write out the input elements
            writeContent(pageContext, txtInputRenderer.getRenderedInputField(pageContext));
            writeContent(pageContext, selInputRenderer.getRenderedInputField(pageContext));
        } else {
            PropertyInputName propertyInputName = PropertyInputNameUtils
                    .extractPropertyInputNameFromProperty(propertyDTO);

            renderAmount(pageContext, propertyDTO, propertyInputName, inputValue, errorMessage);
        }
    }

    private void renderAmount(PageContext pageContext, PropertyDTO propertyDTO, PropertyInputName propertyInputName,
                              String inputValue, String errorMessage) {
        propertyInputName.setType(AMOUNT_COMPONENT_NAME);
        TextInputRenderer inputRenderer = new TextInputRenderer("edit-kind-" + propertyDTO.getKind().getId(),
                propertyInputName.toString(), formatForTextInput(propertyDTO, inputValue), 10);

        if (errorMessage != null) {
            inputRenderer.setStyleClass("error");
        }

        writeContent(pageContext, inputRenderer.getRenderedInputField(pageContext));
    }

    @Override
    public void doRenderPropertyValue(PageContext pageContext, PropertyDTO property, String inputValue) {
        setOmitCode(false);
        super.doRenderPropertyValue(pageContext, property, inputValue);
    }

    /**
     * Format the currency amount for display
     */
    @Override
    protected String formatPropertyValue(PageContext pageContext, Object propertyValue) {
        if (propertyValue == null) {
            return StringUtils.EMPTY;
        }

        ITypeFormatter typeFormatter =
                WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext()).getBean(ITypeFormatter.class);

        IProductDevelopmentService productDevelopmentService = WebApplicationContextUtils.getWebApplicationContext
                (pageContext.getServletContext()).getBean(IProductDevelopmentService.class);

        CurrencyAmount ca = (CurrencyAmount) propertyValue;
        BigDecimal amountRounded = ca.getAmount().setScale(2, RoundingMode.HALF_UP);

        String currencyAmount = typeFormatter.formatBigDecimal(amountRounded);

        if ((!isOmitCode()) && ca.getCurrencyCode() != null) {
            IEnumeration currencyCode = productDevelopmentService.getEnumeration(new ApplicationContext(),
                                                                                 ca.getCurrencyCode()
            );
            return currencyAmount + ":" + currencyCode.getName();
        } else {
            return currencyAmount;
        }
    }

    /**
     * @param propertyDTO The currency amount property.
     * @param inputValue  The input value.
     * @return The formatted amount
     */
    private CurrencyAmount formatForSelectInput(PageContext pageContext, PropertyDTO propertyDTO, String inputValue) {
        if (inputValue != null) {
            return (CurrencyAmount) new CurrencyAmountPropertyConverter().convertFromString(
                    (HttpServletRequest) pageContext.getRequest(), inputValue);
        }
        if (propertyDTO == null || propertyDTO.getValue() == null) {
            return null;
        }

        return (CurrencyAmount) propertyDTO.getValue();
    }

    /**
     * Return the currency amount in a format for display in an input element
     *
     * @param propertyDTO The currency amount property.
     * @param inputValue  The input value.
     * @return The formatted amount
     */
    private String formatForTextInput(PropertyDTO propertyDTO, String inputValue) {
        if (inputValue != null) {
            return inputValue;
        }
        if (propertyDTO == null || propertyDTO.getValue() == null) {
            return StringUtils.EMPTY;
        }

        return String.valueOf(propertyDTO.getValue());
    }

    @Override
    protected Collection<?> getAllowedPropertyValues(PageContext pageContext, PropertyDTO propertyDTO) {

        @SuppressWarnings("unchecked")
        IProductDevelopmentService productDevelopmentService = WebApplicationContextUtils.getWebApplicationContext(
                pageContext.getServletContext()).getBean(IProductDevelopmentService.class);
        Collection<IEnumeration> currencyCodeEnums = productDevelopmentService.findEnumerationsByType(
                new ApplicationContext(), EnumerationType.CURRENCY_CODE.getValue());

        Collections.sort((List)currencyCodeEnums, new Comparator<IEnumeration>() {
                             @Override
                             public int compare(IEnumeration o1, IEnumeration o2) {
                                 return o1.getName().compareTo(o2.getName());
                             }
                         });

        return currencyCodeEnums;
    }

    /**
     * Should the code component of the currency amount be rendered.
     *
     * @return true if the code component should be rendered, otherwise false.
     */
    public boolean isOmitCode() {
        return omitCode;
    }

    public void setOmitCode(boolean omitCode) {
        this.omitCode = omitCode;
    }
}
