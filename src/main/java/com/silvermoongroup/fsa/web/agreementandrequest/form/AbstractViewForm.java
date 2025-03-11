/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.form;

import com.silvermoongroup.businessservice.policymanagement.dto.StructuredActualDTO;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.form.ValidatorForm;
import com.silvermoongroup.fsa.web.common.format.PropertyInputName;
import com.silvermoongroup.fsa.web.common.format.PropertyInputNameUtils;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mubeen
 */
public abstract class AbstractViewForm<T extends StructuredActualDTO> extends ValidatorForm {

    private static final long serialVersionUID = 1L;

    /**
     * Utilised during the creation of Roles, Components
     */
    private String kind;

    /** This should be a Request or AgreementVersion ObjectReference */
    private ObjectReference contextObjectReference;

    /** The Role Conformance Types */
    private Long [] conformanceTypes;

    /**
     * This path forms the base path of our navigation in generic fashion
     */
    private String contextPath;
    
    /**
     * The path to the construct (eg: Role) that is to be added, updated
     */
    private String path;
    
    private Map<PropertyInputName, Object> inputProperties = new HashMap<>();

    private Map<PropertyInputName, String> inputPropertiesValidationMessages = new HashMap<>();

    private String initialMethod;

    private String initialUrl;

    /**
     * The StructuredActual(RequestDTO, AgreementDTO, RoleDTO) View that we are currently busy with.
     */
    private T structuredActualDTO;

    /**
     * Utilised to send Information Messages back to the end-user
     */
    private ActionMessages actionMessages = new ActionMessages();

    /**
     * @return the kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * @param kind
     *            the kind to set
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * @return the contextObjectReference
     */
    public ObjectReference getContextObjectReference() {
        return contextObjectReference;
    }

    /**
     * @param contextObjectReference
     *            the contextObjectReference to set
     */
    public void setContextObjectReference(ObjectReference contextObjectReference) {
        this.contextObjectReference = contextObjectReference;
    }

    /**
     * @return the roleTypes
     */
    public Long [] getConformanceTypes() {
        return conformanceTypes;
    }

    /**
     * @param roleTypes
     *            the roleTypes to set
     */
    public void setConformanceTypes(Long [] roleTypes) {
        this.conformanceTypes = roleTypes;
    }

    /**
     * @return the contextPath
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * @param contextPath
     *            the contextPath to set
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
    
    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    public Map<PropertyInputName, String> getInputPropertiesValidationMessages() {
        return inputPropertiesValidationMessages;
    }

    public void setInputPropertiesValidationMessages(Map<PropertyInputName, String> inputPropertiesValidationMessages) {
        this.inputPropertiesValidationMessages = inputPropertiesValidationMessages;
    }

    public Map<PropertyInputName, Object> getAllInputProperties() {
        return inputProperties;
    }

    /**
     * This method is invoked by struts when it binds the properties to the form. For example, if we have the following
     * input fields defined on the form:
     * <p>
     * &lt;input type="text" name="inputProperties(1011|String)" value="a" /&gt;<br>
     * &lt;input type="text" name="inputProperties(2122|String)" value="b" /&gt;
     *
     * Then this method will be called twice:
     * <ol>
     * <li>once with the key '1011|String' and the value 'a'</li>
     * <li>and once with the key '2122|String' and the value 'b'</li>
     * </ol>
     * 
     * @param key
     *            The property key to parse.
     * @param value
     *            The property value.
     */
    public void setInputProperties(String key, Object value) {
        PropertyInputName propertyInputName = PropertyInputNameUtils.parse(key);
        getAllInputProperties().put(propertyInputName, value);
    }

    public Object getInputProperties(PropertyInputName key) {
        return getAllInputProperties().get(key);
    }

    /**
     * @return the initialMethod
     */
    public String getInitialMethod() {
        return initialMethod;
    }

    /**
     * @param initialMethod
     *            the initialMethod to set
     */
    public void setInitialMethod(String initialMethod) {
        this.initialMethod = initialMethod;
    }

    /**
     * @return the initialUrl
     */
    public String getInitialUrl() {
        return initialUrl;
    }

    /**
     * @param initialUrl
     *            the initialUrl to set
     */
    public void setInitialUrl(String initialUrl) {
        this.initialUrl = initialUrl;
    }

    /**
     * @return the structuredActualDTO
     */
    public T getStructuredActualDTO() {
        return structuredActualDTO;
    }

    /**
     * @param structuredActualDTO
     *            the structuredActualDTO to set
     */
    public void setStructuredActualDTO(T structuredActualDTO) {
        this.structuredActualDTO = structuredActualDTO;
    }

    /**
     * @return the actionMessages
     */
    public ActionMessages getActionMessages() {
        return actionMessages;
    }

    public final void resetPropertyValidationAttributes() {
        inputProperties = new HashMap<>();
        inputPropertiesValidationMessages = new HashMap<>();
        initialMethod = null;
        initialUrl = null;
    }

}
