function setFocusButtonForTab(tabHighlighted) {
    if (tabHighlighted=='general' && 
            (document.getElementById('savePersonButton')!=null || document.getElementById('saveOrgButton')!=null)) { 
        if (document.getElementById('partyType').value == 8700) {             
            if (document.getElementById('savePersonButton')!=null) {
                document.getElementById('savePersonButton').focus();
            }
            if (document.getElementById('updateGeneralPersonButton')!=null) {
                document.getElementById('updateGeneralPersonButton').focus();
            } 
            if (document.getElementById('savePersonAndLinkButton')!=null) {
                document.getElementById('savePersonAndLinkButton').focus();
            }
        } else {
            if (document.getElementById('saveOrgButton')!=null) {
                document.getElementById('saveOrgButton').focus();
            }
            if (document.getElementById('updateOrgButton')!=null) {
                document.getElementById('updateOrgButton').focus();
            }           
            if (document.getElementById('saveOrganisationAndLinkButton')!=null) {
                document.getElementById('saveOrganisationAndLinkButton').focus();
            }               
        }
    } else if (tabHighlighted=='roles' && document.getElementById('rolesAddButton')!=null) {
        document.getElementById('rolesAddButton').focus();
    } else if (tabHighlighted=='contact' && document.getElementById('contactBackButton')!=null) {
        document.getElementById('contactBackButton').focus();   
    } else if (tabHighlighted=='bank' && document.getElementById('bankBackButton')!=null) {
        document.getElementById('bankBackButton').focus();  
    } else if (tabHighlighted=='registrations' && document.getElementById('registrationBackButton')!=null) {
        document.getElementById('registrationBackButton').focus();  
    }
}

function setFocusButtonBackOnly(tabHighlighted) {
    var buttonId = null;
    if (tabHighlighted == 'find') {
        buttonId = "searchPageBackButton";
    } else if (tabHighlighted=='search' ) {
        buttonId = "searchBackButton";
    } else if (tabHighlighted=='general') {     
        if (document.getElementById('partyType').value == 8700) {   
            buttonId = "generalBackButton";    
        } else {
            buttonId = "orgBackButton";
        }
    } else if (tabHighlighted=='roles') {
        buttonId = "rolesBackButton";
    } else if (tabHighlighted=='contact') {
        buttonId = "contactBackButton";
    } else if (tabHighlighted=='bank') {
        buttonId = "bankBackButton";
    } else if (tabHighlighted=='registrations') {
        buttonId = "registrationBackButton";
    }

    if (buttonId != null && $("#" + buttonId).length > 0) {
        $("#" + buttonId).focus();

    }
}

function readOnlyDisplay(ullist) {
    if (document.forms[0].partyIsReadOnly.value =='true') { 
        for (var i=0; i<ullist.length; i++){
            $("#partyType").attr("disabled", "disabled");

            $("#savePersonButton").hide();
            $("#updateGeneralPersonButton").hide();           
            $("#savePersonAndLinkButton").hide();
            $("#generalCancelButton").hide();            

            $("#personTitle").attr("disabled", "disabled");
            $("#personSuffix").attr("disabled", "disabled");
            $("#personGender").attr("disabled", "disabled");
            $("#personMaritalStatus").attr("disabled", "disabled");
            $("#personLanguage").attr("disabled", "disabled");
            $("#personEducationLevel").attr("disabled", "disabled");
            $("#personEmploymentStatus").attr("disabled", "disabled");

            // person name              
            $("#addNameButton").hide();
            $("#updateNameButton").hide();
            $("#personNameType").attr("disabled", "disabled");
            $("#personDefaultName").attr("disabled", "disabled");

            // organisation
            $("#saveOrgButton").hide();
            $("#updateOrgButton").hide();
            $("#orgCancelButton").hide();
            $("#saveOrganisationAndLinkButton").hide(); 

            $("#orgFullName").attr("disabled", "disabled"); 

            // roles  
            $("#rolesAddButton").hide();
            $("#rolesCancelButton").hide();  
            $("#partyLinkingRoles").attr("disabled", "disabled");
            $("#partyRelationshipRoles").attr("disabled", "disabled");

            // contacts       
            $("#contactCancelButton").hide();                
            $("#contactPreferenceTypes").attr("disabled", "disabled");              
            $("#roleTypeForCpointsLoad").attr("disabled", "disabled");      
            $("#contactPointTypes").attr("disabled", "disabled");   

            // bank
            $("#bankSaveButton").hide(); 
            $("#bankCancelButton").hide(); 

            $("#bankAccountTypes").attr("disabled", "disabled");        
            $("#defBankCheck").attr("disabled", "disabled");    
            $("#crCardExpDate").attr("disabled", "disabled");    

            // registrations
            $("#registrationCancelButton").hide();
            $("#regTypes").attr("disabled", "disabled");    

            // find party tab
            $("#selSearch").attr("disabled", "disabled");
            $("#orgTypes").attr("disabled", "disabled");            
            $("#searchPartyButtonPerson").hide();
            $("#searchPartyButtonOrg").hide();
            $("#searchPageCancelButton").hide();
        }
    }       
}