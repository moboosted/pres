/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.common.datatype.CurrencyAmount;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Format a currency amount.
 * 
 * @author Justin Walsh
 */
public class FormatCurrencyAmountTag extends TagSupport {
    
    private static final long serialVersionUID = 1L;
    
    private CurrencyAmount value;
    private boolean cc = true;
    
    
    @Override
    public int doStartTag() throws JspException {
    
        if (value == null || value.getAmount() == null) {
            return SKIP_BODY;
        }
        
        StringBuilder output = new StringBuilder();
        if (value.getCurrencyCode() != null && cc) {
            EnumerationReference currencyCode = value.getCurrencyCode();
            output.append(getTypeFormatter().formatEnum(currencyCode));
            output.append(" ");
        }
        
        output.append(getTypeFormatter().formatCurrencyAmount(getValue()));
        
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
    
    public CurrencyAmount getValue() {
        return value;
    }
    public void setValue(CurrencyAmount currencyAmount) {
        this.value = currencyAmount;
    }
    
    public boolean isCc() {
        return cc;
    }
    public void setCc(boolean includeCurrencyCode) {
        this.cc = includeCurrencyCode;
    }
}
