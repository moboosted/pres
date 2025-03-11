/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement;

import com.silvermoongroup.claim.domain.intf.IElementaryClaim;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;

/**
 * @author Justin Walsh
 */
public class ElementaryClaimTableDecorator extends AbstractTableDecorator {

    public Object getType() {
        return formatType(getElementaryClaim().getTypeId());
    }

    public Object getStartDate() {
        return formatDate(getElementaryClaim().getStartDate());
    }

    public Object getEndDate() {
        return formatDate(getElementaryClaim().getEndDate());
    }

    private IElementaryClaim getElementaryClaim() {
        return (IElementaryClaim) getCurrentRowObject();
    }


}
