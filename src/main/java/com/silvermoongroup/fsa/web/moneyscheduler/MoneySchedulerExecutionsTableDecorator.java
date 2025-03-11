/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.moneyscheduler;

import com.silvermoongroup.common.datatype.Amount;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;
import com.silvermoongroup.ftx.domain.MoneySchedulerExecution;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @author Justin Walsh
 */
public class MoneySchedulerExecutionsTableDecorator extends AbstractTableDecorator {

    public Object getId() {
        return generateIdentityDivWithObjectReferenceTooltip(getMoneySchedulerExecution().getObjectReference());
    }

    public Object getType() {
        return generateTypeDivWithObjectReferenceTooltip(getMoneySchedulerExecution().getObjectReference());
    }

    public Object getRequestedExecutionDate() {
        return formatDate(getMoneySchedulerExecution().getRequestedExecutionDate());
    }

    public Object getCyclePeriod() {
        return String.format(
                "%1s &rarr; %2s",
                formatDate(getMoneySchedulerExecution().getSchedulerExecutionCyclePeriod().getStart()),
                formatDate(getMoneySchedulerExecution().getSchedulerExecutionCyclePeriod().getEnd())
        );
    }

    public Object getStartedAt() {
        return formatDateTime(getMoneySchedulerExecution().getStartedAt());
    }

    public Object getEndedAt() {
        return formatDateTime(getMoneySchedulerExecution().getEndedAt());
    }

    public Object getDuration() {
        DateTime startedAt = getMoneySchedulerExecution().getStartedAt();
        DateTime endedAt = getMoneySchedulerExecution().getEndedAt();
        if (endedAt == null) {
            return StringUtils.EMPTY;
        }
        return formatAmount(new Amount(new BigDecimal(endedAt.getMillis() - startedAt.getMillis())));
    }

    public Object getReturnCode() {
        return formatEnum(getMoneySchedulerExecution().getReturnCode());
    }

    public Object getMoneyScheduler() {
        return generateTypeDivWithObjectReferenceTooltip(getMoneySchedulerExecution().getMoneyScheduler().getObjectReference());
    }

    private MoneySchedulerExecution getMoneySchedulerExecution() {
        return ((MoneySchedulerExecution) getCurrentRowObject());
    }

    @Override
    public String addRowId() {
        return "mse_" + getMoneySchedulerExecution().getId();
    }

    @Override
    public String addRowClass() {
        return "mse";
    }
}
