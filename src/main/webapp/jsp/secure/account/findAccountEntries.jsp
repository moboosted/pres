<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<smg:datepicker selector=".datefield"/>

<jsp:useBean id="findAccountEntriesForm" scope="request"
             type="com.silvermoongroup.fsa.web.account.FindAccountEntriesForm"/>

<script type="text/javascript">
    $(function () {

        $('#postedDateOptionAny').change(function () {
            $('#postedDateOn').val("");
            $('#postedDateBetweenStart').val("");
            $('#postedDateBetweenEnd').val("");
        });

        $('#postedDateOptionOn').change(function () {
            $('#postedDateBetweenStart').val("");
            $('#postedDateBetweenEnd').val("");
        });

        $('#postedDateOptionBetween').change(function () {
            $('#postedDateOn').val("");
        });

        $('#valueDateOptionAny').change(function () {
            $('#valueDateOn').val("");
            $('#valueDateBetweenStart').val("");
            $('#valueDateBetweenEnd').val("");
        });

        $('#valueDateOptionOn').change(function () {
            $('#valueDateBetweenStart').val("");
            $('#valueDateBetweenEnd').val("");
        });

        $('#valueDateOptionBetween').change(function () {
            $('#valueDateOn').val("");
        });
        // focus
        //

        $('#postedDateOn').focus(function () {
            $('#postedDateOptionOn').prop('checked', true).change();
        });
        $('#postedDateBetweenStart').focus(function () {
            $('#postedDateOptionBetween').prop('checked', true).change();
        });
        $('#postedDateBetweenEnd').focus(function () {
            $('#postedDateOptionBetween').prop('checked', true).change();
        });

        $('#valueDateOn').focus(function () {
            $('#valueDateOptionOn').prop('checked', true).change();
        });
        $('#valueDateBetweenStart').focus(function () {
            $('#valueDateOptionBetween').prop('checked', true).change();
        });
        $('#valueDateBetweenEnd').focus(function () {
            $('#valueDateOptionBetween').prop('checked', true).change();
        });

        // yesterday/today links
        $('#postedYesterdayLink').bind('click', function (e) {
            $('#postedDateOn').val("${findAccountEntriesForm.yesterday}");
            $('#postedDateOptionOn').prop('checked', true).change();

        });
        $('#postedTodayLink').bind('click', function (e) {
            $('#postedDateOn').val("${findAccountEntriesForm.today}");
            $('#postedDateOptionOn').prop('checked', true).change();
        });

        $('#valueYesterdayLink').bind('click', function (e) {
            $('#valueDateOn').val("${findAccountEntriesForm.yesterday}");
            $('#valueDateOptionOn').prop('checked', true).change();

        });
        $('#valueTodayLink').bind('click', function (e) {
            $('#valueDateOn').val("${findAccountEntriesForm.today}");
            $('#valueDateOptionOn').prop('checked', true).change();
        });
    });
</script>

<c:url var="accountUrl" value="/secure/account.do">
    <c:param name="accountObjectReference" value="${findAccountEntriesForm.accountObjectReference}"/>
</c:url>

<div class="row">
    <div class="col-md-12">
        <label for="findAccountEntriesTable" class="groupHeading">
            <a href="${accountUrl}"><smg:objectReference value="${findAccountEntriesForm.accountObjectReference}"
                                                         display="type"/></a>
        </label>
    </div>
</div>

