/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.servlet.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Binds the servlet context to the holder
 *
 * @author Justin Walsh
 */
public class ServletContextHolderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextHolder.bind(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextHolder.release();
    }
}
