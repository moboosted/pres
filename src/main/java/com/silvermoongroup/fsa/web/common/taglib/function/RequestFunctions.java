/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib.function;

import com.silvermoongroup.businessservice.policymanagement.dto.RequestDTO;
import com.silvermoongroup.fsa.web.locale.TypeFormatter;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.PageContext;

/**
 * JSP functions related to request processing.
 *
 * @author Justin Walsh
 */
public class RequestFunctions {

    private static final String executeMessagePrefix = "page.request.message.confirmexecute";
    private static final String cancelMessagePrefix = "page.request.message.confirmcancel";

    /**
     * Does the cancellation of a request require some form of confirmation by the user?
     * @param pageContext The page context
     * @param requestDTO The request being cancelled.
     * @return true if confirmation is required, otherwise false
     */
    public static String getRequestCancellationConfirmationMessage(javax.servlet.jsp.PageContext pageContext, RequestDTO requestDTO) {
        TypeFormatter formatter = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext()).getBean(TypeFormatter.class);
        return getConfirmationMessage(requestDTO, formatter, cancelMessagePrefix);
    }

    /**
     * Does the execution of a request require some form of confirmation by the user?
     * @param pageContext The page context
     * @param requestDTO The request being cancelled.
     * @return true if confirmation is required, otherwise false
     */
    public static String getRequestExecutionConfirmationMessage(javax.servlet.jsp.PageContext pageContext, RequestDTO requestDTO) {
        TypeFormatter formatter = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext()).getBean(TypeFormatter.class);
        String messagePrefix = executeMessagePrefix;
        return getConfirmationMessage(requestDTO, formatter, messagePrefix);

    }
    
    /**
     * Determine if the requestDTO that we are dealing with is a componentRequest on an Agreement or
     * a Request on the TopLevelAgreement
     * 
     * @param pageContext The page context
     * @param requestDTO The request being interrogated
     * @return true if request is "not" a componentRequest, otherwise false
     */
    public static boolean isComponentRequest(PageContext pageContext, RequestDTO requestDTO) {
        return requestDTO.isComponentRequest();
    }

    private static String getConfirmationMessage(RequestDTO requestDTO, TypeFormatter formatter, String messagePrefix) {
        // first look for a specific message for the request kind
        String message = formatter.formatMessageWithFallback(messagePrefix + "." + requestDTO.getKind().getName(), null, (String [])null);
        if (message != null) {
            return message;
        }

        // now look for a default message
        message = formatter.formatMessageWithFallback(messagePrefix, null, (String [])null);
        return message;
    }
}
