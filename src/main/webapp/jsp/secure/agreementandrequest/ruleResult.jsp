<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<div class="table-responsive">
	<table class="table table-striped table-condensed">
        <thead>
    	<tr>
			<th><fmt:message key="page.agreement.ruleresults.label.kind" /></th>
            <th ><fmt:message key="page.agreement.ruleresults.label.overridable" /></th>
			<th><fmt:message key="page.agreement.ruleresults.label.message" /></th>
		</tr>
        </thead>
        <tbody>
		<c:forEach items="${requestViewForm.structuredActualDTO.ruleResultDTO}" var="ruleResult">
			<tr>
				<td title="<c:out value="${ruleResult.kind.name} (${ruleResult.kind.id})" />"><smg:fmt value="${ruleResult.kind}" /></td>
                <td><smg:fmt value="label.yesno.${ruleResult.overridable}" /></td>
				<td><smg:fmt value="${ruleResult.message}" arguments="${ruleResult.parameters}" /></td>
			</tr>
		</c:forEach>
        </tbody>
  </table>
</div>