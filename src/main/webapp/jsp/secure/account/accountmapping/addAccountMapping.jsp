<%@ page import="com.silvermoongroup.common.enumeration.EnumerationType" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/secure/account/accountmapping/add.do" styleId="addForm" styleClass="form-horizontal">

    <input type="hidden" name="method" id="method" value=""/>

    <div class="form-group">
        <label for="companyCode" class="col-md-2 control-label"><smg:fmt
                value="account.accountmapping.companycode"/></label>
        <div class="col-md-3">
            <html:select property="companyCode" styleId="companyCode" styleClass="form-control input-sm required"
                         errorStyleClass="form-control input-sm required has-error">
                <smg:enumOptions enumTypeId="<%=EnumerationType.INTERNAL_COMPANY_CODE.getValue()%>" showTerminated="true"  />
            </html:select>
        </div>
    </div>

    <div class="form-group">
        <label for="financialTransactionTypeId" class="col-md-2 control-label">
            <smg:fmt value="account.accountmapping.financialtransactiontype"/></label>
        <div class="col-md-3">
            <html:select property="financialTransactionTypeId" styleId="financialTransactionTypeId"
                         styleClass="form-control input-sm required" errorStyleClass="form-control input-sm required has-error">
                <option name="" value=""></option>
                <smg:typeTree rootTypeId="503" includeRootType="false" indent="true"/>
            </html:select>
        </div>
    </div>

    <div class="form-group">
        <label for="contextTypeId" class="col-md-2 control-label"><smg:fmt value="account.accountmapping.contexttype"/></label>
        <div class="col-md-3">
            <html:select property="contextTypeId" styleId="contextTypeId" styleClass="form-control input-sm">
                <smg:generalOptionsCollection property="contextTypeOptions"/>
            </html:select>
        </div>
    </div>

    <div class="form-group">
        <label for="currencyCode" class="col-md-2 control-label"><smg:fmt value="account.accountmapping.currency"/></label>
        <div class="col-md-3">
            <html:select property="currencyCode" styleId="currencyCode" styleClass="form-control input-sm required"
                         errorStyleClass="form-control input-sm required has-error">
                <smg:enumOptions enumTypeId="<%=EnumerationType.CURRENCY_CODE.getValue()%>" showTerminated="true"  />
            </html:select>
        </div>
    </div>

    <div class="form-group">
        <label for="meansOfPayment" class="col-md-2 control-label"><smg:fmt
                value="account.accountmapping.meansofpayment"/></label>
        <div class="col-md-3">
            <html:select property="meansOfPayment" styleId="meansOfPayment" styleClass="form-control input-sm"
                         errorStyleClass="form-control input-sm has-error">
                <smg:enumOptions enumTypeId="<%=EnumerationType.MEANS_OF_PAYMENT.getValue()%>" showTerminated="true"  />
            </html:select>
        </div>
    </div>

    <div class="form-group">
        <label for="mappingDirection" class="col-md-2 control-label"><smg:fmt
                value="account.accountmapping.mappingdirection"/></label>
        <div class="col-md-3">
            <html:select property="mappingDirection" styleId="mappingDirection" styleClass="form-control input-sm"
                         errorStyleClass="form-control input-sm has-error">
                <smg:immutableEnumOptions immutableEnumerationClass="com.silvermoongroup.account.enumeration.AccountMappingDirection" />
            </html:select>
        </div>
    </div>

    <div class="form-group">
        <label for="accountId" class="col-md-2 control-label"><smg:fmt value="account.accountmapping.accountid"/></label>
        <div class="col-md-2">
            <html:text property="accountId" styleClass="form-control input-sm" styleId="accountId" />
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-10">
            <html:submit property="method" styleClass="btn btn-primary btn-sm" onclick="$('#method').val('.add');">
                <fmt:message key="page.addaccountmapping.action.add"/>
            </html:submit>
            <html:submit property="method" styleClass="btn btn-default btn-sm" onclick="$('#method').val('.back');">
                <fmt:message key="label.cancel"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>

</html:form>
