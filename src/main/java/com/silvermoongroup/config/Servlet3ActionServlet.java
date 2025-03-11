package com.silvermoongroup.config;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;

public class Servlet3ActionServlet extends ActionServlet {

    public Servlet3ActionServlet(String servletName, String urlPattern) {
        super();
        addServletMapping(servletName, urlPattern);
    }

    @Override
    protected void initServlet() {
        this.servletName = getServletConfig().getServletName();
        if (servletMapping != null) {
            getServletContext().setAttribute(Globals.SERVLET_KEY, servletMapping);
        }

    }

}
