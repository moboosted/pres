<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<bean:define id="claimFolder" type="com.silvermoongroup.claim.domain.intf.IClaimFolder" name="claimNavigatorContentForm"
             property="claimFolder"/>

<div id="claimFolderDetails">
    <label for="claimFolderDetailsTable" class="groupHeading">
        <fmt:message key="claim.claimfolder"/></label>

    <table id="claimFolderDetailsTable" class="input-table">
        <tr>
            <td class="title"><fmt:message key="label.id"/></td>
            <td class="input"><smg:objectReference
                    value="${claimFolder.objectReference}" display="id"/></td>
            <td class="spacer">&nbsp;</td>
            <td class="title"><fmt:message key="label.startdate"/> &rarr; <fmt:message
                    key="label.enddate"/></td>
            <td class="input"><smg:fmt value="${claimFolder.startDate}"/> &rarr; <smg:fmt
                    value="${claimFolder.endDate}"/>
            </td>
        </tr>
        <tr>
            <td class="title"><fmt:message key="label.type"/></td>
            <td class="input"><smg:objectReference value="${claimFolder.objectReference}"
                                                   display="type"/></td>
            <td class="spacer">&nbsp;</td>
            <td class="title"><fmt:message key="label.currentstatus"/></td>
            <td class="input"><smg:fmt value="${claimFolder.currentClaimFolderStatus.state}" /></td>
        </tr>
        <tr>
            <td class="title"><fmt:message key="label.externalreference"/></td>
            <td class="input"><c:out value="${claimFolder.externalReference}"/></td>
            <td colspan="3" class="spacer">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="5">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2" class="title"><label for="description"><fmt:message key="label.description"/></label></td>
            <td>&nbsp;</td>
            <td class="title"><fmt:message key="claim.claim.accidentfault"/></td>
            <td class="input"><smg:fmt value="${claimFolder.accidentFault}"/></td>
        </tr>
        <tr>
            <td colspan="2" rowspan="3"><textarea id="description"
                                                  style=" min-width: 99%; width: 99%; min-height: 100%; height: 100%; resize: none;"
                                                  readonly><c:out value="${claimFolder.description}"/></textarea></td>
            <td class="spacer">&nbsp;</td>
            <td class="title"><fmt:message key="claim.claim.recoverystrategy"/></td>
            <td class="input"><smg:fmt value="${claimFolder.recoveryStrategy}"/></td>
        </tr>
        <tr>
            <td class="spacer">&nbsp;</td>
            <td class="title"><fmt:message key="claim.claim.coveragepercentage"/></td>
            <td class="input"><smg:fmt value="${claimFolder.coveragePercentage}"/></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td class="title"><fmt:message key="claim.claim.settlementcurrencyselection"/></td>
            <td class="input"><smg:fmt value="${claimFolder.settlementCurrencySelection}"/></td>
        </tr>
    </table>
</div>

<div id="tab-wrapper">
    <ul class="nav nav-tabs" id="claimTabs">
        <li><a href="#roleTab" class="active" data-toggle="tab"
               data-url="${pageContext.request.contextPath}/secure/claim/claimnavigator/claim/roles.do">
            <fmt:message key="label.roles"/>
            <c:if test="${!empty claimFolder.rolesInClaim}">
                <span class="badge badge-info"><c:out value="${fn:length(claimFolder.rolesInClaim)}" /> </span>
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
