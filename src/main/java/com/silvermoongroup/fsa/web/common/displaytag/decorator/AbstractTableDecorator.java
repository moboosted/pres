/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.displaytag.decorator;

import com.silvermoongroup.businessservice.policymanagement.dto.KindDTO;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.common.datatype.Amount;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.datatype.MeasureAmount;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.enumeration.intf.IEnum;
import com.silvermoongroup.fsa.web.common.util.FormatUtil;
import com.silvermoongroup.party.domain.PartyName;
import com.silvermoongroup.party.domain.PersonName;
import com.silvermoongroup.party.domain.UnstructuredName;
import org.apache.commons.lang3.StringUtils;
import org.displaytag.decorator.TableDecorator;

/**
 * Base class for table decorators.
 *
 * @author Justin Walsh
 */
public abstract class AbstractTableDecorator extends TableDecorator {

    protected String formatDate(Date date) {
        return FormatUtil.getTypeFormatter(getPageContext()).formatDate(date);
    }

    protected String formatDateTime(DateTime dateTime) {
        return FormatUtil.getTypeFormatter(getPageContext()).formatDateTime(dateTime);
    }

    protected String formatKind(KindDTO kind) {
        return FormatUtil.getTypeFormatter(getPageContext()).formatKind(kind);
    }

    protected String formatMessage(String key) {
        return FormatUtil.getTypeFormatter(getPageContext()).formatMessage(key);
    }

    protected String formatEnum(EnumerationReference value) {
        return FormatUtil.getTypeFormatter(getPageContext()).formatEnum(value);
    }
    
    protected String formatEnum(IEnum value) {
        return FormatUtil.getTypeFormatter(getPageContext()).formatEnum(value);
    }

    protected String formatAmount(Amount value) {
        return FormatUtil.getTypeFormatter(getPageContext()).formatAmount(value);
    }

    protected String formatMeasureAmount(MeasureAmount value, boolean includeUnitOfMeasure) {
        return FormatUtil.getTypeFormatter(getPageContext()).formatMeasureAmount(value, includeUnitOfMeasure);
    }

    protected String formatType(Long value) {
        return FormatUtil.getTypeFormatter(getPageContext()).formatType(value);
    }

    protected String formatType(Type type) {
        return FormatUtil.getTypeFormatter(getPageContext()).formatType(type);
    }

    protected String formatYesNo(boolean value) {
        return value ?  formatMessage("label.yesno.true") : formatMessage("label.yesno.false");
    }

    protected String getFormattedPartyName(PartyName pn) {
        if (pn instanceof UnstructuredName) {
            return ((UnstructuredName) pn).getFullName();
        } else if (pn instanceof PersonName) {
            PersonName name = (PersonName) pn;
            StringBuilder sb = new StringBuilder();
            if (name.getPrefixTitles() != null) {
                sb.append(formatEnum(name.getPrefixTitles()));
                sb.append(" ");
            }
            if (name.getFirstName() != null) {
                sb.append(name.getFirstName());
                sb.append(" ");
            }
            if (name.getLastName() != null) {
                sb.append(name.getLastName());
            }
            return sb.toString();
        } else { // null
            return StringUtils.EMPTY;
        }
    }

    /**
     * Generate an HTML div containing the object id with a tool tip that provides information about the object reference
     *
     * @param objectReference The object reference
     * @return The generated div.
     */
    protected String generateIdentityDivWithObjectReferenceTooltip(ObjectReference objectReference) {
        return FormatUtil.getTypeFormatter(getPageContext()).generateIdentityDivWithObjectReferenceTooltip(objectReference);
    }

    /**
     * Generate an HTML div containing the object type with a tool tip that provides information about the object reference
     *
     * @param objectReference The object reference
     * @return The generated div.
     */
    protected String generateTypeDivWithObjectReferenceTooltip(ObjectReference objectReference) {
        return FormatUtil.getTypeFormatter(getPageContext()).generateTypeDivWithObjectReferenceTooltip(objectReference);
    }

    /**
     * Generate an HTML div containing the name (falling back on the type if the name is null)
     * with a tool tip that provides information about the object reference
     *
     * @param objectReference The object reference
     * @param name            The name to display (if not null)
     * @return The generated div.
     */
    protected String generateDivWithObjectReferenceTooltip(ObjectReference objectReference, String name) {
        return FormatUtil.getTypeFormatter(getPageContext()).generateDivWithObjectReferenceTooltip(objectReference, name);
    }

    protected String generateCategoryName(long categoryId){
        return FormatUtil.getTypeFormatter(getPageContext()).formatCategory(categoryId);
    }

}
