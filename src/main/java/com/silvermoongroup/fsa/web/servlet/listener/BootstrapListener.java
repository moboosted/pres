/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.servlet.listener;

import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.struts.EnumerationReferenceConverter;
import com.silvermoongroup.fsa.web.struts.LongConverter;
import com.silvermoongroup.fsa.web.struts.ObjectReferenceConverter;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;

/**
 * Bootstraps the web application, specifically
 * <ul>
 * <li>Loading the custom converters</li>
 * </ul>
 *
 * @author Justin Walsh
 */
public class BootstrapListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        // empty values should be treated as null
        ConvertUtils.register(new LongConverter(), Long.class);
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new ObjectReferenceConverter(), ObjectReference.class);
        ConvertUtils.register(new EnumerationReferenceConverter(), EnumerationReference.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

}
