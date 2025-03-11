<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<div class="mainlinkcss">
    <a href="<%=request.getContextPath()%>/secure/user/dashboard.do">
        <fmt:message key="menu.dashboard"/>
    </a>
</div>

<div class="mainlinkcss">
    <a href="<%=request.getContextPath()%>/secure/product/find.do">
        <fmt:message key="menu.agreement.create"/>
    </a>
</div>


<div class="mainlinkcss">
    <a href="<%=request.getContextPath()%>/secure/agreement/find.do?ccb=true" id="findAgreementMenuLink">
        <fmt:message key="menu.agreement.find"/>
    </a>
</div>

<div class="mainlinkcss">
	<span>
		<a href="<%=request.getContextPath()%>/secure/request/search.do?ccb=true"
           id="requestSearchMenuLink"><fmt:message key="menu.request.find"/></a>
	</span>
</div>

<div class="mainlinkcss">
      <span onMouseover="dropdownmenu(this, event, 'menu7')">
            <a href="#" onClick="return clickreturnvalue()"><fmt:message key="menu.party"/></a>
      </span>
</div>

<div id="menu7" class="anylinkcss">
    <a href="<%=request.getContextPath()%>/secure/partyAction.do?method=.findParty" id="findPartyMenuLink">
        <fmt:message key="menu.party.find"/>
    </a>

    <a href="<%=request.getContextPath()%>/secure/partyAction.do?method=.createParty" id="createPartyMenuLink">
        <fmt:message key="menu.party.create"/>
    </a>
</div>


<%-- Product menu --%>
<div class="mainlinkcss">
  <span title="Product" onMouseover="dropdownmenu(this, event, 'menu11')"> 
    <a href="#" onClick="return clickreturnvalue()"><fmt:message key="menu.product"/></a>
  </span>
</div>
<div id="menu11" class="anylinkcss">
    <a href="<%=request.getContextPath()%>/secure/product/find.do" title="Find a product"
       id="findProductMenuLink"><fmt:message key="menu.product.find"/></a>
    <a href="<%=request.getContextPath()%>/secure/product/upload.do" title="Upload a product"
       id="uploadProductMenuLink"><fmt:message key="menu.product.upload"/></a>
    <a href="<%=request.getContextPath()%>/secure/product/agreementstate/find.do" title="Agreement States"
       id="agreementStatesLink"><fmt:message key="menu.product.manageagreementstates"/></a>
</div>

<%-- Claim --%>
<div class="mainlinkcss">
  <span title="Claim" onMouseover="dropdownmenu(this, event, 'menu20')">
    <a href="#" onClick="return clickreturnvalue()"><fmt:message key="menu.claim"/></a>
  </span>
</div>
<div id="menu20" class="anylinkcss">
    <a href="<%=request.getContextPath()%>/secure/claim/claimfolder/find.do" title="Find a Claim Folder"
       id="findClaimFolderLink"><fmt:message key="menu.claim.findclaimfolder"/></a>
</div>

<%-- Fund functionality --%>
<div class="mainlinkcss">
  <span title="Fund Administration" onMouseover="dropdownmenu(this, event, 'menu9')">
    <a href="#" onClick="return clickreturnvalue()"><fmt:message key="menu.fund"/></a>
  </span>
</div>
<div id="menu9" class="anylinkcss">
    <a href="<%=request.getContextPath()%>/secure/fund/create.do" title="Create Fund"
       id="createFundMenuLink"><fmt:message key="menu.fund.create"/></a>
    <a href="<%=request.getContextPath()%>/secure/fund/search.do" title="Find Fund"
       id="findFundMenuLink"><fmt:message key="menu.fund.find"/></a>
    <a href="<%=request.getContextPath()%>/secure/fund/assetprices/find.do" title="Find an Asset Price"
       id="findAssetPricesMenuLink"><fmt:message key="menu.fund.findassetprice"/></a>
</div>

