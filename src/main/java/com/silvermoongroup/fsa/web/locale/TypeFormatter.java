/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.locale;

import com.silvermoongroup.businessservice.policymanagement.dto.AgreementStateDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.ComponentListDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.KindDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.RequestStateDTO;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.constants.Components;
import com.silvermoongroup.common.datatype.*;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.Category;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.common.enumeration.intf.IEnum;
import com.silvermoongroup.common.enumeration.intf.ITypeReference;
import com.silvermoongroup.fsa.web.user.profile.IUserProfileManager;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A formatter for types which uses request and user information
 *
 * @author Justin Walsh
 */
@Component
public class TypeFormatter implements ITypeFormatter {

    private static Logger log = LoggerFactory.getLogger(TypeFormatter.class);

    private static final String ENUM = "enum";
    private static final String KIND_PREFIX = "spf.psd.kind.";
    private static final String REQUEST_STATE_PREFIX = "spf.psd.requeststate.";
    private static final String AGREEMENT_STATE_PREFIX = "spf.psd.agreementstate.";
    private static final String DOT = ".";

    private static final String OBJECT_REFERENCE_TOOLTIP_FORMAT = "%s (%s:%s:%d)";
    private static final String OBJECT_REFERENCE_FORMAT = "<div><span rel=\"tooltip\" data-toggle=\"tooltip\" title=\"%s\">%s</span></div>";

    private static final String MISSING_KEY_PREFIX_SUFFIX = "????";

    // regular expression used to find the component when an enumeration is passed in
    private final Pattern PROPERTY_ENUM_PATTERN = Pattern.compile("^com\\.silvermoongroup\\.(\\w+)\\..*");

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private IUserProfileManager userProfileManager;

    @Autowired
    private IProductDevelopmentService typeManager;
    
    public TypeFormatter() {
    }

    @Override
    public String formatAmount(Amount toFormat) {
        
        if (toFormat == null) {
            return StringUtils.EMPTY;
        }

        return formatBigDecimal(toFormat.getAmount());
    }

    @Override
    public String formatCurrencyAmount(CurrencyAmount value) {
        // for now, simply format the amount
        return formatAmount(value);
    }

    @Override
    public String formatBigDecimal(BigDecimal toFormat) {

        if (toFormat == null) {
            return StringUtils.EMPTY;
        }

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(getUserProfileManager().getDecimalSeparator());
        symbols.setGroupingSeparator(getUserProfileManager().getGroupingSeparator());

        DecimalFormat df = new DecimalFormat(getUserProfileManager().getNumberFormat(), symbols);
        return df.format(toFormat);
    }
    
    @Override
    public String formatMeasureAmount(MeasureAmount toFormat) {
        return formatMeasureAmount(toFormat, true);
    }

    @Override
    public String formatMeasureAmount(MeasureAmount toFormat, boolean includeUnitOfMeasure) {
        String amount = formatAmount(toFormat);
        if (includeUnitOfMeasure) {
            StringBuilder returnValue = new StringBuilder();
            if (toFormat.getUnitOfMeasure() != null) {
                returnValue.append(formatEnum(toFormat.getUnitOfMeasure()));
            }
            return returnValue.append(" ").append(amount).toString();
        } else {
            return amount;
        }
    }

    @Override
    public String formatPercentage(Percentage toFormat) {

        if (toFormat == null || toFormat.getAmount() == null) {
            return StringUtils.EMPTY;
        }

        BigDecimal raw = toFormat.getAmount();
        BigDecimal converted = raw.movePointRight(2);

        String numberPattern = "0.######";
        NumberFormat nf = new DecimalFormat(numberPattern);
        return nf.format(converted);
    }

    /**
     * @see com.silvermoongroup.fsa.web.locale.ITypeFormatter#formatDate(com.silvermoongroup.common.datatype.Date)
     */
    @Override
    public String formatDate(Date dateToFormat) {

        if (dateToFormat == null) {
            return StringUtils.EMPTY;
        }
        FastDateFormat fdf = FastDateFormat.getInstance(getUserProfileManager().getDateFormat());
        return fdf.format(dateToFormat.toJavaUtilDate());
    }

