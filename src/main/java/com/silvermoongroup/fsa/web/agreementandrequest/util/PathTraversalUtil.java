/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.util;

import com.silvermoongroup.businessservice.policymanagement.dto.AgreementDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.ComponentDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.ComponentListDTO;
import com.silvermoongroup.businessservice.policymanagement.enumeration.ElementType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;

/**
 * @author justinw
 */
public class PathTraversalUtil {

    private PathTraversalUtil() {
    }

    /**
     * This utility is intended to reduce the path by one traversal \"node\" Eg:
     * Agreement("Life Insurance)[0].Role("PolicyHolder")[1] becomes Agreement("Life Insurance")[0]
     * 
     * @param path
     *            - The current path
     * @param elementType
     *            - This can be either Agreement or Role, In the example the enumeration will refer to Role.
     * 
     * @return The trimmed string
     */
    public static String trimTraversalPath(String path, ElementType elementType) {
        Assert.notNull(elementType, "A elementType is required to trim the String");

        if (path == null) {
            return StringUtils.EMPTY;
        }

        return new StringBuilder(path).substring(0, path.lastIndexOf(elementType.getName() + "(")).toString();
    }

    public static StringBuilder generateTraversalPath(StringBuilder stringBuilder,
            Collection<ComponentListDTO> componentListCollection, AgreementDTO agreement) {
        int index = 0;

        if (componentListCollection != null && !componentListCollection.isEmpty()) {
            COMPLETED: for (ComponentListDTO componentList : componentListCollection) {
                index = 0;
                List<ComponentDTO> components = componentList.getComponents();
                if (components != null && !components.isEmpty()) {
                    for (ComponentDTO component : components) {
                        if (agreement.getUuid().equals(component.getUuid())) {
                            break COMPLETED;
                        }
                        index++;
                    }
                }
            }
        }

        String childComponentsString = StringUtils.trimToNull(stringBuilder.toString());
        if (childComponentsString != null && StringUtils.isNotEmpty(childComponentsString)) {
            stringBuilder = new StringBuilder();
        }

        stringBuilder.append("Agreement(\"").append(agreement.getKind().getName()).append("\")[").append(index)
                .append("].");

        if (childComponentsString != null && StringUtils.isNotEmpty(childComponentsString)) {
            stringBuilder.append(childComponentsString);
        }

        return stringBuilder;
    }

}