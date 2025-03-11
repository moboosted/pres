<%@page import="com.silvermoongroup.common.enumeration.EnumerationType"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<c:if test="${!empty partyForm.bankDetailList}">
<table class="party-table">		
	<tr>
		<td>
			<table class="party-table" cellpadding="3">
				<tr>
					<td class="lightgrey" style="color:#000066;width:25%;height:27px;"><label>
					<fmt:message key="page.party.bank.label.accounttype"/>
					</label></td>
					<td class="lightgrey" style="width:25%">
						<html:select styleId="bankAccountTypes" styleClass="form-control input-sm" property="accountType">
					        <smg:enumOptions enumTypeId="<%=EnumerationType.BANK_ACCOUNT_TYPE.getValue()%>"  showTerminated = "true"/>
						</html:select>
					</td>
					<td class="lightgrey" style="width:50%">
				</tr>
			</table>
		</td>
	</tr>
</table>
</c:if>
