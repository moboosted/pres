<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<jsp:useBean id="productForm" scope="request"
             type="com.silvermoongroup.fsa.web.product.ProductForm"/>
<c:set var="product" value="${productForm.product}" scope="page"/>

<c:if test="${productForm.versionReference == null}">
    <div class="row">

    <%-- Left column--%>
    <div class="col-md-4">
        <div class="row">
            <div class="col-md-6">
                <label class="groupHeading" for="productCriteria">
                    <fmt:message key="label.details"/></label>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2"><label class="control-label"><fmt:message key="label.id"/></label></div>
            <div class="col-md-10"><smg:objectReference value="${product.objectReference}" display="id"/></div>
        </div>
        <div class="row">
            <div class="col-md-2"><label class="control-label"><fmt:message key="label.type"/></label></div>
            <div class="col-md-10"><smg:objectReference value="${product.objectReference}" display="type"/></div>
        </div>
        <div class="row">
            <div class="col-md-2"><label class="control-label"><fmt:message key="label.uuid"/></label></div>
            <div class="col-md-10"><c:out value="${product.uuid}"/></div>
        </div>
    </div>

    <%-- Right column--%>
    <div class="col-md-4">
        <div class="row">
            <div class="col-md-12">
                <label class="groupHeading" for="productCriteria">
                    <fmt:message key="page.product.label.definingcriteria"/></label>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div>
                    <fmt:message key="label.the"/> ${fn:length(product.definitionCriteria)} <fmt:message key="page.product.label.definingcriteria.definition"/>:
                </div>
            </div>
        </div>
        <div class="row" id="productCriteria">
            <div class="col-md-12">
            <dl class="dl-horizontal">
                <c:forEach items="${product.definitionCriteria}" var="criterion">
                    <dt><c:out value="${criterion.key}"/></dt>
                    <dd><c:out value="${criterion.value}"/></dd>
                </c:forEach>
            </dl>
                </div>
        </div>
    </div>

</div>
</c:if>

<div id="productVersions" class="spacer table-responsive">
    <c:if test="${productForm.versionReference != null}">
        <label for="productVersionsTable" class="groupHeading"><fmt:message
            key="page.product.label.versionsselect"/></label>
    </c:if>

    <c:if test="${productForm.versionReference == null}">
        <label for="productVersionsTable" class="groupHeading"><fmt:message
                key="page.product.label.versions"/></label>
    </c:if>

    <c:url var="productVersionUrl" value="/secure/productversion.do">
        <c:param name="context" value="${product.objectReference}"/>
        <c:if test="${productForm.versionReference != null}">
            <c:param name="selected" value="${true}"/>
            <c:param name="contextReference" value="${productForm.versionReference}"/>
        </c:if>
    </c:url>

    <display:table name="${product.productVersions}"
                   decorator="com.silvermoongroup.fsa.web.product.ProductVersionsTableDecorator"
                   id="productVersionsTable">

        <display:column property="id" titleKey="label.id" href="${productVersionUrl}" paramId="objectReference"
                        paramProperty="objectReference"/>
        <display:column property="type" titleKey="label.type" href="${productVersionUrl}" paramId="objectReference"
                        paramProperty="objectReference"/>
        <display:column property="label" titleKey="label.label"/>
        <display:column property="businessEffectivePeriod" titleKey="label.businesseffectiveperiod"/>
        <display:column property="transactionEffectivePeriod" titleKey="label.transactionperiod"/>
        <display:column property="specificationLifeCycleStatus" titleKey="page.productversion.label.lifecyclestatus"/>


    </display:table>

    <c:if test="${productForm.versionReference != null}">
        <html:form action="/secure/product.do">
            <input type="hidden" name="method" id="method" value=".back"/>
            <html:hidden property="context" value="${productForm.objectReference}"/>
            <html:hidden property="versionReference" value="${productForm.versionReference}"/>
            <html:submit styleClass="btn btn-link btn-sm">
                <fmt:message key="label.cancel"/>
            </html:submit>
        </html:form>
    </c:if>
</div>
