/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.util;

import com.silvermoongroup.fsa.web.agreementandrequest.helpers.ViewActionHelper;
import com.silvermoongroup.fsa.web.common.callback.CallBackUtility;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Justin Walsh
 */
public class SessionUtil {
    
    private SessionUtil() {}
    
    /**
     * Clean the session of any conversation state (callbacks, etc)
     * Typically used when the user clicks on a menu item to start a new 'conversation'.
     *  
     * @param request The request object.
     */
    public static void clearConversationalState(HttpServletRequest request) {
        CallBackUtility.resetCallBacks(request);
        ViewActionHelper.cleanActionHelperDTO(request);
    }

}
