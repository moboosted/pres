/**
 **  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.agreementandrequest.helpers;

import com.silvermoongroup.businessservice.customerrelationshipmanagement.dto.Document;
import com.silvermoongroup.businessservice.customerrelationshipmanagement.service.intf.ICustomerRelationshipService;
import com.silvermoongroup.businessservice.policymanagement.dto.*;
import com.silvermoongroup.businessservice.policymanagement.dto.intf.RolePlayerSupportive;
import com.silvermoongroup.businessservice.policymanagement.enumeration.ElementType;
import com.silvermoongroup.businessservice.policymanagement.service.intf.IPolicyAdminService;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.BusinessModelObject;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.intf.ICategoryScheme;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.agreementandrequest.constants.TopLevelAgreementContextEnum;
import com.silvermoongroup.fsa.web.agreementandrequest.form.AbstractViewForm;
import com.silvermoongroup.fsa.web.agreementandrequest.util.PathTraversalUtil;
import com.silvermoongroup.fsa.web.common.callback.CallBackAction;
import com.silvermoongroup.fsa.web.dto.ActionHelper;
import com.silvermoongroup.kindmanager.domain.KindCategory;
import org.apache.commons.lang3.Validate;
import org.apache.struts.Globals;
import org.apache.struts.action.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class OnlyActualRoleInActualStrategy<T extends AbstractViewForm<?>> extends
        AbstractRoleInActualStrategy<T> {

    @Autowired
    protected IProductDevelopmentService productDevelopmentService;

    @Autowired
    private ICustomerRelationshipService customerRelationshipService;

    @Autowired
    private IProductDevelopmentService categoryManager;

    @Autowired
    private IPolicyAdminService policyAdminService;

    @Override
    public ActionForward roleInActualAddCallback(ApplicationContext applicationContext, ActionMapping actionMapping, HttpServletRequest request,
                                                 HttpServletResponse response, T form) {
        Long[] roleType = form.getConformanceTypes();
        Assert.notNull(roleType, "The Role Conformance Type is required to add a Role.");

        Long conformanceType = roleType[0];
        List<Long> ancestorTypeIds = new ArrayList<>();
        ancestorTypeIds.add(conformanceType);
        ancestorTypeIds.addAll(productDevelopmentService.getAncestorTypeIds(applicationContext, conformanceType));

        if (ancestorTypeIds.contains(CoreTypeReference.AGREEMENT.getValue())) {
            return addCallBackAndRedirectTo(actionMapping, request, response, form, "findAgreement",
                    CallBackAction.ADD, null);
        } else if (ancestorTypeIds.contains(CoreTypeReference.PRODUCT.getValue())) {
            return addCallBackAndRedirectTo(actionMapping, request, response, form, "findProduct",
                    CallBackAction.ADD, null);
        } else if (ancestorTypeIds.contains(CoreTypeReference.PARTY.getValue())) {
            return addCallBackAndRedirectTo(actionMapping, request, response, form, "addRolePlayerFromAgreement",
                    CallBackAction.ADD, null);
        } else if (ancestorTypeIds.contains(CoreTypeReference.MANUFACTUREDITEM.getValue())) {
            return addCallBackAndRedirectTo(actionMapping, request, response, form, "manufacturedItemUI",
                    BusinessModelObject.class, CallBackAction.ADD, null);
        } else if (ancestorTypeIds.contains(CoreTypeReference.FUND.getValue())) {
            return addCallBackAndRedirectTo(actionMapping, request, response, form, "linkFundUI",
                    BusinessModelObject.class, CallBackAction.ADD, null);
        } else if (ancestorTypeIds.contains(CoreTypeReference.CATEGORY.getValue())) {
            return createRedirectToCategorySelectionUI(actionMapping, request, response, form);
        } else if (ancestorTypeIds.contains(CoreTypeReference.BUSINESSMODELOBJECT.getValue())) {
            ViewActionHelper<T> viewActionHelper = new ViewActionHelper<T>();
            ActionHelper actionHelperDTO = viewActionHelper.popActionHelperDTO(request);

            // in this case the path is the path of the actual that the role is being added to
            String path = actionHelperDTO.getPath();
            ObjectReference contextObjectReference = actionHelperDTO.getContextObjectReference();

            KindDTO roleKindDTO = actionHelperDTO.getKindDTO();
            addEmptyRoleToOnlyActual(path, contextObjectReference, roleKindDTO);

            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.request.message.roleplayeradded", roleKindDTO.getName()));
            request.getSession().setAttribute(Globals.MESSAGE_KEY, messages);

            // redirect to display the page which has the role
            ActionRedirect redirect = new ActionRedirect(actionMapping.getPath() + ".do");
            redirect.addParameter("contextObjectReference", contextObjectReference);
            redirect.addParameter("path", path);

            return redirect;
        } else {
            throw new IllegalStateException("The addition of the role player type: " + conformanceType
                    + " is not supported");
        }

    }

    protected abstract void addEmptyRoleToOnlyActual(String path, ObjectReference contextObjectReference, KindDTO roleKindDTO);

    @Override
    public ActionForward roleInActualViewCallback(ActionMapping actionMapping, HttpServletRequest request,
                                                  HttpServletResponse response, T form, IPolicyAdminService fsaBusinessServiceRemote,
                                                  IProductDevelopmentService typeService, RolePlayerSupportive rolePlayerSupportive,
                                                  TopLevelAgreementContextEnum topLevelAgreementContextEnum) {

        Long[] roleType = form.getConformanceTypes();
        Assert.notNull(roleType, "The Role Conformance Type is required to view a Role.");

        ObjectReference rolePlayer = rolePlayerSupportive.getRolePlayer();
        Long rolePlayerTypeId = rolePlayer.getTypeId();

        List<Long> ancestorTypeIds = new ArrayList<>();
        ancestorTypeIds.add(rolePlayerTypeId);
        ancestorTypeIds.addAll(productDevelopmentService.getAncestorTypeIds(new ApplicationContext(),
                rolePlayerTypeId)); // TODO

        String context = TopLevelAgreementContextEnum.REQUEST.name();
        if (topLevelAgreementContextEnum != null) {
            context = topLevelAgreementContextEnum.name();
        }

        ActionRedirect actionRedirect;

        if (ancestorTypeIds.contains(CoreTypeReference.ACCOUNT.getValue())) {
            actionRedirect = addCallBackAndRedirectTo(actionMapping, request, response, form, "account",
                    CallBackAction.UPDATE, context);
            actionRedirect.addParameter("accountObjectReference", rolePlayer);

        } else if (ancestorTypeIds.contains(CoreTypeReference.CATEGORY.getValue())) {
            String messageKey = "page.selectcategory.message.nofurtherinfo";
            form.setPath(PathTraversalUtil.trimTraversalPath(form.getPath(), ElementType.Role)); // remove the role
            // path and redisplay the agreement
            return redisplayOnlyActualWithMessage(request, form, context, messageKey);

        } else if (ancestorTypeIds.contains(CoreTypeReference.MONEYPROVISION.getValue())) {
            actionRedirect = addCallBackAndRedirectTo(actionMapping, request, response, form, "moneyProvision",
                    CallBackAction.UPDATE, context);
            actionRedirect.addParameter("moneyProvisionObjectReference", rolePlayer);
            actionRedirect.addParameter("moneyProvisionContextObjectReference", form.getContextObjectReference());

        } else if (ancestorTypeIds.contains(CoreTypeReference.MONEYSCHEDULER.getValue())) {
            actionRedirect = addCallBackAndRedirectTo(actionMapping, request, response, form, "moneySchedulerUI",
                    CallBackAction.UPDATE, context);

            actionRedirect.addParameter("moneySchedulerObjectReference", rolePlayer);

        } else if (ancestorTypeIds.contains(CoreTypeReference.VERSIONEDAGREEMENT.getValue())) {
            actionRedirect = addCallBackAndRedirectTo(actionMapping, request, response, form, "agreementGUI",
                    CallBackAction.UPDATE, context);
            AgreementDTO tlaDTO = fsaBusinessServiceRemote
                    .getTopLevelAgreementViewWithHighestVersionNumber(new ApplicationContext(),
                            rolePlayer);
            ObjectReference tlaObjectReference = tlaDTO.getObjectReference();
            actionRedirect.addParameter("contextObjectReference", tlaObjectReference.toString());
            return actionRedirect;
        } else if (ancestorTypeIds.contains(CoreTypeReference.TOPLEVELFINANCIALSERVICESAGREEMENT.getValue())) {
            actionRedirect = addCallBackAndRedirectTo(actionMapping, request, response, form, "agreementGUI",
                    CallBackAction.UPDATE, context);
            actionRedirect.addParameter("contextObjectReference", rolePlayer.toString());
            return actionRedirect;
        } else if (ancestorTypeIds.contains(CoreTypeReference.AGREEMENT.getValue())) {
            actionRedirect = addCallBackAndRedirectTo(actionMapping, request, response, form, "agreementGUI",
                    CallBackAction.UPDATE, context);
            ApplicationContext applicationContext = new ApplicationContext();

            if (typeService.isSubType(applicationContext,
                    CoreTypeReference.TOPLEVELFINANCIALSERVICESAGREEMENT.getValue(), rolePlayer.getTypeId())) {
                actionRedirect.addParameter("contextObjectReference", rolePlayer.toString());
                return actionRedirect;
            } else if (typeService.isSubType(applicationContext, CoreTypeReference.AGREEMENT.getValue(),
                    rolePlayer.getTypeId())) {
                StringBuilder sb = new StringBuilder();
                AgreementDTO agreement = fsaBusinessServiceRemote.getAgreementView(applicationContext,
                        form.getContextObjectReference(), rolePlayer);
                AgreementDTO topLevelAgreement = fsaBusinessServiceRemote
                        .getTopLevelAgreementForAgreementPart(applicationContext, rolePlayer);

                while (agreement.getCompositeObjectReference() != null) {
                    AgreementDTO parentAgreement = fsaBusinessServiceRemote.getAgreementView(applicationContext,
                            form.getContextObjectReference(), agreement.getCompositeObjectReference());
                    sb = PathTraversalUtil.generateTraversalPath(sb, parentAgreement.getComponentLists(), agreement);
                    agreement = parentAgreement;
                }

                ObjectReference tlaObjectReference = topLevelAgreement.getObjectReference();
                actionRedirect.addParameter("path", sb.toString());
                actionRedirect.addParameter("contextObjectReference", tlaObjectReference.toString());
                return actionRedirect;
            }

        } else if (ancestorTypeIds.contains(CoreTypeReference.ROLEPLAYER.getValue())) {
            actionRedirect = addCallBackAndRedirectTo(actionMapping, request, response, form,
                    "displayPartyFromExternal", CallBackAction.UPDATE, context);

            actionRedirect.addParameter("roleKindToLink", getRoleKind(form).getName());
            actionRedirect.addParameter("rolePlayerObjectReference", rolePlayer);

        } else if (ancestorTypeIds.contains(CoreTypeReference.MANUFACTUREDITEM.getValue())) {

            actionRedirect = addCallBackAndRedirectTo(actionMapping, request, response, form, "manufacturedItemUI",
                    CallBackAction.UPDATE, context);
            actionRedirect.addParameter("readOnly", (rolePlayerSupportive.isReadOnly() ? "true" : "false"));
            actionRedirect.addParameter("rolePlayerObjectReference", rolePlayer);

        } else if (ancestorTypeIds.contains(CoreTypeReference.FUND.getValue())) {
            actionRedirect = addCallBackAndRedirectTo(actionMapping, request, response, form, "fund",
                    CallBackAction.UPDATE, context);
            actionRedirect.addParameter("fundObjectReference", rolePlayer);

        } else if (ancestorTypeIds.contains(CoreTypeReference.DOCUMENT.getValue())) {
            try {
                retrieveAndDisplayDocument(response, rolePlayerSupportive.getRolePlayer());
            } catch (IOException e) {
            }
            actionRedirect = null;
        } else if (ancestorTypeIds.contains(CoreTypeReference.PRODUCT.getValue())) {
            actionRedirect = addCallBackAndRedirectTo(actionMapping, request, response, form, "productUI",
                    CallBackAction.UPDATE, context);
            actionRedirect.addParameter("objectReference", rolePlayer);
        }
        else {
            throw new IllegalStateException("Unknown role type: " + roleType[0]);
        }

        return actionRedirect;

    }

    protected ActionForward redisplayOnlyActualWithMessage(HttpServletRequest httpRequest, T form,
                                                           String context, String messageKey, Object... args) {

        // clean up the stack as we are not going to display the role
        ViewActionHelper<T> viewActionHelper = new ViewActionHelper<T>();
        viewActionHelper.popActionHelperDTO(httpRequest);

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(messageKey, args));
        httpRequest.getSession().setAttribute(Globals.MESSAGE_KEY, messages);

        ActionRedirect redirect = new ActionRedirect(getCallBackPath(context));
        redirect.addParameter("contextObjectReference", form.getContextObjectReference());
        redirect.addParameter("path", form.getPath());
        return redirect;
    }

    private void retrieveAndDisplayDocument(HttpServletResponse httpServletResponse, ObjectReference objRef)
            throws IOException {
        Assert.notNull(objRef);
        Document pdf = customerRelationshipService.getPolicyDocument(new ApplicationContext(), objRef);
        httpServletResponse.setHeader(
                "Content-disposition",
                "attachment; filename=" + pdf.getName());
        httpServletResponse.setContentType("application/pdf");
        httpServletResponse.getOutputStream().write(pdf.getDocument());
    }

    /**
     * Handle the request to display the category UI.
     */
    private ActionForward createRedirectToCategorySelectionUI(ActionMapping actionMapping,
                                                              HttpServletRequest httpRequest, HttpServletResponse response, T form) {

        // TODO it would be nice be able to get spec object information here, eg.
        // Agreement("Foo").Specification.RoleSpecification("fd)
        KindDTO categorySchemeKind = policyAdminService.getKind(new ApplicationContext(), KindCategory.PROPERTY,
                "Category Scheme");

        if (categorySchemeKind == null) {
            return redisplayOnlyActualWithMessage(httpRequest, form, null,
                    "page.selectcategory.message.categoryschemekindmissing");
        }

        OnlyActualDTO dto = getOnlyActualView(form);
        List<RoleDTO> roles = dto.getRoleListOfKind(getRoleKind(form).getName()).getRoles();
        if (roles.isEmpty()) {
            return redisplayOnlyActualWithMessage(httpRequest, form, null, "page.selectcategory.message.norolesdefined"
            );
        }

        PropertyDTO property = roles.get(0).getPropertyOfKind(categorySchemeKind);
        if (property == null) {
            return redisplayOnlyActualWithMessage(httpRequest, form, null,
                    "page.selectcategory.message.categoryschemepropertymissing");
        }

        String schemeName = (String) property.getValue();
        ICategoryScheme categoryScheme = categoryManager.getCategoryScheme(new ApplicationContext(), schemeName);
        if (categoryScheme == null) {
            return redisplayOnlyActualWithMessage(httpRequest, form, null,
                    "page.selectcategory.message.categoryschememissing", schemeName);
        }

        ActionRedirect redirect = addCallBackAndRedirectTo(actionMapping, httpRequest, response, form,
                "selectCategoryUI", CallBackAction.ADD, null);
        redirect.addParameter("categorySchemeObjectReference", categoryScheme.getObjectReference());

        return redirect;
    }

    protected abstract OnlyActualDTO getOnlyActualView(T form);

    protected IProductDevelopmentService getProductDevelopmentService() {
        return productDevelopmentService;
    }

    protected ICustomerRelationshipService getCustomerRelationshipService() {
        return customerRelationshipService;
    }

    protected IProductDevelopmentService getCategoryManager() {
        return categoryManager;
    }

    protected IPolicyAdminService getPolicyAdminService() {
        return policyAdminService;
    }

}

