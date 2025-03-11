/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.validator.type;

import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.intf.ICategory;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.EnumerationType;
import com.silvermoongroup.fsa.web.common.property.validator.IPropertyValidator;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

public class CategoryPropertyValidator extends AbstractValidator {

    public static final String MSG_CATEGORY_INVALID = "page.propertygeneric.message.category.invalid";
    @Override
    public String validate(HttpServletRequest request, String value) {
        if(StringUtils.isEmpty(value)) {
            return null;
        }
        wac = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        IProductDevelopmentService productDevelopmentService = wac.getBean(IProductDevelopmentService.class);
        ICategory category = productDevelopmentService.getCategoryByName(new ApplicationContext(),value);
        if (category == null) {
            return MSG_CATEGORY_INVALID;
        }
        return null;
    }

}
