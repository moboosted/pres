<%@page import="com.silvermoongroup.fsa.web.common.callback.CallBackUtility"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

    <div style="display: none;" id="generalOrganisation">

        <%@include file="partyHeader.jsp" %>

        <table class="party-table" cellpadding="3">
            <tr>
                <td class="mandatory" style="width: 10%;"><label><fmt:message key="page.party.label.organisationname" /></label></td>
                <td class="input"><html:text property="txtFullName" styleId="orgFullName" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error" /></td>
            </tr>
        </table>
      <table class="party-table">
        <tr>
          <td colspan="4" align="center"><c:choose>
              <c:when test="${partyForm.embeddedMode == false}">
                  <c:if test="${! partyForm.isDelegating}">
                      <c:if test="${empty partyForm.partyObjectRef}">
                          <html:submit styleId="saveOrgButton" property="method" styleClass="btn btn-primary btn-sm" onclick="setValidationMethod('validateOrganisation'); overrideDuplicatesIfPresent();">
                              <fmt:message key="page.party.action.saveorganisation" />
                          </html:submit>
                      </c:if>
                      <c:if test="${!empty partyForm.partyObjectRef}">
                          <html:submit styleId="updateOrgButton" property="method" styleClass="btn btn-primary btn-sm"  onclick="setValidationMethod('validateOrganisation'); return true;">
                              <fmt:message key="page.party.generalparty.action.updateorganisation" />
                          </html:submit>
                      </c:if>
                  </c:if>
              </c:when>
              <c:otherwise>
                  <c:if test="${! partyForm.isDelegating}">
                      <html:submit styleId="updateOrgButton" property="method" styleClass="btn btn-primary btn-sm"  onclick="setValidationMethod('validateOrganisation'); return true;">
                        <fmt:message key="page.party.generalparty.action.updateorganisation" />
                      </html:submit>
                  </c:if>
                  <html:submit styleId="linkToAnotherComponentButton" styleClass="btn btn-primary btn-sm"  property="method">
                    <fmt:message key="page.party.action.link" />
                  </html:submit>
              </c:otherwise>
            </c:choose>
            <% if (!CallBackUtility.isCallBackEmpty(request, response)) {%> 
                <html:submit styleId="generalCancelButton" property="method" styleClass="btn btn-primary btn-sm">
                    <fmt:message key="button.cancel"/>
                </html:submit>
            <% } %>
          </td>
        </tr>
      </table>
    </div>