<%-- Financials --%>
<div class="mainlinkcss">
  <span title="Financials" onMouseover="dropdownmenu(this, event, 'menu12')"> 
    <a href="#" onClick="return clickreturnvalue()"><fmt:message key="menu.financials"/></a>
  </span>
</div>
<div id="menu12" class="anylinkcss">
    <a href="<%=request.getContextPath()%>/secure/account/find.do" title="Find an Account"
       id="findAccountLink"><fmt:message key="menu.financials.findaccount"/></a>
    <a href="<%=request.getContextPath()%>/secure/ftx/financialtransaction/find.do" title="Find a Financial Transaction"
       id="findFinancialTransactionLink"><fmt:message key="menu.financials.findfinancialtransaction"/></a>
    <a href="<%=request.getContextPath()%>/secure/moneyscheduler/find.do" title="Find a Money Scheduler"
       id="findMoneySchedulersLink"><fmt:message key="menu.financials.findmoneyscheduler"/></a>
    <a href="<%=request.getContextPath()%>/secure/moneyscheduler/findmoneyschedulerstoexecute.do" title="Find a Money Scheduler to Execute"
       id="findMoneySchedulersToExecuteLink"><fmt:message key="menu.financials.findmoneyschedulerstoexecute"/></a>
    <a href="<%=request.getContextPath()%>/secure/moneyscheduler/execute.do" title="Execute a Money Scheduler"
       id="executeSchedulerLink"><fmt:message key="menu.financials.executescheduler"/></a>
    <a href="<%=request.getContextPath()%>/secure/moneyscheduler/execution/find.do"
       title="Money Scheduler Execution History"
       id="moneySchedulerExecutionHistoryLink"><fmt:message key="menu.financials.findmoneyschedulerexecutions"/></a>
    <a href="<%=request.getContextPath()%>/secure/trialbalance.do" title="Run a Trial Balance"
       id="calculateTrialBalanceLink"><fmt:message key="menu.financials.trialbalance"/></a>
    <a href="<%=request.getContextPath()%>/secure/ageingreport.do" title="Run an Ageing Report"
       id="calculateAgeingReportLink"><fmt:message key="menu.financials.ageingreport"/></a>
    <a href="<%=request.getContextPath()%>/secure/account/accountmapping/find.do" title="Manage Account Mappings"
       id="findAccountMappingLink"><fmt:message key="menu.financials.findaccountmapping"/></a>
</div>

<%-- Configurations --%>
<div class="mainlinkcss">
  <span title="Configuration" onMouseover="dropdownmenu(this, event, 'menu13')">
    <a href="#" onClick="return clickreturnvalue()"><fmt:message key="menu.configuration"/></a>
  </span>
</div>

<div id="menu13" class="anylinkcss">
    <a href="<%=request.getContextPath()%>/secure/configuration/flushCaches.do?method=.clearCache" id="clearCacheMenuLink">
        <fmt:message key="menu.configuration.clear.cache"/>
    </a>
    <a href="<%=request.getContextPath()%>/secure/publicholiday/search.do" title="Manage Public Holidays"
       id="managePublicHolidayMenuLink"><fmt:message key="menu.configuration.managepublicholidays"/>
    </a>
    <a href="<%=request.getContextPath()%>/secure/types/browse.do" id="browseTypesMenuLink">
        <fmt:message key="menu.types.browse"/>
    </a>
    <a href="<%=request.getContextPath()%>/secure/enumeration/enumerationTypes.do" title="Enumeration Types"
       id="enumerationsTypeLink"><fmt:message key="menu.configuration.enumeration"/>
    </a>
</div>

<div class="mainlinkcss">
      <span onMouseover="dropdownmenu(this, event, 'menu10')">
            <a href="#" onClick="return clickreturnvalue()"><fmt:message key="menu.help"/></a>
      </span>
</div>
<div id="menu10" class="anylinkcss">
    <a href="<%=request.getContextPath()%>/secure/help.do?method=.about" id="aboutMenuLink">
        <fmt:message key="menu.help.about"/>
    </a>
</div>

<div class="end">&nbsp;</div>
