/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement;

import com.silvermoongroup.claim.domain.intf.IClaimFolder;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;

/**
 * @author Justin Walsh
 */
public class ClaimFolderTableDecorator extends AbstractTableDecorator {

    public Object getType() {
        return formatType(getClaimFolder().getTypeId());
    }

    public Object getStartDate() {
        return formatDate(getClaimFolder().getStartDate());
    }

    public Object getEndDate() {
        return formatDate(getClaimFolder().getEndDate());
    }

    private IClaimFolder getClaimFolder() {
        return (IClaimFolder) getCurrentRowObject();
    }


}
