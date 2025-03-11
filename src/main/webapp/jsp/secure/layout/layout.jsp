<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:html lang="en">
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/icons/sm.ico"/>

        <link rel="stylesheet" type="text/css"
              href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.10.4.custom.css"/>
        <link rel="stylesheet" type="text/css" media="screen"
              href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css"/>
        <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/main.css"/>

        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.10.4.min.js"></script>
        <smg:jQueryDatePickerScript/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>


        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/html5shiv.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/respond.min.js"></script>
        <![endif]-->

        <title>
            <tiles:importAttribute name="title"/>
            <fmt:message key="${title}"/>
        </title>

    </head>

    <body>

        <%-- Error Dialog --%>
    <c:if test="${!empty ROOT_CAUSE_STACK}">
        <div class="modal fade" id="rootCauseDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <h4 class="modal-title" id="myModalLabel">Root Cause</h4>
                    </div>
                    <div class="modal-body">
                        <pre><c:out value="${ROOT_CAUSE_STACK}"/></pre>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <tiles:insert attribute="header" />

    <div id="main" class="container-fluid">

        <logic:messagesPresent message="false">
            <div class="alert alert-danger alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>

                <html:messages id="message" message="false">
                    <p><c:out value="${message}"/></p>
                </html:messages>

                <c:if test="${!empty ROOT_CAUSE_STACK}">
                    <a href="#" data-toggle="modal" data-target="#rootCauseDialog" id="rootCauseDialogOpener"
                       class="alert-link">[<fmt:message
                            key="label.error.showrootcause"/>]</a>
                </c:if>
            </div>
        </logic:messagesPresent>

        <html:messages id="message" message="true">
            <div class="alert alert-success alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <c:out value="${message}" />
            </div>
        </html:messages>

        <%--Content--%>
        <tiles:insert attribute="content"/>

    </div>

     <%--Footer--%>
    <div class="footer">
        <div class="container">
            <p class="text-muted"><fmt:message key="page.footer.label.copyright" /></p>
        </div>
    </div>

    </body>
</html:html>