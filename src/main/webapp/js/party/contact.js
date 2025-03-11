/**
 * Called when the page is first loaded
 */
function initialiseContactPage() {
	toggleContactPointsForSelectedPreference($('#partyForm'))
}

//ADD PREFERENCE DISPLAY
function showContactPreferenceTypeAdd(form) { 
    form.txtStartDateContactPref.value = '';
    form.txtEndDateContactPref.value = '';
    form.selPreferredLanguage.value = 'French';

    if ($('#selContactPreferenceTypeId').val() == '') {
        $('#contactPreference').hide();   
        $('#buttonAddPref').hide(); 
        $('#buttonUpdatePref').hide();    
    } else {
        $('#contactPreference').show();
        $('#buttonAddPref').show();
        $('#buttonUpdatePref').hide();
    } 
}

function getContactPreferenceById(contactPreferenceId) {
    var contactPreference;
    $.each(contactPreferencesJson, function (i, pref) {
        if (pref.id == contactPreferenceId) {
            contactPreference = pref;
        }
    });
    return contactPreference;
}

function getContactPointById(contactPointId) {

    var contactPoint;

    $.each(contactPreferencesJson, function (i, pref) {
        $.each(pref.contactPoints, function (j, point) {
            if (point.id == contactPointId) {
                contactPoint = point;
                return;
            }
        });
        if (!contactPoint) {
            return;
        }
    });
    return contactPoint;

}
/**
 * Triggered when the radio button next to a contact preference is selected
 */
function loadPreferenceToEditAndSelect(form) {

	var selectedPreferenceId = $('input[name=preferenceSelected]:checked', form).val();
    var previouslySelectedPreferenceId = $('input[name=selectedPreferenceId]', form).val();


    if (selectedPreferenceId == previouslySelectedPreferenceId) {
        console.log("Clicking on the same Contact Prefernce - no action taken");
        return;
    }
    else {
        $('input[name=selectedPreferenceId]', form).val(selectedPreferenceId); // store the currently selected preference
    }


    var contactPreference = getContactPreferenceById(selectedPreferenceId);
    if (!contactPreference) {
        console.error("Did not find a matching Contact Preference in the JSON");
        return;
    }

    form.selContactPreferenceTypeId.value = contactPreference.typeId;
    form.selPreferredLanguage.value = contactPreference.preferredLanguage;
    form.txtStartDateContactPref.value = contactPreference.startDate;
    form.txtEndDateContactPref.value = contactPreference.endDate;
    form.defContPref.checked = contactPreference.defaultContactPreference;

    document.getElementById("selContactPreferenceTypeId").setAttribute("disabled", "disabled");

    // contact pref values Store (for changes listening)
    showContactPreferenceTypeForEdit();
    showContactPointTypeSelection();
    hideContactPointEditFields();
    toggleContactPointsForSelectedPreference(form);
}   

function showContactPreferenceTypeForEdit() { 

	if ($('#contactPreferenceTypeId').val() == '') {
	    $('#contactPreference').hide(); 
	    $('#buttonAddPref').hide(); 
	    $('#buttonUpdatePref').hide();     
	} else {                             
	    $('#contactPreference').show(); 
	    $('#buttonAddPref').hide(); 
	   	$('#buttonUpdatePref').show();         
	}      
}

function showContactPointTypeSelection() {
	 $('#contactPointTypes').show();
}

/**
 * Only allow the selection of contact points for the selected preference
 */
function toggleContactPointsForSelectedPreference(form) {
	// first, disable and uncheck all selected contact points
	$('input[name=contactSelected]', form).each(function(){
		  $(this).attr('checked', false);
		  $(this).attr('disabled', 'disabled');
	});
	
	var selectedPreferenceId = $('input[name=selectedPreferenceId]', form).val();
	
	// now enable those contact points that belong to the preference
	$.each(contactPreferencesJson, function(prefIndex, preference) {
		if (preference.id == selectedPreferenceId) {
			$.each(preference.contactPoints, function(cpIndex, contactPoint) {
				$('input[name=contactSelected][value=' + contactPoint.id + ']', form).removeAttr("disabled")
			});
		}
	});
	
}

//------------------------------------------------------------------------------------------------------------
// CONTACT POINT 
//------------------------------------------------------------------------------------------------------------

