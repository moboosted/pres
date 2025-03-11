/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.displaytag.decorator;

import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import javax.servlet.jsp.PageContext;
import java.math.BigDecimal;

/**
 * Format a {@link BigDecimal} column value.
 * 
 * @author Justin Walsh
 */
public class BigDecimalColumnDecorator implements DisplaytagColumnDecorator {

    /**
     * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(java.lang.Object, javax.servlet.jsp.PageContext, org.displaytag.properties.MediaTypeEnum)
     */
    @Override
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
        BigDecimal bd = (BigDecimal)columnValue;
        return FormatUtil.getTypeFormatter(pageContext).formatBigDecimal(bd);
    }

}
