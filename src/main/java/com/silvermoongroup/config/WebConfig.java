/**
 **  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silvermoongroup.common.domain.ApplicationContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Locale;

/**
 * Only care about dependencies of the Dispatcher servlet as all other should com from the Root ApplicationContext (ApplicationConfig.class)
 */
@Configuration
public class WebConfig {

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
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public static ApplicationContext applicationContext() {
        return new ApplicationContext();
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Bean
    public CookieLocaleResolver cookieLocaleResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return cookieLocaleResolver;
    }

    @Bean
    public RequestMappingHandlerMapping defaultAnnotationHandlerMapping() {
        RequestMappingHandlerMapping defaultAnnotationHandlerMapping = new RequestMappingHandlerMapping();
        defaultAnnotationHandlerMapping.setInterceptors(new Object[]{localeChangeInterceptor()});
        return defaultAnnotationHandlerMapping;
    }

}
