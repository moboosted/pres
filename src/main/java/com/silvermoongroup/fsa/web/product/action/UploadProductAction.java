package com.silvermoongroup.fsa.web.product.action;

import com.silvermoongroup.fsa.web.common.WebApplicationProperties;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.product.form.UploadProductForm;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.xml.transform.ResourceSource;
import org.springframework.xml.transform.StringResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadProductAction extends AbstractLookupDispatchAction {

    private static Logger log = LoggerFactory.getLogger(UploadProductAction.class);

    private static final String DEFAULT_URI = "/product-development-ws/service/productdevelopment";

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("button.submit", "processUpload");
        return map;
    }

    public ActionForward defaultExecute(ActionMapping mapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        UploadProductForm form = (UploadProductForm) actionForm;

        if (httpServletRequest.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED) != null) {
            ActionMessages actionMessages = new ActionMessages();
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE,
                    new ActionMessage(
                            "page.uploadproduct.message.maxfilesizeexceeded",
                            mapping.getModuleConfig().getControllerConfig().getMaxFileSize()
                            )
                    );
            saveErrorMessages(httpServletRequest, actionMessages);


        }

        if (form.getEndpointURL() == null) {
            form.setEndpointURL(getServerNameAndPort(httpServletRequest).concat(DEFAULT_URI));
        }
        return mapping.getInputForward();
    }

    public ActionForward processUpload(ActionMapping mapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        UploadProductForm form = (UploadProductForm) actionForm;
        FormFile psdFile = form.getPsdFile();

        String contentType = psdFile.getContentType();
        String fileName = psdFile.getFileName();
        int fileSize = psdFile.getFileSize();
        byte[] fileData = psdFile.getFileData();

        if (log.isDebugEnabled()) {
            log.debug("Uploading file " + fileName + " with content type: " + contentType + "and size: " + fileSize);
        }

        ActionMessages actionMessages = new ActionMessages();
        StringResult result = new StringResult();
        WebServiceTemplate template = new WebServiceTemplate();

        String endpointURL = form.getEndpointURL();
        if (endpointURL == null) {
            endpointURL = getServerNameAndPort(httpServletRequest).concat(DEFAULT_URI);
        }
        template.setDefaultUri(endpointURL);

        Resource resource = new InputStreamResource(new ByteArrayInputStream(fileData));
        Source requestSource = new ResourceSource(resource);
        template.sendSourceAndReceiveToResult(requestSource, result);

        // handle the response
        if (result.toString().contains(">fail<")){ // very crude

            actionMessages.add(
                    ActionMessages.GLOBAL_MESSAGE,
                    new ActionMessage("page.uploadproduct.message.uploadfailure", new Object[]{fileName}));

            saveErrorMessages(httpServletRequest, actionMessages);
        }
        else {
            actionMessages.add(
                    ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.uploadproduct.message.success", new Object[]{fileName}));
            saveInformationMessages(httpServletRequest, actionMessages);
        }
        return mapping.getInputForward();
    }

    private String getServerNameAndPort(HttpServletRequest httpServletRequest) {
        StringBuffer sb;
        if (httpServletRequest.isSecure()) {
            sb = new StringBuffer("https://");
        } else {
            sb = new StringBuffer("http://");
        }
        WebApplicationProperties properties = getApplicationProperties();
        sb.append(properties.getProductUploadServer());
        sb.append(":");

        String port = properties.getProductUploadPort();
        if (port == null) {
            sb.append(httpServletRequest.getServerPort());
        } else {
            sb.append(port);
        }

        return sb.toString();
    }

}
