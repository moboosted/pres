<%@ page import="com.silvermoongroup.fsa.web.claimmanagement.ExtendedCheckboxTableDecorator" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%
    ExtendedCheckboxTableDecorator decorator = new ExtendedCheckboxTableDecorator();
    decorator.setId("id");
    decorator.setFieldName("_chk");
    pageContext.setAttribute("checkboxDecorator", decorator);
%>

<jsp:useBean id="addClaimFolderHierarchyForm" scope="request"
type="com.silvermoongroup.fsa.web.claimmanagement.form.AddClaimFolderHierarchyForm"/>

<label class="groupHeading" for="addClaimFolderHierarchyTable"><fmt:message key="page.addclaimfolderhierarchy.title"/></label>

<html:form action="/secure/claim/lossevent/addClaimFolderHierarchy.do" styleId="findForm" styleClass="form-horizontal">

    <div class="row form-container">

        <div class="row">
            <div class="form-group">
                <label for="externalReference" class="col-md-2 control-label"><smg:fmt value="claim.addclaimfolderhierarchy.claimfolderexternalreference"/></label>
                <div class="col-md-2">
                    <td><html:text property="claimFolderExternalReference" readonly="true" styleId="externalReference" styleClass="form-control input-sm"/></td>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="externalReference" class="col-md-2 control-label"><smg:fmt value="claim.addclaimfolderhierarchy.losseventexternalreference"/></label>
                <div class="col-md-2">
                    <td><html:text property="lossEventExternalReference" readonly="true" styleId="externalReference" styleClass="form-control input-sm"/></td>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="victimName" class="col-md-2 control-label"><smg:fmt value="claim.addclaimfolderhierarchy.victimname"/></label>
                <div class="col-md-2">
                    <td><html:text property="victimName" readonly="true" styleId="victimName" styleClass="form-control input-sm"/></td>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="claimantName" class="col-md-2 control-label"><smg:fmt value="claim.addclaimfolderhierarchy.claimantname"/></label>
                <div class="col-md-2">
                    <td><html:text property="claimantName" readonly="true" styleId="claimantName" styleClass="form-control input-sm"/></td>
                </div>
            </div>
        </div>


    </div>

    <c:if test="${addClaimFolderHierarchyForm.claimableBenefits != null}">
        <div class="table-responsive spacer">
            <c:url value="/secure/agreementversion/tla.do" var="agreementUrl"/>

            <display:table name="${addClaimFolderHierarchyForm.claimableBenefits}"
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
    </c:if>

    <div class="row spacer">
        <div class="col-md-12 text-center">
            <input type="hidden" name="method" value=".submit"/>
            <html:submit accesskey="s" titleKey="label.submitwithalts" styleId="findButton" styleClass="btn btn-primary btn-sm">
                <fmt:message key="button.submit"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>

</html:form>