function getSelectedContactPreferenceId() {
    return parseInt(document.forms[0].selContactPointTypeId.value);
}

function getSelectedContactPointId() {
    var selectContactPointId;
    var selected = $("input[type='radio'][name='contactSelected']:checked");
    if (selected.length > 0) {
        selectContactPointId = selected.val();
    }
    return selectContactPointId;
}

function showContactPointTypeAdd(form) {
    resetFields(form);
    form.defContPoint.disabled=false;
    document.getElementById("defCntPointDisp").removeAttribute("disabled", "disabled");
    form.defContPoint.value='off';
    form.defContPoint.checked=false;

    var selectedContactPointTypeId = getSelectedContactPreferenceId();
    console.log("Selected contact point type is " + selectedContactPointTypeId);

    var electronicAddressDiv = $('#electronicAddress');

    var physicalAddressDiv = $('#physicalAddress');
    var postalAddressDiv = $('#postalAddress');
    var landLineDiv = $('#landline');
    var unstructuredAddressDiv = $('#unstructuredAddress');
    var buttonAddDiv = $('#commonButtonAdd');
    var buttonUpdateDiv = $('#commonButtonUpdate');
    var contactToLinkButtonDiv = $('#searchContactToLinkButton');
    var defaultContactPointDisplayDiv = $('#defCntPointDisp');
    switch (selectedContactPointTypeId) {
        case 8300:
            electronicAddressDiv.hide();
            physicalAddressDiv.hide();
            postalAddressDiv.hide();
            landLineDiv.show();
            unstructuredAddressDiv.hide();
            buttonAddDiv.show();
            buttonUpdateDiv.hide();
            contactToLinkButtonDiv.show();
            break;

        case 8301:
            electronicAddressDiv.show();
            physicalAddressDiv.hide();
            postalAddressDiv.hide();
            landLineDiv.hide();
            unstructuredAddressDiv.hide();
            buttonAddDiv.show();
            buttonUpdateDiv.hide();
            contactToLinkButtonDiv.show();
            defaultContactPointDisplayDiv.show();
            break;

        case 8315:     // physical address
            electronicAddressDiv.hide();
            physicalAddressDiv.show();
            postalAddressDiv.hide();
            landLineDiv.hide();
            unstructuredAddressDiv.hide();
            buttonAddDiv.show();
            buttonUpdateDiv.hide();
            contactToLinkButtonDiv.show();
            defaultContactPointDisplayDiv.show();
            break;

        case 8316:     // postal address
            electronicAddressDiv.hide();
            physicalAddressDiv.hide();
            postalAddressDiv.show();
            landLineDiv.hide();
            unstructuredAddressDiv.hide();
            buttonAddDiv.show();
            buttonUpdateDiv.hide();
            contactToLinkButtonDiv.show();
            defaultContactPointDisplayDiv.show();
            break;

        case 8317:     // unstructured address
            electronicAddressDiv.hide();
            physicalAddressDiv.hide();
            postalAddressDiv.hide();
            landLineDiv.hide();
            unstructuredAddressDiv.show();
            buttonAddDiv.show();
            buttonUpdateDiv.hide();
            contactToLinkButtonDiv.show();
            defaultContactPointDisplayDiv.show();
            break;

        default :
            electronicAddressDiv.hide();
            physicalAddressDiv.hide();
            postalAddressDiv.show();
            landLineDiv.hide();
            unstructuredAddressDiv.hide();
            buttonAddDiv.hide();
            buttonUpdateDiv.hide();
            contactToLinkButtonDiv.hide();
            defaultContactPointDisplayDiv.hide();
            break;
    }
}

