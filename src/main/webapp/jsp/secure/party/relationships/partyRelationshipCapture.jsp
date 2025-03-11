<div style="display: none;" id="partyRelFrom">
    <table class="party-table">
        <tr>
            <td class="title" style="color:#000066;">
                <label id="lblPartyRelationshipFromDescription">
                    <fmt:message key="page.party.relationships.label.description"/>
                </label>
            </td>
            <td class="title">
                <html:text property="txtPartyRelationshipFromDescription" styleClass="form-control input-sm"/>
            </td>
            <td class="title" style="color:#000066;">
                <label id="lblPartyRelationshipFromStartDate" styleClass="form-control input-sm">
                    <fmt:message key="page.party.relationships.label.startdate"/>
                </label>
            </td>
            <td class="title">
                <html:text property="txtPartyRelationshipFromStartDate" styleClass="form-control input-sm datefield"
                           styleId="relStartDateFrom"/>
            </td>
        </tr>
        <tr>
            <td class="title" style="color:#000066;">
                <label id="lblPartyRelationshipFromEndDate">
                    <fmt:message key="page.party.relationships.label.enddate"/>
                </label>
            </td>
            <td class="title">
                <html:text property="txtPartyRelationshipFromEndDate" styleClass="form-control input-sm datefield"/>
            </td>
            <td class="title" style="color:#000066;">
                <label id="lblPartyRelationshipRelatedFrom" for="relatedToParty">
                    <fmt:message key="page.party.relationships.label.relatedto"/>
                </label>
            </td>
            <td class="title">
                <input class="form-control input-sm" name="relatedToParty" id="relatedToParty" type="text"/>
            </td>
        </tr>
    </table>
</div>

<!-- Add Relationship button -->
<div style="display: none;" id="addRelFromButton">
    <table class="party-table">
        <tr>
            <td id="addRelFrom" colspan="4" align="center">
                <html:submit property="method" style="width:13%" styleClass="btn btn-primary btn-sm">
                    <fmt:message key="page.party.relationships.action.addrelationship"/>
                </html:submit>
            </td>
        </tr>
    </table>
</div>

<!-- Edit Relationship button -->
<div style="display: none;" id="updateRelFromButton">
    <table class="party-table">
        <tr>
            <td id="editRelFromButton" colspan="4" align="center">
                <html:submit property="method" style="width:13%" styleClass="btn btn-primary btn-sm">
                    <fmt:message key="page.party.relationships.action.updaterelationship"/>
                </html:submit>
                <html:button property="method" styleClass="btn btn-primary btn-sm" onclick="cancelRelationshipEdit()">
                    <fmt:message key="page.party.relationships.action.cancelupdate"/>
                </html:button>
            </td>
        </tr>
    </table>
</div>
<div class="sectionDivide"></div>