<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/secure/agreement/role/category/select.do">

    <html:hidden property="categorySchemeObjectReference"/>
    <input type="hidden" name="method" id="method" value=""/>

    <fieldset>
        <legend class="control-label"><fmt:message key="page.selectcategory.label.fieldsetlegend"/>: <b><c:out
                value="${selectCategoryForm.categoryScheme.name}"/></b></legend>
        <div>
            <c:forEach items="${selectCategoryForm.categories}" var="category">

                <html:radio property="selectedCategoryObjectReference" value="${category.value}">
                    <label style="padding-left: 5px;"><c:out value="${category.label}"/>
                    </label>
                    <br>
                </html:radio>

            </c:forEach>
        </div>
    </fieldset>

    <div class="col-md-12 text-center">
        <html:submit styleId="submitButton" property="method" styleClass="btn btn-primary btn-sm"
                     onclick="$('#method').val('.submit');return true;">
            <fmt:message key="button.submit"/>
        </html:submit>
        <html:submit styleId="backButton" styleClass="btn btn-link btn-sm" property="method"
                     onclick="$('#method').val('.back');return true;">
            <fmt:message key="button.back"/>
        </html:submit>
    </div>

</html:form>