function postalPhysEditORModifyRegion() {
    // check if postal or physical contact, and in edit mode
    if (document.forms[0].postalContactEdit.value=='true' || document.forms[0].physicalContactEdit.value=='true'
        || document.forms[0].unstructuredContactEdit.value=='true'
        ) {
        if (document.forms[0].postalContactEdit.value == 'true') {    
            showPostalContactFields();
            $('#commonButtonUpdate').show();
            $('#commonButtonAdd').hide();
        } else
        if (document.forms[0].physicalContactEdit.value == 'true') {    
            showPhysicalContactFields();
            $('#commonButtonUpdate').show();
            $('#commonButtonAdd').hide();
        } else
        if (document.forms[0].unstructuredContactEdit.value == 'true') {
            showUnstructuredContactFields();
            $('#commonButtonUpdate').show();
            $('#commonButtonAdd').hide();
        }
        document.forms[0].selContactPointType.value =''; 
    }

    // check if postal or physical or unstructured contact, and if REGION has been changed
    if (document.forms[0].postAddrRegionChanged.value=='true' || document.forms[0].physAddrRegionChanged.value=='true'
            || document.forms[0].unstructAddrRegionChanged.value=='true'
        ) {
        if (document.forms[0].postAddrRegionChanged.value=='true') {
            showPostalContactFields();
        } else
        if (document.forms[0].physAddrRegionChanged.value=='true') {
            showPhysicalContactFields();
        } else
        if (document.forms[0].unstructAddrRegionChanged.value=='true') {
            showPhysicalContactFields();
        }
        // if either of above, check whether to show Add or Edit button
        if (document.forms[0].selContactPointType !=null) {
            if (document.forms[0].selContactPointType.value =='-Select-') {
                  $('#commonButtonUpdate').show();
            } else {
                if (document.forms[0].selContactPointType.value !='-Select-') {
                    $('#commonButtonAdd').show();
                }
            }           
        }
    }
    document.forms[0].postalContactEdit.value = 'false';
    document.forms[0].physicalContactEdit.value = 'false'; 
    document.forms[0].postAddrRegionChanged.value='false';
    document.forms[0].physAddrRegionChanged.value='false';
    document.forms[0].unstructAddrRegionChanged.value='false';
}

 function showPhysicalContactFields() { 
    $('#physicalAddress').show(); 
    $('#defCntPointDisp').show();
    $('#defCntPointDisp').show();
    $('#postalAddress').hide();
    $('#unstructuredAddress').hide();
    $('#electronicAddress').hide();
    $('#landline').hide(); 
 }
 function showPostalContactFields() { 
    $('#physicalAddress').hide(); 
    $('#defCntPointDisp').hide();
    $('#defCntPointDisp').show();
    $('#postalAddress').show();
    $('#unstructuredAddress').hide();
    $('#electronicAddress').hide();
    $('#landline').hide();     
 }
function showUnstructuredContactFields() {
    $('#physicalAddress').hide();
    $('#defCntPointDisp').hide();
    $('#defCntPointDisp').show();
    $('#postalAddress').hide();
    $('#unstructuredAddress').show();
    $('#electronicAddress').hide();
    $('#landline').hide();
}

