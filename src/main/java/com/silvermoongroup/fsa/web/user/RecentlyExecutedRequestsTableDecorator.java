/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.user;

import com.silvermoongroup.businessservice.policymanagement.dto.AgreementRequestSearchResultDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.AgreementStateDTO;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;

/**
 * Decorator for the recently executed requests table
 *
 * @author Justin Walsh
 */
public class RecentlyExecutedRequestsTableDecorator extends AbstractTableDecorator {

    public Object getRequestKind() {
        AgreementRequestSearchResultDTO currentRowObject = getAgreementSearchResultDTO();
        return formatKind(currentRowObject.getRequestKind());
    }

    public Object getExecutedDate() {
        AgreementRequestSearchResultDTO currentRowObject = getAgreementSearchResultDTO();
        return formatDateTime(currentRowObject.getExecutedDate());
    }

    public Object getAgreementSummary() {
        AgreementRequestSearchResultDTO currentRowObject = getAgreementSearchResultDTO();

        AgreementStateDTO value = currentRowObject.getCurrentAgreementState();
        return formatKind(currentRowObject.getAgreementKind()) + " - " +
                (currentRowObject.getAgreementExternalReference() == null ? "" :  currentRowObject.getAgreementExternalReference()) +
                " (" + FormatUtil.getTypeFormatter(getPageContext()).formatAgreementState(value) +
                " - v" + currentRowObject.getAgreementVersionNumber() + ") ";
    }

    private AgreementRequestSearchResultDTO getAgreementSearchResultDTO() {
        return (AgreementRequestSearchResultDTO) getCurrentRowObject();
    }


}
