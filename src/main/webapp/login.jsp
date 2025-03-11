<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>

    <!--  SMG Icon on Tabbed Browsers -->
    <link rel="shortcut icon" href="<%=request.getContextPath() %>/images/icons/sm.ico"/>
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/main.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>

    <title><fmt:message key="page.login.title"/></title>

    <style>
        body {
            background: #eee !important;
        }

        .btn-primary {
            background-color: #01073B;
        }

        h2 {
            text-align: center;
        }

        .wrapper {
            margin-top: 80px;
            margin-bottom: 80px;
        }

        .form-signin {
            max-width: 500px;
            padding: 15px 35px 35px;
            margin: 0 auto;
            background-color: #fff;
            border: 1px solid rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            text-align: center;
        }

        .form-signin .form-control {
            position: relative;
            font-size: 16px;
            height: auto;
            padding: 10px;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
        }

        .form-signin input[type="text"] {
            margin-bottom: -1px;
        }

        .form-signin input[type="password"] {
            margin-bottom: 20px;
        }

        #alsoOfferedIn {
            text-align: center;
            margin-top: 20px;
        }

    </style>

<head>
<body>

<jsp:useBean id="loginForm" scope="request" type="com.silvermoongroup.fsa.web.security.form.LoginForm"/>

<div class="wrapper">
    <form class="form-signin" action="${pageContext.request.contextPath}/login" method="post" id="loginForm">

        <img class="profile-img" src="${pageContext.request.contextPath}/images/silvermoon-logo.png" width="240px" alt="Silvermoon Logo">
        <h2 class="form-signin-heading"><fmt:message key="page.login.welcome"/></h2>

        <c:if test="${loginForm.failure}">
            <div class="alert alert-danger" role="alert">
                <fmt:message key="page.login.failed"/>
            </div>
        </c:if>

        <input type="text" name="username" id="usernameText" class="form-control" required="" autofocus=""
               placeholder='<fmt:message key="page.login.username" />'/>
        <input type="password" name="password" id="passwordText" class="form-control" required=""
               placeholder='<fmt:message key="page.login.password" />'/>
        <button class="btn btn-lg btn-primary btn-block" type="submit" id="loginButton"><fmt:message key="page.login.login" /></button>
        <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
    </form>

    <div id="alsoOfferedIn">
        <c:if test="${! empty loginForm.availableLocales}">
            <fmt:message key="page.login.offeredin"/>:&nbsp;&nbsp;&nbsp;&nbsp;

            <c:forEach items="${loginForm.availableLocales}" var="locale">
                <c:url value="/login.do" var="changeLocaleUrl">
                    <c:param name="method" value=".changeLocale"/>
                    <c:param name="locale" value="${locale.value}"/>
                </c:url>
                <a id="locale-${locale.value}" href="<c:out value="${changeLocaleUrl}" />"><c:out
                        value="${locale.label}"/></a>
                &nbsp;&nbsp;&nbsp;
            </c:forEach>

        </c:if>
    </div>
</div>

</body>

</html>
