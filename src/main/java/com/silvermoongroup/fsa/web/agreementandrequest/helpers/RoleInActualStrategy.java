/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.helpers;

import com.silvermoongroup.businessservice.policymanagement.dto.intf.RolePlayerSupportive;
import com.silvermoongroup.businessservice.policymanagement.service.intf.IPolicyAdminService;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.fsa.web.agreementandrequest.constants.TopLevelAgreementContextEnum;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mubeen
 */
public interface RoleInActualStrategy<T extends ValidatorForm> {

    ActionForward roleInActualAddCallback(ApplicationContext applicationContext, ActionMapping actionMapping, HttpServletRequest request,
                                          HttpServletResponse response, T form);

    ActionForward roleInActualViewCallback(ActionMapping actionMapping, HttpServletRequest request,
                                           HttpServletResponse response, T form, IPolicyAdminService fsaBusinessServiceRemote,
                                           IProductDevelopmentService typeService, RolePlayerSupportive rolePlayerSupportive, TopLevelAgreementContextEnum topLevelAgreementContextEnum);
}
