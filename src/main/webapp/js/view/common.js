/**
 * Toggle a section to hide and show
 * @param sectionName - the sectionName which we click
 * 
 * A corresponding div#sectionName should exist
 */
function toggleSection(sectionName, contextPath) {
	var sectionDiv = $("#" + $(sectionName).attr('name'));
	if ($(sectionDiv).is(":hidden")) {
		sectionDiv.slideDown(500);
		$(sectionName).attr('src', contextPath + '/images/icon_minus.gif');
	} else {
		sectionDiv.slideUp(500);
		$(sectionName).attr('src', contextPath + '/images/icon_plus.gif');
	}
}