<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<smg:datepicker selector=".datefield"/>

<html:form action="/secure/publicholiday/new.do" styleClass="form-horizontal" styleId="createPublicHolidayForm">
    <input type="hidden" name="method" id="method" value=""/>

    <div class="form-group">
        <label for="date" class="col-md-2 control-label"><fmt:message key="label.date"/></label>
        <div class="col-md-3">
            <html:text property="date" styleClass="form-control input-sm required datefield" styleId="date" errorStyleClass="form-control input-sm required has-error datefield"/>
        </div>
    </div>

    <div class="form-group">
        <label for="description" class="col-md-2 control-label"><fmt:message key="label.description"/></label>
        <div class="col-md-3">
            <html:text property="description" styleId="description" styleClass="form-control input-sm required" errorStyleClass="form-control input-sm required has-error"/>
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-10">
            <html:submit property="method" styleClass="btn btn-primary btn-sm" onclick="$('#method').val('.add');"><smg:fmt value="button.add" /></html:submit>
            <html:submit property="method" styleClass="btn btn-link btn-sm" onclick="$('#method').val('.cancel');"><smg:fmt value="button.cancel" /></html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>

</html:form>
