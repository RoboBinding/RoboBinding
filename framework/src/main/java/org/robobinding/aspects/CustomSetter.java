package org.robobinding.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * It is for annotating a setter so that AspectJ will not do any auto weaving
 * for the method.
 * 
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface CustomSetter {

}
