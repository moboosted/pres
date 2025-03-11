/**
 * Licensed Materials
 * *  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 * *  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 * *  All Rights Reserved.
 **/
package com.silvermoongroup.config;

import com.silvermoongroup.fsa.web.config.RestServletConfig;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exclude com.silvermoongroup.fsa.web package as this will be scanned by the servlet dependency instead.
 */
@Configuration
@ImportResource({"classpath*:com/silvermoongroup/**/*-beans.xml", "classpath*:com/silvermoongroup/**/*-beans-override.xml"})
@ComponentScan(
        value = "com.silvermoongroup",
        excludeFilters={
            @ComponentScan.Filter(type=FilterType.ANNOTATION,  value={RestController.class, Controller.class}),
            @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,  value={RestServletConfig.class})
        }
)
@EnableTransactionManagement
@EnableAspectJAutoProxy
@PropertySource("${application.properties:classpath:application.properties}")
public class ApplicationConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