    /**
     * @see com.silvermoongroup.fsa.web.locale.ITypeFormatter#formatDateTime(com.silvermoongroup.common.datatype.DateTime)
     */
    @Override
    public String formatDateTime(DateTime toFormat) {

        if (toFormat == null) {
            return StringUtils.EMPTY;
        }
        FastDateFormat fdf = FastDateFormat.getInstance(getUserProfileManager().getDateTimeFormat());
        return fdf.format(toFormat.toJavaUtilDate());
    }

    /**
     * Format a name of a kind..
     *
     * @param kind The kind to format.
     * @return The formatted string, defaulting to the {@link KindDTO}'s name if a translation cannot be found. An empty
     *         String if the kind is null.
     */
    @Override
    public String formatKind(KindDTO kind) {
        if (kind == null) {
            return StringUtils.EMPTY;
        }
        String key = buildKindKey(kind);
        return getFormattedMessage(key, kind.getName());
    }

    /**
     * Format a kind given its category and name
     *
     * @param kindCategory The kind category
     * @param kindName     The kind name.
     * @return The formatted string, defaulting to the {@link KindDTO}'s name if a translation cannot be found. An empty
     *         String if either the kind category or kind name is null.
     */
    @Override
    public String formatKind(String kindCategory, String kindName) {
        String key = buildKindKey(kindCategory, kindName);
        return getFormattedMessage(key, kindName);

    }

    @Override
    public String formatSpecificationObjectName(KindDTO kind, String defaultDisplayName) {

        if (kind == null) {
            return StringUtils.EMPTY;
        }

        String key = buildKindKey(kind);

        // first check if we have a translated message
        String translatedName = getFormattedMessage(key, null);
        if (translatedName != null) {
            return StringEscapeUtils.escapeHtml3(translatedName);
        }

        // next, use the default display name
        if (defaultDisplayName != null) {
            return StringEscapeUtils.escapeHtml3(defaultDisplayName);
        }

        // other wise fall back on the kind name
        return StringEscapeUtils.escapeHtml3(kind.getName());

    }

    /**
     * Format a hint which is used to provide IAA specifics about the kind
     *
     * @param kind The kind to provide a title for
     * @return The formatted string, an empty string if the kind is null.
     */
    @Override
    public String formatKindHint(KindDTO kind) {

        if (kind == null) {
            return StringUtils.EMPTY;
        }
        return "Kind: " + StringEscapeUtils.escapeHtml3(kind.getName()) + " (" + kind.getId() + ")";
    }

    @Override
    public String formatSpecificationObjectHint(KindDTO kind, String defaultDisplayHint) {

        if (kind == null) {
            return StringUtils.EMPTY;
        }

        StringBuilder sb = new StringBuilder();
        if (defaultDisplayHint != null) {
            sb.append(StringEscapeUtils.escapeHtml3(defaultDisplayHint));
            sb.append(" ");
        }
        sb.append("Kind: ");
        sb.append(StringEscapeUtils.escapeHtml3(kind.getName()));
        sb.append(" (");
        sb.append(kind.getId());
        sb.append(")");

        return sb.toString();
    }

    /**
     * Format the lifecycle status enumeration.
     *
     * @param state The status to format
     * @return The formatted string, empty if the status enumeration is null. The description of the statusEnum is
     *         returned if the statusEnum is not null, but a translation cannot be found.
     */
    @Override
    public String formatRequestState(RequestStateDTO state) {
        if (state == null) {
            return null;
        }
        String key = buildRequestStateKey(state.getName());
        return getFormattedMessage(key, state.getName());
    }

    @Override
    public String formatRequestState(String stateName) {
        if (stateName == null) {
            return null;
        }
        String key = buildRequestStateKey(stateName);
        return getFormattedMessage(key, stateName);
    }

    @Override
    public String formatAgreementState(AgreementStateDTO state) {
        if (state == null) {
            return null;
        }
        String key = buildAgreementStateKey(state.getName());
        return getFormattedMessage(key, state.getName());
    }

    @Override
    public String formatEnum(IEnum immutableEnum) {
        if (immutableEnum == null) {
            return null;
        }
        return immutableEnum.getName();
    }
    
