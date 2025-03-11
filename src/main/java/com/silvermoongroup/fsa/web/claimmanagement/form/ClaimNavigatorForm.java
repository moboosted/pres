/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement.form;

import com.silvermoongroup.common.domain.ObjectReference;
import org.apache.struts.action.ActionForm;


/**
 * @author Justin Walsh
 */
public class ClaimNavigatorForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    /**
     * This is the node that the entry point happens at
     */
    private ObjectReference nodeObjectReference;

    public ClaimNavigatorForm() {
    }

    public ObjectReference getNodeObjectReference() {
        return nodeObjectReference;
    }

    public void setNodeObjectReference(ObjectReference nodeObjectReference) {
        this.nodeObjectReference = nodeObjectReference;
    }
}
