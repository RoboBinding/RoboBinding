package org.robobinding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used on a Getter of a presentation model to list any properties this property is dependent on.
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DependsOnStateOf {
	String[] value();
}
