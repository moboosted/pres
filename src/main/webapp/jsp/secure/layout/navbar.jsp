<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<nav role="navigation" class="navbar navbar-default navbar-inverse navbar-static-top navbar-custom">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a id="menu-home" href="${pageContext.request.contextPath}/secure/user/dashboard.do" class="navbar-brand"><fmt:message key="menu.dashboard"/></a>
        </div>
        <!-- Collection of nav links and other content for toggling -->
        <div id="navbarCollapse" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">

                <li class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#"><fmt:message key="menu.agreement" /> <b
                            class="caret"></b></a>
                    <ul role="menu" class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/secure/agreement/find.do?ccb=true"><fmt:message key="menu.agreement.find" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/product/find.do"><fmt:message key="menu.agreement.create" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/agreementstatistics.do"><fmt:message key="menu.agreement.agreementstatistics" /></a></li>
                        <li class="divider"></li>
                        <li><a href="${pageContext.request.contextPath}/secure/request/search.do?ccb=true"><fmt:message key="menu.request.find" /></a></li>
                    </ul>
                </li>

                <li class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#"><fmt:message key="menu.party" /> <b class="caret"></b></a>
                    <ul role="menu" class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/secure/party/findParty.do"><fmt:message key="menu.party.find" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/party/addParty.do"><fmt:message key="menu.party.add" /></a></li>
                    </ul>
                </li>

                <li class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#"><fmt:message key="menu.financials" /> <b class="caret"></b></a>
                    <ul role="menu" class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/secure/ftx/financialtransaction/find.do"><fmt:message key="menu.financials.findfinancialtransaction" /></a></li>
                        <li class="divider"></li>
                        <li><a href="${pageContext.request.contextPath}/secure/account/find.do"><fmt:message key="menu.financials.findaccount" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/account/accountmapping/find.do"><fmt:message key="menu.financials.findaccountmapping" /></a></li>
                        <li class="divider"></li>
                        <li><a href="${pageContext.request.contextPath}/secure/moneyscheduler/find.do"><fmt:message key="menu.financials.findmoneyscheduler" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/moneyscheduler/findmoneyschedulerstoexecute.do"><fmt:message key="menu.financials.findmoneyschedulerstoexecute" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/moneyscheduler/execute.do"><fmt:message key="menu.financials.executescheduler" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/moneyscheduler/execution/find.do"><fmt:message key="menu.financials.findmoneyschedulerexecutions" /></a></li>
                        <li class="divider"></li>
                        <li><a href="${pageContext.request.contextPath}/secure/trialbalance.do"><fmt:message key="menu.financials.trialbalance" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/ageingreport.do"><fmt:message key="menu.financials.ageingreport" /></a></li>
                    </ul>
                </li>

                <li class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#"><fmt:message key="menu.fund" /> <b class="caret"></b></a>
                    <ul role="menu" class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/secure/fund/search.do"><fmt:message key="menu.fund.find" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/fund/create.do"><fmt:message key="menu.fund.create" /></a></li>
                        <li class="divider"></li>
                        <li><a href="${pageContext.request.contextPath}/secure/fund/assetprices/find.do"><fmt:message key="menu.fund.findassetprice" /></a></li>
                    </ul>
                </li>

                <li class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#"><fmt:message key="menu.claim" /> <b class="caret"></b></a>
                    <ul role="menu" class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/secure/claim/lossevent/find.do"><fmt:message key="menu.claim.findlossevent" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/claim/lossevent/add.do"><fmt:message key="menu.claim.addlossevent" /></a></li>
                        <li class="divider"></li>
                        <li><a href="${pageContext.request.contextPath}/secure/claim/claimfolder/find.do"><fmt:message key="menu.claim.findclaimfolder" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/claim/elementaryclaim/find.do"><fmt:message key="menu.claim.findelementaryclaim" /></a></li>
                    </ul>
                </li>

                <li class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#"><fmt:message key="menu.product" /> <b class="caret"></b></a>
                    <ul role="menu" class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/secure/product/find.do"><fmt:message key="menu.product.find" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/product/upload.do"><fmt:message key="menu.product.upload" /></a></li>
                    </ul>
                </li>

                <li class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#"><fmt:message key="menu.configuration" /> <b
                            class="caret"></b></a>
                    <ul role="menu" class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/secure/types/browse.do"><fmt:message key="menu.types.browse" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/product/agreementstate/find.do"><fmt:message key="menu.product.manageagreementstates" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/publicholiday/search.do"><fmt:message key="menu.configuration.managepublicholidays" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/enumeration/viewEnumerations.do"><fmt:message key="menu.configuration.enumeration" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/configuration/flushCaches.do?method=.clearCache"><fmt:message key="menu.configuration.clear.cache" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/configuration/flushCaches.do?method=.clearMessageResources"><fmt:message key="menu.configuration.clear.messageresources" /></a></li>
                        <li class="divider"></li>
                        <li><a href="/batch-admin" target="_blank"><fmt:message key="menu.configuration.batch" /></a></li>
                        <li><a href="${pageContext.request.contextPath}/secure/help.do?method=.about"><fmt:message key="menu.help.about" /></a></li>
                    </ul>
                </li>

            </ul>
            <c:if test="${! empty pageContext.request.userPrincipal}">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#"><span class="glyphicon glyphicon-user">&nbsp;</span><c:out value="${userProfile.principal.name}" /> <b class="caret"></b></a>
                    <ul role="menu" class="dropdown-menu">
                        <li><a id="menu-profile" href="${pageContext.request.contextPath}/secure/user/preferences.do"><fmt:message key="menu.profile" /></a></li>
                        <li class="divider"></li>
                        <li><a id="menu-logout" href="${pageContext.request.contextPath}/logout.do"><fmt:message key="menu.logout" /></a></li>
                    </ul>
                </li>
            </ul>
            </c:if>
        </div>
    </div>
</nav>
