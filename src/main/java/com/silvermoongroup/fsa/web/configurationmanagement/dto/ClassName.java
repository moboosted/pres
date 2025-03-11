package com.silvermoongroup.fsa.web.configurationmanagement.dto;

/**
 * Created by koen on 24.03.16.
 */

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "ClassName")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(
        name = "ClassNameType",
        propOrder = {"name"}
)
public class ClassName {

    @XmlElement(name = "name")
    String name;

    public ClassName() {
    }

    public ClassName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
