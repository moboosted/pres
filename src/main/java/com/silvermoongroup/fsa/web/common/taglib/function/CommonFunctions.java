/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.taglib.function;

import com.silvermoongroup.businessservice.financialmanagement.dto.FinancialTransactionDTO;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.PageContext;

/**
 * Created by damir on 27.04.17.
 */
public class CommonFunctions {

    public static boolean isSameOrSubTypeOfPayment(PageContext pageContext, FinancialTransactionDTO financialTransaction) {

        IProductDevelopmentService typeService =
                WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext()).
                        getBean(IProductDevelopmentService.class);
        ApplicationContext applicationContext = new ApplicationContext();
        return typeService.isSameOrSubType(applicationContext,
            CoreTypeReference.PAYMENT.getValue(), Long.valueOf(financialTransaction.getObjectReference().getTypeId()));
    }

}
