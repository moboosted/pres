<%@ page import="com.silvermoongroup.common.enumeration.EnumerationType" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<smg:datepicker selector=".datefield"/>

<jsp:useBean id="findFinancialTransactionsForm" scope="request"
             type="com.silvermoongroup.fsa.web.ftx.FindFinancialTransactionsForm"/>

<script type="text/javascript">
    $(function () {

        // posted date
        $('#postedDateOptionAny').change(function () {
            $('#postedDateOn').val("");
            $('#postedDateFrom').val("");
            $('#postedDateTo').val("");
        });

        $('#postedDateOptionOn').change(function () {
            $('#postedDateFrom').val("");
            $('#postedDateTo').val("");
        });

        $('#postedDateOptionBetween').change(function () {
            $('#postedDateOn').val("");
        });

        $('#postedDateTodayLink').bind('click', function (e) {
            e.preventDefault();
            $('#postedDateOn').val("${findFinancialTransactionsForm.today}");
            $('#postedDateOptionOn').prop('checked', true).change();
        });

        $('#postedDateYesterdayLink').bind('click', function (e) {
            e.preventDefault();
            $('#postedDateOn').val("${findFinancialTransactionsForm.yesterday}");
            $('#postedDateOptionOn').prop('checked', true).change();
        });

        $('#postedDateCurrentMonthLink').bind('click', function (e) {
            e.preventDefault();
            $('#postedDateFrom').val("${findFinancialTransactionsForm.firstOfCurrentMonth}");
            $('#postedDateTo').val("${findFinancialTransactionsForm.lastOfCurrentMonth}");
            $('#postedDateOptionBetween').prop('checked', true).change();
        });

        $('#postedDatePreviousMonthLink').bind('click', function (e) {
            e.preventDefault();
            $('#postedDateFrom').val("${findFinancialTransactionsForm.firstOfPreviousMonth}");
            $('#postedDateTo').val("${findFinancialTransactionsForm.lastOfPreviousMonth}");
            $('#postedDateOptionBetween').prop('checked', true).change();
        });

        $('#postedDateOn').focus(function () {
            $('#postedDateOptionOn').prop('checked', true).change();
        });
        $('#postedDateFrom').focus(function () {
            $('#postedDateOptionBetween').prop('checked', true).change();
        });
        $('#postedDateTo').focus(function () {
            $('#postedDateOptionBetween').prop('checked', true).change();
        });

        // amount option
        $('#amountOptionAny').change(function () {
            $('#minimumAmount').val("");
            $('#maximumAmount').val("");
            $('#amountExact').val("");
        });

        $('#amountOptionExact').change(function () {
            $('#minimumAmount').val("");
            $('#maximumAmount').val("");
        });

        $('#amountOptionBetween').change(function () {
            $('#amountExact').val("");
        });

        $('#amountExact').focus(function () {
            $('#amountOptionExact').prop('checked', true).change();
        });
        $('#minimumAmount').focus(function () {
            $('#amountOptionBetween').prop('checked', true).change();
        });
        $('#maximumAmount').focus(function () {
            $('#amountOptionBetween').prop('checked', true).change();
        });


    });
</script>

