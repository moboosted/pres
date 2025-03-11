/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.helpers;

import com.silvermoongroup.businessservice.policymanagement.dto.KindDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.OnlyActualDTO;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.agreementandrequest.form.RequestViewForm;
import org.springframework.stereotype.Component;

@Component("RequestStrategy")
public class RequestRoleInActualStrategy extends OnlyActualRoleInActualStrategy<RequestViewForm> {

    @Override
    protected void addEmptyRoleToOnlyActual(String path, ObjectReference contextObjectReference, KindDTO roleKindDTO) {
        getPolicyAdminService().addEmptyRoleToOnlyActual(new ApplicationContext(), contextObjectReference, roleKindDTO,
                Boolean.FALSE, path);
    }

    @Override
    protected OnlyActualDTO getOnlyActualView(RequestViewForm form) {
        return getPolicyAdminService().getRequestView(new ApplicationContext(),
                form.getContextObjectReference());
    }

    @Override
    public String getCallBackPath(String context) {
        return "/secure/request.do";
    }

    @Override
    public KindDTO getRoleKind(RequestViewForm form) {
        return KindDTO.convertFromString(form.getKind());
    }

    @Override
    public String getRoleUniqueIdentifier(RequestViewForm form) {
        return null;
    }

}
