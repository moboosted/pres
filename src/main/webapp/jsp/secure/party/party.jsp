<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<smg:datepicker selector=".datefield" />

<html:form action="/secure/partyAction.do" styleId="partyForm">
      
    <input type="hidden" name="hiddenMethod"/>
    <input type="hidden" name="validate"/>
    <input id="embeddedMode" type="hidden" name="embeddedMode" value="${partyForm.embeddedMode}"/>
    <input id="roleKindToLink" type="hidden" name="roleKindToLink" value="${partyForm.roleKindToLink}"/>
    <%@ include file="hidden.jsp" %>
    
    <!-- Include the tabs -->
    <div id="tab-wrapper">
        <%@ include file="tabs.jsp" %>
 
        <div class="tab-content">
            <div title="General Party" id="general">
                <c:choose>
                    <c:when test="${partyForm.generalTabLoaded eq 'false'}">
<%--                             <%@include file="partyTypes/partyTypeSelection.jsp" %> --%>
                            <c:choose>
                                <c:when test="${partyForm.selPartyType eq 'Person'}">
                                    <%@include file="partyTypes/generalPerson.jsp" %>
                                </c:when>
                                <c:otherwise>
                                    <%@include file="partyTypes/organisation.jsp" %>
                                </c:otherwise>
                            </c:choose>
                    </c:when>
                    <c:otherwise>
                        <%@include file="partyTypes/general.jsp"%>
                    </c:otherwise>
                </c:choose>
            </div>
            <div title="Relationship" id="relationships">
                <c:choose>
                    <c:when test="${partyForm.relationshipsTabLoaded eq 'false'}">
                        <%@include file="clearscrn.jsp"%>
                    </c:when>
                    <c:otherwise>
                        <%@include file="relationships/partyRelationships.jsp"%>
                    </c:otherwise>
                </c:choose>
            </div>
            <div title="Roles" id="roles">
                <c:choose>
                    <c:when test="${partyForm.rolesTabLoaded eq 'false'}">
                        <%@include file="clearscrn.jsp"%>
                    </c:when>
                    <c:otherwise>
                        <%@include file="roles/partyRoles.jsp"%>
                    </c:otherwise>
                </c:choose>
            </div>
            <div title="Contact" id="contact">
                <c:choose>
                    <c:when test="${partyForm.contactTabLoaded eq 'false'}">
                        <%@include file="clearscrn.jsp"%>
                    </c:when>
                    <c:otherwise>
                        <%@include file="contactPoints/contact.jsp"%>
                    </c:otherwise>
                </c:choose>
            </div>
            <div title="Banking Details" id="bank">
                <c:choose>
                    <c:when test="${partyForm.bankTabLoaded eq 'false'}">
                        <%@include file="clearscrn.jsp"%>
                    </c:when>
                    <c:otherwise>
                        <%@include file="bank/bank.jsp"%>
                    </c:otherwise>
                </c:choose>
            </div>
            <div title="Registrations" id="registrations">
                <c:choose>
                    <c:when test="${partyForm.regTabLoaded eq 'false'}">
                        <%@include file="clearscrn.jsp"%>
                    </c:when>
                    <c:otherwise>
                        <%@include file="registrations/registration.jsp"%>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</html:form>

<!-- Include any additional scripting js/css -->
<%@ include file="scripts.jsp" %>
