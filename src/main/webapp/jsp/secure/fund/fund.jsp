<%@ page import="com.silvermoongroup.fsa.web.common.callback.CallBackUtility" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:useBean id="fundForm" scope="request" type="com.silvermoongroup.fsa.web.fund.FundForm"/>

<c:url value="/secure/account.do" var="fundPayableAccountUrl">
    <c:param name="accountObjectReference" value="${fundForm.fund.fundPayableAccount.objectReference}"/>
</c:url>
<c:url value="/secure/account.do" var="fundReceivableAccountUrl">
    <c:param name="accountObjectReference" value="${fundForm.fund.fundReceivableAccount.objectReference}"/>
</c:url>
<c:url value="/secure/account.do" var="unitTransactionAccountUrl">
    <c:param name="accountObjectReference" value="${fundForm.fund.unitTransactionAccount.objectReference}"/>
</c:url>

<div class="row">
    <label for="fundDetailsTable" class="groupHeading col-md-12"> <c:out value="${fundForm.fund.name}"/></label>
</div>
<div id="fundDetailsTable" class="row spacer form-container">

    <%--Left Column--%>
    <div class="col-md-6">

        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="label.id"/></label></div>
            <div class="col-md-8"><smg:objectReference value="${fundForm.fund.objectReference}"/></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="label.type"/></label></div>
            <div class="col-md-8"><smg:objectReference value="${fundForm.fund.objectReference}" display="type"/></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="account.fund.name"/></label></div>
            <div class="col-md-8"><c:out value="${fundForm.fund.name}"/></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="account.fund.code"/></label></div>
            <div class="col-md-8"><c:out value="${fundForm.fund.fundCode}"/></div>
        </div>
        <div class="row"><div class="col-md-12">&nbsp;</div></div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="account.fund.currencycode"/></label></div>
            <div class="col-md-8"><smg:fmt value="${fundForm.fund.currencyCode}"/></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="account.fund.internalcompanycode"/></label></div>
            <div class="col-md-8"><fmt:message key="account.fund.internalcompanycode"/></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="ftx.fund.probableoptimisticgrowthrate"/></label></div>
            <div class="col-md-8"><smg:fmt value="${fundForm.fund.probableGrowthRate}"/>&#47;<smg:fmt
                    value="${fundForm.fund.optimisticGrowthRate}"/>&nbsp;&#37;</div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="account.fund.description"/></label></div>
            <div class="col-md-8"><c:out value="${fundForm.fund.description}"/></div>
        </div>

    </div>

    <%--Right Column--%>
    <div class="col-md-6">

        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="account.fund.startdate"/> &rarr; <fmt:message
                    key="account.fund.enddate"/></label></div>
            <div class="col-md-8"><smg:fmt value="${fundForm.fund.startDate}"/> &rarr; <smg:fmt
                    value="${fundForm.fund.endDate}"/></div>
        </div>
        <div class="row"><div class="col-md-12">&nbsp;</div></div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="account.fund.investmentscope"/></label></div>
            <div class="col-md-8"><c:out value="${fundForm.fund.investmentScope}"/></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="account.fund.investmentobjective"/></label></div>
            <div class="col-md-8"><c:out value="${fundForm.fund.investmentObjective}"/></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="account.fund.investmentstrategy"/></label></div>
            <div class="col-md-8"><c:out value="${fundForm.fund.investmentStrategy}"/></div>
        </div>
        <div class="row"><div class="col-md-12">&nbsp;</div></div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="account.fund.fundpayableaccount"/></label></div>
            <div class="col-md-8"><a class="text-info" href="${fundPayableAccountUrl}"><c:out value="${fundForm.fund.fundPayableAccount.name}"/></a></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="account.fund.fundreceiveableaccount"/></label></div>
            <div class="col-md-8"><a class="text-info" href="${fundReceivableAccountUrl}"><c:out
                    value="${fundForm.fund.fundReceivableAccount.name}"/></a></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="account.fund.unittransactionaccount"/></label></div>
            <div class="col-md-8"><a class="text-info" href="${unitTransactionAccountUrl}"><c:out
                    value="${fundForm.fund.unitTransactionAccount.name}"/></a></div>
        </div>

    </div>
</div>

<div id="assetPricesAndOperatingFees" class="row spacer">

    <%--Left Column--%>
    <div class="col-md-6">
        <c:url value="/secure/fund/assetprices/find.do" var="allAssetPricesUrl">
            <c:param name="method" value=".findAndDisplay"/>
            <c:param name="financialAssetId" value="${fundForm.fund.objectReference.objectId}"/>
        </c:url>

        <label class="groupHeading"><fmt:message key="page.fund.label.assetprices"/></label>

        <div><fmt:message key="page.fund.label.recentassetprices"/><br/>
            <a class="text-info" href="${allAssetPricesUrl}"><fmt:message key="page.fund.label.showallassetprices"/></a>
        </div>
        <p></p>

        <c:forEach items="${fundForm.recentAssetPrices.assetPrices}" var="assetPricesForType">

            <div><label><strong><fmt:message key="account.assetprice.pricetype"/>: <smg:fmt
                    value="${assetPricesForType.key}"/></strong> </label></div>
            <div><fmt:message key="page.fund.label.mostrecentthe"/> ${assetPricesForType.value.size()} <fmt:message
                    key="page.fund.label.mostrecent"/> <em><smg:fmt value="${assetPricesForType.key}"/></em>
                <fmt:message key="page.fund.label.pricetypesdisplayedhere"/></div>

            <div class="table-responsive">
                <display:table name="${assetPricesForType.value}"
                               decorator="com.silvermoongroup.fsa.web.fund.AssetPriceTableDecorator">

                    <display:column property="type" titleKey="label.type"/>
                    <display:column property="startDate" titleKey="account.assetprice.startdate"/>
                    <display:column property="endDate" titleKey="account.assetprice.enddate"/>
                    <display:column property="priceType" titleKey="account.assetprice.pricetype"/>
                    <display:column property="price" titleKey="account.assetprice.price"/>

                </display:table>
            </div>

        </c:forEach>

    </div>


    <%--Right Column--%>
    <div class="col-md-6">
        <label class="groupHeading"><fmt:message key="page.fund.label.assetoperatingfees"/></label>
        <c:if test="${! empty fundForm.assetOperatingFees}">
            <div><fmt:message key="label.the"/>&nbsp;${fn:length(fundForm.assetOperatingFees)}&nbsp;<fmt:message
                    key="page.fund.label.recentoperatingfees"/><br/></div>
        </c:if>

        <div class="table-responsive">
            <display:table name="${fundForm.assetOperatingFees}"
                           decorator="com.silvermoongroup.fsa.web.fund.AssetOperatingFeeTableDecorator">

                <display:column property="type" titleKey="label.type"/>
                <display:column property="startDate" titleKey="label.startdate"/>
                <display:column property="endDate" titleKey="label.enddate"/>
                <display:column property="frequency" titleKey="label.frequency"/>
                <display:column property="percentage" titleKey="label.percentage"/>

            </display:table>
        </div>

    </div>

</div>


<% if (!CallBackUtility.isCallBackEmpty(request, response)) { %>
<div class="text-center">
    <html:form action="/secure/fund.do">
        <input type="hidden" name="method" value=".back">
        <html:submit property="method" styleClass="btn btn-default btn-sm">
            <fmt:message key="button.back"/>
        </html:submit>
    </html:form>
</div>
<%} %>