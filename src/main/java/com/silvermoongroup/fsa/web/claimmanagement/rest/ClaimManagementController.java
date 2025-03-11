/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement.rest;

import com.silvermoongroup.businessservice.claimmanagement.dto.ClaimTreeNode;
import com.silvermoongroup.businessservice.claimmanagement.service.intf.IClaimManagementService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.ObjectReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST resource for claim management
 *
 * @author Justin Walsh
 */
@RestController
@RequestMapping("/claim-management")
public class ClaimManagementController {

    @Autowired
    private IClaimManagementService claimManagementService;

    @RequestMapping(
            value = "/claim-tree/node/{objectReference}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    @ResponseBody
    public ResponseEntity getClaimTree(@PathVariable("objectReference") ObjectReference objectReference) {
        ClaimTreeNode claimTree = claimManagementService.getClaimTree(new ApplicationContext(), objectReference);
        return ResponseEntity.ok(claimTree);
    }
}
