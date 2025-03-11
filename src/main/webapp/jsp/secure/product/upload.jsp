<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>

    .btn-file {
        position: relative;
        overflow: hidden;
    }

    .btn-file input[type=file] {
        position: absolute;
        top: 0;
        right: 0;
        min-width: 100%;
        min-height: 100%;
        font-size: 100px;
        text-align: right;
        filter: alpha(opacity=0);
        opacity: 0;
        background: red;
        cursor: inherit;
        display: block;
    }

    input[readonly] {
        background-color: white !important;
        cursor: text !important;
    }
</style>

<script>
    $(document).on('change', '.btn-file :file', function () {
        var input = $(this),
                numFiles = input.get(0).files ? input.get(0).files.length : 1,
                label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger('fileselect', [numFiles, label]);
    });

    $(document).ready(function () {
        $('.btn-file :file').on('fileselect', function (event, numFiles, label) {
            $('#fileNameDisplay').val(label);
        });
    });

</script>

<html:form action="/secure/product/upload.do" styleId="productuploadform" enctype="multipart/form-data"
           styleClass="form-horizontal">
    <input type="hidden" name="method" value=".processUpload"/>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-4">
            <div class="input-group">
            <span class="input-group-btn">
                        <span class="btn btn-primary btn-file btn-sm">
                            <fmt:message key="label.browse"/>&hellip; <html:file property="psdFile"/>
                        </span>
                    </span>

                <input type="text" id="fileNameDisplay" class="form-control input-sm required" readonly>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label for="endPointUrl" class="col-md-2 control-label"><fmt:message
                key="page.uploadproduct.label.endpoint"/></label>

        <div class="col-md-4">
            <html:text property="endpointURL" styleId="endPointUrl" styleClass="form-control input-sm"/>
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-4">
            <html:submit property="method" styleClass="btn btn-primary btn-sm">
                <bean:message key="button.submit"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>


</html:form>