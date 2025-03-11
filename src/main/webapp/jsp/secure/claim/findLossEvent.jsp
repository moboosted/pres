<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<smg:datepicker selector=".datefield"/>

<jsp:useBean id="findLossEventForm" scope="request"
             type="com.silvermoongroup.fsa.web.claimmanagement.form.FindLossEventForm"/>

<label class="groupHeading" for="lossEventTable"><fmt:message key="page.findlossevent.title"/></label>

<html:form action="/secure/claim/lossevent/find.do" styleId="findForm" styleClass="form-horizontal">
    <div class="row form-container">

        <div class="row">
            <div class="form-group">
                <label for="effectiveDate" class="col-md-2 control-label"><smg:fmt value="claim.findlossevent.effectivedate"/></label>
                <div class="col-md-2">
                    <html:text property="effectiveDate" styleId="effectiveDate" styleClass="form-control input-sm datefield" errorStyleClass="form-control input-sm datefield has-error"/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="lossEventDate" class="col-md-2 control-label"><smg:fmt value="claim.findlossevent.losseventtime"/></label>
                <div class="col-md-2">
                    <html:text property="lossEventDate" styleId="lossEventDate" styleClass="form-control input-sm datefield" errorStyleClass="form-control input-sm datefield has-error"/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="externalReference" class="col-md-2 control-label"><smg:fmt value="claim.findlossevent.losseventexternalreference"/></label>
                <div class="col-md-2">
                    <html:text property="externalReference" styleId="externalReference" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>
                </div>
            </div>
        </div>

    </div>

    <div class="row spacer">
        <div class="col-md-12 text-center">
            <input type="hidden" name="method" value=".find"/>
            <html:submit accesskey="s" titleKey="label.submitwithalts" styleId="findButton" styleClass="btn btn-primary btn-sm">
                <fmt:message key="button.search"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>

</html:form>

<c:if test="${findLossEventForm.results != null}">
    <div class="table-responsive spacer">

        <c:url value="/secure/claim/lossevent/edit.do?method=.edit&externalReference" var="editLossEventUrl"/>

        <display:table name="${findLossEventForm.results}"
                       id="lossEventTable"
                       decorator="com.silvermoongroup.fsa.web.claimmanagement.LossEventTableDecorator"
                       pagesize="15"
                       requestURI="/secure/claim/lossevent/find.do">

            <display:column property="id" titleKey="label.id"
                            paramId="externalReference" paramProperty="externalReference" href="${editLossEventUrl}"/>
            <display:column property="type" titleKey="claim.lossevent.type"
                            paramId="externalReference" paramProperty="externalReference" href="${editLossEventUrl}"/>
            <display:column property="externalReference" titleKey="claim.lossevent.externalreference"
                            paramId="externalReference" paramProperty="externalReference" href="${editLossEventUrl}"/>
            <display:column property="name" titleKey="claim.lossevent.name" />
            <display:column property="description" titleKey="claim.lossevent.description" />
            <display:column property="eventTime" titleKey="claim.lossevent.losseventtime" />
            <display:column property="startDate" titleKey="claim.lossevent.startdate" />
            <display:column property="endDate" titleKey="claim.lossevent.enddate"/>
            <%--<display:column class="m">
                <html:form action="/secure/claim/lossevent/find.do">
                    <html:hidden property="lossEventExternalReference" value="${lossEvent.externalReference}"/>
                    <input type="hidden" name="method" value=".createClaimFolder"/>
                    <html:submit styleClass="btn btn-default btn-sm">
                        <fmt:message key="page.findlossevent.action.createclaimfolder"/>
                    </html:submit>
                </html:form>
            </display:column>--%>

        </display:table>
    </div>
</c:if>


