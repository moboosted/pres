/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement;

import com.silvermoongroup.businessservice.claimmanagement.dto.LossEvent;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;

/**
 * Created by damir on 2/15/2017.
 */
public class LossEventTableDecorator extends AbstractTableDecorator {

    public Object getEventTime() {
        LossEvent currentRowObject = (LossEvent) getCurrentRowObject();
        return formatDateTime(currentRowObject.getEventTime());
    }

    public Object getType() {
        LossEvent currentRowObject = (LossEvent) getCurrentRowObject();
        return formatType(currentRowObject.getTypeId());
    }

    public Object getStartDate() {
        LossEvent currentRowObject = (LossEvent) getCurrentRowObject();
        return formatDate(currentRowObject.getStartDate());
    }

    public Object getEndDate() {
        LossEvent currentRowObject = (LossEvent) getCurrentRowObject();
        return formatDate(currentRowObject.getEndDate());
    }
}