function loadContactToEdit(form) { // phone and email
	
    resetFields(form);

    var selectedContactPointId = getSelectedContactPointId();
    var contactPoint = getContactPointById(selectedContactPointId);
    var baseTypeId = contactPoint.baseTypeId;

    form.selContactPointTypeId.value = contactPoint.typeId;
    document.getElementById("selContactPointTypeId").setAttribute("disabled", "disabled");  // disable the selection of the contact preference whilst editing

    switch (baseTypeId) {
        case 8300:  // telephone number
            form.txtLandLineCountryCode.value = contactPoint.countryCode;
            form.txtLandLineAreaCode.value = contactPoint.areaCode;
            form.txtLandLineLocalNumber.value = contactPoint.localNumber;
            form.txtLandLineExt.value = contactPoint.extension;
            form.txtStartDateNumber.value = contactPoint.startDate;
            form.txtEndDateNumber.value = contactPoint.endDate;

            $('#electronicAddress').hide();
            $('#physicalAddress').hide();
            $('#postalAddress').hide();
            $('#unstructuredAddress').hide();
            $('#landline').show();
            $('#commonButtonUpdate').show();

            break;
        case 8301:  // email
            form.txtEmail.value = contactPoint.address;
            form.txtStartDateEmail.value = contactPoint.startDate;
            form.txtEndDateEmail.value = contactPoint.endDate;

            $('#electronicAddress').show();
            $('#physicalAddress').hide();
            $('#postalAddress').hide();
            $('#unstructuredAddress').hide();
            $('#landline').hide();
            $('#commonButtonUpdate').show();

            break;
        case 8315:  // physical address

            form.txtUnitNumber.value = contactPoint.unitNumber;
            form.txtFloorNumber.value = contactPoint.floorNumber;
            form.txtBuildingName.value = contactPoint.buildingName;
            form.txtHouseNumber.value = contactPoint.houseNumber;
            form.txtStreet.value = contactPoint.street;
            form.txtPhysicalAddressSubRegion.value = contactPoint.subregion;
            form.txtPhysicalAddressCity.value = contactPoint.city;
            form.txtPhysicalAddressRegion.value = contactPoint.region;
            form.selPhysicalAddressCountryName.value = contactPoint.country;
            form.txtPhysicalAddressPostalCode.value = contactPoint.postalCode;
            form.txtStartDatePhysicalAddress.value = contactPoint.startDate;
            form.txtEndDatePhysicalAddress.value = contactPoint.endDate;

            $('#electronicAddress').hide();
            $('#physicalAddress').show();
            $('#postalAddress').hide();
            $('#unstructuredAddress').hide();
            $('#landline').hide();
            $('#commonButtonUpdate').show();
            break;
        case 8316:  // postal address

            form.txtBoxNumber.value = contactPoint.boxNumber;
            form.txtPostnetSuite.value = contactPoint.postnetSuite;
            form.txtPostalAddressSubRegion.value = contactPoint.subregion;
            form.txtPostalAddressCity.value = contactPoint.city;
            form.txtPostalAddressRegion.value = contactPoint.region;
            form.txtPostalAddressPostalCode.value = contactPoint.postalCode;
            form.txtStartDatePostalAddress.value = contactPoint.startDate;
            form.txtEndDatePostalAddress.value = contactPoint.endDate;
            form.selPostalAddressCountryName.value=contactPoint.country;

            $('#electronicAddress').hide();
            $('#physicalAddress').hide();
            $('#postalAddress').show();
            $('#unstructuredAddress').hide();
            $('#landline').hide();
            $('#commonButtonUpdate').show();
            break;

        case 8317:  // unstructured address

            form.txtAddressLine1.value = contactPoint.addressLine1;
            form.txtAddressLine2.value = contactPoint.addressLine2;
            form.txtAddressLine3.value = contactPoint.addressLine3;
            form.txtAddressLine4.value = contactPoint.addressLine4;
            form.txtUnstructuredAddressSubRegion.value = contactPoint.subregion;
            form.txtUnstructuredAddressCity.value = contactPoint.city;
            form.txtUnstructuredAddressRegion.value = contactPoint.region;
            form.txtUnstructuredAddressPostalCode.value = contactPoint.postalCode;
            form.txtStartDateUnstructuredAddress.value = contactPoint.startDate;
            form.txtEndDateUnstructuredAddress.value = contactPoint.endDate;
            form.selUnstructuredAddressCountryName.value=contactPoint.country;

            $('#electronicAddress').hide();
            $('#physicalAddress').hide();
            $('#postalAddress').hide();
            $('#unstructuredAddress').show();
            $('#landline').hide();
            $('#commonButtonUpdate').show();
            break;

        default:
            console.error("Unknown contact point type: " + baseTypeId);
            return;
    }
}

/**
 * Hide the section where contact points are edited
 */
function hideContactPointEditFields() {
	$('#electronicAddress').hide()
    $('#physicalAddress').hide()      
    $('#postalAddress').hide()
    $('#unstructuredAddress').hide()
    $('#landline').hide()
    $('#defCntPointDisp').hide()
    
    $('#commonButtonAdd').hide(); 
    $('#commonButtonUpdate').hide(); 
    $('#searchContactToLinkButton').hide();
}

