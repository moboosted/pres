/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.validator.type;

import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.EnumerationType;
import org.apache.commons.validator.GenericValidator;

import javax.servlet.http.HttpServletRequest;

/**
 * Validates the String representation of a currency amount.
 * 
 * <p>
 * The validation behaviour of this class is as follows:
 *
 * <ol>
 * <li>The currency code passed in is matched. If it does not match,
 * {@link CurrencyAmountPropertyValidator#MSG_CURRENCY_AMOUNT_INVALID} is returned.</li>
 * <li>If a currency code has not been specified, the currency code is retrieved from the session.</li>
 * <li>If the currency code has been specified, but is not valid, a
 * {@link CurrencyAmountPropertyValidator#MSG_CURRENCY_CODE_INVALID} value is returned.</li>
 * <li>Otherwise, validation passes and null is returned.</li>
 * </ol>
 * 
 * @author Justin Walsh
 */
public class CurrencyAmountPropertyValidator extends AbstractValidator {

    public static final String MSG_CURRENCY_CODE_INVALID = "page.propertygeneric.message.currency.code.invalid";
    public static final String MSG_CURRENCY_AMOUNT_INVALID = "page.propertygeneric.message.currency.amount.invalid";

    /**
     * @see com.silvermoongroup.fsa.web.common.property.validator.IPropertyValidator#validate(javax.servlet.http.HttpServletRequest,
     *      java.lang.String)
     */
    @Override
    public String validate(HttpServletRequest request, String value) {

        if (GenericValidator.isBlankOrNull(value)) {
            return null;
        }

        String[] currencyAmountParts = value.split(":");

        if (currencyAmountParts.length != 2) {
            return MSG_CURRENCY_AMOUNT_INVALID;
        }

        if (GenericValidator.isBlankOrNull(currencyAmountParts[1])) {
            return MSG_CURRENCY_CODE_INVALID;
        }

        if (!isValidBigDecimal(request, currencyAmountParts[0])) {
            return MSG_CURRENCY_AMOUNT_INVALID;
        }

        // validate the currency code
        IProductDevelopmentService productDevelopmentService = wac.getBean(IProductDevelopmentService.class);
        IEnumeration enumeration = productDevelopmentService.findEnumerationByNameAndType(new ApplicationContext(),
                currencyAmountParts[1], EnumerationType.CURRENCY_CODE.getValue());
        if (enumeration == null) {
            return MSG_CURRENCY_CODE_INVALID;
        }

        return null;
    }
}