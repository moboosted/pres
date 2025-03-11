<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="shortcut icon" href="<%=request.getContextPath() %>/images/icons/sm.ico"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/css/style_sheet.css"/>
    <title><fmt:message key="page.403.title"/></title>

    <style type="text/css" media="all">

        html, body	{height:100%; margin:0; padding:0;}

        #floater	{height:50%; margin-bottom:-150px; position:relative;}
        #content	{clear:both; height:300px; position:relative; margin:0 auto; padding:20px; text-align: center}

    </style>

</head>
<body>

<c:url value="/images/noentry.gif" var="noEntryUrl"/>

<div id="floater">
</div>

<div id="content">
    <img src="${noEntryUrl}" style="width: 400px; height: 100px">
    <p><fmt:message key="page.403.label.notauthorized"/> <%=request.getUserPrincipal() == null ? "" : request.getUserPrincipal()%>.</p>

    <p><fmt:message key="page.403.label.contactsystemadmin"/></p>
</div>



</body>
</html>

