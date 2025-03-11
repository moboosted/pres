/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.validator.type;

import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

public class TypePropertyValidator extends AbstractValidator {

    public static final String MSG_TYPE_INVALID = "page.propertygeneric.message.type.invalid";
    @Override
    public String validate(HttpServletRequest request, String value) {
        if(StringUtils.isEmpty(value)) {
            return null;
        }
        wac = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        IProductDevelopmentService productDevelopmentService = wac.getBean(IProductDevelopmentService.class);
        Type type = productDevelopmentService.getType(new ApplicationContext(),value);
        if (type == null) {
            return MSG_TYPE_INVALID;
        }
        return null;
    }


}
