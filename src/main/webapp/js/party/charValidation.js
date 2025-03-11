//------------------------------------------------------------------------------------------------------------
// CHAR VALIDATION
//------------------------------------------------------------------------------------------------------------

function emailCharacter(evt) {
    evt = (evt) ? evt : event;    
    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));
    if ((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122) || (charCode >=48  && charCode <=57 ) 
    || (charCode == 64) // @
    || (charCode == 45) // - dash
    || (charCode == 46) // . fullstop      
    || (charCode == 32) // space
    || (charCode == 8)  // Backspace
    || (charCode == 9)  // Tab
    || (charCode == 13) // Enter
    ) {
        return true;
    }          
    alert("Only alphabetic, numeric, @ and . characters allowed in this field.");
    return false;
}

function validateNumerics(objValue) { 
    var charpos = objValue.search("[^0-9]"); 
    return charpos;
}

function trim(stringToTrim) {
    return stringToTrim.replace(/^\s+|\s+$/g,"");
}

