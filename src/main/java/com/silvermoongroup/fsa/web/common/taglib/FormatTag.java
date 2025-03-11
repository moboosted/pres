/**
 * 
 */
package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import com.silvermoongroup.kindmanager.domain.Kind;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Format an 'IAA' object.
 * 
 * Support is provided for formatting a:
 * <ul>
 * <li>{@link Kind}</li>
 * <li>{@link com.silvermoongroup.common.datatype.Date}</li>
 * </ul>
 * 
 * @author Justin Walsh
 * 
 */
public class FormatTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private Object value;
    private List<String> arguments;
    private String arg;
    private String fallback;

    public FormatTag() {
        value = null;
        arguments = null;
    }

    @Override
    public int doStartTag() throws JspException {

        Object toFormat = getValue();

        String output;
        if (toFormat == null) {
            output = StringUtils.EMPTY;
        } else if (toFormat instanceof String) {
            
            String [] args;
            List<String> arguments = getArguments();
            if (arguments != null && !arguments.isEmpty()) {
                args = arguments.toArray(new String [arguments.size()]);
            }
            else if (getArg() != null) {
                args = new String [] {getArg()};
            }
            else {
                args = new String [0];
            }
            if (getFallback() != null) {
                output = getTypeFormatter().formatMessageWithFallback((String) toFormat, fallback, args);
            }
            else {
                output = getTypeFormatter().formatMessage((String) toFormat, args);
            }
        } 
        else {
            output = getTypeFormatter().formatObject(toFormat);
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

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }
    
    public String getArg() {
        return arg;
    }
    
    public void setArg(String arg) {
        this.arg = arg;
    }

    public String getFallback() {
        return fallback;
    }

    public void setFallback(String fallback) {
        this.fallback = fallback;
    }

    private ITypeFormatter getTypeFormatter() {
        WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        return ac.getBean(ITypeFormatter.class);
    }

}