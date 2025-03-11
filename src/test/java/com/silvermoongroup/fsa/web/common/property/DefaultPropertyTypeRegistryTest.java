/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property;

import com.silvermoongroup.base.junit.categories.UnitTests;
import com.silvermoongroup.spflite.enumeration.ConformanceType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author Justin Walsh
 */
@Category(UnitTests.class)
public class DefaultPropertyTypeRegistryTest {
    
    private DefaultPropertyTypeRegistry propertyTypeRegistry;
    
    @Before
    public void setUp() {
        propertyTypeRegistry = new DefaultPropertyTypeRegistry();
    }
    
    @Test
    public void testThatDefaultConvertersExist() {
        
        Assert.assertEquals(13, propertyTypeRegistry.getConverters().size());
        
        Assert.assertNotNull(propertyTypeRegistry.getConverterForType(ConformanceType.AMOUNT.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getConverterForType(ConformanceType.BIGDECIMAL.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getConverterForType(ConformanceType.BOOLEAN.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getConverterForType(ConformanceType.CURRENCYAMOUNT.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getConverterForType(ConformanceType.DATE.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getConverterForType(ConformanceType.DOUBLE.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getConverterForType(ConformanceType.INTEGER.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getConverterForType(ConformanceType.LONG.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getConverterForType(ConformanceType.OBJECTREFERENCE.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getConverterForType(ConformanceType.PERCENTAGE.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getConverterForType(ConformanceType.STRING.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getConverterForType(ConformanceType.TIMESTAMP.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getConverterForType(ConformanceType.IREQUEST_VALUES_OBJECT.getName()));
    }
    
    @Test
    public void testThatDefaultRenderersExist() {
        
        Assert.assertEquals(13, propertyTypeRegistry.getRenderers().size());
        
        Assert.assertNotNull(propertyTypeRegistry.getRendererForType(ConformanceType.AMOUNT.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getRendererForType(ConformanceType.BIGDECIMAL.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getRendererForType(ConformanceType.BOOLEAN.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getRendererForType(ConformanceType.CURRENCYAMOUNT.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getRendererForType(ConformanceType.DATE.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getRendererForType(ConformanceType.DOUBLE.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getRendererForType(ConformanceType.INTEGER.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getRendererForType(ConformanceType.LONG.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getRendererForType(ConformanceType.OBJECTREFERENCE.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getRendererForType(ConformanceType.PERCENTAGE.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getRendererForType(ConformanceType.STRING.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getRendererForType(ConformanceType.TIMESTAMP.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getRendererForType(ConformanceType.IREQUEST_VALUES_OBJECT.getName()));
    } 
    
    @Test
    public void testThatDefaultValidatorsExist() {
        
        Assert.assertEquals(14, propertyTypeRegistry.getValidators().size());
        
        Assert.assertNotNull(propertyTypeRegistry.getValidatorForType(ConformanceType.AMOUNT.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getValidatorForType(ConformanceType.BIGDECIMAL.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getValidatorForType(ConformanceType.BOOLEAN.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getValidatorForType(ConformanceType.CURRENCYAMOUNT.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getValidatorForType(ConformanceType.DATE.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getValidatorForType(ConformanceType.DOUBLE.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getValidatorForType(ConformanceType.INTEGER.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getValidatorForType(ConformanceType.LONG.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getValidatorForType(ConformanceType.OBJECTREFERENCE.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getValidatorForType(ConformanceType.PERCENTAGE.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getValidatorForType(ConformanceType.STRING.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getValidatorForType(ConformanceType.TIMESTAMP.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getValidatorForType(ConformanceType.OBJECT.getName()));
        Assert.assertNotNull(propertyTypeRegistry.getValidatorForType(ConformanceType.IREQUEST_VALUES_OBJECT.getName()));
    }

}
