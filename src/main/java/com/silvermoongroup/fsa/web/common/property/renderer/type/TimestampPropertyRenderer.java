/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.renderer.type;

import com.silvermoongroup.businessservice.policymanagement.dto.PropertyDTO;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.fsa.web.common.property.renderer.AbstractPropertyRenderer;
import com.silvermoongroup.fsa.web.common.property.renderer.input.AbstractInputRenderer;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.PageContext;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampPropertyRenderer extends AbstractPropertyRenderer {

    public TimestampPropertyRenderer() {
    }

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

        if (propertyValue instanceof Date) {
            Date dateValue = (Date) propertyValue;
            SimpleDateFormat sdf = new SimpleDateFormat(DateTime.DEFAULT_PATTERN);
            return sdf.format(dateValue);
        }
        return propertyValue.toString();
    }

}
