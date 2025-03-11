/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.renderer.type;

import com.silvermoongroup.businessservice.policymanagement.dto.PropertyDTO;
import com.silvermoongroup.fsa.web.common.property.renderer.AbstractPropertyRenderer;
import com.silvermoongroup.fsa.web.common.property.renderer.input.AbstractInputRenderer;
import com.silvermoongroup.fsa.web.user.profile.IUserProfileManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.PageContext;

public class DatePropertyRenderer extends AbstractPropertyRenderer {

    @Override
    protected void configureInputStyles(PageContext pageContext, AbstractInputRenderer inputRenderer,
            PropertyDTO propertyDTO, String inputValue, String errorMessage) {
        if (errorMessage != null) {
            inputRenderer.setStyleClass("form-control input-sm datefield has-error");
        } else {
            inputRenderer.setStyleClass("form-control input-sm datefield");
        }
    }

    @Override
    protected String formatPropertyValue(PageContext pageContext, Object propertyValue) {

        if (propertyValue == null) {
            return StringUtils.EMPTY;
        }

        IUserProfileManager upm = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext())
                .getBean(IUserProfileManager.class);

        com.silvermoongroup.common.datatype.Date date = (com.silvermoongroup.common.datatype.Date) propertyValue;
        return date.format(upm.getDateFormat());
    }

}
