/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.apache.struts.taglib.html.SelectTag;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EnumOptionsTag extends AbstractOptionsTag {

    private static final long serialVersionUID = 1L;

    private Long enumTypeId;
    private boolean showTerminated;

    @SuppressWarnings("unchecked")
    @Override
    void buildOptions(SelectTag selectTag, StringBuffer sb) throws JspException {
        
        ITypeFormatter typeFormatter = getWebApplicationContext().getBean(ITypeFormatter.class);
        IProductDevelopmentService productDevelopmentService = getWebApplicationContext().getBean(
                IProductDevelopmentService.class);

        Collection<IEnumeration> enumerations;
        if (showTerminated) {
            enumerations = productDevelopmentService.findEnumerationsByTypeAndPeriod(new ApplicationContext(),
                    enumTypeId, null);
        } else {
            enumerations = productDevelopmentService.findEnumerationsByType(new ApplicationContext(), enumTypeId);
        }
        if (enumerations.isEmpty()) {
            throw new JspTagException("Enumerations for type Id [" + getEnumTypeId() + "] was not found.");
        }

        // build a list of label and values which we will then sort
        List<LabelValueBean> labelAndValues = new ArrayList<>(enumerations.size());

        for (IEnumeration enumeration : enumerations) {
        
                labelAndValues.add(
                        new LabelValueBean(
                                typeFormatter.formatEnum(enumeration),
                                enumeration.getEnumerationReference().toString()
                        )
                );
        }
        
        // now sort
        Collections.sort(labelAndValues, LabelValueBean.CASE_INSENSITIVE_ORDER);

        for (LabelValueBean bean: labelAndValues) {
            addOption(
                    sb,
                    bean.getLabel(),
                    bean.getValue(),
                    selectTag.isMatched(bean.getValue())
            );
        }

    }

    public Long getEnumTypeId() {
        return enumTypeId;
    }

    public void setEnumTypeId(Long enumTypeId) {
        this.enumTypeId = enumTypeId;
    }

    public boolean isShowTerminated() {
        return showTerminated;
    }

    public void setShowTerminated(boolean showTerminated) {
        this.showTerminated = showTerminated;
    }
}
