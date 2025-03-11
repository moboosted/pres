package com.silvermoongroup.fsa.web.configurationmanagement.rest;

import com.silvermoongroup.fsa.web.configurationmanagement.dto.ClassName;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by koen on 24.03.16.
 */
@XmlRootElement
public class ComponentResponse {

    List<ClassName> classNames = new ArrayList<>(20);

    public ComponentResponse() {
    }

    public List<ClassName> getClassNames() {
        return classNames;
    }

    public void setClassNames(
            List<ClassName> classNames) {
        this.classNames = classNames;
    }
}
