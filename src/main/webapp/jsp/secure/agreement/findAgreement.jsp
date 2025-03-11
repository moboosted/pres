<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<jsp:useBean id="findAgreementForm" scope="request"
             type="com.silvermoongroup.fsa.web.agreement.form.FindAgreementForm"/>


<script type="text/javascript">
    $(function () {
        $('.clickable').bind('click', function() {
            $(this).find('img').toggle();
        });
    });
</script>

<html:form action="/secure/agreement/find.do" styleId="findagreementform" styleClass="form-horizontal">
    <input type="hidden" name="method" value=".find"/>

    <div class="row form-container">

            <%-- Left column--%>
        <div class="col-md-6">

            <div class="row">
                <div class="form-group">
                    <label for="agreementId" class="col-md-3 control-label"><fmt:message key="label.id"/></label>

                    <div class="col-md-4">
                        <html:text styleId="agreementId" property="agreementId" styleClass="form-control input-sm"
                                   errorStyleClass="form-control input-sm has-error"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="externalReference" class="col-md-3 control-label"><fmt:message
                            key="page.findagreement.agreementnumber"/></label>

                    <div class="col-md-4">
                        <html:text styleId="externalReference" property="externalReference" styleClass="form-control input-sm"
                                   errorStyleClass="form-control input-sm has-error"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <div class="col-md-9 col-md-offset-3">
                        <html:checkbox property="restrictToTLA" styleId="restrictToTLA" styleClass="checkbox-inline"/>
                        &nbsp;<label for="restrictToTLA" class=" control-label"> <fmt:message
                            key="page.findagreement.restricttotla"/></label>
                    </div>
                </div>
            </div>

        </div>

            <%-- Right column--%>
        <div class="col-md-6">

            <div class="row">
                <div class="form-group">
                    <label for="topLevelAgreementKind" class="col-md-3 control-label"><fmt:message
                            key="page.findagreement.toplevelagreementkind"/></label>

                    <div class="col-md-4">
                        <html:select property="tlaKind" styleId="topLevelAgreementKind" styleClass="form-control input-sm"
                                     errorStyleClass="form-control input-sm has-error">
                            <html:option value=""><fmt:message key="page.findagreement.allkinds"/></html:option>
                            <smg:kinds kindType="TopLevelAgreement"/>
                        </html:select>
                    </div>
                </div>
            </div>

        </div>

    </div>

    <div class="row spacer-sm">
        <div class="col-md-12 text-center">
            <html:submit accesskey="s" titleKey="label.submitwithalts" styleClass="btn btn-primary btn-sm">
                <fmt:message key="button.search"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>
</html:form>

