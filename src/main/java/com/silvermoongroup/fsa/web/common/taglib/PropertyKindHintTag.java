package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.businessservice.policymanagement.dto.PropertyDTO;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PropertyKindHintTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private PropertyDTO property;

    public PropertyKindHintTag() {
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write(
                    getTypeFormatter().formatSpecificationObjectHint(property.getKind(), property.getDefaultDisplayHint()));
        } catch (IOException ioe) {
            throw new JspTagException(ioe.getMessage(), ioe);
        }

        return (SKIP_BODY);
    }

    public PropertyDTO getProperty() {
        return property;
    }

    public void setProperty(PropertyDTO property) {
        this.property = property;
    }

    private ITypeFormatter getTypeFormatter() {
        return FormatUtil.getTypeFormatter(pageContext);
    }

}
