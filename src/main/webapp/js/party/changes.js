// called when user clicks on party tabs to check for unsaved party changes
function generalTabClicked() {
    document.forms[0].tabToChangeTo.value = 0;    
}

function relationshipsTabClicked() {
    document.forms[0].tabToChangeTo.value = 1;

    if (document.forms[0].relationshipsTabLoaded.value == 'false') {
        var pg = '/fsa/secure/partyAction.do';
        var actn = '';
        actn = pg +'?method=.tabChange';
        document.forms[0].action=actn;
        document.forms[0].submit();
    }
}

function rolesTabClicked() {
    document.forms[0].tabToChangeTo.value = 2;
    
    if (document.forms[0].rolesTabLoaded.value == 'false') {    
        var pg = '/fsa/secure/partyAction.do';
        var actn = '';
        actn = pg +'?method=.tabChange';
        document.forms[0].action=actn;           
        document.forms[0].submit();
    }   
}

function contactTabClicked() {
    document.forms[0].tabToChangeTo.value = 3;
    
    if (document.forms[0].contactTabLoaded.value == 'false') {
        var pg = '/fsa/secure/partyAction.do';
        var actn = '';
        actn = pg +'?method=.tabChange';
        document.forms[0].action=actn;       
        
        document.forms[0].submit();
    }       
}

function bankTabClicked() {
    document.forms[0].tabToChangeTo.value = 4;
    
    //always need to load bank tab 
    if (document.forms[0].bankTabLoaded.value == 'false') {        
        var pg = '/fsa/secure/partyAction.do';
        var actn = '';
        actn = pg +'?method=.tabChange';
        document.forms[0].action=actn;      
        
        document.forms[0].submit();
    }
}

function registrationTabClicked() {
    document.forms[0].tabToChangeTo.value = 5;
    
    if (document.forms[0].regTabLoaded.value == 'false') {        
        var pg = '/fsa/secure/partyAction.do';
        var actn = '';
        actn = pg +'?method=.tabChange';
        document.forms[0].action=actn;       
                
        document.forms[0].submit();
    }               
}

function findTabClicked() {
    document.forms[0].tabToChangeTo.value = 6;
}

function searchTabClicked() {
}