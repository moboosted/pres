<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<script src="${pageContext.request.contextPath}/js/bootbox.min.js"></script>
<input type="hidden" id="deleteLabel" value='<smg:fmt value="label.delete"/>'/>
<input type="hidden" id="cancelLabel" value='<smg:fmt value="label.cancel"/>'/>
<%--
  We need to render the first role on the same line as the role list information.
  Once the first role has been rendered, we set this variable to indicate that subsequent
  roles need to be rendered in their own row (tr)
 --%>
<c:set var="renderRoleInSameRow" scope="page" value="${true}"/>

<c:forEach items="${roleListDTO.roles}" var="roleDTO" varStatus="roleDTOLoopStatus">

    <%
        boolean renderRoleInSameRow = (Boolean) pageContext.getAttribute("renderRoleInSameRow");
        if (!renderRoleInSameRow) {
    %>
    <tr>
        <%
            }
        %>
        <c:set var="rolePath" value="${roleDTO.path}" scope="page"/>

            <%-- View/update role player link --%>
        <td>
            <c:choose>
                <c:when test="${smg:canRolePlayerBeViewed(pageContext, roleListDTO, roleDTO)}">

                    <c:url value="${contextPath}.do?" var="rolePlayerUrl">
                        <c:param name="method" value=".viewRolePlayer" />
                         <c:param name="embeddedMode" value="${true}" />
                        <c:param name="contextObjectReference" value="${contextObjectReference}" />
                        <c:param name="path" value="${roleDTO.path}" />
                        <c:param name="kind" value="${roleDTO.kind}" />
                        <c:param name="conformanceTypes" value="${roleDTO.rolePlayer.typeId}" />
                        <c:forEach var="ct" items="${roleListDTO.conformanceTypes}">
                            <c:param name="conformanceTypes" value="${ct.id}"/>
                        </c:forEach>
                    </c:url>

                    <a href="${rolePlayerUrl}" title='<c:out value="${roleDTO.rolePlayer}"/>' id="role-kind-${roleDTO.kind.name}-${roleDTOLoopStatus.index}" class="validate-props">
                        <c:out value="${roleDTO.rolePlayerDefaultName}" />
                    </a>

                </c:when>
                <c:otherwise>
                    <span id="role-kind-${roleListDTO.kind.name}-${roleDTOLoopStatus.index}"><fmt:message
                            key="page.agreement.roles.noroleplayer"/></span>
                </c:otherwise>
            </c:choose>
        </td>

            <%-- The properties button - either view or update (if properties are present) --%>
        <td>

            <c:if test="${!empty roleDTO.properties}">

                <c:choose>
                    <c:when test="${roleListDTO.readOnly}">

                        <html:form action="${contextPath}.do">
                            <html:hidden property="contextObjectReference" value="${contextObjectReference}"/>
                            <html:hidden property="kind" value="${roleListDTO.kind}"/>
                            <c:forEach var="ct" items="${roleListDTO.conformanceTypes}">
                                <html:hidden property="conformanceTypes" value="${ct.id}"/>
                            </c:forEach>
                            <html:hidden property="path" value="${rolePath}"/>
                            <html:hidden property="method" value=".viewRoleProperties"/>
                            <html:submit styleClass="btn btn-default btn-xs"
                                    styleId="view-properties-role-kind-${roleListDTO.kind.name}-${roleDTOLoopStatus.index}">
                                <fmt:message key="button.viewproperties"/>
                            </html:submit>
                        </html:form>

                    </c:when>
                    <c:otherwise>

                        <html:form action="${contextPath}.do">
                            <html:hidden property="contextObjectReference" value="${contextObjectReference}"/>
                            <html:hidden property="kind" value="${roleListDTO.kind}"/>
                            <c:forEach var="ct" items="${roleListDTO.conformanceTypes}">
                                <html:hidden property="conformanceTypes" value="${ct.id}"/>
                            </c:forEach>
                            <html:hidden property="path" value="${rolePath}"/>
                            <html:hidden property="method" value=".updateRoleProperties"/>
                            <html:submit styleClass="btn btn-default btn-xs"
                                    styleId="update-properties-role-kind-${roleListDTO.kind.name}-${roleDTOLoopStatus.index}">
                                <fmt:message key="button.updateproperties"/>
                            </html:submit>
                        </html:form>

                    </c:otherwise>
                </c:choose>

            </c:if>

        </td>

            <%-- The delete button - allow the user to delete if we have a role player --%>
        <td>

            <c:if test="${smg:isRoleListEditable(pageContext, roleListDTO)}">
                <html:form action="${contextPath}.do">
                    <input type="hidden" id="confirmMessage" name="confirmMessage"
                           value='<smg:fmt value="page.agreement.message.confirmroledelete" arg="${roleListDTO.kind.name}" />'/>

                    <html:hidden property="contextObjectReference" value="${contextObjectReference}"/>
                    <html:hidden property="kind" value="${roleListDTO.kind}"/>
                    <c:forEach var="ct" items="${roleListDTO.conformanceTypes}">
                        <html:hidden property="conformanceTypes" value="${ct.id}"/>
                    </c:forEach>
                    <html:hidden property="path" value="${rolePath}"/>
                    <html:hidden property="method" value=".deleteRolePlayer"/>
                    <html:button property="method" styleId="delete-role-kind-${roleListDTO.kind.name}-${roleDTOLoopStatus.index}"
                                 disabled="${!smg:canRoleOrRolePlayerBeDeleted(pageContext, roleListDTO, roleDTO)}"
                                 styleClass="btn btn-danger btn-xs" onclick="deleteButton(this.form)">
                        <fmt:message key="button.delete"/>
                    </html:button>
                </html:form>

            </c:if>

        </td>

        <%
            if (!renderRoleInSameRow) {
        %>
    </tr>
    <%
        } else {
            // we've rendered the first row, so now generate a new row for each role
            pageContext.setAttribute("renderRoleInSameRow", false);
        }
    %>

</c:forEach>