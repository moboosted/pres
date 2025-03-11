/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.validator.type;

import com.silvermoongroup.base.junit.categories.UnitTests;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.Enumeration;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.EnumerationType;
import com.silvermoongroup.fsa.web.user.profile.IUserProfileManager;
import com.silvermoongroup.kindmanager.domain.Kind;
import com.silvermoongroup.kindmanager.domain.KindCategory;
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
public class CurrencyAmountPropertyValidatorTest {

    private CurrencyAmountPropertyValidator validator;
    private HttpServletRequest request;
    private HttpSession session;
    private ServletContext servletContext;
    private WebApplicationContext webApplicationContext;
    private IUserProfileManager userProfileManager;
    private IProductDevelopmentService productDevelopmentService;

    @Before
    public void setup() throws Exception {

        Kind currencyCodeKind = new Kind(1L, KindCategory.PROPERTY, "Currency Code");

        validator = new CurrencyAmountPropertyValidator();
        request = EasyMock.createMock(HttpServletRequest.class);
        session = EasyMock.createMock(HttpSession.class);
        servletContext = EasyMock.createMock(ServletContext.class);
        webApplicationContext = EasyMock.createMock(WebApplicationContext.class);
        userProfileManager = EasyMock.createMock(IUserProfileManager.class);
        productDevelopmentService = EasyMock.createMock(IProductDevelopmentService.class);

    }

    @Test
    public void testValidateEmptyOrNullCurrencyAmount() {
        Assert.assertNull(validator.validate(null, null));
        Assert.assertNull(validator.validate(null, ""));
        Assert.assertNull(validator.validate(null, "   "));
    }

    @Test
    public void testValidateWithoutCurrencyCode() {

        setupExpectationsAndReplay();
        Assert.assertEquals(CurrencyAmountPropertyValidator.MSG_CURRENCY_AMOUNT_INVALID, validator.validate(request, "0"));

        setupExpectationsAndReplay();
        Assert.assertEquals(CurrencyAmountPropertyValidator.MSG_CURRENCY_AMOUNT_INVALID, validator.validate(request, "1"));

        setupExpectationsAndReplay();
        Assert.assertEquals(CurrencyAmountPropertyValidator.MSG_CURRENCY_AMOUNT_INVALID, validator.validate(request, "11298"));

        setupExpectationsAndReplay();
        Assert.assertEquals(CurrencyAmountPropertyValidator.MSG_CURRENCY_AMOUNT_INVALID, validator.validate(request, "19287232873"));

        // with decimal point
        //
        setupExpectationsAndReplay();
        Assert.assertEquals(CurrencyAmountPropertyValidator.MSG_CURRENCY_AMOUNT_INVALID, validator.validate(request, "0.01"));

        setupExpectationsAndReplay();
        Assert.assertEquals(CurrencyAmountPropertyValidator.MSG_CURRENCY_AMOUNT_INVALID, validator.validate(request, "1.1"));

        setupExpectationsAndReplay();
        Assert.assertEquals(CurrencyAmountPropertyValidator.MSG_CURRENCY_AMOUNT_INVALID, validator.validate(request, "100.00001"));

        setupExpectationsAndReplay();
        Assert.assertEquals(CurrencyAmountPropertyValidator.MSG_CURRENCY_AMOUNT_INVALID, validator.validate(request, "1."));

        setupExpectationsAndReplay(null);
        Assert.assertEquals(
                CurrencyAmountPropertyValidator.MSG_CURRENCY_CODE_INVALID,
                validator.validate(request, "19287232873:a"));
    }

    @Test
    public void testValidateWithCurrencyCode() {

        setupExpectationsAndReplay();
        Assert.assertNull(validator.validate(request, "0:USD"));

        setupExpectationsAndReplay();
        Assert.assertNull(validator.validate(request, "1:USD"));

        setupExpectationsAndReplay(null);
        Assert.assertEquals(
                CurrencyAmountPropertyValidator.MSG_CURRENCY_CODE_INVALID,
                validator.validate(request, "19287232873:FFF"));

        // with decimal point
        //
        setupExpectationsAndReplay();
        Assert.assertNull(validator.validate(request, "0.1:USD"));

        setupExpectationsAndReplay();
        Assert.assertNull(validator.validate(request, "1.3:ZAR"));

        setupExpectationsAndReplay(null);
        Assert.assertEquals(
                CurrencyAmountPropertyValidator.MSG_CURRENCY_CODE_INVALID,
                validator.validate(request, "19287232873.01:FFF"));

        setupExpectationsAndReplay(null);
        Assert.assertEquals(
                CurrencyAmountPropertyValidator.MSG_CURRENCY_CODE_INVALID,
                validator.validate(request, "19287232873.01:a"));

        // ideally, this should fail
//        setupExpectationsAndReplay();
//        Assert.assertEquals(
//                CurrencyAmountPropertyValidator.MSG_CURRENCY_AMOUNT_INVALID,  
//                validator.validate(request, "19287232873.01:"));
    }

    private void setupExpectationsAndReplay() {
        IEnumeration enumeration = new Enumeration();
        enumeration.setId(1L);
        enumeration.setEnumerationTypeId(EnumerationType.CURRENCY_CODE.getValue());
        setupExpectationsAndReplay(enumeration);
    }

    private void setupExpectationsAndReplay(IEnumeration enumeration) {
        EasyMock.reset(
                request, session, servletContext, webApplicationContext, userProfileManager,
                productDevelopmentService
        );
        EasyMock.expect(request.getSession()).andReturn(session);
        EasyMock.expect(session.getServletContext()).andReturn(servletContext);
        EasyMock.expect(WebApplicationContextUtils.getWebApplicationContext(servletContext)).andReturn
                (webApplicationContext);
        EasyMock.expect(webApplicationContext.getBean(IUserProfileManager.class)).andReturn(userProfileManager);
        EasyMock.expect(webApplicationContext.getBean(IProductDevelopmentService.class)).andReturn
                (productDevelopmentService);

        String numberFormat = "#,##0.00";
        EasyMock.expect(userProfileManager.getNumberFormat()).andReturn(numberFormat);
        EasyMock.expect(userProfileManager.getDecimalSeparator()).andReturn('.');
        EasyMock.expect(userProfileManager.getGroupingSeparator()).andReturn(',');
        EasyMock.expect(userProfileManager.getNumberFormat()).andReturn(numberFormat);


        EasyMock.expect(productDevelopmentService.findEnumerationByNameAndType(
                                EasyMock.anyObject(
                                        ApplicationContext
                                                .class
                                ), EasyMock.anyObject(String.class),
                                EasyMock.anyLong()
                        )).andReturn(enumeration);

        EasyMock.replay(request, session, servletContext, webApplicationContext, userProfileManager,
                productDevelopmentService);
    }
}
