/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.common.enumeration.intf.ITypeReference;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.apache.struts.taglib.html.SelectTag;

import java.util.Collection;

/**
 * Renders option elements given a collection of types.
 * 
 * @author Justin Walsh
 */
public class TypeReferenceOptionsTag extends AbstractOptionsTag {

    private static final long serialVersionUID = 1L;

    private Collection<ITypeReference> types;

    @Override
    void buildOptions(SelectTag selectTag, StringBuffer sb) {

        ITypeFormatter typeFormatter = getTypeFormatter();

        for (ITypeReference type: getTypes()) {
            addOption(
                    sb,
                    typeFormatter.formatTypeReference(type),
                    type.getName(),
                    selectTag.isMatched(type.getName())
                    );
        }
    }

    public Collection<ITypeReference> getTypes() {
        return types;
    }

    public void setTypes(Collection<ITypeReference> types) {
        this.types = types;
    }
}
