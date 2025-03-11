<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%-- inserts the main page header. Visually controlled in Style sheet  --%>

    <c:if test="${! empty pageContext.request.userPrincipal}">
      <div style="padding-top: 1.7em; padding-right: 5em; color: white; font-size: 12px; font-weight: bold; text-align: right;">
        <a style="color: white; font-size: 12px; font-weight: bold;" href="<%=request.getContextPath()%>/secure/user/preferences.do"
          id="userPreferencesLink"><c:out value="${userProfile.principal.name}" /></a>

          &nbsp;&nbsp;|&nbsp;&nbsp;

          <a style="color: white; font-size: 12px; font-weight: bold;" href="<%=request.getContextPath()%>/logout.do" id="logoutMenuLink"><fmt:message
            key="menu.logout" /></a>
      </div>
    </c:if>