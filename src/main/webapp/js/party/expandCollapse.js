var userChoicesStoreArray = [];
function switchMenuReg(obj, expand) {
    var el = document.getElementById(obj);
    var obj = el.id;
    var imageId = expand.id;
    // store user collapse expand choices for reset later
    userChoicesStoreArray.push(obj);
    userChoicesStoreArray.push(imageId);
    if (el.style.display != 'none') {
        el.style.display = 'none';
        expand.src = '/fsa/images/icon_plus.gif';
        userChoicesStoreArray.push('plus');
        document.forms[0].userChoicesStoreArray.value = userChoicesStoreArray;
        return(false);
    } else {
        el.style.display = '';
        expand.src = '/fsa/images/icon_minus.gif';
        userChoicesStoreArray.push('minus');
        document.forms[0].userChoicesStoreArray.value = userChoicesStoreArray;
        return(false);
    }
}

function change(id, newClass) {
    identity = document.getElementById(id);
    identity.className = newClass;
}