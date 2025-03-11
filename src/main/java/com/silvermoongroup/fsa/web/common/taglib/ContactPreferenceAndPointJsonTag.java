/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.taglib;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import com.silvermoongroup.party.domain.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * Generate a JSON string representing the contact preferences and points for a party
 * 
 * @author Justin Walsh
 */
public class ContactPreferenceAndPointJsonTag extends TagSupport {

    private static final long serialVersionUID = 1L;
    
    private  Map<ContactPreference, SortedMap<String, SortedMap<Long, ContactPoint>>> contactPreferenceMap;

    @Override
    public int doStartTag() throws JspException {

        ITypeFormatter typeFormatter = getTypeFormatter();
        IProductDevelopmentService productDeveleopmentService = getProductDevelopmentService();

        List<Map<String, Object>> jsonContactPreferences = new ArrayList<>();

        for (ContactPreference contactPreference: contactPreferenceMap.keySet()) {

            // dig out the contact points for this preference
            List<ContactPoint> contactPoints = new ArrayList<>();
            for (SortedMap<Long, ContactPoint> pointByType : contactPreferenceMap.get(contactPreference).values()) {
                contactPoints.addAll(pointByType.values());
            }

            // create a new ContactPreference 'object'
            Map<String, Object> jsonCpref = new HashMap<>();
            jsonCpref.put("id", contactPreference.getId());
            jsonCpref.put("typeId", contactPreference.getTypeId());
            jsonCpref.put("startDate", formatDate(typeFormatter, contactPreference.getEffectivePeriod().getStart()));
            jsonCpref.put("endDate", formatDate(typeFormatter, contactPreference.getEffectivePeriod().getEnd()));
            final EnumerationReference preferredLanguage = contactPreference.getPreferredLanguage();
            IEnumeration language =  preferredLanguage != null
                    ? productDeveleopmentService.getEnumeration(new ApplicationContext(), preferredLanguage)
                    : null;
            jsonCpref.put("preferredLanguage", language == null ? null : language.getName());
            jsonCpref.put("defaultContactPreference", contactPreference.isDefault());

            // and create contact points for this contact preference
            List<Map<String, Object>> jsonContactPoints = new ArrayList<>();
            for (ContactPoint contactPoint: contactPoints) {
                Map<String, Object> jsonCpoint = new HashMap<>();
                jsonCpoint.put("id", contactPoint.getId());
                jsonCpoint.put("typeId", contactPoint.getTypeId());
                jsonCpoint.put("externalReference", contactPoint.getExternalReference());
                jsonCpoint.put("startDate", formatDate(typeFormatter, contactPoint.getEffectivePeriod().getStart()));
                jsonCpoint.put("endDate", formatDate(typeFormatter, contactPoint.getEffectivePeriod().getEnd()));

                jsonContactPoints.add(jsonCpoint);

                if (contactPoint instanceof TelephoneNumber) {
                    TelephoneNumber t = (TelephoneNumber)contactPoint;
                    jsonCpoint.put("baseTypeId", CoreTypeReference.TELEPHONENUMBER.getValue());
                    jsonCpoint.put("areaCode", t.getAreaCode());
                    jsonCpoint.put("countryCode", t.getCountryPhoneCode());
                    jsonCpoint.put("extension", t.getExtension());
                    jsonCpoint.put("localNumber", t.getLocalNumber());
                    jsonCpoint.put("mobile", t.getMobile());
                    jsonCpoint.put("startDate", formatDate(typeFormatter, t.getEffectivePeriod().getStart()));
                    jsonCpoint.put("endDate", formatDate(typeFormatter, t.getEffectivePeriod().getEnd()));
                }
                else if (contactPoint instanceof ElectronicAddress) {
                    ElectronicAddress e = (ElectronicAddress)contactPoint;
                    jsonCpoint.put("baseTypeId", CoreTypeReference.ELECTRONICADDRESS.getValue());
                    jsonCpoint.put("address", e.getAddress());

                }
                else if (contactPoint instanceof Address) {
                    Address a = (Address) contactPoint;
                    jsonCpoint.put("city", a.getCity());
                    jsonCpoint.put("country", String.valueOf(a.getCountry().getValue() + ":" + a.getCountry().getEnumerationTypeId()));
                    jsonCpoint.put("region", a.getRegion());
                    jsonCpoint.put("subregion", a.getSubregion());
                    jsonCpoint.put("postalCode", a.getPostalCode());

                    if (contactPoint instanceof PostalAddress) {
                        PostalAddress p = (PostalAddress)contactPoint;
                        jsonCpoint.put("baseTypeId", CoreTypeReference.POSTALADDRESS.getValue());
                        jsonCpoint.put("boxNumber", p.getBoxNumber());
                        jsonCpoint.put("postnetSuite", p.getPostnetSuite());
                    }
                    else if (contactPoint instanceof PhysicalAddress) {
                        PhysicalAddress p = (PhysicalAddress)contactPoint;
                        jsonCpoint.put("baseTypeId", CoreTypeReference.PHYSICALADDRESS.getValue());
                        jsonCpoint.put("street", p.getStreet());
                        jsonCpoint.put("houseNumber", p.getHouseNumber());
                        jsonCpoint.put("unitNumber", p.getUnitNumber());
                        jsonCpoint.put("floorNumber", p.getFloorNumber());
                        jsonCpoint.put("buildingName", p.getBuildingName());
                    }
                    else if (contactPoint instanceof UnstructuredAddress) {
                        UnstructuredAddress p = (UnstructuredAddress)contactPoint;
                        jsonCpoint.put("baseTypeId", CoreTypeReference.UNSTRUCTUREDADDRESS.getValue());
                        jsonCpoint.put("addressLine1", p.getAddressLine1());
                        jsonCpoint.put("addressLine2", p.getAddressLine2());
                        jsonCpoint.put("addressLine3", p.getAddressLine3());
                        jsonCpoint.put("addressLine4", p.getAddressLine4());
                    }
                }
            }

            jsonCpref.put("contactPoints", jsonContactPoints);
            jsonContactPreferences.add(jsonCpref);
        }
        
        // if I write this directly to the JSP writer, I get an error
        StringWriter out = new StringWriter(1024);
        try {
            getJsonObjectMapper().writeValue(out, jsonContactPreferences);
            pageContext.getOut().write(out.toString());
        } catch (IOException e) {
            throw new JspTagException(e);
        }
        finally {
            try {
                out.close();
            } catch (IOException e) {
                // no nothing
            }
        }

        return SKIP_BODY;

    }

    private String formatDate(ITypeFormatter typeFormatter, DateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return typeFormatter.formatDate(dateTime.getDate());
    }

    private String formatDate(ITypeFormatter typeFormatter, Date date) {
        if (date == null) {
            return null;
        }
        return typeFormatter.formatDate(date);
    }

    public Map<ContactPreference, SortedMap<String, SortedMap<Long, ContactPoint>>> getContactPreferenceMap() {
        return contactPreferenceMap;
    }
    public void setContactPreferenceMap(
            Map<ContactPreference, SortedMap<String, SortedMap<Long, ContactPoint>>> contactPreferenceMap) {
        this.contactPreferenceMap = contactPreferenceMap;
    }

    private ObjectMapper getJsonObjectMapper() {
        WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        return ac.getBean(ObjectMapper.class);
    }

    private ITypeFormatter getTypeFormatter() {
        WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        return ac.getBean(ITypeFormatter.class);
    }
    
    private IProductDevelopmentService getProductDevelopmentService() {
        WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        return ac.getBean(IProductDevelopmentService.class);
    }

}
