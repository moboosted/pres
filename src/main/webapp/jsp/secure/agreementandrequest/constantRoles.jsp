<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<c:forEach items="${structuredActualDTO.constantRoles}" var="roleDTO" varStatus="roleDTOLoopStatus">
	<tr>
		<td title="<smg:fmtSpecificationObjectHint value="${roleDTO}"/>">
            <smg:fmtSpecificationObjectName value="${roleDTO}"/>
            <sup>(Constant)</sup>
		</td>	 
		
		<td><smg:fmtType value="${roleDTO.rolePlayer.typeId}" /></td>
	   	<td>&nbsp;</td>
        
		<%-- View link --%>
		<td>
			<c:choose>
				<c:when test="${!empty roleDTO.rolePlayer.objectId}">

                    <c:url value="${contextPath}.do?" var="rolePlayerUrl">
                        <c:param name="method" value=".viewConstantRolePlayer" />
                        <c:param name="contextObjectReference" value="${contextObjectReference}" />
                        <c:param name="path" value="${roleDTO.path}" />
                        <c:param name="kind" value="${roleDTO.kind}" />
                        <c:param name="conformanceTypes" value="${roleDTO.rolePlayer.typeId}" />
                    </c:url>

                    <a href="${rolePlayerUrl}" title='<c:out value="${roleDTO.rolePlayer}"/>' class="validate-props">
                        <c:out value="${roleDTO.rolePlayerDefaultName}" />
                    </a>

				</c:when>
				<c:otherwise>
					<div><fmt:message key="page.agreement.roles.noroleplayer" /></div>
				</c:otherwise>
			</c:choose>
		</td>
    
        <td>&nbsp;</td>
        <td>&nbsp;</td>
	</tr>
</c:forEach>