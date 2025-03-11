<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<script src="${pageContext.request.contextPath}/js/bootbox.min.js"></script>
<smg:datepicker selector=".datefield"/>

<script type="text/javascript">
    function confirmDelete() {
        var confirmMessage = "<fmt:message key="page.publicholiday.message.confirmdelete"/>";
        var labelYes = '<fmt:message key="label.yesno.true" />';
        var labelNo = '<fmt:message key="label.yesno.false" />';
        var title = '<fmt:message key="label.confirmation" />';
        bootbox.dialog({
            message: confirmMessage,
            title: title,
            buttons: {
                ok: {
                    label: labelYes,
                    className: "btn-primary",
                    callback: function () {
                        $('#method').val('.delete');
                        $('#updatePublicHolidayForm').submit();
                    }
                },
                cancel: {
                    label: labelNo,
                }
            }
        });
    }
</script>

<html:form action="/secure/publicholiday.do" styleId="updatePublicHolidayForm" styleClass="form-horizontal">
    <html:hidden property="publicHolidayObjectReference" />

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
            <input id="method" type="hidden" name="method"/>
            <html:submit property="method" styleClass="btn btn-primary btn-sm" onclick="$('#method').val('.update');"><smg:fmt value="button.update" /></html:submit>
            <html:button property="method" styleClass="btn btn-danger btn-sm" onclick="confirmDelete();"><smg:fmt value="label.delete" /></html:button>
            <html:submit property="method" styleClass="btn btn-link btn-sm" onclick="$('#method').val('.cancel');"><smg:fmt value="button.cancel" /></html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>

</html:form>