/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.converter.type;

import com.silvermoongroup.base.exception.ApplicationRuntimeException;
import com.silvermoongroup.base.junit.categories.UnitTests;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.datatype.CurrencyAmount;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.Enumeration;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.EnumerationType;
import com.silvermoongroup.common.ext.enumeration.CurrencyCodeExt;
import com.silvermoongroup.fsa.web.locale.ITypeParser;
import com.silvermoongroup.fsa.web.locale.TypeParser;
import com.silvermoongroup.fsa.web.user.profile.IUserProfileManager;
import com.silvermoongroup.kindmanager.domain.Kind;
import com.silvermoongroup.kindmanager.domain.KindCategory;
import com.silvermoongroup.spflite.specframework.domain.versionedproduct.ProductVersion;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Justin Walsh
 */
@Category(UnitTests.class)
public class CurrencyAmountPropertyConverterTest {

    private CurrencyAmountPropertyConverter converter;
    private HttpSession session;
    private HttpServletRequest request;
    private ServletContext servletContext;
    private WebApplicationContext webApplicationContext;
    private ITypeParser typeParser;
    private IUserProfileManager userProfileManager;
    private IProductDevelopmentService productDevelopmentService;
    private ProductVersion mpv;


    @Before
    public void setup() throws Exception {

        Kind currencyCodeKind = new Kind(1L, KindCategory.PROPERTY, "Currency Code");

        converter = new CurrencyAmountPropertyConverter();
        request = EasyMock.createMock(HttpServletRequest.class);
        session = EasyMock.createMock(HttpSession.class);
        servletContext = EasyMock.createMock(ServletContext.class);
        webApplicationContext = EasyMock.createMock(WebApplicationContext.class);
        userProfileManager = EasyMock.createMock(IUserProfileManager.class);
        productDevelopmentService = EasyMock.createMock(IProductDevelopmentService.class);

        typeParser = new TypeParser();
        ((TypeParser) typeParser).setUserProfileManager(userProfileManager);

    }

    @Test
    public void testConvertEmptyCurrencyAmount() {
        Assert.assertNull(converter.convertFromString(request, null));
        Assert.assertNull(converter.convertFromString(request, ""));
        Assert.assertNull(converter.convertFromString(request, " "));
    }

    @Test
    public void testInvalidCurrencyAmount() {
        try {
            setupExpectationsAndReplay();
            converter.convertFromString(request, "USD:100");
            Assert.fail("Expecting IllegalStateException for invalid currency amount");
        } catch (ApplicationRuntimeException ex) {
            // pass
        }

        try {
            setupExpectationsAndReplay();
            converter.convertFromString(request, "A.100");
            Assert.fail("Expecting IllegalStateException for invalid currency amount");
        } catch (IllegalStateException ex) {
            // pass
        }
    }

    @Test
    public void testConvertCurrencyAmountWithCode() {
        setupExpectationsAndReplay();
        CurrencyAmount currencyAmount = (CurrencyAmount) converter.convertFromString(request, "1:USD");
        Assert.assertNotNull(currencyAmount);

        Assert.assertEquals(CurrencyCodeExt.USD.getEnumerationReference(), currencyAmount.getCurrencyCode());
        Assert.assertEquals("1.00", currencyAmount.getAmount().toPlainString());

        setupExpectationsAndReplay();
        currencyAmount = (CurrencyAmount) converter.convertFromString(request, "1.01:USD");
        Assert.assertNotNull(currencyAmount);
        Assert.assertEquals(CurrencyCodeExt.USD.getEnumerationReference(), currencyAmount.getCurrencyCode());
        Assert.assertEquals("1.01", currencyAmount.getAmount().toPlainString());

        setupExpectationsAndReplay();
        currencyAmount = (CurrencyAmount) converter.convertFromString(request, "87842.01:USD");
        Assert.assertNotNull(currencyAmount);
        Assert.assertEquals(CurrencyCodeExt.USD.getEnumerationReference(), currencyAmount.getCurrencyCode());
        Assert.assertEquals("87842.01", currencyAmount.getAmount().toPlainString());

        setupExpectationsAndReplay();
        // another currency code
        currencyAmount = (CurrencyAmount) converter.convertFromString(request, "483.197:EUR");
        Assert.assertNotNull(currencyAmount);
        Assert.assertEquals(CurrencyCodeExt.USD.getEnumerationReference(), currencyAmount.getCurrencyCode());
        Assert.assertEquals("483.20", currencyAmount.getAmount().toPlainString());
    }

    @Test
    public void testConvertCurrencyAmountWithoutCode() {
        setupExpectationsAndReplay();
        try {
            converter.convertFromString(request, "1");
            Assert.fail();
        } catch (IllegalStateException ignored) {
        }


        setupExpectationsAndReplay();
        try {
            converter.convertFromString(request, "1.001");
            Assert.fail();
        } catch (IllegalStateException ignored) {
        }

        setupExpectationsAndReplay();
        try {
            converter.convertFromString(request, "87842.001");
            Assert.fail();
        } catch (IllegalStateException ignored) {
        }
    }

    private void setupExpectationsAndReplay() {
        EasyMock.reset(request, session, servletContext, webApplicationContext, userProfileManager, productDevelopmentService);
        EasyMock.expect(request.getSession()).andReturn(session).anyTimes();
        EasyMock.expect(session.getServletContext()).andReturn(servletContext).anyTimes();
        EasyMock.expect(WebApplicationContextUtils.getWebApplicationContext(servletContext)).andReturn(webApplicationContext).anyTimes();
        EasyMock.expect(webApplicationContext.getBean(ITypeParser.class)).andReturn(typeParser);
        EasyMock.expect(webApplicationContext.getBean(IProductDevelopmentService.class)).andReturn(productDevelopmentService);

        EasyMock.expect(userProfileManager.getDecimalSeparator()).andReturn('.');
        EasyMock.expect(userProfileManager.getGroupingSeparator()).andReturn(',');
        String numberFormat = "#,##0.00";
        EasyMock.expect(userProfileManager.getNumberFormat()).andReturn(numberFormat);

        IEnumeration enumeration = new Enumeration();
        enumeration.setId(1L);
        enumeration.setEnumerationTypeId(EnumerationType.CURRENCY_CODE.getValue());
        EasyMock.expect(productDevelopmentService.findEnumerationByNameAndType(EasyMock.anyObject(ApplicationContext
                        .class), EasyMock.anyObject(String.class),
                EasyMock.anyLong())).andReturn(enumeration);

        EasyMock.replay(request, session, servletContext, webApplicationContext, userProfileManager, productDevelopmentService);
    }

}
