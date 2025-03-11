<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/jstree/themes/default/style.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/browse.css"/>
<script src="${pageContext.request.contextPath}/js/jstree/jstree.min.js"></script>

<div class="container-fluid">
    <div class="row">
        <div id="types-node-tree" class="col-md-2">
            <img src='${pageContext.request.contextPath}/images/wait.gif'/> Loading ...
        </div>
    </div>
</div>

<!-- Modal Dialog -->
<div class="modal fade" id="informationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title"><fmt:message key="page.browsetypes.title.information" /></h4>
            </div>
            <div class="modal-body">
                <p id="informationMessage"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="page.browsetypes.button.ok" /></button>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">
	        <span aria-hidden="true">&times;</span>
	        <span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title" id="myModalLabel">Types</h4>
      </div>
      <div class="modal-body">
		    <form id="types-form" class="form-horizontal" role="form">

                <div class="form-group" id="formGroupID">
                    <label class="control-label col-sm-2"><fmt:message key="page.browsetypes.label.id" /></label>
                    <div class="col-sm-10">
                        <div class="input-group col-sm-10">
                            <input type="text" class="form-control" disabled="disabled" id="id" name="id" />
                        </div>
                    </div>  
                </div>

                <div class="form-group">
                    <label class="control-label col-sm-2"><fmt:message key="page.browsetypes.label.parentId" /></label>
                    <div class="col-sm-10">
                        <div class="input-group col-sm-10">
                            <input type="text" class="form-control" disabled="disabled" id="parentId" name="parentId" />
                        </div>
                    </div>  
                </div>

		        <div class="form-group">
		            <label class="control-label col-sm-2"><fmt:message key="page.browsetypes.label.name" /></label>
		            <div class="col-sm-10">
		                <div class="input-group col-sm-10">
		                    <input type="text" class="form-control" id="name" name="name"/>
		                </div>
		            </div>  
		        </div>

		        <div class="form-group">
		            <label class="control-label col-sm-2"><fmt:message key="page.browsetypes.label.description" /></label>
		            <div class="col-sm-10">
		                <div class="input-group col-sm-10">
		                    <input type="text" class="form-control" id="description" name="description" />
		                </div>
		            </div>  
		        </div>

		        <div class="form-group">
		            <label class="control-label col-sm-2"><fmt:message key="page.browsetypes.label.component" /></label>
		            <div class="col-sm-10">
		                <div class="input-group col-sm-10">
		                    <select class="form-control" id="component" name="component" onchange="repopulateClassNames()">
                                <option value="1">Common</option>
                                <option value="2">Account</option>
                                <option value="3">Financial Transaction</option>
                                <option value="4">Agreement</option>
                                <option value="5">Party</option>
                                <option value="6">Communication</option>
                                <option value="7">Activity Place Condition</option>
                                <option value="9">Rating</option>
                                <option value="10">Claim</option>
                                <option value="11">Physical Object</option>
                            </select>
		                </div>
		            </div>  
		        </div>

		        <div class="form-group">
		            <label class="control-label col-sm-2"><fmt:message key="page.browsetypes.label.classname" /></label>
		            <div class="col-sm-10">
		                <div class="input-group col-sm-10">
		                    <select class="form-control" id="className" name="className">
                            </select>
		                </div>
		            </div>  
		        </div>

                <div class="form-group" id=schemaNameGroup>
                    <label class="control-label col-sm-2"><fmt:message key="page.browsetypes.label.schemaname" /></label>
                    <div class="col-sm-10">
                        <div class="input-group col-sm-10">
                            <input type="text" class="form-control" id="schemaName" name="schemaName"/>
                        </div>
                    </div>  
                </div>

                <div class="form-group" id=tableNameGroup>
                    <label class="control-label col-sm-2"><fmt:message key="page.browsetypes.label.tablename" /></label>
                    <div class="col-sm-10">
                        <div class="input-group col-sm-10">
                            <input type="text" class="form-control" id="tableName" name="tableName"/>
                        </div>
                    </div>  
                </div>

                <div class="form-group" id=baseTableNameGroup>
                    <label class="control-label col-sm-2"><fmt:message key="page.browsetypes.label.basetablename" /></label>
                    <div class="col-sm-10">
                        <div class="input-group col-sm-10">
                            <input type="text" class="form-control" id="baseTableName" name="baseTableName"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                          <div class="checkbox">
                            <label>
                                <input type="checkbox" id="abstractCheckbox"><fmt:message key="page.browsetypes.label.abstract" />
                            </label>    
                        </div>
                    </div>
                </div>

               <%@include file="confirmdelete.jsp" %>

                <div id="modalFooter" class="modal-footer">
                    <div class="form-group">
                        <button type="button" id="saveButton" class="btn btn-primary"><fmt:message
                                key="page.browsetypes.button.save"/></button>
                        <button type="button" id="updateButton" class="btn btn-primary"><fmt:message
                                key="page.browsetypes.button.update"/></button>
                        <button type="button" id="deleteButton" data-toggle="modal"
                                data-target="#confirmDelete" class="btn btn-danger"><fmt:message
                                key="page.browsetypes.button.delete"/></button>
                        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message
                                key="page.browsetypes.button.close"/></button>
                    </div>
                </div>

		    </form>
	    </div>
      </div>

    </div>
  </div>

<script>
    var selectedTypeParentId;
    var contextPath = "${pageContext.request.contextPath}";

    function repopulateClassNames(){
        var componentElement = document.getElementById("component");
        var componentIndex = componentElement.options[componentElement.selectedIndex].value;
        var componentName = componentElement.options[componentElement.selectedIndex].innerHTML;
        var componentClasses;

        $.ajax({
            async: false,
            url: contextPath + "/rest/configuration-management/component/" + componentIndex,
            type: "GET",
            dataType: "json",
            success: function (resp) {
                componentClasses = resp;
            },
            error: function (xhr, status, errorThrown) {
                var errorMessage = 'An error occurred whilst loading the classes for ' + componentName +
                        '.  Please see the logs for details';
                showMessage(errorMessage);
                console.log("Error: " + errorThrown);
                console.log("Status: " + status);
                console.dir(xhr);
            }
        });

        var classNamesElement = document.getElementById("className");
        classNamesElement.options.length = 0;
        for(var i=0;i < componentClasses.classNames.length; i++){
            var option = document.createElement("option");
            option.value = componentClasses.classNames[i].name;
            option.innerHTML = componentClasses.classNames[i].name;
            classNamesElement.appendChild(option);
        }

    }
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/types/browse.js"></script>