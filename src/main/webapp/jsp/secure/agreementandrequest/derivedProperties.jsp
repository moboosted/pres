<c:if test="${!empty structuredActualDTO.derivedProperties}">

    <div class="row">
            <%--Left Column--%>
        <div class="col-md-6">
            <c:forEach var="entry" items="${structuredActualDTO.derivedProperties}" varStatus="loopStatus">
                <c:if test="${loopStatus.index%2 == 0}">
                    <div class="row form-inline spacer-sm">
                        <label class="col-md-4" title="<smg:propertyKindHint property="${entry}"/>" rel="tooltip">
                            <smg:propertyNameTag property="${entry}" structuredActual="${structuredActualDTO}"
                                                 inputValues="${form.allInputProperties}" errorMessages="${form.inputPropertiesValidationMessages}"/>
                        </label>

                        <div class="col-md-8">
                            <smg:propertyValueTag property="${entry}" structuredActual="${structuredActualDTO}"
                                inputValues="${form.allInputProperties}" errorMessages="${form.inputPropertiesValidationMessages}"/>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>

            <%--Right Column--%>
        <div class="col-md-6">
            <c:forEach var="entry" items="${structuredActualDTO.derivedProperties}" varStatus="loopStatus">
                <c:if test="${loopStatus.index%2 == 1}">

                    <div class="row form-inline spacer-sm">
                        <label class="col-md-4" title="<smg:propertyKindHint property="${entry}"/>" rel="tooltip">
                            <smg:propertyNameTag property="${entry}" structuredActual="${structuredActualDTO}"
                                                 inputValues="${form.allInputProperties}" errorMessages="${form.inputPropertiesValidationMessages}"/>
                        </label>

                        <div class="col-md-8">
                            <smg:propertyValueTag property="${entry}" structuredActual="${structuredActualDTO}"
                                inputValues="${form.allInputProperties}" errorMessages="${form.inputPropertiesValidationMessages}"/>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
</c:if>

<c:if test="${empty structuredActualDTO.derivedProperties}">
    <div class="empty">
        <smg:fmt value="label.nopropertiesdefined"/>
    </div>
</c:if>