<html:form action="/secure/account/accountentry/find.do" styleId="findForm" styleClass="form-horizontal">

    <input type="hidden" name="method" value=".find"/>
    <html:hidden property="accountObjectReference"/>

    <div class="row form-container" id="findAccountEntriesTable">

            <%--Left column--%>
        <div class="col-md-6">

            <div class="row">
                <div class="form-group">
                    <label for="debitCreditIndicator" class="col-md-3 control-label"><fmt:message
                            key="account.accountentry.debitcreditindicator"/></label>

                    <div class="col-md-4">
                        <html:select property="debitCreditIndicator" styleClass="form-control input-sm"
                                     errorStyleClass="form-control input-sm has-error" styleId="debitCreditIndicator">
                            <option value=""><fmt:message
                                    key="page.findaccountentries.label.bothdebitandcredits"/></option>
                            <smg:immutableEnumOptions immutableEnumerationClass = "com.silvermoongroup.account.enumeration.DebitCreditIndicator"/>                        </html:select>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="minimumAmount" class="col-md-3 control-label"><fmt:message
                            key="page.findaccountentries.label.withanamountbetween"/></label>

                    <div class="col-md-9 form-inline">
                        <html:text property="minimumAmount" styleClass="form-control input-sm"
                                   errorStyleClass="form-control input-sm has-error" styleId="minimumAmount"/>
                        <fmt:message key="label.and"/>
                        <html:text property="maximumAmount" styleClass="form-control input-sm"
                                   errorStyleClass="form-control input-sm has-error" styleId="maximumAmount"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="selectedAccountEntryTypes" class="col-md-3 control-label">
                        <fmt:message key="page.findaccountentries.label.restrictedtotype"/>
                    </label>

                    <div class="col-md-6">
                        <html:select property="selectedAccountEntryTypes" styleId="selectedAccountEntryTypes"
                                     styleClass="form-control input-sm" multiple="true">
                            <smg:generalOptionsCollection property="accountEntryTypes" value="value" label="label"/>
                        </html:select>
                    </div>
                </div>
            </div>

        </div>

            <%--Right column--%>
        <div class="col-md-6">

            <div class="row">
                <div class="form-group-sm">
                    <div class=" col-md-offset-3 col-md-9">
                        <html:radio property="postedDateOption" value="any" styleId="postedDateOptionAny"
                                    styleClass="radio-inline"/>
                        <fmt:message key="page.findaccountentries.label.atanytime"/>
                    </div>
                </div>

                <div class="form-group-sm">
                    <label for="postedDateOptionAny" class="col-md-3 control-label"><fmt:message
                            key="account.accountentry.posteddate"/></label>

                    <div class="col-md-9 form-inline">
                        <html:radio property="postedDateOption" value="on" styleId="postedDateOptionOn"
                                    styleClass="radio-inline"/>
                        <fmt:message
                                key="page.findaccountentries.label.on"/>
                        <html:text property="postedDateOn" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error" styleId="postedDateOn"/>
                        &nbsp;&nbsp;[ <a href="#" id="postedYesterdayLink"><fmt:message key="label.yesterday"/></a> ]
                        &nbsp;[ <a href="#" id="postedTodayLink"><fmt:message key="label.today"/></a> ]
                    </div>
                </div>

                <div class="form-group-sm">
                    <div class=" col-md-offset-3 col-md-9 form-inline">
                        <html:radio property="postedDateOption" value="between" styleId="postedDateOptionBetween"/>
                        <fmt:message
                                key="page.findaccountentries.label.between"/>
                        <html:text property="postedDateBetweenStart" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error" styleId="postedDateBetweenStart"/>
                        <fmt:message key="page.findaccountentries.label.and"/>
                        <html:text property="postedDateBetweenEnd" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error" styleId="postedDateBetweenEnd"/>
                    </div>
                </div>
            </div>

            <div class="row spacer">
                <div class="form-group-sm">
                    <div class=" col-md-offset-3 col-md-9">
                        <html:radio property="valueDateOption" value="any" styleId="valueDateOptionAny"
                                    styleClass="radio-inline"/>
                        <fmt:message key="page.findaccountentries.label.atanytime"/>
                    </div>
                </div>

                <div class="form-group-sm">
                    <label for="valueDateOptionAny" class="col-md-3 control-label"><fmt:message
                            key="account.accountentry.valuedate"/></label>

                    <div class="col-md-9 form-inline">
                        <html:radio property="valueDateOption" value="on" styleId="valueDateOptionOn"
                                    styleClass="radio-inline"/>
                        <fmt:message
                                key="page.findaccountentries.label.on"/>
                        <html:text property="valueDateOn" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error" styleId="valueDateOn"/>
                        &nbsp;&nbsp;[ <a href="#" id="valueYesterdayLink"><fmt:message key="label.yesterday"/></a> ]
                        &nbsp;[ <a href="#" id="valueTodayLink"><fmt:message key="label.today"/></a> ]
                    </div>
                </div>

                <div class="form-group-sm">
                    <div class=" col-md-offset-3 col-md-9 form-inline">
                        <html:radio property="valueDateOption" value="between" styleId="valueDateOptionBetween"
                                    styleClass="radio-inline"/>
                        <fmt:message
                                key="page.findaccountentries.label.between"/>
                        <html:text property="valueDateBetweenStart" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error" styleId="valueDateBetweenStart"/>
                        <fmt:message key="page.findaccountentries.label.and"/>
                        <html:text property="valueDateBetweenEnd" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error" styleId="valueDateBetweenEnd"/>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <div class="row spacer">
        <div class="col-md-12 text-center">
            <html:submit accesskey="s"
                         titleKey="label.submitwithalts" styleClass="btn btn-primary btn-sm">
                <fmt:message key="button.search"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>
