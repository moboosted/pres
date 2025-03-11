/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property;

import java.util.HashMap;
import java.util.Map;

import com.silvermoongroup.fsa.web.common.property.converter.type.*;
import com.silvermoongroup.fsa.web.common.property.renderer.type.*;
import com.silvermoongroup.fsa.web.common.property.validator.type.*;
import org.springframework.stereotype.Component;

import com.silvermoongroup.fsa.web.common.property.converter.IPropertyConverter;
import com.silvermoongroup.fsa.web.common.property.renderer.IPropertyRenderer;
import com.silvermoongroup.fsa.web.common.property.validator.IPropertyValidator;
import com.silvermoongroup.spflite.enumeration.ConformanceType;


/**
 * An implementation of a property type registry providing a default set of
 * renderers, validators and converters.
 * 
 * <p>Clients can override the the the set of renderers, validator or converters
 * if required.
 * 
 * @author Justin Walsh
 */
@Component
public class DefaultPropertyTypeRegistry implements IPropertyTypeRegistry {

    private Map<String, IPropertyRenderer> renderers =
            new HashMap<>();

    private Map<String, IPropertyValidator> validators =
            new HashMap<>();

    private Map<String, IPropertyConverter> converters =
            new HashMap<>();

    /**
     * Default constructor
     */
    public DefaultPropertyTypeRegistry() {
        registerDefaultTypes();
    }

    public Map<String, IPropertyRenderer> getRenderers() {
        return renderers;
    }

    public Map<String, IPropertyValidator> getValidators() {
        return validators;
    }

    public Map<String, IPropertyConverter> getConverters() {
        return converters;
    }

    /**
     * @param type The property type.
     * @return The property renderer for the type, or null if one is not
     * configured.
     */
    @Override
    public IPropertyRenderer getRendererForType(String type) {
        return getRenderers().get(type);
    }

    /**
     * @param type The property type.
     * @return The property converter for the type, or null if one is not
     * configured.
     */
    @Override
    public IPropertyConverter getConverterForType(String type) {
        return getConverters().get(type);
    }

    /**
     * @param type The property type.
     * @return The property converter for the type, or null if one is not
     * configured.
     */
    @Override
    public IPropertyValidator getValidatorForType(String type) {
        return getValidators().get(type);
    }

    /**
     * Register the set of default types and their corresponding
     * converters, renderers and validators
     */
    private void registerDefaultTypes() {

        // converters
        //
        getConverters().put(ConformanceType.AMOUNT.getName(), new AmountPropertyConverter());
        getConverters().put(ConformanceType.BIGDECIMAL.getName(), new BigDecimalPropertyConverter());
        getConverters().put(ConformanceType.BOOLEAN.getName(), new BooleanPropertyConverter());
        getConverters().put(ConformanceType.CURRENCYAMOUNT.getName(), new CurrencyAmountPropertyConverter());
        getConverters().put(ConformanceType.DATE.getName(), new DatePropertyConverter());
        getConverters().put(ConformanceType.DOUBLE.getName(), new DoublePropertyConverter());
        getConverters().put(ConformanceType.INTEGER.getName(), new IntegerPropertyConverter());
        getConverters().put(ConformanceType.LONG.getName(), new LongPropertyConverter());
        getConverters().put(ConformanceType.OBJECTREFERENCE.getName(), new ObjectReferencePropertyConverter());
        getConverters().put(ConformanceType.PERCENTAGE.getName(), new PercentagePropertyConverter());
        getConverters().put(ConformanceType.STRING.getName(), new StringPropertyConverter());
        getConverters().put(ConformanceType.TIMESTAMP.getName(), new TimestampPropertyConverter());
        getConverters().put(ConformanceType.TYPE.getName(), new TypePropertyConverter());
        getConverters().put(ConformanceType.ENUMERATION.getName(), new EnumerationPropertyConverter());
        getConverters().put(ConformanceType.CATEGORY.getName(), new CategoryPropertyConverter());
        getConverters().put(ConformanceType.IREQUEST_VALUES_OBJECT.getName(), new IRequestValuesObjectPropertyConverter());

        // renderers
        //
        getRenderers().put(ConformanceType.AMOUNT.getName(), new AmountPropertyRenderer());
        getRenderers().put(ConformanceType.BIGDECIMAL.getName(), new BigDecimalPropertyRenderer());
        getRenderers().put(ConformanceType.BOOLEAN.getName(), new BooleanPropertyRenderer());
        getRenderers().put(ConformanceType.CURRENCYAMOUNT.getName(), new CurrencyAmountPropertyRenderer());
        getRenderers().put(ConformanceType.DATE.getName(), new DatePropertyRenderer());
        getRenderers().put(ConformanceType.DOUBLE.getName(), new DoublePropertyRenderer());
        getRenderers().put(ConformanceType.INTEGER.getName(), new IntegerPropertyRenderer());
        getRenderers().put(ConformanceType.LONG.getName(), new LongPropertyRenderer());
        getRenderers().put(ConformanceType.OBJECTREFERENCE.getName(), new ObjectReferencePropertyRenderer());
        getRenderers().put(ConformanceType.PERCENTAGE.getName(), new PercentagePropertyRenderer());
        getRenderers().put(ConformanceType.STRING.getName(), new StringPropertyRenderer());
        getRenderers().put(ConformanceType.TIMESTAMP.getName(), new TimestampPropertyRenderer());
        getRenderers().put(ConformanceType.TYPE.getName(), new TypePropertyRenderer());
        getRenderers().put(ConformanceType.ENUMERATION.getName(), new EnumerationPropertyRenderer());
        getRenderers().put(ConformanceType.CATEGORY.getName(), new CategoryPropertyRenderer());
        getRenderers().put(ConformanceType.IREQUEST_VALUES_OBJECT.getName(), new RequestValuesObjectPropertyRenderer());

        // validators
        //
        getValidators().put(ConformanceType.AMOUNT.getName(), new AmountPropertyValidator());
        getValidators().put(ConformanceType.BIGDECIMAL.getName(), new BigDecimalPropertyValidator());
        getValidators().put(ConformanceType.BOOLEAN.getName(), new BooleanPropertyValidator());
        getValidators().put(ConformanceType.CURRENCYAMOUNT.getName(), new CurrencyAmountPropertyValidator());
        getValidators().put(ConformanceType.DATE.getName(), new DatePropertyValidator());
        getValidators().put(ConformanceType.DOUBLE.getName(), new DoublePropertyValidator());
        getValidators().put(ConformanceType.INTEGER.getName(), new IntegerPropertyValidator());
        getValidators().put(ConformanceType.LONG.getName(), new LongPropertyValidator());
        getValidators().put(ConformanceType.OBJECTREFERENCE.getName(), new ObjectReferencePropertyValidator());
        getValidators().put(ConformanceType.PERCENTAGE.getName(), new PercentagePropertyValidator());
        getValidators().put(ConformanceType.STRING.getName(), new StringPropertyValidator());
        getValidators().put(ConformanceType.TIMESTAMP.getName(), new TimestampPropertyValidator());
        getValidators().put(ConformanceType.OBJECT.getName(), new ObjectPropertyValidator());
        getValidators().put(ConformanceType.TYPE.getName(), new TypePropertyValidator());
        getValidators().put(ConformanceType.ENUMERATION.getName(), new EnumerationPropertyValidator());
        getValidators().put(ConformanceType.CATEGORY.getName(), new CategoryPropertyValidator());
        getValidators().put(ConformanceType.IREQUEST_VALUES_OBJECT.getName(), new IRequestValuesObjectPropertyValidator());
    }
}
