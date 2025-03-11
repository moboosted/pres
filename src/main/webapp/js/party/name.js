var x = "0";

function checkNamesTickBoxEdit(form) {
	var id;
    if (form.personNameSelected.length == null) {
        id = form.personNameSelected.value;
    } else {
        for (i = 0; i < form.personNameSelected.length; i++) {
            if (form.personNameSelected[i].checked) {
                id = form.personNameSelected[i].value;
            }
        }
     } 	
	var defNameId = form.defaultPartyNameId.value;
	if (defNameId == x || id == null){			
		if (form.defaultPartyName.checked==false) {
			alert('The default name cannot be unset. Please edit another to be the default, or add a new default');
			form.defaultPartyName.checked=true;
			form.defaultPartyName.value='on';
		} 
	}
}

function checkNameType(form) {
	if (form.selNameType.value == 'Married Name') {
		form.selMaritalStatus.value = 'Married';
		if (form.selGender.value == 'Female') {
			form.selTitle.value = 'Mrs';
	}
}
}


function checkTitleMarried(form) {
	if (form.selTitle.value == 'Mrs') {
		form.selMaritalStatus.value = 'Married';		
		form.selNameType.value = 'Married Name';
	}
}


function populateSelectedNameType(form, nameType) {

	var nameTypeOptions = form.selNameType.getElementsByTagName("option");
	for (var i = 0; i < nameTypeOptions.length; i++) {
		if (nameType == nameTypeOptions[i].value) {
			nameTypeOptions[i].setAttribute('selected', 'selected');
		} else {
			nameTypeOptions[i].removeAttribute('selected');
		}
	}
}

function populateSelectedTitle(form, title) {

	var selTitleOptions = form.selTitle.getElementsByTagName("option");
	for (var i = 0; i < selTitleOptions.length; i++) {
		if (title == selTitleOptions[i].text) {
			selTitleOptions[i].setAttribute('selected', 'selected');
			form.setTitle.value = selTitleOptions[i].value;
		} else {
			selTitleOptions[i].removeAttribute('selected');
		}
	}
}

function loadNames(form) {
    var id;
    if (form.personNameSelected.length == null) {
        id = form.personNameSelected.value;
    } else {
        for (i = 0; i < form.personNameSelected.length; i++) {
            if (form.personNameSelected[i].checked) {
                id = form.personNameSelected[i].value;
            }
        }
     }
	x = id;
    var row = document.getElementById(id);
    var columns = row.getElementsByTagName('td');
    var defNameId = form.defaultPartyNameId.value;
    if (confirm('Edit this name? If Yes, press Edit Name Button when editing is complete')) {
		if (columns[0].lastChild != null) {
			var nameType = columns[0].lastChild.nodeValue;
			populateSelectedNameType(form,nameType);
			form.setAttribute.value = nameType;
			form.selNameType.setAttribute('readOnly', 'true');
		}
		if (columns[2].lastChild != null) {
			var firstName = columns[2].lastChild.nodeValue;
			form.txtFirstName.value = firstName;
		}
		if (columns[3].lastChild != null) {
			var middleName = columns[3].lastChild.nodeValue;
			form.txtMiddleName.value = middleName;
		} else {
			form.txtMiddleName.value = "";
		}
		if (columns[4].lastChild != null) {
			var surname = columns[4].lastChild.nodeValue;
			form.txtSurname.value = surname;
		}
		if (columns[5].lastChild != null) {
			var selSuffix = columns[5].lastChild.nodeValue;
			form.selSuffix.value = selSuffix;
		} else{
			form.selSuffix.value = "";
		}
		if (columns[6].lastChild != null) {
			var nameStartDate = columns[6].lastChild.nodeValue;
			form.nameStartDate.value = nameStartDate;
		}
		if (columns[7].lastChild != null) {
			var nameEndDate = columns[7].lastChild.nodeValue;
			form.nameEndDate.value = nameEndDate;
		} else{
			form.nameEndDate.value = "";
		}
		if (columns[1].lastChild != null) {
			var title = columns[1].lastChild.nodeValue;
			populateSelectedTitle(form, title);
		}
		if (defNameId == id){
			form.defaultPartyName.checked=true;
		} else {
			form.defaultPartyName.checked=false;
		}
	}
	if (form.defaultPartyName.checked==true) {
		form.defaultPartyName.value='on';
	} else {
		form.defaultPartyName.value='off';
	}
}

function namesTickBoxValue(form) {
	if (form.defaultPartyName.checked==true) {
		form.defaultPartyName.value='on';
	} else {
		form.defaultPartyName.value='off';
	}
}
