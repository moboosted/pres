/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.ftx;

import com.silvermoongroup.businessservice.financialmanagement.dto.UnitTransactionRelationshipDTO;
import com.silvermoongroup.common.constants.Components;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;
import com.silvermoongroup.ftx.domain.UnitTransactionRelationship;

/**
 * Decorator for a table containing unit transactions.
 */
public class UnitTransactionRelationshipTableDecorator extends AbstractTableDecorator {

    public Object getType() {
        UnitTransactionRelationshipDTO relationshipDTO = getUnitTransactionRelationshipDTO();
        return generateTypeDivWithObjectReferenceTooltip(
                new ObjectReference(
                        Components.FTX, relationshipDTO.getObjectReference().getTypeId(),
                        relationshipDTO.getObjectReference().getObjectId()));
    }

    public Object getMeasureAmount() {
        return formatMeasureAmount(getUnitTransactionRelationshipDTO().getAmount(), false);
    }

    public Object getRelatedFrom() {
        UnitTransactionRelationshipDTO r = getUnitTransactionRelationshipDTO();
        return generateTypeDivWithObjectReferenceTooltip(r.getRelatedFromUnitTransaction());
    }

    public Object getRelatedTo() {
        UnitTransactionRelationshipDTO r = getUnitTransactionRelationshipDTO();
        return generateTypeDivWithObjectReferenceTooltip(r.getRelatedToUnitTransaction());
    }

    private UnitTransactionRelationship getUnitTransactionRelationship() {
        return (UnitTransactionRelationship) getCurrentRowObject();
    }
    
    private UnitTransactionRelationshipDTO getUnitTransactionRelationshipDTO() {
        return (UnitTransactionRelationshipDTO) getCurrentRowObject();
    }
}
