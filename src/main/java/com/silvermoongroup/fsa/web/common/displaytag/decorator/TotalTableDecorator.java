/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.displaytag.decorator;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.TableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.PageContext;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A modification of the {@link org.displaytag.decorator.TotalTableDecorator} which fixes a few bugs and satisfies a number
 * of special requirements
 * <ul>
 * <li>We always work with BigDecimal values</li>
 * <li>A total row is not generated (not even an empty one) if no total columns are specified</li>
 * <li>Column decorators are applied <em>AFTER</em> totals are calculated.</li
 * </ul>
 *
 * @author Justin Walsh
 */
public class TotalTableDecorator extends AbstractTableDecorator {

    /**
     * Logger.
     */
    private static Logger log = LoggerFactory.getLogger(TotalTableDecorator.class);

    /**
     * total amount.
     */
    private Map<String, BigDecimal> grandTotals = new HashMap<>();

    /**
     * total amount for current group.
     */
    private Map<String, BigDecimal> subTotals = new HashMap<>();

    /**
     * Previous values needed for grouping.
     */
    private Map<String, Object> previousValues = new HashMap<>();

    /**
     * Name of the property used for grouping.
     */
    private String groupPropertyName;

    /**
     * Label used for subtotals. Default: "{0} total".
     */
    private String subtotalLabel = "{0} subtotal";

    /**
     * Label used for totals. Default: "Total".
     */
    private String totalLabel = "Total";

    /**
     * Is a total column present?
     */
    private boolean totalColumnPresent;

    /**
     * Setter for <code>subtotalLabel</code>.
     *
     * @param subtotalLabel The subtotalLabel to set.
     */
    public void setSubtotalLabel(String subtotalLabel) {
        this.subtotalLabel = subtotalLabel;
    }

    /**
     * Setter for <code>totalLabel</code>.
     *
     * @param totalLabel The totalLabel to set.
     */
    public void setTotalLabel(String totalLabel) {
        this.totalLabel = totalLabel;
    }

    /**
     * @see org.displaytag.decorator.Decorator#init(PageContext, Object, TableModel)
     */
    @Override
    public void init(PageContext context, Object decorated, TableModel tableModel) {
        super.init(context, decorated, tableModel);

        // reset
        groupPropertyName = null;
        grandTotals.clear();
        subTotals.clear();
        previousValues.clear();
        totalColumnPresent = false;

        for (@SuppressWarnings("unchecked")
             Iterator<HeaderCell> it = tableModel.getHeaderCellList().iterator(); it.hasNext(); ) {
            HeaderCell cell = it.next();
            if (cell.getGroup() == 1) {
                groupPropertyName = cell.getBeanPropertyName();
            }
        }
    }

    @Override
    public String startRow() {
        String subtotalRow = null;

        if (groupPropertyName != null) {
            Object groupedPropertyValue = evaluate(groupPropertyName);
            Object previousGroupedPropertyValue = previousValues.get(groupPropertyName);
            // subtotals
            if (previousGroupedPropertyValue != null
                    && !ObjectUtils.equals(previousGroupedPropertyValue, groupedPropertyValue)) {
                subtotalRow = createTotalRow(false);
            }
            previousValues.put(groupPropertyName, groupedPropertyValue);
        }

        for (@SuppressWarnings("unchecked")
             Iterator<HeaderCell> it = tableModel.getHeaderCellList().iterator(); it.hasNext(); ) {
            HeaderCell cell = it.next();
            if (cell.isTotaled()) {
                totalColumnPresent = true;

                String totalPropertyName = cell.getBeanPropertyName();
                BigDecimal amount = (BigDecimal) evaluate(totalPropertyName);

                BigDecimal previousSubTotal = subTotals.get(totalPropertyName);
                BigDecimal previousGrandTotals = grandTotals.get(totalPropertyName);

                if (amount == null) {
                    continue;
                }

                if (previousSubTotal == null) {
                    previousSubTotal = BigDecimal.ZERO;
                }

                if (previousGrandTotals == null) {
                    previousGrandTotals = BigDecimal.ZERO;
                }

                subTotals.put(totalPropertyName, previousSubTotal.add(amount));
                grandTotals.put(totalPropertyName, previousGrandTotals.add(amount));
            }
        }

        return subtotalRow;
    }

    /**
     * After every row completes we evaluate to see if we should be drawing a new total line and summing the results
     * from the previous group.
     *
     * @return String
     */
    @Override
    @SuppressWarnings("rawtypes")
    public final String finishRow() {
        StringBuilder buffer = new StringBuilder(1000);

        // Grand totals...
        if (getViewIndex() == ((List) getDecoratedObject()).size() - 1) {
            if (groupPropertyName != null) {
                buffer.append(createTotalRow(false));
            }
            buffer.append(createTotalRow(true));
        }
        return buffer.toString();

    }

    protected String createTotalRow(boolean grandTotal) {
        if (!totalColumnPresent) {
            return "";
        }

        StringBuilder buffer = new StringBuilder(1000);
        buffer.append("\n<tr class=\"total\">"); //$NON-NLS-1$

        for (HeaderCell cell : (Iterable<HeaderCell>) tableModel.getHeaderCellList()) {
            String cssClass = ObjectUtils.toString(cell.getHtmlAttributes().get("class"));

            buffer.append("<td"); //$NON-NLS-1$
            if (StringUtils.isNotEmpty(cssClass)) {
                buffer.append(" class=\""); //$NON-NLS-1$
                buffer.append(cssClass);
                buffer.append("\""); //$NON-NLS-1$
            }
            buffer.append(">"); //$NON-NLS-1$

            if (cell.isTotaled()) {
                String totalPropertyName = cell.getBeanPropertyName();
                Object total = grandTotal ? grandTotals.get(totalPropertyName) : subTotals.get(totalPropertyName);

                DisplaytagColumnDecorator[] decorators = cell.getColumnDecorators();
                for (DisplaytagColumnDecorator decorator : decorators) {
                    try {
                        total = decorator.decorate(total, this.getPageContext(), tableModel.getMedia());
                    } catch (DecoratorException e) {
                        log.warn(e.getMessage(), e);
                        // ignore, use undecorated value for totals
                    }
                }
                buffer.append(total);
            } else if (groupPropertyName != null && groupPropertyName.equals(cell.getBeanPropertyName())) {
                buffer.append(grandTotal ? totalLabel : MessageFormat.format(subtotalLabel, new Object[]{previousValues
                        .get(groupPropertyName)}));
            }

            buffer.append("</td>"); //$NON-NLS-1$

        }

        buffer.append("</tr>"); //$NON-NLS-1$

        // reset subtotal
        this.subTotals.clear();

        return buffer.toString();
    }

}
