<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<jsp:useBean id="productVersionForm" scope="request"
             type="com.silvermoongroup.fsa.web.product.ProductVersionForm"/>
<c:set var="productVersion" value="${productVersionForm.productVersion}" scope="page"/>

<c:url value="/secure/product.do" var="productUrl">
    <c:param name="objectReference" value="${productVersion.productObjectReference}"/>
</c:url>

<div>
    <div class="row">
        <label for="availableRequests" class="groupHeading col-md-6"><fmt:message
                key="label.details"/></label>
    </div>

    <div class="row">

        <%-- Left column--%>
        <div class="col-md-6">

            <div class="row">
                <div class="col-md-3"><label class="control-label"><fmt:message key="label.id"/></label></div>
                <div class="col-md-4"><smg:objectReference value="${productVersion.objectReference}"
                                                           display="id"/></div>
            </div>
            <div class="row">
                <div class="col-md-3"><label class="control-label"><fmt:message key="label.type"/></label></div>
                <div class="col-md-4"><smg:objectReference value="${productVersion.objectReference}"
                                                           display="type"/></div>
            </div>
            <div class="row">
                <div class="col-md-3"><label class="control-label"><fmt:message key="label.label"/></label></div>
                <div class="col-md-4"><c:out value="${productVersion.label}"/></div>
            </div>
            <div class="row">
                <div class="col-md-3"><label class="control-label"><fmt:message
                        key="page.productversion.label.lifecyclestatus"/></label></div>
                <div class="col-md-4"><c:out value="${productVersion.specificationLifeCycleStatus}"/></div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <label for="replacedRadio" class="control-label"><fmt:message
                            key="label.replaced"/></label>
                </div>
                <div class="col-md-4">
                    <html:form>
                    <label class="radio-inline">
                        <html:radio property="replaced" value="true" styleId="replacedRadio" onclick="$('#replaced').val('true')"
                                    errorStyleClass="has-error"/>Yes
                    </label>
                    <label class="radio-inline">
                        <html:radio property="replaced" value="false" styleId="replacedRadio" onclick="$('#replaced').val('false')"
                                    errorStyleClass="has-error"/>No
                    </label>
                    </html:form>
                </div>
            </div>
        </div>

        <%-- Left column--%>
        <div class="col-md-6">

            <div class="row">
                <div class="col-md-3"><label class="control-label"><fmt:message
                        key="page.productversion.label.product"/></label></div>
                <div class="col-md-4"><smg:objectReference display="id"
                                                           value="${productVersion.productObjectReference}"/></div>
            </div>
            <div class="row">
                <div class="col-md-3"><label class="control-label"><fmt:message
                        key="label.businesseffectiveperiod"/></label></div>
                <div class="col-md-4"><smg:fmt value="${productVersion.businessEffectivePeriod.start}"/> &rarr; <smg:fmt
                        value="${productVersion.businessEffectivePeriod.end}"/></div>
            </div>
            <div class="row">
                <div class="col-md-3"><label class="control-label"><fmt:message key="label.transactionperiod"/></label>
                </div>
                <div class="col-md-4"><smg:fmt value="${productVersion.transactionEffectivePeriod.start}"/> &rarr;
                    <smg:fmt
                            value="${productVersion.transactionEffectivePeriod.end}"/></div>
            </div>
            <div class="row">
                <div class="col-md-3"><label class="control-label"><fmt:message
                        key="page.productversion.label.plannedissuancedate"/></label></div>
                <div class="col-md-4"><smg:fmt value="${productVersion.plannedIssuanceDate}"/></div>
            </div>
            <c:if test="${productVersionForm.replaced}">
            <div class="row">
                <div class="col-md-3"><label class="control-label"><fmt:message
                        key="page.productversion.label.replacedwith"/></label></div>
                <div class="col-md-4" style="display: flex;">
                    <c:if test="${productVersion.replacedWith != null}">
                        <smg:objectReference display="id" value="${productVersion.replacedWith.objectReference}"/>
                        <html:form action="/secure/productversion.do" styleId="updateProductVersionFormRemove" styleClass="form-horizontal">
                            <input type="hidden" name="method" value=".remove"/>
                            <html:hidden property="productObjectReference" value="${productVersionForm.productObjectReference}"/>
                            <html:hidden property="context" value="${productVersionForm.context}"/>
                            <html:submit styleClass="btn btn-danger btn-sm" style="margin-left: 5px">
                                <fmt:message key="page.productversion.action.remove" />
                            </html:submit>
                        </html:form>
                    </c:if>
                    <c:if test="${productVersion.replacedWith == null}">
                        <html:form action="/secure/productversion.do" styleId="updateProductVersionFormReplace" styleClass="form-horizontal">
                            <html:hidden property="method" value=".replaceFind" />
                            <html:hidden property="context" value="${productVersionForm.context}"/>
                            <html:hidden property="productObjectReference" value="${productVersionForm.productObjectReference}"/>
                            <html:submit styleClass="btn btn-default btn-xs">
                                <fmt:message key="page.productversion.action.replace" />
                            </html:submit>
                        </html:form>
                    </c:if>
                </div>
            </div>
            </c:if>
        </div>
    </div>
    <div class="row">
        <div class="form-group">
            <div class="col-md-offset-3 col-md-9">
                <html:form action="/secure/productversion.do" styleId="updateProductVersionFormSave" styleClass="form-horizontal">
                    <c:if test="${productVersion.replacedWith != null}">
                        <html:hidden property="linkedReference" value="${productVersion.replacedWith.objectReference}"/>
                    </c:if>

                    <input type="hidden" name="method" value=".save"/>
                    <html:hidden property="productObjectReference" value="${productVersionForm.productObjectReference}"/>
                    <html:hidden property="context" value="${productVersionForm.context}"/>
                    <html:hidden property="replaced" value="${productVersionForm.replaced}" styleId="replaced" />
                    <html:submit styleClass="btn btn-primary btn-sm">
                        <fmt:message key="page.productversion.action.save" />
                    </html:submit>
                </html:form>
            </div>
        </div>
    </div>
</div>

<div id="availableRequests" class="spacer">

    <label for="availableRequests" class="groupHeading"><fmt:message
            key="page.productversion.label.availablerequests"/></label>

    <ul>
        <c:forEach items="${productVersion.newBusinessRequestKinds}" var="kind">

            <c:url value="/secure/product/createrequest.do" var="requestURL" scope="page">
                <c:param name="requestKindId" value="${kind.id}"/>
                <c:param name="mpvObjectReference" value="${productVersion.objectReference}"/>
            </c:url>
            <li>
                <a href="<c:out value="${requestURL}" />" title="<c:out value="${kind.name} (${kind.id})" />"><smg:fmt
                        value="${kind}"/></a>
            </li>
        </c:forEach>
    </ul>


</div>