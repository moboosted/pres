package com.silvermoongroup.fsa.web.common.property.renderer;

import com.silvermoongroup.businessservice.policymanagement.dto.PropertyDTO;

import javax.servlet.jsp.PageContext;

public interface IPropertyRenderer {

    void renderEditablePropertyValue(PageContext pageContext,
            PropertyDTO propertyDTO, String inputValue, String errorMessage);

    void renderReadOnlyPropertyValue(PageContext pageContext,
            PropertyDTO propertyDTO, String inputValue);

    void renderReadOnlyPropertyName(PageContext pageContext,
            PropertyDTO propertyDTO, String inputValue);
}
