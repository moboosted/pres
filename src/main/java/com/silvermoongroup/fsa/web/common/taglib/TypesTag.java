/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import org.apache.struts.taglib.html.SelectTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author Justin Walsh
 */
public class TypesTag extends AbstractOptionsTag {

    private static Logger log = LoggerFactory.getLogger(TypesTag.class);

    private String subTypesOf;
    private boolean immediateSubTypesOnly;
    private String excludedTypes;

    private boolean valuesAsNames;

    private boolean addParentType;

    public TypesTag() {
    }

    @Override
    void buildOptions(SelectTag selectTag, StringBuffer sb) throws JspException {

        IProductDevelopmentService typeService = getWebApplicationContext().getBean(IProductDevelopmentService.class);
        ApplicationContext applicationContext = new ApplicationContext();

        Set<Type> types;

        for (String typeOf : getTypesArray(getSubTypesOf())) {
            Type parentType = typeService.getType(applicationContext, typeOf);
            if (parentType == null) {
                log.warn("Type with name: " + typeOf + " was not found.");
                return;
            }

            if (isImmediateSubTypesOnly()) {
                types = typeService.getImmediateSubTypes(applicationContext, parentType.getId());
            } else {
                types = typeService.getAllSubTypes(applicationContext, parentType.getId());
            }

            if (isAddParentType()) {
                types.add(parentType);
            }

            if (null != getExcludedTypes()) {
                for (Type type : types) {
                    for (String typeToken : getTypesArray(getExcludedTypes())) {
                        // exclude types from excludedTypes array list
                        if (!typeToken.equals(type.getName())) {
                            addOption(selectTag, sb, type);
                            addParentTypeToOption(sb, parentType);
                        }
                    }
                }
            }
            else {
                for (Type type : types) {
                    addOption(selectTag, sb, type);
                    addParentTypeToOption(sb, parentType);
                }
            }
        }
    }

    private void addParentTypeToOption(StringBuffer sb, Type parentType) {
        int index = sb.indexOf("<option value");
        if (index != -1) {
            sb.replace(index + 8, index + 8, "title=\"" + parentType.getName() + "\" ");
        }
    }

    private void addOption(SelectTag selectTag, StringBuffer sb, Type type) {
        if (valuesAsNames) {
            addOption(
                    sb,
                    getTypeFormatter().formatType(type),
                    type.getName(),
                    selectTag.isMatched(type.getName())
            );
        } else {
            addOption(
                    sb,
                    getTypeFormatter().formatType(type),
                    String.valueOf(type.getId()),
                    selectTag.isMatched(type.getName())
            );
        }
    }

    public boolean isValuesAsNames() {
        return valuesAsNames;
    }

    public void setValuesAsNames(boolean valuesAsNames) {
        this.valuesAsNames = valuesAsNames;
    }

    public String getSubTypesOf() {
        return subTypesOf;
    }

    public void setSubTypesOf(String subTypesOf) {
        this.subTypesOf = subTypesOf;
    }

    public boolean isImmediateSubTypesOnly() {
        return immediateSubTypesOnly;
    }

    public void setImmediateSubTypesOnly(boolean immediateSubTypesOnly) {
        this.immediateSubTypesOnly = immediateSubTypesOnly;
    }

    public String getExcludedTypes() {
        return excludedTypes;
    }

    public void setExcludedTypes(String excludedTypes) {
        this.excludedTypes = excludedTypes;
    }

    public boolean isAddParentType() {
        return addParentType;
    }

    public void setAddParentType(boolean addParentType) {
        this.addParentType = addParentType;
    }

    private List<String> getTypesArray(String types) {
        StringTokenizer st = new StringTokenizer(types, ",");
        ArrayList<String> typesArray = new ArrayList<>(st.countTokens());

        while (st.hasMoreElements()) {
            typesArray.add(st.nextToken());
        }

        return typesArray;
    }
}
