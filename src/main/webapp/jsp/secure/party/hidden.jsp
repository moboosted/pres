<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:hidden property="differentRoleSelected"/> 
<html:hidden property="tabToChangeTo"/>          
<html:hidden property="showNameButton"/>
<html:hidden property="defaultPartyNameId"/>    
<html:hidden property="addContactPressed"/>
<html:hidden property="editContactPressed"/>            
<html:hidden property="defContPrefName"/>
<html:hidden property="contactId"/> 
<html:hidden property="editCPType"/>
<html:hidden property="physicalContactEdit"/>
<html:hidden property="postalContactEdit"/>
<html:hidden property="postalCodeStore"/>
<html:hidden property="currentContactSelectedId"/>
<html:hidden property="txtBirthDateStore"/>
<html:hidden property="partyRolesSize" />
<html:hidden property="bankTabMode" styleId="bankTabMode" />
<html:hidden property="partyIsReadOnly"/>
<html:hidden property="readOnly" styleId="readOnly" />
<html:hidden property="overRideDups"/>
<html:hidden property="duplicatesFound"/>
<html:hidden property="generalTabLoaded"/>
<html:hidden property="relationshipsTabLoaded"/>
<html:hidden property="rolesTabLoaded"/>
<html:hidden property="contactTabLoaded"/>
<html:hidden property="bankTabLoaded"/>
<html:hidden property="regTabLoaded"/>
<html:hidden property="genTabFirstLoad"/>
<html:hidden property="rolesTabFirstLoad"/>
<html:hidden property="contactTabFirstLoad"/>
<html:hidden property="bankTabFirstLoad"/>
<html:hidden property="regTabFirstLoad"/>
<html:hidden property="searchTabFirstLoad"/>
<html:hidden property="postAddrRegionChanged"/>
<html:hidden property="physAddrRegionChanged"/>
<html:hidden property="unstructAddrRegionChanged"/>
<html:hidden property="fullName"/>
<html:hidden property="key" styleId="key"/>

<%-- Client side variables --%>
<input type="hidden" name="selectedPreferenceId" value=""/>