<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<table class="party-table" cellpadding="3">
    <div style="display: none;" id="partyTypeSelection" class="form-inline">
        <label class="control-label" for="partyType"><fmt:message
                key="page.party.generalparty.label.partytype"/></label>
        <html:select styleId="partyType" property="selPartyType" disabled="false"
                     styleClass="form-control input-sm"
                     onchange="var actn='${pageContext.request.contextPath}/secure/partyAction.do?method=.refresh';document.forms[0].action=actn;document.forms[0].submit();">
            <smg:types subTypesOf="Party" valuesAsNames="${true}"/>
        </html:select>
    </div>
</table>