$(document).ready(function() {
    if ($(roleKindToLink).val() === 'Insurer'){

        var my_value = "Organisation";
    	$('#selType option').each(function(){
    		var $this = $(this); // cache the this jQuery object to avoid overhead

    		if ($this.val() == my_value) { // if this option's value is equal to our value
				$this.attr('selected', 'selected'); 
    			return false; // break the loop, no need to look further
    		}
			else {
				$this.removeAttr('selected');
			}
    	});
    }
    
	if ($('#selType').val() === "Person") {
    	$('#addPartyPerson').show();
    	$('#addOrg').hide();
    	$('#addPartyButton').show();
    	$('#addOrganisationButton').hide();	
    }
    if ($('#selType').val() === "Organisation") {
    	$('#addPartyPerson').hide();
    	$('#addOrg').show();
    	$('#addPartyButton').hide();
    	$('#addOrganisationButton').show();
    	$('#txtOrgType').val("");
    }    	
    
    function reset() {
    	$('#txtFirstName').val("");
    	$('#txtMiddleName').val("");
    	$('#txtSurname').val("");
    	$('#txtBirthDate').val("");
    	$('#txtOrgFullName').val("");
    }

    if (document.forms[0].duplicatesFound.value=='true') {
        var alertMessage = 'The Party you are capturing is potentially a duplicate of an existing Party in the database.\nIf you are positive this is not a duplicate add the party.';
        bootbox.dialog({
            message: alertMessage,
            title: "Information",
            buttons: {
                ok: {
                    label: "OK"
                }
            }
        });
    }
});


function showType() {
    reset();
    if (document.forms[0].selType.value == "Person") {
        document.getElementById('addPartyPerson').style.display = 'block';
        document.getElementById('addOrg').style.display = 'none';
        document.getElementById('addPartyButton').style.display = 'block';
        document.getElementById('addOrganisationButton').style.display = 'none';
    } 
    if (document.forms[0].selType.value == "Organisation") {
        document.getElementById('addPartyPerson').style.display = 'none';
        document.getElementById('addOrg').style.display = 'block';
        document.getElementById('addPartyButton').style.display = 'none';
        document.getElementById('addOrganisationButton').style.display = 'block';
    }

}

function reset() {
    document.forms[0].txtFirstName.value='';
    document.forms[0].txtMiddleName.value='';
    document.forms[0].txtSurname.value='';
    document.forms[0].txtBirthDate.value='';
    document.forms[0].txtOrgFullName.value='';
}

/*
 * Set the validation method that should be triggered with this form submission
 */
function setValidationMethod(validationMethod) {
	$('#validate').val(validationMethod);
}

/*
 * Triggered when the user clicks the save... button after duplicates have been displayed
 */
function overrideDuplicatesIfPresent() {  
    if (document.forms[0].duplicatesFound.value == 'true') {
    	// duplicates have been found, and the user has been notified of this
        document.forms[0].overRideDups.value = 'true';
    }
}