<html:form action="/secure/ftx/financialtransaction/find.do" styleId="findForm" styleClass="form-horizontal">

    <input type="hidden" name="method" value=".find"/>

    <div class="row form-container">

            <%--Left column--%>
        <div class="col-md-6">
            <div class="row">
                <div class="form-group">
                    <label for="financialTransactionId" class="col-md-3 control-label"><fmt:message
                            key="label.id"/></label>

                    <div class="col-md-2">
                        <html:text property="financialTransactionId" styleId="financialTransactionId"
                                   styleClass="form-control input-sm"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="financialTransactionTypeId" class="col-md-3 control-label"><fmt:message
                            key="label.type"/></label>

                    <div class="col-md-6">
                        <html:select property="financialTransactionTypeId" styleClass="form-control input-sm"
                                     errorStyleClass="form-control input-sm has-error" styleId="financialTransactionTypeId">
                            <smg:typeTree rootTypeId="503"/>
                        </html:select>
                        <div class="form-inline">
                            <html:checkbox property="restrictType" styleClass="checkbox"/> <fmt:message
                                key="label.restricttothistype"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="externalReference" class="col-md-3 control-label"><fmt:message
                            key="ftx.financialtransaction.externalreference"/></label>

                    <div class="col-md-6">
                        <html:text property="externalReference" styleId="externalReference" styleClass="form-control input-sm"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="meansOfPayment" class="col-md-3 control-label"><fmt:message
                            key="ftx.financialtransaction.paymentmethod"/></label>

                    <div class="col-md-6">
                        <html:select property="meansOfPayment" styleClass="form-control input-sm"
                                     errorStyleClass="form-control input-sm has-error" styleId="meansOfPayment">
                            <option value=""><fmt:message
                                    key="page.findfinancialtransactions.label.anypaymentmethod"/></option>
                            <smg:enumOptions enumTypeId="<%=EnumerationType.MEANS_OF_PAYMENT.getValue()%>" showTerminated = "true"/>
                        </html:select>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="companycode" class="col-md-3 control-label"><fmt:message
                            key="ftx.financialtransaction.companycode"/></label>

                    <div class="col-md-6">
                        <html:select property="companyCode" styleClass="form-control input-sm"
                                     errorStyleClass="form-control input-sm has-error" styleId="companycode">
                            <option value=""><fmt:message
                                    key="page.findfinancialtransactions.label.anycompanycode"/></option>
                            <smg:enumOptions enumTypeId="<%=EnumerationType.INTERNAL_COMPANY_CODE.getValue()%>" showTerminated = "true"/>
                        </html:select>
                    </div>
                </div>
            </div>
        </div>

            <%--Right column--%>
        <div class="col-md-6">


            <div class="row">
                <div class="form-group-sm">
                    <div class="col-md-offset-3 col-md-2 form-inline">
                        <div class="radio">
                            <html:radio property="amountOption" value="any" styleId="amountOptionAny"
                                        styleClass="radio"/>
                            <fmt:message key="page.findfinancialtransactions.label.anyamount"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group-sm">

                    <label for="amountExact" class="col-md-3 control-label"><fmt:message
                            key="ftx.financialtransaction.amount"/></label>

                    <div class="col-md-9">
                        <div class="form-inline">
                            <html:radio property="amountOption" value="exact" styleId="amountOptionExact"
                                        styleClass="radio"/>
                            <fmt:message key="page.findfinancialtransactions.label.theexactamountof"/>
                            <html:text property="amount" styleClass="form-control input-sm"
                                       errorStyleClass="form-control input-sm has-error" styleId="amountExact"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group-sm">

                    <div class=" col-md-offset-3 col-md-9">
                        <div class="form-inline">
                            <html:radio property="amountOption" value="between" styleId="amountOptionBetween"
                                        styleClass="radio"/>
                            <fmt:message
                                    key="page.findmoneyschedulers.label.between"/>
                            <html:text property="minimumAmount" styleClass="form-control input-sm"
                                       errorStyleClass="form-control input-sm has-error" styleId="minimumAmount"/>
                            <fmt:message key="label.and"/>
                            <html:text property="maximumAmount" styleClass="form-control input-sm"
                                       errorStyleClass="form-control input-sm has-error" styleId="maximumAmount"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row spacer">

                <div class="form-group-sm">
                    <div class="col-md-offset-3 col-md-2 form-inline">
                        <html:radio property="postedDateOption" value="any" styleId="postedDateOptionAny"/>
                        <fmt:message key="label.anytime"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group-sm">

                    <label for="amountExact" class="col-md-3 control-label"><fmt:message
                            key="ftx.financialtransaction.posteddate"/></label>

                    <div class="col-md-9">
                        <div class="form-inline">
                            <html:radio property="postedDateOption" value="on" styleId="postedDateOptionOn"
                                        styleClass="radio"/>
                            <fmt:message key="label.on"/>
                            <html:text property="postedDate" styleClass="form-control input-sm datefield"
                                       errorStyleClass="form-control input-sm datefield has-error" styleId="postedDateOn"/>
                            &nbsp;&nbsp;[ <a href="#" id="postedDateYesterdayLink"><fmt:message
                                key="label.yesterday"/></a> ]
                            &nbsp;[ <a href="#" id="postedDateTodayLink"><fmt:message key="label.today"/></a> ]
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group-sm">

                    <div class=" col-md-offset-3 col-md-9">
                        <div class="form-inline">
                            <html:radio property="postedDateOption" value="between" styleId="postedDateOptionBetween"/>
                            <fmt:message
                                    key="label.between"/>
                            <html:text property="postedDateFrom" styleClass="form-control input-sm datefield"
                                       errorStyleClass="form-control input-sm datefield has-error" styleId="postedDateFrom"/>
                            <fmt:message key="page.findmoneyschedulers.label.and"/>
                            <html:text property="postedDateTo" styleClass="form-control input-sm datefield"
                                       errorStyleClass="form-control input-sm datefield has-error" styleId="postedDateTo"/>
                            &nbsp;&nbsp;[ <a href="#" id="postedDatePreviousMonthLink"><fmt:message
                                key="label.lastmonth"/></a> ]
                            &nbsp;[ <a href="#" id="postedDateCurrentMonthLink"><fmt:message
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

<c:url value="/secure/ftx/financialtransaction.do" var="financialTransactionUrl"/>

<c:if test="${findFinancialTransactionsForm.results != null}">
    <div class="table-responsive spacer">
        <display:table name="${findFinancialTransactionsForm.results}"
                       decorator="com.silvermoongroup.fsa.web.ftx.FinancialTransactionTableDecorator">

            <display:column property="id" titleKey="label.id" href="${financialTransactionUrl}"
                            paramId="financialTransactionObjectReference" paramProperty="objectReference"/>
            <display:column property="type" titleKey="label.type" href="${financialTransactionUrl}"
                            paramId="financialTransactionObjectReference" paramProperty="objectReference"/>
            <display:column property="externalReference" titleKey="ftx.financialtransaction.externalreference"
                            maxLength="40"/>
            <display:column property="amount" titleKey="ftx.financialtransaction.amount"/>
            <display:column property="postedDate" titleKey="ftx.financialtransaction.posteddate"/>
            <display:column property="paymentMethod" titleKey="ftx.financialtransaction.paymentmethod"/>
            <display:column property="contextType" titleKey="ftx.financialtransaction.context"
                            maxLength="75"/>

        </display:table>
    </div>
</c:if>
