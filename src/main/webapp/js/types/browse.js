$(function () {

    /**
     * Create Tree View with Dynamic Load functionality
     */
    $.ajax({
        url: contextPath + "/rest/configuration-management/types",
        type: "GET",
        dataType: "json",
        success: function (json) {
        	processNode(json);

            var data = [];
            data.push(json);

        	$('#types-node-tree').jstree({
                "core": {
                    "data": data,
                    "multiple": false,
                    "check_callback" : true
                },
                "plugins": ["contextmenu", "sort"],
                "contextmenu": {
                    "items": contextMenu,
                    "select_node": true
                },
                'themes': {
                    'name': 'proton',
                    'responsive': true
                }
            });
        	/**
        	 * This function loads the children nodes dynamically.
        	 * This function is called when a user clicks on the dropdown image(load_node event).
        	 */
        	var loadNodeHandler = function(e, data){
        		// Load the children on the click load node event.
            	var newlySelectedParentTypeId = data.node.id;
            	if (newlySelectedParentTypeId == "#"){
            		// The initial content load. Ignore
            		return;
            	}
                setNewlySelectedParentTypeId(newlySelectedParentTypeId);
                var tmp = {};
                $.ajax({
                    async: false,
                    url: contextPath + "/rest/configuration-management/types/" + data.node.id,
                    type: "GET",
                    dataType: "json",
                    success: function (resp) {
                        tmp = resp;
                    },
                    error: function (xhr, status, errorThrown) {
                        var errorMessage = 'An error occurred whilst loading the detail of ' + getNewlySelectedParentTypeId() +
                            '.  Please see the logs for details';
                        showMessage(errorMessage);
                        console.log("Error: " + errorThrown);
                        console.log("Status: " + status);
                        console.dir(xhr);
                    }
                });
                processNode(tmp);

                //The load node is unbound so that no loadnode events are called when the create_node function is used.
                $('#types-node-tree').unbind("load_node.jstree");
                var children = tmp.children;
                $.each(children, function(key, value){
                	childNode = value;
                	$.extend(childNode, {'text': value.text, 'type': 'valid_child'});
                    $('#types-node-tree').jstree().create_node(data.node.id, childNode);
                });

                // Re-bind the load_node event
                $('#types-node-tree').bind("load_node.jstree", loadNodeHandler);
                data.node.state = {
                    loaded: true,
                    closed : true
                };
                // Open the node so that the loaded data/nodes are viewed
                $('#types-node-tree').jstree().open_node(data.node.id);
        	};
            $('#types-node-tree').bind("load_node.jstree", loadNodeHandler);
        },

        error: function (xhr, status, errorThrown) {
            var errorMessage = 'An error occurred whilst loading the detail of ' + getNewlySelectedParentTypeId() +
            '.  Please see the logs for details';
            showMessage(errorMessage);
            console.log("Error: " + errorThrown);
            console.log("Status: " + status);
            console.dir(xhr);
        }
    });


    /**
     * Clean up modal. Remove any information
     */
    var clearModalElements = function(){
        $('#types-form').bootstrapValidator('resetForm', true);
        $("#description").val("");
        $("#id").val("");
    };

	/**
     * Drives the context menu(on right click) for the types tree
     */
    function contextMenu(node) {

        var populateModalElements = function(node){
        	$("#id").val(node.original.id);
        	$("#parentId").val(node.original.parentId);
            $("#name").val(node.original.name);
            $("#description").val(node.original.description);
            $("#component").val(node.original.componentId);
            repopulateClassNames();
            $("#className").val(node.original.qualifiedClassName);
            $("#schemaName").val(node.original.schemaName);
            $("#tableName").val(node.original.tableName);
            $("#baseTableName").val(node.original.baseTableName);

            var viewState = (node.original.readOnly == true ? "View" : "Edit");
            prepareElements(node.original.abstractType, viewState);
        };

        // The default set of all items
        var tree = $('#types-node-tree').jstree();

        var items = {
            expandNode: {
                label: "Expand all",
                action: function () {
                    tree.open_all(node);
                },
                _disabled: (tree.is_leaf(node) || tree.is_open(node))
            },
            collapseNode: {
                label: "Collapse all",
                action: function () {
                    tree.close_all(node);
                },
                _disabled: (tree.is_leaf(node) || !tree.is_open(node))
            },
            addNode: {
                label: "Create Child",
                action: function () {
                	$('#types-node-tree').jstree().open_node(node.id);
                    $("#myModal").modal('show');
                    $("#types-form").addClass("form-horizontal");
                    prepareElements(true, "Create");

                    var parentNode = $('#types-node-tree').jstree(true).get_node(node.parent);
                    if (parentNode.id != "#"){
                        $("#parentId").val(node.original.id);
                        $("#component").val(node.original.componentId);
                        repopulateClassNames();
                        $("#className").val(node.original.qualifiedClassName);
                        if (!node.original.abstractType) {
                            $("#schemaName").val(node.original.schemaName);
                            $("#tableName").val(node.original.tableName);
                            $("#baseTableName").val(node.original.baseTableName);
                        }
                    }

                }
            },

            updateNode: {
                label: (node.original.readOnly === true ? "View": "Edit"),
                action: function () {
                    $('#types-node-tree').jstree().open_node(node.id);
                	$("#myModal").modal('show');
                	$("#types-form").addClass("form-horizontal");
                    populateModalElements(node);
                }
                //_disabled: (node.original.readOnly === true)
            }
        };
        return items;
    }

    /**
     * Process the tree node so as to set the state/view/look and feel as required.
     */
    function processNode(node) {
        // get it into the shape that jstree expects
        node.text =
            '<span class="typestree-external-ref">' + node.name + '</span>' +
            (node.componentName ? '<span class="typestree-status"> (' + node.componentName + ')' : '</span>');

        if (isCurrentlySelected(node.id)) {
            node.state = {
                selected: true,
                opened: true
            };
        }

        node.li_attr = { "title": node.name ? node.name : "" };
        if (node.children) {
            $.each(node.children, function(key, value){
            	processNode(value);
            });
        } else if (node.hasChildren) {
        	node.children = true;
        	node.state = {
    			closed : true,
    			loaded : true
        	};
        }
    }
    /**
	 * Utility methods to manage the selection of Nodes within a tree based on Type Parent id
	 */
	function setNewlySelectedParentTypeId(nodeId) {
        selectedTypeParentId = nodeId;
    }

    function getNewlySelectedParentTypeId() {
        return selectedTypeParentId;
    }

    function isCurrentlySelected(nodeId) {
        return nodeId === getNewlySelectedParentTypeId();
    }

    /**
     * Custom validator for bootstrap modals.
     *
     */
    $('#types-form').bootstrapValidator({
        message: 'This value is not valid',
        fields: {
            description: {
                validators: {

                }
            },

            name: {
                validators: {
                    notEmpty: {

                    }
                }
            },

            component: {
                validators: {
                    integer: {
                        message: 'The component should be a numerical value.'
                    },
                    notEmpty: {

                    }
                }
            },

            className: {
                validators: {
                    notEmpty: {

                    }
                }
            },

            schemaName: {
                validators: {
                    notEmpty: {

                    }
                }
            },

            tableName: {
                validators: {
                    notEmpty: {

                    }
                }
            },

            baseTableName: {
                validators: {
                    notEmpty: {

                    }
                }
            }
        }
    });

    /**
     * Show the appropriate input fields as per the state of the abstract checkbox.
     */
    $('#abstractCheckbox').change(function () {
        prepareElements($(this).is(':checked'));
    });

    /**
	 * This function is responsible for hiding and viewing elements based on state
	 * 
	 * isAbstract - If the type is abstract do not show certain fields
	 * viewState - Enable buttons/fields according to the view state (Create/Edit)
	 */
    var prepareElements = function (isAbstract, viewState) {
        switch(viewState) {
            case "Create":
            	$("#formGroupID").hide();
                $("#saveButton").show();
                $("#updateButton").hide();
                $("#deleteButton").hide();
                disableElements(false);
                $("#modalFooter").show();
                break;
            case "Edit":
            	$("#formGroupID").show();
                $("#saveButton").hide();
                $("#updateButton").show();
                $("#deleteButton").show();
                disableElements(false);
                $("#modalFooter").show();
                break;
            case "View":
            	disableElements(true);
                $("#modalFooter").hide();
                break;
            default:
                break;
        }

        if (isAbstract) {
            $("#abstractCheckbox").prop('checked' , true);
            $("#schemaNameGroup").hide();
            $("#tableNameGroup").hide();
            $("#baseTableNameGroup").hide();
        }
        else {
            $("#abstractCheckbox").prop('checked' , false);
            $("#schemaNameGroup").show();
            $("#tableNameGroup").show();
            $("#baseTableNameGroup").show();
        }
    };
    
    /**
     * disabled - true or false
     */
    var disableElements = function (disabled) {
    	$("#name").prop('disabled', disabled);
    	$("#description").prop('disabled', disabled);
    	$("#component").prop('disabled', disabled);
    	$("#className").prop('disabled', disabled);
    	$("#schemaName").prop('disabled', disabled);
    	$("#tableName").prop('disabled', disabled);
    	$("#baseTableName").prop('disabled', disabled);
    	$("#abstractCheckbox").prop('disabled', disabled);
    };

    var buildJson = function() {
        var isAbstractChecboxChecked = ($("#abstractCheckbox").is(':checked')) ? "true" : "false";
        var type = {
            id: $("#id").val(),
            name: $("#name").val(),
            description: $("#description").val(),
            qualifiedClassName: $("#className").val(),
            componentId: $("#component").val(),
            schemaName: (isAbstractChecboxChecked == "false") ? $("#schemaName").val() : null,
            tableName: (isAbstractChecboxChecked == "false") ? $("#tableName").val() : null,
            baseTableName: (isAbstractChecboxChecked == "false") ? $("#baseTableName").val() : null,
    		abstractType: isAbstractChecboxChecked
        };
        return JSON.stringify(type);
    };

    /**
    *
    */
   $('#saveButton').click(function(event){
       $('#types-form').bootstrapValidator('validate');
       if(!$('#types-form').data('bootstrapValidator').isValid()){
       	// Validate the form, do not allow user to Save if any errors

       }
       else{
           var parentNodeId = $('#types-node-tree').jstree('get_selected');
           var parentId = $('#types-node-tree').jstree().get_node(parentNodeId).original.id;
       	    // All data is filled in, save the Type
           $.ajax({
               async: false,
               url: contextPath + "/rest/configuration-management/types/" + parentId,
               type: "POST",
               data: buildJson(),
               contentType: "application/json; charset=UTF-8",
               dataType: "json",
               success: function (resp) {
                   var tmp = resp.typeDTO;
                   processNode(tmp);
                   $.extend(tmp, {'text': tmp.text, 'type': 'valid_child'});
                   $('#types-node-tree').jstree().create_node(parentNodeId, tmp);
                   // Open the node so that the loaded data/nodes are viewed
                   $('#types-node-tree').jstree().open_node(parentNodeId);

                   showMessage(resp.message);
               },
               error: function (xhr, status, errorThrown) {
            	   var errorMessage;
            	   if (xhr.responseJSON.message != "") {
            		   errorMessage = xhr.responseJSON.message;
            	   }
            	   else{
            		   errorMessage = 'An error occurred whilst creating ' + $("#name").val() +
                       '. Please see the logs for details';
            	   }
            	   showMessage(errorMessage);
                   console.log("Error: " + errorThrown);
                   console.log("Status: " + status);
                   console.dir(xhr);
               }
           });
       }
   });


    /**
     *
     */

    $('#confirmDeleteButton').click(function (event) {
        var nodeId = $('#types-node-tree').jstree('get_selected');
        // All data is filled in, save the Type
        $.ajax({
            async: false,
            url: contextPath + "/rest/configuration-management/types/" + nodeId,
            type: "DELETE",
            dataType: "json",
            success: function (resp) {
                $('#types-node-tree').jstree().delete_node(nodeId);
                showMessage(resp.message);
            },
            error: function (xhr, status, errorThrown) {
         	   var errorMessage;
        	   if (xhr.responseJSON.message != "") {
        		   errorMessage = xhr.responseJSON.message;
        	   }
        	   else{
        		   errorMessage = 'An error occurred whilst deleting ' + $("#name").val() +
                   '.  Please see the logs for details';
        	   }
            	showMessage(errorMessage);
                console.log("Error: " + errorThrown);
                console.log("Status: " + status);
                console.dir(xhr);
            }
        });
    });

    /**
     *
     */
    $('#updateButton').click(function(event){
        $('#types-form').bootstrapValidator('validate');
        if(!$('#types-form').data('bootstrapValidator').isValid()){
            // Validate the form, do not allow user to Save if any errors

        }
        else{
            var nodeId = $('#types-node-tree').jstree('get_selected');
            var updateJson = buildJson();
            // Update Type
            $.ajax({
                async: false,
                url: contextPath + "/rest/configuration-management/types/" + nodeId,
                type: "PUT",
                data: updateJson,
                contentType: "application/json; charset=UTF-8",
                dataType: "json",
                success: function (resp) {
                    var nodeId = $('#types-node-tree').jstree('get_selected');
                    var oldNode = $('#types-node-tree').jstree().get_node(nodeId);
                    var tmp = resp.typeDTO;

                    processNode(tmp);
                    $.extend(tmp, {'text': tmp.text, 'type': 'valid_child'});

                    $('#types-node-tree').jstree().rename_node(oldNode, tmp.text);

                    oldNode.text = tmp.text;
                    oldNode.li_attr.title = tmp.li_attr.title;
                    oldNode.original.abstractType = tmp.abstractType;
                    oldNode.original.baseTableName= tmp.baseTableName;
                    oldNode.original.name = tmp.name;
                    oldNode.original.tableName = tmp.tableName;
                    oldNode.original.schemaName = tmp.schemaName;
                    oldNode.original.children = tmp.children;
                    oldNode.original.componentId = tmp.componentId;
                    oldNode.original.componentName = tmp.componentName;
                    oldNode.original.description = tmp.description;
                    oldNode.original.hasChildren = tmp.hasChildren;
                    oldNode.original.qualifiedClassName = tmp.qualifiedClassName;

                    showMessage(resp.message);
                },
                error: function (xhr, status, errorThrown) {
              	   var errorMessage;
            	   if (xhr.responseJSON.message != "") {
            		   errorMessage = xhr.responseJSON.message;
            	   }
            	   else{
            		   errorMessage = 'An error occurred whilst updating the type ' + $("#name").val() + '.  Please see the logs for details';
            	   }
            	   showMessage(errorMessage);
            	   console.log("Error: " + errorThrown);
                   console.log("Status: " + status);
                   console.dir(xhr);
                }
            });
        }
    });

    $('#myModal').on('hide.bs.modal', function (e) {
        clearModalElements();
    });

    var showMessage = function(message){
        $('.modal').modal('hide');
    	$("#informationMessage").text(message);
        $("#informationModal").modal('show');
    };
});