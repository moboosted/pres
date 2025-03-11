<%@ page import="com.silvermoongroup.common.enumeration.EnumerationType" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<smg:datepicker selector=".datefield" />

<html:form action="/secure/party/addParty.do">
    <html:hidden property="overRideDups"/>
    <html:hidden property="duplicatesFound"/>
    <input type="hidden" name="method" id="method" value=""/>
    <input id="validate" type="hidden" name="validate"/>
    <input id="embeddedMode" type="hidden" name="embeddedMode" value="${addPartyForm.embeddedMode}"/>
    <input id="roleKindToLink" type="hidden" name="roleKindToLink" value="${addPartyForm.roleKindToLink}"/>
    
    <div style="display:block;" id="findQuery">

        <table class="party-table" cellpadding="3">
            <td>
                <div class="form-inline">
                    <label class="control-label">
                        <fmt:message key="page.party.add.label.partytype"/>
                    </label>

                    <html:select property="selPartyType" onchange="showType()" styleId="selType"
                                 styleClass="form-control input-sm">
                        <smg:types subTypesOf="Party" immediateSubTypesOnly="${true}" valuesAsNames="${true}"/>
                    </html:select>
                </div>
            </td>
        </table>
        <hr>

        <%-- AddPartyAction person --%>
        <div style="display:block;" class="structure" id="addPartyPerson">
            <table class="party-table" cellpadding="3">
                <tr></tr>
                <tr>
                    <td><label class="control-label"><fmt:message key="page.party.label.title"/></label></td>
                    <td><html:select styleId="personTitle" property="selTitle" value="18"
                                     styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"
                                     onchange="checkTitleMarried(this.form);">
                        <smg:immutableEnumOptions immutableEnumerationClass="com.silvermoongroup.party.enumeration.PrefixTitles" />
                    </html:select></td>
                    <td><label class="control-label"><fmt:message key="page.party.generalparty.label.nametype"/></label></td>
                    <td><html:select styleId="personNameType" property="selNameType" value="Birth Name"
                                     styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"
                                     onchange="checkNameType(this.form);">
                        <smg:types subTypesOf="Person Name" valuesAsNames="${true}"/>
                    </html:select></td>
                </tr>
                <tr>
                    <td><label class="control-label"><fmt:message key="page.party.label.firstname" /></label></td>
                    <td>
                        <html:text property="txtFirstName" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>
                    </td>
                    <td><label class="control-label"><fmt:message key="page.party.label.middlename" /></label></td>
                    <td>
                        <html:text property="txtMiddleName" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>
                    </td>
                </tr>
                <tr>
                    <td><label class="control-label"><fmt:message key="page.party.label.surname" /></label></td>
                    <td>
                        <html:text property="txtSurname" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>
                    </td>
                </tr>
                <tr>
                    <td><label class="control-label"><fmt:message key="page.party.label.gender"/></label></td>
                    <td><html:select styleId="personGender" property="selGender"
                                     styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error">
                        <html:option value=""/>
                        <smg:immutableEnumOptions immutableEnumerationClass="com.silvermoongroup.common.enumeration.Gender" />
                    </html:select></td>
                </tr>
                <tr>
                    <td><label><fmt:message key="page.party.label.birthdate" /></label></td>
                    <td>
                        <html:text property="txtBirthDate" styleClass="form-control input-sm datefield" errorStyleClass="form-control input-sm has-error datefield"/>
                    </td>
                </tr>
                <tr></tr>
                <tr>
                    <td colspan="4" align="center">
                        <html:submit styleId="addPartyButton" property="method" styleClass="btn btn-primary btn-sm" onclick="setValidationMethod('validatePerson'); $('#method').val('.addParty'); overrideDuplicatesIfPresent(); return true;">
                            <fmt:message key="page.party.add.action.addparty"/>
                        </html:submit>
                        <html:submit styleId="searchPageCancelButton" styleClass="btn btn-link btn-sm" property="method" onclick="$('#method').val('.cancel');">
                            <fmt:message key="button.cancel"/>
                        </html:submit>		                
                    </td>
                </tr>
            </table>
        </div>

        <%-- AddPartyAction organisation --%>
        <div style="display:none;" class="structure" id="addOrg">
          <table class="party-table" cellpadding="3">
            <tr>
              <td><label><fmt:message key="page.party.label.organisationname" /></label></td>
              <td>
                <html:text property="txtOrgFullName" styleId="orgFullName" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>
              </td>
              <td><label><fmt:message key="page.party.label.organisationtype" /></label></td>
                <td><html:select styleId="orgTypes" property="txtOrgType" value="Company"
                                 styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error">
                    <smg:types subTypesOf="Organisation" valuesAsNames="${true}" addParentType="${true}"/>
                </html:select></td>
            </tr>
            <tr></tr>
            <tr>
              <td colspan="4" align="center">
                <html:submit styleId="addOrganisationButton" property="method" styleClass="btn btn-primary btn-sm" onclick="setValidationMethod('validateOrganisation'); $('#method').val('.addParty'); overrideDuplicatesIfPresent(); return true;">
                  <fmt:message key="page.party.add.action.addparty"/>
                </html:submit>
                </td>
                <td colspan="4" align="center">
                <html:submit styleId="searchPageCancelButton" property="method" onclick="$('#method').val('.cancel');" styleClass="btn btn-link btn-sm" >
                    <fmt:message key="button.cancel"/>
                </html:submit>
              </td>
            </tr>
          </table>
        </div>
    </div>
</html:form>
<script type="text/javascript"src="${pageContext.request.contextPath}/js/party/add.js"></script>
<script type="text/javascript"src="${pageContext.request.contextPath}/js/party/name.js"></script>
