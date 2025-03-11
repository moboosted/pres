/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.configurationmanagement.dto;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Justin Walsh
 */
@XmlRootElement(name = "type")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(
        name = "TypeType",
        propOrder = {
                "id",
                "name",
                "componentId",
                "componentName",
                "description",
                "qualifiedClassName",
                "parentId",
                "readOnly",
                "hasChildren",
                "children",
                "abstractType",
                "schemaName",
                "tableName",
                "baseTableName"
        }
)
public class TypeDTO {

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "componentId")
    private Long componentId;

    @XmlElement(name = "componentName")
    private String componentName;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "qualifiedClassName")
    private String qualifiedClassName;

    @XmlElement(name = "parentId")
    private Long parentId;

    @XmlElement(name = "readOnly")
    private Boolean readOnly;

    @XmlElement(name = "hasChildren")
    private Boolean hasChildren;

    @XmlElement(name = "children")
    private List<TypeDTO> children;

    @XmlElement(name = "abstractType")
    private boolean abstractType;

    @XmlElement(name = "schemaName")
    private String schemaName;

    @XmlElement(name = "tableName")
    private String tableName;

    @XmlElement(name = "baseTableName")
    private String baseTableName;

    public TypeDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getComponentId() {
        return componentId;
    }

    public void setComponentId(Long componentId) {
        this.componentId = componentId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQualifiedClassName() {
        return qualifiedClassName;
    }

    public void setQualifiedClassName(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public List<TypeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<TypeDTO> children) {
        this.children = children;
    }

    public void addChild(TypeDTO child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public boolean getAbstractType() {
        return abstractType;
    }

    public void setAbstractType(boolean abstractType) {
        this.abstractType = abstractType;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBaseTableName() {
        return baseTableName;
    }

    public void setBaseTableName(String baseTableName) {
        this.baseTableName = baseTableName;
    }
}
