package com.silvermoongroup.fsa.web.physicalobject.action;

import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.common.datatype.CurrencyAmount;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.common.enumeration.EnumerationType;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.common.callback.CallBack;
import com.silvermoongroup.fsa.web.common.callback.CallBackUtility;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import com.silvermoongroup.fsa.web.physicalobject.form.ManufacturedItemForm;
import com.silvermoongroup.fsa.web.struts.OnFormValidationFailure;
import com.silvermoongroup.physicalobject.domain.ManufacturedItem;
import com.silvermoongroup.physicalobject.domain.intf.IManufacturedItem;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * Struts action for the Physical object action.
 */
public class ManufacturedItemAction extends AbstractLookupDispatchAction {

    public ManufacturedItemAction() {
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("button.back", "back");
        map.put("button.save", "save");
        map.put("button.update", "update");
        return map;
    }

    /**
     * Display the page, either a new or existing physical object.
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
                                        HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        ManufacturedItemForm form = (ManufacturedItemForm) actionForm;
        ObjectReference rolePlayer = form.getRolePlayerObjectReference();

        if (form.getRolePlayerObjectReference() != null) {
            ManufacturedItem<?> item = (ManufacturedItem<?>) getPhysicalObjectManagementService().getManufacturedItem(
                    getApplicationContext(), rolePlayer);
            form.setCurrencyCode(item.getValue() == null ? null : (item.getValue().getCurrencyCode() == null ? null
                    : item.getValue().getCurrencyCode().toString()));
            form.setDescription(item.getDescription());
            form.setEndDate(formatDate(item.getEffectivePeriod().getEnd()));
            form.setStartDate(formatDate(item.getEffectivePeriod().getStart()));
            form.setExternalReference(item.getSerialNumber());
            form.setInspectionCompleted(item.getInspection());
            form.setName(item.getName());
            form.setTypeId(rolePlayer.getTypeId());
            form.setValue(item.getValue() == null ? "" : formatAmount(item.getValue()));
        }

        if (form.getStartDate() == null) {
            form.setStartDate(formatDate(Date.today()));
        }

        populateStaticPageElements(form, httpRequest);
        return actionMapping.getInputForward();
    }


    /**
     * User hits the back button
     */
    public ActionForward back(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpRequest,
                              HttpServletResponse httpResponse) {
        CallBack callBack = CallBackUtility.getCallBack(httpRequest, httpResponse);
        return CallBackUtility.getForwardAction(callBack);
    }

    /**
     * Save a new object
     */
    public ActionForward save(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpRequest,
                              HttpServletResponse httpResponse) {

        ManufacturedItemForm form = (ManufacturedItemForm) actionForm;
        return saveOrUpdate(actionMapping, form, httpRequest, httpResponse);
    }

    /**
     * Update an existing object
     */
    public ActionForward update(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpRequest,
                                HttpServletResponse httpResponse) {

        ManufacturedItemForm form = (ManufacturedItemForm) actionForm;
        return saveOrUpdate(actionMapping, form, httpRequest, httpResponse);
    }

    /**
     * Save a new physical object, or update an existing one
     */
    private ActionForward saveOrUpdate(ActionMapping actionMapping, ManufacturedItemForm form, HttpServletRequest httpRequest,
                                       HttpServletResponse httpResponse) {

        ObjectReference rolePlayer = form.getRolePlayerObjectReference();

        IManufacturedItem item;

        if (rolePlayer != null) {
            item = getPhysicalObjectManagementService().getManufacturedItem(
                    getApplicationContext(), rolePlayer);
            populatePhysicalObjectFromForm(form, item);

            // this has the effect of creating a new version - we should possible save as draft and only 
            // create a new version when we raise the request
            item = getPhysicalObjectManagementService().modifyManufacturedItem(getApplicationContext(), item);
        } else {
            ManufacturedItem<?> mi = new ManufacturedItem();
            populatePhysicalObjectFromForm(form, mi);
            item = getPhysicalObjectManagementService().establishManufacturedItem(getApplicationContext(), mi);
        }


        CallBack callBack = CallBackUtility.getCallBack(httpRequest, httpResponse);
        CallBackUtility.setAttribute(httpRequest, item.getObjectReference(), callBack);
        return CallBackUtility.getForwardAction(callBack);
    }

    @OnFormValidationFailure
    public void onFormValidationFailure(ActionForm actionForm, HttpServletRequest httpRequest) {
        ManufacturedItemForm form = (ManufacturedItemForm) actionForm;
        populateStaticPageElements(form, httpRequest);
    }


    @SuppressWarnings("unchecked")
    private void populateStaticPageElements(ManufacturedItemForm actionForm, HttpServletRequest httpRequest) {
        ITypeFormatter typeFormatter = FormatUtil.getTypeFormatter(httpRequest);

        if (actionForm.getCurrencyCode() == null) {
            IEnumeration defaultCurrencyCode = getProductDevelopmentService().findEnumerationByNameAndType(
                    getApplicationContext(), "EUR", EnumerationType.CURRENCY_CODE.getValue());
            if (defaultCurrencyCode != null) {
                actionForm.setCurrencyCode(defaultCurrencyCode.getEnumerationReference().toString());
            }
        }

        Set<Type> manufacturedItemsTypes =
                getProductDevelopmentService().getAllSubTypes(
                        getApplicationContext(), CoreTypeReference.MANUFACTUREDITEM.getValue()
                );

        // types - hardcoded for now 
        List<LabelValueBean> types = new ArrayList<>();
        for (Type type : manufacturedItemsTypes) {
            types.add(new LabelValueBean(formatType(type), String.valueOf(type.getId())));
        }
        Collections.sort(types, LabelValueBean.CASE_INSENSITIVE_ORDER);
        actionForm.setTypeOptions(types);

        List<LabelValueBean> yesNo = new ArrayList<>();
        yesNo.add(new LabelValueBean(typeFormatter.formatMessage("label.true"), Boolean.TRUE.toString()));
        yesNo.add(new LabelValueBean(typeFormatter.formatMessage("label.false"), Boolean.FALSE.toString()));
        actionForm.setInspectionCompletedOptions(yesNo);
    }

    private void populatePhysicalObjectFromForm(ManufacturedItemForm form, IManufacturedItem mi) {
        if (!GenericValidator.isBlankOrNull(form.getCurrencyCode())) {
            EnumerationReference currencyCodeEnumRef = EnumerationReference.convertFromString(form.getCurrencyCode());
            BigDecimal bigDecimal = getTypeParser().parseBigDecimal(form.getValue());
            mi.setValue(new CurrencyAmount(bigDecimal, currencyCodeEnumRef));
        }
        mi.setDescription(form.getDescription());
        mi.setEffectivePeriod(parseDatePeriod(form.getStartDate(), form.getEndDate()));
        mi.setName(form.getName());
        mi.setTypeId(form.getTypeId());
        mi.setInspection(form.isInspectionCompleted());
        mi.setExternalReference(form.getExternalReference());
    }
}
