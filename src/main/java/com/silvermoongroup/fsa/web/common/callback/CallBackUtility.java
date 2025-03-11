/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.callback;

import com.silvermoongroup.base.exception.ApplicationRuntimeException;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Stack;

/**
 * A utility class for callbacks.
 * 
 * This class used for forward the request to the specified action.
 */

public class CallBackUtility {

    public static final String CALL_BACK_STACK = "CALL_BACK_STACK";
    public static final String PAGE = "PAGE";
    public static final String SCOPE = "SCOPE";
    public static final String TYPE = "TYPE";
    public static final String KEY = "KEY";
    public static final String REDIRECT = "REDIRECT";
    public static final String INTENDED_MAPPING = "INTENDED_MAPPING";

    // should never be instantiated
    private CallBackUtility() {
    }

    /**
     * This method is used for setting the attributes to the stack and stack will be setting in to session.
     * @param request
     * @param response
     * @return callBack
     */
    public static CallBack addCallBack(HttpServletRequest request, HttpServletResponse response) {

        String page = (String)request.getAttribute(CallBackUtility.PAGE);
        String key = (String)request.getAttribute(CallBackUtility.KEY);
        String scope = (String)request.getAttribute(CallBackUtility.SCOPE);
        Class<?> type = (Class<?>)request.getAttribute(CallBackUtility.TYPE);
        Boolean redirect = (Boolean)request.getAttribute(CallBackUtility.REDIRECT);
        String intendedMapping = (String)request.getAttribute(CallBackUtility.INTENDED_MAPPING);
        CallBack callBack = new CallBack();


        if (page != null) {
            callBack.setPage(page);
        } else{
            throw new ApplicationRuntimeException("page attribute cannot be null");
        }

        if (key != null) {
            callBack.setKey(key);
        }
        if (scope != null) {
            callBack.setScope(scope);
        } else{
            scope = "request";
        }
        if (type != null) {
            callBack.setType(type);
        }

        if (redirect != null) {
            callBack.setRedirect(redirect);
        }
        else {
            callBack.setRedirect(false);
        }

        callBack.setIntendedMapping(intendedMapping);

        // getting stack from session, if object null then create new and set back to session
        Stack<CallBack> stack =
                (Stack<CallBack>)request.getSession().getAttribute(CallBackUtility.CALL_BACK_STACK);

        if (stack == null) {
            stack = new Stack<CallBack>();
        }
        stack.push(callBack);

        request.getSession().setAttribute(CallBackUtility.CALL_BACK_STACK, stack);
        return callBack;

    }
    /**
     * This method is used for getting the callBack object from the stack if exists, otherwise return null.
     * @param request
     * @param response
     * @return callBack
     */
    public static CallBack getCallBack(HttpServletRequest request, HttpServletResponse response) {
        Stack<CallBack> stack =
                (Stack<CallBack>) request.getSession().getAttribute(CallBackUtility.CALL_BACK_STACK);
        CallBack callBack = null;
        if (stack != null && stack.size() != 0) {
            callBack = stack.pop();
        }
        return callBack;
    }

    public static boolean isCallBackEmpty(HttpServletRequest request, HttpServletResponse response) {
        Stack<CallBack> stack = (Stack<CallBack>) request.getSession().getAttribute(CallBackUtility.CALL_BACK_STACK);
        if (stack == null) {
            return true;
        }
        return stack.empty();
    }

    public static boolean isCallBackIntendedForMethod(HttpServletRequest request, HttpServletResponse response, String method) {
        Stack<CallBack> stack = (Stack<CallBack>) request.getSession().getAttribute(CallBackUtility.CALL_BACK_STACK);
        if (stack == null || stack.empty()) {
            return false;
        }
        return stack.peek().getIntendedMapping().equals(method);
    }

    /**
     * Resets all call backs
     * @param request
     */
    public static void resetCallBacks(HttpServletRequest request) {
        request.getSession().removeAttribute(CallBackUtility.CALL_BACK_STACK);
    }

    /**
     * This method is used for return the ActionForward object if callBack object exist in the stack.
     * @param callBack
     * @return actionForward
     */
    public static ActionForward getForwardAction(CallBack callBack) {
        ActionForward actionForward = new ActionForward();

        String page = callBack.getPage();
        if (page != null) {
            actionForward.setPath(page);
        }
        actionForward.setRedirect(callBack.isRedirect());
        return actionForward;

    }

    /**
     * This method is used for return the ActionForward object if callBack object exist in the stack.
     * @param callBack
     * @param redirect
     * @return actionForward
     */
    public static ActionForward getForwardAction(CallBack callBack, boolean redirect) {
        ActionForward actionForward = new ActionForward();
        actionForward.setRedirect(redirect);

        String page = callBack.getPage();
        if (page != null) {
            actionForward.setPath(page);
        }
        actionForward.setRedirect(callBack.isRedirect());
        return actionForward;

    }
    /**
     * This method is used for return the ActionForward Object if callBack object exists in the stack.
     * @param request
     * @param response
     * @return actionForward
     */
    public static ActionForward getForwardAction(HttpServletRequest request, HttpServletResponse response) {
        CallBack callBack = getCallBack(request,response);
        ActionForward actionForward = getForwardAction(callBack);
        actionForward.setRedirect(callBack.isRedirect());
        return actionForward;
    }

    /**
     * This method is used for set the object in to specified scope.
     * @param request
     * @param object
     * @param callBack
     */
    public static void setAttribute(HttpServletRequest request, Object object, CallBack callBack) {
        if (callBack.getScope().equals("request")) {
            request.setAttribute(callBack.getKey(), object);
        } else if (callBack.getScope().equals("session")) {
            request.getSession().setAttribute(callBack.getKey(), object);
        }
    }
}
