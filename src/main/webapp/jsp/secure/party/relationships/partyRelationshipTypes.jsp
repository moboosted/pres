<div>
    <table>
        <tr>
            <td class="lightgrey">
                <label><fmt:message key="page.party.relationships.label.addafrom"/>&nbsp;&nbsp;</label>
            </td>
            <td>
                <html:select styleId="relTypesFrom" styleClass="form-control input-sm"
                             property="selRelationshipTypeFrom" onchange="showRelationshipsAdd(this.form);">
                    <html:option value="selected">
                        <smg:fmt value="page.party.relationships.label.select"/>
                    </html:option>
                    <c:choose>
                        <c:when test="${partyForm.selPartyType == 'Person'}">
                            <smg:types subTypesOf="From Person To Person Relationship,From Person To Organisation Relationship"
                                       valuesAsNames="${false}"/>
                        </c:when>
                        <c:otherwise>
                            <smg:types subTypesOf="From Organisation To Organisation Relationship,From Organisation To Person Relationship"
                                       valuesAsNames="${false}"/>
                        </c:otherwise>
                    </c:choose>
                </html:select>
                <html:hidden property="selRelationshipTypeForEditFrom" styleId="selRelationshipTypeForEditFrom"/>
            </td>
            <td class="lightgrey">
                <label>&nbsp;&nbsp;<fmt:message key="page.party.relationships.label.relationship"/></label>
            </td>
        </tr>
    </table>
</div>