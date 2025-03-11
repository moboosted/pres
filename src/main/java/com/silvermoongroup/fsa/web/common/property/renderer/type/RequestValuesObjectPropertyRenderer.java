/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.renderer.type;

import com.silvermoongroup.businessservice.customerrelationshipmanagement.service.intf.ICustomerRelationshipService;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.constants.Components;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.property.renderer.AbstractPropertyRenderer;
import com.silvermoongroup.fsa.web.party.util.PartyNameUtil;
import com.silvermoongroup.party.domain.Party;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.PageContext;

/**
 * Created by Jordan Olsen on 2017/06/09.
 */
public class RequestValuesObjectPropertyRenderer extends AbstractPropertyRenderer {

    public RequestValuesObjectPropertyRenderer() {
    }

    @Override
    protected String formatPropertyValue(PageContext pageContext, Object propertyValue) {
        if (propertyValue == null) {
            return StringUtils.EMPTY;
        }

        if (propertyValue instanceof ObjectReference) {
            ObjectReference objectReference = (ObjectReference) propertyValue;

            if (Components.PARTY.equals(objectReference.getComponentId())) {
                ICustomerRelationshipService partyManager = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext())
                        .getBean(ICustomerRelationshipService.class);
                IProductDevelopmentService productDevelopmentService = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext())
                        .getBean(IProductDevelopmentService.class);

                Party party = partyManager.getPartyForPartyOrPartyRole(new ApplicationContext(),
                        (ObjectReference) propertyValue, new String[]{"partyNames"});
                return PartyNameUtil.getPartyFullName(party.getDefaultName(), productDevelopmentService);
            }
        }

        return propertyValue.toString();
    }
}
