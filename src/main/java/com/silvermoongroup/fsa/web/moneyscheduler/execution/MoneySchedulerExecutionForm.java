/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.moneyscheduler.execution;

import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.ftx.domain.MoneySchedulerExecution;
import com.silvermoongroup.ftx.domain.intf.IFinancialTransaction;

import java.util.Collection;

/**
 * @author Justin Walsh
 */
public class MoneySchedulerExecutionForm extends ValidatorForm {

    private ObjectReference moneySchedulerExecutionObjectReference;
    private MoneySchedulerExecution moneySchedulerExecution;
    private Collection<IFinancialTransaction> financialTransactions;

    public MoneySchedulerExecutionForm() {
    }

    public MoneySchedulerExecution getMoneySchedulerExecution() {
        return moneySchedulerExecution;
    }

    public void setMoneySchedulerExecution(MoneySchedulerExecution moneySchedulerExecution) {
        this.moneySchedulerExecution = moneySchedulerExecution;
    }

    public ObjectReference getMoneySchedulerExecutionObjectReference() {
        return moneySchedulerExecutionObjectReference;
    }

    public void setMoneySchedulerExecutionObjectReference(ObjectReference moneySchedulerExecutionObjectReference) {
        this.moneySchedulerExecutionObjectReference = moneySchedulerExecutionObjectReference;
    }

    public void setFinancialTransactions(Collection<IFinancialTransaction> financialTransactions) {
        this.financialTransactions = financialTransactions;
    }

    public Collection<IFinancialTransaction> getFinancialTransactions() {
        return financialTransactions;
    }

    public Long getDurationInMillis() {
        DateTime startedAt = moneySchedulerExecution.getStartedAt();
        DateTime endedAt = moneySchedulerExecution.getEndedAt();
        if (startedAt == null || endedAt == null) {
            return null;
        }
        return endedAt.getMillis()- startedAt.getMillis();
    }
}
