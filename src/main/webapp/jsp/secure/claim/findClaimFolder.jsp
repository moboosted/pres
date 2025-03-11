<%@ page import="com.silvermoongroup.common.enumeration.EnumerationType" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<smg:datepicker selector=".datefield"/>

<jsp:useBean id="findClaimFolderForm" scope="request"
             type="com.silvermoongroup.fsa.web.claimmanagement.form.FindClaimFolderForm"/>

<label class="groupHeading" for="findClaimFolderTable"><fmt:message key="page.findclaimfolder.title"/></label>

<script type="text/javascript">
    $(function () {

        $('#dateOptionAny').change(function () {
            $('#effectiveOn').val("");
            $('#effectiveFrom').val("");
            $('#effectiveTo').val("");
        });

        $('#dateOptionOn').change(function () {
            $('#effectiveFrom').val("");
            $('#effectiveTo').val("");
        });

        $('#dateOptionBetween').change(function () {
            $('#effectiveOn').val("");
        });

        $('#effectiveTodayLink').bind('click', function (e) {
            e.preventDefault();
            $('#effectiveOn').val("${findClaimFolderForm.today}");
            $('#dateOptionOn').prop('checked', true).change();
        });

        $('#effectiveYesterdayLink').bind('click', function (e) {
            e.preventDefault();
            $('#effectiveOn').val("${findClaimFolderForm.yesterday}");
            $('#dateOptionOn').prop('checked', true).change();
        });

        $('#effectiveCurrentMonthLink').bind('click', function (e) {
            e.preventDefault();
            $('#effectiveFrom').val("${findClaimFolderForm.firstOfCurrentMonth}");
            $('#effectiveTo').val("${findClaimFolderForm.lastOfCurrentMonth}");
            $('#dateOptionBetween').prop('checked', true).change();
        });

        $('#effectivePreviousMonthLink').bind('click', function (e) {
            e.preventDefault();
            $('#effectiveFrom').val("${findClaimFolderForm.firstOfPreviousMonth}");
            $('#effectiveTo').val("${findClaimFolderForm.lastOfPreviousMonth}");
            $('#dateOptionBetween').prop('checked', true).change();
        });

        $('#effectiveOn').focus(function () {
            $('#dateOptionOn').prop('checked', true).change();
        });
        $('#effectiveFrom').focus(function () {
            $('#dateOptionBetween').prop('checked', true).change();
        });
        $('#effectiveTo').focus(function () {
            $('#dateOptionBetween').prop('checked', true).change();
        });
    });
</script>

<html:form action="/secure/claim/claimfolder/find.do" styleId="findForm" styleClass="form-horizontal">

    <div class="row form-container">

        <%--Left column--%>
        <div class="col-md-6">

            <div class="row">
                <div class="form-group">
                    <label for="claimFolderId" class="col-md-3 control-label"><fmt:message key="claim.claimfolder"/> <fmt:message key="label.id"/></label>
                    <div class="col-md-4">
                        <html:text styleId="claimFolderId" property="claimFolderId" styleClass="form-control input-sm"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="externalReference" class="col-md-3 control-label"><fmt:message
                            key="label.externalreference"/>
                        <fmt:message key="label.id"/></label>

                    <div class="col-md-4">
                        <html:text property="externalReference" styleId="externalReference"
                                   styleClass="form-control input-sm"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="claimFolderStates" class="col-md-3 control-label"><fmt:message
                            key="label.status"/></label>

                    <div class="col-md-6">
                        <html:select property="claimFolderStates" multiple="true" styleId="claimFolderStates"
                                     styleClass="form-control input-sm">
                            <smg:enumOptions enumTypeId="<%=EnumerationType.CLAIM_FOLDER_STATE.getValue()%>" showTerminated = "true"/>
                        </html:select>
                    </div>
                </div>
            </div>

        </div>


                <%--Right column--%>
        <div class="col-md-6 form-column">
            <div class="row">
                <div class="form-group">
                    <label for="topLevelClaimFoldersOnly" class="col-md-3 control-label"><fmt:message
                            key="page.findclaimfolder.label.toplevelclaimfoldersonly"/></label>

                    <div class="col-md-8">
                        <html:checkbox property="topLevelClaimFoldersOnly" styleId="topLevelClaimFoldersOnly" styleClass="checkbox"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="dateOptionAny" class="col-md-3 control-label"><fmt:message
                            key="label.effective"/> </label>

                    <div class="col-md-9">
                        <div class="form-inline">
                            <html:radio property="dateOption" value="any" styleId="dateOptionAny"/>
                            <fmt:message key="label.anydate"/>
                        </div>
                        <div class="form-inline spacer-sm">
                            <html:radio property="dateOption" value="on" styleId="dateOptionOn"/> On
                            <html:text property="effectiveOn" styleClass="form-control input-sm datefield"
                                       errorStyleClass="form-control input-sm datefield has-error" styleId="effectiveOn"/>
                            &nbsp;&nbsp;[ <a href="#" id="effectiveYesterdayLink"><fmt:message
                                key="label.yesterday"/></a> ]
                            &nbsp;[ <a href="#" id="effectiveTodayLink"><fmt:message key="label.today"/></a> ]
                        </div>
                        <div class="form-inline spacer-sm">
                            <html:radio property="dateOption" value="between" styleId="dateOptionBetween"/>
                            <fmt:message key="label.between"/>
                            <html:text property="effectiveFrom" styleClass="form-control input-sm datefield"
                                       errorStyleClass="form-control input-sm datefield has-error" styleId="effectiveFrom"/>
                            <fmt:message key="label.and"/>
                            <html:text property="effectiveTo" styleClass="form-control input-sm datefield"
                                       errorStyleClass="form-control input-sm datefield has-error" styleId="effectiveTo"/>
                            &nbsp;&nbsp;[ <a href="#" id="effectivePreviousMonthLink"><fmt:message
                                key="label.lastmonth"/></a> ]
                            &nbsp;[ <a href="#" id="effectiveCurrentMonthLink"><fmt:message
                                key="label.currentmonth"/></a> ]
                        </div>
                    </div>
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

<c:if test="${findClaimFolderForm.results != null}">
    <div class="table-responsive spacer">

        <c:url value="/secure/claim/claimnavigator.do" var="claimNavigatorUrl"/>
        <display:table name="${findClaimFolderForm.results}"
                       id="findClaimFolderTable"
                       decorator="com.silvermoongroup.fsa.web.claimmanagement.ClaimFolderTableDecorator"
                       pagesize="15"
                       requestURI="/secure/claim/claimfolder/find.do">

            <display:column property="id" titleKey="label.id" href="${claimNavigatorUrl}"
                            paramId="nodeObjectReference" paramProperty="objectReference"/>
            <display:column property="type" titleKey="label.type"
                            href="${claimNavigatorUrl}" paramId="nodeObjectReference" paramProperty="objectReference"/>
            <display:column property="externalReference" titleKey="label.externalreference" href="${claimNavigatorUrl}" paramId="nodeObjectReference"
                            paramProperty="objectReference"/>
            <display:column property="description" titleKey="label.description" maxLength="75" />
            <display:column property="startDate" titleKey="label.startdate" />
            <display:column property="endDate" titleKey="label.enddate"/>

        </display:table>
    </div>
</c:if>