function resetFields(form) {
    // email
    form.txtEmail.value = '';
    form.txtStartDateEmail.value = '';
    form.txtEndDateEmail.value = '';
    // telephone    
    form.txtLandLineCountryCode.value = '';
    form.txtLandLineAreaCode.value = '';
    form.txtLandLineLocalNumber.value = '';
    form.txtStartDateNumber.value = '';
    form.txtEndDateNumber.value = '';
    // physical
    form.txtUnitNumber.value = '';
    form.txtFloorNumber.value = '';
    form.txtBuildingName.value = '';
    form.txtHouseNumber.value = '';
    form.txtStreet.value = '';
    form.txtPhysicalAddressCity.value = '';
    form.txtPhysicalAddressSubRegion.value = '';
    form.txtPhysicalAddressRegion.value = '';
    form.txtPhysicalAddressPostalCode.value = '';
    form.txtStartDatePhysicalAddress.value = '';
    form.txtEndDatePhysicalAddress.value = '';
    // postal
    form.txtBoxNumber.value = '';
    form.txtPostnetSuite.value = '';
    form.txtPostalAddressCity.value = '';
    form.txtPostalAddressSubRegion.value = '';  
    form.txtPostalAddressRegion.value = '';
    form.txtPostalAddressPostalCode.value = '';
    form.txtStartDatePostalAddress.value = '';
    form.txtEndDatePostalAddress.value = '';
    // unstructured
    form.txtAddressLine1.value = '';
    form.txtAddressLine2.value = '';
    form.txtAddressLine3.value = '';
    form.txtAddressLine4.value = '';
    form.txtUnstructuredAddressSubRegion.value = '';
    form.txtUnstructuredAddressCity.value = '';
    form.txtUnstructuredAddressRegion.value = '';
    form.txtUnstructuredAddressPostalCode.value = '';
    form.txtStartDateUnstructuredAddress.value = '';
    form.txtEndDateUnstructuredAddress.value = '';
    form.selUnstructuredAddressCountryName.value = '';
}

//------------------------------------------------------------------------------------------------------------
// VALIDATE ADD CONTACT Button
//------------------------------------------------------------------------------------------------------------
function validateForAddContact(form) {      
    // Email
    if (document.forms[0].selContactPointType.value == 'Email') {
        validateEmail();
    }
    // Fax
    if (document.forms[0].selContactPointType.value == 'Fax') {
        validatePhone();        
    }
    // Landline
    if (document.forms[0].selContactPointType.value == 'Landline') {
        validatePhone();                    
    }
    // Mobile
    if (document.forms[0].selContactPointType.value == 'Mobile') {  
        validatePhone();    
    }
    if (document.forms[0].selContactPointType.value == 'Postal Address') {
        validatePostal();
    }
    if (document.forms[0].selContactPointType.value == 'Unstructured Address') {
        validateUnstructured();
    }
    if (document.forms[0].selContactPointType.value == 'Physical Address')  {
        validatePhysical();
    }
    if (document.forms[0].selContactPreferenceTypeId.value == '') {
        alert("Please select the Contact Preference for this new Contact Point");
        document.forms[0].selContactPointType.focus();
    }       
    if (form.defContPoint.checked==true) {
        form.defContPoint.value='on';
    } else {
        form.defContPoint.value='off';
    }       
}

//------------------------------------------------------------------------------------------------------------
// VALIDATE EDIT CONTACT Button
//------------------------------------------------------------------------------------------------------------
function validateForUpdateContact(form) {
    var loadedContactId;
    if (form.contactSelected.length == null) {
        loadedContactId = form.contactSelected.value;
    } else {
        for (i=0; i<form.contactSelected.length; i++){
            if (form.contactSelected[i].checked) {
                var loadedContactId = form.contactSelected[i].value;            
            }
        }
    }   
    var postalOrPhysId=form.currentContactSelectedId.value;
    if (loadedContactId != null){
        var row = document.getElementById(loadedContactId); 
    } else {
        var row = document.getElementById(postalOrPhysId);
    }
    var columns = row.getElementsByTagName('td'); 
    if (columns.length==5) {
        validateEmail();
    }       
    if (columns.length==8) {
        validatePhone();
    }       
    if (columns.length==11)  {
        validatePostal(); 
    }
    if (columns.length==14) {
        validatePhysical(); 
    }
    if (columns.length==13)  {
        validateUnstructured();
    }
} 

//------------------------------------------------------------------------------------------------------------
// EMAIL VALIDATION
//------------------------------------------------------------------------------------------------------------
function validateEmail() {
    var emailstr = document.forms[0].txtEmail.value;
    echeck(emailstr);
    var emailStartDate = document.forms[0].txtStartDateEmail.value;
    var emailEndDate  = document.forms[0].txtEndDateEmail.value;
    if (document.forms[0].txtEndDateEmail.value != '') {      
        validateStartAndEndDates(emailStartDate, emailEndDate);
    }
    document.forms[0].txtEndDateEmail.focus();
    //document.forms[0].txtEndDateNumber.value = '';
    //document.forms[0].txtEndDateEmail.value = '';
    //document.forms[0].txtEndDatePhysicalAddress.value = '';
    //document.forms[0].txtEndDatePostalAddress.value = '';
}

