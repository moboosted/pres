/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.renderer.input;

import javax.servlet.jsp.PageContext;

public interface IInputRenderer {

    final String PROPERTIES_MAP = "inputProperties";

    String getRenderedInputField(PageContext pageContext);
}
