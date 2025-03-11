/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.renderer.type;

import com.silvermoongroup.businessservice.policymanagement.dto.PropertyDTO;
import com.silvermoongroup.common.datatype.Percentage;
import com.silvermoongroup.fsa.web.common.property.renderer.AbstractPropertyRenderer;

import javax.servlet.jsp.PageContext;

public class PercentagePropertyRenderer extends AbstractPropertyRenderer {

    private static final String PERCENTAGE_SIGN = " %";

    public PercentagePropertyRenderer() {
    }

    @Override
    protected void renderAfterInputField(PageContext pageContext, PropertyDTO propertyDTO, String inputValue, String errorMessage)  {
        writeContent(pageContext, PERCENTAGE_SIGN);
    }

    @Override
    protected String formatPropertyValue(PageContext pageContext, Object propertyValue) {        
        Percentage percentage = (Percentage)propertyValue;
        return getTypeFormatter(pageContext).formatPercentage(percentage);
    }
    
    @Override
    public void doRenderPropertyValue(PageContext pageContext, PropertyDTO property, String inputValue) {
        super.doRenderPropertyValue(pageContext, property, inputValue);
        writeContent(pageContext, PERCENTAGE_SIGN);
    }

}
