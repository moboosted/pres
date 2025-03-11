/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.businessservice.policymanagement.dto.KindDTO;
import com.silvermoongroup.businessservice.policymanagement.service.intf.IPolicyAdminService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import com.silvermoongroup.kindmanager.criteria.KindSearchCriteria;
import com.silvermoongroup.kindmanager.domain.KindCategory;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.OptionsCollectionTag;
import org.apache.struts.taglib.html.SelectTag;
import org.apache.struts.util.LabelValueBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Render a set of kind options based on the kindType parameter.
 */
public class KindOptionsTag extends OptionsCollectionTag {

    private static final long serialVersionUID = 1L;

    private String kindType;

    /**
     * @see org.apache.struts.taglib.html.OptionsCollectionTag#doStartTag()
     */
    @SuppressWarnings("unchecked")
    @Override
    public int doStartTag() throws JspException {

        // Acquire the select tag we are associated with
        SelectTag selectTag = (SelectTag) pageContext.getAttribute(Constants.SELECT_KEY);

        if (selectTag == null) {
            JspException e = new JspException(OptionsCollectionTag.messages.getMessage("optionsCollectionTag.select"));
            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        List<KindDTO> kinds = new ArrayList<>();

        try {
            if (getKindType().equals("TopLevelAgreement")) {
                kinds.addAll(getFSABusinessService().getMarketableProductKinds(new ApplicationContext()));
            } else {
                KindSearchCriteria kindSearchCriteria = new KindSearchCriteria();
                if (getKindType() != null) {
                    KindCategory kindCategory = KindCategory.fromName(getKindType());
                    kindSearchCriteria.setKindCategory(kindCategory);
                }
                kinds.addAll(getFSABusinessService().findKinds(new ApplicationContext(), kindSearchCriteria));
            }
        } catch (Exception ex) {
            throw new JspException(ex.getMessage(), ex);
        }

        Collections.sort(kinds, new Comparator<KindDTO>() {
            @Override
            public int compare(KindDTO kind1, KindDTO kind2) {
                return kind1.getName().compareTo(kind2.getName());
            }
        });

        List<LabelValueBean> options = new ArrayList<>();
        for (KindDTO kindDTO : kinds) {

            LabelValueBean lvb = new LabelValueBean(getTypeFormatter().formatKind(kindDTO), String.valueOf(kindDTO
                    .getId()));
            options.add(lvb);
        }

        // sort, case insensitive
        Collections.sort(options, LabelValueBean.CASE_INSENSITIVE_ORDER);

        StringBuffer sb = new StringBuffer(128);
        for (LabelValueBean labelValueBean : options) {
            addOption(sb, labelValueBean.getLabel(), labelValueBean.getValue(),
                    selectTag.isMatched(labelValueBean.getValue()));
        }

        TagUtils.getInstance().write(pageContext, sb.toString());
        return Tag.SKIP_BODY;
    }

    public String getKindType() {
        return kindType;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setKindType(String kindType) {
        this.kindType = kindType;
    }

    private ITypeFormatter getTypeFormatter() {
        return FormatUtil.getTypeFormatter(pageContext);
    }

    private IPolicyAdminService getFSABusinessService() {
        WebApplicationContext context =
                WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
        return context.getBean(IPolicyAdminService.class);
    }
}
