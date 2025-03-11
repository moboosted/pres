/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.intf.ICategory;
import com.silvermoongroup.common.domain.intf.ICategoryScheme;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.apache.struts.taglib.html.SelectTag;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CategoryOptionsTag extends AbstractOptionsTag {

    private static final long serialVersionUID = 1L;

    private String categorySchemeName;

    @SuppressWarnings("unchecked")
    @Override
    void buildOptions(SelectTag selectTag, StringBuffer sb) throws JspException {
        
        ITypeFormatter typeFormatter = getWebApplicationContext().getBean(ITypeFormatter.class);
        IProductDevelopmentService productDevelopmentService = getWebApplicationContext().getBean(
                IProductDevelopmentService.class);


        ApplicationContext context = new ApplicationContext();
        ICategoryScheme catScheme = productDevelopmentService.getCategoryScheme(context, categorySchemeName);

        Collection<ICategory> categories =
                productDevelopmentService.getCategoriesForScheme(context,
                                                                  catScheme.getObjectReference(),
                                                                  Date.today());

        if (categories.isEmpty()) {
            throw new JspTagException("Category scheme [" + getCategorySchemeName() + "] was not found.");
        }

        // build a list of label and values which we will then sort
        List<LabelValueBean> labelAndValues = new ArrayList<>(categories.size());

        for (ICategory category : categories) {
        
                labelAndValues.add(
                        new LabelValueBean(
                                category.getName(),
                                category.getId().toString()
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

    public String getCategorySchemeName() {
        return categorySchemeName;
    }

    public void setCategorySchemeName(String categorySchemeName) {
        this.categorySchemeName = categorySchemeName;
    }
}
