/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.taglib.function;

import com.silvermoongroup.businessservice.policymanagement.dto.ComponentListDTO;

import javax.servlet.jsp.PageContext;

/**
 * @author Justin Walsh
 */
public class ComponentFunctions {

    /**
     * Generate a summary of the cardinality constraints for this component list
     * @param context The page context
     * @param componentList The component list
     * @return A string representation of the constraint
     */
    public static String getComponentListCardinality(PageContext context, ComponentListDTO componentList) {

        StringBuilder sb = new StringBuilder();

        if (componentList.getMinimumNumberOfComponents().equals(componentList.getMaximumNumberOfComponents())) {
            sb.append(componentList.getMinimumNumberOfComponents());
        }
        else {
            sb.append(componentList.getMinimumNumberOfComponents());
            sb.append(" - ");
            if (componentList.getMaximumNumberOfComponents().equals(Integer.MAX_VALUE)) {
                sb.append("*");
            }
            else {
                sb.append(componentList.getMaximumNumberOfComponents());
            }
        }
        return sb.toString();
    }

    /**
     * Can a component be added to the component list?
     * 
     * In addition to checking whether the component list is editable, this method
     * ensures that the cardinality constraints will not be added if a component is added.
     * 
     * @param context The page context
     * @param componentList The role list
     * @return true if a component can be added, otherwise false
     */
    public static boolean canComponentBeAdded(PageContext context, ComponentListDTO componentList) {

        if (componentList.isReadOnly()) {
            return false;
        }

        return componentList.getComponents().size() < componentList.getMaximumNumberOfComponents();
    }

    /**
     * Can a component be deleted from the component list?
     * 
     * In addition to checking whether the component list is editable, this method
     * ensures that the cardinality constraints will not be added if a component is deleted.
     * 
     * @param context The page context
     * @param componentList The role list
     * @return true if a component can be deleted, otherwise false
     */
    public static boolean canComponentBeDeleted(PageContext context, ComponentListDTO componentList) {

        if (componentList.isReadOnly()) {
            return false;
        }

        return componentList.getComponents().size() > componentList.getMinimumNumberOfComponents();

    }

}