    @Override
    public String formatEnum(EnumerationReference enumerationRef) {
        if (enumerationRef == null) {
            return null;
        }
        IEnumeration enumeration = typeManager.getEnumeration(new ApplicationContext(), enumerationRef);
        return formatEnum(enumeration);
    }
    
    /**
     * @see com.silvermoongroup.fsa.web.locale.ITypeFormatter#formatEnum(IEnumeration)
     */
    @SuppressWarnings("unchecked")
    @Override
    public String formatEnum(IEnumeration enumeration) {
        if (enumeration == null) {
            return null;
        }

        // get the to root of our enumerated class hierarchy
        Class<? extends IEnumeration> baseClass;
        Class<?> superClass = enumeration.getClass().getSuperclass();

        if (IEnumeration.class.isAssignableFrom(superClass)) {
            if (Modifier.isAbstract(superClass.getModifiers())) {
                baseClass = enumeration.getClass();
            } else {
                //noinspection ConstantConditions
                baseClass = (Class<? extends IEnumeration>) superClass;
            }
        } else {
            baseClass = enumeration.getClass();
        }

        return formatEnum(enumeration, baseClass);
    }
    
    private String formatEnum(IEnumeration value, Class<? extends IEnumeration> baseClass) {

        String className = baseClass.getSimpleName().toLowerCase();
        String packageName = baseClass.getPackage().getName();

        Matcher matcher = PROPERTY_ENUM_PATTERN.matcher(packageName);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Unable to derive component from package name: " + packageName);
        }
        String component = matcher.group(1);

        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(component).append(DOT).append(ENUM).append(DOT).append(className).append(DOT)
                .append(value.getName());
        String key = keyBuilder.toString();

        if (log.isDebugEnabled()) {
            log.debug("Looking up message to format enum with key [" + key + "]");
        }