<c:if test="${!empty findAgreementForm.searchResults}">
    <div class="table-responsive spacer">
        <table class="data-table table table-condensed table-hover find-agreement-table">
            <thead>
            <tr>
                <th>&nbsp;</th>
                <th><fmt:message key="page.findagreement.number"/></th>
                <th><fmt:message key="page.findagreement.version"/></th>
                <th><fmt:message key="page.findagreement.kind"/></th>
                <th><fmt:message key="page.findagreement.startdate"/></th>
                <th><fmt:message key="page.findagreement.enddate"/></th>
                <th><fmt:message key="page.findagreement.status"/></th>
                <th class="text-center"><fmt:message key="page.findagreement.action"/></th>
            </tr>
            </thead>
            <tbody>


            <c:forEach var="agreementVO" items="${findAgreementForm.searchResults}" varStatus="agreementVOStatus">
                <c:set var="objectId" value="${agreementVO.primaryPartyObjectReference}" scope="page"/>
                <tr class="${agreementVOStatus.index %2 == 0 ? "even" : "odd"}">

                    <td class="clickable plus" data-toggle="collapse" data-target=".collapsed-${agreementVOStatus.index}">
                        <c:if test="${findAgreementForm.linkingMode == false}">
                            <img src="<%=request.getContextPath()%>/images/icon_plus.gif">
                            <img style="display: none" src="<%=request.getContextPath()%>/images/icon_minus.gif">
                        </c:if>
                    </td>
                    <td><b><c:out value="${agreementVO.number}"/></b></td>
                    <td><c:out
                            value="${agreementVO.versionNumber.major}.${agreementVO.versionNumber.minor}"/></td>
                    <td
                            title="<c:out value="${agreementVO.kind.name} (${agreementVO.kind.id})" />">
                        <smg:fmt value="${agreementVO.kind}"/></td>
                    <td><smg:fmt value="${agreementVO.startDate}"/></td>
                    <td><smg:fmt value="${agreementVO.endDate}"/>&nbsp;</td>
                    <td><smg:fmt value="${agreementVO.state}"/></td>
                    <td class="text-right">
                            <%-- When linking, we expect a versioned agreement object reference --%>
                        <c:choose>
                            <c:when test="${findAgreementForm.linkingMode}">
                                <html:form action="/secure/agreement/find.do">
                                    <input type="hidden" name="hiddenMethod" value=".link"/>
                                    <html:hidden property="selectedAgreementObjectReference"
                                                 value="${agreementVO.versionedAgreementObjectReference}"/>
                                    <html:submit property="method" styleClass="btn btn-primary btn-sm"
                                            ><bean:message
                                            key="page.findagreement.link"/></html:submit>
                                    <span class="spinner"><img
                                            src='${pageContext.request.contextPath}/images/wait.gif'/></span>
                                </html:form>
                            </c:when>
                            <c:otherwise>
                                <html:form action="/secure/agreement/find.do">
                                    <input type="hidden" name="hiddenMethod" value=".retrieve"/>
                                    <html:hidden property="selectedAgreementObjectReference"
                                                 value="${agreementVO.topLevelAgreementObjectReference}"/>
                                    <html:submit property="method" styleClass="btn btn-sm btn-primary"
                                            ><bean:message
                                            key="page.findagreement.retrieve"/></html:submit>
                                    <span class="spinner"><img
                                            src='${pageContext.request.contextPath}/images/wait.gif'/></span>

                                </html:form>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>

                <c:if test="${!empty agreementVO.previousAgreementVersionsList && (!findAgreementForm.linkingMode)}">

                    <c:forEach items="${agreementVO.previousAgreementVersionsList}" var="previousAgreementVO"
                               varStatus="index">
                        <tr class="collapse out collapsed-${agreementVOStatus.index}">
                            <td>&nbsp;</td>
                            <td><c:out value="${agreementVO.number}"/></td>
                            <td><c:out
                                    value="${previousAgreementVO.versionNumber.major}.${previousAgreementVO.versionNumber.minor}"/></td>
                            <td title="<c:out value="${agreementVO.kind.name} (${agreementVO.kind.id})" />"><smg:fmt
                                    value="${agreementVO.kind}"/></td>
                            <td><smg:fmt value="${previousAgreementVO.startDate}"/></td>
                            <td><smg:fmt value="${previousAgreementVO.endDate}"/></td>
                            <td><smg:fmt value="${previousAgreementVO.state}"/></td>
                            <td class="text-right">
                                <c:choose>
                                    <c:when test="${previousAgreementVO.canCreateNextVersion}">
                                        <html:form action="/secure/agreement/find.do">
                                            <html:hidden property="selectedAgreementObjectReference"
                                                         value="${previousAgreementVO.objectReference}"/>
                                            <input type="hidden" name="hiddenMethod" value=".retrieve"/>
                                            <html:submit property="method" styleClass="btn btn-sm btn-default">
                                                <fmt:message key="page.findagreement.retrieve"/>
                                            </html:submit>
                                            <span class="spinner"><img
                                                    src='${pageContext.request.contextPath}/images/wait.gif'/></span>
                                        </html:form>
                                    </c:when>
                                    <c:otherwise>
                                        <html:form action="/secure/agreement/find.do">
                                            <input type="hidden" name="hiddenMethod" value=".view"/>
                                            <html:hidden property="selectedAgreementObjectReference"
                                                         value="${previousAgreementVO.objectReference}"/>
                                            <html:submit property="method"
                                                         styleClass="btn btn-sm btn-default"><fmt:message
                                                    key="page.findagreement.view"/></html:submit>
                                            <span class="spinner"><img
                                                    src='${pageContext.request.contextPath}/images/wait.gif'/></span>
                                        </html:form>
                                    </c:otherwise>
                                </c:choose>

                            </td>
                        </tr>

                    </c:forEach>
                </c:if>
            </c:forEach>

            </tbody>
        </table>
    </div>
</c:if>