//------------------------------------------------------------------------------------------------------------
// PHONE VALIDATION
//------------------------------------------------------------------------------------------------------------
function validatePhone() {
    var contactTypeValue = document.forms[0].selContactPointType.value; 
    if (contactTypeValue != 'Mobile' && document.forms[0].editCPType.value != 'Mobile') {
    }   
    if (document.forms[0].txtLandLineCountryCode.value == '') {                        
        alert('Enter Country Code');
        document.forms[0].txtLandLineCountryCode.focus();
    }
    if (document.forms[0].txtLandLineAreaCode.value == '') {                        
        alert('Enter Area Code');
        document.forms[0].txtLandLineAreaCode.focus();
    }  
    if (document.forms[0].txtLandLineLocalNumber.value == '') {                        
        alert('Enter Number');
        document.forms[0].txtLandLineLocalNumber.focus();
    }             
    var cntryCodeValue = document.forms[0].txtLandLineCountryCode.value;
    var charposCC = validateNumerics(cntryCodeValue);
        if (charposCC >= 0) { 
            alert('Only Numerics allowed in Country Code!');
            document.forms[0].txtLandLineCountryCode.value = '';
            document.forms[0].txtLandLineCountryCode.focus();
        } 
    if (contactTypeValue != 'Mobile' && document.forms[0].editCPType.value != 'Mobile') {
        var areaCodeValue = document.forms[0].txtLandLineAreaCode.value;
        var charposAC = validateNumerics(areaCodeValue);
        validateNumerics(areaCodeValue);
        if (charposAC >= 0) {     
            alert('Only Numerics allowed in Area Code');
            document.forms[0].txtLandLineAreaCode.value = '';
            document.forms[0].txtLandLineAreaCode.focus();
        } 
    }
    var numbValue = document.forms[0].txtLandLineLocalNumber.value;
    var charposNB = validateNumerics(numbValue);
    validateNumerics(numbValue);
    if (charposNB >= 0) { 
        alert('Only Numerics allowed in Number');
        document.forms[0].txtLandLineLocalNumber.value = '';
        document.forms[0].txtLandLineLocalNumber.focus();
    }   
    var phoneStartDate = document.forms[0].txtStartDateNumber.value;
    var phoneEndDate  = document.forms[0].txtEndDateNumber.value;   
    if (document.forms[0].txtEndDateNumber.value != '') {   
        validateStartAndEndDates(phoneStartDate, phoneEndDate);
    }
    document.forms[0].txtEndDateNumber.focus();
}

//------------------------------------------------------------------------------------------------------------
// POSTAL VALIDATION
//------------------------------------------------------------------------------------------------------------
function validatePostal() {
    if (document.forms[0].txtPostalAddressCity.value == '...Type in City/Town' 
        || document.forms[0].txtPostalAddressCity.value == '') {
        document.forms[0].txtPostalAddressPostalCode.value = '';
        document.forms[0].txtPostalAddressCity.focus();
    }
    if (document.forms[0].txtPostalAddressPostalCode.value == '...Type in City/Town' 
        || document.forms[0].txtPostalAddressPostalCode.value == '') {
        document.forms[0].txtPostalAddressCity.value = '...Type in City/Town';
        document.forms[0].txtPostalAddressPostalCode.value = '';
        document.forms[0].txtPostalAddressCity.focus();
    }
    if (document.forms[0].txtPostalAddressCity.value == '' 
        || document.forms[0].txtPostalAddressPostalCode.value == '') {  
        if (document.forms[0].selPostalAddressCountryName.value == 'Belgium') {
            alert('Please type in City/Town name OR Postal Code');
            document.forms[0].txtPostalAddressPostalCode.focus();
        } else {
            if (document.forms[0].txtPostalAddressCity.value == '' 
                && document.forms[0].txtPostalAddressPostalCode.value == '') { 
                alert('Please enter City/Town name and Postal Code');
                document.forms[0].txtPostalAddressCity.focus();
            } else
            if (document.forms[0].txtPostalAddressCity.value == '' ) {
                alert('Please enter City/Town name');
                document.forms[0].txtPostalAddressCity.focus();
            } else 
            if (document.forms[0].txtPostalAddressPostalCode.value == '' ) {
                alert('Please enter Postal Code');
                document.forms[0].txtPostalAddressPostalCode.focus();
            }
        }       
    }
    var postalStartDate = document.forms[0].txtStartDatePostalAddress.value;
    var postalEndDate  = document.forms[0].txtEndDatePostalAddress.value;
    if (document.forms[0].txtEndDatePostalAddress.value != '') {      
        var result = validateStartAndEndDates(postalStartDate, postalEndDate);
        if (result == 1) {
            document.forms[0].txtEndDatePostalAddress.focus();
        }
    }
}

