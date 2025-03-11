
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<div style="display: none;" id="generalPerson">

    <%@include file="partyHeader.jsp" %>

    <table class="party-table">
        <tr>
            <td><label class="control-label"><fmt:message key="page.party.label.title"/></label></td>
            <td><html:select styleId="personTitle" property="selTitle"
                             styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"
                             onchange="checkTitleMarried(this.form);">
                <smg:enumOptions enumTypeId="<%=EnumerationType.PREFIX_TITLES.getValue()%>" showTerminated="false"/>
            </html:select></td>
            <td><label class="control-label"><fmt:message key="page.party.generalparty.label.nametype"/></label></td>
            <td><html:select styleId="personNameType" property="selNameType"
                             styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"
                             onchange="checkNameType(this.form);">
                <smg:types subTypesOf="Person Name" valuesAsNames="${true}"/>
            </html:select></td>
        </tr>
        <tr>
            <td><label class="control-label"><fmt:message key="page.party.label.firstname"/></label></td>
            <td><html:text property="txtFirstName" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>
            </td>
            <td><label class="control-label"><fmt:message key="page.party.label.middlename"/></label></td>
            <td><html:text property="txtMiddleName" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/></td>
        </tr>
        <tr>
            <td><label class="control-label"><fmt:message key="page.party.label.surname"/></label></td>
            <td><html:text property="txtSurname" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>
            </td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td><label class="control-label"><fmt:message key="page.party.generalparty.label.suffix"/></label></td>
            <td>
                <html:text styleId="personSuffix" property="selSuffix" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>
            </td>
            <td colspan="2"></td>
        </tr>
        <tr>
            <td><label class="control-label"><fmt:message key="page.party.generalparty.label.namestartdate"/></label></td>
            <td><html:text property="nameStartDate" styleClass="form-control input-sm datefield" errorStyleClass="form-control input-sm has-error datefield"/></td>
            <td><label class="control-label"><fmt:message key="page.party.generalparty.label.nameenddate"/></label></td>
            <td><html:text property="nameEndDate" styleClass="form-control input-sm datefield" errorStyleClass="form-control input-sm has-error datefield"/></td>
        </tr>
        <tr>
            <td><label class="control-label"><fmt:message key="page.party.label.initials"/></label></td>
            <td><html:text property="txtInitials" styleClass="form-control input-sm" disabled="true"/></td>
            <td align="left"><c:if test="${partyForm.showNameButton == true}">
                <label class="control-label">Default Name</label>
                <html:checkbox styleId="personDefaultName" property="defaultPartyName" styleClass="checkbox-inline"
                               onclick="namesTickBoxValue(this.form)"/>
            </c:if></td>
            <td><c:if test="${partyForm.showNameButton == true}">
                <html:submit styleId="addNameButton" property="method" styleClass="btn btn-primary btn-sm"
                             onclick="setValidationMethod('validatePersonName'); return true;">
                    <fmt:message key="page.party.generalparty.action.addpersonname"/>
                </html:submit>
                <html:submit styleId="updateNameButton" property="method"
                             styleClass="btn btn-primary btn-sm"
                             onfocus="checkNamesTickBoxEdit(this.form);"
                             onclick="setValidationMethod('validatePersonName'); return true;">
                    <fmt:message key="page.party.generalparty.action.updatepersonname"/>
                </html:submit>
            </c:if></td>
        </tr>
    </table>

    <hr/>

    <c:if test="${partyForm.showNameButton == true}">
        <div style="display: block;" id="addPersonName">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><fmt:message key="page.party.generalparty.label.nametype"/></th>
                    <th><fmt:message key="page.party.label.title"/></th>
                    <th><fmt:message key="page.party.label.firstname"/></th>
                    <th><fmt:message key="page.party.label.middlename"/></th>
                    <th><fmt:message key="page.party.generalparty.label.lastname"/></th>
                    <th><fmt:message key="page.party.generalparty.label.suffix"/></th>
                    <th><fmt:message key="page.party.generalparty.label.startdate"/></th>
                    <th><fmt:message key="page.party.generalparty.label.enddate"/></th>
                    <th><b></b></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${partyForm.partyNamesMap}" var="names">

                    <tr id="<c:out value="${names.key}" />">
                        <td><smg:fmtType value="${names.value.typeId}"/></td>
                        <td><smg:fmt value="${names.value.prefixTitles}"/></td>
                        <td><c:out value="${names.value.firstName}"/></td>
                        <td><c:out value="${names.value.middleNames}"/></td>
                        <td><c:out value="${names.value.lastName}"/></td>
                        <td><c:out value="${names.value.suffixTitles}"/></td>
                        <td><smg:fmt value="${names.value.effectivePeriod.start}"/></td>
                        <td><smg:fmt value="${names.value.effectivePeriod.end}"/></td>
                        <td><input id="personNameRadioSel" type="radio" name="personNameSelected"
                                   value="<c:out value="${names.key}" />"
                                   onclick="loadNames(this.form);"/></td>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <table class="party-table">
        <tr>
            <td><label class="control-label"><fmt:message key="page.party.label.birthdate"/></label></td>
            <td><html:text property="nameStartDate2" styleClass="form-control input-sm datefield"
                           errorStyleClass="form-control input-sm datefield has-error"/></td>
            <td><label class="control-label"><fmt:message key="page.party.generalparty.label.dateofdeath"/></label></td>
            <td><html:text property="txtDeathDateDisplay" disabled="true" styleClass="form-control input-sm"/></td>
        </tr>
        <tr>
            <td><label class="control-label"><fmt:message key="page.party.generalparty.label.gender"/></label></td>
            <td><html:select styleId="personGender" property="selGender"
                             styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error">
                <html:option value=""/>
              <smg:immutableEnumOptions immutableEnumerationClass="com.silvermoongroup.common.enumeration.Gender" />
            </html:select></td>
            <td><label class="control-label"><fmt:message key="page.party.generalparty.label.language"/></label></td>
            <td><html:select styleId="personLanguage" property="selLanguage"
                             styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error">
                <html:option value=""/>
              <smg:enumOptions enumTypeId="<%=EnumerationType.LANGUAGE.getValue()%>" showTerminated = "true"/>
            </html:select></td>
        </tr>
        <tr>
            <td><label class="control-label"><fmt:message key="page.party.generalparty.label.maritalstatus"/></label></td>
            <td><html:select styleId="personMaritalStatus" property="selMaritalStatus"
                             styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error">
                <html:option value=""/>
              <smg:enumOptions enumTypeId="<%=EnumerationType.MARITAL_STATUS.getValue()%>" showTerminated = "true"/>
            </html:select></td>
            <td><label class="control-label"><fmt:message key="page.party.generalparty.label.employmentstatus"/></label>
            </td>
            <td><html:select styleId="personEmploymentStatus" property="selEmploymentStatus"
                             styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error">
                <html:option value=""/>
              <smg:enumOptions enumTypeId="<%=EnumerationType.EMPLOYMENT_STATUS.getValue()%>" showTerminated = "true"/>
            </html:select></td>
        </tr>
        <tr>
            <td><label class="control-label"><fmt:message key="page.party.generalparty.label.grossincome"/></label></td>
            <td><html:text property="txtGrossIncome" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/></td>

            <td><label class="control-label"><fmt:message key="page.party.generalparty.label.educationlevel"/></label>
            </td>
            <td><html:select styleId="personEducationLevel" property="selEducationLevel"
                             styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error">
                <html:option value=""/>
              <smg:enumOptions enumTypeId="<%=EnumerationType.EDUCATION_LEVEL.getValue()%>" showTerminated = "true"/>
            </html:select></td>
        </tr>
        <tr>
            <td><label class="control-label"><fmt:message key="page.party.generalparty.label.disposableincome"/></label>
            </td>
            <td><html:text property="txtDisposableIncome"
                           styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/></td>
            <td><c:if test="${!empty partyForm.fullName and ! partyForm.isDelegating}">
                <html:submit styleId="updateGeneralPersonButton" property="method"
                             styleClass="btn btn-primary btn-sm">
                    <fmt:message key="page.party.generalparty.action.updategeneral"/>
                </html:submit>
            </c:if></td>
        </tr>
    </table>
</div>

<div class="text-center">
<c:choose>
    <c:when test="${partyForm.embeddedMode == false}">
        <c:if test="${empty partyForm.fullName}">
            <html:submit styleId="savePersonButton" property="method" styleClass="btn btn-primary btn-sm"
                         onclick="setValidationMethod('validatePerson'); return true;">
                <fmt:message key="page.party.action.saveperson"/>
            </html:submit>
        </c:if>
    </c:when>
    <c:otherwise>
		<html:submit styleId="linkToAnotherComponentButton" property="method" styleClass="btn btn-primary btn-sm">
		    <fmt:message key="page.party.action.link"/>
		</html:submit>
    </c:otherwise>
</c:choose>
	<html:submit styleId="generalCancelButton" property="method" styleClass="btn btn-link btn-sm">
	    <fmt:message key="button.cancel"/>
	</html:submit>
</div>
