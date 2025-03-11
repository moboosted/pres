<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<bean:define id="elementaryClaim" type="com.silvermoongroup.claim.domain.intf.IElementaryClaim"
             name="claimNavigatorContentForm" property="elementaryClaim"/>

<div id="elementaryClaimDetails">
    <label for="elementaryClaimDetailsTable" class="groupHeading">
        <fmt:message key="claim.elementaryclaim"/></label>

    <table id="elementaryClaimDetailsTable" class="input-table">
        <tr>
            <td class="title"><fmt:message key="label.id"/></td>
            <td class="input"><smg:objectReference
                    value="${elementaryClaim.objectReference}" display="id"/></td>
            <td>&nbsp;</td>
            <td class="title"><fmt:message key="label.startdate"/> &rarr; <fmt:message
                    key="label.enddate"/></td>
            <td class="input"><smg:fmt value="${elementaryClaim.startDate}"/> &rarr; <smg:fmt
                    value="${elementaryClaim.endDate}"/>
            </td>
        </tr>
        <tr>
            <td class="title"><fmt:message key="label.type"/></td>
            <td class="input"><smg:objectReference value="${elementaryClaim.objectReference}"
                                                   display="type"/></td>
            <td>&nbsp;</td>
            <td class="title"><fmt:message key="label.currentstatus"/></td>
            <td class="input"><smg:fmt value="${elementaryClaim.currentElementaryClaimStatus.state}" /></td>
        </tr>
        <tr>
            <td class="title"><fmt:message key="label.externalreference"/></td>
            <td class="input"><c:out value="${elementaryClaim.externalReference}"/></td>
            <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="5">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2" class="title"><label for="description"><fmt:message key="label.description"/></label></td>
            <td>&nbsp;</td>
            <td class="title"><fmt:message key="claim.claim.accidentfault"/></td>
            <td class="input"><smg:fmt value="${elementaryClaim.accidentFault}"/></td>
        </tr>
        <tr>
            <td colspan="2" rowspan="3">
                <textarea id="description"
                          style=" min-width: 99%; width: 99%; min-height: 100%; height: 100%; resize: none;"
                          readonly><c:out value="${elementaryClaim.description}"/></textarea></td>
            <td>&nbsp;</td>
            <td class="title"><fmt:message key="claim.claim.recoverystrategy"/></td>
            <td class="input"><smg:fmt value="${elementaryClaim.recoveryStrategy}"/></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td class="title"><fmt:message key="claim.claim.coveragepercentage"/></td>
            <td class="input"><smg:fmt value="${elementaryClaim.coveragePercentage}"/></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td class="title"><fmt:message key="claim.claim.settlementcurrencyselection"/></td>
            <td class="input"><smg:fmt value="${elementaryClaim.settlementCurrencySelection}"/></td>
        </tr>

    </table>
</div>

<div id="tab-wrapper">
    <ul class="nav nav-tabs" id="claimTabs">
        <li><a href="#roleTab" class="active" data-toggle="tab"
               data-url="${pageContext.request.contextPath}/secure/claim/claimnavigator/claim/roles.do">
            <fmt:message key="label.roles"/>
            <c:if test="${!empty elementaryClaim.rolesInClaim}">
                <span class="badge badge-info"><c:out value="${fn:length(elementaryClaim.rolesInClaim)}" /> </span>
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