        return getFormattedMessage(key, value.getName());
    }

    /**
     * @see com.silvermoongroup.fsa.web.locale.ITypeFormatter#formatMessage(java.lang.String, java.lang.String[])
     */
    @Override
    public String formatMessage(String key, String... parameters) {
        return getFormattedMessage(key, buildMissingKeyValue(key), parameters);
    }

    @Override
    public String formatMessageWithFallback(String key, String fallback, String... parameters) {
        return getFormattedMessage(key, fallback, parameters);
    }

    @Override
    public String formatType(Long toFormat) {
        if (toFormat == null) {
            return null;
        }
        Type type = getTypeManager().getType(new ApplicationContext(), toFormat);
        return formatType(type);
    }

    @Override
    public String formatType(Type toFormat) {
        if (toFormat == null) {
            return null;
        }
        String key = "common.type." + toFormat.getName();
        return formatMessageWithFallback(key, toFormat.getName());
    }

    @Override
    public String formatTypeReference(ITypeReference type) {
        return formatType(type.getValue());
    }

    @Override
    public String formatObject(Object toFormat) {

        // the order of this if/else statement is important
        //
        String output;
        if (toFormat instanceof String) {
            output = (String) toFormat;
        } else if (toFormat instanceof Amount) {
            output = formatAmount((Amount) toFormat);
        } else if (toFormat instanceof Number) {
            output = String.valueOf(toFormat);
        } else if (toFormat instanceof Date) {
            output = formatDate((Date) toFormat);
        } else if (toFormat instanceof DateTime) {
            output = formatDateTime((DateTime) toFormat);
        } else if (toFormat instanceof KindDTO) {
            output = formatKind((KindDTO) toFormat);
        } else if (toFormat instanceof EnumerationReference) {
            output = formatEnum((EnumerationReference) toFormat);
        } else if (toFormat instanceof IEnum) {
            output = formatEnum((IEnum) toFormat);
        } else if (toFormat instanceof Type) {
            output = formatType((Type) toFormat);
        } else if (toFormat instanceof RequestStateDTO) {
            output = formatRequestState((RequestStateDTO) toFormat);
        } else if (toFormat instanceof AgreementStateDTO) {
            output = formatAgreementState((AgreementStateDTO) toFormat);
        } else if (toFormat instanceof Boolean) {
            output = formatBoolean((Boolean) toFormat);
        } else if (toFormat instanceof ComponentListDTO) {
            ComponentListDTO dto = (ComponentListDTO)toFormat;
            output = formatSpecificationObjectName(dto.getKind(), dto.getDefaultDisplayName());
        } else if (toFormat == null) {
            output = StringUtils.EMPTY;
        } else {
            throw new IllegalArgumentException("Unable to format object of class: " + toFormat.getClass());
        }

        return output;
    }

    private String formatBoolean(Boolean toFormat) {
        return formatMessage("label.yesno." + (toFormat ? "true" : "false"));
    }

    @Override
    public String[] formatObjects(List<Object> objectsToFormat) throws IllegalArgumentException {

        String[] result;
        if (objectsToFormat == null || objectsToFormat.isEmpty()) {
            return new String[0];
        } else {
            result = new String[objectsToFormat.size()];
        }

        for (int i = 0; i < result.length; i++) {
            result[i] = formatObject(objectsToFormat.get(i));
        }

        return result;
    }

    /**
     * Generate an HTML div containing the object id with a tool tip that provides information about the object reference
     *
     * @param objectReference The object reference.
     * @return A div containing the identity of the object (taken from the object reference) and a tooltip
     */
    @Override
    public String generateIdentityDivWithObjectReferenceTooltip(ObjectReference objectReference) {

        if (objectReference == null) {
            return StringUtils.EMPTY;
        }

        String toolTip = StringEscapeUtils.escapeHtml3(
                String.format(
                        OBJECT_REFERENCE_TOOLTIP_FORMAT,
                        objectReference,
                        formatMessage("component." + objectReference.getComponentId()),
                        formatType(objectReference.getTypeId()),
                        objectReference.getObjectId()
                )
        );

        return String.format(OBJECT_REFERENCE_FORMAT, toolTip, objectReference.getObjectId());
    }

    @Override
    public String generateTypeDivWithObjectReferenceTooltip(ObjectReference objectReference) {
        return generateDivWithObjectReferenceTooltip(objectReference, null);
    }

    @Override
    public String generateDivWithObjectReferenceTooltip(ObjectReference objectReference, String name) {

        if (objectReference == null) {
            return StringUtils.EMPTY;
        }

        if (name == null) {
            name = formatType(objectReference.getTypeId());
        }

        String toolTip = StringEscapeUtils.escapeHtml3(
                String.format(
                        OBJECT_REFERENCE_TOOLTIP_FORMAT,
                        objectReference,
                        formatMessage("component." + objectReference.getComponentId()),
                        formatType(objectReference.getTypeId()),
                        objectReference.getObjectId()
                )
        );
        return String.format(OBJECT_REFERENCE_FORMAT, toolTip, StringEscapeUtils.escapeHtml3(name));
    }


    @Override
    public String formatCategory(long toFormat) {
        Category category = (Category) typeManager.getCategory(new ApplicationContext(), new ObjectReference(
                Components.COMMON.longValue(), CoreTypeReference.CATEGORY.getValue(), toFormat));
        return category.getName();
    }


    // helpers

    private String buildMissingKeyValue(String key) {
        return MISSING_KEY_PREFIX_SUFFIX + key + MISSING_KEY_PREFIX_SUFFIX;
    }

    private String buildKindKey(KindDTO kind) {

        if (kind == null) {
            return null;
        }
        return buildKindKey(kind.getCategory(), kind.getName());
    }

    private String buildKindKey(String category, String name) {

        if (name == null || category == null) {
            return null;
        }

        return KIND_PREFIX + category.toLowerCase() + DOT + name;
    }

    private String buildRequestStateKey(String name) {

        if (name == null) {
            return null;
        }
        return REQUEST_STATE_PREFIX + name;
    }

    private String buildAgreementStateKey(String name) {

        if (name == null) {
            return null;
        }
        return AGREEMENT_STATE_PREFIX + name;
    }

    private String getFormattedMessage(String key, String defaultIfNotPresent, String... parameters) {
        if (key == null) {
            return StringUtils.EMPTY;
        }
        Locale locale = getUserProfileManager().getLocale();
        return messageSource.getMessage(key, parameters, defaultIfNotPresent, locale);
    }

    private IUserProfileManager getUserProfileManager() {
        return userProfileManager;
    }

    private IProductDevelopmentService getTypeManager() {
        return typeManager;
    }
}
