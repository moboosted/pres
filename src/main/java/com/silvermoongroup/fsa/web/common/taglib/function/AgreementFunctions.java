/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib.function;

import com.silvermoongroup.businessservice.policymanagement.dto.AgreementDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.TopLevelAgreementDTO;

import javax.servlet.jsp.PageContext;

public class AgreementFunctions {

    public static boolean isTopLevelAgreement(PageContext pageContext, AgreementDTO agreementDTO) {
        return (agreementDTO instanceof TopLevelAgreementDTO);
    }
}