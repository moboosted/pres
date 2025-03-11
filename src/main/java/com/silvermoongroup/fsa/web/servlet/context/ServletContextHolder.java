/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.servlet.context;

import javax.servlet.ServletContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Logger;


/**
 * Inspired by the apache deltaspike project.
 *
 * As of this writing, we can't use apache delta spike as there is an incompatibility between openwebbeans (used in
 * websphere) and deltaspike.  This incompatibility results in the following error on startup:
 * <pre>
 *     return type in the component implementation class : org.apache.deltaspike.servlet.impl.produce.ServletObjectProducer
 *     with passivating scope @javax.enterprise.context.SessionScoped must be Serializable
 * </pre>
 * It would appear as if openwebbeans is validating that a session scoped producer method returns a serializable
 * object (and HttpSession) is not serializable.
 */
public class ServletContextHolder {

    private static final Logger log = Logger.getLogger(ServletContextHolder.class.getName());

    private static final Map<ClassLoader, ServletContext> CONTEXT_BY_CLASSLOADER = Collections.synchronizedMap(
            new WeakHashMap<ClassLoader, ServletContext>());

    private ServletContextHolder()
    {
        // hide constructor
    }

    /**
     * Bind the supplied {@link ServletContext} to the current context class loader. Subsequent calls to {@link #get()}
     * with the same context class loader will always return this context.
     *
     * @param servletContext
     *            The context to bind to the context class loader
     */
    static void bind(ServletContext servletContext)
    {
        ClassLoader classLoader = ServletContextHolder.getClassLoader(null);
        ServletContext existingContext = CONTEXT_BY_CLASSLOADER.put(classLoader, servletContext);
        if (existingContext != null)
        {
            throw new IllegalArgumentException("There is already a ServletContext associated with class loader: "
                    + classLoader);
        }
    }

    /**
     * Returns the {@link ServletContext} associated with the current context class loader.
     *
     * @throws IllegalStateException
     *             if there is no {@link ServletContext} stored for the current context class loader
     */
    public static ServletContext get()
    {
        ClassLoader classLoader = ServletContextHolder.getClassLoader(null);
        ServletContext servletContext = CONTEXT_BY_CLASSLOADER.get(classLoader);
        if (servletContext == null)
        {
            throw new IllegalStateException("There is no ServletContext stored for class loader: " + classLoader);
        }
        return servletContext;
    }

    /**
     * Releases the {@link ServletContext} from the current context class loader. Subsequent calls to {@link #get()}
     * with the same context class loader will return <code>null</code>.
     */
    static void release()
    {
        ClassLoader classLoader = ServletContextHolder.getClassLoader(null);
        ServletContext removedContext = CONTEXT_BY_CLASSLOADER.remove(classLoader);
        if (removedContext == null)
        {
            log.warning("Cannot find a ServletContext to release for class loader: " + classLoader);
        }
    }

    /**
     * Detect the right ClassLoader.
     * The lookup order is determined by:
     * <ol>
     * <li>ContextClassLoader of the current Thread</li>
     * <li>ClassLoader of the given Object 'o'</li>
     * <li>ClassLoader of this very ClassUtils class</li>
     * </ol>
     *
     * @param o if not <code>null</code> it may get used to detect the classloader.
     * @return The {@link ClassLoader} which should get used to create new instances
     */
    public static ClassLoader getClassLoader(Object o)
    {
        if (System.getSecurityManager() != null)
        {
            return AccessController.doPrivileged(new GetClassLoaderAction(o));
        }
        else
        {
            return getClassLoaderInternal(o);
        }
    }

    static class GetClassLoaderAction implements PrivilegedAction<ClassLoader>
    {
        private Object object;
        GetClassLoaderAction(Object object)
        {
            this.object = object;
        }

        @Override
        public ClassLoader run()
        {
            try
            {
                return getClassLoaderInternal(object);
            }
            catch (Exception e)
            {
                return null;
            }
        }
    }

    private static ClassLoader getClassLoaderInternal(Object o)
    {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        if (loader == null && o != null)
        {
            loader = o.getClass().getClassLoader();
        }

        if (loader == null)
        {
            loader = ServletContextHolder.class.getClassLoader();
        }

        return loader;
    }
}
