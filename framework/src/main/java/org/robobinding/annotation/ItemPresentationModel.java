package org.robobinding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.robobinding.itempresentationmodel.ViewTypeSelectionContext;

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
	 * default constructor. When specified, an
	 * ItemPresentationModel will be created by invoking the
	 * PresentationModel.factoryMethod(int itemViewType), which the itemViewType parameter is optional.
	 * factoryMethod return type is the ItemPresentationModel. 
	 * In this way, the {@link org.robobinding.itempresentationmodel.ItemPresentationModel} 
	 * instances can be configured freely.
	 */
	String factoryMethod() default "";
	
	/**
	 * Optional. Used when having multiple view layouts for different items.
	 * when specified, PresentationModel.viewTypeSelector({@link ViewTypeSelectionContext} context) method wil be invoked to get view type,
	 * which in turn to pick a view layout for current item. The context parameter is optional.
	 * viewTypeSelector return type is int, ranging from 0 to ({@link ViewTypeSelectionContext#getViewTypeCount()} - 1).
	 */
	String viewTypeSelector() default "";
	
	/**
	 * Optional. Used to change preInitializingViews behavior. Default to {@link PreInitializingViews#YES}.
	 */
	PreInitializingViews preInitializingViews() default PreInitializingViews.YES;
}
