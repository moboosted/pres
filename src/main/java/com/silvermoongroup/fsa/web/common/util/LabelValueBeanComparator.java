/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.util;

import org.apache.struts.util.LabelValueBean;

import java.util.Comparator;

/**
 * Compares {@link LabelValueBean}s alphabetically by label.
 * 
 * @author Justin Walsh
 */
public class LabelValueBeanComparator implements Comparator<LabelValueBean> {

    @Override
    public int compare(LabelValueBean lhs, LabelValueBean rhs) {

        return lhs.getLabel().compareTo(rhs.getLabel());
    }
}
