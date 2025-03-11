/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.businessservice.productmanagement.dto.TypeTree;
import com.silvermoongroup.businessservice.productmanagement.dto.TypeTreeNode;
import com.silvermoongroup.businessservice.productmanagement.dto.TypeTreeNodeVisitor;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.common.enumeration.intf.ITypeReference;
import com.silvermoongroup.common.ext.enumeration.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.SelectTag;

import javax.servlet.jsp.JspException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Render an options selection for the party registrations point types that are supported by the UI
 */
public class RegistrationTypesTag extends AbstractOptionsTag {

    private static final long serialVersionUID = 1L;

    private static final String INDENT = "&nbsp;&nbsp;";

    private IProductDevelopmentService productDevelopmentService;

    @Override
    void buildOptions(SelectTag selectTag, StringBuffer sb) throws JspException {

        productDevelopmentService = getWebApplicationContext().getBean(IProductDevelopmentService.class);
        ApplicationContext applicationContext = new ApplicationContext();

        String partyType = (String) TagUtils.getInstance().lookup(pageContext, name, property, null);
        ITypeReference partyTypeReference = TypeReference.fromName(partyType);

        TypeTree typeTree = productDevelopmentService.getTypeTreeRootedAt(new ApplicationContext(), CoreTypeReference.PARTYREGISTRATION.getValue());

        // don't include certain registration types based on the party type
        Set<Long> typeIdsToExclude = new HashSet<>();
        if (productDevelopmentService.isSubType(applicationContext, CoreTypeReference.ORGANISATION.getValue(),
                partyTypeReference.getValue())) {

            typeIdsToExclude.addAll(productDevelopmentService.getTypeAndSubTypeIds(applicationContext, CoreTypeReference.BIRTHCERTIFICATE.getValue()));
            typeIdsToExclude.addAll(productDevelopmentService.getTypeAndSubTypeIds(applicationContext, CoreTypeReference.DEATHCERTIFICATE.getValue()));
            typeIdsToExclude.addAll(productDevelopmentService.getTypeAndSubTypeIds(applicationContext, CoreTypeReference.DRIVINGLICENSE.getValue()));
            typeIdsToExclude.addAll(productDevelopmentService.getTypeAndSubTypeIds(applicationContext, CoreTypeReference.EDUCATIONCERTIFICATEREGISTRATION.getValue()));
            typeIdsToExclude.addAll(productDevelopmentService.getTypeAndSubTypeIds(applicationContext, CoreTypeReference.MARRIAGEREGISTRATION.getValue()));
            typeIdsToExclude.addAll(productDevelopmentService.getTypeAndSubTypeIds(applicationContext, CoreTypeReference.NATIONALREGISTRATION.getValue()));

        } else {
            typeIdsToExclude.addAll(productDevelopmentService.getTypeAndSubTypeIds(applicationContext, CoreTypeReference.COMPANYREGISTRATION.getValue()));
        }

        // internationalise the names
        typeTree.accept(new TypeTreeNodeVisitor() {
            @Override
            public void visitNode(TypeTreeNode node) {
                node.setName(getTypeFormatter().formatType(node.getId()));
            }
        });

        // sort the names
        typeTree.accept(new TypeTreeNodeVisitor() {

            Comparator<TypeTreeNode> nameComparator = new Comparator<TypeTreeNode>() {
                @Override
                public int compare(TypeTreeNode o1, TypeTreeNode o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            };

            @Override
            public void visitNode(TypeTreeNode node) {
                Collections.sort(node.getChildren(), nameComparator);
            }
        });

        TypeTreeNode rootNode = typeTree.getRootNode();
        setFilter(false);
        addOption(applicationContext, typeIdsToExclude, selectTag, sb, rootNode);
        addChildOptions(applicationContext, typeIdsToExclude, selectTag, sb, rootNode);
    }

    private void addChildOptions(ApplicationContext applicationContext, Set<Long> typeIdsToExclude, SelectTag selectTag, StringBuffer sb, TypeTreeNode parent) {
        for (TypeTreeNode node : parent.getChildren()) {
            addOption(applicationContext, typeIdsToExclude, selectTag, sb, node);
            addChildOptions(applicationContext, typeIdsToExclude, selectTag, sb, node);
        }
    }

    private void addOption(ApplicationContext applicationContext, Set<Long> typeIdsToExclude, SelectTag selectTag, StringBuffer sb, TypeTreeNode node) {

        if (typeIdsToExclude.contains(node.getId())) {
            return;
        }

        //Set the div we should go to
        String divId = getDivId(applicationContext, node.getId());
        if(StringUtils.isBlank(divId)) {
            divId = "partyReg";
        }

        addOption(
                sb,
                createIndentForDepth(node) + TagUtils.getInstance().filter(node.getName()),
                String.valueOf(node.getId()),
                selectTag.isMatched(String.valueOf(node.getId())),
                divId
        );
    }

    private String createIndentForDepth(TypeTreeNode node) {
        if (isIndent()) {
            StringBuilder indentation = new StringBuilder();
            for (int i = 0; i < node.getDepth(); i++) {
                indentation.append(INDENT);
            }
            return indentation.toString();
        }
        return StringUtils.EMPTY;
    }

    private boolean isIndent() {
        return true;
    }

    private String getDivId(ApplicationContext applicationContext, Long typeId) {
        if (productDevelopmentService.isSameOrSubType(applicationContext,
                CoreTypeReference.BIRTHCERTIFICATE.getValue(), typeId)) {
            return "birthReg";
        } else if (productDevelopmentService.isSameOrSubType(applicationContext,
                CoreTypeReference.DEATHCERTIFICATE.getValue(), typeId)) {
            return "deathReg";
        } else if (productDevelopmentService.isSameOrSubType(applicationContext,
                CoreTypeReference.DRIVINGLICENSE.getValue(), typeId)) {
            return "driversLicenceReg";
        } else if (productDevelopmentService.isSameOrSubType(applicationContext,
                CoreTypeReference.EDUCATIONCERTIFICATEREGISTRATION.getValue(), typeId)) {
            return "educationReg";
        } else if (productDevelopmentService.isSameOrSubType(applicationContext,
                CoreTypeReference.MARRIAGEREGISTRATION.getValue(), typeId)) {
            return "marriageReg";
        } else if (productDevelopmentService.isSameOrSubType(applicationContext,
                CoreTypeReference.NATIONALREGISTRATION.getValue(), typeId)) {
            return "nationalRegPerson";
        } else if (productDevelopmentService.isSameOrSubType(applicationContext,
                CoreTypeReference.ACCREDITATION.getValue(), typeId)) {
            return "accredReg";
        }
        return null;
    }

    protected void addOption(StringBuffer sb, String label, String value,
            boolean matched, String divId) {
        sb.append("<option value=\"");

        if (filter) {
            sb.append(TagUtils.getInstance().filter(value));
        } else {
            sb.append(value);
        }

        sb.append("\"");

        if (divId != null) {
            sb.append(" data-divid=\"");
            sb.append(divId);
            sb.append("\"");
        }

        if (matched) {
            sb.append(" selected=\"selected\"");
        }

        if (getStyle() != null) {
            sb.append(" style=\"");
            sb.append(getStyle());
            sb.append("\"");
        }

        if (getStyleClass() != null) {
            sb.append(" class=\"");
            sb.append(getStyleClass());
            sb.append("\"");
        }

        sb.append(">");

        if (filter) {
            sb.append(TagUtils.getInstance().filter(label));
        } else {
            sb.append(label);
        }

        sb.append("</option>\r\n");
    }

}