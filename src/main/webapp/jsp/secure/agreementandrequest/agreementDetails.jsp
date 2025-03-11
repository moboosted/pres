<div class="row">

    <%--Left Column--%>
    <div class="col-md-6">
        <div class="row">
            <label class="col-md-3"><fmt:message key="label.id"/></label>

            <div id="agreementId" class="col-md-9"><smg:objectReference
                    value="${structuredActualDTO.objectReference}"/></div>
        </div>
        <div class="row">
            <label class="col-md-3"><fmt:message key="label.type"/></label>

            <div class="col-md-9"><smg:objectReference value="${structuredActualDTO.objectReference}"
                                                       display="type"/></div>
        </div>

        <c:choose>
            <c:when test="${smg:isTopLevelAgreement(pageContext, structuredActualDTO)}">

                <div class="row">
                    <label class="col-md-3"><fmt:message key="page.agreement.no"/></label>
                    <div class="col-md-9" id="agreementExternalReference"><c:out value="${structuredActualDTO.externalReference}"/></div>
                </div>

                <c:url value="/secure/productversion.do" var="productVersionUrl">
                    <c:param name="objectReference" value="${structuredActualDTO.productVersion.objectReference}"/>
                </c:url>

                <div class="row">
                    <label class="col-md-3"><fmt:message key="page.agreement.label.productversion"/></label>

                    <div class="col-md-9">
                        <a href="${productVersionUrl}" class="validate-props"><smg:objectReference display="id"
                                                                                                   value="${structuredActualDTO.productVersion.objectReference}"/></a>
                    </div>
                </div>
            </c:when>

            <c:otherwise>

                <c:url value="${contextPath}.do" var="topLevelAgreementUrl">
                    <c:param name="contextObjectReference" value="${contextObjectReference}"/>
                    <c:param name="method" value=".backToTopLevelAgreement"/>
                </c:url>

                <div class="row">
                    <label class="col-md-3"><fmt:message key="page.agreement.toplevelagreement"/></label>

                    <div class="col-md-9"><a id="topLevelAgreementExternalReference" href="${topLevelAgreementUrl}"> <c:out
                            value="${structuredActualDTO.topLevelAgreementExternalReference}"/>
                    </a></div>
                </div>

                <div class="row">
                    <label class="col-md-3"><fmt:message key="page.agreement.no"/></label>
                    <div class="col-md-9" id="agreementExternalReference"><c:out value="${structuredActualDTO.externalReference}"/></div>
                </div>

            </c:otherwise>
        </c:choose>

    </div>

    <%--Right Column--%>
    <div class="col-md-6">
        <div class="row">
            <label class="col-md-3"><fmt:message key="page.agreement.startdate"/> &rarr; <fmt:message
                    key="page.agreement.enddate"/></label>

            <div class="col-md-9">
                <smg:fmt value="${structuredActualDTO.startDate}"/> &rarr; <smg:fmt
                    value="${structuredActualDTO.endDate}"/>
            </div>
        </div>

        <div class="row">
            <label class="col-md-3"><fmt:message key="page.agreement.originalstartdate"/></label>

            <div class="col-md-9"><smg:fmt value="${structuredActualDTO.originalStartDate}"/>
            </div>
        </div>
        <div class="row">
            <label class="col-md-3"><fmt:message key="page.agreement.status"/></label>

            <div class="col-md-9"><smg:fmt value="${structuredActualDTO.state}"/></div>
        </div>
    </div>
</div>
