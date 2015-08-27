package org.robobinding.codegen.presentationmodel.typemirror;

import org.robobinding.itempresentationmodel.ItemViewFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used in presentation models to configure the ItemPresentationModel
 * information for a data-set property.
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
	 * ItemPresentationModel is created by invoking the
	 * PresentationModel.factoryMethod().
	 */
	String factoryMethod() default "";

	/**
	 * Optional. When the ItemViewFactory is specified, you can configure item view layout freely.
	 * @return
	 */
	Class<? extends org.robobinding.itempresentationmodel.ItemViewFactory> viewFactory() default ItemViewFactory.Default.class;

}
