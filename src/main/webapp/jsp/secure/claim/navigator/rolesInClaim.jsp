<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<jsp:useBean id="rolesInClaimForm" scope="request"
             type="com.silvermoongroup.fsa.web.claimmanagement.form.RolesInClaimForm"/>

<c:choose>
    <c:when test="${!empty rolesInClaimForm.rolesInClaim}">
        <table class="table table-striped table-hover table-condensed" id="roleTable">
            <tr>
                <th><fmt:message key="label.id" /></th>
                <th><fmt:message key="label.roletype" /></th>
                <th><fmt:message key="label.roleplayertype" /></th>
                <th><fmt:message key="label.roleplayerdetails" /></th>
            </tr>

            <c:forEach var="roleInClaimOffer" items="${rolesInClaimForm.rolesInClaim}">
                <tr>
                    <td><smg:objectReference value="${roleInClaimOffer.objectReference}" display="id"/></td>
                    <td><smg:objectReference value="${roleInClaimOffer.objectReference}" display="type"/></td>
                    <td><smg:objectReference value="${roleInClaimOffer.rolePlayerObjectReference}" display="type"/></td>
                    <td><c:out value="${roleInClaimOffer.rolePlayerName}"/></td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 no-results"><fmt:message key="page.claimnavigator.label.noclaimroles" /></div>
            </div>
        </div>
    </c:otherwise>

</c:choose>