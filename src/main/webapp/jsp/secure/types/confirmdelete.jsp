<!-- Modal Dialog -->
<!-- <div class="modal fade" id="confirmDelete" role="dialog" aria-labelledby="confirmDeleteLabel" aria-hidden="true"> -->
<div class="modal fade" id="confirmDelete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">
	            <span aria-hidden="true">&times;</span>
	            <span class="sr-only">Close</span>
	        </button>
                <h4 class="modal-title"><fmt:message key="page.browsetypes.label.deletetype" /></h4>
            </div>
            <div class="modal-body">
                <p><fmt:message key="page.browsetypes.label.deletemessage" /></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="page.browsetypes.button.cancel" /></button>
                <button type="button" id="confirmDeleteButton" data-dismiss="modal" class="btn btn-danger"><fmt:message key="page.browsetypes.button.delete" /></button>
            </div>
        </div>
    </div>
</div>
