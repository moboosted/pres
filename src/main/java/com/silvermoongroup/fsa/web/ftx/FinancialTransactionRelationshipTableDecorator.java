/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.ftx;

import com.silvermoongroup.businessservice.financialmanagement.dto.FinancialTransactionRelationshipDTO;
import com.silvermoongroup.common.constants.Components;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;
import com.silvermoongroup.ftx.domain.FinancialTransactionRelationship;

/**
 * Decorator for a table containing financial transactions.
 *
 * @author Justin Walsh
 */
public class FinancialTransactionRelationshipTableDecorator extends AbstractTableDecorator {

    public Object getType() {
        FinancialTransactionRelationshipDTO r = getFinancialTransactionRelationshipDTO();
        return generateTypeDivWithObjectReferenceTooltip(
                new ObjectReference(Components.FTX, r.getObjectReference().getTypeId(), r.getObjectReference().getObjectId()));
    }

    public Object getAmount() {
        return formatAmount(getFinancialTransactionRelationshipDTO().getAmount());
    }

    public Object getRelatedFrom() {
        FinancialTransactionRelationshipDTO r = getFinancialTransactionRelationshipDTO();
        return generateTypeDivWithObjectReferenceTooltip(r.getRelatedFromFinancialTransaction());
    }

    public Object getRelatedTo() {
        FinancialTransactionRelationshipDTO r = getFinancialTransactionRelationshipDTO();
        return generateTypeDivWithObjectReferenceTooltip(r.getRelatedToFinancialTransaction());
    }

    private FinancialTransactionRelationship getFinancialTransactionRelationship() {
        return (FinancialTransactionRelationship) getCurrentRowObject();
    }
    
    private FinancialTransactionRelationshipDTO getFinancialTransactionRelationshipDTO() {
        return (FinancialTransactionRelationshipDTO) getCurrentRowObject();
    }
}
