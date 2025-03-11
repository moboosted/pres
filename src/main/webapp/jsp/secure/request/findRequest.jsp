<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<smg:datepicker selector=".datefield"/>

<script>
    $(function () {
        $('#agreementId').attr("placeholder", "<fmt:message key="label.any"/> <fmt:message key="label.id"/>");
        $('#agreementExternalRef').attr("placeholder", "<fmt:message key="label.any"/> <fmt:message key="label.externalreference"/>");
    });
</script>

<jsp:useBean id="requestSearchForm" scope="request" class="com.silvermoongroup.fsa.web.request.RequestSearchForm"/>

<html:form action="/secure/request/search.do" styleId="requestSearchForm" styleClass="form-horizontal">

    <input type="hidden" name="method" value=".search"/>

    <div class="row form-container">

            <%--Left column--%>
        <div class="col-md-6">
            <div class="row">
                <div class="form-group">
                    <label for="requestId" class="col-md-3 control-label"><fmt:message
                            key="page.requestsearch.label.requestid"/></label>

                    <div class="col-md-2"><html:text property="requestId" styleId="requestId" styleClass="form-control input-sm"
                                                     errorStyleClass="form-control input-sm has-error"/></div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="requestKind" class="col-md-3 control-label"><fmt:message
                            key="page.requestsearch.label.requestkind"/></label>

                    <div class="col-md-6">
                        <html:select property="requestKind" styleId="requestKind" styleClass="form-control input-sm"
                                     errorStyleClass="form-control input-sm has-error">
                            <html:option value=""><fmt:message key="page.requestsearch.label.anykind"/></html:option>
                            <smg:kinds kindType="Request"/>
                        </html:select>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group-sm">
                    <div class="col-md-3 col-md-offset-6">
                        <html:text property="agreementId" styleId="agreementId" styleClass="form-control input-sm"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group-sm">
                    <div class="col-md-5 col-md-offset-1 form-inline">
                        <html:select property="targetOrResultActualOption" styleId="targetOrResultActualOption"
                                     styleClass="form-control input-sm">
                            <smg:generalOptionsCollection property="requestOptions"/>
                        </html:select>
                        <span><fmt:message key="page.requestsearch.label.anagreementwith"/>:</span>

                    </div>

                    <div class="col-md-3">
                        <html:text property="agreementExternalRef" styleId="agreementExternalRef" styleClass="form-control input-sm"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group-sm">
                    <div class="col-md-3 col-md-offset-6">
                        <html:select property="agreementKind" styleId="agreementKind" styleClass="form-control input-sm">
                            <html:option value=""><fmt:message key="page.requestsearch.label.anykind"/></html:option>
                            <smg:kinds kindType="TopLevelAgreement"/>
                        </html:select>
                    </div>
                </div>
            </div>

        </div>

            <%--Right column--%>
        <div class="col-md-6">

            <div class="row">
                <div class="form-group">
                    <label for="requestStartDate" class="col-md-3 control-label"><fmt:message
                            key="page.request.label.requestdate"/></label>

                    <div class="col-md-6 form-inline">
                        <fmt:message key="label.between"/>
                        <html:text property="requestStartDate" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error"
                                   styleId="requestStartDate"/>
                        <fmt:message key="label.and"/>
                        <html:text property="requestEndDate" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error"
                                   styleId="requestEndDate"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="requestedStartDate" class="col-md-3 control-label"><fmt:message
                            key="page.request.label.requesteddate"/></label>

                    <div class="col-md-6 form-inline">
                        <fmt:message key="label.between"/>
                        <html:text property="requestedStartDate" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error"
                                   styleId="requestedStartDate"/>
                        <fmt:message key="label.and"/>
                        <html:text property="requestedEndDate" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error"
                                   styleId="requestedEndDate"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="requestStatus" class="col-md-3 control-label"><fmt:message
                            key="spf.request.status.fq"/></label>

                    <div class="col-md-5">
                        <html:select property="requestStatus" styleId="requestStatus" styleClass="form-control input-sm">
                            <smg:generalOptionsCollection property="requestStatuses"/>
                        </html:select>
                    </div>
                </div>
            </div>

        </div>

        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>

    </div>

    <div class="row spacer">
        <div class="col-md-12 text-center">
            <html:submit property="method" styleClass="btn btn-primary btn-sm">
                <fmt:message key="page.requestsearch.action.search"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>

    <c:if test="${requestSearchForm.results != null}">
        <div class="table-responsive spacer">
            <display:table name="requestSearchForm.results"
                           decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.RequestSearchResultsTableDecorator"
                           pagesize="${requestSearchForm.rowsPerPage}"
                           size="${requestSearchForm.results.fullListSize}"
                           export="false"
                           requestURI="/secure/request/search.do"
                           id="requestSearchResults"
                           partialList="true"
                           sort="external"
                           defaultsort="1">

                <display:column property="requestId" href="search.do?method=.select"
                                paramId="selectedRequestObjectReference" paramProperty="requestObjectRef"
                                titleKey="label.id" sortable="true"
                                sortName="requestId"/>
                <display:column property="requestKind" titleKey="label.kind" sortable="true" sortName="requestKind"
                                href="search.do?method=.select" paramId="selectedRequestObjectReference"
                                paramProperty="requestObjectRef"/>
                <display:column property="requestLifeCycleStatus" titleKey="spf.request.status" sortable="true"
                                sortName="requestLifeCycleStatus"/>
                <display:column property="requestDate" titleKey="spf.request.requestdate" sortable="true"
                                sortName="requestDate"/>
                <display:column property="requestedDate" titleKey="spf.request.requesteddate" />
                <display:column property="executedDate" titleKey="spf.request.executeddate" sortable="true"
                                sortName="executedDate"/>
                <display:column property="agreementExternalReference" href="../agreement/find.do"
                                paramId="externalReference" paramProperty="agreementExternalReference"
                                titleKey="spf.agreement.externalreference.fq" sortable="true"
                                sortName="agreementExternalReference"/>
                <display:column property="agreementKind" titleKey="spf.agreement.kind.fq" sortable="true"
                                sortName="agreementKind"/>
                <display:column property="agreementLifeCycleStatus" titleKey="spf.agreement.status.fq" />
                <display:column property="agreementVersionNumber" titleKey="spf.agreement.version.fq" />

            </display:table>
        </div>
    </c:if>

</html:form>