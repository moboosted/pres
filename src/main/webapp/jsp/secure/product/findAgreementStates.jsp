<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<jsp:useBean id="findAgreementStateForm" scope="request"
             type="com.silvermoongroup.fsa.web.product.FindAgreementStateForm"/>

<c:url var="agreementStateUrl" value="/secure/product/agreementstate.do" />


<div class="text-right">
<html:form action="/secure/product/agreementstate/find.do">
    <input type="hidden" name="method" value=".add" />
    <html:submit property="method" styleClass="btn btn-primary btn-sm">
        <fmt:message key="page.findagreementstate.action.add" />
    </html:submit>
</html:form>
</div>

<div class="table-responsive">
<display:table name="${findAgreementStateForm.agreementStates}" id="agreementStatesTable"
        decorator="com.silvermoongroup.fsa.web.product.AgreementStateTableDecorator">

    <display:column property="name" titleKey="spf.agreementstate.name" href="${agreementStateUrl}" paramId="id" paramProperty="id" />
    <display:column property="legallyBinding" titleKey="spf.agreementstate.legallybinding" />
    <display:column property="riskBearing" titleKey="spf.agreementstate.riskbearing" />

</display:table>
</div>

