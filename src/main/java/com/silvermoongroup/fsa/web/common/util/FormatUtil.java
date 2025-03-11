/**
 *
 */
package com.silvermoongroup.fsa.web.common.util;

import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * Utilities for looking up formatting classes.
 *
 * @author Justin Walsh
 */
public class FormatUtil {

    private FormatUtil() {
    }

    public static ITypeFormatter getTypeFormatter(HttpServletRequest request) {
        return getTypeFormatter(request.getSession().getServletContext());
    }

    public static ITypeFormatter getTypeFormatter(PageContext pageContext) {
        return getTypeFormatter(pageContext.getServletContext());
    }

    private static ITypeFormatter getTypeFormatter(ServletContext sc) {
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(sc);
        return wac.getBean(ITypeFormatter.class);
    }
}
