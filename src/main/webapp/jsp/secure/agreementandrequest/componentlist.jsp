<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>


<hr>
<c:forEach items="${structuredActualDTO.componentLists}" var="componentListDTO">

    <%-- The component list header --%>
	<tr>
    	<td colspan="2" class="lightgreybold" valign="top" title="<smg:fmtSpecificationObjectHint value="${componentListDTO}" />">
      
            <div style="float:left;">
                <smg:fmtSpecificationObjectName value="${componentListDTO}"/>&nbsp;
              <sup style="font-weight: normal;">(<c:out value="${smg:getComponentListCardinality(pageContext, componentListDTO)}" />)</sup>
            </div>

            <c:if test="${smg:canComponentBeAdded(pageContext, componentListDTO)}">
                <div>
                    <html:form action="${contextPath}.do">
                <c:if test="${!empty currentPath}">
                  <html:hidden property="path" value="${currentPath}" />
                </c:if>
                <html:hidden property="contextObjectReference" value="${contextObjectReference}" />
                <html:hidden property="kind" value="${componentListDTO.kind}" />
                <html:hidden property="method" value=".addComponent" />
                <html:submit styleId="add-component-kind-${componentListDTO.kind.name}" styleClass="addcomponentbutton" disabled="${componentListDTO.readOnly}">
                  <fmt:message key="page.agreement.action.addcomponent" />
                </html:submit>
              </html:form>
              </div>
            </c:if>
    	</td>
              
	</tr>
    
    <%-- Output a row for each component in the component list --%>
    <c:forEach items="${componentListDTO.components}" var="componentDTO" varStatus="componentDTOLoopStatus">
    
        <tr>
    	
    	<c:set var="path" value="${componentDTO.path}" scope="page" />
    
    	<%-- View/update link --%>
    	<td class="title">                
    		<span title="<smg:fmtSpecificationObjectHint value="${componentDTO}" />">
    	          	<smg:fmtSpecificationObjectName value="${componentDTO}" />
    		</span>
    	</td>
    	
        <td class="title" align="center">
    	<c:choose>
    		<c:when test="${!componentListDTO.readOnly}">
    			<%-- Update button --%>
  				<html:form action="${contextPath}.do" style="float:left">
  					<html:hidden property="contextObjectReference" value="${contextObjectReference}" />
  					<html:hidden property="method" value=".viewComponent" />
  					<html:hidden property="path" value="${path}" />
  					<html:submit styleId="update-component-kind-${componentListDTO.kind.name}-${componentDTO.index}" styleClass="updatecomponentbutton">
  						<fmt:message key="button.update" />
  					</html:submit>
  				</html:form>              
    			
    			<%-- Delete button --%>
                <c:if test="${smg:canComponentBeDeleted(pageContext, componentListDTO)}">
    				<html:form action="${contextPath}.do">
                        <input type="hidden" name="confirmMessage" value='<smg:fmt value="page.agreement.message.confirmcomponentdelete" arg="${componentListDTO.kind.name}" />' />
    					<html:hidden property="contextObjectReference" value="${contextObjectReference}" />
    					<html:hidden property="method" value=".deleteComponent" />
    					<html:hidden property="path" value="${path}" />
                        <html:hidden property="kind" value="${componentListDTO.kind}" />
    					<html:submit styleId="delete-component-kind-${componentListDTO.kind.name}-${componentDTO.index}" styleClass="deletecomponentbutton">
    						<fmt:message key="button.delete" />
    					</html:submit>
    				</html:form>
                </c:if>
    		</c:when>
    		<c:otherwise>
    			<%-- View button --%>
  				<html:form action="${contextPath}.do">
  					<html:hidden property="contextObjectReference" value="${contextObjectReference}" />
  					<html:hidden property="method" value=".viewComponent" />
  					<html:hidden property="path" value="${path}" />
  					<html:submit styleId="update-component-kind-${componentListDTO.kind.name}-${componentDTO.index}" styleClass="updatecomponentbutton">
  						<fmt:message key="button.view" />
              
  					</html:submit>
  				</html:form>
    		</c:otherwise>
    	</c:choose>
      
      </td>
      
      </tr>
      
    </c:forEach>
    
</c:forEach>