/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.converter.type;

import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.datatype.CurrencyAmount;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.domain.intf.IType;
import com.silvermoongroup.common.enumeration.EnumerationType;
import com.silvermoongroup.fsa.web.common.property.validator.type.CurrencyAmountPropertyValidator;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Converts a currency amount representation from a {@link String} to a {@link CurrencyAmount}.
 * 
 * <p>
 * The invocation of the {@link #convertFromString(HttpServletRequest, String)} method assumes that the validation has
 * passed.
 *
 * 
 * @author Justin Walsh
 * @see CurrencyAmountPropertyValidator
 */
public class TypePropertyConverter extends AbstractConverter {

    public TypePropertyConverter() {
    }

    /**
     * @see com.silvermoongroup.fsa.web.common.property.converter.IPropertyConverter#convertFromString(HttpServletRequest,
     *      String)
     */
    @Override
    public Object convertFromString(HttpServletRequest request, String value) {

        if (GenericValidator.isBlankOrNull(value)) {
            return null;
        }

        WebApplicationContext wac =
                WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        IProductDevelopmentService productDevelopmentService = wac.getBean(IProductDevelopmentService.class);

        IType type = productDevelopmentService.findType(new ApplicationContext(), value);

        if(type == null) {
            throw new IllegalStateException("Type not found: " + value);
        }
        return type.getId();

    }
}