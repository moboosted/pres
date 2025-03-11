/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.action;

import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.fsa.web.party.form.GetPersonForm;
import com.silvermoongroup.fsa.web.party.util.PersonSearchComparator;
import com.silvermoongroup.fsa.web.party.vo.PartySearchResultVO;
import com.silvermoongroup.party.criteria.PersonSearchCriteria;
import com.silvermoongroup.party.domain.Person;
import com.silvermoongroup.party.domain.PersonName;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Jordan Olsen on 2017/04/20.
 */
public class GetPersonAction extends AbstractPartyAction {

    /**
     * GET: The page is rendered for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
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

        GetPersonForm form = (GetPersonForm) actionForm;

        ApplicationContext applicationContext = new ApplicationContext();
        PersonSearchCriteria personSearchCriteria = new PersonSearchCriteria();

        personSearchCriteria.setLastName(StringUtils.trimToNull(form.getTxtSearchSurname()));
        personSearchCriteria.setFirstName(StringUtils.trimToNull(form.getTxtSearchFirstName()));
        personSearchCriteria.setMiddleNames(StringUtils.trimToNull(form.getTxtSearchMiddleName()));

        personSearchCriteria.setBasic(true);

        List<PartySearchResultVO> personLists = new ArrayList<>();
        List<Person> searchResults = getCustomerRelationshipService().retrieveParties(applicationContext, personSearchCriteria);

        for (Person person : searchResults) {
            PartySearchResultVO matchSelect = new PartySearchResultVO();
            matchSelect.setObjectReference(String.valueOf(person.getObjectReference()));

            if (person.getBirthDate() != null) {
                matchSelect.setBirthDate(getTypeFormatter().formatDate(person.getBirthDate()));
            }

            PersonName personName = null;
            PersonName defaultName = (PersonName) person.getDefaultName();

            if (defaultName != null) {
                personName = defaultName;
            } else if (!person.getAllNames().isEmpty()) {
                personName = (PersonName) person.getAllNames().iterator().next();
            } else {
                matchSelect.setFullName("Unknown");
                matchSelect.setSurname("Unknown");
                matchSelect.setFirstname("Unknown");
            }

            if (personName == null) {
                personLists.add(matchSelect);
                continue;
            }

            matchSelect.setFullName(personName.getFullName());
            matchSelect.setSurname(personName.getLastName());
            matchSelect.setFirstname(personName.getFirstName());
            matchSelect.setMiddlename(personName.getMiddleNames());
            matchSelect.setInitials(personName.getInitials());

            if (personName.getPrefixTitles() != null) {
                IEnumeration prefixTitle = getProductDevelopmentService().getEnumeration(applicationContext,
                        personName.getPrefixTitles());
                matchSelect.setTitle(prefixTitle.getName());
            }

            personLists.add(matchSelect);
        }

        Collections.sort(personLists, new PersonSearchComparator(getTypeParser()));
        form.setPersonLists(personLists);

        return actionMapping.getInputForward();
    }
}