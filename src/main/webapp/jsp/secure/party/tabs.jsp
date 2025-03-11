<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:choose>
    <c:when test = "${!empty partyForm.fullName}">
        <ul class="nav nav-tabs" role="tablist" id="maintab">
            <li><a href="#" tabindex="0" rel="general" id="generalTab" onfocus="generalTabClicked()"><fmt:message key="party.page.tabs.label.generalparty"/></a></li>
            <li><a href="#" tabindex="1" rel="relationships" id="relationshipTab" onfocus="relationshipsTabClicked()"><fmt:message key="party.page.tabs.label.partyrelationhips"/></a></li>
            <li><a href="#" tabindex="2" rel="roles" id="roleTab" onfocus="rolesTabClicked()"><fmt:message key="party.page.tabs.label.partyroles"/></a></li>
            <li><a href="#" tabindex="3" rel="contact" id="contactTab" onfocus="contactTabClicked()"><fmt:message key="party.page.tabs.label.contactdetails"/></a></li>
            <li><a href="#" tabindex="4" rel="bank" id="bankTab" onfocus="bankTabClicked()"><fmt:message key="party.page.tabs.label.bankdetails"/></a></li>
            <li><a href="#" tabindex="5" rel="registrations" id="registrationTab" onfocus="registrationTabClicked()"><fmt:message key="party.page.tabs.label.registrations"/></a></li>
        </ul>
    </c:when>       
    <c:otherwise>
        <ul class="nav nav-tabs" role="tablist" id="maintab">
            <li><a href="#" tabindex="0" rel="general" id="generalTab" onfocus="generalTabClicked()"><fmt:message key="party.page.tabs.label.generalparty"/></a></li>
            <li><a href="#" tabindex="1" rel="relationships" id="relationshipTab"><fmt:message key="party.page.tabs.label.partyrelationhips"/></a></li>
            <li><a href="#" tabindex="2" rel="roles" id="roleTab"><fmt:message key="party.page.tabs.label.partyroles"/></a></li>
            <li><a href="#" tabindex="3" rel="contact" id="contactTab"><fmt:message key="party.page.tabs.label.contactdetails"/></a></li>
            <li><a href="#" tabindex="4" rel="bank" id="bankTab"><fmt:message key="party.page.tabs.label.bankdetails"/></a></li>
            <li><a href="#" tabindex="5" rel="registrations" id="registrationTab"><fmt:message key="party.page.tabs.label.registrations"/></a></li>
        </ul>
    </c:otherwise>  
</c:choose>
