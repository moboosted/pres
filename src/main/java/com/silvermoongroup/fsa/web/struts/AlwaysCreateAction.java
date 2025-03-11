/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.struts;

import org.apache.struts.action.Action;
import org.apache.struts.chain.commands.servlet.CreateAction;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.config.ActionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A customisation of the usual {@link CreateAction} which ALWAYS creates an action.  
 * This is regarded as a negligible overhead since actions are singlton objects and are cached.
 * 
 * The reason for this is so the {@link ExecuteInvalidFormHookAction} can be executed with an action.
 */
public class AlwaysCreateAction extends CreateAction {
    
    /**
     * Provide a Commons logging instance for this class.
     */
    private static Logger LOG = LoggerFactory.getLogger(AlwaysCreateAction.class);

    /**
     * @see org.apache.struts.chain.commands.ActionCommandBase#execute(org.apache.struts.chain.contexts.ActionContext)
     */
    @Override
    public boolean execute(ActionContext actionCtx) throws Exception {
        // Skip processing if the current request is not valid
//        Boolean valid = actionCtx.getFormValid();

        // ALWAYS create an action
/*
 
        if ((valid == null) || !valid.booleanValue()) {
            LOG.trace("Invalid form; not going to execute.");

            return (false);
        }
**/

        // Check to see if an action has already been created
        if (actionCtx.getAction() != null) {
            LOG.trace("already have an action [" + actionCtx.getAction() + "]");

            return (false);
        }

        // Look up the class name for the desired Action
        ActionConfig actionConfig = actionCtx.getActionConfig();
        String type = actionConfig.getType();

        if (type == null) {
            String command = actionConfig.getCommand();
            if ((command == null) && (actionConfig.getForward() == null)
                && (actionConfig.getInclude() == null)) {
                LOG.error("no type or command for " + actionConfig.getPath());
            } else {
                LOG.trace("no type for " + actionConfig.getPath());
            }

            return (false);
        }

        // Create (if necessary) and cache an Action instance
        Action action = getAction(actionCtx, type, actionConfig);

        if (LOG.isTraceEnabled()) {
            LOG.trace("setting action to " + action);
        }

        actionCtx.setAction(action);

        return (false);
    }

}
