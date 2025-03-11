<%@ page import="com.silvermoongroup.fsa.web.claimmanagement.ExtendedCheckboxTableDecorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<smg:datepicker selector=".datefield"/>

<%
    ExtendedCheckboxTableDecorator decorator = new ExtendedCheckboxTableDecorator();
    decorator.setId("id");
    decorator.setFieldName("_chk");
    pageContext.setAttribute("checkboxDecorator", decorator);
%>

<jsp:useBean id="addLossEventForm" scope="request"
             type="com.silvermoongroup.fsa.web.claimmanagement.form.LossEventForm"/>

<label class="groupHeading"><fmt:message key="page.editlossevent.title"/></label>

<html:form action="/secure/claim/lossevent/edit.do" styleId="findForm" styleClass="form-horizontal">

    <%@ include file="lossEvent.jsp" %>

    <div class="row spacer">
        <div class="col-md-3 text-right">
            <html:submit styleClass="btn btn-primary btn-sm" property="method">
                <fmt:message key="button.save"/>
            </html:submit>
        </div>
    </div>

    <c:if test="${addLossEventForm.claimableBenefits.size() > 0}">

        <label class="groupHeading"><fmt:message key="page.editlossevent.listofclaimablebenefits"/></label>

        <div class="table-responsive spacer">
            <c:url value="/secure/agreementversion/tla.do" var="agreementUrl"/>

            <display:table name="${addLossEventForm.claimableBenefits}"
                           id="addClaimFolderHierarchyTable"
                           decorator="checkboxDecorator"
                           pagesize="15"
                           excludedParams="_chk">

                <display:column property="claimableCoverageKindName" titleKey="claim.addclaimfolderhierarchy.claimablecoveragekindname" />
                <display:column property="claimableCoverage" titleKey="claim.addclaimfolderhierarchy.claimablecoverage" />
                <display:column property="topLevelAgreement" titleKey="claim.addclaimfolderhierarchy.topLevelagreement"
                                paramId="contextObjectReference" paramProperty="topLevelAgreement" href="${agreementUrl}"/>
                <display:column property="startDate" titleKey="claim.addclaimfolderhierarchy.effectivefrom"/>
                <display:column property="endDate" titleKey="claim.addclaimfolderhierarchy.effectiveto"/>
                <display:column property="checkbox" titleKey="claim.addclaimfolderhierarchy.checktoprocess"/>

            </display:table>
        </div>

        <div class="row spacer">
            <div class="col-md-12 text-center">
                <html:submit styleClass="btn btn-primary btn-sm" property="method">
                    <fmt:message key="claim.button.raiseclaim"/>
                </html:submit>
            </div>
        </div>
    </c:if>

</html:form>
