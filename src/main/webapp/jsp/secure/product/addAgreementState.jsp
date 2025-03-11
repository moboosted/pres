<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html:form action="/secure/product/agreementstate/add.do" styleId="updateAgreementStateForm" styleClass="form-horizontal">

    <input type="hidden" name="method" id="method" value=""/>
    <html:hidden property="id"/>

    <div class="form-group">
        <label for="name" class="col-md-2 control-label"><fmt:message key="spf.agreementstate.name"/></label>

        <div class="col-md-3">
            <html:text property="name" styleId="name" styleClass="form-control input-sm required"
                       errorStyleClass="form-control input-sm required has-error"/>
        </div>
    </div>


    <div class="form-group">
        <label for="legallyBinding" class="col-md-2 control-label"><fmt:message
                key="spf.agreementstate.legallybinding"/></label>

        <div class="col-md-3">
            <label class="radio-inline">
                <html:radio property="legallyBinding" value="true" styleId="legallyBinding"
                            errorStyleClass="has-error"/><fmt:message key="label.yesno.true" />
            </label>
            <label class="radio-inline">
                <html:radio property="legallyBinding" value="false" styleId="legallyBinding"
                            errorStyleClass="has-error"/><fmt:message key="label.yesno.false" />
            </label>
        </div>
    </div>

    <div class="form-group">
        <label for="riskBearing" class="col-md-2 control-label"><fmt:message
                key="spf.agreementstate.riskbearing"/></label>

        <div class="col-md-3">
            <label class="radio-inline">
                <html:radio property="riskBearing" value="true" styleId="riskBearing"
                            errorStyleClass="has-error"/>Yes
            </label>
            <label class="radio-inline">
                <html:radio property="riskBearing" value="false" styleId="riskBearing"
                            errorStyleClass="has-error"/>No
            </label>
        </div>
    </div>


    <div class="form-group">
        <div class="col-md-offset-2 col-md-10">
            <html:submit onclick="$('#method').val('.add');" styleClass="btn btn-primary btn-sm">
                <fmt:message key="page.addagreementstate.action.add"/>
            </html:submit>
            <html:submit onclick="$('#method').val('.back');" styleClass="btn btn-link btn-sm">
                <fmt:message key="label.cancel"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>
</html:form>
