/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.product;

import com.silvermoongroup.businessservice.productmanagement.dto.AgreementState;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;

/**
 * @author Justin Walsh
 */
public class AgreementStateTableDecorator extends AbstractTableDecorator {

    public Object getLegallyBinding() {
        return formatYesNo(getAgreementState().isLegallyBinding());
    }

    public Object getRiskBearing() {
        return formatYesNo(getAgreementState().isRiskBearing());
    }

    private AgreementState getAgreementState() {
        return (AgreementState) getCurrentRowObject();
    }


}
