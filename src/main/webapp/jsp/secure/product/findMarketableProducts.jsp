<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<jsp:useBean id="findMarketableProductsForm" scope="request" type="com.silvermoongroup.fsa.web.product.form.FindMarketableProductsForm"/>

<%-- Search field --%>
<html:form action="/secure/product/find.do" styleId="tbform" styleClass="form-horizontal">

    <div class="row form-container">

        <c:forEach items="${findMarketableProductsForm.searchProductCriteria}" var="criteriaItem">
            <div class="form-group">
                <div class="col-md-10 form-inline">
                    <html:text property="searchProductCriteria(${criteriaItem.key})"
                    styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>
                    <label for="searchProductCriteria(${criteriaItem.key})"><c:out value="${criteriaItem.key}"/></label>
                </div>
            </div>
        </c:forEach>

        <div class="form-group">
            <div class="col-md-10">
                <html:submit property="method" styleClass="btn btn-primary btn-sm">
                    <fmt:message key="page.findmarktetableproducts.find"/>
                </html:submit>
                <%--<html:submit onclick="this.form.method.value = '.clearFilter'; return true;" styleClass="btn btn-default btn-sm">
                    <fmt:message key="page.findaccountmapping.action.clearfilter"/>
                </html:submit>--%>
                <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
            </div>
        </div>
        
    </div>
</html:form>
<%--<div class="structure" >
	<table id="searchMPtable">
		<tr></tr>

		&lt;%&ndash;Dynamic fields&ndash;%&gt;
		<c:forEach items="${findMarketableProductsForm.searchProductCriteria}" var="criteriaItem">
			<tr>
				<td><label class="control-label"><c:out value="${criteriaItem.key}"/></label></td>
				<td>
					<html:text property="searchProductCriteria(${criteriaItem.key})"
							   styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>
					&lt;%&ndash;<html:text property="searchProductCriteria.[${criteriaItem.key}])"
							   styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>&ndash;%&gt;
				</td>
			</tr>

		</c:forEach>
		<tr></tr>
		<tr>
			<td colspan="4" align="center">
                &lt;%&ndash;<html:submit accesskey="s" titleKey="label.submitwithalts" styleClass="btn btn-primary btn-sm">
                    <fmt:message key="button.search"/>
                </html:submit>&ndash;%&gt;
				<html:submit property="method" styleClass="btn btn-sm btn-default">
					<fmt:message key="page.findmarktetableproducts.find"/>
				</html:submit>
				<span class="spinner"><img
						src='${pageContext.request.contextPath}/images/wait.gif'/></span>
				&lt;%&ndash;
				<% if (!CallBackUtility.isCallBackEmpty(request, response)) {%>
				<html:submit styleId="searchCancelButton" property="method" styleClass="btn btn-link btn-sm"
							 onclick="$('#method').val('.cancel');return true;">
					<fmt:message key="button.cancel"/>
				</html:submit>
				<% } %>
				&ndash;%&gt;
			</td>
		</tr>
	</table>

</div>--%>

<%-- Search results --%>
<div class="table-responsive">
<table id="marketableProductsTable" class="data-table table table-striped table-condensed table-hover">
	<thead>
		<tr>
			<th><fmt:message key="page.findmarktetableproducts.label.name"/></th>
			<th><fmt:message key="page.findmarktetableproducts.label.effectivefrom"/></th>
			<th><fmt:message key="page.findmarktetableproducts.label.effectiveto"/></th>
			<th><fmt:message key="page.findmarktetableproducts.label.criteria"/></th>
			<th><fmt:message key="page.findmarktetableproducts.label.actions"/></th>
		</tr>
	</thead>
	<tbody>

		<c:if test="${empty findMarketableProductsForm.searchResults}">
			<tr><td colspan="5"><fmt:message key="page.findmarktetableproducts.label.noresults"/></td></tr>
		</c:if>
	
		<c:forEach items="${findMarketableProductsForm.searchResults}" var="mp">

            <c:url value="/secure/product.do" var="productUrl">
                <c:param name="objectReference" value="${mp.productObjectReference}" />
            </c:url>

		<tr>
			<td><a href="${productUrl}"><smg:fmt value="spf.psd.productdefinitioncriteria.MARKETABLE_PRODUCT.${mp.name}" fallback="${mp.name}"  /></a></td>
			<td><c:out value="${mp.effectiveFrom}" /></td>
			<td><c:out value="${mp.effectiveTo}" /></td>
			<td>
			<ul class="list-unstyled">
			<c:forEach items="${mp.definitionCriteria}" var="criteria">
              <li><b><smg:fmt value="spf.psd.productdefinitioncriteria.${criteria.key}" fallback="${criteria.key}" /></b> :
                  <smg:fmt value="spf.psd.productdefinitioncriteria.${criteria.key}.${criteria.value}" fallback="${criteria.value}" />
              </li>
			</c:forEach>
			</ul>
			</td>
			<td>
			<c:choose>
				<c:when test="${findMarketableProductsForm.linkingMode}">
					<html:form action="/secure/product/find.do">
						<input type="hidden" name="hiddenMethod" value=".link"/>
						<html:hidden property="selectedProductObjectReference"
									 value="${mp.productObjectReference}"/>
						<html:submit property="method" styleClass="btn btn-primary btn-sm">
							<bean:message key="page.findmarktetableproducts.link"/>
						</html:submit>
						<span class="spinner"><img
								src='${pageContext.request.contextPath}/images/wait.gif'/></span>
					</html:form>
				</c:when>
				<c:otherwise>
					<c:forEach items="${mp.newBusinessRequestKinds}" var="kind">

							<c:url value="/secure/product/createrequest.do" var="requestURL" scope="page">
								<c:param name="requestKindId" value="${kind.id}" />
								<c:param name="mpvObjectReference" value="${mp.objectReference}" />
							</c:url>
						<div>
							<a href="<c:out value="${requestURL}" />" title="<c:out value="${kind.name} (${kind.id})" />"><smg:fmt value="${kind}" /></a>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			</td>

		</tr>
		</c:forEach>
	</tbody>
</table>
</div>