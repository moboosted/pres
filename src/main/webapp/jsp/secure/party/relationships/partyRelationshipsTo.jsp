<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<c:if test="${! empty partyForm.partyRelationshipsToMap}">
<table class="party-table" style="width:60%">
    <tr>
        <td>
            <div style="display: block;">
                <table cellpadding="6px">
                    <tr>
                        <td class="agreementExpand" width="18px">
                        </td>
                        <td class="lightgrey" style="color:#000066;">
                            <label id="lblRelationshipsTo">
                                <fmt:message key="page.party.relationships.label.relationshipsfrom" />
                            </label>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="obj3">
                <div style="display: block;" id="relationships">
                    <table class="table table-striped">
                        <c:if test="${! empty partyForm.partyRelationshipsToMap}">
                        <tr>
                            <td class="lightgrey" style="color:#000066;width:25%"><b><fmt:message key="page.party.relationships.label.relatedfrom" /></b></td>
                            <td class="lightgrey" style="color:#000066;width:25%"><b><fmt:message key="page.party.relationships.label.nature" /></b></td>
                            <td class="lightgrey" style="color:#000066;width:20%"><b><fmt:message key="page.party.relationships.label.description" /></b></td>
                            <td class="lightgrey" style="color:#000066;width:15%"><b><fmt:message key="page.party.relationships.label.startdate" /></b></td>
                            <td class="lightgrey" style="color:#000066;width:15%"><b><fmt:message key="page.party.relationships.label.enddate" /></b></td>
                            <td class="lightgrey" style="width:2%"><b></b></td>
                        </tr>
                            <c:forEach var="relationshipsTo" items="${partyForm.partyRelationshipsToMap}">
                                <tr id="editRel${relationshipsTo.key}">
                                    <td class="title"><c:out value="${relationshipsTo.value.relatedFrom}" /></td>
                                    <td class="title"><c:out value="${relationshipsTo.value.type}"/></td>
                                    <td class="title"><c:out value="${relationshipsTo.value.description}"/></td>
                                    <td class="title"><c:out value="${relationshipsTo.value.startDate}"/></td>
                                    <td class="title"><c:out value="${relationshipsTo.value.endDate}"/></td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </table>
                </div>
            </div>
        </td>
    </tr>
</table>
</c:if>
