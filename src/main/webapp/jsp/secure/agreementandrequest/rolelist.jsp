<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<c:forEach items="${structuredActualDTO.roleLists}" var="roleListDTO">
    <c:choose>
        <c:when test="${fn:length(roleListDTO.roles) > 0}">
            <c:set var="rowspan" value="${fn:length(roleListDTO.roles)}" scope="page"/>
        </c:when>
        <c:otherwise>
            <c:set var="rowspan" value="1" scope="page"/>
        </c:otherwise>
    </c:choose>
	<tr>
		<td rowSpan="<c:out value="${rowspan}" />" title="<smg:fmtSpecificationObjectHint value="${roleListDTO}"/>">
            <smg:fmtSpecificationObjectName value="${roleListDTO}"/>
            <sup style="font-weight: normal;">(<c:out value="${smg:getRoleListCardinality(pageContext, roleListDTO)}" />)</sup>     
		</td>
 
		<td rowSpan="<c:out value="${rowspan}" />">
          <c:out value="${smg:formatConformanceTypes(pageContext, roleListDTO)}" />
        </td>
    
        <%-- Add role button --%>
        <td rowSpan="<c:out value="${rowspan}" />">

          <c:if test="${smg:isRoleListEditable(pageContext, roleListDTO)}">
            <html:form action="${contextPath}.do">
              <c:if test="${!empty currentPath}">
                <html:hidden property="path" value="${currentPath}" />
              </c:if>
              <c:forEach var="ct" items="${roleListDTO.conformanceTypes}">
                <html:hidden property="conformanceTypes" value="${ct.id}" />
              </c:forEach>
              <html:hidden property="contextObjectReference" value="${contextObjectReference}" />
              <html:hidden property="kind" value="${roleListDTO.kind}" />
              <html:hidden property="method" value=".addRole" />
              <html:submit styleId="add-role-kind-${roleListDTO.kind.name}" disabled="${!smg:canRolePlayerBeAdded(pageContext, roleListDTO)}"
                      styleClass="btn btn-default btn-xs">
                <fmt:message key="page.requestview.action.addrole" />
              </html:submit>
            </html:form>
          </c:if>
        
        </td>
        
        <c:choose>
          <c:when test="${empty roleListDTO.roles}">
            <td >&nbsp;</td>
            <td >&nbsp;</td>
            <td >&nbsp;</td>
          </c:when>
          <c:otherwise>
            <%@include file="role.jsp" %>            
          </c:otherwise>
        </c:choose>
        
      
	</tr>
</c:forEach>