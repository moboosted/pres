
package com.silvermoongroup.fsa.web.common.displaytag.decorator;

import com.silvermoongroup.businessservice.policymanagement.dto.AgreementRequestSearchResultDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.AgreementStateDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.RequestStateDTO;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import com.silvermoongroup.party.domain.PartyName;

import java.util.Collection;
import java.util.Iterator;

/**
 * Wraps a request search result, providing customisations to the display of the
 * result.
 * 
 * @author Justin Walsh
 */
public class RequestSearchResultsTableDecorator extends AbstractTableDecorator {

    public Object getRequestId() {
        AgreementRequestSearchResultDTO currentRowObject = (AgreementRequestSearchResultDTO) getCurrentRowObject();
        return generateIdentityDivWithObjectReferenceTooltip(currentRowObject.getRequestObjectRef());
    }

    public Object getRequestKind() {
        AgreementRequestSearchResultDTO currentRowObject = (AgreementRequestSearchResultDTO) getCurrentRowObject();
        return formatKind(currentRowObject.getRequestKind());
    }

    public Object getRequestLifeCycleStatus() {
        AgreementRequestSearchResultDTO currentRowObject = (AgreementRequestSearchResultDTO) getCurrentRowObject();
        RequestStateDTO value = currentRowObject.getCurrentRequestState();
        if (value == null) {
            return null;
        }
        return FormatUtil.getTypeFormatter(getPageContext()).formatRequestState(value);
    }

    /**
     * @return A java.util.Date representation of the request date.
     */
    public Object getRequestDate() {
        AgreementRequestSearchResultDTO currentRowObject = (AgreementRequestSearchResultDTO) getCurrentRowObject();
        return formatDateTime(currentRowObject.getRequestDate());
    }

    /**
     * @return A java.util.Date representation of the requested date.
     */
    public Object getRequestedDate() {

        AgreementRequestSearchResultDTO currentRowObject = (AgreementRequestSearchResultDTO) getCurrentRowObject();
        return formatDateTime(currentRowObject.getRequestedDate());
    }

    /**
     * @return A java.util.Date representation of the executed date.
     */
    public Object getExecutedDate() {
        AgreementRequestSearchResultDTO currentRowObject = (AgreementRequestSearchResultDTO) getCurrentRowObject();
        return formatDateTime(currentRowObject.getExecutedDate());
    }

    public Object getAgreementLifeCycleStatus() {
        AgreementRequestSearchResultDTO currentRowObject = (AgreementRequestSearchResultDTO) getCurrentRowObject();
        AgreementStateDTO value = currentRowObject.getCurrentAgreementState();
        if (value == null) {
            return null;
        }
        return FormatUtil.getTypeFormatter(getPageContext()).formatAgreementState(value);
    }

    public Object getAgreementExternalReference() {
        AgreementRequestSearchResultDTO currentRowObject = (AgreementRequestSearchResultDTO) getCurrentRowObject();
        return currentRowObject.getAgreementExternalReference();
    }

    public Object getAgreementKind() {
        AgreementRequestSearchResultDTO currentRowObject = (AgreementRequestSearchResultDTO) getCurrentRowObject();
        return formatKind(currentRowObject.getAgreementKind());
    }

    public Object getAgreementVersionNumber() {
        AgreementRequestSearchResultDTO currentRowObject = (AgreementRequestSearchResultDTO) getCurrentRowObject();
        return currentRowObject.getAgreementVersionNumber();
    }

    public Object getRequestorName() {
        AgreementRequestSearchResultDTO currentRowObject = (AgreementRequestSearchResultDTO) getCurrentRowObject();
        return getFormattedPartyName(currentRowObject.getRequestorName());
    }

    public Object getAuthorisorNames() {
        AgreementRequestSearchResultDTO currentRowObject = (AgreementRequestSearchResultDTO) getCurrentRowObject();
        Collection<PartyName> partyNames = currentRowObject.getAuthorisors().values();
        StringBuilder sb = new StringBuilder();

        int i = 1;
        for (Iterator<PartyName> iterator = partyNames.iterator(); iterator.hasNext();) {
            PartyName partyName = iterator.next();

            // only append numbers if there is more than one authorisor
            if (((i == 1) && iterator.hasNext()) || (i > 1)) {
                sb.append(i++);
                sb.append(". ");
            }

            sb.append(getFormattedPartyName(partyName));

            if (iterator.hasNext()) {
                sb.append("<br>");
            }
        }

        return sb.toString();
    }

    public Object getOverriderName() {
        AgreementRequestSearchResultDTO currentRowObject = (AgreementRequestSearchResultDTO) getCurrentRowObject();
        return getFormattedPartyName(currentRowObject.getOverriderName());
    }
}
