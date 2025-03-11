<%@page import="com.silvermoongroup.common.enumeration.EnumerationType" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<smg:datepicker selector=".datefield"/>

<script type="text/javascript">
    $(document).ready(function () {
        $(function () {
            $('#viewEnumerationsForm').submit(function () {
                $(this).find('input:submit').attr('disabled', 'disabled');
                $(this).find('.spinner').css('visibility', 'visible');
            });
        });

        $('#search').keyup(function () {
            var $rows = $('#enumeration tbody tr');
            var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();

            $rows.show().filter(function () {
                var text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
                return !~text.indexOf(val);
            }).hide();
        });
    });

    function confirmTerminate(form) {

        var title = '<fmt:message key="label.confirmation" />';
        var labelYes = '<fmt:message key="label.yesno.true" />';
        var labelNo = '<fmt:message key="label.yesno.false" />';
        var confirmMessage='<fmt:message key="page.viewenumeration.message.confirmterminate"/>';

        if (confirmMessage) {
            bootbox.dialog({
                message: confirmMessage,
                title: title,
                buttons: {
                    ok: {
                        label: labelYes,
                        className: "btn-primary",
                        callback: function () {
                            return doFormSubmit(form);
                        }
                    },
                    cancel: {
                        label: labelNo
                    }
                }
            });
            return false;
        }
        return doFormSubmit(form);
    }

    function doFormSubmit(form){
        form.submit();
        return false;
    }
</script>

<div class="row">
    <div class="col-lg-6">
        <div class="input-group">
            <label for="search" class="control-label"><fmt:message key="page.viewenumeration.label.search"/></label>
            <input type="text" id="search" class="form-control input-sm" />
        </div>
        <!-- /input-group -->
    </div>
    <!-- /.col-lg-6 -->
</div>

<c:set var="unitOfMeasureValue" value="<%=EnumerationType.UNIT_OF_MEASURE.getValue()%>"/>
<c:set var="internalCoCodeValue" value="<%=EnumerationType.INTERNAL_COMPANY_CODE.getValue()%>"/>

<div class="spacer">
    <div class="section-divider"><label class="groupHeading">${viewEnumerationsForm.enumerationTypeName}</label></div>
</div>


<div class="table-responsive spacer">

    <display:table name="${viewEnumerationsForm.enumerations}" id="enumeration"
                   requestURI="/secure/enumeration/viewEnumerations.do"
                   decorator="com.silvermoongroup.fsa.web.enumeration.EnumerationsTableDecorator"
                   defaultsort="1" sort="list">

        <display:column property="value" titleKey="label.code" style="width:3%" class="l m" headerClass="l m"/>
        <c:if test="${enumeration.enumerationTypeId == unitOfMeasureValue}">
            <display:column property="measure" titleKey="page.enumeration.label.measure" style="width:17%" class="l m"
                            headerClass="l m"/>
        </c:if>
        <c:if test="${enumeration.enumerationTypeId == internalCoCodeValue}">
            <display:column property="organisation" titleKey="page.enumeration.label.organisation" style="width:17%"
                            class="l m" headerClass="l m"/>
        </c:if>
        <display:column property="name" titleKey="label.name" style="width:10%" class="l m" headerClass="l m"/>
        <display:column property="description" titleKey="label.description" style="width:17%" class="l m"
                        headerClass="l m"/>
        <display:column property="abbreviation" titleKey="label.abbreviation" style="width:6%" class="l m"
                        headerClass="l m"/>
        <display:column property="startDate" titleKey="label.startdate" style="width:10%" class="l m"
                        headerClass="l m"/>
        <display:column property="endDate" titleKey="label.enddate" style="width:10%" class="l m" headerClass="l m"/>

        <display:column class="m" style="width:5%">
            <html:form action="/secure/enumeration/viewEnumerations.do">
                <input type="hidden" name="method" value=".update"/>
                <html:hidden property="enumerationReference" value="${enumeration.enumerationReference}"/>
                <html:submit disabled="${!enumeration.mutable}" onclick="setFilterGrid('test');">
                    <%--  				    <html:submit styleId="edit" onclick = "disable('${enumeration.mutable}', '${enumeration.effectivePeriod.end}');" disabled = "disable();" > --%>
                    <%-- 						<html:submit onclick="enableTerminate('${enumeration.mutable}', '${enumeration.effectivePeriod.end}')" disabled = "enableTerminate('${enumeration.mutable}', '${enumeration.effectivePeriod.end}')" > --%>
                    <fmt:message key="label.edit"/>
                </html:submit>
            </html:form>
        </display:column>
        <display:column class="m" style="width:5%">
            <html:form action="/secure/enumeration/viewEnumerations.do" styleId="terminateEnumerationForm">
                <html:hidden property="enumerationReference" value="${enumeration.enumerationReference}"/>
                <html:hidden property="enumerationTypeId" value="${enumeration.enumerationTypeId}"/>
                <input type="hidden" name="method" value=".terminate"/>
                <html:button property="method" onclick="confirmTerminate(this.form)" disabled="${!enumeration.mutable}">
                    <fmt:message key="label.terminate"/>
                </html:button>
            </html:form>
        </display:column>

    </display:table>
</div>

<html:form action="/secure/enumeration/viewEnumerations.do">
    <div class="row spacer-sm">
        <div class="col-md-12 text-center">
            <html:hidden property="enumerationTypeId" value="${viewEnumerationsForm.enumerationTypeId}"/>
            <html:hidden property="hiddenMethod" styleId="hiddenMethod" value=""/>
            <html:submit styleId="submitButton" property="method" onclick="$('#hiddenMethod').val('.add');"
                         styleClass="btn btn-primary btn-sm"><fmt:message
                    key="page.addenumeration.title"/></html:submit>
            <html:submit styleId="cancelButton" property="method" onclick="$('#hiddenMethod').val('.cancel');"
                         styleClass="btn btn-default btn-sm"><smg:fmt
                    value="button.cancel"/></html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>

        </div>
    </div>
</html:form>
