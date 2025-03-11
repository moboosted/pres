/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.common.datatype.MeasureAmount;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Format a measure amount.
 */
public class FormatMeasureAmountTag extends TagSupport {
    
    private static final long serialVersionUID = 1L;
    
    private MeasureAmount value;
    private boolean includeUnitOfMeasure = true;
    
    
    @Override
    public int doStartTag() throws JspException {
    
        if (value == null || value.getAmount() == null) {
            return SKIP_BODY;
        }
        
        StringBuilder output = new StringBuilder();
        output.append(getTypeFormatter().formatMeasureAmount(getValue(), includeUnitOfMeasure));
        
        JspWriter w = pageContext.getOut();
        try {
            w.write(output.toString());
        } catch (IOException ex) {
            throw new JspException(ex.toString(), ex);
        }

        return SKIP_BODY;
    }

    private ITypeFormatter getTypeFormatter() {
        WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        return ac.getBean(ITypeFormatter.class);
    }
    
    public MeasureAmount getValue() {
        return value;
    }
    public void setValue(MeasureAmount measureAmount) {
        value = measureAmount;
    }

    public boolean isIncludeUnitOfMeasure() {
        return includeUnitOfMeasure;
    }

    public void setIncludeUnitOfMeasure(boolean includeUnitOfMeasure) {
        this.includeUnitOfMeasure = includeUnitOfMeasure;
    }
}
