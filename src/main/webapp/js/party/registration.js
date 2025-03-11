function showRegistrationAdd(form) {
    
    // reset and hide everything first
    resetRegistrationData(form);
    hideAll(); 
    
    var selected = $('#regTypes').children("option:selected");
    var divId = selected.data('divid');
    $("#" + divId).show();
    $("#addRegButton").show();
}

function loadPartyRegToEdit(form, typeId) {

    var id = $("input[name='regSelected']:checked").val();
    var row = document.getElementById('editReg' + id);
    var columns = row.getElementsByTagName('td');
    if (columns[0].lastChild != null) {
        form.txtPartyRegistrationDescription.value = columns[0].lastChild.nodeValue;    }
    if (columns[1].lastChild != null) {
        form.txtPartyRegistrationStartDate.value = columns[1].lastChild.nodeValue;
    }
    if (columns[2].lastChild != null) {
        form.txtPartyRegistrationEndDate.value = columns[2].lastChild.nodeValue;
    }
    if (columns[3].lastChild != null) {
        form.txtPartyRegistrationIssueDate.value = columns[3].lastChild.nodeValue;
    }
    showRegTypeForEdit('partyReg', typeId);
}

function loadAccredRegToEdit(form, typeId) {
  
    var id = $("input[name='regSelected']:checked").val();
    var row = document.getElementById('editReg' + id);
    var columns = row.getElementsByTagName('td');
    if (columns[1].lastChild != null) {
        $("#selAccreditationTypeId").val(columns[0].lastChild.nodeValue);
        $("#selAccreditationTypeId").attr('disabled', true);
    }
    if (columns[2].lastChild != null) {
        form.txtAccreditationRegistrationStartDate.value = columns[2].lastChild.nodeValue;
    }
    if (columns[3].lastChild != null) {
        form.txtAccreditationRegistrationEndDate.value = columns[3].lastChild.nodeValue;
    }
    if (columns[4].lastChild != null) {
        form.txtAccreditationRegistrationIssueDate.value = columns[4].lastChild.nodeValue;
    }
    showRegTypeForEdit('accredReg', typeId);
}

function loadBirthRegToEdit(form, typeId) {
	
	var id = $("input[name='regSelected']:checked").val();
    var row = document.getElementById('editReg' + id);
    var columns = row.getElementsByTagName('td'); 
    if (columns[0].lastChild != null) {
        form.txtBirthDate.value = columns[0].lastChild.nodeValue;
    }
    if (columns[1].lastChild != null) {
        form.txtBDRegisteringAuthority.value = columns[1].lastChild.nodeValue;
    }   
    if (columns[2].lastChild != null) {
        form.txtBDIssueDate.value = columns[2].lastChild.nodeValue;
    }   
    showRegTypeForEdit('birthReg', typeId);
}

function loadDeathRegToEdit(form, typeId) {
	var id = $("input[name='regSelected']:checked").val();   
    var row = document.getElementById('editReg' + id);
    var columns = row.getElementsByTagName('td'); 
    if (columns[1].lastChild != null) {
        form.txtDateOfDeath.value = columns[1].lastChild.nodeValue;     
    }
    if (columns[2].lastChild != null) {
        form.txtDateOfDeathRegisteringAuthority.value = columns[2].lastChild.nodeValue;
    }   
    if (columns[3].lastChild != null) {
        form.txtDateOfDeathRegistrationNumber.value = columns[3].lastChild.nodeValue;
    }  
    showRegTypeForEdit('deathReg', typeId);
}

function loadEducationRegToEdit(form, typeId) {
	var id = $("input[name='regSelected']:checked").val();
    var row = document.getElementById('editReg' + id);
    var columns = row.getElementsByTagName('td'); 
    if (columns[1].lastChild != null) {
        form.txtEducationIssueDate .value = columns[1].lastChild.nodeValue;
    }
    if (columns[2].lastChild != null) {
        form.txtEducationRegisteringAuthority.value = columns[2].lastChild.nodeValue;
    }
    if (columns[3].lastChild != null) {
        form.txtEducationRegistrationNumber.value = columns[3].lastChild.nodeValue;
    }   
    
    showRegTypeForEdit('educationReg', typeId);
}

function loadLicenceRegToEdit(form, typeId) {
	var id = $("input[name='regSelected']:checked").val();
    var row = document.getElementById('editReg' + id);
    var columns = row.getElementsByTagName('td'); 
    
    if (columns[1].lastChild != null) {
        form.txtDriversLicenceExternalReference.value = columns[1].lastChild.nodeValue;
    }
    if (columns[2].lastChild != null) {
        form.txtDriversLicenceIssueDate.value = columns[2].lastChild.nodeValue;
    }
    if (columns[3].lastChild != null) {
        form.txtDriversLicenceRenewalDate.value = columns[3].lastChild.nodeValue;   
    }
    if (columns[4].lastChild != null) {
        form.txtDriversLicenceEndDate.value = columns[4].lastChild.nodeValue;
    }   
    
    showRegTypeForEdit('driversLicenceReg', typeId);
}

function loadMarriageRegToEdit(form, typeId) {
	var id = $("input[name='regSelected']:checked").val();
    var row = document.getElementById('editReg' + id);
    var columns = row.getElementsByTagName('td'); 
    if (columns[1].lastChild != null) {
        form.txtMRRegistrationNumber.value = columns[1].lastChild.nodeValue;
    }
    if (columns[2].lastChild != null) {
        form.txtMRRegisteringAuthority.value = columns[2].lastChild.nodeValue;
    }
    if (columns[3].lastChild != null) {
        form.txtMaritalStartDate.value = columns[3].lastChild.nodeValue;
    }
    if (columns[4].lastChild != null) {
        form.txtMaritalEndDate.value = columns[4].lastChild.nodeValue;
    }
    
    showRegTypeForEdit('marriageReg', typeId);
}

