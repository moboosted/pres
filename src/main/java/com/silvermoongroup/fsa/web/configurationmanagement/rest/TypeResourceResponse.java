package com.silvermoongroup.fsa.web.configurationmanagement.rest;

import com.silvermoongroup.fsa.web.configurationmanagement.dto.TypeDTO;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TypeResourceResponse {

    private String message;
    private TypeDTO typeDTO;

    public TypeResourceResponse(String message, TypeDTO typeDTO){
        this.message = message;
        this.typeDTO = typeDTO;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TypeDTO getTypeDTO() {
        return typeDTO;
    }

    public void setTypeDTO(TypeDTO typeDTO) {
        this.typeDTO = typeDTO;
    }
    
    
}
