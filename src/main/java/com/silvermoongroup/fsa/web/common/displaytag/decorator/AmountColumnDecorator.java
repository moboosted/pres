/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.displaytag.decorator;

import com.silvermoongroup.common.datatype.Amount;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import javax.servlet.jsp.PageContext;

/**
 * Format an {@link Amount} column value.
 * 
 * @author Justin Walsh
 */
public class AmountColumnDecorator implements DisplaytagColumnDecorator {

    /**
     * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(java.lang.Object, javax.servlet.jsp.PageContext, org.displaytag.properties.MediaTypeEnum)
     */
    @Override
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
        Amount amount = (Amount)columnValue;
        return FormatUtil.getTypeFormatter(pageContext).formatAmount(amount);
    }

}