function loadNationalRegPersonToEdit(form, typeId) {
	resetRegistrationData(form);
	var id = $("input[name='regSelected']:checked").val();  
    var row = document.getElementById('editReg' + id);
    var columns = row.getElementsByTagName('td'); 
   
    if (columns[1].lastChild != null) {
        form.txtIdentityNumber.value = columns[1].lastChild.nodeValue;      
    }
    if (columns[2].lastChild != null) {
        form.txtNRRegisteringAuthority.value = columns[2].lastChild.nodeValue;
    }
    if (columns[3].lastChild != null) {
        form.txtNRIssueDate.value = columns[3].lastChild.nodeValue;
    }
     if (columns[4].lastChild != null) {
        form.selNationalRegCountryName.value = columns[4].lastChild.nodeValue;
    }
    showRegTypeForEdit('nationalRegPerson', typeId);
}

function hideAll() {

    $("#partyReg").hide();
    $("#accredReg").hide();      
    $("#birthReg").hide();                
    $("#deathReg").hide();  
    $("#driversLicenceReg").hide();
    $("#educationReg").hide();    
    $("#marriageReg").hide();
    $("#nationalRegPerson").hide();
    $("#addRegButton").hide();
    $("#updateRegButton").hide();
}

function showRegTypeForEdit(divId, typeId) {
    
    hideAll();

    var attributeTypeSelection = $('#regTypes');
    var selRegistrationTypeForEdit = $('#selRegistrationTypeForEdit');
    attributeTypeSelection.val(typeId);
    selRegistrationTypeForEdit.val(typeId);
    attributeTypeSelection.attr('disabled', 'disabled');
    $("#" + divId).show();
    $("#updateRegButton").show();
}

/* Cancel the editing of a registration */
function cancelRegistrationEdit() {
    var attributeTypeSelection = $(document.forms[0].selRegistrationType);
    attributeTypeSelection.val('');
    
    attributeTypeSelection.attr('disabled', false);
    $("#selAccreditationTypeId").attr('disabled', false);
    
    $("input[name='regSelected']:checked").attr("checked", false);
    
    resetRegistrationData(document.forms[0]);
    hideAll();
}

function resetRegistrationData(form) {
    // accreditation
    form.selAccreditationTypeId.value='';
    form.txtAccreditationRegistrationStartDate.value = '';
    form.txtAccreditationRegistrationEndDate.value = '';
    form.txtAccreditationRegistrationIssueDate.value = '';
    // death
    form.txtDateOfDeathRegistrationNumber.value = '';
    form.txtDateOfDeathRegisteringAuthority.value = '';
    // education
    form.txtEducationRegistrationNumber.value = '';
    form.txtEducationRegisteringAuthority.value = '';
    form.txtEducationIssueDate.value = '';
    // drivers licence
    form.txtDriversLicenceExternalReference.value = '';
    form.txtDriversLicenceIssueDate.value = '';
    form.txtDriversLicenceEndDate.value = '';
    form.txtDriversLicenceRenewalDate.value = '';
    // marriage
    form.txtMRRegistrationNumber.value = '';
    form.txtMRRegisteringAuthority.value = '';
    form.txtMaritalStartDate.value = '';
    form.txtMaritalEndDate.value = '';
    // national
    form.txtIdentityNumber.value = '';
    form.txtNRRegisteringAuthority.value = '';
    form.txtNRIssueDate.value = '';
    // party registrations
    form.txtPartyRegistrationDescription.value = '';
    form.txtPartyRegistrationIssueDate.value = '';
    form.txtPartyRegistrationStartDate.value = '';
    form.txtPartyRegistrationEndDate.value = '';

}

//------------------------------------------------------------------------------------------------------------
function mandatories() {
    if (document.forms[0].selRegistrationType.value=='Identity Card') { 
        if (document.forms[0].txtIdentityNumber.value=='' || (document.forms[0].txtIdentityNumber.value.indexOf(" ") != -1)) { 
            alert('Please capture Identity number');
            var x = document.forms[0].txtIdentityNumber.value;
            document.forms[0].txtIdentityNumber.value = trim(x);
            document.forms[0].txtIdentityNumber.focus();
        }   
    } else
    if (document.forms[0].selRegistrationType.value=='Social Security') {   
        if (document.forms[0].txtIdentityNumber.value=='' || (document.forms[0].txtIdentityNumber.value.indexOf(" ") != -1)) {
            alert('Please capture Social security number');
            var x = document.forms[0].txtIdentityNumber.value;
            document.forms[0].txtIdentityNumber.value = trim(x);
            document.forms[0].txtIdentityNumber.focus();
        } 
    } else  
    if (document.forms[0].selRegistrationType.value==8110) { // passport  
        if (document.forms[0].txtIdentityNumber.value=='' || (document.forms[0].txtIdentityNumber.value.indexOf(" ") != -1)) {
            alert('Please capture Passport number');
            var x = document.forms[0].txtIdentityNumber.value;
            document.forms[0].txtIdentityNumber.value = trim(x);
            document.forms[0].txtIdentityNumber.focus();
        }   
    }       
}
//------------------------------------------------------------------------------------------------------------