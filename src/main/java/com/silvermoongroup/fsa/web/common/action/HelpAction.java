package com.silvermoongroup.fsa.web.common.action;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The about action.
 */
public class HelpAction extends AbstractLookupDispatchAction {

    public ActionForward about(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return actionMapping.findForward("input");
    }
}
