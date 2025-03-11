package com.silvermoongroup.fsa.web.context.support;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.PropertyMessageResourcesFactory;

public class ReloadablePropertyMessageResourcesFactory extends PropertyMessageResourcesFactory {

    public MessageResources createResources(String config) {
        return new ReloadablePropertyMessageResources(this, config, this.getReturnNull());
    }

}
