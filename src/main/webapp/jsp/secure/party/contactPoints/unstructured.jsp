<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<!-- Unstructured Display -->
<table class="structure" style="width:90%;">
  <tr>
    <td colspan="4">
      <div id="obj${count}">
        <div style="display:block;" id="unstructureds">
            <table class="party-table" cellpadding="3px" style="width:100%;">
            <tr>
              <td class="lightgrey" style="color:#000066;width:9%; white-space:nowrap;">
                <b><fmt:message key="page.party.contactpoints.label.addressline1"/></b>
              </td>
              <td class="lightgrey" style="color:#000066;width:9%; white-space:nowrap;">
                <b><fmt:message key="page.party.contactpoints.label.addressline2"/></b>
              </td>
              <td class="lightgrey" style="color:#000066;width:9%; white-space:nowrap;">
                <b><fmt:message key="page.party.contactpoints.label.addressline3"/></b>
              </td>
              <td class="lightgrey" style="color:#000066;width:9%; white-space:nowrap;">
                <b><fmt:message key="page.party.contactpoints.label.addressline4"/></b>
              </td>

              <td class="lightgrey" style="color:#000066;width:13%"><b><fmt:message
                      key="page.party.contactpoints.label.txtUnstructuredAddressSubRegion"/></b></td>
              <td class="lightgrey" style="color:#000066;width:13%"><b><fmt:message
                      key="page.party.contactpoints.label.txtUnstructuredAddressCity"/></b></td>
              <td class="lightgrey" style="color:#000066;width:13%"><b><fmt:message
                      key="page.party.contactpoints.label.txtUnstructuredAddressRegion"/></b></td>
              <td class="lightgrey" style="color:#000066;width:13%"><b><fmt:message
                      key="page.party.contactpoints.label.selUnstructuredAddressCountryName"/></b></td>
              <td class="lightgrey" style="color:#000066;width:8%; white-space:nowrap;"><b><fmt:message
                      key="page.party.contactpoints.label.txtUnstructuredAddressPostalCode"/></b></td>
              <td class="lightgrey" style="color:#000066;width:8%; white-space:nowrap;"><b><fmt:message
                      key="page.party.contactpoints.label.txtStartDateUnstructuredAddress"/></b></td>
              <td class="lightgrey" style="color:#000066;width:8%; white-space:nowrap;"><b><fmt:message
                      key="page.party.contactpoints.label.txtEndDateUnstructuredAddress"/></b></td>
              <td class="lightgrey" style="width:1%"><b></b></td>
            </tr>
            <logic:iterate id="unstructureds" name="unstructuredContactPoints">
              <tr id="${unstructureds.value.id}">

                <td style="display:none;">
                  <smg:fmtType value="${contactPref.typeId}"/>
                </td>

                <td class="title"><c:out value="${unstructureds.value.addressLine1}"/></td>
                <td class="title"><c:out value="${unstructureds.value.addressLine2}"/></td>
                <td class="title"><c:out value="${unstructureds.value.addressLine3}"/></td>
                <td class="title"><c:out value="${unstructureds.value.addressLine4}"/></td>
                <td class="title"><c:out value="${unstructureds.value.subregion}"/></td>
                <td class="title"><c:out value="${unstructureds.value.city}"/></td>
                <td class="title"><c:out value="${unstructureds.value.region}"/></td>
                <td class="title"><smg:fmt value="${unstructureds.value.country}" /></td>
                <td class="title"><c:out value="${unstructureds.value.postalCode}"/></td>
                <td class="title" style="white-space:nowrap;"><smg:fmt value="${unstructureds.value.effectivePeriod.start}"/></td>
                <td class="title" style="white-space:nowrap;"><smg:fmt value="${unstructureds.value.effectivePeriod.end}"/></td>

                <c:if test="${! partyForm.isDelegating}">
                  <td class="contactSelection">
                    <input id="unstructContPointRadio" type="radio" name="contactSelected"
                           value="<c:out value="${unstructureds.value.id}"/>"
                           onclick="loadContactToEdit(this.form);"/>
                  </td>
                </c:if>
              </tr>
            </logic:iterate>
          </table>
        </div>
      </div>
    </td>
  </tr>
  <tr><td></td></tr>
  <tr><td></td></tr>
</table>
