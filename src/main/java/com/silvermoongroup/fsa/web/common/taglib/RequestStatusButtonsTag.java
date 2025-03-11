/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.businessservice.policymanagement.dto.RequestDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.RequestStateDTO;
import com.silvermoongroup.spflite.enumeration.RequestStateNames;
import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.util.List;

public class RequestStatusButtonsTag extends TagSupport {

    private static final long serialVersionUID = 1L;
    private RequestDTO request;
    private String onclick;

    @Override
    public int doEndTag() throws JspException {


        RequestStateDTO requestState = getRequest().getState();
        final boolean readOnly = request.isReadOnly();

        List<Button> buttons = new ArrayList<>();
        String primaryButtonClass = "btn btn-primary btn-sm";
        if (request.isComponentRequest()) {
            buttons.add(new Button(getMessage("page.request.action.done"), primaryButtonClass));
        } else if (!readOnly) {
            if (RequestStateNames.SAVED.getName().equals(requestState.getName())) {
                buttons.add(new Button(getMessage("button.save"), "btn btn-default btn-sm"));
                buttons.add(new Button(getMessage("page.request.action.done"), primaryButtonClass));
                buttons.add(new Button(getMessage("page.request.action.cancel"), "btn btn-link btn-sm"));
            } else if (RequestStateNames.REQUIRESAUTHORISATION.getName().equals(requestState.getName())) {
                buttons.add(new Button(getMessage("button.authorise"), primaryButtonClass));
                buttons.add(new Button(getMessage("button.decline"), primaryButtonClass));
            } else if (RequestStateNames.OVERRIDABLE_RULE_FAILED.getName().equals(requestState.getName())) {
                buttons.add(new Button(getMessage("button.decline"), primaryButtonClass));
                buttons.add(new Button(getMessage("button.override"), primaryButtonClass));
            }
        }


        // now build the HTML for the buttons
        //
        StringBuilder results = new StringBuilder();
        results.append("<input type=\"hidden\" name=\"hiddenMethod\" /> ");

        for (Button button : buttons) {

            results.append("<input type=\"submit\" name=\"method\" value=\"");
            results.append(button.label);
            results.append("\" class=\"");
            results.append(button.styleClass);
            results.append("\" ");
            if (getOnclick() != null) {
                results.append("onclick=\"");
                results.append(getOnclick());
                results.append("\"");
            }
            results.append("/> ");
        }

        TagUtils.getInstance().write(pageContext, results.toString());

        return (Tag.EVAL_PAGE);
    }

    private String getMessage(String key) throws JspException {
        return TagUtils.getInstance().message(pageContext, null, null, key);
    }

    public RequestDTO getRequest() {
        return request;
    }

    public void setRequest(RequestDTO request) {
        this.request = request;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    private class Button {

        String label;
        String styleClass;

        private Button(String label, String styleClass) {
            this.label = label;
            this.styleClass = styleClass;
        }
    }
}
