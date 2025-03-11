package com.silvermoongroup.config;

import com.silvermoongroup.fsa.web.config.RestServletConfig;
import org.displaytag.filter.ResponseOverrideFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class FSAWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletCxt)  throws ServletException {
        super.onStartup(servletCxt);
        //Register Struts servlet
        Servlet3ActionServlet actionServlet = new Servlet3ActionServlet("action", "*.do");
        ServletRegistration.Dynamic strutsRegistration = servletCxt.addServlet("action", actionServlet);
        strutsRegistration.setInitParameter("config", "/WEB-INF/struts-config.xml");
        strutsRegistration.setInitParameter("chainConfig", "com/silvermoongroup/fsa/web/struts/chain-config.xml");
        strutsRegistration.setInitParameter("debug", "2");
        strutsRegistration.setInitParameter("detail", "2");
        strutsRegistration.setLoadOnStartup(2);
        strutsRegistration.addMapping("*.do");

        CharacterEncodingFilter characterEncodingFilter =  new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        FilterRegistration.Dynamic characterEncodingFilterRegistration = servletCxt.addFilter("characterEncodingFilter", characterEncodingFilter);
        characterEncodingFilterRegistration.addMappingForUrlPatterns(null, false, "/*");

        ResponseOverrideFilter responseOverrideFilter = new ResponseOverrideFilter();
        FilterRegistration.Dynamic responseOverrideFilterRegistration = servletCxt.addFilter("responseOverrideFilter", responseOverrideFilter);
        responseOverrideFilterRegistration.addMappingForUrlPatterns(null, false, "*.do");

        servletCxt.addFilter("userProfileFilter", new DelegatingFilterProxy("userProfileFilter"))
                .addMappingForUrlPatterns(null, false, "*.do");

        servletCxt.addListener(com.silvermoongroup.fsa.web.servlet.listener.BootstrapListener.class);
        servletCxt.addListener(com.silvermoongroup.fsa.web.servlet.listener.TrackingSessionListener.class);
        servletCxt.addListener(org.springframework.web.context.request.RequestContextListener.class);
        servletCxt.addListener(com.silvermoongroup.fsa.web.servlet.context.ServletContextHolderListener.class);

    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/rest/*" };
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { ApplicationConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { RestServletConfig.class };
    }
}
