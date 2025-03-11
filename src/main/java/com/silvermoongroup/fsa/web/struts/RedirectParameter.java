package com.silvermoongroup.fsa.web.struts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The field, the value of which should be used when redirecting constructing a redirect.
 * <br>
 * This assumes that a string representation of the parameter exists, and the that this representation
 * can be converted back into the object at the 'far end' of the redirect. 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedirectParameter {

}
