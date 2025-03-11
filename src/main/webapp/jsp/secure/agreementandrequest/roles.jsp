<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<c:choose>

    <c:when test="${(empty structuredActualDTO.constantRoles) && (empty structuredActualDTO.roleLists)}">

        <div class="empty">
            <smg:fmt value="label.norolesdefined"/>
        </div>

    </c:when>

    <c:otherwise>
        <div class="table-responsive">
            <table class="data-table table table-striped table-condensed">

                <thead>
                <tr>
                    <th colspan="1"><fmt:message key="label.rolename"/></th>
                    <th colspan="1"><fmt:message
                            key="label.roletype"/></th>
                    <th colspan="1">&nbsp;</th>
                    <th colspan="1">Name</th>
                    <th colspan="2">&nbsp;</th>
                </tr>
                </thead>

                <tbody>
                <%@ include file="constantRoles.jsp" %>
                <%@ include file="rolelist.jsp" %>
                </tbody>

            </table>
        </div>

    </c:otherwise>
</c:choose>