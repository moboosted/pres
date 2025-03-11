/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property;

import com.silvermoongroup.fsa.web.common.property.converter.IPropertyConverter;
import com.silvermoongroup.fsa.web.common.property.renderer.IPropertyRenderer;
import com.silvermoongroup.fsa.web.common.property.validator.IPropertyValidator;

/**
 * A registry of {@link IPropertyRenderer}s, {@link IPropertyValidator}s and
 * {@link IPropertyConverter} for a set of property conformance types.
 * 
 * @author Justin Walsh
 *
 */
public interface IPropertyTypeRegistry {

    /**
     * @param type The property type.
     * @return The property renderer for the type, or null if one is not
     * configured.
     */
    public IPropertyRenderer getRendererForType(String type);

    /**
     * @param type The property type.
     * @return The property converter for the type, or null if one is not
     * configured.
     */
    public IPropertyConverter getConverterForType(String type);

    /**
     * @param type The property type.
     * @return The property converter for the type, or null if one is not
     * configured.
     */
    public IPropertyValidator getValidatorForType(String type);

}
