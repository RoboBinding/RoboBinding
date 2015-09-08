package org.robobinding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.TYPE })
public @interface ViewBinding {

	String[] simpleOneWayProperties() default {};

}
