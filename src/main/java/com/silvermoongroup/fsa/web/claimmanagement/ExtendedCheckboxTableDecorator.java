//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.silvermoongroup.fsa.web.claimmanagement;

import org.apache.commons.lang3.ObjectUtils;
import org.displaytag.model.TableModel;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.*;

public class ExtendedCheckboxTableDecorator extends ClaimFolderHierarchyTableDecorator {
    private String id = "id";
    private Map params;
    private List checkedIds;
    private String fieldName = "_chk";

    public ExtendedCheckboxTableDecorator() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void init(PageContext pageContext, Object decorated, TableModel tableModel) {
        super.init(pageContext, decorated, tableModel);
        String[] params = pageContext.getRequest().getParameterValues(this.fieldName);
        this.checkedIds = params != null?new ArrayList(Arrays.asList(params)):new ArrayList(0);
    }

    public void finish() {
        if(!this.checkedIds.isEmpty()) {
            JspWriter writer = this.getPageContext().getOut();
            Iterator it = this.checkedIds.iterator();

            while(it.hasNext()) {
                String name = (String)it.next();
                StringBuffer buffer = new StringBuffer();
                buffer.append("<input type=\"hidden\" name=\"");
                buffer.append(this.fieldName);
                buffer.append("\" value=\"");
                buffer.append(name);
                buffer.append("\">");

                try {
                    writer.write(buffer.toString());
                } catch (IOException var6) {
                    ;
                }
            }
        }

        super.finish();
    }

    public String getCheckbox() {
        String evaluatedId = ObjectUtils.toString(this.evaluate(this.id));
        boolean checked = this.checkedIds.contains(evaluatedId);
        StringBuffer buffer = new StringBuffer();
        buffer.append("<input type=\"checkbox\" name=\"_chk\" value=\"");
        buffer.append(evaluatedId);
        buffer.append("\"");
        if(checked) {
            this.checkedIds.remove(evaluatedId);
            buffer.append(" checked=\"checked\"");
        }

        buffer.append("/>");
        return buffer.toString();
    }
}
