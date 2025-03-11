<%@ page import="com.silvermoongroup.common.enumeration.EnumerationType" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<smg:datepicker selector=".datefield"/>

<jsp:useBean id="manufacturedItemForm" scope="request"
             type="com.silvermoongroup.fsa.web.physicalobject.form.ManufacturedItemForm"/>

<html:form action="/secure/agreement/role/manufactureditem.do" styleClass="form-horizontal">
    <html:hidden property="rolePlayerObjectReference"/>
    <input type="hidden" name="method" value=""/>


    <div class="row form-container">

            <%-- Left column--%>
        <div class="col-md-6">

            <div class="row">
                <div class="form-group">
                    <label for="name" class="col-md-3 control-label"><fmt:message key="page.physicalobject.shared.label.name"/></label>

                    <div class="col-md-4">
                        <html:text property="name" styleId="name" styleClass="form-control input-sm required" errorStyleClass="form-control input-sm required has-error" />
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="startDate" class="col-md-3 control-label"><fmt:message key="label.startdate"/></label>

                    <div class="col-md-4">
                        <html:text property="startDate" styleId="startDate" styleClass="form-control input-sm required datefield"
                                   errorStyleClass="form-control input-sm required has-error datefield"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="externalReference" class="col-md-3 control-label"><fmt:message key="page.physicalobject.shared.label.serialNumber"/></label>

                    <div class="col-md-4">
                        <html:text property="externalReference" styleId="externalReference" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="typeId" class="col-md-3 control-label"><fmt:message key="page.physicalobject.shared.label.type"/></label>

                    <div class="col-md-4">
                        <html:select property="typeId" styleId="typeId" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error">
                            <smg:generalOptionsCollection  property="typeOptions" value="value" label="label"/>
                        </html:select>
                    </div>
                </div>
            </div>

        </div>

            <%-- Right column--%>
        <div class="col-md-6">

            <div class="row">
                <div class="form-group">
                    <label for="description" class="col-md-3 control-label"><fmt:message key="page.physicalobject.shared.label.description"/></label>

                    <div class="col-md-4">
                        <html:text property="description" styleId="description" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error" />
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="endDate" class="col-md-3 control-label"><fmt:message key="label.enddate"/></label>

                    <div class="col-md-4">
                        <html:text property="endDate" styleId="endDate" styleClass="form-control input-sm required datefield"
                                   errorStyleClass="form-control input-sm required has-error datefield"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="inspectionCompleted" class="col-md-3 control-label"><fmt:message key="page.physicalobject.shared.label.inspectionCompleted"/></label>

                    <div class="col-md-4">
                        <html:select property="inspectionCompleted" styleId="inspectionCompleted" styleClass="form-control input-sm" errorStyleClass="form-control input-sm">
                            <smg:generalOptionsCollection property="inspectionCompletedOptions" value="value" label="label"/>
                        </html:select>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="currencyCode" class="col-md-3 control-label"><fmt:message key="page.physicalobject.shared.label.value"/></label>

                    <div class="col-md-9">
                        <div class="form-inline">
                            <html:select property="currencyCode" styleId="currencyCode" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error">
                                <smg:enumOptions enumTypeId="<%=EnumerationType.CURRENCY_CODE.getValue()%>" showTerminated = "true"/>
                            </html:select>
                            <html:text property="value" styleClass="form-control input-sm required" errorStyleClass="form-control input-sm required has-error"/>
                        </div>
                    </div>
                </div>
            </div>


        </div>

    </div>

    <div class="row spacer-sm">
        <div class="col-md-12 text-center">

            <c:if test="${empty manufacturedItemForm.rolePlayerObjectReference}">
                <html:submit styleId="submitButton" onclick="this.form.method.value='.save'"
                             styleClass="btn btn-primary btn-sm">
                    <fmt:message key="button.save"/>
                </html:submit>
            </c:if>
            <c:if test="${!empty manufacturedItemForm.rolePlayerObjectReference}">
                <html:submit styleId="submitButton" onclick="this.form.method.value='.update';"
                             styleClass="btn btn-primary btn-sm">
                    <fmt:message key="button.update"/>
                </html:submit>
            </c:if>
            <html:submit styleId="backButton" onclick="this.form.method.value='.back';" styleClass="btn btn-default btn-sm">
                <fmt:message key="label.back"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>

        </div>
    </div>

</html:form>
