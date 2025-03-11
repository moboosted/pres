var propertiesFormSelector = "#propertiesForm";
var deleteFormClicked;

var deleteButton = function(form){
    deleteFormClicked = form;
    var confirmMessage = $(deleteFormClicked.confirmMessage).val();
    var deleteLabel = $('#deleteLabel').val();
    var cancel = $('#cancelLabel').val();
    bootbox.dialog({
        message: confirmMessage,
        title:  deleteLabel,
        buttons: {
            delete: {
                label: deleteLabel,
                className: "btn-danger",
                callback: function () {
                    $(deleteFormClicked).find('input:button').attr('disabled', 'disabled');
                    doMainFormSubmitWithInitialForm($(deleteFormClicked), $(deleteFormClicked).find(":input[name='method']").val());
                }
            },
            cancel: {
                label: cancel
            }
        }
    });
};

/**
 * Checks whether a propertiesForm is associated with the page.  If not found, return true to indicate that the 
 * calling code needs to take action.  Otherwise append the input fields of the initial form to the 
 * properties form, include the initialMethodValue as a hidden field (initialMethod), submit the properties form, and 
 * return false
 * 
 * return - true if the original form should be submitted by the calling code.  
 *          false if the properties form will be submitted by this code (in which case the calling code 
 *          should not submit the form)
 */
function doMainFormSubmitWithInitialForm(initialForm, initialMethodValue){

    var propertiesForm = $(propertiesFormSelector);
    console.log(initialForm);

	if (propertiesForm.length == 0) {
		return true;
	}

    // we are submitting a form which will replace these values - delete them otherwise we end up with duplicates
    propertiesForm.find("input:hidden[name='path']").remove();
    propertiesForm.find("input:hidden[name='contextObjectReference']").remove();

	$(initialForm).find(":input:not(:button,:reset,:submit)").clone().css("display", "none").appendTo(propertiesFormSelector);
	
	if(initialMethodValue != undefined){
		propertiesForm.append("<input type='hidden' name='initialMethod' value='" + initialMethodValue + "' />");
	}
	
	propertiesForm.submit();
	return false;
}

/**
 * Include the initialUrlValue as a hidden field (initialUrl), and submit the form
 */
function doMainFormSubmitWithInitialUrl(initialUrlValue){
	if(initialUrlValue != undefined){
		$(propertiesFormSelector).append("<input type='hidden' name='initialUrl' value='" + initialUrlValue + "' />");
	}
	$(propertiesFormSelector).submit();
}

function registerFormsThatNeedToBeOverridden(formSelection){

	// if there are no properties, don't override any forms
	if ($(propertiesFormSelector).length == 0) {
		return false;
	}

 	$(formSelection).bind("submit", function(){

	    var initialMethodValue = $(this).find(":input[name='method']").val();
	    doMainFormSubmitWithInitialForm(this, initialMethodValue);
	    return false;
    });
}

function registerLinksThatNeedToBeOverridden(linkSelection){
	
	// if there are no properties, don't override any links
	if ($(propertiesFormSelector).length == 0) {
		return false;
	}

	$(linkSelection).bind("click", function(){
		var initialUrlValue = $(this).attr("href");
		if(initialUrlValue != undefined){
			doMainFormSubmitWithInitialUrl(initialUrlValue);
		}
		return false;
	});
}