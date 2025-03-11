
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<smg:datepicker selector=".datefield" />

<html:form action="/secure/party/findParty.do" styleId="findPartyForm" styleClass="form-horizontal">
    <input type="hidden" name="method" id="method" value=""/>
    <input id="validate" type="hidden" name="validate"/>
    <input id ="roleKindToLink" type="hidden" name="roleKindToLink" value="${findPartyForm.roleKindToLink}"/>
    <input id="embeddedMode" type="hidden" name="embeddedMode" value="${findPartyForm.embeddedMode}"/>
    <input type="hidden" name="method" id="method" value=""/>

    <div id="findQuery">
        <table class="party-table" cellpadding="3">
            <td>
                <div class="form-inline">
                    <label class="control-label">
                        <fmt:message key="page.party.find.label.findparty"/>
                    </label>

                    <html:select property="selType" styleId="selSearch" styleClass="form-control input-sm">
                        <smg:types subTypesOf="Party" immediateSubTypesOnly="${true}" valuesAsNames="${true}"/>
                    </html:select>
                    <c:if test="${! findPartyForm.isDelegating}">
                        <html:submit styleId="addPartyButton" property="method" onclick="$('#method').val('.addParty')" styleClass="btn btn-primary btn-sm pull-right">
                            <fmt:message key="page.party.add.action.addparty"/>
                        </html:submit>
                    </c:if>
                </div>
            </td>
        </table>
        <hr>

        <%-- Find person --%>
        <div class="row form-container" id="findPartyPerson">

            <%--Left column--%>
            <div class="col-md-6">
                <div class="row">
                    <div class="form-group">
                        <label for="txtSearchSurname" class="col-md-2 control-label"><fmt:message
                                key="page.party.label.surname"/></label>

                        <div class="col-md-8">
                            <html:text property="txtSearchSurname" styleId="txtSearchSurname"
                                       styleClass="form-control input-sm"
                                       errorStyleClass="form-control input-sm has-error"/>
                            <span class="party-required">* Required</span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="txtSearchFirstName" class="col-md-2 control-label"><fmt:message
                                key="page.party.label.firstname"/></label>

                        <div class="col-md-8">
                            <html:text property="txtSearchFirstName" styleId="txtSearchFirstName"
                                       styleClass="form-control input-sm"
                                       errorStyleClass="form-control input-sm has-error"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="txtSearchMiddleName" class="col-md-2 control-label"><fmt:message
                                key="page.party.label.middlename"/></label>

                        <div class="col-md-8">
                            <html:text property="txtSearchMiddleName" styleId="txtSearchMiddleName"
                                       styleClass="form-control input-sm"
                                       errorStyleClass="form-control input-sm has-error"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="txtSearchBirthDate" class="col-md-2 control-label"><fmt:message
                                key="page.party.find.label.dateofbirth"/></label>

                        <div class="col-md-3">
                            <html:text property="txtSearchBirthDate" styleId="txtSearchBirthDate"
                                       styleClass="form-control input-sm datefield" errorStyleClass="form-control input-sm has-error datefield"/>
                        </div>
                    </div>
                </div>
            </div>

            <%--Right column--%>
            <div class="col-md-6">
                <div class="row">
                    <div class="col-md-2">
                        <div class="row">
                            <h5>
                                <strong><fmt:message key="page.party.contactpoints.label.physicaladdress"/>:</strong>
                            </h5>
                        </div>
                    </div>
                    <div class="col-md-9">
                        <div class="row">
                            <div class="form-group">
                                <label for="txtSearchStreet" class="col-md-2 control-label"><fmt:message
                                        key="page.party.label.street"/></label>

                                <div class="col-md-9">
                                    <html:text property="txtSearchStreet" styleId="txtSearchStreet"
                                               styleClass="form-control input-sm"
                                               errorStyleClass="form-control input-sm has-error"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="txtSearchHouseNumber" class="col-md-2 control-label"><fmt:message
                                        key="page.party.label.housenumber"/></label>

                                <div class="col-md-2">
                                    <html:text property="txtSearchHouseNumber" styleId="txtSearchHouseNumber"
                                               styleClass="form-control input-sm"
                                               errorStyleClass="form-control input-sm has-error"/>
                                </div>

                                <div class="col-md-4">
                                    <span class="party-required">* Fill in street name first</span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                        <div class="form-group">
                            <label for="txtSearchCity" class="col-md-2 control-label"><fmt:message
                                    key="page.party.label.city"/></label>

                            <div class="col-md-9">
                                <html:text property="txtSearchCity" styleId="txtSearchCity"
                                           styleClass="form-control input-sm"
                                           errorStyleClass="form-control input-sm has-error"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="txtSearchPostalCode" class="col-md-2 control-label"><fmt:message
                                    key="page.party.label.postalcode"/></label>

                            <div class="col-md-2">
                                <html:text property="txtSearchPostalCode" styleId="txtSearchPostalCode"
                                           styleClass="form-control input-sm"
                                           errorStyleClass="form-control input-sm has-error"/>
                            </div>
                        </div>
                    </div>
                    </div>
                </div>

            </div>
        </div>

        <%-- Find organisation --%>
        <div style="display:none;" class="structure" id="findPartyOrg">
            <table class="party-table" cellpadding="3">
                <tr>
                    <td><label><fmt:message key="page.party.label.organisationname"/></label></td>
                    <td>
                        <html:text property="txtSearchOrgFullName" styleId="txtSearchOrgFullName" styleClass="form-control input-sm"
                                   errorStyleClass="form-control input-sm has-error"/>
                    </td>
                    <td><label><fmt:message key="page.party.label.organisationtype"/></label></td>
                    <td>
                        <html:select styleId="orgTypes" property="txtSearchOrgType" styleClass="form-control input-sm"
                                     errorStyleClass="form-control input-sm has-error">
                            <option value="">-Select-</option>
                            <smg:types subTypesOf="Organisation" valuesAsNames="${true}"/>
                        </html:select>
                    </td>
                </tr>
                <tr></tr>
                <tr>
                    <td colspan="4" align="center">
                        <html:button styleId="resetOrganisationSearchCriteria" property="method"
                                     styleClass="btn btn-primary btn-sm"
                                     onclick="resetPartySearchCriteria()">
                            <fmt:message key="button.reset"/>
                        </html:button>
                        <html:submit styleId="searchPartyButtonOrg" property="method"
                                     styleClass="btn btn-primary btn-sm"
                                     onclick="setValidationMethod('validateSearchOrganisation');$('#method').val('.searchOrganisation');return true;">
                            <fmt:message key="page.party.find.action.searchOrganisation"/>
                        </html:submit>
                        <% if (!CallBackUtility.isCallBackEmpty(request, response)) {%>
                        <html:submit styleId="searchCancelButton" property="method" styleClass="btn btn-link btn-sm"
                                     onclick="$('#method').val('.cancel');return true;">
                            <fmt:message key="button.cancel"/>
                        </html:submit>
                        <% } %>
                    </td>
                </tr>
            </table>
        </div>

        <%-- Search button --%>
        <div class="row spacer">
            <div class="col-md-12 text-center">
                <html:button styleId="resetPersonSearchCriteria" property="method"
                             styleClass="btn btn-primary btn-sm"
                             onclick="resetPartySearchCriteria()">
                    <fmt:message key="button.reset"/>
                </html:button>
                <html:submit styleId="searchPartyButtonPerson" property="method"
                             styleClass="btn btn-primary btn-sm"
                             onclick="setValidationMethod('validateSearchPerson');$('#method').val('.searchPerson');return true; ">
                    <fmt:message key="page.party.find.action.searchperson"/>
                </html:submit>
                <% if (!CallBackUtility.isCallBackEmpty(request, response)) {%>
                <html:submit styleId="searchCancelButton" property="method" styleClass="btn btn-link btn-sm"
                             onclick="$('#method').val('.cancel');return true;">
                    <fmt:message key="button.cancel"/>
                </html:submit>
                <% } %>
            </div>
        </div>

        <%-- Search results --%>
        <div style="display:block;" class="structure" id="searchResults">
            <div title="Search Results" id="search">
                <%@include file="../searchResults/searchResults.jsp"%>
            </div>
        </div>

    </div>
</html:form>

<script type="text/javascript"src="${pageContext.request.contextPath}/js/party/find.js"></script>

