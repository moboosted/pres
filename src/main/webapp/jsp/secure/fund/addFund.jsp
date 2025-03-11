<%@ page import="com.silvermoongroup.common.enumeration.EnumerationType" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<smg:datepicker selector=".datefield"/>

<html:form action="/secure/fund/create.do" styleId="createFundForm" styleClass="form-horizontal">
    <input type="hidden" name="method" value=".add"/>

    <div class="form-group">
        <label for="name" class="col-md-2 control-label"><smg:fmt value="ftx.fund.name"/></label>
        <div class="col-md-2">
            <html:text property="name" styleId="name" styleClass="form-control input-sm required" errorStyleClass="form-control input-sm required has-error"/>
        </div>
    </div>

    <div class="form-group">
        <label for="fundCode" class="col-md-2 control-label"><smg:fmt value="ftx.fund.code"/></label>
        <div class="col-md-2">
            <html:text property="fundCode" styleId="fundCode" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>
        </div>
    </div>

    <div class="form-group">
        <label for="description" class="col-md-2 control-label"><smg:fmt value="ftx.fund.description"/></label>
        <div class="col-md-5">
            <html:text property="description" styleId="description" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>
        </div>
    </div>

    <div class="form-group">
        <label for="currencyCode" class="col-md-2 control-label"><smg:fmt value="ftx.fund.currencycode"/></label>
        <div class="col-md-1">
            <html:select property="currencyCode" styleId="currencyCode" styleClass=" form-control input-sm required" errorStyleClass="form-control input-sm required has-error">
                <smg:enumOptions enumTypeId="<%=EnumerationType.CURRENCY_CODE.getValue()%>" showTerminated="true"  />

            </html:select>
        </div>
    </div>

    <div class="form-group">
        <label for="companyCode" class="col-md-2 control-label"><smg:fmt value="ftx.fund.companycode"/></label>
        <div class="col-md-3">
            <html:select property="companyCode" styleId="companyCode" styleClass="form-control input-sm required" errorStyleClass="form-control input-sm required has-error">
                <smg:enumOptions enumTypeId="<%=EnumerationType.INTERNAL_COMPANY_CODE.getValue()%>" showTerminated="true"  />

            </html:select>
        </div>
    </div>

    <div class="form-group">
        <label for="startDate" class="col-md-2 control-label"><smg:fmt value="label.datefrom"/></label>
        <div class="col-md-2">
            <html:text property="startDate" styleId="startDate" styleClass="form-control input-sm required datefield" errorStyleClass="form-control input-sm required datefield has-error"/>
        </div>
    </div>

    <div class="form-group">
        <label for="endDate" class="col-md-2 control-label"><smg:fmt value="label.dateto"/></label>
        <div class="col-md-2">
            <html:text property="endDate" styleId="endDate" styleClass="form-control input-sm datefield required" errorStyleClass="form-control input-sm required datefield has-error"/>
        </div>
    </div>

    <div class="form-group">
        <label for="endDate" class="col-md-2 control-label"><smg:fmt value="ftx.fund.probablegrowthrate"/>&nbsp;&#37;</label>
        <div class="col-md-1">
            <html:text property="probableGrowthRate" styleId="probableGrowthRate" styleClass="form-control input-sm required" errorStyleClass="form-control input-sm required datefield has-error"/>
        </div>
    </div>

    <div class="form-group">
        <label for="endDate" class="col-md-2 control-label"><smg:fmt value="ftx.fund.optimisticgrowthrate"/>&nbsp;&#37;</label>
        <div class="col-md-1">
            <html:text property="optimisticGrowthRate" styleId="optimisticGrowthRate" styleClass="form-control input-sm required" errorStyleClass="form-control input-sm required datefield has-error"/>
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-2">
            <html:submit property="method" styleClass="btn btn-primary btn-sm">
                <smg:fmt value="button.add"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>

</html:form>
