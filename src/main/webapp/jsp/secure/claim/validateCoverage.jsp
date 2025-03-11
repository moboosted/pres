<%@ page import="com.silvermoongroup.common.enumeration.EnumerationType" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="validateCoverageForm" scope="request"
             type="com.silvermoongroup.fsa.web.claimmanagement.form.ValidateCoverageForm"/>

<label class="groupHeading"><fmt:message key="page.validateCoverage.title"/></label>

<html:form action="/secure/claim/elementaryclaim/validate.do" styleId="findForm" styleClass="form-horizontal">
    <div class="row form-container">

        <div class="row">
            <div class="form-group">
                <label for="externalReference" class="col-md-2 control-label"><smg:fmt value="claim.elementaryclaim.validatecoverage.externalreference"/></label>
                <div class="col-md-2">
                    <td><html:text property="externalReference" readonly="true" styleId="externalReference" styleClass="form-control input-sm"/></td>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="insuredName" class="col-md-2 control-label"><smg:fmt value="claim.elementaryclaim.validatecoverage.insuredname"/></label>
                <div class="col-md-2">
                    <td><html:text property="insuredName" readonly="true" styleId="insuredName" styleClass="form-control input-sm"/></td>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="beneficiaryName" class="col-md-2 control-label"><smg:fmt value="claim.elementaryclaim.validatecoverage.beneficiaryname"/></label>
                <div class="col-md-2">
                    <td><html:text property="beneficiaryName" readonly="true" styleId="beneficiaryName" styleClass="form-control input-sm"/></td>
                </div>
            </div>
        </div>

    </div>

    <%--Buttons--%>
    <div class="row spacer">
        <div class="col-md-12 text-center">
            <div class="form-group">
                <input type="submit" name="method" value="Validate" class="btn btn-primary btn-sm">
                <input type="submit" name="method" value="Cancel" class="btn btn-primary btn-sm">
            </div>
        </div>
    </div>
</html:form>