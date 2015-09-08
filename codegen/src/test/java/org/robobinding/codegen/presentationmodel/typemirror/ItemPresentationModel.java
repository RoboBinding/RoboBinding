package org.robobinding.codegen.presentationmodel.typemirror;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link org.robobinding.annotation.ItemPresentationModel}}
 * @since 1.0
 * @author Cheng Wei
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ItemPresentationModel {
	Class<? extends org.robobinding.itempresentationmodel.ItemPresentationModel<?>> value();

	String factoryMethod() default "";
	
	String viewTypeSelector() default "";
}
