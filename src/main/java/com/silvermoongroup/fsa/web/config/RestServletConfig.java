/**
 **  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@Configuration
@ComponentScan(
        value = {"com.silvermoongroup.fsa.web"},
        includeFilters = {
                @ComponentScan.Filter(type= FilterType.ANNOTATION,  value={RestController.class})
        }
)
@PropertySource("${application.properties:classpath:application.properties}")
@EnableWebMvc
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class RestServletConfig extends DelegatingWebMvcConfiguration {

    @Value("${path.message.resources}")
    private String messageResourcesPath;

    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:com/silvermoongroup/fsa/web/common/MessageResources");
        messageSource.setCacheSeconds(10);
        return messageSource;
    }

    @Bean
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping(@Qualifier("mvcContentNegotiationManager") ContentNegotiationManager contentNegotiationManager, @Qualifier("mvcConversionService") FormattingConversionService conversionService, @Qualifier("mvcResourceUrlProvider") ResourceUrlProvider resourceUrlProvider) {
        RequestMappingHandlerMapping mapping = super.requestMappingHandlerMapping(contentNegotiationManager, conversionService, resourceUrlProvider);
        mapping.setRemoveSemicolonContent(false);
        return mapping;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
