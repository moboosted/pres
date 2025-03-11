<%@page import="com.silvermoongroup.common.enumeration.EnumerationType"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<smg:datepicker selector=".datefield"/>

<jsp:useBean id="findAssetPricesForm" scope="request" type="com.silvermoongroup.fsa.web.fund.FindAssetPricesForm"/>

<script type="text/javascript">
    $(function () {

        $('#dateOptionAny').change(function () {
            $('#effectiveOn').val("");
            $('#effectiveFrom').val("");
            $('#effectiveTo').val("");
        });

        $('#dateOptionOn').change(function () {
            $('#effectiveFrom').val("");
            $('#effectiveTo').val("");
        });

        $('#dateOptionBetween').change(function () {
            $('#effectiveOn').val("");
        });

        $('#effectiveTodayLink').bind('click', function (e) {
            e.preventDefault();
            $('#effectiveOn').val("${findAssetPricesForm.today}");
            $('#dateOptionOn').prop('checked', true).change();
        });

        $('#effectiveYesterdayLink').bind('click', function (e) {
            e.preventDefault();
            $('#effectiveOn').val("${findAssetPricesForm.yesterday}");
            $('#dateOptionOn').prop('checked', true).change();
        });

        $('#effectiveCurrentMonthLink').bind('click', function (e) {
            e.preventDefault();
            $('#effectiveFrom').val("${findAssetPricesForm.firstOfCurrentMonth}");
            $('#effectiveTo').val("${findAssetPricesForm.lastOfCurrentMonth}");
            $('#dateOptionBetween').prop('checked', true).change();
        });

        $('#effectivePreviousMonthLink').bind('click', function (e) {
            e.preventDefault();
            $('#effectiveFrom').val("${findAssetPricesForm.firstOfPreviousMonth}");
            $('#effectiveTo').val("${findAssetPricesForm.lastOfPreviousMonth}");
            $('#dateOptionBetween').prop('checked', true).change();
        });

        $('#effectiveOn').focus(function () {
            $('#dateOptionOn').prop('checked', true).change();
        });
        $('#effectiveFrom').focus(function () {
            $('#dateOptionBetween').prop('checked', true).change();
        });
        $('#effectiveTo').focus(function () {
            $('#dateOptionBetween').prop('checked', true).change();
        });
    });
</script>

<html:form action="/secure/fund/assetprices/find.do" styleId="findForm" styleClass="form-horizontal">

    <input type="hidden" name="method" value=".find"/>

    <div class="row form-container">

            <%--Left column--%>
        <div class="col-md-6">
            <div class="row">
                <div class="form-group">
                    <label for="financialAssetId" class="col-md-3 control-label"><fmt:message
                            key="account.financialasset"/> <fmt:message key="label.id"/></label>

                    <div class="col-md-2">
                        <html:text property="financialAssetId" styleId="financialAssetId" styleClass="form-control input-sm"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="financialAssetName" class="col-md-3 control-label"><fmt:message
                            key="account.financialasset.name"/></label>

                    <div class="col-md-4">
                        <html:text property="financialAssetName" styleId="financialAssetName"
                                   styleClass="form-control input-sm"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="fundCode" class="col-md-3 control-label"><fmt:message
                            key="account.financialasset.code"/></label>

                    <div class="col-md-4">
                        <html:text property="fundCode" styleId="fundCode"
                                   styleClass="form-control input-sm"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="priceType" class="col-md-3 control-label"><fmt:message
                            key="account.assetprice.pricetype"/></label>

                    <div class="col-md-6">
                        <html:select property="priceTypeId" styleId="priceType" styleClass="form-control input-sm">
                            <option value=""><fmt:message key="page.findassetprices.label.anyassetprice"/></option>
                            <smg:enumOptions enumTypeId="<%=EnumerationType.PRICE_TYPE.getValue()%>" showTerminated = "true"/>
                        </html:select>
                        <div class="checkbox-inline"><html:checkbox property="latestPriceOnly"/>
                            <fmt:message key="page.findassetprices.label.onlyshowlatestprice"/>
                        </div>
                    </div>
                </div>
            </div>

        </div>

            <%--Right column--%>
        <div class="col-md-6">

            <div class="row">
                <div class="form-group">
                    <div class="col-md-offset-3 col-md-2 form-inline">
                        <div class="radio">
                            <html:radio property="dateOption" value="any" styleId="dateOptionAny"/>
                            <fmt:message key="page.findassetprices.label.atanydate"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">

                    <label for="dateOptionAny" class="col-md-3 control-label"><fmt:message
                            key="page.findassetprices.label.assetpricesthatareeffective"/></label>

                    <div class="col-md-9">
                        <div class="form-inline">
                            <html:radio property="dateOption" value="on" styleId="dateOptionOn" styleClass="radio"/> On&nbsp;
                            <html:text property="effectiveOn" styleClass="form-control input-sm datefield"
                                       errorStyleClass="form-control input-sm datefield has-error" styleId="effectiveOn"/>
                            &nbsp;&nbsp;[ <a href="#" id="effectiveYesterdayLink"><fmt:message
                                key="label.yesterday"/></a> ]
                            &nbsp;[ <a href="#" id="effectiveTodayLink"><fmt:message key="label.today"/></a> ]
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">

                    <div class=" col-md-offset-3 col-md-9">
                        <div class="form-inline">
                            <html:radio property="dateOption" value="between" styleId="dateOptionBetween"
                                        styleClass="radio"/>
                            <fmt:message key="label.between"/>
                            <html:text property="effectiveFrom" styleClass="form-control input-sm datefield"
                                       errorStyleClass="form-control input-sm datefield has-error" styleId="effectiveFrom"/>
                            <fmt:message key="label.and"/>
                            <html:text property="effectiveTo" styleClass="form-control input-sm datefield"
                                       errorStyleClass="form-control input-sm datefield has-error" styleId="effectiveTo"/>
                            &nbsp;&nbsp;[ <a href="#" id="effectivePreviousMonthLink"><fmt:message
                                key="label.lastmonth"/></a> ]
                            &nbsp;[ <a href="#" id="effectiveCurrentMonthLink"><fmt:message
                                key="label.currentmonth"/></a> ]
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row spacer">
        <div class="col-md-12 text-center">
            <html:submit accesskey="s" titleKey="label.submitwithalts" styleClass="btn btn-primary btn-sm">
                <fmt:message key="button.search"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>


</html:form>

<c:if test="${findAssetPricesForm.results != null}">

    <c:url value="/secure/fund.do" var="fundUrl"/>

    <div class="table-responsive spacer">
        <display:table name="${findAssetPricesForm.results}"
                       decorator="com.silvermoongroup.fsa.web.fund.AssetPriceTableDecorator">

            <display:column property="id" titleKey="label.id"/>
            <display:column property="financialAssetName" titleKey="account.financialasset.name"
                            href="${fundUrl}" paramId="fundObjectReference"
                            paramProperty="financialAsset.objectReference"/>
            <display:column property="fundCode" titleKey="account.assetprice.fundCode"/>
            <display:column property="financialAssetDescription" maxLength="35"
                            titleKey="account.financialasset.description"/>
            <display:column property="priceType" titleKey="account.assetprice.pricetype"/>
            <display:column property="price" titleKey="account.assetprice.price"/>
            <display:column property="startDate" titleKey="account.assetprice.startdate"/>
            <display:column property="endDate" titleKey="account.assetprice.enddate"/>

        </display:table>
    </div>
</c:if>
