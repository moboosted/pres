<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/properties.js"></script>

<jsp:useBean id="agreementViewForm" scope="request" type="com.silvermoongroup.fsa.web.agreementandrequest.form.AgreementForm"/>

<c:set var="form" value="${agreementViewForm}" scope="page"/>
<c:set var="structuredActualDTO" value="${agreementViewForm.structuredActualDTO}" scope="page"/>
<c:set var="contextObjectReference" value="${agreementViewForm.contextObjectReference}" scope="page"/>
<c:set var="contextPath" value="${agreementViewForm.contextPath}" scope="page"/>
<c:set var="currentPath" value="${agreementViewForm.path}" scope="page"/>

<c:if test="${!structuredActualDTO.readOnly}">
<script>
    $(function () {
        registerFormsThatNeedToBeOverridden("form[name='agreementViewForm']:not(#propertiesForm,#agreementForm)");
        registerLinksThatNeedToBeOverridden("a.validate-props");

        <%-- Don't allow submits of the properties form directly with the enter key --%>
        $(document).keypress(function (e) {
            if (e.which == 13) {
                if ($(e.target.form).attr('id') == "propertiesForm") {
                    e.preventDefault();
                }
            }
        });

    });
</script>
</c:if>
<smg:datepicker selector=".datefield"/>

<h3 class="text-center" title='<smg:fmtSpecificationObjectHint value="${structuredActualDTO}"/>' rel="tooltip">
    <c:choose>
        <c:when test="${smg:isTopLevelAgreement(pageContext, structuredActualDTO)}"><fmt:message
                key="spf.agreement.tla"/></c:when>
        <c:otherwise><fmt:message key="spf.agreement.component"/></c:otherwise>
    </c:choose>
    - <smg:fmtSpecificationObjectName value="${structuredActualDTO}"/>
</h3>


<div class="row spacer">

    <%--Left Column--%>
    <div class="col-md-2">

        <%-- Agreement Components --%>
        <div class="section-divider"><label class="groupHeading"><fmt:message
                key="page.agreement.label.hierarchy"/></label>
        </div>
        <%@include file="agreementNavigator.jsp" %>

        <c:if test="${!empty structuredActualDTO.availableRequests}">
            <div class="section-divider spacer"><label class="groupHeading">Available Actions</label></div>
            <%@include file="availableRequests.jsp" %>
        </c:if>
    </div>

    <%--Right Column--%>
    <div class="col-md-10">

        <div class="section-divider"><label class="groupHeading"><fmt:message key="label.details"/></label></div>
        <%@include file="agreementDetails.jsp" %>


        <%-- Agreement Requests  --%>
        <div class="section-divider spacer"><label class="groupHeading"><fmt:message
                key="page.agreement.label.requests"/></label></div>

        <%@include file="agreementRequests.jsp" %>

        <%--Properties--%>
        <html:form action="${agreementViewForm.contextPath}.do" styleId="propertiesForm">
            <html:hidden property="contextObjectReference" value="${contextObjectReference}"/>
            <html:hidden property="path" value="${currentPath}"/>
            <c:if test="${!structuredActualDTO.readOnly}">
                <html:hidden property="method" value=".validateAndBindProperties"/>
            </c:if>

            <div class="section-divider spacer"><label class="groupHeading"><fmt:message
                    key="page.agreement.label.properties"/></label></div>
            <%@include file="properties.jsp" %>

            <div class="section-divider spacer"><label class="groupHeading"><fmt:message
                    key="page.agreement.label.derivedproperties"/></label></div>
            <%@include file="derivedProperties.jsp" %>

        </html:form>

        <%-- Roles --%>
        <div class="section-divider spacer"><label class="groupHeading"><fmt:message key="page.agreement.label.roles"/></label>
        </div>
        <%@include file="roles.jsp" %>

    </div>
</div>


<c:if test="${!structuredActualDTO.readOnly}">
    <div class="row text-center">
        <div class="col-md-12">
            <html:form action="${agreementViewForm.contextPath}.do" styleId="agreementForm">
                <html:hidden property="contextObjectReference" value="${contextObjectReference}"/>
                <html:hidden property="path" value="${currentPath}"/>
                <input type="hidden" name="method" value=".back"/>

                <html:submit property="method" styleClass="btn btn-primary btn-sm"
                             onclick="return doMainFormSubmitWithInitialForm(this.form, 'Submit');">
                    <fmt:message key="button.submit"/>
                </html:submit>
                <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
            </html:form>

        </div>
    </div>
</c:if>