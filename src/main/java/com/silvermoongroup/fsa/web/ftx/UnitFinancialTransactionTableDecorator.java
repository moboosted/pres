package com.silvermoongroup.fsa.web.ftx;

import com.silvermoongroup.businessservice.financialmanagement.dto.FinancialTransactionDTO;
import com.silvermoongroup.businessservice.financialmanagement.dto.UnitFinancialTransactionRelationshipDTO;
import com.silvermoongroup.businessservice.financialmanagement.dto.UnitTransactionDTO;
import com.silvermoongroup.common.datatype.CurrencyAmount;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;

public class UnitFinancialTransactionTableDecorator extends AbstractTableDecorator {

    public Object getId() {
        return generateIdentityDivWithObjectReferenceTooltip(getUnitTransaction().getObjectReference());
    }

    public Long getUnitTransactionObjectReferenceObjectId() {
        return getUnitTransaction().getObjectReference().getObjectId();
    }

    public Long getFinancialTransactionObjectReferenceObjectId() {
        return getFinancialTransaction().getObjectReference().getObjectId();
    }

    public Object getType() {
        return generateTypeDivWithObjectReferenceTooltip(getUnitFinancialTransactionTransaction().getObjectReference());
    }

    public Object getEffectiveDate() {
        return getUnitTransaction().getEffectiveDate();
    }
    
    public Object getUnitTransactionPostedDate() {
        return formatDate(getUnitTransaction().getPostedDate().getDate());
    }

    public Object getFinancialTransactionPostedDate() {
        return formatDate(getFinancialTransaction().getPostedDate().getDate());
    }
    
    public Object getNumberOfUnits() {
        return formatMeasureAmount(getUnitTransaction().getNumberOfUnits(), true);
    }

    public Object getCurrencyAmount() {
        CurrencyAmount currencyAmount = (CurrencyAmount) getFinancialTransaction().getAmount();
        String code = getTypeFormatter().formatEnum(currencyAmount.getCurrencyCode());
        String amount = getTypeFormatter().formatCurrencyAmount(currencyAmount);

        StringBuilder codeAndAmount = new StringBuilder();
        codeAndAmount.append(code)
                     .append(" ")
                     .append(amount);
        return codeAndAmount;
    }

    private UnitTransactionDTO getUnitTransaction() {
        return ((UnitFinancialTransactionRelationshipDTO) getCurrentRowObject()).getUnitTransaction();
    }

    private FinancialTransactionDTO getFinancialTransaction() {
        return ((UnitFinancialTransactionRelationshipDTO) getCurrentRowObject()).getFinancialTransaction();
    }

    private UnitFinancialTransactionRelationshipDTO getUnitFinancialTransactionTransaction() {
        return (UnitFinancialTransactionRelationshipDTO) getCurrentRowObject();
    }

    private ITypeFormatter getTypeFormatter() {
        return FormatUtil.getTypeFormatter(getPageContext());
    }

}
