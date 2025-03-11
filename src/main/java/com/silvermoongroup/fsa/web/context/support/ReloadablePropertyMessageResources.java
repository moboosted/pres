package com.silvermoongroup.fsa.web.context.support;

import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;

import java.util.ResourceBundle;

public class ReloadablePropertyMessageResources extends PropertyMessageResources {

    public ReloadablePropertyMessageResources(MessageResourcesFactory factory, String config) {
        super(factory, config);
    }

    public ReloadablePropertyMessageResources(MessageResourcesFactory factory, String config, boolean returnNull) {
        super(factory, config, returnNull);
    }

    public synchronized void reload() {
        log.info("Reloading message properties");
        locales.clear();
        messages.clear();
        formats.clear();
        ResourceBundle.clearCache(Thread.currentThread().getContextClassLoader());

    }

}
