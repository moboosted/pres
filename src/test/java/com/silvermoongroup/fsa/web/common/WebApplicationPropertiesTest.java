package com.silvermoongroup.fsa.web.common;

import com.silvermoongroup.base.junit.categories.UnitTests;
import com.silvermoongroup.fsa.web.common.config.WebApplicationPropertiesTestConf;
import org.apache.commons.lang3.LocaleUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WebApplicationPropertiesTestConf.class)
@Category(UnitTests.class)
public class WebApplicationPropertiesTest {

    @Autowired
    public WebApplicationProperties properties;

    @Test
    public void testProperties() {
        assertNotNull(properties.getSupportedLocales());
        assertTrue(properties.getSupportedLocales().contains(Locale.ENGLISH));
        assertTrue(properties.getSupportedLocales().contains(Locale.FRENCH));
        assertTrue(properties.getSupportedLocales().contains(Locale.SIMPLIFIED_CHINESE));
        assertTrue(properties.getSupportedLocales().contains(LocaleUtils.toLocale("jp")));
        assertNotNull(properties.getSupportedDateFormats());
        assertNotNull(properties.getDefaultLocale());
        assertNotNull(properties.getDefaultDateFormat());
        assertNotNull(properties.getDefaultNumberFormat());
        assertNotNull(properties.getLogoutUrl());
        assertTrue(properties.displayRolePropertiesOnCallBack(5004L));
        assertTrue(properties.displayRolePropertiesOnCallBack(5019L));
        assertFalse(properties.displayRolePropertiesOnCallBack(1L));
    }

}