<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<smg:datepicker selector=".datefield"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/properties.js"></script>

<jsp:useBean id="roleViewForm" scope="request" type="com.silvermoongroup.fsa.web.agreementandrequest.form.RolePropertiesForm"/>

<label class="groupHeading">
    <fmt:message key="page.roleview.properties">
        <c:choose>
            <c:when test="${!empty roleViewForm.structuredActualDTO.rolePlayerDefaultName}">
                <fmt:param value="${roleViewForm.structuredActualDTO.rolePlayerDefaultName}"/>
            </c:when>
            <c:otherwise>
                <fmt:param value="${roleViewForm.structuredActualDTO.kind.name}"/>
            </c:otherwise>
        </c:choose>
    </fmt:message>
</label>

<html:form action="/secure/agreement/role/properties.do" styleId="propertiesForm" styleClass="form-horizontal spacer">
    <input type="hidden" name="method" value=".validateAndBindProperties"/>
    <html:hidden property="contextPath" value="${roleViewForm.contextPath}"/>
    <html:hidden property="tlaRelativePath" value="${roleViewForm.tlaRelativePath}"/>

    <div class="row">
        <%--Left Column--%>
        <div class="col-md-6">
            <c:forEach var="entry" items="${roleViewForm.structuredActualDTO.properties}" varStatus="loopStatus">
                <c:if test="${loopStatus.index%2 == 0}">

                    <div class="row form-inline spacer-sm">
                        <label class="col-md-4" title="<smg:propertyKindHint property="${entry}"/>">
                            <smg:propertyNameTag property="${entry}"
                                                 structuredActual="${roleViewForm.structuredActualDTO}"
                                                 inputValues="${roleViewForm.allInputProperties}"
                                                 errorMessages="${roleViewForm.inputPropertiesValidationMessages}"/>
                        </label>

                        <div class="col-md-8"><smg:propertyValueTag
                                property="${entry}"
                                structuredActual="${roleViewForm.structuredActualDTO}"
                                inputValues="${roleViewForm.allInputProperties}"
                                errorMessages="${roleViewForm.inputPropertiesValidationMessages}"/>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>

                <%--Right Column--%>
            <div class="col-md-6">
                <c:forEach var="entry" items="${roleViewForm.structuredActualDTO.properties}" varStatus="loopStatus">
                    <c:if test="${loopStatus.index%2 == 1}">

                        <div class="row form-inline spacer-sm">
                            <label class="col-md-4" title="<smg:propertyKindHint property="${entry}"/>">
                                <smg:propertyNameTag property="${entry}"
                                                     structuredActual="${roleViewForm.structuredActualDTO}"
                                                     inputValues="${roleViewForm.allInputProperties}"
                                                     errorMessages="${roleViewForm.inputPropertiesValidationMessages}"/>
                            </label>

                            <div class="col-md-8"><smg:propertyValueTag
                                    property="${entry}"
                                    structuredActual="${roleViewForm.structuredActualDTO}"
                                    inputValues="${roleViewForm.allInputProperties}"
                                    errorMessages="${roleViewForm.inputPropertiesValidationMessages}"/>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
    </div>


    <div class="row">
            <%--Left Column--%>
        <div class="col-md-6">
            <c:forEach var="entry" items="${roleViewForm.structuredActualDTO.derivedProperties}" varStatus="loopStatus">
                <c:if test="${loopStatus.index%2 == 0}">
                    <label class="groupHeading spacer">
                        <fmt:message key="page.roleview.derivedproperties">
                            <c:choose>
                                <c:when test="${!empty roleViewForm.structuredActualDTO.rolePlayerDefaultName}">
                                    <fmt:param value="${roleViewForm.structuredActualDTO.rolePlayerDefaultName}"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:param value="${roleViewForm.structuredActualDTO.kind.name}"/>
                                </c:otherwise>
                            </c:choose>
                        </fmt:message>
                    </label>

                    <div class="row form-inline spacer-sm">
                        <label class="col-md-4" title="<smg:propertyKindHint property="${entry}"/>">
                            <smg:propertyNameTag property="${entry}" structuredActual="${roleViewForm.structuredActualDTO}"
                                                 inputValues="${roleViewForm.allInputProperties}" errorMessages="${roleViewForm.inputPropertiesValidationMessages}"/>
                        </label>

                        <div class="col-md-8">
                            <smg:propertyValueTag property="${entry}" structuredActual="${roleViewForm.structuredActualDTO}"
                                inputValues="${roleViewForm.allInputProperties}" errorMessages="${roleViewForm.inputPropertiesValidationMessages}"/>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>

            <%--Right Column--%>
        <div class="col-md-6">
            <c:forEach var="entry" items="${roleViewForm.structuredActualDTO.derivedProperties}" varStatus="loopStatus">
                <c:if test="${loopStatus.index%2 == 1}">

                    <div class="row form-inline spacer-sm">
                        <label class="col-md-4" title="<smg:propertyKindHint property="${entry}"/>">
                            <smg:propertyNameTag property="${entry}" structuredActual="${roleViewForm.structuredActualDTO}"
                                                 inputValues="${roleViewForm.allInputProperties}" errorMessages="${roleViewForm.inputPropertiesValidationMessages}"/>
                        </label>

                        <div class="col-md-8">
                            <smg:propertyValueTag property="${entry}" structuredActual="${roleViewForm.structuredActualDTO}"
                                inputValues="${roleViewForm.allInputProperties}" errorMessages="${roleViewForm.inputPropertiesValidationMessages}"/>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>

</html:form>

<html:form action="/secure/agreement/role/properties.do">

    <input type="hidden" name="method" value=".back"/>
    <html:hidden property="contextObjectReference" value="${roleViewForm.contextObjectReference}"/>
    <html:hidden property="contextPath" value="${roleViewForm.contextPath}"/>
    <html:hidden property="path" value="${roleViewForm.path}"/>
    <html:hidden property="tlaRelativePath" value="${roleViewForm.tlaRelativePath}"/>

    <div class="row spacer">
        <div class="col-md-12 text-center">
            <c:if test="${!roleViewForm.structuredActualDTO.readOnly}">
                <html:submit property="method" styleClass="btn btn-primary btn-sm"
                             onclick="return doMainFormSubmitWithInitialForm(this.form, 'Submit');">
                    <fmt:message key="button.submit"/>
                </html:submit>
            </c:if>
            <html:submit styleId="generalBackButton" property="method" styleClass="btn btn-default btn-sm">
                <fmt:message key="button.back"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>

</html:form>
