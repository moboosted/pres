/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.action;

import com.silvermoongroup.fsa.web.exception.BaseExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.*;
import org.apache.struts.chain.commands.UnauthorizedActionException;
import org.apache.struts.config.ExceptionConfig;
import org.hibernate.StaleStateException;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Displays the root cause of the error message using a message key lookup.
 *
 * @author Justin Walsh
 */
public class ActionExceptionHandler extends BaseExceptionHandler {

    private static final String ROOT_CAUSE_STACK = "ROOT_CAUSE_STACK";

    @Override
    protected ActionForward handleRootCause(Exception raised, Throwable rootCause, ExceptionConfig ae,
            ActionMapping mapping, ActionForm formInstance, HttpServletRequest request, HttpServletResponse response) {
        if (rootCause instanceof StaleStateException) {
            return handleFailedOptimisticConcurrencyFailure(raised, rootCause,
                    ae, mapping, formInstance, request, response);
        } else if (rootCause instanceof AccessDeniedException || rootCause instanceof UnauthorizedActionException) {
            try {
                response.sendError(403);
                return null;
            } catch (IOException e) {
                return new ActionRedirect("/403.jsp");
            }
        } else {
            return handleGeneralError(raised, rootCause, ae, mapping, formInstance,
                    request, response);
        }
    }

    private ActionForward handleGeneralError(Exception raised, Throwable rootCause,
            ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance, HttpServletRequest request,
            HttpServletResponse response) {
        ActionForward forward = mapping.findForward("input");

        ActionMessage error = new ActionMessage(
                ae.getKey(),
                rootCause.getMessage()
        );

        String property = error.getKey();
        request.setAttribute(Globals.EXCEPTION_KEY, rootCause);

        // store the root cause as a string, trimming at 30 lines
        int noOfFramesToInclude = Math.min(rootCause.getStackTrace().length, 30);
        try {
            final Writer writer = new StringWriter();
            final BufferedWriter bw = new BufferedWriter(writer);

            bw.write("Error: " + rootCause.getClass().getName());
            bw.newLine();
            if (rootCause.getMessage() != null) {
                bw.write("Message: " + rootCause.getMessage());
                bw.newLine();
            }

            bw.write("Stack Trace:");
            bw.newLine();

            for (int i = 0; i < noOfFramesToInclude; i++) {
                StackTraceElement ste = rootCause.getStackTrace()[i];
                bw.write("   ");
                bw.write(StringUtils.trimToEmpty(ste.toString()));
                bw.newLine();
            }

            if (noOfFramesToInclude < rootCause.getStackTrace().length) {
                bw.write("...");
                bw.newLine();
            }

            bw.flush();

            String rootCauseStack = writer.toString();
            request.setAttribute(ROOT_CAUSE_STACK, rootCauseStack);
            writer.close();
        } catch (IOException e) {
        }

        storeException(request, property, error, forward, ae.getScope());

        return forward;
    }

    private ActionForward handleFailedOptimisticConcurrencyFailure(Exception raised, Throwable rootCause,
            ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance, HttpServletRequest request,
            HttpServletResponse response) {
        ActionForward forward = mapping.findForward("input");

        ActionMessage error = new ActionMessage(
                "errors.optimisticConcurrencyFailed");

        String property = error.getKey();
        request.setAttribute(Globals.EXCEPTION_KEY, rootCause);
        storeException(request, property, error, forward, ae.getScope());

        return forward;
    }
}
