$(function () {

    function setSelectedNodeObjectReference(or) {
        selectedNodeObjectReference = or.replace(/_/g, ':');
    }

    function getSelectedNodeObjectReference() {
        return selectedNodeObjectReference;
    }

    function isCurrentlySelected(objectReference) {
        return objectReference.replace(/_/g, ':') === getSelectedNodeObjectReference();
    }

    /**
     * Drives the context menu for the claim navigator tree
     */
    function contextMenu(node) {
        // The default set of all items
        var tree = $('#claim-node-tree').jstree();

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
            }
        };
        return items;
    }

    $.ajax({
        url: contextPath + "/rest/claim-management/claim-tree/node/" + getSelectedNodeObjectReference(),
        type: "GET",
        dataType: "json",
        success: function (json) {

            processNode(json);

            var data = [];
            data.push(json);

            $('#claim-node-tree').jstree({
                "core": {
                    "data": data,
                    "multiple": false,
                    "check_callback" : true
                },
                "plugins": ["contextmenu"],
                "contextmenu": {
                    "items": contextMenu,
                    "select_node": false

                }
            }).on('changed.jstree', function (e, data) {
                var newlySelectedNodeObjectReference = data.instance.get_node(data.selected[0]).id;
                if (isCurrentlySelected(newlySelectedNodeObjectReference)) {
                    return;
                }
                setSelectedNodeObjectReference(newlySelectedNodeObjectReference);
                loadNodeContent(getSelectedNodeObjectReference());
            });

            loadNodeContent(getSelectedNodeObjectReference());

            function buildObjectReferenceForNode(node) {
                return node.objectReference.componentId + '_' + node.objectReference.typeId + '_' + node.objectReference.objectId;
            }

            function processNode(node) {

                // get it into the shape that jstree expects
                node.id = buildObjectReferenceForNode(node);
                node.text =
                    '<span class="claimtree-external-ref">' + node.externalReference + '</span>' +
                    (node.status ? '<span class="claimtree-status"> (' + node.status + ')' : '</span>');


                switch (node.classification) {
                    case 'CO':
                    case 'EC':
                    {
                        node.icon = "claimtree-icon-" + node.classification;
                    }
                }

                if (isCurrentlySelected(buildObjectReferenceForNode(node))) {
                    node.state = {
                        selected: true,
                        opened: true
                    };
                }

                node.li_attr = { "title": node.description ? node.description : "" };

                // now recursively process each node
                $.each(node.children, function (key, value) {
                    processNode(value);
                });
            }
        },
        error: function (xhr, status, errorThrown) {
            alert("An error occurred whilst building the claim tree structure.  Please see the logs for details");
            console.log("Error: " + errorThrown);
            console.log("Status: " + status);
            console.dir(xhr);
        }
    });

    /**
     * Load the content for a specific code into the content (main) pane.
     */
    function loadNodeContent(nodeObjectReference) {

        var loading = '<img src="' + contextPath + '/images/wait.gif"> Loading ...';
        $('#claim-node-content').html(loading);

        $.ajax({
            url: contextPath + "/secure/claim/claimnavigator/content.do?nodeObjectReference=" + getSelectedNodeObjectReference(),
            type: "GET",
            dataType: "text",
            success: function (resp) {
                $('#claim-node-content').html(resp);
            },
            error: function (xhr, status, errorThrown) {
                var alertMessage = 'An error occurred whilst loading the detail of ' + getSelectedNodeObjectReference() +
                    '.  Please see the logs for details';
                bootbox.dialog({
                    message: alertMessage,
                    title: "Error",
                    buttons: {
                        ok: {
                            label: "OK"
                        }
                    }
                });
                console.log("Error: " + errorThrown);
                console.log("Status: " + status);
                console.dir(xhr);
            }
        });
    }
});