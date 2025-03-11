/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.renderer.type;

import com.silvermoongroup.businessservice.policymanagement.dto.PropertyDTO;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.fsa.web.common.property.renderer.AbstractPropertyRenderer;
import com.silvermoongroup.fsa.web.common.property.renderer.input.AbstractInputRenderer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.PageContext;

public class TypePropertyRenderer extends AbstractPropertyRenderer {

    @Override
    protected String formatPropertyValue(PageContext pageContext, Object propertyValue) {
        if (propertyValue == null) {
            return StringUtils.EMPTY;
        }
        Long typeId = (Long)propertyValue;
        IProductDevelopmentService productDevelopmentService = WebApplicationContextUtils.getWebApplicationContext
                (pageContext.getServletContext()).getBean(IProductDevelopmentService.class);

        Type type = productDevelopmentService.getType(new ApplicationContext(), typeId);
        if(type == null) {
            return StringUtils.EMPTY;
        }
        return type.getName();
    }

}
