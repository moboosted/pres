/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.action;

import com.silvermoongroup.businessservice.customerrelationshipmanagement.service.intf.ICustomerRelationshipService;
import com.silvermoongroup.businessservice.policymanagement.service.intf.IPolicyAdminService;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.EnumerationType;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import com.silvermoongroup.fsa.web.party.form.PartyForm;
import com.silvermoongroup.fsa.web.party.vo.BankDetailVO;
import com.silvermoongroup.party.domain.*;
import com.silvermoongroup.spflite.dto.BasicAgreementData;
import org.apache.commons.validator.GenericValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class BankActionHelper {

    private IPolicyAdminService fsaBusinessService;
    private IProductDevelopmentService productDevelopmentService;
    private ApplicationContext applicationContext;

    public BankActionHelper(IPolicyAdminService contractService, IProductDevelopmentService productDevelopmentService,
            ApplicationContext applicationContext) {
        this.fsaBusinessService = contractService;
        this.productDevelopmentService = productDevelopmentService;
        this.applicationContext = applicationContext;
    }

    /**
     * Will only show the effective BankDetail.
     */
    public Collection<BankDetailVO> retrieveBankDetailsForRolePlayers(HttpServletRequest httpServletRequest,
            Collection<RolePlayer> rolePlayers, BankDetailVO financialAccountDetail) {
        BankDetailVO createBankDetail = null;
        Collection<BankDetailVO> bankDetails = new ArrayList<>();

        for (RolePlayer rolePlayer : rolePlayers) {
            for (ContactPreference contactPreference : rolePlayer.getContactPreferences()) {
                for (ContactPoint contactPoint : contactPreference.getContactPoints()) {
                    if (FinancialAccountDetail.class.isAssignableFrom(contactPoint.getClass())) {
                        FinancialAccountDetail detail = (FinancialAccountDetail) contactPoint;
                        if (detail.getAccountNumber().equals(financialAccountDetail.getAccountNumber())) {
                            createBankDetail = createBankDetail(httpServletRequest, rolePlayer, contactPreference, detail);
                            if (createBankDetail != null) {
                                bankDetails.add(createBankDetail);
                            }
                        }
                    }
                }
            }
        }
        return bankDetails;

    }

    public Collection<BankDetailVO> retrieveBankDetailsForRolePlayer(ApplicationContext applicationContext, ICustomerRelationshipService customerRelationshipService,
            HttpServletRequest httpServletRequest, RolePlayer rolePlayer) {

        Collection<ContactPreference> contactPreferences =
                customerRelationshipService.findContactPreferencesAndPoints(applicationContext, rolePlayer.getObjectReference());

        Collection<BankDetailVO> bankDetails = new ArrayList<>();
        for (ContactPreference contactPreference : contactPreferences) {
            for (ContactPoint contactPoint : contactPreference.getContactPoints()) {

                if (FinancialAccountDetail.class.isAssignableFrom(contactPoint.getClass())) {
                    FinancialAccountDetail detail = (FinancialAccountDetail) contactPoint;
                    BankDetailVO createBankDetail = createBankDetail(httpServletRequest, rolePlayer, contactPreference, detail);
                    if (createBankDetail != null) {
                        bankDetails.add(createBankDetail);
                    }
                }

            }
        }
        return bankDetails;

    }

    private BankDetailVO createBankDetail(HttpServletRequest httpServletRequest, RolePlayer rolePlayer,
            ContactPreference contactPreference, FinancialAccountDetail detail) {
        BankDetailVO createBankDetail = null;
        Date effectiveFrom = detail.getEffectivePeriod().getStart();
        Date effectiveTo = detail.getEffectivePeriod().getEnd();
        if ((effectiveFrom == null || effectiveFrom.isBeforeOrEqual(Date.today()))
                && (effectiveTo == null || effectiveTo.isAfter(Date.today()))) {
            createBankDetail = new BankDetailVO();
            createBankDetail.setPreference(FormatUtil.getTypeFormatter(httpServletRequest).formatType(contactPreference.getTypeId()));
            createBankDetail.setPartyRole(FormatUtil.getTypeFormatter(httpServletRequest).formatType(rolePlayer.getTypeId()));
            createBankDetail.setRolePlayer(rolePlayer);
            createBankDetail.setId(detail.getId());
            createBankDetail.setTypeId(detail.getTypeId());
            createBankDetail.setKey(UUID.randomUUID().toString());
            createBankDetail.setAccountNumber(detail.getAccountNumber());
            createBankDetail.setAccountHolder(detail.getAccountHolderName());
            IEnumeration accountTypeEnumeration = productDevelopmentService.getEnumeration(new ApplicationContext(),
                    detail.getAccountType());
            createBankDetail.setAccountType(accountTypeEnumeration.getName());
            createBankDetail.setStartDate(detail.getEffectivePeriod().getStart());
            createBankDetail.setEndDate(detail.getEffectivePeriod().getEnd());
            if (detail.equals(contactPreference.getDefaultContactPoint())) {
                createBankDetail.setDef(true);
            }
            if (detail.getExpiryDate() != null) {
                String expDateFirstTwo = detail.getExpiryDate().getYear();
                String expDateLastTwo = detail.getExpiryDate().getMonth();
                String expDateFrmtd = expDateFirstTwo + "/" + expDateLastTwo;
                createBankDetail.setExpirtyDate(expDateFrmtd);
            }
            if (PartyRole.class.isAssignableFrom(rolePlayer.getClass())) {
                ObjectReference context = ((PartyRole) rolePlayer).getContext();
                if (context != null) {
                    BasicAgreementData basicAgreementData = fsaBusinessService.getBasicAgreementDataForVersionedAgreement(
                            new ApplicationContext(), context);
                    createBankDetail.setContext(context);
                    createBankDetail.setContextReference(basicAgreementData.getExternalReference());
                }

            }
            if (!(null==detail.getBankName())) {
                IEnumeration bankEnum = productDevelopmentService.getEnumeration(new ApplicationContext(),
                    detail.getBankName());
                createBankDetail.setBank(bankEnum.getName());
            }

            createBankDetail.setBankBranchReference(detail.getBankBranchReference());
            createBankDetail.setBankBranchCode(detail.getBankBranchCode());
            createBankDetail.setBankBranchName(detail.getBankBranchName());
        }
        return createBankDetail;
    }

    public String getAccountNumberForEdit(PartyForm form, Collection<BankDetailVO> bankDetail) {
        String accountNumber = null;
        for (BankDetailVO bankDetailVO : bankDetail) {
            if (bankDetailVO.getKey().equals(form.getKey())) {
                accountNumber = bankDetailVO.getAccountNumber();
            }
        }
        return accountNumber;
    }

    public PartyForm populateBankDetailFieldsForEdit(HttpServletRequest request, PartyForm form, Collection<BankDetailVO> bankDetail)
            throws Exception {
        for (BankDetailVO bankDetailVO : bankDetail) {
            if (bankDetailVO.getKey().equals(form.getKey())) {

                // what is fadSelected ???
                form.accountId = bankDetailVO.getId();
                form.accountTypeId = bankDetailVO.getTypeId();

                Type preferenceType = productDevelopmentService
                        .getType(applicationContext, bankDetailVO.getPreference());

                if (preferenceType != null) {
                    form.setBankContactPreferenceType(preferenceType.getId().intValue());
                }

                form.setAccountNumber(bankDetailVO.getAccountNumber());
                form.setAccountHolder(bankDetailVO.getAccountHolder());
                EnumerationReference accountTypeEnumRef =
                        productDevelopmentService
                                .findEnumerationByNameAndType(applicationContext, bankDetailVO.getAccountType(),
                                                              EnumerationType.BANK_ACCOUNT_TYPE.getValue()
                                ).getEnumerationReference();
                form.setAccountType(accountTypeEnumRef.toString());

                EnumerationReference bankNameRef =
                        productDevelopmentService
                                .findEnumerationByNameAndType(applicationContext, bankDetailVO.getBank(),
                                                              EnumerationType.BANKING_INSTITUTION.getValue()
                                ).getEnumerationReference();
                form.setBank(bankNameRef.toString());
                form.setBankBranchReference(bankDetailVO.getBankBranchReference());
                form.setBankBranchCode(bankDetailVO.getBankBranchCode());
                form.setBankBranchName(bankDetailVO.getBankBranchName());

                if (!GenericValidator.isBlankOrNull(bankDetailVO.getExpirtyDate())) {
                    String expDateFmtd = bankDetailVO.getExpirtyDate().replaceAll("/", "");
                    form.setCrCardExpiryDate(expDateFmtd);
                }
                if (bankDetailVO.getStartDate() != null) {
                    form.setEffectiveFromDate(FormatUtil.getTypeFormatter(request).formatDate(bankDetailVO.getStartDate()));
                }
                if (bankDetailVO.getEndDate() != null) {
                    form.setEffectiveToDate(FormatUtil.getTypeFormatter(request).formatDate(bankDetailVO.getEndDate()));
                }
                break;
            }
        }
        return form;
    }

}
