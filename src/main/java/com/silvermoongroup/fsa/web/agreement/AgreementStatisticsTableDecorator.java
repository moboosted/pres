/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.agreement;

import com.silvermoongroup.fsa.statistics.AgreementStatisticsEntry;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;

/**
 * Created by damir on 8/28/2015.
 */
public class AgreementStatisticsTableDecorator extends AbstractTableDecorator {

    public Object getAgreementKind() {
        AgreementStatisticsEntry currentRowObject = (AgreementStatisticsEntry) getCurrentRowObject();
        return currentRowObject.getAgreementKind().getName();
    }

    public Object getCurrencyCode() {
        AgreementStatisticsEntry currentRowObject = (AgreementStatisticsEntry) getCurrentRowObject();
        return formatEnum(currentRowObject.getCurrencyCode());
    }

    public Object getAgreementState() {
        AgreementStatisticsEntry currentRowObject = (AgreementStatisticsEntry) getCurrentRowObject();
        return currentRowObject.getAgreementState().getName();
    }

    public Object getAgreementCount() {
        AgreementStatisticsEntry currentRowObject = (AgreementStatisticsEntry) getCurrentRowObject();
        return currentRowObject.getAgreementCount();
    }

    public Object getAgreementPremiumAmount() {
        AgreementStatisticsEntry currentRowObject = (AgreementStatisticsEntry) getCurrentRowObject();
        return formatAmount(currentRowObject.getAgreementPremiumAmount());
    }

    public Object getRequestKind() {
        AgreementStatisticsEntry currentRowObject = (AgreementStatisticsEntry) getCurrentRowObject();
        if (currentRowObject.getRequestKind().getName() != null) {
            return currentRowObject.getRequestKind().getName();
        }
        return new String("");
    }

    public Object getRequestCount() {
        AgreementStatisticsEntry currentRowObject = (AgreementStatisticsEntry) getCurrentRowObject();
        return currentRowObject.getRequestCount();
    }

    public Object getRequestPremiumAmount() {
        AgreementStatisticsEntry currentRowObject = (AgreementStatisticsEntry) getCurrentRowObject();
        return formatAmount(currentRowObject.getRequestPremiumAmount());
    }
}