//------------------------------------------------------------------------------------------------------------
// UNSTRUCTURED VALIDATION
//------------------------------------------------------------------------------------------------------------
function validateUnstructured() {
    if (document.forms[0].txtUnstructuredAddressCity.value == '...Type in City/Town'
        || document.forms[0].txtUnstructuredAddressCity.value == '') {
        document.forms[0].txtUnstructuredAddressPostalCode.value = '';
        document.forms[0].txtUnstructuredAddressCity.focus();
    }
    if (document.forms[0].txtUnstructuredAddressPostalCode.value == '...Type in City/Town'
        || document.forms[0].txtUnstructuredAddressPostalCode.value == '') {
        document.forms[0].txtUnstructuredAddressCity.value = '...Type in City/Town';
        document.forms[0].txtUnstructuredAddressPostalCode.value = '';
        document.forms[0].txtUnstructuredAddressCity.focus();
    }
    if (document.forms[0].txtUnstructuredAddressCity.value == ''
        || document.forms[0].txtUnstructuredAddressPostalCode.value == '') {
        if (document.forms[0].selUnstructuredAddressCountryName.value == 'Belgium') {
            alert('Please type in City/Town name OR Postal Code');
            document.forms[0].txtUnstructuredAddressPostalCode.focus();
        } else {
            if (document.forms[0].txtUnstructuredAddressCity.value == ''
                && document.forms[0].txtUnstructuredAddressPostalCode.value == '') {
                alert('Please enter City/Town name and Postal Code');
                document.forms[0].txtUnstructuredAddressCity.focus();
            } else
            if (document.forms[0].txtUnstructuredAddressCity.value == '' ) {
                alert('Please enter City/Town name');
                document.forms[0].txtUnstructuredAddressCity.focus();
            } else
            if (document.forms[0].txtUnstructuredAddressPostalCode.value == '' ) {
                alert('Please enter Postal Code');
                document.forms[0].txtUnstructuredAddressPostalCode.focus();
            }
        }
    }
    var postalStartDate = document.forms[0].txtStartDateUnstructuredAddress.value;
    var postalEndDate  = document.forms[0].txtEndDateUnstructuredAddress.value;
    if (document.forms[0].txtEndDateUnstructuredAddress.value != '') {
        var result = validateStartAndEndDates(postalStartDate, postalEndDate);
        if (result == 1) {
            document.forms[0].txtEndDateUnstructuredAddress.focus();
        }
    }
}

//------------------------------------------------------------------------------------------------------------
// PHYSICAL VALIDATION
//------------------------------------------------------------------------------------------------------------
function validatePhysical() {
    var physicalStartDate = document.forms[0].txtStartDatePhysicalAddress.value;
    var physicalEndDate  = document.forms[0].txtEndDatePhysicalAddress.value;
    if (document.forms[0].txtEndDatePhysicalAddress.value != '') {      
        var result = validateStartAndEndDates(physicalStartDate, physicalEndDate);
        if (result == 1) {
            document.forms[0].txtEndDatePhysicalAddress.focus();
        }
    }   
}

//------------------------------------------------------------------------------------------------------------
// EMAIL FORMAT VALIDATION
//------------------------------------------------------------------------------------------------------------
function echeck(str) {
	
	var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var validEmail = regex.test(str);
	if (!validEmail) {
		alert("Invalid Email format");
		document.forms[0].txtEmail.focus();
	}
	return validEmail;
}   
//------------------------------------------------------------------------------------------------------------