/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.exception;

import com.silvermoongroup.fsa.web.struts.RedirectException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Base class for exception handlers.  Performs some base functionality
 * including
 * <ul>
 * <li>Handing redirect exceptions (which are not logged)</li>
 * <li>Logging the exception</li>
 * <li>Deriving the root cause for use by subclasses</li>
 * </ul>
 * 
 * @author Justin Walsh
 */
public abstract class BaseExceptionHandler extends ExceptionHandler {

    private final  Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public final ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance,
            HttpServletRequest request, HttpServletResponse response) throws ServletException {
        
        if (ex instanceof RedirectException) {
            RedirectException rd = (RedirectException)ex;
            if (rd.getRedirect() != null) {
                return rd.getRedirect();
            }
        }

        // first log the exception
        log.error(ex.getMessage(), ex);

        // derive the root cause
        Throwable rootCause = ex;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }

        return handleRootCause(
                ex,
                rootCause,
                ae,
                mapping,
                formInstance,
                request,
                response);
    }

    /**
     * Handle the root cause of the exception
     * @param raised The original exception raised.
     * @param rootCause The root cause.  Guaranteed not to be null.
     * @param ae
     * @param mapping
     * @param formInstance
     * @param request
     * @param response
     *
     * @return
     *     possible object is
     *     {@link ActionForward }
     */
    protected abstract ActionForward handleRootCause(
            Exception raised, Throwable rootCause, ExceptionConfig ae,
            ActionMapping mapping, ActionForm formInstance,
            HttpServletRequest request, HttpServletResponse response);

}
