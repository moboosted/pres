package com.silvermoongroup.fsa.web.enumeration;

import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;
import org.apache.struts.util.LabelValueBean;


public class EnumerationTypeTableDecorator extends AbstractTableDecorator {
    
    public Object getEnumerationTypeName() {
        LabelValueBean currentRowObject = (LabelValueBean) getCurrentRowObject();
        return currentRowObject.getLabel();
    }
    
    public Object getEnumerationTypeId() {
        LabelValueBean currentRowObject = (LabelValueBean) getCurrentRowObject();
        return currentRowObject.getValue();
    }
    
}
