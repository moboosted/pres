/*
 * Licensed Materials
 * (C) Copyright Silvermoon Business Systems BVBA, Belgium
 * (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 * All Rights Reserved.
 */

package com.silvermoongroup.fsa.web.administration.action;

import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.context.support.ReloadablePropertyMessageResources;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FlushCachesAction extends AbstractLookupDispatchAction {

    public ActionForward clearCache(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        getConfigurationService().flushCaches(getApplicationContext());
        addInformationMessage(httpServletRequest, "page.cacheconfiguration.message.cachescleared");
        return actionMapping.findForward("input");
    }

    public ActionForward clearMessageResources(ActionMapping actionMapping, ActionForm actionForm,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        ((ReloadablePropertyMessageResources)getResources(httpServletRequest)).reload();

        return actionMapping.findForward("home");
    }


}
