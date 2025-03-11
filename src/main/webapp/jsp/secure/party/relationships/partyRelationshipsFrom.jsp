<c:if test="${! empty partyForm.partyRelationshipsFromMap}">
    <table class="party-table" style="width:60%">
        <tr>
            <td>
                <div style="display: block;">
                    <table cellpadding="6px">
                        <tr>
                            <td class="agreementExpand" width="18px">
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label id="lblRelationshipsFrom">
                                    <fmt:message key="page.party.relationships.label.relationshipsto" />
                                </label>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="obj3">
                    <div style="display: block;" id="relationships">
                        <table class="table table-striped">
                            <c:if test="${! empty partyForm.partyRelationshipsFromMap}">
                            <tr>
                                <td class="lightgrey" style="color:#000066;width:25%"><b><fmt:message key="page.party.relationships.label.relatedto" /></b></td>
                                <td class="lightgrey" style="color:#000066;width:25%"><b><fmt:message key="page.party.relationships.label.nature" /></b></td>
                                <td class="lightgrey" style="color:#000066;width:20%"><b><fmt:message key="page.party.relationships.label.description" /></b></td>
                                <td class="lightgrey" style="color:#000066;width:15%"><b><fmt:message key="page.party.relationships.label.startdate" /></b></td>
                                <td class="lightgrey" style="color:#000066;width:15%"><b><fmt:message key="page.party.relationships.label.enddate" /></b></td>
                                <td class="lightgrey" style="width:2%"><b></b></td>
                            </tr>
                                <c:forEach var="relationshipsFrom" items="${partyForm.partyRelationshipsFromMap}">
                                    <tr id="editRel${relationshipsFrom.key}">
                                        <td class="title"><c:out value="${relationshipsFrom.value.relatedTo}" /></td>
                                        <td class="title"><c:out value="${relationshipsFrom.value.type}"/></td>
                                        <td class="title"><c:out value="${relationshipsFrom.value.description}"/></td>
                                        <td class="title"><c:out value="${relationshipsFrom.value.startDate}"/></td>
                                        <td class="title"><c:out value="${relationshipsFrom.value.endDate}"/></td>
                                        <td style="background-color:#6DA1CF;text-align:center;">
                                            <input type="radio" name="relFromSelected"
                                                   value="${relationshipsFrom.key}"
                                                   onclick="loadRelToEdit(this.form, ${relationshipsFrom.value.typeId});"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </table>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</c:if>
<html:hidden property="txtPartyRelationshipToObjRef" styleId="relatedToObjRef"/>

<script>
    (function(){

        //[Button] Add Insured
        $("input[name=relatedToParty]").click(function(e) {
            e.preventDefault();

            var parentType = $("#relTypesFrom").children("option:selected");

            if (parentType[0].title.indexOf("To Person") !== -1) {
                //new window
                window.returnValueObj = {
                    $obj   : "",
                    $objRef   : "",
                    getReturnValue : function(returnValue) {
                        var obj = JSON.parse( returnValue );
                        this.$obj.val( obj.title + " " + obj.surname + (function(){
                                if(obj.middlename) {
                                    return " " + obj.middlename;
                                } else {
                                    return "";
                                }
                            })() + " " + obj.firstname );
                        this.$objRef.val( obj.objectReference );
                    }
                };

                window.returnValueObj.$obj  = $("input[name=relatedToParty]");
                window.returnValueObj.$objRef = $("#relatedToObjRef");
                window.newWindow = window.open("${pageContext.request.contextPath}/secure/party/getPerson.do", "_blank", "menubar=no,status=no,toolbar=no,width=750,height=600");
                window.newWindow.focus();
            } else {
                //new window
                window.returnValueObj = {
                    $obj   : "",
                    $objRef   : "",
                    getReturnValue : function(returnValue) {
                        var obj = JSON.parse( returnValue );
                        this.$obj.val(obj.fullName);
                        this.$objRef.val( obj.objectReference );
                    }
                };

                window.returnValueObj.$obj  = $("input[name=relatedToParty]");
                window.returnValueObj.$objRef = $("#relatedToObjRef");
                window.newWindow = window.open("${pageContext.request.contextPath}/secure/party/getOrganisation.do", "_blank", "menubar=no,status=no,toolbar=no,width=750,height=600");
                window.newWindow.focus();
            }
        });
    })();
</script>