/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.businessservice.policymanagement.dto.*;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * @author Justin Walsh
 */
public class FormatSpecificationObjectHintTag extends TagSupport {

    private Object value;

    @Override
    public int doStartTag() throws JspException {

        if (value == null) {
            return SKIP_BODY;
        }

        KindDTO kind;
        String defaultDisplayHint;
        if (value instanceof StructuredActualDTO) {
            StructuredActualDTO s = (StructuredActualDTO)value;
            kind = s.getKind();
            defaultDisplayHint = s.getDefaultDisplayHint();
        }
        else if (value instanceof ComponentListDTO) {
            ComponentListDTO s = (ComponentListDTO) value;
            kind = s.getKind();
            defaultDisplayHint = s.getDefaultDisplayHint();

        } else if (value instanceof ComponentDTO) {
            ComponentDTO s = (ComponentDTO) value;
            kind = s.getKind();
            defaultDisplayHint = s.getDefaultDisplayHint();

        } else if (value instanceof AvailableRequestDTO) {
            AvailableRequestDTO s = (AvailableRequestDTO)value;
            kind = s.getKind();
            defaultDisplayHint = s.getDefaultDisplayHint();

        } else if (value instanceof BasicRequestDTO) {
            BasicRequestDTO s = (BasicRequestDTO)value;
            kind = s.getKind();
            defaultDisplayHint = s.getDefaultDisplayHint();

        } else if (value instanceof ConstantRoleDTO) {
            ConstantRoleDTO s = (ConstantRoleDTO)value;
            kind = s.getKind();
            defaultDisplayHint = s.getDefaultDisplayHint();

        } else if (value instanceof RoleListDTO) {
            RoleListDTO s = (RoleListDTO)value;
            kind = s.getKind();
            defaultDisplayHint = s.getDefaultDisplayHint();

        } else {
            throw new UnsupportedOperationException("Unknown specification object: " + value);
        }

        String output = getTypeFormatter().formatSpecificationObjectHint(kind, defaultDisplayHint);

        JspWriter w = pageContext.getOut();
        try {
            w.write(output);
        } catch (IOException ex) {
            throw new JspException(ex.toString(), ex);
        }

        return SKIP_BODY;


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
