<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<div id="bank_capture" style="display: none;">
<table colspan= "3" class="party-table" cellpadding="3px">
	<tr>
		<td class="mandatory" style="width:150px">
		<label><fmt:message key="page.party.bank.label.cardnumber"/></label>
		</td>
		<td class="input">
			<html:text styleId="accountNumber" styleClass="form-control input-sm" property="accountNumber" disabled="false" />
		</td>
		<td class="mandatory" style="width:150px">
		<label>
			<fmt:message key="page.party.bank.label.cardholdername"/>
		</label>
		</td>
		<td class="input">
			<html:text styleId="accountHolder" styleClass="form-control input-sm" property="accountHolder" disabled="false" />
		</td>                                                                          
		<td class="mandatory">
		<label>
			<fmt:message key="page.party.bank.label.cardexpirydate"/>
		</label>
		</td>
		<td class="input">
            <html:text styleId="crCardExpiryDate" styleClass="form-control input-sm" property="crCardExpiryDate" maxlength="4" />
		</td>
		<td class="mandatory">
		<label>
			<fmt:message key="page.party.bank.label.accounttype"/>
		</label>
		</td>
		<td class="input">
			<html:select styleId="bankAccountTypes" styleClass="form-control input-sm" property="accountType">
				<smg:enumOptions enumTypeId="<%=EnumerationType.BANK_ACCOUNT_TYPE.getValue()%>"  showTerminated = "true"/>
			</html:select>
		</td>
	</tr>

	<tr>
		<td class="title">
			<label><fmt:message key="page.party.bank.label.bankname"/></label>
		</td>
		<td class="input">
			<html:select styleId="bank" styleClass="form-control input-sm" property="bank">
				<smg:enumOptions enumTypeId="<%=EnumerationType.BANKING_INSTITUTION.getValue()%>"  showTerminated = "true"/>
			</html:select>
		</td>
		<td class="title">
			<label>
				<fmt:message key="page.party.bank.label.bankbranchreference"/>
			</label>
		</td>
		<td class="input">
			<html:text styleId="bankBranchReference" styleClass="form-control input-sm" property="bankBranchReference"/>
		</td>
		<td class="title">
			<label>
				<fmt:message key="page.party.bank.label.bankbranchcode"/>
			</label>
		</td>
		<td class="input">
			<html:text styleId="bankBranchCode" styleClass="form-control input-sm" property="bankBranchCode"/>
		</td>
		<td class="title">
			<label>
				<fmt:message key="page.party.bank.label.bankbranchname"/>
			</label>
		</td>
		<td class="input">
			<html:text styleId="bankBranchName" styleClass="form-control input-sm" property="bankBranchName"/>
		</td>
	</tr>

	<tr>
		<td colspan="4" align="right">
			<html:submit styleClass="btn btn-primary btn-sm" property="method">
				<fmt:message key="page.party.capturebankdetails.action.addFinancialAccountDetails"/>
			</html:submit>
		</td>
		<div id="updateFadButton" style="display: none">
		   <td colspan="4" align="left">
			   <html:submit styleClass="btn btn-primary btn-sm" property="method">
				   <fmt:message key="page.party.capturebankdetails.action.updateFinancialAccountDetails"/>
			   </html:submit>
		   </td>
		</div>
    </tr>
	
</table>
</div>