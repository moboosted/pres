/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property;

import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.enumeration.intf.ICurrencyCode;


/**
 * Identifies a classification of <code>BusinessModelObject</code>s attributes according to their currency selection.
 */

public enum CurrencyAmountPropertyTestEnum implements ICurrencyCode {

    ZAR("ZAR", 1L),
    USD("USD", 2L),
    EUR("EUR", 3L);

    private Long code;
    private String name;

    CurrencyAmountPropertyTestEnum(String name, Long code) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Long getCode() {
        return code;
    }

    @Override
    public Long getEnumerationTypeId() {
        return enumerationType.getValue();
    }

    @Override
    public EnumerationReference getEnumerationReference() {
        return new EnumerationReference(getCode(), getEnumerationTypeId());
    }

    @Override
    public String getName() {
        return this.name;
    }

}
