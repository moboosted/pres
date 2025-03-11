<%@page import="com.silvermoongroup.fsa.web.common.callback.CallBackUtility" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<c:if test="${!empty partyForm.fullName}">

  <%@include file="../partyTypes/partyHeader.jsp" %>
  <c:if test="${!partyForm.isDelegating}">
    <%@include file="registrationTypes.jsp" %>
  </c:if>
  <%@include file="registrationCapture.jsp" %>

  <%@include file="partyReg.jsp" %>
  <%@include file="birth.jsp" %>
  <%@include file="accreditation.jsp" %>
  <%@include file="death.jsp" %>
  <%@include file="education.jsp" %>
  <%@include file="nationalPerson.jsp" %>
  <%@include file="licence.jsp" %>
  <%@include file="marriage.jsp" %>
  <br/>
  <table>
    <tr>
      <td colspan="4" align="center">
        <% if (!CallBackUtility.isCallBackEmpty(request, response)) {%>
          <html:submit styleId="registrationBackButton" property="method" styleClass="btn btn-link btn-sm">
              <fmt:message key="button.cancel"/>
          </html:submit>
        <% } %>
      </td>
    </tr>
  </table>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/party/registration.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/party/expandCollapse.js"></script>
</c:if>