
package com.silvermoongroup.fsa.web.enumeration;

import com.silvermoongroup.businessservice.customerrelationshipmanagement.service.intf.ICustomerRelationshipService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.UnitOfMeasureEnumeration;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.domain.intf.IInternalCompanyCodeEnumeration;
import com.silvermoongroup.common.domain.intf.IUnitOfMeasureEnumeration;
import com.silvermoongroup.fsa.web.common.displaytag.decorator.AbstractTableDecorator;
import com.silvermoongroup.party.domain.Party;
import com.silvermoongroup.party.domain.PartyName;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class EnumerationsTableDecorator extends AbstractTableDecorator {

    public Object getValue() {
        IEnumeration currentRowObject = (IEnumeration) getCurrentRowObject();
        return currentRowObject.getId();
    }
    
    public Object getName() {
        IEnumeration currentRowObject = (IEnumeration) getCurrentRowObject();
        return currentRowObject.getName();
    }
    
    public Object getDescription() {
        IEnumeration currentRowObject = (IEnumeration) getCurrentRowObject();
        return currentRowObject.getDescription();
    }

    public Object getAbbreviation() {
        IEnumeration currentRowObject = (IEnumeration) getCurrentRowObject();
        return currentRowObject.getAbbreviation();
    }
    
    public Object getStartDate() {
        IEnumeration currentRowObject = (IEnumeration) getCurrentRowObject();
        return formatDate(currentRowObject.getEffectivePeriod().getStart());
    }
    
    public Object getEndDate() {
        IEnumeration currentRowObject = (IEnumeration) getCurrentRowObject();
        return formatDate(currentRowObject.getEffectivePeriod().getEnd());
    }
    
    public Object getMeasure() {
        IUnitOfMeasureEnumeration currentRowObject = (UnitOfMeasureEnumeration) getCurrentRowObject();
        return currentRowObject.getMeasure().name();
    }

    public Object getOrganisation() {
        IInternalCompanyCodeEnumeration currentRowObject = (IInternalCompanyCodeEnumeration) getCurrentRowObject();

        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(getPageContext()
                .getServletContext());
        ICustomerRelationshipService service = wac.getBean(ICustomerRelationshipService.class);
        Party party = service.getParty(new ApplicationContext(), currentRowObject.getObjectReference());
        PartyName name = service.getDefaultPartyNameForRolePlayer(new ApplicationContext(), party.getObjectReference());
        return getFormattedPartyName(name) + " ("
                + ObjectReference.convertToString(currentRowObject.getObjectReference()) + ")";
    }
}
