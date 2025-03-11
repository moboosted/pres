<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<smg:datepicker selector=".datefield"/>

<script type="text/javascript">
    $(function () {

        // clear out elements that are not relevant
        //
        $('#executionOptionAgreement').change(function () {
            $('#moneySchedulerObjectReference').val("");
        });

        $('#executionOptionObjectReference').change(function () {
            $('#agreementNumber').val("");
        });

        // focus
        //
        $('#schedulerTypeId').focus(function () {
            $('#executionOptionAgreement').prop('checked', true).change();
        });
        $('#agreementNumber').focus(function () {
            $('#executionOptionAgreement').prop('checked', true).change();
        });
        $('#agreementKindId').focus(function () {
            $('#executionOptionAgreement').prop('checked', true).change();
        });


        $('#moneySchedulerObjectReference').focus(function () {
            $('#executionOptionObjectReference').prop('checked', true).change();
        });
    });
</script>

<html:form action="/secure/moneyscheduler/execute.do" styleId="executeschedulerform" styleClass="form-horizontal">

    <input type="hidden" name="method" value=".executeScheduler"/>

    <fieldset class="col-md-6">
        <legend><html:radio property="executionOption" value="agreement"
                            styleId="executionOptionAgreement"/> <fmt:message
                key="page.executemoneyscheduler.executeforagreement"/></legend>
        <div class="col-md-12">

            <div class="form-group">
                <label for="schedulerTypeId" class="col-md-3 control-label"><fmt:message
                        key="page.executemoneyscheduler.schedulertype"/></label>
                <div class="col-md-6">
                    <html:select property="schedulerTypeId" styleClass="form-control input-sm"
                                 errorStyleClass="form-control input-sm has-error"
                                 styleId="schedulerTypeId">
                        <smg:typeTree rootTypeId="502"/>
                    </html:select>
                </div>
            </div>
            <div class="form-group">
                <label for="agreementNumber" class="col-md-3 control-label"><fmt:message
                        key="page.executemoneyscheduler.agreementnumber"/></label>
                <div class="col-md-6">
                    <html:text property="agreementNumber" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"
                               styleId="agreementNumber"/>
                </div>
            </div>
            <div class="form-group">
                <label for="agreementKindId" class="col-md-3 control-label"><fmt:message
                        key="page.executemoneyscheduler.agreementkind"/></label>
                <div class="col-md-6">
                    <html:select property="agreementKindId" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"
                                 styleId="agreementKindId">
                        <smg:kinds kindType="TopLevelAgreement"/>
                    </html:select>
                </div>
            </div>

        </div>
    </fieldset>
    <fieldset class="col-md-6">
        <legend><html:radio property="executionOption" value="objectreference"
                            styleId="executionOptionObjectReference"/> <fmt:message
                key="page.executemoneyscheduler.executeforobjectreference"/></legend>
        <div class="col-md-12">
            <div class="form-group">
                <label for="moneySchedulerObjectReference" class="col-md-3 control-label"><fmt:message
                        key="common.type.Money Scheduler"/></label>
                <div class="col-md-6">
                    <html:text property="moneySchedulerObjectReference" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"
                               styleId="moneySchedulerObjectReference"/>
                </div>
            </div>

        </div>
    </fieldset>

    <div class="form-group">
        <div class="col-md-12 form-inline text-center">
            <label for="schedulerTypeId"><fmt:message
                    key="ftx.moneyschedulerexecution.requestedexecutiondate"/></label>
            <html:text property="executionDate" styleClass="form-control input-sm datefield"
                       errorStyleClass="form-control input-sm datefield has-error"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-12 form-inline text-center">
            <html:submit property="method" styleId="submitButton" styleClass="btn btn-primary btn-sm">
                <fmt:message key="page.executemoneyscheduler.action.execute"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>
</html:form>
