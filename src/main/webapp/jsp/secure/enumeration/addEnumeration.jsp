<%@page import="com.silvermoongroup.common.enumeration.EnumerationType" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<smg:datepicker selector=".datefield"/>

<script type="text/javascript">
    $(document).ready(function () {

        $('#addEnumerationForm').submit(function () {
            $(this).find('input:submit').attr('disabled', 'disabled');
            $(this).find('.spinner').css('visibility', 'visible');
        });
    });
</script>
<script type="text/javascript"src="${pageContext.request.contextPath}/js/party/date.js"></script>
<html:form action="/secure/enumeration/addEnumeration.do" styleId="addEnumerationForm" styleClass="form-horizontal">

    <html:hidden property="hiddenMethod" styleId="hiddenMethod" value=""/>
    <html:hidden property="enumerationTypeId" value="${addEnumerationForm.enumerationTypeId}"/>
    <c:set var="unitOfMeasureValue" value="<%=EnumerationType.UNIT_OF_MEASURE.getValue()%>"/>
    <c:set var="internalCoCodeValue" value="<%=EnumerationType.INTERNAL_COMPANY_CODE.getValue()%>"/>

    <div class="row form-container">
        <div class="col-md-6">
            <div class="row">
                <div class="form-group">
                    <label for="enumerationTypeName" class="col-md-3 control-label"><fmt:message
                            key="page.enumeration.label.enumeration"/></label>

                    <div class="col-md-4">
                        <html:text property="enumerationTypeName" styleId="enumerationTypeName"
                                   styleClass="form-control input-sm required"
                                   errorStyleClass="form-control input-sm required has-error" readonly="true"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label for="name" class="col-md-3 control-label"><fmt:message key="label.name"/></label>

                    <div class="col-md-4">
                        <html:text property="name" styleId="name" styleClass="form-control input-sm required"
                                   errorStyleClass="form-control input-sm required has-error"/>
                    </div>
                </div>
            </div>
            <c:choose>
                <c:when test="${addEnumerationForm.enumerationTypeId == unitOfMeasureValue}">
                    <div class="row">
                        <div class="form-group">
                            <label for="measure" class="col-md-3 control-label"><fmt:message
                                    key="page.enumeration.label.measure"/></label>

                            <div class="col-md-4">
                                <html:select property="measure" styleId="measure" styleClass="form-control input-sm"
                                             errorStyleClass="form-control input-sm">
                                    <smg:generalOptionsCollection property="measureOptions" value="value" label="label"/>
                                </html:select>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:when test="${addEnumerationForm.enumerationTypeId == internalCoCodeValue}">
                    <div class="row">
                        <div class="form-group">
                            <label for="organisationObjectReference" class="col-md-3 control-label"><fmt:message
                                    key="page.enumeration.label.organisation"/></label>

                            <div class="col-md-4">
                                <html:select property="organisationObjectReference"
                                             styleId="organisationObjectReference" styleClass="form-control input-sm"
                                             errorStyleClass="form-control input-sm">
                                    <smg:generalOptionsCollection property="organisationOptions" value="value" label="label"/>
                                </html:select>
                            </div>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </div>

        <div class="col-md-6">
            <div class="row">
                <div class="form-group">
                    <label for="description" class="col-md-3 control-label"><fmt:message
                            key="label.description"/></label>

                    <div class="col-md-4">
                        <html:text property="description" styleId="description"
                                   styleClass="form-control input-sm required"
                                   errorStyleClass="form-control input-sm required has-error"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label for="abbreviation" class="col-md-3 control-label"><fmt:message
                            key="label.abbreviation"/></label>

                    <div class="col-md-2">
                        <html:text property="abbreviation" styleId="abbreviation"
                                   styleClass="form-control input-sm required"
                                   errorStyleClass="form-control input-sm required has-error"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label for="startDate" class="col-md-3 control-label"><fmt:message key="label.startdate"/></label>

                    <div class="col-md-4">
                        <html:text styleClass="datefield form-control input-sm required"
                                   errorStyleClass="datefield form-control input-sm required has-error"
                                   styleId="startDate" property="startDate" onchange="dateChanged(this);"
                                   onblur="if (dateHaschanged==true) {dateValidateInputFormat(this, this.form); validateDate(this)}"
                                   style="width: 100%"
                                />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label for="endDate" class="col-md-3 control-label"><fmt:message key="label.enddate"/></label>

                    <div class="col-md-4">
                        <html:text styleClass="datefield form-control input-sm required"
                                   errorStyleClass="datefield form-control input-sm required has-error"
                                   styleId="endDate" property="endDate" onchange="dateChanged(this);"
                                   onblur="if (dateHaschanged==true) {dateValidateInputFormat(this, this.form); validateDate(this)}"
                                   style="width: 100%"
                                />
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row spacer-sm">
        <div class="col-md-12 text-center">

            <html:submit styleId="submitButton" property="method" onclick="$('#hiddenMethod').val('.add');"
                         styleClass="btn btn-primary btn-sm"><smg:fmt
                    value="button.add"/></html:submit>
            <html:submit styleId="cancelButton" property="method" onclick="$('#hiddenMethod').val('.cancel');"
                         styleClass="btn btn-default btn-sm"><smg:fmt
                    value="button.cancel"/></html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>

        </div>
    </div>

</html:form>
