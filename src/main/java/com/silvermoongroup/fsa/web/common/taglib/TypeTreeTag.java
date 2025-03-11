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
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.SelectTag;

import javax.servlet.jsp.JspException;
import java.util.Collections;
import java.util.Comparator;

/**
 * Render a type tree in a hierarchical manner (i.e. options are indented by spaces to reflect the hierarchy)
 *
 * @author Justin Walsh
 */
public class TypeTreeTag extends AbstractOptionsTag {

    private static final long serialVersionUID = 1L;

    private Long rootTypeId;
    private boolean indent = true;
    private boolean includeRootType = true;

    private static final String INDENT = "&nbsp;&nbsp;";

    public TypeTreeTag() {
    }

    @Override
    void buildOptions(SelectTag selectTag, StringBuffer sb) throws JspException {

        IProductDevelopmentService typeService = getWebApplicationContext().getBean(IProductDevelopmentService.class);

        TypeTree typeTree = typeService.getTypeTreeRootedAt(new ApplicationContext(), getRootTypeId());

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
        if (isIncludeRootType()) {
            addOption(selectTag, sb, rootNode);
        }
        addChildOptions(selectTag, sb, rootNode);
    }

    private void addChildOptions(SelectTag selectTag, StringBuffer sb, TypeTreeNode parent) {
        for (TypeTreeNode node : parent.getChildren()) {
            addOption(selectTag, sb, node);
            addChildOptions(selectTag, sb, node);
        }
    }

    private void addOption(SelectTag selectTag, StringBuffer sb, TypeTreeNode node) {
        addOption(
                sb,
                createIndentForDepth(node) + TagUtils.getInstance().filter(node.getName()),
                String.valueOf(node.getId()),
                selectTag.isMatched(String.valueOf(node.getId()))
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


    public Long getRootTypeId() {
        return rootTypeId;
    }

    public void setRootTypeId(Long rootTypeId) {
        this.rootTypeId = rootTypeId;
    }

    public boolean isIncludeRootType() {
        return includeRootType;
    }

    public void setIncludeRootType(boolean includeRootType) {
        this.includeRootType = includeRootType;
    }

    public boolean isIndent() {
        return indent;
    }

    public void setIndent(boolean indent) {
        this.indent = indent;
    }
}
