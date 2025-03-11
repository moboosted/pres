/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.action;

import com.silvermoongroup.businessservice.claimmanagement.service.intf.IClaimManagementService;
import com.silvermoongroup.businessservice.configurationmanagement.service.intf.IConfigurationService;
import com.silvermoongroup.businessservice.customerrelationshipmanagement.service.intf.ICustomerRelationshipService;
import com.silvermoongroup.businessservice.financialmanagement.service.intf.IBillingAndCollectionService;
import com.silvermoongroup.businessservice.financialmanagement.service.intf.IFinancialManagementService;
import com.silvermoongroup.businessservice.physicalobjectmanagement.service.intf.IPhysicalObjectManagementService;
import com.silvermoongroup.businessservice.policymanagement.dto.KindDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.RequestStateDTO;
import com.silvermoongroup.businessservice.policymanagement.service.intf.IPolicyAdminService;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.datatype.Amount;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.intf.IEnum;
import com.silvermoongroup.fsa.web.common.WebApplicationProperties;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import com.silvermoongroup.fsa.web.locale.ITypeParser;
import com.silvermoongroup.fsa.web.user.profile.IUserProfileManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.*;
import org.apache.struts.actions.LookupDispatchAction;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The AbstractAction provides a base class for all struts action classes.
 */
public abstract class AbstractLookupDispatchAction extends LookupDispatchAction {

    private WebApplicationContext webApplicationContext;

    private IFinancialManagementService financialManagementService;
    private IBillingAndCollectionService billingAndCollectionService;
    private IPhysicalObjectManagementService physicalObjectManagementService;
    private IProductDevelopmentService productDevelopmentService;
    private ICustomerRelationshipService customerRelationshipService;
    private IPolicyAdminService policyAdminService;
    private IConfigurationService configurationService;
    private IClaimManagementService claimManagementService;

    private WebApplicationProperties applicationProperties;
    private IUserProfileManager userProfileManager;
    private ITypeFormatter typeFormatter;
    private ITypeParser typeParser;

    // ~ Methods ================================================================

    /**
     * Constructor.
     */
    public AbstractLookupDispatchAction() {
    }

