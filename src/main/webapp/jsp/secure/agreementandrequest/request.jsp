<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<smg:datepicker selector=".datefield"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/properties.js"></script>
<script src="${pageContext.request.contextPath}/js/bootbox.min.js"></script>

<jsp:useBean id="requestViewForm" scope="request" type="com.silvermoongroup.fsa.web.agreementandrequest.form.RequestViewForm"/>

<script type="text/javascript">
    $(function () {

        <%-- We only want to override this link when we are editing a request --%>
        <c:if test="${!requestViewForm.structuredActualDTO.readOnly}">
        registerLinksThatNeedToBeOverridden("#resultActualLink");
        registerFormsThatNeedToBeOverridden("form[name='requestViewForm']:not(#propertiesForm,#requestForm)");
        </c:if>

        $('#propertiesForm').bind('submit', function () {
            $('#requestForm').find('.spinner').css('visibility', 'visible');
        });
    });

    <%--
     Perform any action that needs to occur (such a popping up a confirmation dialog) before a request action is performed.
     Return true if the action should proceed, otherwise false.
     This script is inlined as is needs to deal with tranlated values
     --%>
    function confirmRequestAction(initialForm, initialMethodValue) {
        deleteFormClicked = initialForm;

        var cancelMethod = '<fmt:message key="page.request.action.cancel" />';
        var title = '<fmt:message key="label.confirmation" />';
        var doneMethod = '<fmt:message key="page.request.action.done" />';
        var labelYes = '<fmt:message key="label.yesno.true" />';
        var labelNo = '<fmt:message key="label.yesno.false" />';
        var confirmMessage=null;

        if (initialMethodValue == cancelMethod) {
            confirmMessage = "${smg:getRequestCancellationConfirmationMessage(pageContext, requestViewForm.structuredActualDTO)}";
        }
        else if (initialMethodValue == doneMethod) {
            confirmMessage = "${smg:getRequestExecutionConfirmationMessage(pageContext, requestViewForm.structuredActualDTO)}";
        }

        if (confirmMessage) {
            bootbox.dialog({
                message: confirmMessage,
                title: title,
                buttons: {
                    ok: {
                        label: labelYes,
                        className: "btn-primary",
                        callback: function () {
                            return doMainFormSubmitWithInitialForm(initialForm, initialMethodValue);
                        }
                    },
                    cancel: {
                        label: labelNo
                    }
                }
            });
            return false;
        }
        return doMainFormSubmitWithInitialForm(initialForm, initialMethodValue);
    };
</script>

<c:set var="form" value="${requestViewForm}" scope="page"/>
<c:set var="structuredActualDTO" value="${requestViewForm.structuredActualDTO}" scope="page"/>
<c:set var="contextObjectReference" value="${requestViewForm.contextObjectReference}" scope="page"/>
<c:set var="contextPath" value="${requestViewForm.contextPath}" scope="page"/>
<c:set var="currentPath" value="${requestViewForm.path}" scope="page"/>

<h3 class="text-center" title='<smg:fmtSpecificationObjectHint value="${structuredActualDTO}"/>' rel="tooltip">
    <fmt:message key="spf.request"/> - <smg:fmtSpecificationObjectName value="${structuredActualDTO}"/>
</h3>

<div class="section-divider"><label class="groupHeading"><fmt:message key="label.details"/></label></div>

