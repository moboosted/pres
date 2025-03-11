function relationShipRoleToEnd(form) {
  if (confirm('End this role?')) {
    form.hiddenMethod.value = 'endRole';
    form.submit();
    return true;
  } else {
    return false;
  }
}

$(document).ready(function () {
  $("tr#row35_history").hide();
  $("a.history").click(function () {
    $("tr#row35_history").show();
    $("tr#row35").hide();
  });

  $("a.close").click(function () {
    $("tr#row35_history").find("tr:gt(1)").remove();
    $("tr#row35_history").hide();
    $("tr#row35").show();
  });
});

var retrieveHistory = function (contextPath, anchorObjectReference, tlaObjectReference, agreementPartObjectReference) {
  $.ajax({
    url: contextPath + "/secure/partyAction.do?method=.displayPartyRoleHistoryForAgreement",
    type: "GET",
    data: {"anchorObjectReference": anchorObjectReference, "tlaObjectReference": tlaObjectReference, "agreementPartObjectReference": agreementPartObjectReference},
    dataType: 'json',
    success: function (data) {
      // console.log(data.agreementversions[0]);
      $.each(data.agreementversions, function (i, item) {
        var rowContent = $('<tr>');

        // Version Number
        rowContent.append('<td class="title"><a href="' + contextPath + '/secure/agreementversion/tla.do?contextObjectReference=' + item.tlaObjectReference + '">' + item.version);
        rowContent.append('</a></td>');

        // Effective Date
        rowContent.append('<td class="title c">' + item.effectiveDate);
        rowContent.append('</td>');

        // Creation Date
        rowContent.append('<td class="title c">' + item.creationDate);
        rowContent.append('</td>');

        // Status
        rowContent.append('<td class="title c">' + item.status);
        rowContent.append('</td>');

        // Legally Binding
        var chekboxContent = (item.legallyBinding == true)
          ? '<img src=' + contextPath + '/images/tick.png>'
          : '<img src=' + contextPath + '/images/cross.png>';


        rowContent.append('<td class="title c">' + chekboxContent);
        rowContent.append('</td>');

        // Agreement Part
        rowContent.append('<td class="title"><a href="' + contextPath + '/secure/agreementversion/tla.do?method=.viewAgreementPart'
          + '&contextObjectReference=' + item.tlaObjectReference
          + '&relativeAgreementPartObjectReference=' + item.agreementPartObjectReference + '">' + item.agreementPart);
        rowContent.append('</td>');

        rowContent.append('</tr>');

        rowContent.insertAfter('tr#historyHeader');
      });
    },
    error: function (e) {
      console.log(e);
      alert("Error retrieving agreement version history for party role: " + e);
    }
  });
};