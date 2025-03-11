/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.displaytag.decorator;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import javax.servlet.jsp.PageContext;

/**
 * Decorates an Object reference with a div containing the name of the type with an informative tooltip.
 *
 * @author Justin Walsh
 */
public class ObjectReferenceTypeNameColumnDecorator implements DisplaytagColumnDecorator {

    @Override
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
        ObjectReference objectReference = (ObjectReference)columnValue;
        return FormatUtil.getTypeFormatter(pageContext).generateTypeDivWithObjectReferenceTooltip(objectReference);
    }
}
