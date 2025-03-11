/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.product.form;

import com.silvermoongroup.common.domain.ObjectReference;
import org.apache.struts.action.ActionForm;

/**
 * @author justinw
 */
public class CreateRequestForMarketableProductVersionForm extends ActionForm {
    
    private static final long serialVersionUID = 1L;

    public CreateRequestForMarketableProductVersionForm() {
    }
    
    private ObjectReference mpvObjectReference;
    private Long requestKindId;

    public ObjectReference getMpvObjectReference() {
        return mpvObjectReference;
    }
    public void setMpvObjectReference(ObjectReference mpvObjectReference) {
        this.mpvObjectReference = mpvObjectReference;
    }
    public Long getRequestKindId() {
        return requestKindId;
    }
    public void setRequestKindId(Long requestKindId) {
        this.requestKindId = requestKindId;
    }
}
