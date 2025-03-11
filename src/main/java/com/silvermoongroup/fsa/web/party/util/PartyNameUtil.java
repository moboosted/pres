/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.util;

import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.party.domain.PartyName;
import com.silvermoongroup.party.domain.PersonName;
import com.silvermoongroup.party.domain.UnstructuredName;
import org.apache.commons.lang3.StringUtils;

/**
 * Utilities for working with a party name.
 * 
 * @author Justin Walsh
 */
public class PartyNameUtil {

    private static final String UNKNOWN = "Unknown";

    private PartyNameUtil() {}

    /**
     * @param partyName                 The party name DTO.
     * @param productDevelopmentService
     * @return The full party name as a String.
     */
    public static String getPartyFullName(PartyName partyName, IProductDevelopmentService productDevelopmentService) {

        String fullName = UNKNOWN;
        if (partyName != null) {
            if (partyName instanceof UnstructuredName) {
                fullName = ((UnstructuredName) partyName).getFullName();
            } else if (partyName instanceof PersonName) {
                fullName = partyName.getFullName() != null ? partyName.getFullName() : getFullName((PersonName) partyName, productDevelopmentService);
            }
        }
        return fullName;
    }

    private static String getFullName(PersonName name, IProductDevelopmentService productDevelopmentService) {
        IEnumeration prefixTitle = null;
        if (name.getPrefixTitles() !=null){
            prefixTitle = productDevelopmentService.getEnumeration(new ApplicationContext(), name.getPrefixTitles());
        }
        return join(
                new String[]{prefixTitle != null ? prefixTitle.getName() : "", name.getFirstName(),
                        name.getMiddleNames(), name.getLastName(), name.getSuffixTitles()});
    }

    /**
     * <p>
     * Joins the elements of the provided array into a single String containing
     * the provided list of elements separated by spaces.
     *
     */
    private static String join(String[] array) {
        StringBuffer result = new StringBuffer();

        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                String string = array[i];
                if (!StringUtils.isBlank(string)) {
                    result.append(string.trim()).append(" ");
                }
            }
        }

        return result.toString().trim();
    }
}
