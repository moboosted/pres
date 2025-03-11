<%@ page import="org.hibernate.internal.util.StringHelper" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<div class="row form-container">
    <%
        if(addLossEventForm.isEditing()) {
    %>
        <div class="row">
            <div class="form-group">
                <label for="externalReference" class="col-md-2 control-label"><smg:fmt value="claim.lossevent.externalreference"/></label>
                <div class="col-md-2">
                    <td><html:text property="externalReference" readonly="true" styleId="externalReference" styleClass="form-control input-sm"/></td>
                </div>
            </div>
        </div>
    <%
        }
    %>
        <div class="row">
            <div class="form-group">
                <label for="name" class="col-md-2 control-label"><smg:fmt value="claim.lossevent.name"/></label>
                <div class="col-md-2">
                    <html:text property="lossEventName" styleId="name" styleClass="form-control input-sm required" errorStyleClass="form-control input-sm required has-error"/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="description" class="col-md-2 control-label"><smg:fmt value="claim.lossevent.description"/></label>
                <div class="col-md-5">
                    <html:text property="description" styleId="description" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="lossEventTime" class="col-md-2 control-label"><smg:fmt value="claim.lossevent.losseventtime"/></label>
                <div class="col-md-2">
                    <html:text property="lossEventTime" styleId="lossEventTime" styleClass="form-control input-sm required" errorStyleClass="form-control input-sm required has-error"/>
                    <script type="text/javascript">
                        $(function(){
                            $('*[name=lossEventTime]').appendDtpicker({
                                "autodateOnStart": false,
                                "dateFormat": "DD MM YYYY hh:mm"
                            });
                        });
                    </script>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="typeId" class="col-md-2 control-label"><fmt:message key="label.type"/></label>

                <div class="col-md-2">
                    <html:select property="typeId" multiple="false" styleId="typeId"
                                 styleClass="form-control input-sm required">
                        <smg:categorizedTypes categoryName="Claimable Peril Types"/>
                    </html:select>
                </div>
            </div>
        </div>

    <%
        if(addLossEventForm.isEditing()) {
    %>

        <div class="row">
            <div class="form-group">
                <label for="victimName" class="col-md-2 control-label"><smg:fmt value="claim.lossevent.victimname"/></label>
                <div class="col-md-2">
                    <td><html:text property="victimName" readonly="true" styleId="victimName" styleClass="form-control input-sm"/></td>
                </div>
                <input type="submit" name="method" value="Add Victim" class="btn btn-primary btn-sm">
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="claimClaimantName" class="col-md-2 control-label"><smg:fmt value="claim.lossevent.claimclaimantname"/></label>
                <div class="col-md-2">
                    <td><html:text property="claimClaimantName" readonly="true" styleId="claimClaimantName" styleClass="form-control input-sm"/></td>
                </div>
                <input type="submit" name="method" value="Add Claimant" class="btn btn-primary btn-sm">
             </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="agreementNumber" class="col-md-2 control-label"><smg:fmt value="claim.lossevent.agreementnumber"/></label>
                <div class="col-md-2">
                    <td><html:text property="agreementNumber" readonly="true" styleId="agreementNumber" styleClass="form-control input-sm"/></td>
                </div>
                <input type="submit" name="method" value="Add Agreement" class="btn btn-primary btn-sm">
            </div>
        </div>

        <c:url value="/secure/claim/claimnavigator.do" var="claimNavigatorUrl">
            <c:param name="nodeObjectReference" value="${addLossEventForm.claimObjectReference}"/>
        </c:url>

        <div class="row">
            <div class="form-group">
                <label class="col-md-2 control-label"><smg:fmt value="claim.lossevent.claim"/></label>
                <div class="col-md-2">
                    <td>
                        <a href="${claimNavigatorUrl}" class="validate-props">
                            <html:text property="claimExternalReference" readonly="true" styleClass="form-control "/>
                        </a>
                    </td>
                </div>
                <% if (addLossEventForm.getClaimExternalReference() == null) { %>
                <input type="submit" name="method" value="Initiate Claim" class="btn btn-primary btn-sm">
                <% }  %>

            </div>
        </div>

    <%
        }
    %>
</div>

<script type="text/javascript"src="${pageContext.request.contextPath}/js/jquery.simple-dtpicker.js"></script>
<link type="text/css" href="${pageContext.request.contextPath}/js/jquery.simple-dtpicker.css" rel="stylesheet" />