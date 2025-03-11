<%@page import="com.silvermoongroup.common.enumeration.EnumerationType"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<smg:datepicker selector=".datefield"/>

<jsp:useBean id="agreementStatisticsForm" scope="request" type="com.silvermoongroup.fsa.web.agreement.form.AgreementStatisticsForm"/>

<script type="text/javascript">
  $(document).ready(function () {
    $('#lastMonthLink').bind('click', function (e) {
      e.preventDefault();
      $('#dateFrom').val("${agreementStatisticsForm.firstOfPreviousMonth}");
      $('#dateTo').val("${agreementStatisticsForm.lastOfPreviousMonth}");
    });
    $('#currentMonthLink').bind('click', function (e) {
      e.preventDefault();
      $('#dateFrom').val("${agreementStatisticsForm.firstOfCurrentMonth}");
      $('#dateTo').val("${agreementStatisticsForm.lastOfCurrentMonth}");
    });

  });
</script>

<html:form action="/secure/agreementstatistics.do" styleId="tbform" styleClass="form-horizontal">

  <input type="hidden" name="method" value=".calculate"/>

  <div class="form-group">
    <label for="dateFrom" class="col-md-2 control-label"><fmt:message key="page.agreementstatistics.label.date"/></label>

    <div class="col-md-10 form-inline">
      <label for="dateFrom"><fmt:message key="page.agreementstatistics.label.from"/>:</label>
      <html:text property="dateFrom" styleId="dateFrom" styleClass="form-control input-sm datefield"
                 errorStyleClass="form-control input-sm datefield has-error"/>
      <label for="dateTo"><fmt:message key="page.agreementstatistics.label.to"/>:</label>
      <html:text property="dateTo" styleId="dateTo" styleClass="form-control input-sm datefield"
                 errorStyleClass="form-control input-sm datefield has-error"/>
      &nbsp;&nbsp;[ <a href="#" id="lastMonthLink"><fmt:message key="label.lastmonth"/></a> ]
      &nbsp;[ <a href="#" id="currentMonthLink"><fmt:message key="label.currentmonth"/></a> ]
    </div>
  </div>

  <div class="form-group">
    <label for="companyCode" class="col-md-2 control-label"><fmt:message
          key="page.agreementstatistics.label.companycode"/></label>

    <div class="col-md-3">
      <html:select property="companyCode" styleId="companyCode" styleClass="form-control input-sm">
        <smg:enumOptions enumTypeId="<%=EnumerationType.INTERNAL_COMPANY_CODE.getValue()%>" showTerminated = "true"/>
      </html:select>
    </div>
  </div>

  <div class="form-group">
    <div class="col-md-offset-2 col-md-10">
      <html:submit styleId="submitButton" styleClass="btn btn-primary btn-sm">
        <fmt:message key="page.agreementstatistics.action.calculate"/>
      </html:submit>
      <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
    </div>
  </div>

  <c:if test="${!empty agreementStatisticsForm.agreementStatistics}">

    <div class="table-responsive">

      <display:table name="requestScope.agreementStatisticsForm.agreementStatistics.entries"
                     id="tableAgreementStatisticsEntries"
                     decorator="com.silvermoongroup.fsa.web.agreement.AgreementStatisticsTableDecorator"
                     requestURI="/secure/agreementstatistics.do"
                     export="true">

          <display:setProperty name="export.csv.filename" value="${agreementStatisticsForm.exportFilename}.csv"/>
          <display:setProperty name="export.excel.filename" value="${agreementStatisticsForm.exportFilename}.xls"/>

          <display:column property="agreementKind" titleKey="page.agreementstatistics.label.agreementkind" sortable="true"/>
          <display:column property="currencyCode" titleKey="page.agreementstatistics.label.currencycode" sortable="true"/>
          <display:column property="agreementState" titleKey="page.agreementstatistics.label.agreementstate" sortable="true"/>
          <display:column property="agreementCount" titleKey="page.agreementstatistics.label.agreementcount" sortable="false"/>
          <display:column property="agreementPremiumAmount" titleKey="page.agreementstatistics.label.agreementpremiumamount"
                          sortable="false" class="monetary" headerClass="monetary"/>
          <display:column property="requestKind" titleKey="page.agreementstatistics.label.requestkind" sortable="true"/>
          <display:column property="requestCount" titleKey="page.agreementstatistics.label.requestcount" sortable="false"/>
          <display:column property="requestPremiumAmount" titleKey="page.agreementstatistics.label.requestpremiumamount"
                          sortable="false" class="monetary" headerClass="monetary"/>

      </display:table>
    </div>

  </c:if>

</html:form>