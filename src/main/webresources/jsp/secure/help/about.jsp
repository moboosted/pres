<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-md-offset-3 col-md-6 ">
        <div class="text-center">
            <img src="${pageContext.request.contextPath}/images/silvermoon-logo.png" width="400">
            <h4>${project.version}</h4>
            <h4><fmt:message key="page.about.label.builton"/>: ${buildTimestamp}</h4>
        </div>
    </div>

</div>




