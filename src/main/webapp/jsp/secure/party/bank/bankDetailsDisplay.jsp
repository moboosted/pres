<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<table class="party-table">
    <tr>
        <td style="width:15%;">
            <label class="groupHeading" style="vertical-align:bottom;">
            <fmt:message key="page.party.bank.label.accountdetails"/>
            </label>
        </td>                   
    </tr>
    <tr>
        <td colspan="4">
            <table class="party-table" cellpadding="3px">
            	<c:choose>
            		<c:when test="${!empty partyForm.bankDetailList}">
	                   <tr bgcolor="#CCCCCC">
	                       <td class="lightgrey" style="color:#000066;width:12%">
	                       <fmt:message key="page.party.bank.label.cardnumber"/>
	                       </td>
	                       <td class="lightgrey" style="color:#000066;width:12%">
	                       <fmt:message key="page.party.bank.label.cardholdername"/>
	                       </td>
	                       <td class="lightgrey" style="color:#000066;width:12%">
	                       <fmt:message key="page.party.bank.label.cardtype"/>
	                       </td>
	                       <td class="lightgrey" style="color:#000066;width:8%">
	                       <fmt:message key="page.party.bank.label.cardexpirydate"/>
	                       </td>
						   <td class="lightgrey" style="color:#000066;width:12%">
							   <fmt:message key="page.party.bank.label.bankname"/>
						   </td>
						   <td class="lightgrey" style="color:#000066;width:12%">
							   <fmt:message key="page.party.bank.label.bankbranchreference"/>
						   </td>
						   <td class="lightgrey" style="color:#000066;width:12%">
							   <fmt:message key="page.party.bank.label.bankbranchcode"/>
						   </td>
						   <td class="lightgrey" style="color:#000066;width:12%">
							   <fmt:message key="page.party.bank.label.bankbranchname"/>
						   </td>
	                       <td class="lightgrey" style="width:2%"><b></b></td>                        
	                   </tr>
                      <c:forEach items="${partyForm.bankDetailList}" var="bankDetail"> 
	                       <tr>
	                           <td><c:out value="${bankDetail.accountNumber}" /></td>
	                           <td><c:out value="${bankDetail.accountHolder}" /></td>
	                           <td><c:out value="${bankDetail.accountType}" /></td>
	                           <td><c:out value="${bankDetail.expirtyDate}" /></td>
							   <td><c:out value="${bankDetail.bank}" /></td>
							   <td><c:out value="${bankDetail.bankBranchReference}" /></td>
							   <td><c:out value="${bankDetail.bankBranchCode}" /></td>
							   <td><c:out value="${bankDetail.bankBranchName}" /></td>
							   <c:if test="${! partyForm.isDelegating}">
								   <td style="background-color:#6DA1CF;text-align:center;">
									   <input name="fadSelected" id="${bankDetail.key}" style="width:10px" type="radio"
										value="${bankDetail.id}"
										/>
								   </td>
							   </c:if>
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