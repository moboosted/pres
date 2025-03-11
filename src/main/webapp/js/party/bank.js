jQuery(document).ready(function() {
	
	var mode = jQuery('#bankTabMode').val();
	var key = jQuery('#key').val();

	if (mode == 'EDIT_MODE' || mode == 'ADD_MODE') {
        jQuery('#bank_capture').show('fast');
	} else {
        jQuery('#bank_capture').hide('fast')
	}
	
	if(key != '') {
		if (jQuery('#'+key).length > 0){
	            jQuery('input:radio[id='+key+']').attr('checked', true);
	    }
	}
	
	jQuery('input:radio[name=fadSelected]').click(function() {
		var uuid = jQuery(this).attr('id');
		var id = jQuery(this).val();
		var form = jQuery(this).closest('form');
		jQuery('#key').val(uuid);
		
		
		if (confirm('Edit Existing Bank detail?')) {
			jQuery('#bankTabMode').val('EDIT_MODE');
			jQuery('updateFadButton').show('fast');
			
			var action = form.attr('action');
			action = action+'?method=.loadBankDetailForEdit';
			form.attr('action', action);
			form.submit();
		}
		

	});

    jQuery('#crCardExpiryDate').blur(function() {
        if (jQuery('#key').val() != '') {
            if (jQuery(this).val() != '') { 
                if (jQuery(this).val().length==4) {               
                    var charposCR = validateNumerics(jQuery(this).val());     
                    if (charposCR >= 0) { 
                        alert('Invalid, please enter in the format YYMM');
                        jQuery(this).focus();
                    } else {            
                        var yy = jQuery(this).val().substring(0,2);
                        var mm = jQuery(this).val().substring(2,4);   
                        if (mm > 12) {
                            alert('Invalid month');
                            jQuery(this).focus();
                        }
                    }
                } else 
                    if (jQuery(this).val().length<4) {
                        alert('Invalid, please enter in the format YYMM');
                        jQuery(this).focus();
                }   
            } 
        }   
    });

});
