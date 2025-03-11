<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<!-- Postal Display -->
<table class="party-table" style="width:90%;">
  <tr>
    <td colspan="4">
      <div id="obj${count}">
        <div style="display:block;" id="postals">
            <table class="party-table" cellpadding="3px" style="width:100%;">
            <tr>
              <td class="lightgrey" style="color:#000066;width:7%; white-space:nowrap;">
                <b><fmt:message key="page.party.contactpoints.label.pobox"/></b>
              </td>
              <td class="lightgrey" style="color:#000066;width:7%"><b><fmt:message
                      key="page.party.contactpoints.label.suite"/></b></td>
              <td class="lightgrey" style="color:#000066;width:15%"><b><fmt:message
                      key="page.party.contactpoints.label.suburb"/></b></td>
              <td class="lightgrey" style="color:#000066;width:15%"><b><fmt:message
                      key="page.party.contactpoints.label.city"/></b></td>
              <td class="lightgrey" style="color:#000066;width:15%"><b><fmt:message
                      key="page.party.contactpoints.label.region"/></b></td>
              <td class="lightgrey" style="color:#000066;width:15%"><b><fmt:message
                      key="page.party.contactpoints.label.country"/></b></td>
              <td class="lightgrey" style="color:#000066;width:10%; white-space:nowrap;"><b><fmt:message
                      key="page.party.contactpoints.label.postalcode"/></b></td>
              <td class="lightgrey" style="color:#000066;width:10%; white-space:nowrap;"><b><fmt:message
                      key="page.party.contactpoints.label.startdate"/></b></td>
              <td class="lightgrey" style="color:#000066;width:10%; white-space:nowrap;"><b><fmt:message
                      key="page.party.contactpoints.label.enddate"/></b></td>
              <td class="lightgrey" style="width:2%"><b></b></td>
            </tr>
            <logic:iterate id="postals" name="postalContactPoints">
              <tr id="${postals.value.id}">

                <td style="display:none;">
                  <smg:fmtType value="${contactPref.typeId}"/>
                </td>

                <td class="title"><c:out value="${postals.value.boxNumber}"/></td>
                <td class="title"><c:out value="${postals.value.postnetSuite}"/></td>
                <td class="title"><c:out value="${postals.value.subregion}"/></td>
                <td class="title"><c:out value="${postals.value.city}"/></td>
                <td class="title"><c:out value="${postals.value.region}"/></td>
                <td class="title"><smg:fmt value="${postals.value.country}" /></td>
                <td class="title"><c:out value="${postals.value.postalCode}"/></td>
                <td class="title" style="white-space:nowrap;"><smg:fmt value="${postals.value.effectivePeriod.start}"/></td>
                <td class="title" style="white-space:nowrap;"><smg:fmt value="${postals.value.effectivePeriod.end}"/></td>

                <c:if test="${! partyForm.isDelegating}">
                  <td class="contactSelection">
                    <input id="postContPointRadio" type="radio" name="contactSelected"
                           value="<c:out value="${postals.value.id}"/>"
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
