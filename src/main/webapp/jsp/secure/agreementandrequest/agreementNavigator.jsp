<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<div>
    <ul class="tree">

        <%-- The parent - taking into account that we may not have a parent --%>
        <c:if test="${structuredActualDTO.composite != null}">
        <c:url value="${contextPath}.do" var="compositeUrl">
            <c:param name="contextObjectReference" value="${contextObjectReference}"/>
            <c:param name="method" value=".back"/>
            <c:param name="path" value="${currentPath}"/>
        </c:url>


        <li><a href="${compositeUrl}"><smg:fmtSpecificationObjectName value="${structuredActualDTO.composite}"/></a>
            <ul>
                </c:if>

                <%--This is our current location (the top of the tree--%>
                <li><strong><smg:fmtSpecificationObjectName value="${structuredActualDTO}"/></strong>
                    <ul>
                        <c:forEach items="${structuredActualDTO.componentLists}" var="componentListDTO">

                        <li><smg:fmtSpecificationObjectName value="${componentListDTO}"/>&nbsp;
                            <sup style="font-weight: normal;">(<c:out
                                    value="${smg:getComponentListCardinality(pageContext, componentListDTO)}"/>)</sup>

                                <%--Add a component--%>
                            <c:if test="${smg:canComponentBeAdded(pageContext, componentListDTO)}">
                                <c:url value="${contextPath}.do" var="addComponentUrl">
                                    <c:if test="${!empty currentPath}">
                                        <c:param name="path" value="${currentPath}"/>
                                    </c:if>
                                    <c:param name="contextObjectReference" value="${contextObjectReference}"/>
                                    <c:param name="path" value="${structuredActualDTO.path}"/>
                                    <c:param name="method" value=".addComponent"/>
                                    <c:param name="kind" value="${componentListDTO.kind}"/>
                                </c:url>
                                &nbsp;<a id="add-component-kind-${componentListDTO.kind.name}" href="${addComponentUrl}" class="glyphicon glyphicon-plus validate-props" style="color: gray"></a>
                            </c:if>


                            <c:if test="${!empty (componentListDTO.components)}">
                                <ul>
                                    <c:forEach items="${componentListDTO.components}" var="componentDTO">

                                        <c:url value="${contextPath}.do" var="viewComponentUrl">
                                            <c:param name="contextObjectReference" value="${contextObjectReference}"/>
                                            <c:param name="method" value=".viewComponent"/>
                                            <c:param name="path" value="${componentDTO.path}"/>
                                        </c:url>

                                        <li>
                                            <a href="${viewComponentUrl}" class="validate-props"
                                               id="view-component-kind-${componentListDTO.kind.name}-${componentDTO.index}"><smg:fmtSpecificationObjectName
                                                    value="${componentDTO}"/></a>

                                                <%--Delete a component--%>
                                            <c:if test="${smg:canComponentBeDeleted(pageContext, componentListDTO)}">

                                                <c:set var="linkId"
                                                       value="delete-component-kind-${componentListDTO.kind.name}-${componentDTO.index}"
                                                       scope="page"/>

                                                <c:url value="${contextPath}.do" var="deleteComponentUrl">
                                                    <c:param name="contextObjectReference"
                                                             value="${contextObjectReference}"/>
                                                    <c:param name="method" value=".deleteComponent"/>
                                                    <c:param name="path" value="${componentDTO.path}"/>
                                                    <c:param name="kind" value="${componentListDTO.kind}"/>
                                                </c:url>
                                                &nbsp;<a id="${linkId}" data-kind="${componentListDTO.kind.name}"
                                                href="${deleteComponentUrl}" class="glyphicon glyphicon-trash confirm-delete validate-props" style="color: gray"></a>

                                            </c:if>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                            </c:forEach>
                        </li>
                    </ul>
                </li>

                <c:if test="${structuredActualDTO.composite != null}">
            </ul>
        </li>
        </c:if>
    </ul>
</div>
