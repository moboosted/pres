package com.silvermoongroup.fsa.web.struts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates a method on an action class which should be invoked on form validation.
 * 
 * The method should take two parameters HttpServletRequest and an ActionForm (in that order)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnFormValidationFailure {

}
