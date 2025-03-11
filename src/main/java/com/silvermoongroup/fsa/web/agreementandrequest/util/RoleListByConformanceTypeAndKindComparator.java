/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.util;

import com.silvermoongroup.businessservice.policymanagement.dto.RoleListDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.TypeDTO;

import java.util.Comparator;

/**
 * Compare based on the conformance type of the role list, and then kind (if the conformance types are equal)
 * 
 * @author Justin Walsh
 */
public class RoleListByConformanceTypeAndKindComparator implements Comparator<RoleListDTO> {
    
    private static final RoleListByConformanceTypeAndKindComparator INSTANCE = 
            new RoleListByConformanceTypeAndKindComparator();
    
    public static RoleListByConformanceTypeAndKindComparator getInstance() {
        return INSTANCE;
    }
    
    public RoleListByConformanceTypeAndKindComparator() {
    }
    
    @Override
    public int compare(RoleListDTO r1, RoleListDTO r2) {
        
        // this should never happen ...
        if (r1.getConformanceTypes().isEmpty()) {
            return -1;
        }
        if (r2.getConformanceTypes().isEmpty()) {
            return 1;
        }
        
        TypeDTO firstConformanceType = r1.getConformanceTypes().iterator().next();
        TypeDTO secondConformanceType = r2.getConformanceTypes().iterator().next();
        
        int value = firstConformanceType.getName().compareTo(secondConformanceType.getName());
        if (value != 0) {
            return value;
        }
        
        // now compare on kind name
        if (r1.getKind() == null || r1.getKind().getName() == null) {
            return -1;
        }
        if (r2.getKind() == null || r2.getKind().getName() == null) {
            return 1;
        }
        
        return r1.getKind().getName().compareTo(r2.getKind().getName());
    }
}
