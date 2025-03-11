<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<jsp:useBean id="getPersonForm" scope="request" type="com.silvermoongroup.fsa.web.party.form.GetPersonForm"/>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/icons/sm.ico"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.10.4.custom.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/main.css"/>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.10.4.min.js"></script>
	<smg:jQueryDatePickerScript/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
	<title>Find Person</title>
</head>

<body>
<div id="main" class="container-fluid">
    <p>&nbsp;</p>
    <label class="groupHeading" style="align:bottom">Person Search</label>
    <%-- Find person --%>
    <form action="${pageContext.request.contextPath}/secure/party/getPerson.do" method="POST" >
           <div style="display: block;" class="structure" id="findPartyPerson">
            <table class="party-table" cellpadding="3">
                <tr></tr>
                <tr>
                    <td><label class="control-label"><fmt:message key="page.party.label.surname" /></label></td>
                    <td>
                        <input type="text" name="txtSearchSurname" value="${param.txtSearchSurname}" class="form-control input-sm" />
                    </td>
                    <td><label class="control-label"><fmt:message key="page.party.label.firstname" /></label></td>
                    <td>
                        <input type="text" name="txtSearchFirstName" value="${param.txtSearchFirstName}" class="form-control input-sm" />
                    </td>
                </tr>
                <tr>
                    <td><label class="control-label"><fmt:message key="page.party.label.middlename" /></label></td>
                    <td>
                        <input type="text" name="txtSearchMiddleName" value="${param.txtSearchMiddleName}" class="form-control input-sm" />
                    </td>
                    <td></td>
                    <td></td>
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

    <c:if test="${getPersonForm.personLists.size() > 0}">

        <hr />

        <table class="structure">
            <tr>
                <td style="width:15%;vertical-align:bottom">
                    <label class="groupHeading" style="align:bottom"><fmt:message key="page.party.searchresults.label.personsearchresults" /></label>
                </td>
            </tr>
        </table>

        <span class="pagebanner"> ${getPersonForm.personLists.size() } items found, displaying all items. </span>

		<div class="table-responsive">
			<display:table name="${getPersonForm.personLists}" id="row">
				<c:url value="/secure/party/getPerson.do" var="findURL">
					<c:param name="objectReference"	value="${row.objectReference}"	/>
					<c:param name="title" 			value="${row.title}"			/>
					<c:param name="surname" 		value="${row.surname}"			/>
					<c:param name="firstname" 		value="${row.firstname}"		/>
					<c:param name="middlename" 		value="${row.middlename}"		/>
				</c:url>
				<display:column titleKey="page.party.label.surname">
	                <a href="${findURL}" /> ${row.surname}</a>
	            </display:column>
				<display:column titleKey="page.party.label.firstname">
	                <a href="${findURL}"/> ${row.firstname}</a>
	            </display:column>
				<display:column titleKey="page.party.label.middlename">
	                <a href="${findURL}"/> ${row.middlename}</a>
	            </display:column>
				<display:column property="initials" titleKey="page.party.label.initials"/>
				<display:column property="title" titleKey="page.party.label.title"/>
				<display:column property="birthDate" titleKey="page.party.label.birthdate" headerClass="order1"/>
			</display:table>
		</div>
    </c:if>
</div>

<script>
	if( !!"${param.objectReference }"  ) {
		var returnValue = {
			objectReference	: "${param.objectReference }",
			title			: "${param.title}",
			surname			: "${param.surname}",
			firstname		: "${param.firstname}",
			middlename		: "${param.middlename}"
		};
		
		window.opener.returnValueObj.getReturnValue(JSON.stringify(returnValue));
		window.close();
	}
	
	$("input[name=txtSearchSurname]").focus();
</script>
</body>
</html>