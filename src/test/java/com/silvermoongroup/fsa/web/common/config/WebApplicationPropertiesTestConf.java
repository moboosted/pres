package com.silvermoongroup.fsa.web.common.config;

import com.silvermoongroup.fsa.web.common.property.converter.InputPropertiesConverter;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
@ComponentScan(
        basePackages = {"com.silvermoongroup.fsa.web.common"}, excludeFilters={
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value=InputPropertiesConverter.class)}
)
@PropertySource("${application.properties:classpath:application.properties}")
public class WebApplicationPropertiesTestConf {

    @Bean
    public static PropertySourcesPlaceholderConfigurer
    propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ConversionService conversionService() {
        return new DefaultConversionService();
    }
}