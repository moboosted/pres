/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib;

import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;


public class SelectTag extends org.apache.struts.taglib.html.SelectTag {

    private boolean readonly;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Create an appropriate select start element based on our parameters.
     * @throws JspException
     * @since Struts 1.1
     */
    @Override
    protected String renderSelectStartElement() throws JspException {
        if (isReadonly()) {
            setDisabled(true);
            doDisabled = true;
        } else {
            setDisabled(false);
            doDisabled = false;
        }

        return super.renderSelectStartElement();
    }

    /**
     * Render the end of this form.
     *
     * @exception JspException if a JSP exception has occurred
     */
    @Override
    public int doEndTag() throws JspException {

        super.doEndTag();

        if (isReadonly()) {

            StringBuffer results = new StringBuffer();
            for (String selectValue : this.match) {
                results.append("<input type=\"hidden\"");
                prepareAttribute(results, "name", prepareName());
                prepareAttribute(results, "value", selectValue);
                results.append(getElementClose());
            }

            TagUtils.getInstance().write(pageContext, results.toString());
        }

        return (Tag.EVAL_PAGE);
    }

    public boolean isReadonly() {
        return readonly;
    }

    @Override
    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }
}