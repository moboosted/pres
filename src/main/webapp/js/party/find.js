$(document).ready(function() {
    if ($(roleKindToLink).val() === 'Insurer'){
    	$('#selSearch').val("Organisation");
    }

	if ($('#selSearch').val() === "Person") {
    	showPerson();
		$('#txtSearchHouseNumber')[0].disabled = isEmptyOrSpaces($('#txtSearchStreet').val());
    }

	$('#txtSearchStreet').keyup(function () {
		if(!isEmptyOrSpaces($('#txtSearchStreet').val())){
			$('#txtSearchHouseNumber')[0].disabled = false;
		} else {
			$('#txtSearchHouseNumber').val("");
			$('#txtSearchHouseNumber')[0].disabled = true;
		}
	})

    if ($('#selSearch').val() === "Organisation") {
    	showOrganisation();
    }
    
//    if ($('#embeddedMode').val() === "true"){
//    	$('#selSearch').attr("disabled","disabled");
//    }
    
    $('#selSearch').change(function(){
    	showPartySearchType();
    });
    
    var showPartySearchType = function(){
        if ($('#selSearch').val() === "Person") {
			resetPerson();
			showPerson();
			$('#txtSearchHouseNumber')[0].disabled = true;
			$('#organisationSearch').hide();
			$('#personSearch').show();
        }
        if ($('#selSearch').val() === "Organisation") {
			resetOrganisation();
			showOrganisation();
			$('#organisationSearch').show();
			$('#personSearch').hide();
		}
    };
    var searchButton = function(){
        if ($('#selSearch').val() === "Person") {
			resetPerson();
			showPerson();
			$('#txtSearchHouseNumber')[0].disabled = true;
			$('#organisationSearch').hide();
			$('#personSearch').show();
		}
        if ($('#selSearch').val() === "Organisation") {
			resetOrganisation();
			showOrganisation();
			$('#organisationSearch').show();
			$('#personSearch').hide();
		}
    };
    
});

function searchButtonBackup() {
	if ($('#selSearch').val() === "Person") {
		$('#organisationSearch').hide();
		$('#personSearch').show();
	}
	if ($('#selSearch').val() === "Organisation") {
		$('#organisationSearch').show();
		$('#personSearch').hide();
	}
}

function showPartySearchTypeBackkup() {
    if ($('#selSearch').val() === "Person") {
		resetPerson();
    	showPerson();
		$('#txtSearchHouseNumber')[0].disabled = isEmptyOrSpaces($('#txtSearchStreet').val());
    }
    if ($('#selSearch').val() === "Organisation") {
		resetOrganisation();
    	showOrganisation();
    }
}

/*
 * Set the validation method that should be triggered with this form submission
 */
function setValidationMethod(validationMethod) {
	$('#validate').val(validationMethod);
}

function isEmptyOrSpaces(str){
	return str === null || str.match(/^ *$/) !== null;
}

function resetPartySearchCriteria(){
	if ($('#selSearch').val() === "Person") {
		resetPerson();
		showPerson();
		$('#organisationSearch').hide();
		$('#personSearch').show();
		$('#txtSearchHouseNumber')[0].disabled = true;
	}
	if ($('#selSearch').val() === "Organisation") {
		resetOrganisation();
		showOrganisation();
		$('#organisationSearch').show();
		$('#personSearch').hide();
	}
}

function showPerson(){
	$('#findPartyPerson').show();
	$('#findPartyOrg').hide();
	$('#searchPartyButtonPerson').show();
	$('#searchPartyButtonOrg').hide();
	$('#resetPersonSearchCriteria').show();
	$('#resetOrganisationSearchCriteria').hide();
}

function showOrganisation(){
	$('#findPartyPerson').hide();
	$('#findPartyOrg').show();
	$('#searchPartyButtonPerson').hide();
	$('#searchPartyButtonOrg').show();
	$('#resetPersonSearchCriteria').hide();
	$('#resetOrganisationSearchCriteria').show();
}

function resetPerson(){
	$('#txtSearchFirstName').val("");
	$('#txtSearchMiddleName').val("");
	$('#txtSearchSurname').val("");
	$('#txtSearchBirthDate').val("");
	$('#txtSearchCity').val("");
	$('#txtSearchPostalCode').val("");
	$('#txtSearchStreet').val("");
	$('#txtSearchHouseNumber').val("");
}

function resetOrganisation(){
	$('#orgTypes').val("");
	$('#txtSearchOrgFullName').val("");
}