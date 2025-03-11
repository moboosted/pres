<%@page import="com.silvermoongroup.common.enumeration.EnumerationType"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<smg:datepicker selector=".datefield"/>


<script type="text/javascript">
    $(function () {

        $('#openedDateOptionAny').change(function () {
            $('#openedOnDate').val("");
            $('#openedBetweenDateStart').val("");
            $('#openedBetweenDateEnd').val("");
        });

        $('#openedDateOptionOn').change(function () {
            $('#openedBetweenDateStart').val("");
            $('#openedBetweenDateEnd').val("");
        });

        $('#openedDateOptionBetween').change(function () {
            $('#openedOnDate').val("");
        });

        // focus
        //
        $('#openedOnDate').focus(function () {
            $('#openedDateOptionOn').prop('checked', true).change();
        });
        $('#openedBetweenDateStart').focus(function () {
            $('#openedDateOptionBetween').prop('checked', true).change();
        });
        $('#openedBetweenDateEnd').focus(function () {
            $('#openedDateOptionBetween').prop('checked', true).change();
        });
    });
</script>

<jsp:useBean id="findAccountsForm" scope="request" type="com.silvermoongroup.fsa.web.account.FindAccountForm"/>

<html:form action="/secure/account/find.do" styleId="findForm" styleClass="form-horizontal">

    <input type="hidden" name="method" value=".find"/>

    <div class="row form-container">

            <%--Left Column--%>
        <div class="col-md-6">

            <div class="row">
                <div class="form-group">
                    <label for="accountId" class="col-md-3 control-label"><fmt:message
                            key="label.id"/></label>

                    <div class="col-md-3"><html:text property="accountId" styleId="accountId"
                                                     styleClass="form-control input-sm"/></div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="accountName" class="col-md-3 control-label"><fmt:message
                            key="account.account.name"/></label>

                    <div class="col-md-3"><html:text property="name" styleId="accountName"
                                                     styleClass="form-control input-sm"/></div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="accountTypeId" class="col-md-3 control-label"><fmt:message
                            key="account.account.type"/></label>

                    <div class="col-md-6">
                        <html:select property="accountTypeId" styleClass="form-control input-sm"
                                     errorStyleClass="form-control input-sm has-error" styleId="accountTypeId">
                            <smg:typeTree rootTypeId="6001"/>
                        </html:select>
                        <div class="checkbox">
                            <label>
                                <html:checkbox property="restrictAccountType"/> <fmt:message
                                    key="label.restricttothistype"/>
                            </label>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="currencyCode" class="col-md-3 control-label"><fmt:message
                            key="account.account.currencycode"/></label>

                    <div class="col-md-6">
                        <html:select property="currencyCode" styleClass="form-control input-sm"
                                     errorStyleClass="form-control input-sm has-error" styleId="currencyCode">
                            <option value=""><fmt:message key="page.findaccounts.label.anycurrencycode"/></option>
                            <smg:enumOptions enumTypeId="<%=EnumerationType.CURRENCY_CODE.getValue()%>" showTerminated = "true"/>
                        </html:select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label for="companyCode" class="col-md-3 control-label">
                        <fmt:message key="account.account.companycode"/>
                    </label>

                    <div class="col-md-6">
                        <html:select property="companyCode" styleClass="form-control input-sm"
                                     errorStyleClass="form-control input-sm has-error" styleId="companyCode">
                            <option value=""><fmt:message key="page.findaccounts.label.anycompanycode"/></option>
                            <smg:enumOptions enumTypeId="<%=EnumerationType.INTERNAL_COMPANY_CODE.getValue()%>" showTerminated = "true"/>                        </html:select>
                    </div>
                </div>
            </div>


        </div>

            <%--Right Column--%>
        <div class="col-md-6">

            <div class="row">
                <div class="form-group">

                    <div class="col-md-offset-3 col-md-6">
                        <div class="form-inline">
                            <html:radio property="openedDateOption" value="any" styleId="openedDateOptionAny"
                                        styleClass="radio-inline"/>
                            <fmt:message key="page.findaccounts.label.atanytime"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="openedDateOptionAny" class="col-md-3 control-label"><fmt:message
                            key="account.account.openingdate"/></label>

                    <div class="col-md-6">
                        <div class="form-inline">
                            <html:radio property="openedDateOption" value="on" styleId="openedDateOptionOn"
                                        styleClass="radio-inline"/>
                            <fmt:message
                                    key="page.findmoneyschedulers.label.on"/>
                            <html:text property="openedOnDate" styleClass="form-control input-sm datefield"
                                       errorStyleClass="form-control input-sm datefield has-error" styleId="openedOnDate"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">

                    <div class="col-md-offset-3 col-md-6">

                        <div class="form-inline">
                            <html:radio property="openedDateOption" value="between" styleId="openedDateOptionBetween"
                                        styleClass="radio-inline"/>
                            <fmt:message
                                    key="page.findaccounts.label.between"/>
                            <html:text property="openedBetweenDateStart" styleClass="form-control input-sm datefield"
                                       errorStyleClass="form-control input-sm datefield has-error"
                                       styleId="openedBetweenDateStart"/>
                            <fmt:message key="page.findaccounts.label.and"/>
                            <html:text property="openedBetweenDateEnd" styleClass="form-control input-sm datefield"
                                       errorStyleClass="form-control input-sm datefield has-error"
                                       styleId="openedBetweenDateEnd"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="accountEntryTypes" class="col-md-3 control-label"><fmt:message
                            key="page.findaccounts.label.havingaccountentriesoftype"/></label>

                    <div class="col-md-6">
                        <html:select property="accountEntryTypes" styleId="accountEntryTypes" multiple="true"
                                     styleClass="form-control input-sm">
                            <optgroup label="Account Entry Types">
                                <smg:typeTree rootTypeId="6014" includeRootType="true" indent="true"/>
                            </optgroup>
                        </html:select>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row spacer-sm">
        <div class="col-md-12 text-center">
            <html:submit accesskey="s" styleClass="btn btn-primary btn-sm"
                         titleKey="label.submitwithalts">
                <fmt:message key="button.search"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>

</html:form>

<c:url value="/secure/account.do" var="accountUrl"/>

<c:if test="${findAccountsForm.results != null}">
    <div class="table-responsive spacer">
        <display:table name="${findAccountsForm.results}"
                       decorator="com.silvermoongroup.fsa.web.account.AccountTableDecorator">

            <display:column property="id" titleKey="label.id" href="${accountUrl}" paramId="accountObjectReference"
                            paramProperty="objectReference"/>
            <display:column property="name" titleKey="account.account.name" maxLength="50" href="${accountUrl}"
                            paramId="accountObjectReference" paramProperty="objectReference"/>
            <display:column property="type" titleKey="label.type" maxLength="50" href="${accountUrl}"
                            paramId="accountObjectReference" paramProperty="objectReference"/>
            <display:column property="openingDate" titleKey="account.account.openingdate"/>
            <display:column property="closingDate" titleKey="account.account.closingdate"/>
            <display:column property="currencyCode" titleKey="account.account.currencycode"/>
            <display:column property="internalCompanyCode" titleKey="account.account.companycode"/>

        </display:table>
    </div>
    <c:if test="${fn:length(findAccountsForm.results) eq 100}">
        <div>
            <fmt:message key="page.findaccounts.label.limitedto100results"/>
        </div>
    </c:if>
</c:if>