</html:form>

<c:if test="${findAccountEntriesForm.results != null}">
    <div class="table-responsive spacer">

        <c:url var="accountEntryUrl" value="/secure/account/accountentry.do"/>
        <c:url var="financialTransactionUrl" value="/secure/ftx/financialtransaction.do"/>
        <c:url var="unitTransactionUrl" value="/secure/ftx/unittransaction.do"/>

        <display:table name="findAccountEntriesForm.results"
                       sort="external"
                       decorator="com.silvermoongroup.fsa.web.account.AccountEntriesTableDecorator"
                       pagesize="${findAccountEntriesForm.rowsPerPage}"
                       partialList="true"
                       size="${findAccountEntriesForm.results.fullListSize}"
                       requestURI="/secure/account/accountentry/find.do"
                       id="recentAccountEntryPostingsTable">

            <display:column property="id" titleKey="label.id" href="${accountEntryUrl}"
                            paramId="accountEntryObjectReference" paramProperty="objectReference"/>
            <display:column property="type" titleKey="account.accountentry.type" href="${accountEntryUrl}"
                            paramId="accountEntryObjectReference" paramProperty="objectReference"/>

            <c:choose>
                <c:when test="${!empty recentAccountEntryPostingsTable.financialTransaction}">
                    <display:column property="transactionType" titleKey="account.accountentry.generatingtransaction"
                                    href="${financialTransactionUrl}"
                                    paramId="financialTransactionObjectReference" paramProperty="financialTransaction"/>
                </c:when>
                <c:otherwise>
                    <display:column property="transactionType" titleKey="account.accountentry.generatingtransaction"
                                    href="${unitTransactionUrl}"
                                    paramId="unitTransactionObjectReference" paramProperty="unitTransaction"/>
                </c:otherwise>
            </c:choose>

            <display:column property="postedDate" titleKey="account.accountentry.posteddate" />
            <display:column property="valueDate" titleKey="account.accountentry.valuedate" />
            <display:column property="debitEntry" titleKey="account.accountentry.debitabbreviation"
                            decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.AmountColumnDecorator"/>
            <display:column property="creditEntry" titleKey="account.accountentry.creditabbreviation"
                            decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.AmountColumnDecorator"/>


            <display:footer>
                <tr class="total">
                    <td colspan="5"><fmt:message key="page.findaccountentries.label.totals"/>:</td>
                    <td class="total"><smg:fmt value="${findAccountEntriesForm.debitTotal}"/></td>
                    <td class="total"><smg:fmt value="${findAccountEntriesForm.creditTotal}"/></td>
                </tr>
            </display:footer>

        </display:table>
        <div><fmt:message key="page.findaccountentries.label.totalsdisclaimer"/></div>
    </div>
</c:if>
