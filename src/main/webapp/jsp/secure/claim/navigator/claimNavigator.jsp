<link rel="stylesheet" href="${pageContext.request.contextPath}/js/jstree/themes/default/style.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/claim-navigator.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jstree/jstree.min.js"></script>

<jsp:useBean id="claimNavigatorForm" scope="request"
             type="com.silvermoongroup.fsa.web.claimmanagement.form.ClaimNavigatorForm"/>

<div class="container-fluid">
    <div class="row">
        <div id="claim-node-tree" class="col-md-2">
            <img src='${pageContext.request.contextPath}/images/wait.gif'/> Loading ...
        </div>

        <div id="claim-node-content" class="col-md-10">
            <img src='${pageContext.request.contextPath}/images/wait.gif'/> Loading ...
        </div>
    </div>
</div>

<script>
    var selectedNodeObjectReference = "${claimNavigatorForm.nodeObjectReference}";
    var contextPath = "${pageContext.request.contextPath}";
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/claim/navigator/claim-navigator.js"></script>

