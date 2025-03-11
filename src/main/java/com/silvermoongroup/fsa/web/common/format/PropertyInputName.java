package com.silvermoongroup.fsa.web.common.format;

import java.io.Serializable;

public class PropertyInputName implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEPARATOR = "|";
    
    public PropertyInputName() {
    }

    private String kindId;
    private String type;

    public String getKindId() {
        return kindId;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(kindId);
        stringBuffer.append(SEPARATOR);
        stringBuffer.append(type);

        return stringBuffer.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((kindId == null) ? 0 : kindId.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PropertyInputName other = (PropertyInputName) obj;
        if (kindId == null) {
            if (other.kindId != null) {
                return false;
            }
        } else if (!kindId.equals(other.kindId)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }

}
