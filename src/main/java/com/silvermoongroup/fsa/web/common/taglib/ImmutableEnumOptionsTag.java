/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.common.enumeration.intf.IEnum;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.apache.struts.taglib.html.SelectTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

public class ImmutableEnumOptionsTag extends AbstractOptionsTag {

    private static final long serialVersionUID = 1L;

    private String immutableEnumerationClass;

    @SuppressWarnings("unchecked")
    @Override
    void buildOptions(SelectTag selectTag, StringBuffer sb) throws JspException {

        ITypeFormatter typeFormatter = getWebApplicationContext().getBean(ITypeFormatter.class);

        if (immutableEnumerationClass != null) {
            Class<IEnum> enumerationType;
            try {
                enumerationType = (Class<IEnum>) Class.forName(immutableEnumerationClass);
            } catch (Exception e) {
                throw new JspTagException("Immutable Enum for class name  [" + immutableEnumerationClass
                        + "] was not found.");
            }
            IEnum[] enumerationValues = enumerationType.getEnumConstants();
            for (IEnum enumeration : enumerationValues) {
                addOption(sb, typeFormatter.formatEnum(enumeration), String.valueOf(enumeration.getCode()),
                        selectTag.isMatched(String.valueOf(enumeration.getCode())));
            }
        }
    }

    public String getImmutableEnumerationClass() {
        return immutableEnumerationClass;
    }

    public void setImmutableEnumerationClass(String immutableEnumerationClass) {
        this.immutableEnumerationClass = immutableEnumerationClass;
    }
}
