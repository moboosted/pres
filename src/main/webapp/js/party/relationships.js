/**
 * Created by Jordan Olsen on 2017/04/20.
 */
function showRelationshipsAdd(form) {
    // reset and hide everything first
    resetRelationshipData(form);
    hideAll();

    var selected = $("#relTypesFrom").children("option:selected");

    if (selected[0].label !== "--- Select ---") {
        $("#partyRelFrom").show();
        $("#addRelFromButton").show();
    }
}

function hideAll() {
    $("#partyRelFrom").hide();
    $("#addRelFromButton").hide();
    $("#updateRelFromButton").hide();
}

function resetRelationshipData(form) {
    form.txtPartyRelationshipFromDescription.value = '';
    form.txtPartyRelationshipFromStartDate.value = '';
    form.txtPartyRelationshipFromEndDate.value = '';
    form.txtPartyRelationshipToObjRef.value = '';
    $("input[name=relatedToParty]").val("");
}

function loadRelToEdit(form, typeId) {

    var id = $("input[name='relFromSelected']:checked").val();
    var row = document.getElementById("editRel" + id);
    var columns = row.getElementsByTagName("td");

    if (columns[0].lastChild != null) {
        var relatedToField = $("input[name='relatedToParty']");
        relatedToField.val(columns[0].lastChild.nodeValue);
    }

    if (columns[2].lastChild != null) {
        form.txtPartyRelationshipFromDescription.value = columns[2].lastChild.nodeValue;
    }

    if (columns[3].lastChild != null) {
        form.txtPartyRelationshipFromStartDate.value = columns[3].lastChild.nodeValue;
    }

    if (columns[4].lastChild != null) {
        form.txtPartyRelationshipFromEndDate.value = columns[4].lastChild.nodeValue;
    }

    showRelTypeForEdit("partyRelFrom", typeId);
}

function showRelTypeForEdit(divId, typeId) {
    hideAll();

    var attributeTypeSelection = $("#relTypesFrom");
    attributeTypeSelection.val(typeId);
    attributeTypeSelection.attr("disabled", "disabled");

    var selRelationshipTypeForEditFrom = $("#selRelationshipTypeForEditFrom");
    selRelationshipTypeForEditFrom.val(typeId);

    // when the start date is disabled, the date is not populated in the form, so we read the startdate from the db. If we enable the start date field, the date is populated in the form, so we will need to edit PartyRelationshipActionHelper.java to read it from the form
    var startDateField = $("#relStartDateFrom");
    startDateField.attr("disabled", "disabled");
    var relatedToField = $("input[name='relatedToParty']");
    relatedToField.attr("disabled", "disabled");

    $("#" + divId).show();
    $("#updateRelFromButton").show();
}

/* Cancel the editing of a relationship */
function cancelRelationshipEdit() {
    var attributeTypeSelection = $(document.forms[0].selRelationshipTypeFrom);
    attributeTypeSelection.val("--- Select ---");
    attributeTypeSelection.attr("disabled", false);

    $("#relTypesFrom").attr("disabled", false);
    $("input[name='relFromSelected']:checked").attr("checked", false);

    resetRelationshipData(document.forms[0]);
    hideAll();
}