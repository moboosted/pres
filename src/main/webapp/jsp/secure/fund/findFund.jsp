<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html:form styleId="searchFundForm" action="/secure/fund/search.do" styleClass="form-inline">
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

<div class="text-right">
    <html:form action="/secure/fund/create.do">
        <html:submit styleClass="btn btn-primary btn-sm">
            <fmt:message key="page.fund.label.addafund" />
        </html:submit>
    </html:form>
</div>

<c:url value="/secure/fund.do" var="fundUrl"/>
<div class="table-responsive spacer">
    <display:table name="searchFundForm.searchResults"
                   decorator="com.silvermoongroup.fsa.web.fund.FundSearchResultsTableDecorator"
                   pagesize="20">

        <display:column property="name" titleKey="ftx.fund.name" href="${fundUrl}"
                        paramId="fundObjectReference" paramProperty="objectReference"/>
        <display:column property="fundCode" titleKey="ftx.fund.code" />
        <display:column property="description" titleKey="ftx.fund.description" />
        <display:column property="currencyCode" titleKey="ftx.fund.currencycode" />
        <display:column property="startDate" titleKey="ftx.fund.startdate" />
        <display:column property="endDate" titleKey="ftx.fund.enddate" />
        <display:column property="probableGrowthRate" titleKey="ftx.fund.probablegrowthrate" />
        <display:column property="optimisticGrowthRate" titleKey="ftx.fund.optimisticgrowthrate" />

    </display:table>
</div>
