<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<smg:datepicker selector=".datefield"/>

<script type="text/javascript">
    $(document).ready(function () {
        $('#search').keyup(function () {
            var $rows = $('#enumerationType tbody tr');
            var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();

            $rows.show().filter(function () {
                var text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
                return !~text.indexOf(val);
            }).hide();
        });
    });
</script>

<div class="row">
    <div class="col-lg-6">
        <div class="input-group">
            <label for="search" class="control-label"><fmt:message key="page.viewenumeration.label.search"/></label>
            <input type="text" id="search" class="form-control input-sm" />
        </div>
        <!-- /input-group -->
    </div>
    <!-- /.col-lg-6 -->
</div>

<div class="table-responsive spacer">

<display:table name="${enumerationTypesForm.enumerationTypes}" id="enumerationType"
               requestURI="/secure/enumeration/enumerationTypes.do"
               decorator="com.silvermoongroup.fsa.web.enumeration.EnumerationTypeTableDecorator">


    <display:column property="enumerationTypeId" titleKey="label.code" style="width:5%" class="l m" headerClass="l m"/>
    <display:column property="enumerationTypeName" titleKey="label.name" style="width:20%" class="l m"
                    headerClass="l m"/>

    <display:column class="m" style="width:5%">
        <html:form action="/secure/enumeration/enumerationTypes.do">
            <html:hidden property="enumerationTypeId" value="${enumerationType.value}"/>
            <input type="hidden" name="method" value=".view"/>
            <html:submit>
                <fmt:message key="page.enumeration.label.view"/>
            </html:submit>
        </html:form>
    </display:column>

</display:table>
</div>
