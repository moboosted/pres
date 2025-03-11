/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import org.apache.struts.taglib.html.SelectTag;

import javax.servlet.jsp.JspException;
import java.util.ArrayList;
import java.util.List;

/**
 * Renders the contact points types that are supported in the party GUI on the contact points page
 *
 * @author Justin Walsh
 */
public class ContactPointTypesTag extends AbstractOptionsTag {

    @Override
    void buildOptions(SelectTag selectTag, StringBuffer sb) throws JspException {

        IProductDevelopmentService typeService = getWebApplicationContext().getBean(IProductDevelopmentService.class);
        ApplicationContext applicationContext = new ApplicationContext();

        List<Type> types = new ArrayList<>();

        types.add(typeService.getType(applicationContext, CoreTypeReference.POSTALADDRESS.getValue()));
        types.add(typeService.getType(applicationContext, CoreTypeReference.PHYSICALADDRESS.getValue()));
        types.add(typeService.getType(applicationContext, CoreTypeReference.TELEPHONENUMBER.getValue()));
        types.add(typeService.getType(applicationContext, CoreTypeReference.ELECTRONICADDRESS.getValue()));
        types.add(typeService.getType(applicationContext, CoreTypeReference.UNSTRUCTUREDADDRESS.getValue()));

        types.addAll(typeService.getAllSubTypes(applicationContext, CoreTypeReference.POSTALADDRESS.getValue()));
        types.addAll(typeService.getAllSubTypes(applicationContext, CoreTypeReference.PHYSICALADDRESS.getValue()));
        types.addAll(typeService.getAllSubTypes(applicationContext, CoreTypeReference.TELEPHONENUMBER.getValue()));
        types.addAll(typeService.getAllSubTypes(applicationContext, CoreTypeReference.ELECTRONICADDRESS.getValue()));
        types.addAll(typeService.getAllSubTypes(applicationContext, CoreTypeReference.UNSTRUCTUREDADDRESS.getValue()));

        for (Type type : types) {
            addOption(
                    sb,
                    getTypeFormatter().formatType(type),
                    String.valueOf(type.getId()),
                    selectTag.isMatched(String.valueOf(type.getId()))
            );
        }

    }
}
