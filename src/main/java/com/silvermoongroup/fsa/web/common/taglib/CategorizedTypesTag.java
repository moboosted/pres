/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.domain.ApplicationContext;
import org.apache.struts.taglib.html.SelectTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Justin Walsh
 */
public class CategorizedTypesTag extends AbstractOptionsTag {

    private static Logger log = LoggerFactory.getLogger(CategorizedTypesTag.class);

    private String categoryName;
    private String excludedTypes;

    private boolean valuesAsNames;

    public CategorizedTypesTag() {
    }

    @Override
    void buildOptions(SelectTag selectTag, StringBuffer sb) throws JspException {

        IProductDevelopmentService typeService = getWebApplicationContext().getBean(IProductDevelopmentService.class);
        ApplicationContext applicationContext = new ApplicationContext();

        Collection<Type> categorizedTypes = typeService.findCategorisedTypes(applicationContext, getCategoryName(), Date.today());

        valuesAsNames = false;
        if (!(null == getExcludedTypes())) {
            for (Type type : categorizedTypes) {
                for (String typeToken : getExcludedTypesArray()) {
                    // exclude types from excludedTypes array list
                    if (!typeToken.equals(type.getName())) {
                        addOption(selectTag, sb, type);
                    }
                }
            }
        }
        else {
            for (Type type : categorizedTypes) {
                addOption(selectTag, sb, type);
            }
        }
    }

    private void addOption(SelectTag selectTag, StringBuffer sb, Type type) {

        addOption(
                sb,
                getTypeFormatter().formatType((Type) type),
                String.valueOf(type.getId()),
                selectTag.isMatched(type.getId() + "")
        );

    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getExcludedTypes() {
        return excludedTypes;
    }

    public void setExcludedTypes(String excludedTypes) {
        this.excludedTypes = excludedTypes;
    }

    private List<String> getExcludedTypesArray() {
        StringTokenizer st = new StringTokenizer(getExcludedTypes(), ",");
        ArrayList<String> excludedTypesArray = new ArrayList<>(st.countTokens());

        while (st.hasMoreElements()) {
            excludedTypesArray.add(st.nextToken());
        }

        return excludedTypesArray;
    }
}
