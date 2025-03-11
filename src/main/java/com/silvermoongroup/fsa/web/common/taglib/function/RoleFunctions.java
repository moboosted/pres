/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.taglib.function;

import com.silvermoongroup.businessservice.policymanagement.dto.RoleDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.RoleListDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.TypeDTO;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.PageContext;
import java.util.HashSet;
import java.util.Set;

/**
 * Functions dealing with role lists and roles
 * 
 * @author Justin Walsh
 */
public class RoleFunctions {

    private static final Set<Long> SYSTEM_MANAGED_CONFORMANCE_TYPES = new HashSet<>();
    private static final Set<String> SYSTEM_MANAGED_KIND_NAMES = new HashSet<>();

    private static final String COMMA_SPACE = ", ";

    static {
        SYSTEM_MANAGED_CONFORMANCE_TYPES.add(CoreTypeReference.ACCOUNT.getValue());
        SYSTEM_MANAGED_CONFORMANCE_TYPES.add(CoreTypeReference.COMMUNICATION.getValue());
        SYSTEM_MANAGED_CONFORMANCE_TYPES.add(CoreTypeReference.MONEYPROVISION.getValue());
        SYSTEM_MANAGED_CONFORMANCE_TYPES.add(CoreTypeReference.MONEYSCHEDULER.getValue());
//        SYSTEM_MANAGED_CONFORMANCE_TYPES.add(CoreTypeReference.RATING().getValue());

        SYSTEM_MANAGED_KIND_NAMES.add("Requestor");
    }

    /**
     * Are the role players of the role list editable (i.e. can we add and delete them ?)
     * 
     * @param context The page context
     * @param roleList The role list
     * @return true if the role players are editable, otherwise false.
     */
    public static boolean isRoleListEditable(PageContext context, RoleListDTO roleList) {

        // firstly if the role list marked as read only, then the answer is no
        if (roleList.isReadOnly()) {
            return false;
        }

        if (SYSTEM_MANAGED_KIND_NAMES.contains(roleList.getKind().getName())) {
            return false;
        }

        // certain role player conformance types cannot be modified by the client
        Set<Long> intersection = new HashSet<>();
        for (TypeDTO typeDTO: roleList.getConformanceTypes()) {
            intersection.add(typeDTO.getId());
        }

        intersection.retainAll(SYSTEM_MANAGED_CONFORMANCE_TYPES);

        return intersection.isEmpty();

    }

    /**
     * Can a role player be added to the role list?
     * 
     * In addition to checking whether the role players of the role list are editable, this method
     * ensure that the cardinality constraints are not violated.
     * 
     * @param context The page context
     * @param roleList The role list
     * @return true if the role player can be added, otherwise false
     */
    public static boolean canRolePlayerBeAdded(PageContext context, RoleListDTO roleList) {

        // if the role list is not editable, then we can never delete
        if (!isRoleListEditable(context, roleList)) {
            return false;
        }
        
        // count the number of role players - this handles the case of a x-x cardinalities where the role is 
        // present but the role player is not
        int maxCardinality = roleList.getMaximumNumberOfRoles();
        int rolePlayerCount = 0;
        for (RoleDTO role: roleList.getRoles()) {
            if (role.getRolePlayer() != null && role.getRolePlayer().getObjectId() != null) {
                rolePlayerCount++;
            }
        }
        return rolePlayerCount < maxCardinality;

    }

    /**
     * Format the conformance types for a role list
     * @param pageContext The page context
     * @param roleList The role list
     * @return The formatted string
     */
    public static String formatConformanceTypes(PageContext pageContext, RoleListDTO roleList) {

        Set<TypeDTO> conformanceTypes = roleList.getConformanceTypes();

        if (conformanceTypes.isEmpty()) {
            return StringUtils.EMPTY;
        }

        StringBuilder sb = new StringBuilder();
        ITypeFormatter typeFormatter = FormatUtil.getTypeFormatter(pageContext);

        int count = 1;
        for (TypeDTO conformanceType : conformanceTypes) {

            String conformanceMessage = typeFormatter.formatMessageWithFallback(
                    "common.type." + conformanceType.getName(),
                    conformanceType.getName()
            );
            sb.append(conformanceMessage);
            if (count != conformanceTypes.size()) {
                sb.append(COMMA_SPACE);
            }

            count++;
        }

        return sb.toString();
    } 

    /**
     * Generate a summary of the cardinality constraints for this role list
     * @param context The page context
     * @param roleList The role list
     * @return A string representation of the constraint
     */
    public static String getRoleListCardinality(PageContext context, RoleListDTO roleList) {

        StringBuilder sb = new StringBuilder();

        if (roleList.getMinimumNumberOfRoles().equals(roleList.getMaximumNumberOfRoles())) {
            sb.append(roleList.getMinimumNumberOfRoles());
        }
        else {
            sb.append(roleList.getMinimumNumberOfRoles());
            sb.append(" - ");
            if (roleList.getMaximumNumberOfRoles().equals(Integer.MAX_VALUE)) {
                sb.append("*");
            }
            else {
                sb.append(roleList.getMaximumNumberOfRoles());
            }
        }
        return sb.toString();
    }
    
    /**
     * Should a link be rendered to view the role player
     * @param context The page context
     * @param roleList The role list in which the role is present
     * @param role The role
     * @return true if a link should be rendered, otherwise false.
     */
    public static boolean canRolePlayerBeViewed(PageContext context, RoleListDTO roleList, RoleDTO role) {

        return role.getRolePlayer() != null && role.getRolePlayer().getObjectId() != null;
    }
    
    /**
     * Can the role, or the role player be deleted ?
     * @param context The page context
     * @param roleList The role list
     * @param role The role
     * @return true if the role or the role player can be deleted, otherwise false
     */
    public static boolean canRoleOrRolePlayerBeDeleted(PageContext context, RoleListDTO roleList, RoleDTO role) {
        
        // if the role list is not editable, then we can never delete
        if (!isRoleListEditable(context, roleList)) {
            return false;
        }
        
        // if we are above the minimum cardinality, we can always delete
        int minCardinality = roleList.getMinimumNumberOfRoles();
        if (roleList.getRoles().size() > minCardinality) {
            return true;
        }
        
        // if we have less than the min number of roles, but we have a role player, we should
        // be allowed to remove the role player
        return role.getRolePlayer() != null && role.getRolePlayer().getObjectId() != null;

    }
}
