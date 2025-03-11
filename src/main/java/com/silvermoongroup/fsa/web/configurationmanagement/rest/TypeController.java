/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.configurationmanagement.rest;

import com.silvermoongroup.base.exception.NotModifiableException;
import com.silvermoongroup.businessservice.configurationmanagement.service.intf.IConfigurationService;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.exception.DuplicateObjectException;
import com.silvermoongroup.fsa.web.configurationmanagement.dto.ClassName;
import com.silvermoongroup.fsa.web.configurationmanagement.dto.TypeDTO;
import com.silvermoongroup.fsa.web.configurationmanagement.pmw.PMWTypeHierarchyModelFactory;
import com.silvermoongroup.fsa.web.configurationmanagement.pmw.jaxb.TypeHierarchyTransfer;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Justin Walsh
 */
@RestController
@RequestMapping("/configuration-management")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class TypeController {

    private IProductDevelopmentService productDevelopmentService;

    private ITypeFormatter typeFormatter;

    private PMWTypeHierarchyModelFactory pmwTypeHierarchyModelFactory;

    private IConfigurationService configurationService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public TypeController(IProductDevelopmentService productDevelopmentService, ITypeFormatter typeFormatter,
                          PMWTypeHierarchyModelFactory pmwTypeHierarchyModelFactory, IConfigurationService configurationService
    ) {
        this.productDevelopmentService = productDevelopmentService;
        this.typeFormatter = typeFormatter;
        this.pmwTypeHierarchyModelFactory = pmwTypeHierarchyModelFactory;
        this.configurationService = configurationService;
    }

    /**
     * Get the information about the root type, its children and whether these children have children in turn.
     * <p>
     * Response codes:
     * <ul>
     * <li>200 ({@link HttpServletResponse#SC_OK}): The command was processed successfully.</li>
     * <li>404 ({@link HttpServletResponse#SC_NOT_FOUND}): The type was not found.</li>
     * </ul>
     *
     */
    @RequestMapping(
            value = "/types",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}

    )
    public ResponseEntity getRootType() {
        Type node = productDevelopmentService.getRootType(applicationContext);
        if (node == null) {
            return ResponseEntity.notFound().build();
        }
        TypeDTO typeDTO = mapToDTO(node, true);
        return ResponseEntity.ok(typeDTO);
    }

    /**
     * Get the information about a type, its children and whether these children have children in turn.
     * <p>
     * Response codes:
     * <ul>
     * <li>200 ({@link HttpServletResponse#SC_OK}): The command was processed successfully.</li>
     * <li>404 ({@link HttpServletResponse#SC_NOT_FOUND}): The type was not found.</li>
     * </ul>
     *
     *
     * @param typeId  The id of the type type to retrieve
     */

    @RequestMapping(
            value = "/types/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}

    )
    @ResponseBody
    public ResponseEntity getType(@PathVariable("id") Long typeId) {

        Type node = productDevelopmentService.getType(applicationContext, typeId);
        if (node == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapToDTO(node, true));
    }

    @RequestMapping(
            value = "/component/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity getComponentClassNames(@PathVariable("id") Long componentId)  {
        String[] classNames = configurationService.getClassNamesForComponent(applicationContext, componentId.intValue());

        if (classNames == null){
            return ResponseEntity.notFound().build();
        }

        ComponentResponse componentClasses = new ComponentResponse();
        for(String className : classNames){
            componentClasses.getClassNames().add(new ClassName(className));
        }

        return ResponseEntity.ok(componentClasses);
    }

    @RequestMapping(
            value = "/types/{parentTypeId}",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity addChildType(@PathVariable("parentTypeId") Long parentTypeId, @RequestBody TypeDTO dto, UriComponentsBuilder builder) {
        Type type = mapFromDTO(dto);
        type.setEffectivePeriod(new DatePeriod(Date.today(), Date.FUTURE));  // we don't expose this on the UI yet.
        try {
            type = productDevelopmentService.addChildType(new ApplicationContext(), parentTypeId, type);
        } catch (DuplicateObjectException ex) {
            TypeResourceResponse entity = createResourceResponse("page.browsetypes.message.duplicate", null,
                    type.getName());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(entity);
        }
        UriComponents uriComponents = builder.path("/types/{id}").buildAndExpand(type.getId());
        URI entityUri = uriComponents.toUri();
        TypeResourceResponse entity = createResourceResponse("page.browsetypes.message.success", mapToDTO(type,
                true), type.getName(), type.getId().toString());
        return ResponseEntity.created(entityUri).body(entity);
    }

    /**
     * Update the type with the given identifier.
     * <p>
     * Response codes:
     * <ul>
     * <li>200 ({@link HttpServletResponse#SC_OK}): The type was updated successfully.</li>
     * <li>400 ({@link HttpServletResponse#SC_BAD_REQUEST}): The request received was syntactically incorrect.</li>
     * <li>403 ({@link HttpServletResponse#SC_FORBIDDEN}): The type is not modifiable</li>
     * <li>409 ({@link HttpServletResponse#SC_CONFLICT}): A type with the name already exists.</li>
     * </ul>
     *
     *
     * @param typeId   The id of the type to update
     * @param dto The value of the type containing the updated fields
     */
    @RequestMapping(
            value = "/types/{typeId}",
            method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity updateType(@PathVariable("typeId") Long typeId, @RequestBody TypeDTO dto, UriComponentsBuilder builder) {

        if (typeId == null || !typeId.equals(dto.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Type typeToUpdate = mapFromDTO(dto);
        try {
            productDevelopmentService.updateType(applicationContext, typeToUpdate);
        } catch (NotModifiableException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (DuplicateObjectException ex) {
            TypeResourceResponse entity = createResourceResponse("page.browsetypes.message.duplicate", null,
                    typeToUpdate.getName());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(entity);
        }

        UriComponents uriComponents = builder.path("/types/{id}").buildAndExpand(typeToUpdate.getId());
        URI entityUri = uriComponents.toUri();

        TypeResourceResponse entity = createResourceResponse(
                "page.browsetypes.message.success",
                mapToDTO(typeToUpdate, true), typeToUpdate.getName(), typeToUpdate.getId()
                                                                                  .toString()
        );
        return ResponseEntity.created(entityUri).body(entity);
    }

    /**
     * Delete the type with the given identifier.
     * <p>
     * Response codes:
     * <ul>
     * <li>200 ({@link HttpServletResponse#SC_OK}): The type was deleted.</li>
     * <li>403 ({@link HttpServletResponse#SC_FORBIDDEN}): The type is not modifiable</li>
     * </ul>
     *
     *
     * @param typeId   The identifier of the type
     */
    @RequestMapping(
            value = "/types/{typeId}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity deleteType(@PathVariable("typeId") Long typeId) {
        try {
            productDevelopmentService.deleteType(applicationContext, typeId);
        } catch (NotModifiableException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        TypeResourceResponse entity = createResourceResponse(
                "page.browsetypes.message.deleted", null,
                typeId.toString()
        );
        return ResponseEntity.ok(entity);
    }

    /**
     * Export the type hierarchy in the specified {@code format}, starting at the {@code rootTypeId}.
     * <p>
     * Response codes:
     * <ul>
     * <li>200 ({@link HttpServletResponse#SC_OK}): The export completed successfully.</li>
     * <li>400 ({@link HttpServletResponse#SC_BAD_REQUEST}): If the {@code format} or {@code rootTypeId} is not
     * recognised</li>
     * </ul>
     *
     * @param rootTypeId The identifier of the root type at which to start the format.  If not specified, the root of
     *                   the tree is specified (i.e. the whole tree is exported).
     * @return An object which can be marshalled into either a JSON or XML representation.
     */
    @RequestMapping(
            value = "/types/export",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity exportToPMWFormat(@RequestParam("rootTypeId") Long rootTypeId) {

        Type rootTypeForExport;
        if (rootTypeId == null) {
            rootTypeForExport = productDevelopmentService.getRootType(applicationContext);
        } else {
            rootTypeForExport = productDevelopmentService.getType(applicationContext, rootTypeId);
        }

        if (rootTypeForExport == null) {
            return ResponseEntity.badRequest().body("{error : rootTypeId not found}");
        }

        TypeHierarchyTransfer model = pmwTypeHierarchyModelFactory.createModel(applicationContext, rootTypeForExport.getId());

        return ResponseEntity.ok(model);
    }

    private Type mapFromDTO(TypeDTO typeToMapFrom) {
        Type childDTOType = new Type();
        childDTOType.setId(typeToMapFrom.getId());
        childDTOType.setDescription(StringUtils.trimToNull(typeToMapFrom.getDescription()));
        childDTOType.setName(typeToMapFrom.getName());
        childDTOType.setQualifiedClassName(typeToMapFrom.getQualifiedClassName());
        childDTOType.setComponentId(typeToMapFrom.getComponentId());
        childDTOType.setAbstract(typeToMapFrom.getAbstractType());
        childDTOType.setSchemaName(typeToMapFrom.getSchemaName());
        childDTOType.setTableName(typeToMapFrom.getTableName());
        childDTOType.setBaseTableName(typeToMapFrom.getBaseTableName());
        
        return childDTOType;
    }


    private TypeDTO mapToDTO(Type node, boolean includeChildren) {
        TypeDTO typeDTO = new TypeDTO();
        typeDTO.setId(node.getId());
        typeDTO.setName(formatTypeName(node));
        typeDTO.setComponentId(node.getComponentId());
        typeDTO.setComponentName(formatComponentIdToName(node.getComponentId()));
        typeDTO.setDescription(node.getDescription());
        typeDTO.setQualifiedClassName(node.getQualifiedClassName());
        typeDTO.setParentId(node.getParentId() == null ? -1L : node.getParentId());
        typeDTO.setReadOnly(node.isReadOnly());
        typeDTO.setAbstractType(node.isAbstract());
        typeDTO.setSchemaName(node.getSchemaName());
        typeDTO.setTableName(node.getTableName());
        typeDTO.setBaseTableName(node.getBaseTableName());

        List<Type> children = new ArrayList<>(productDevelopmentService.getImmediateSubTypes(applicationContext, node.getId()));

        // always indicate whether the type has children or not
        typeDTO.setHasChildren(!children.isEmpty());

        if (includeChildren) {
            sortTypesByName(children);
            for (Type child: children) {
                typeDTO.addChild(mapToDTO(child, false));
            }
        }

        return typeDTO;
    }

    private void sortTypesByName(List<Type> children) {
        Collections.sort(children, new Comparator<Type>() {
            @Override
            public int compare(Type o1, Type o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });
    }

    private String formatTypeName(Type node) {
        return typeFormatter.formatMessageWithFallback("common.type." + node.getName(), node.getName());
    }

    private String formatComponentIdToName(Long componentId) {
        if (componentId == null) {
            typeFormatter.formatMessage("component.unknown");
        }
        return typeFormatter.formatMessage("component." + componentId);
    }

    /**
     *
     * Helper method that sets up a {@link TypeResourceResponse} with entities required
     *
     * @param messageKey The message key
     * @param type The dto Type
     * @return The contstructed entity
     */
    private TypeResourceResponse createResourceResponse(String messageKey, TypeDTO type, String... messageArgs){

        String message = typeFormatter.formatMessage(messageKey, messageArgs);
        return new TypeResourceResponse(message, type);
    }

}
