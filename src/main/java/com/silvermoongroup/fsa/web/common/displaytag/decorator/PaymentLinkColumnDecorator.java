/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.displaytag.decorator;

import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.PageContext;

/**
 * Created by Jordan Olsen on 2017/05/03.
 */
public class PaymentLinkColumnDecorator implements DisplaytagColumnDecorator {

    /**
     * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(java.lang.Object, javax.servlet.jsp.PageContext, org.displaytag.properties.MediaTypeEnum)
     */
    @Override
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
        ObjectReference objectReference = (ObjectReference) columnValue;

        IProductDevelopmentService typeService =
                WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext()).
                        getBean(IProductDevelopmentService.class);
        ApplicationContext applicationContext = new ApplicationContext();

        String linkLocation;

        if (typeService.isSubType(applicationContext, CoreTypeReference.PAYMENT.getValue(), objectReference.getTypeId())) {
            linkLocation = "payment.do";
        } else if (typeService.isSubType(applicationContext, CoreTypeReference.PAYMENTDUE.getValue(), objectReference.getTypeId())) {
            linkLocation = "paymentdue.do";
        } else {
            //When the type id is not a subtype of Payment or PaymentDue we just want to display the object id
            //and not object id as an anchor link
            return objectReference.getObjectId();
        }

        return "<a href=\"/fsa/secure/ftx/" + linkLocation + "?financialTransactionObjectReference=" +
                objectReference.toString() + "\">" + objectReference.getObjectId() + "</a>";
    }
}
