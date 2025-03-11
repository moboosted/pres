<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<jsp:useBean id="getOrganisationForm" scope="request"
             type="com.silvermoongroup.fsa.web.party.form.GetOrganisationForm"/>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/icons/sm.ico"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.10.4.custom.css"/>
    <link rel="stylesheet" type="text/css" media="screen"
          href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" media="screen"
          href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/main.css"/>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.10.4.min.js"></script>
    <smg:jQueryDatePickerScript/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
    <title>Find Organisation</title>
</head>

<body>
<div id="main" class="container-fluid">
    <p>&nbsp;</p>
    <label class="groupHeading" style="align:bottom">Organisation Search</label>
    <%-- Find organisation --%>
    <form action="${pageContext.request.contextPath}/secure/party/getOrganisation.do" method="POST">
        <div style="display:block;" class="structure" id="findPartyOrg">
            <table class="party-table" cellpadding="3">
                <tr>
                    <td>
                        <label><fmt:message key="page.party.label.organisationname"/></label>
                    </td>
                    <td>
                        <input name="txtSearchOrgFullName" class="form-control input-sm" type="text"/>
                    </td>
                    <td>
                        <label><fmt:message key="page.party.label.organisationtype"/></label>
                    </td>
                    <td>
                        <select name="txtSearchOrgType" class="form-control input-sm">
                            <option value="">-Select-</option>
                            <c:forEach var="types" items="${getOrganisationForm.organisationTypes}">
                                <option value="${types.name}">${types.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr></tr>
                <tr>
                    <td colspan="4" align="center">
                        <input type="submit" name="method" value="<fmt:message key="button.search" />" class="btn btn-primary btn-sm">
                    </td>
                </tr>
            </table>
        </div>
    </form>

    <c:if test="${getOrganisationForm.organisationLists.size() > 0}">

        <hr/>

        <table class="structure">
            <tr>
                <td style="width:15%;vertical-align:bottom">
                    <label class="groupHeading" style="align:bottom">
                        <fmt:message key="page.party.searchresults.label.personsearchresults"/>
                    </label>
                </td>
            </tr>
        </table>

        <span class="pagebanner"> ${getOrganisationForm.organisationLists.size() } items found, displaying all items. </span>

        <div class="table-responsive">
            <display:table name="${getOrganisationForm.organisationLists}" id="row">
                <c:url value="/secure/party/getOrganisation.do" var="findURL">
                    <c:param name="objectReference" value="${row.objectReference}"/>
                    <c:param name="fullName" value="${row.fullName}"/>
                    <c:param name="partyType" value="${row.partyType}"/>
                </c:url>
                <display:column titleKey="page.party.label.fullname">
                    <a href="${findURL}"/> ${row.fullName}</a>
                </display:column>
                <display:column titleKey="page.party.label.type">
                    <a href="${findURL}"/> ${row.partyType}</a>
                </display:column>
            </display:table>
        </div>
    </c:if>
</div>

<script>
    if (!!"${param.objectReference }") {
        var returnValue = {
            objectReference: "${param.objectReference }",
            fullName: "${param.fullName}"
        };

        window.opener.returnValueObj.getReturnValue(JSON.stringify(returnValue));
        window.close();
    }

    $("input[name=txtSearchOrgFullName]").focus();
</script>
</body>
</html>