    /**
     * Initialize the WebApplicationContext for this Action.
     * Invokes onInit after successful initialization of the context.
     *
     * @see #initWebApplicationContext
     * @see #onInit
     */
    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        if (actionServlet != null) {
            this.webApplicationContext = initWebApplicationContext(actionServlet);
            onInit();
        }

    }

    /**
     * Fetch ContextLoaderPlugIn's WebApplicationContext from the ServletContext,
     * falling back to the root WebApplicationContext (the usual case).
     *
     * @param actionServlet the associated ActionServlet
     * @return the WebApplicationContext
     * @throws IllegalStateException if no WebApplicationContext could be found
     */
    protected WebApplicationContext initWebApplicationContext(ActionServlet actionServlet)
            throws IllegalStateException {

        return WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
    }


    protected void onInit() {
        applicationProperties = getBean(WebApplicationProperties.class);

        // EJBs
        billingAndCollectionService = getBean(IBillingAndCollectionService.class);
        customerRelationshipService = getBean(ICustomerRelationshipService.class);
        financialManagementService = getBean(IFinancialManagementService.class);
        policyAdminService = getBean(IPolicyAdminService.class);
        physicalObjectManagementService = getBean(IPhysicalObjectManagementService.class);
        productDevelopmentService = getBean(IProductDevelopmentService.class);
        configurationService = getBean(IConfigurationService.class);
        claimManagementService = getBean(IClaimManagementService.class);

        userProfileManager = getBean(IUserProfileManager.class);
        typeFormatter = getBean(ITypeFormatter.class);
        typeParser = getBean(ITypeParser.class);
    }

    /**
     * Return the current Spring WebApplicationContext.
     */
    protected final WebApplicationContext getWebApplicationContext() {
        return this.webApplicationContext;
    }

    /**
     * Return the current ServletContext.
     */
    protected final ServletContext getServletContext() {
        return this.webApplicationContext.getServletContext();
    }

    /**
     * Convenience method to obtain spring beans.
     *
     * @param name The name of the bean
     * @return The bean
     */
    protected Object getBean(String name) {
        return getWebApplicationContext().getBean(name);
    }

    /**
     * Return the bean instance that uniquely matches the given object type. If a bean of the given type does not exist,
     * or there is not a unique match, a {@link NoSuchBeanDefinitionException} is raised. This exception be considered a
     * configuration error and should not be handled by clients.
     *
     * @param clazz The class of the bean to lookup.
     * @return A matching bean instance.
     */
    protected <T> T getBean(Class<T> clazz) {
        return getWebApplicationContext().getBean(clazz);
    }

    /**
     * Return the bean instance of the type with the given name. Consider using this method if there is the possibility
     * of more than one bean existing of the given type.
     * <p>
     * If a bean of the given type does not exist, or there is not a unique match, a
     * {@link NoSuchBeanDefinitionException} is raised. This exception be considered a configuration error and should
     * not be handled by clients.
     *
     * @param clazz The class of the bean to lookup.
     * @param name  The bean name
     * @return A matching bean instance.
     */
    protected <T> T getBean(String name, Class<T> clazz) {
        return getWebApplicationContext().getBean(name, clazz);
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {

        Map<String, String> map = new HashMap<>();
        map.put("button.defaultExecute", "defaultExecute");
        map.put("button.save", "save");
        map.put("button.edit", "edit");
        map.put("button.update", "update");
        map.put("button.delete", "delete");
        map.put("button.find", "find");
        map.put("button.add", "add");
        map.put("button.retrieve", "retrieve");
        map.put("button.submit", "submit");
        map.put("button.resubmit", "resubmit");
        map.put("button.send", "send");
        map.put("button.back", "back");
        map.put("button.handleCallBack", "handleCallBack");
        map.put("button.handlePartyCallBack", "handlePartyCallBack");
        map.put("button.handlePropertyCallBack", "handlePropertyCallBack");
        map.put("button.handleComponentCallBack", "handleComponentCallBack");
        map.put("button.handleRoleListCallBack", "handleRoleListCallBack");
        map.put("button.handleRoleCallBack", "handleRoleCallBack");
        map.put("button.handleRequestCallBack", "handleRequestCallBack");
        map.put("action.createDocument", "createDocument");
        map.put("action.handleExclusionCallBack", "handleExclusionCallBack");
        map.put("action.handleFeaturesCallBack", "handleFeaturesCallBack");
        map.put("action.handleLoadingCallBack", "handleLoadingCallBack");
        map.put("button.handleExclusionCallBack", "handleExclusionCallBack");
        map.put("button.handleLoadingCallBack", "handleLoadingCallBack");
        map.put("action.handleMoneySchedulerCallBack", "handleMoneySchedulerCallBack");
        map.put("action.handleActivityCallBack", "handleActivityCallBack");
        map.put("action.handlePhysicalObjectCallBack", "handlePhysicalObjectCallBack");
        map.put("action.handlePlaceCallBack", "handlePlaceCallBack");
        map.put("action.handleFundCallBack", "handleFundCallBack");
        map.put("button.handleFundCallBack", "handleFundCallBack");
        map.put("action.loadNewFund", "loadNewFund");
        map.put("button.loadNewFund", "loadNewFund");
        map.put("action.findFund", "findFund");
        map.put("button.findFund", "findFund");
        map.put("button.cancel", "cancel");
        map.put("button.search", "search");
        map.put("button.open", "open");
        map.put("button.reset", "reset");
        map.put("button.validate", "validate");
        map.put("button.balance", "balance");
        map.put("button.view", "view");
        map.put("button.execute", "execute");
        map.put("button.load", "load");
        map.put("action.load", "load");
        map.put("button.settle", "settle");
        map.put("button.reloadPage", "reloadPage");
        map.put("button.authorise", "authorise");
        map.put("button.decline", "decline");
        map.put("button.statementAccount", "statementAccount");
        map.put("button.loadAgreementFromSession", "loadAgreementFromSession");
        map.put("button.settleAccounts", "settleAccounts");
        map.put("button.sort", "sort");
        map.put("button.undo", "undo");
        map.put("button.next", "next");
        map.put("button.editRolePlayer", "editRolePlayer");
        map.put("button.proceed", "proceed");
        map.put("button.viewProperties", "viewProperties");
        map.put("button.viewRequestDetails", "viewRequestDetails");
        map.put("button.viewComponentDetails", "viewComponentDetails");
        map.put("button.createRequest", "createRequest");
        map.put("button.create", "create");
        map.put("button.raise", "raise");
        map.put("button.calculate", "calculate");
        map.put("button.clear", "clear");
        map.put("button.restructurePortfolio", "restructurePortfolio");
        map.put("button.underwrite", "underwrite");
        map.put("button.issue", "issue");
        map.put("button.reject", "reject");
        map.put("button.override", "override");
        map.put("action.loadFromSettles", "loadFromSettles");
        map.put("action.enquire", "enquire");
        map.put("action.select", "select");
        map.put("action.load", "load");
        map.put("action.submit", "submit");
        map.put("action.validateAndBindProperties", "validateAndBindProperties");
        map.put("action.displayMPEdetails", "displayMPEdetails");
        map.put("action.retrieve", "retrieve");
        map.put("action.retrieveEnabledByAgreements", "retrieveEnabledByAgreements");
        map.put("action.find", "find");
        map.put("action.raiseClaimRequest", "raiseClaimRequest");
        map.put("action.open", "open");
        map.put("action.loadDetails", "loadDetails");
        map.put("action.loadVehicleModel", "loadVehicleModel");
        map.put("action.processBreadCrumb", "processBreadCrumb");
        map.put("action.view", "view");
        map.put("action.drillDown", "drillDown");
        map.put("action.editRolePlayerFromFind", "editRolePlayerFromFind");
        map.put("action.handleAgreementCallBack", "handleAgreementCallBack");
        map.put("action.handleComponentDetails", "handleComponentDetails");
        map.put("action.loading", "loading");
        map.put("action.exclusion", "exclusion");
        map.put("action.handleTreatmentCallBack", "handleTreatmentCallBack");
        map.put("button.handleCommunicationCallBack", "handleCommunicationCallBack");
        map.put("button.handleTreatementCallBack", "handleTreatementCallBack");
        map.put("action.loadRequestForNewBusiness", "loadRequestForNewBusiness");
        map.put("action.addComponent", "addComponent");
        map.put("action.deleteComponent", "deleteComponent");
        map.put("action.updateComponent", "updateComponent");
        map.put("button.applicationReturnToAgreement", "agreement");
        map.put("button.link", "link");
        map.put("button.unlink", "unlink");
        map.put("action.defaultFind", "defaultFind");
        map.put("action.edit", "edit");
        map.put("action.delete", "delete");
        map.put("action.add", "add");
        map.put("action.addNew", "addNew");
        map.put("action.addExisting", "addExisting");
        map.put("action.getCondCodeAndDesc", "getCondCodeAndDesc");
        map.put("action.getSpecSiteAndCondList", "getSpecSiteAndCondList");
        map.put("action.getIcd_loaDescription", "getIcd_loaDescription");
        map.put("action.loadingDescrAndRate", "loadingDescrAndRate");
        map.put("action.assessmentCategory", "assessmentCategory");
        map.put("button.getAssessmentResult", "getAssessmentResult");
        map.put("action.next", "next");
        map.put("action.attach", "attach");
        map.put("action.detach", "detach");
        map.put("button.attach", "attach");
        map.put("button.viewQuestionTemplatesList", "viewQuestionTemplatesList");
        map.put("button.viewPredefinedAnswersList", "viewPredefinedAnswersList");
        map.put("action.populateAttributes", "populateAttributes");
        map.put("action.link", "link");
        map.put("action.unlink", "unlink");
        map.put("button.notAnswered", "notAnswered");
        map.put("action.refresh", "refresh");
        map.put("action.getRolePlayers", "getRolePlayers");
        map.put("action.getRolePlayersObjects", "getRolePlayersObjects");
        map.put("action.getRolePlayersGUI", "getRolePlayersGUI");
        map.put("action.updateDescription", "updateDescription");
        map.put("action.entryPointCommission", "entryPointCommission");
        map.put("action.entryPointOrganisation", "entryPointOrganisation");
        map.put("action.loadDivMgrs", "loadDivMgrs");
        map.put("action.loadManagers", "loadManagers");
        map.put("action.loadConsultants", "loadConsultants");
        map.put("action.loadAdmin", "loadAdmin");
        map.put("action.loadAssistants", "loadAssistants");
        map.put("button.claimView", "claimView");
        map.put("button.addElementaryClaim", "addElementaryClaim");
        map.put("button.addLossEvent", "addLossEvent");
        map.put("button.addClaimOffer", "addClaimOffer");
        map.put("button.done", "done");
        map.put("action.loadRegisterManualPaymentRequest", "loadRegisterManualPaymentRequest");
        map.put("action.about", "about");
        map.put("action.ajaxPost", "ajaxPost");
        map.put("action.back", "back");

        // request search actions
        map.put("action.findSystemUsers", "findSystemUsers");

        // claim actions
        map.put("action.handleRolePlayerCallBack", "handleRolePlayerCallBack");

        return map;
    }

    @Override
    protected String getMethodName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response, String parameter) throws Exception {

        String methodName = StringUtils.trimToNull(request.getParameter(parameter));

        // if a method is specified with a . (dot) prefix, strip off the dot and use AS IS (don't localise)
        // this logic means that we don't need to internationalise method parameters in urls (links)
        if (methodName != null && methodName.startsWith(".")) {
            return methodName.substring(1);
        }

        if (!GenericValidator.isBlankOrNull(request.getParameter("hiddenMethod"))) {
            methodName = StringUtils.trimToNull(request.getParameter("hiddenMethod"));

            if (methodName != null && methodName.startsWith(".")) {
                return methodName.substring(1);
            }
        }

        methodName = super.getMethodName(mapping, form, request, response, parameter);

        // If methodName is null get it from the javascript value
        if (methodName == null) {
            if (!GenericValidator.isBlankOrNull(request.getParameter("method"))) {
                methodName = request.getParameter("method");
            } else if (!GenericValidator.isBlankOrNull(request.getParameter("hiddenMethod"))) {
                methodName = request.getParameter("hiddenMethod");
            } else {
                methodName = "defaultExecute";
            }

        }
        return methodName;
    }

    /**
     * Convenience method to initialize messages in a subclass.
     *
     * @param request the current request
     * @return the populated (or empty) messages
     */
    @Override
    protected ActionMessages getMessages(HttpServletRequest request) {
        ActionMessages messages;
        HttpSession session = request.getSession();

        if (request.getAttribute(Globals.MESSAGE_KEY) != null) {
            messages = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
            saveMessages(request, messages);
        } else if (session.getAttribute(Globals.MESSAGE_KEY) != null) {
            messages = (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);
            saveMessages(request, messages);
            session.removeAttribute(Globals.MESSAGE_KEY);
        } else {
            messages = new ActionMessages();
        }

        return messages;
    }

    protected void saveErrorMessages(HttpServletRequest request, ActionMessages messages) {
        saveErrors(request.getSession(), messages);
    }

    /**
     * @param request The request being made.
     * @return true if there are error messages associated with the request, otherwise false.
     */
    protected boolean hasErrorMessages(HttpServletRequest request) {
        return (!getErrors(request).isEmpty());
    }


    protected void saveInformationMessages(HttpServletRequest request, ActionMessages messages) {
        saveMessages(request.getSession(), messages);
    }

    /**
     * Add a message with the key and optional parameters.  Existing messages are preserved.
     *
     * @param request    The http request.
     * @param messageKey The message key
     * @param parameters An optional array of replacement parameters.
     */
    protected void addInformationMessage(HttpServletRequest request, String messageKey, String... parameters) {
        ActionMessages messages = getMessages(request);
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(messageKey, parameters));
        saveInformationMessages(request, messages);
    }

    /**
     * Format a message given a key and a set of parameters
     *
     * @param key        The message key
     * @param parameters The parameters
     * @return The formatted message.
     */
    protected String formatMessage(String key, String... parameters) {
        return getTypeFormatter().formatMessage(key, parameters);
    }

    /**
     * Format a date based on a user's preference. Typically used before stashing the date string in a form bean for
     * display
     *
     * @param date The date to format
     * @return The formatted date.
     */
    protected String formatDate(Date date) {
        return getTypeFormatter().formatDate(date);
    }

    /**
     * Format a property enumeration
     *
     * @param value The value to format.
     * @return The formatted value.
     */
    protected String formatEnum(EnumerationReference value) {
        return getTypeFormatter().formatEnum(value);
    }
    
    /**
     * Format an IImmutable enumeration
     *
     * @param value The value to format.
     * @return The formatted value.
     */
    protected String formatEnum(IEnum value) {
        return getTypeFormatter().formatEnum(value);
    }
    
    /**
     * Format a property enumeration
     *
     * @param value The value to format.
     * @return The formatted value.
     */
    protected String formatEnum(IEnumeration value) {
        return getTypeFormatter().formatEnum(value);
    } 

    protected String formatKind(KindDTO kind) {
        return getTypeFormatter().formatKind(kind);
    }

    protected String formatRequestState(RequestStateDTO state) {
        return getTypeFormatter().formatRequestState(state);
    }

    protected String formatRequestState(String stateName) {
        return getTypeFormatter().formatRequestState(stateName);
    }

    protected String formatAmount(Amount amount) {
        return getTypeFormatter().formatAmount(amount);
    }

    protected String formatType(ObjectReference ref) {
        if (ref == null || ref.getTypeId() == null) {
            return StringUtils.EMPTY;
        }
        Type type = getProductDevelopmentService().getType(getApplicationContext(), ref.getTypeId());
        if (type == null) {
            return StringUtils.EMPTY;
        }
        return type.getName();
    }

    protected String formatType(Type type) {
        if (type == null) {
            return StringUtils.EMPTY;
        }
        return getTypeFormatter().formatType(type);
    }

    protected String[] formatObjects(List<Object> objects) {
        return getTypeFormatter().formatObjects(objects);
    }

    /**
     * Parse a string into a {@link Date}
     *
     * @param date The representation to parse.
     * @return The parsed date
     */
    protected Date parseDate(String date) {
        return getTypeParser().parseDate(date);
    }

    /**
     * Parse a string into a {@link DateTime}
     *
     * @param date The representation to parse.
     * @return The parsed dateTime
     */
    protected DateTime parseDateTime(String date) {
        return getTypeParser().parseDateTime(date);
    }

    protected DatePeriod parseDatePeriod(String startDate, String endDate) {
        Date start = parseDate(startDate);
        Date end = parseDate(endDate);
        return new DatePeriod(start, (end == null ? Date.FUTURE : end));
    }

    @SuppressWarnings("unchecked")
    protected EnumerationReference parseEnum(String value) {
        if (value == null) {
            return null;
        }
        return EnumerationReference.convertFromString(value);
    }


    protected String getDateFormat() {
        return getUserProfileManager().getDateFormat();
    }

    /**
     * Lookup the kind given its id
     *
     * @param kindId The id of the kind
     * @return The kind
     */
    protected KindDTO getKindById(Long kindId) {
        return getPolicyAdminService().getKind(getApplicationContext(), kindId);
    }

    protected IUserProfileManager getUserProfileManager() {
        return userProfileManager;
    }

    protected ITypeFormatter getTypeFormatter() {
        return typeFormatter;
    }

    protected ITypeParser getTypeParser() {
        return typeParser;
    }

    protected WebApplicationProperties getApplicationProperties() {
        return applicationProperties;
    }

    protected IPolicyAdminService getPolicyAdminService() {
        return policyAdminService;
    }

    protected IProductDevelopmentService getProductDevelopmentService() {
        return productDevelopmentService;
    }

    protected IPhysicalObjectManagementService getPhysicalObjectManagementService() {
        return physicalObjectManagementService;
    }

    protected IBillingAndCollectionService getBillingAndCollectionService() {
        return billingAndCollectionService;
    }

    protected ICustomerRelationshipService getCustomerRelationshipService() {
        return customerRelationshipService;
    }

    protected IFinancialManagementService getFinancialManagementService() {
        return financialManagementService;
    }

    protected IConfigurationService getConfigurationService() {
        return configurationService;
    }

    protected IClaimManagementService getClaimManagementService() {
        return claimManagementService;
    }
    protected ApplicationContext getApplicationContext() {
        return new ApplicationContext();
    }

}
