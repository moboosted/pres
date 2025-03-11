<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<bean:define id="claimOffer" type="com.silvermoongroup.claim.domain.intf.IClaimOffer"
             name="claimNavigatorContentForm" property="claimOffer"/>

<div id="claimOfferDetails">
    <label for="claimOfferDetailsTable" class="groupHeading">
        <fmt:message key="claim.claimoffer"/></label>

    <table id="claimOfferDetailsTable" class="input-table">
        <tr>
            <td class="title"><fmt:message key="label.id"/></td>
            <td class="input"><smg:objectReference
                    value="${claimOffer.objectReference}" display="id"/></td>
            <td>&nbsp;</td>
            <td class="title"><fmt:message key="label.startdate"/> &rarr; <fmt:message
                    key="label.enddate"/></td>
            <td class="input"><smg:fmt value="${claimOffer.validFromDate}"/> &rarr; <smg:fmt
                    value="${claimOffer.validToDate}"/>
            </td>
        </tr>
        <tr>
            <td class="title"><fmt:message key="label.type"/></td>
            <td class="input"><smg:objectReference value="${claimOffer.objectReference}"
                                                   display="type"/></td>
            <td>&nbsp;</td>
            <td class="title"><fmt:message key="label.currentstatus"/></td>
            <td class="input"><smg:fmt value="${claimOffer.currentClaimOfferStatus.state}" /></td>
        </tr>
        <tr>
            <td class="title"><fmt:message key="label.externalreference"/></td>
            <td class="input"><c:out value="${claimOffer.externalReference}"/></td>
            <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="3">&nbsp;</td>
            <td class="title"><fmt:message key="claim.claimoffer.settlementmethod"/></td>
            <td class="input"><smg:fmt value="${claimOffer.settlementMethod}"/></td>
        </tr>
        <tr>
            <td class="title"><fmt:message key="claim.claimoffer.initiator"/></td>
            <td class="input"><smg:fmt value="${claimOffer.initiator}"/></td>
            <td>&nbsp;</td>
            <td class="title"><fmt:message key="claim.claimoffer.quantity"/></td>
            <td class="input"><c:out value="${claimOffer.quantity}" /></td>
        </tr>

    </table>
</div>

<div id="tab-wrapper">
    <ul class="nav nav-tabs" id="claimTabs">
        <li><a href="#roleTab" class="active" data-toggle="tab"
               data-url="${pageContext.request.contextPath}/secure/claim/claimnavigator/claimoffer/roles.do">
            <fmt:message key="label.roles"/>
            <c:if test="${!empty claimOffer.rolesInClaimOffer}">
                <span class="badge badge-info"><c:out value="${fn:length(claimOffer.rolesInClaimOffer)}" /> </span>
            </c:if>
        </a>
        </li>
    </ul>

    <div class="tab-content">
        <div id="roleTab" class="active tab-pane">
            <img src='${pageContext.request.contextPath}/images/wait.gif'/> <fmt:message key="label.loadingdotdotdot"/>
        </div>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/claim/navigator/claim-tabs.js"></script>
