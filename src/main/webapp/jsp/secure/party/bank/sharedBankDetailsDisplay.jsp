<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<table class="party-table">
    <tr>
        <td style="width:15%;">
            <label class="groupHeading" style="vertical-align:bottom;">
            <fmt:message key="page.party.displaybankdetails.label.sharedaccountdetails"/>
            </label>
        </td>                   
    </tr>
    <tr>
        <td colspan="4">
            <table class="party-table" cellpadding="3px">
            	<c:choose>
            		<c:when test="${!empty partyForm.searchedBankDetailList}">
	                   <tr bgcolor="#CCCCCC">
	                       <td class="lightgrey" style="color:#000066;width:15%">
	                       <fmt:message key="page.party.displaybankdetails.label.partyrole"/>
	                       </td>
	                       <td class="lightgrey" style="color:#000066;width:18%">
	                       <fmt:message key="page.party.displaybankdetails.label.contactpreference"/>
	                       </td>
	                       <td class="lightgrey" style="color:#000066;width:18%">
	                       <fmt:message key="page.party.displaybankdetails.label.agreementno"/>
	                       </td>
	                       <td class="lightgrey" style="color:#000066;width:18%">
	                       <fmt:message key="page.party.bank.label.cardnumber"/>
	                       </td>
	                       <td class="lightgrey" style="color:#000066;width:19%">
	                       <fmt:message key="page.party.bank.label.cardholdername"/>
	                       </td>
	                       <td class="lightgrey" style="color:#000066;width:16%">
	                       <fmt:message key="page.party.bank.label.cardtype"/>
	                       </td>
	                       <td class="lightgrey" style="color:#000066;width:12%">
	                       <fmt:message key="page.party.bank.label.cardexpirydate"/>
	                       </td>
	                   </tr>
                       <c:forEach items="${partyForm.searchedBankDetailList}" var="bankDetail"> 
	                       <tr>
	                           <td><c:out value="${banks.partyRole}"/></td>
	                           <td><c:out value="${banks.preference}"/></td>
	                           <td><c:out value="${banks.contextReference}"/></td>
	                           <td><c:out value="${banks.accountNumber}"/></td>
	                           <td><c:out value="${banks.accountHolder}"/></td>
	                           <td><c:out value="${banks.accountType}"/></td>
	                           <td><smg:fmt value="${banks.expirtyDate}" /></td> 
	                       </tr>
	                   </c:forEach>
            		</c:when>
            		<c:otherwise>
	                   <tr bgcolor="#CCCCCC">
	                       <td class="lightgrey" style="color:#000066;width:100%">
	                       <fmt:message key="page.party.displaybankdetails.message.nobankdetails"/>
	                       </td>
	                   </tr>
            		</c:otherwise>
            	</c:choose>
            </table>
        </td>
    </tr>
</table>