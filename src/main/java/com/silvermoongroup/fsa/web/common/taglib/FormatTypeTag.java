/**
 * 
 */
package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Format a <code>Type</code> object.
 * 
 * @author Justin Walsh
 *
 */
public class FormatTypeTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private Object value;

    public FormatTypeTag() {
        value = null;
    }

    @Override
    public int doStartTag() throws JspException {

        Object toFormat = getValue();
        
        String output;
        if (toFormat == null) {
            output = StringUtils.EMPTY;
        }
        else if (toFormat instanceof Number) {
            Number value = (Number)toFormat;
            output = getTypeFormatter().formatType(value.longValue());
        }
        else if (toFormat instanceof Type){
            Type type = (Type)toFormat;
            output = getTypeFormatter().formatType(type);
        }
        else {
            throw new IllegalStateException("Cannot format object of class [" + toFormat.getClass() + "]: " + toFormat);
        }
        

        JspWriter w = pageContext.getOut();
        try {
            w.write(output);
        } catch (IOException ex) {
            throw new JspException(ex.toString(), ex);
        }

        return SKIP_BODY;
    }

    @Override
    public void release() {
        super.release();
        value = null;
    }

    @Override
    public int doEndTag() throws JspException {
        setValue(null);
        return super.doEndTag();
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    private ITypeFormatter getTypeFormatter() {
        WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        return ac.getBean(ITypeFormatter.class);
    }


}
