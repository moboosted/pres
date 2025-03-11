package com.silvermoongroup.fsa.web.product.form;

import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import javax.servlet.http.HttpServletRequest;


public class UploadProductForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;
    
    private FormFile psdFile;
    private String endpointURL;
    
    /**
     * Default constructor
     */
    public UploadProductForm() {
    }
    
    /**
     * Only validate on upload.
     */
    @Override
    public boolean needsValidation(ActionMapping mapping, HttpServletRequest request) {
        String name = getActionName(mapping, request);
        if ("processUpload".equals(name)) {
            return true;
        }
        return false;
    }
    
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        
        if (needsValidation(mapping, request)) {
        
            if (StringUtils.trimToNull(getPsdFile().getFileName()) == null) {
                ActionErrors actionErrors = new ActionErrors();
                actionErrors.add(
                        "psdFile", new ActionMessage("page.uploadproduct.message.filenotfound"));
                return actionErrors;
            }
            
        }
        return null;
    }
    
    /**
     * @return The file that we are uploading.
     */
    public FormFile getPsdFile() {
        return psdFile;
    }

    /**
     * @param psdFile The file that we are uploading.
     */
    public void setPsdFile(FormFile psdFile) {
        this.psdFile = psdFile;
    }
    
    /**
     * @param endpointURL The webservice endpoint url that we are invoking
     */
    public void setEndpointURL(String endpointURL) {
        this.endpointURL = endpointURL;
    }
    
    /**
     * @return The webservice endpoint url that we are invoking
     */
    public String getEndpointURL() {
        return endpointURL;
    }
}