<html:form action="${requestViewForm.contextPath}.do" styleId="propertiesForm">
    <input type="hidden" name="method" value=".validateAndBindProperties"/>
    <input type="hidden" name="contextObjectReference" value="${requestViewForm.structuredActualDTO.objectReference}"/>

    <div class="row">

            <%--Left Column--%>
        <div class="col-md-6">
            <div class="row">
                <label class="col-md-3"><fmt:message key="page.request.label.requestid"/></label>
                <div id="requestId" class="col-md-9"><smg:fmt value="${requestViewForm.structuredActualDTO.objectReference.objectId}"/></div>
            </div>

            <div class="row">
                <label class="col-md-3"><fmt:message key="page.request.label.currentstate"/></label>
                <div class="col-md-9"><smg:fmt value="${requestViewForm.structuredActualDTO.state}"/></div>
            </div>

            <div class="row">
                <label class="col-md-3"><fmt:message key="page.request.label.requesteddate"/></label>
                <div class="col-md-3"><html:text
                        property="effectiveDate"
                        styleClass="form-control input-sm mandatory ${requestViewForm.structuredActualDTO.readOnly ? '' : 'datefield'} "
                        errorStyleClass="form-control input-sm has-error datefield"
                        readonly="${requestViewForm.structuredActualDTO.readOnly}"/></div>
            </div>
        </div>

            <%--Right Column--%>
        <div class="col-md-6">
            <div class="row">
                <label class="col-md-3"><fmt:message key="page.request.label.requesttype"/></label>
                <div class="col-md-9" rel="tooltip">
                    <span title='<fmt:message key="page.request.label.requesttype.${requestViewForm.structuredActualDTO.requestType}.hint" />' rel="tooltip">
                    <fmt:message
                            key="page.request.label.requesttype.${requestViewForm.structuredActualDTO.requestType}"/>
                        </span>
                </div>
            </div>

            <div class="row">
                <label class="col-md-3">
                    <span title="<fmt:message key="page.request.label.requestdate.hint" />" rel="tooltip">
                        <fmt:message key="page.request.label.requestdate"/>
                    </span>
                </label>
                <div class="col-md-9"><smg:fmt value="${requestViewForm.structuredActualDTO.requestDate}"/></div>
            </div>

            <div class="row">
                <label class="col-md-3">
                    <span title="<fmt:message key="page.request.label.executeddate.hint" />" rel="tooltip">
                        <fmt:message key="page.request.label.executeddate"/>
                    </span>

                </label>
                <div class="col-md-9"><smg:fmt value="${requestViewForm.structuredActualDTO.executedAt}"/></div>
            </div>
        </div>

    </div>

    <%--Properties--%>
    <div class="section-divider spacer"><label class="groupHeading"><fmt:message
            key="page.agreement.label.properties"/></label></div>
    <%@include file="properties.jsp" %>

    <%--Derived Properties--%>
    <div class="section-divider spacer">
        <label class="groupHeading">
            <fmt:message key="page.agreement.label.derivedproperties"/>
        </label>
    </div>
    <%@include file="derivedProperties.jsp" %>

</html:form>

<%-- Request Roles --%>
<div class="section-divider spacer"><label class="groupHeading"><fmt:message key="page.agreement.label.roles"/></label>
</div>
<%@ include file="roles.jsp" %>

<%-- Rule results --%>
<c:if test="${!empty requestViewForm.structuredActualDTO.ruleResultDTO}">
    <div class="section-divider spacer"><label class="groupHeading"><fmt:message
            key="page.agreement.ruleresults.title"/></label></div>
    <%@include file="ruleResult.jsp" %>
</c:if>

<%--  TopLevelAgreement View (Only displays on non component requests) --%>
<c:if test="${!(smg:isComponentRequest(pageContext, requestViewForm.structuredActualDTO))}">
    <div class="section-divider spacer"><label class="groupHeading"><fmt:message key="page.request.agreement"/></label>
    </div>

    <c:url value="${contextPath}/tla.do" var="resultActualURL">
        <c:param name="contextObjectReference" value="${requestViewForm.structuredActualDTO.objectReference}"/>
        <c:param name="externalReference" value="${requestViewForm.structuredActualDTO.objectReference}"/>
    </c:url>

    <div>
        <a id="resultActualLink" href="${resultActualURL}"
           title="<smg:fmtSpecificationObjectHint value="${requestViewForm.agreementDTO}" />">
            <smg:fmtSpecificationObjectName value="${requestViewForm.agreementDTO}"/>
        </a>
    </div>

</c:if>

<html:form action="/secure/request.do" styleId="requestForm">
    <input type="hidden" name="contextObjectReference" value="${requestViewForm.structuredActualDTO.objectReference}"/>
    <div class="text-center">
        <smg:requestStatusButtons request="${requestViewForm.structuredActualDTO}"
                                  onclick="return confirmRequestAction(this.form, $(this).attr('value'));"/>
        <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
    </div>
</html:form>