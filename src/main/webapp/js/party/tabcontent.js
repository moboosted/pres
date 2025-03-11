//** Tab Content script- � Dynamic Drive DHTML code library (http://www.dynamicdrive.com)
//** Last updated: Nov 8th, 06

var enabletabpersistence=1; //enable tab persistence via session only cookies, so selected tab is remembered?
var tabcontentIDs={};

function expandcontent(linkobj){  
    var ulid=linkobj.parentNode.parentNode.id; //id of UL element
    //get list of LIs corresponding to the tab contents  
    var ullist=document.getElementById(ulid).getElementsByTagName("li");
    for (var i=0; i<ullist.length; i++) {
        ullist[i].className = ""  //deselect all tabs
        if (typeof tabcontentIDs[ulid][i] != "undefined") //if tab content within this array index exists (exception: More tabs than there are tab contents)
            if (document.getElementById(tabcontentIDs[ulid][i])) {
                document.getElementById(tabcontentIDs[ulid][i]).style.display = "none";//hide all tab contents
            }
    }
    linkobj.parentNode.className="active";  //highlight currently clicked on tab
    if (document.getElementById(linkobj.getAttribute("rel"))){
        document.getElementById(linkobj.getAttribute("rel")).style.display="block"; //expand corresponding tab content
    }
    if (linkobj.getAttribute("rel")=='general') {
        if (document.getElementById('partyType').textContent == 'Person') {
            if (document.getElementById('generalPerson').style.display == 'none') {     
                document.getElementById('generalPerson').style.display='block';
            }
        } else {
            if (document.getElementById('generalOrganisation').style.display == 'none') {     
                document.getElementById('generalOrganisation').style.display='block'; 
            }
        }        
    } 
    if (linkobj.getAttribute("rel")=='contact') {
        if (document.forms[0].selContactPointType != null) {     
            if (document.forms[0].selContactPointType.value != '-Select-' 
                && document.forms[0].selContactPointType.value != '') {
                if (document.forms[0].selContactPointType.value == 'Physical Address' ) {
                    showPhysicalContactFields();
                } else
                if (document.forms[0].selContactPointType.value == 'Postal Address' ) {
                    showPostalContactFields();
                }
                if (document.forms[0].selContactPointType.value == 'Unstructured Address' ) {
                    showUnstructuredContactFields();
                }
                document.getElementById('commonButtonAdd').style.display = 'block';
                document.getElementById('searchContactToLinkButton').style.display = 'block'; 
            } else //edit
            if (document.forms[0].physicalContactEdit.value == 'true'
              || document.forms[0].postalContactEdit.value == 'true'
              || document.forms[0].physAddrRegionChanged.value == 'true'      
              || document.forms[0].postAddrRegionChanged.value == 'true'
              || document.forms[0].unstructAddrRegionChanged.value == 'true') {
                postalPhysEditORModifyRegion();
                restoreUserChoicesExpandCollapse();
                document.forms[0].txtPhysicalAddressPostalCode.value = partyForm.txtPhysicalAddressCity.value;
                document.forms[0].txtPhysicalAddressCity.value = partyForm.txtPhysicalAddressPostalCode.value;
            }       
        }       
    }
    if (document.forms[0].partyIsReadOnly.value == 'true') {
        readOnlyDisplay(ullist);
        setFocusButtonBackOnly(linkobj.getAttribute("rel"));
    } else {
        setFocusButtonForTab(linkobj.getAttribute("rel"));
    }
    saveselectedtabcontentid(ulid, linkobj.getAttribute("rel"));
}

function expandtab(tabcontentid, tabnumber){ //interface for selecting a tab (plus expand corresponding content)   
    var thetab=document.getElementById(tabcontentid).getElementsByTagName("a")[tabnumber];
    if (thetab.getAttribute("rel")){
        expandcontent(thetab)
    }
    if (document.forms[0].duplicatesFound.value=='true') {
        var alertMessage = 'The Party you are capturing is potentially a duplicate of an existing Party in the database.\nIf you are positive this is not a duplicate go back to the General Page and click submit again.';
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
}

function savetabcontentids(ulid, relattribute){// save ids of tab content divs
    if (typeof tabcontentIDs[ulid]=="undefined"){ //if this array doesn't exist yet
        tabcontentIDs[ulid]=new Array()
    }
    tabcontentIDs[ulid][tabcontentIDs[ulid].length]=relattribute
}

function saveselectedtabcontentid(ulid, selectedtabid){ //set id of clicked on tab as selected tab id & enter into cookie
    if (enabletabpersistence==1){ //if persistence feature turned on
        setCookie(ulid, selectedtabid)
    }
}

function getullistlinkbyId(ulid, tabcontentid){ //returns a tab link based on the ID of the associated tab content
var ullist=document.getElementById(ulid).getElementsByTagName("li")
    for (var i=0; i<ullist.length; i++){
        if (ullist[i].getElementsByTagName("a")[0].getAttribute("rel")==tabcontentid){
            return ullist[i].getElementsByTagName("a")[0];
            break;
        }
    }
}

function initializetabcontent(){
    for (var i=0; i<arguments.length; i++){ //loop through passed UL ids
        if (enabletabpersistence==0 && getCookie(arguments[i])!="") //clean up cookie if persist=off
            setCookie(arguments[i], "")
        var clickedontab=getCookie(arguments[i]); //retrieve ID of last clicked on tab from cookie, if any
        var ulobj=document.getElementById(arguments[i]);
        var ulist=ulobj.getElementsByTagName("li"); //array containing the LI elements within UL

        for (var x=0; x<ulist.length; x++){ //loop through each LI element
            var ulistlink=ulist[x].getElementsByTagName("a")[0];
            if (ulistlink.getAttribute("rel")){
                savetabcontentids(arguments[i], ulistlink.getAttribute("rel")); //save id of each tab content as loop runs
                ulistlink.onclick=function(){
                expandcontent(this);
                return false;
            }

            if (ulist[x].className=="active" && clickedontab=="") //if a tab is set to be selected by default
                expandcontent(ulistlink); //auto load currenly selected tab content
            }
        } //end inner for loop

        if (clickedontab!=""){ //if a tab has been previously clicked on per the cookie value
        var culistlink=getullistlinkbyId(arguments[i], clickedontab);
            if (typeof culistlink!="undefined"){ //if match found between tabcontent id and rel attribute value
                expandcontent(culistlink); //auto load currenly selected tab content
            } else{ //else if no match found between tabcontent id and rel attribute value (cookie mis-association)
                expandcontent(ulist[0].getElementsByTagName("a")[0]); //just auto load first tab instead
            }
        }
    } //end outer for loop
}

function getCookie(Name){ 
    var re=new RegExp(Name+"=[^;]+", "i"); //construct RE to search for target name/value pair
    if (document.cookie.match(re)){ //if cookie found
        return document.cookie.match(re)[0].split("=")[1]; //return its value
    }
    return ""
}

function setCookie(name, value){
    document.cookie = name+"="+value; //cookie value is domain wide (path=/)
}
