/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.configurationmanagement.pmw;

import com.silvermoongroup.base.exception.ApplicationRuntimeException;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.fsa.web.configurationmanagement.pmw.jaxb.TransferType;
import com.silvermoongroup.fsa.web.configurationmanagement.pmw.jaxb.TypeHierarchyTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

/**
 * Factory class for a model representing the view of the type hierarchy required by PMW.
 *
 * @author Justin Walsh
 */
@Component
public class PMWTypeHierarchyModelFactory {

    @Autowired
    private IProductDevelopmentService productDevelopmentService;

    public TypeHierarchyTransfer createModel(ApplicationContext applicationContext, Long typeId) {

        TypeHierarchyTransfer hierarchy = new TypeHierarchyTransfer();
        hierarchy.setDate(new java.util.Date());
        hierarchy.setSystemIdentifier(UUID.randomUUID().toString());
        hierarchy.setTransferType(TransferType.EXPORT);
        hierarchy.setVersionNumber("1.0");

        Type rootTypeForExport = productDevelopmentService.getType(applicationContext, typeId);


        com.silvermoongroup.fsa.web.configurationmanagement.pmw.jaxb.Type type = populatePMWType(applicationContext, rootTypeForExport);
        hierarchy.setTypeHierarchy(type);

        return hierarchy;
    }

    private com.silvermoongroup.fsa.web.configurationmanagement.pmw.jaxb.Type populatePMWType(ApplicationContext applicationContext,
            Type silvermoonType) {

        com.silvermoongroup.fsa.web.configurationmanagement.pmw.jaxb.Type type =
                new com.silvermoongroup.fsa.web.configurationmanagement.pmw.jaxb.Type();

        type.setIsAbstract(false);
        GregorianCalendar calendar = new GregorianCalendar();

        calendar.setTime(silvermoonType.getEffectivePeriod().getStart().toJavaUtilDate());
        XMLGregorianCalendar xmlGregorianCalendar;
        try {
            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException e) {
            throw new ApplicationRuntimeException(e);
        }
        type.setEffectiveFromDate(xmlGregorianCalendar);

        calendar.setTime(silvermoonType.getEffectivePeriod().getEnd().toJavaUtilDate());
        try {
            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException e) {
            throw new ApplicationRuntimeException(e);
        }
        type.setEffectiveToDate(xmlGregorianCalendar);

        type.setName(silvermoonType.getName());
        type.setLifeCycleStatus("?");
        List<String> targetAlias = new ArrayList<>();
        targetAlias.add(String.valueOf(silvermoonType.getId()));
        type.setTargetAlias(targetAlias);

        List<com.silvermoongroup.fsa.web.configurationmanagement.pmw.jaxb.Type> childTypes = new ArrayList<>();
        Set<Type> immediateSubTypes = productDevelopmentService.getImmediateSubTypes(applicationContext, silvermoonType.getId());
        for (Type silvermoonChildType : immediateSubTypes) {
            com.silvermoongroup.fsa.web.configurationmanagement.pmw.jaxb.Type childType = populatePMWType(applicationContext, silvermoonChildType);
            childTypes.add(childType);
        }

        type.setChildTypes(childTypes);

        return type;
    }
}
