<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<html:form action="/secure/user/preferences.do" styleClass="form-horizontal">

    <div class="form-group">
        <label for="username" class="col-md-2 control-label"><fmt:message key="page.userpreferences.username" /></label>
        <div class="col-md-3">
            <input type="text" class="form-control input-sm" id="username" disabled value="${userProfile.principal.name}">
        </div>
    </div>

    <div class="form-group">
        <label for="locale" class="col-md-2 control-label"><fmt:message key="page.userpreferences.locale" /></label>
        <div class="col-md-2">
            <html:select property="locale" styleId="locale" styleClass="form-control input-sm">
                <html:optionsCollection property="availableLocales" />
            </html:select>
        </div>
    </div>

    <div class="form-group">
        <label for="dateFormat" class="col-md-2 control-label"><fmt:message key="page.userpreferences.dateformat" /></label>
        <div class="col-md-3">
            <html:select property="dateFormat" styleClass="form-control input-sm" styleId="dateFormat">
                <html:optionsCollection property="availableDateFormats" />
            </html:select>
        </div>
    </div>

    <div class="form-group">
        <label for="numberFormat" class="col-md-2 control-label"><fmt:message key="page.userpreferences.numberformat" /></label>
        <div class="col-md-2">
            <html:text property="numberFormat" styleClass="form-control input-sm" styleId="numberFormat" />
        </div>
    </div>


    <div class="form-group">
        <label for="decimalSeparator" class="col-md-2 control-label"><fmt:message key="page.userpreferences.decimalseparator" /></label>
        <div class="col-md-1">
            <html:select property="decimalSeparator" styleClass="form-control input-sm" styleId="decimalSeparator">
                <html:optionsCollection property="availableDecimalSeparators" />
            </html:select>
        </div>
    </div>

    <div class="form-group">
        <label for="groupingSeparator" class="col-md-2 control-label"><fmt:message key="page.userpreferences.groupingseparator" /></label>
        <div class="col-md-1">
            <html:select property="groupingSeparator" styleClass="form-control input-sm" styleId="groupingSeparator">
                <html:optionsCollection property="availableGroupingSeparators" />
            </html:select>
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-10">
            <html:submit property="method" styleClass="btn btn-primary btn-sm">
                <fmt:message key="page.userpreferences.save" />
            </html:submit>
        </div>
    </div>

</html:form>