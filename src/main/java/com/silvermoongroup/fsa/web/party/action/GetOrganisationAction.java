/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.action;

import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.party.form.GetOrganisationForm;
import com.silvermoongroup.fsa.web.party.util.PersonSearchComparator;
import com.silvermoongroup.fsa.web.party.vo.PartySearchResultVO;
import com.silvermoongroup.party.criteria.OrganisationSearchCriteria;
import com.silvermoongroup.party.domain.Organisation;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Jordan Olsen on 2017/04/26.
 */
public class GetOrganisationAction extends AbstractPartyAction {

    /**
     * GET: The page is rendered for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        GetOrganisationForm form = (GetOrganisationForm) actionForm;

        Type type = new Type();
        type.setName(CoreTypeReference.ORGANISATION.getName());
        type.setId(CoreTypeReference.ORGANISATION.getValue());

        Set<Type> types = getProductDevelopmentService().getAllSubTypes(new ApplicationContext(), type.getId());
        form.setOrganisationTypes(types);

        return actionMapping.getInputForward();
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("button.search", "search");

        return map;
    }

    public ActionForward search(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {

        GetOrganisationForm form = (GetOrganisationForm) actionForm;

        ApplicationContext applicationContext = new ApplicationContext();
        OrganisationSearchCriteria organisationSearchCriteria = new OrganisationSearchCriteria();

        organisationSearchCriteria.setFullName(StringUtils.trimToNull(form.getTxtSearchOrgFullName()));
        String typeName = StringUtils.trimToNull(form.getTxtSearchOrgType());

        if (typeName != null) {
            Type type = getTypeForName(typeName);
            organisationSearchCriteria.setType(type.getId());
        }

        organisationSearchCriteria.setBasic(true);

        List<PartySearchResultVO> organisationLists = new ArrayList<>();
        List<Organisation> searchResults = getCustomerRelationshipService().retrieveParties(applicationContext, organisationSearchCriteria);

        for (Organisation organisation : searchResults) {

            PartySearchResultVO matchSelect = new PartySearchResultVO();
            matchSelect.setObjectReference(String.valueOf(organisation.getObjectReference()));

            Type type = getProductDevelopmentService().getType(applicationContext, organisation.getTypeId());
            matchSelect.setPartyType(type.getName());

            if (organisation.getDefaultName() != null) {
                matchSelect.setFullName(organisation.getDefaultName().getFullName());
            }


            organisationLists.add(matchSelect);
        }

        Collections.sort(organisationLists, new PersonSearchComparator(getTypeParser()));
        form.setOrganisationLists(organisationLists);

        return actionMapping.getInputForward();
    }
}
