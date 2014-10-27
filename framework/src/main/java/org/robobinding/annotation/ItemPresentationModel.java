package org.robobinding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used on a Getter of a presentation model to configure the ItemPresentationModel information for the data-set property.
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ItemPresentationModel {
	Class<? extends org.robobinding.itempresentationmodel.ItemPresentationModel<?>> value();

	/**
	 * Optional. By default, a ItemPresentationModel is created using its
	 * default constructor. When the factoryMethod is specified. A
	 * {@link org.robobinding.itempresentationmodel.ItemPresentationModel} is created by invoking the
	 * PresentationModel.factoryMethod(). In this way, the {@link org.robobinding.itempresentationmodel.ItemPresentationModel} 
	 * instances can be configured freely.
	 */
	String factoryMethod() default "";
}
