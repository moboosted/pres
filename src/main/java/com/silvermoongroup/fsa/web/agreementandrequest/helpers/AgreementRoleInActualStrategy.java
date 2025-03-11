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
import com.silvermoongroup.fsa.web.agreementandrequest.constants.TopLevelAgreementContextEnum;
import com.silvermoongroup.fsa.web.agreementandrequest.form.AgreementForm;
import org.springframework.stereotype.Component;

/**
 * Dispatches the user to the correct place when roles are viewed/edited.
 */
@Component
public class AgreementRoleInActualStrategy extends OnlyActualRoleInActualStrategy<AgreementForm> {

    @Override
    protected void addEmptyRoleToOnlyActual(String path, ObjectReference contextObjectReference, KindDTO roleKindDTO) {
        getPolicyAdminService().addEmptyRoleToOnlyActual(new ApplicationContext(), contextObjectReference, roleKindDTO,
                Boolean.TRUE, path);
    }

    @Override
    protected OnlyActualDTO getOnlyActualView(AgreementForm form) {
        return getPolicyAdminService().getAgreementView(new ApplicationContext(),
                form.getContextObjectReference(), form.getPath());

    }

    @Override
    public String getCallBackPath(String context) {

        TopLevelAgreementContextEnum tlaContext = null;
        if (context != null) {
            tlaContext = TopLevelAgreementContextEnum.valueOf(context);
        }

        if (TopLevelAgreementContextEnum.AGREEMENT.equals(tlaContext)) {
            return "/secure/agreementversion/tla.do";
        }

        return "/secure/request/tla.do";
    }

    @Override
    public KindDTO getRoleKind(AgreementForm form) {
        return KindDTO.convertFromString(form.getKind());
    }

    @Override
    public String getRoleUniqueIdentifier(AgreementForm form) {
        return null;
    }


}
