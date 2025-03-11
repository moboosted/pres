<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html:form styleId="linkFundForm" action="/secure/agreement/role/linkFund.do" styleClass="form-inline">
    <input type="hidden" name="method" value=".search"/>

    <div class="form-group">
        <label for="fundName" class="control-label"><smg:fmt value="ftx.fund.name"/></label>
        <html:text property="fundName" styleId="fundName" styleClass="form-control input-sm required"
                   errorStyleClass="form-control input-sm required has-error"/>
    </div>
    <div class="form-group">
        <html:submit property="method" styleClass="btn btn-primary btn-sm">
            <smg:fmt value="button.search"/>
        </html:submit>
        <span class="spinner"><img src="${pageContext.request.contextPath}/images/wait.gif"/></span>
    </div>
</html:form>

<div class="table-responsive spacer">
    <display:table name="linkFundForm.searchResults"
                   decorator="com.silvermoongroup.fsa.web.fund.FundSearchResultsTableDecorator"
                   pagesize="100" export="false"
                   requestURI="/secure/agreement/role/linkFund.do?method=.sort" excludedParams="method">

        <display:column property="name" href="linkFund.do?method=.select" paramId="selectedFundObjectReference"
                        paramProperty="objectReference" titleKey="ftx.fund.name"/>
        <display:column property="fundCode" titleKey="ftx.fund.code" />
        <display:column property="description" titleKey="ftx.fund.description"/>
        <display:column property="currencyCode" titleKey="ftx.fund.currencycode"/>
        <display:column property="startDate" titleKey="ftx.fund.startdate"/>
        <display:column property="endDate" titleKey="ftx.fund.enddate"/>

    </display:table>
</div>

<%-- Back to the agreement --%>

<div class="text-center">
    <html:form action="/secure/agreement/role/linkFund.do">
        <input type="hidden" name="method" value=".back"/>
        <html:submit property="method" styleId="backButton" styleClass="btn btn-default btn-sm">
            <fmt:message key="button.back"/>
        </html:submit>
    </html:form></div>
