<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<!-- Email Display-->
<table class="structure" style="width:50%;">
    <tr>
        <td colspan="4"> 
            <div id="obj${count}">
                <div style="display: block;" id="emails">               
                    <table class="party-table" cellpadding="3px" style="width:100%;">
                        <tr>
                            <td class="lightgrey" style="color:#000066; width:44%">
                            <b><fmt:message key="page.party.contactpoints.label.emailaddress"/></b>
                            </td>
                            <td class="lightgrey" style="color:#000066; width:25%"><b><fmt:message key="page.party.contactpoints.label.startdate"/></b></td>
                            <td class="lightgrey" style="color:#000066; width:25%"><b><fmt:message key="page.party.contactpoints.label.enddate"/></b></td>
                            <td class="lightgrey" style="width:6%"><b></b></td>
                        </tr>
                        <logic:iterate id="emails" name="emailContactPoints">   
                            <tr style="empty-cells:show;" id="${emails.value.id}">
                                <td style="display: none;"><smg:fmtType value="${contactPref.typeId}" /></td>
                                        <td class="title"><c:out value="${emails.value.address}" /></td>
                                        <td class="title"><smg:fmt value="${emails.value.effectivePeriod.start}" /></td>
                                        <td class="title"><smg:fmt value="${emails.value.effectivePeriod.end}" /></td>
                                <c:if test="${! partyForm.isDelegating}">
                                    <td class="contactSelection">
                                        <input type="radio" name="contactSelected"
                                            value="${emails.value.id}"
                                            onclick="loadContactToEdit(this.form);"/>
                                    </td>
                                </c:if>
                            </tr>                       
                        </logic:iterate>                            
                    </table>                    
                </div>
            </div>      
        </td>
    </tr>   
    <tr></tr><tr></tr>
    <tr></tr><tr></tr>
</table>