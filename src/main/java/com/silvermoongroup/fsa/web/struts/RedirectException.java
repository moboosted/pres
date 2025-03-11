/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.struts;

import org.apache.struts.action.ActionRedirect;

/**
 * Allows any class/method involved in the handling of a request to 'terminate' execution and indicate that the 
 * client should be redirected to a particular resource.  This relies on the fact that the 
 * <code>ActionExceptionHandler</code> configured in the application handles this type of exception appropriately.
 * 
 * This class does not contain the reason for the redirect.  Make use of the standard mechanisms to convey
 * this information (for example, action messages).
 * 
 * @author Justin Walsh
 */
public class RedirectException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private final ActionRedirect redirect;
    
    public RedirectException(ActionRedirect redirect) {
        this.redirect = redirect;
    }
    
    public ActionRedirect getRedirect() {
        return redirect;
    }
}
