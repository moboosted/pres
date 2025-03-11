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
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.intf.ICategory;
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

public class CategoryPropertyRenderer extends AbstractPropertyRenderer {

    @Override
    protected String formatPropertyValue(PageContext pageContext, Object propertyValue) {
        if (propertyValue == null) {
            return StringUtils.EMPTY;
        }
        ObjectReference categoryObjectReference = (ObjectReference)propertyValue;
        IProductDevelopmentService productDevelopmentService = WebApplicationContextUtils.getWebApplicationContext
                (pageContext.getServletContext()).getBean(IProductDevelopmentService.class);

        ICategory category = productDevelopmentService.getCategory(new ApplicationContext(), categoryObjectReference);
        if(category == null) {
            return StringUtils.EMPTY;
        }
        return category.getName();
    }

}
