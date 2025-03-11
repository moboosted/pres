<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<jsp:useBean id="findPublicHolidayForm" scope="request"
             type="com.silvermoongroup.fsa.web.configuration.publicholiday.FindPublicHolidayForm"/>

<smg:datepicker selector=".datefield"/>

<script type="text/javascript">
    $(document).ready(function () {

        $('#thisMonthLink').bind('click', function (e) {
            e.preventDefault();
            $('#dateFrom').val("${findPublicHolidayForm.firstOfCurrentMonth}");
            $('#dateTo').val("${findPublicHolidayForm.lastOfCurrentMonth}");
        });

        $('#thisYearLink').bind('click', function (e) {
            e.preventDefault();
            $('#dateFrom').val("${findPublicHolidayForm.firstOfCurrentYear}");
            $('#dateTo').val("${findPublicHolidayForm.lastOfCurrentYear}");
        });
    });
</script>

<html:form action="/secure/publicholiday/search.do" styleId="findPublicHolidayForm" styleClass="form-inline">

    <div class="form-group">
        <label for="dateFrom"><fmt:message key="label.between"/></label>
        <html:text property="dateFrom" styleId="dateFrom" styleClass="form-control input-sm required datefield "
                   errorStyleClass="form-control input-sm required datefield has-error"/>

    </div>

    <div class="form-group">
        <label for="dateTo">&nbsp;<fmt:message key="label.and"/>&nbsp;</label>
        <html:text property="dateTo" styleId="dateTo" styleClass="form-control input-sm required datefield"
                   errorStyleClass="form-control input-sm required datefield has-error"/>
    </div>

    <div class="form-group">
        &nbsp;&nbsp;[ <a href="#" class="text-info" id="thisMonthLink"><fmt:message
            key="page.publicholiday.label.thismonth"/></a>
        ]
        &nbsp;[ <a href="#" class="text-info" id="thisYearLink"><fmt:message
            key="page.publicholiday.label.thisyear"/></a>
        ]
    </div>

    <div class="form-group">
        <input type="hidden" name="method" value=".search"/>
        <html:submit property="method" styleClass="btn btn-primary btn-sm"><smg:fmt value="button.search"/></html:submit>
        <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
    </div>

    <div class="text-right">
        <a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/secure/publicholiday/new.do">Add a Public holiday</a>
    </div>

    <table>

</html:form>

<c:url var="publicHolidayUrl" value="/secure/publicholiday.do"/>

<c:if test="${findPublicHolidayForm.searchResults != null}">
    <div class="table-responsive">
        <display:table name="${findPublicHolidayForm.searchResults}">

            <display:column property="dateOfHoliday" titleKey="label.date" href="${publicHolidayUrl}"
                            paramId="publicHolidayObjectReference" paramProperty="objectReference"/>
            <display:column property="description" titleKey="label.description" href="${publicHolidayUrl}"
                            paramId="publicHolidayObjectReference" paramProperty="objectReference"/>

        </display:table>
    </div>
</c:if>