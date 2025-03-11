<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<smg:datepicker selector=".datefield"/>

<jsp:useBean id="addLossEventForm" scope="request"
             type="com.silvermoongroup.fsa.web.claimmanagement.form.LossEventForm"/>

<label class="groupHeading"><fmt:message key="page.addlossevent.title"/></label>

<html:form action="/secure/claim/lossevent/add.do" styleId="findForm" styleClass="form-horizontal">

    <%@ include file="lossEvent.jsp" %>

    <div class="row spacer">
        <div class="col-md-12 text-center">

            <html:submit property="method" styleClass="btn btn-primary btn-sm">
                <smg:fmt value="button.add"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>
</html:form>
