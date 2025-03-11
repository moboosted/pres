<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>


<!-- Contact Preference Display -->
<table class="party-table">
  <tr>
    <td colspan="4">
      <div style="display: block;">
        <table class="party-table" style="width:60%;">
          <tr>
            <td style="display: none;">${contactPref.id}</td>
            <td class="contactHeading"><b>
              <smg:fmtType value="${contactPref.typeId}" /></b></td>
            <td class="contactHeadingSub">&nbsp;
            <logic:present name="contactPref" property="preferredLanguage">
              <b><fmt:message key="page.party.contactpoints.label.preflanguage" />: </b><smg:fmt value="${contactPref.preferredLanguage}" />
            </logic:present></td>

            <td class="contactHeadingSub"><b><fmt:message
                  key="page.party.contactpoints.label.startdate" /></b> <smg:fmt value="${contactPref.effectivePeriod.start}" /></td>

            <td class="contactHeadingSub"><b><fmt:message key="page.party.contactpoints.label.enddate" /></b>
              <smg:fmt value="${contactPref.effectivePeriod.end}" /></td>

            <c:if test="${! partyForm.isDelegating}">
              <td style="background-color: #6DA1CF; text-align: center;"><input type="radio"
                name="preferenceSelected" value="${contactPref.id}"
                onclick="loadPreferenceToEditAndSelect(this.form);" /></td>
            </c:if>
          </tr>
        </table>
      </div>
    </td>
  </tr>
</table>
