package org.robobinding.annotation;

import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used on {@link ItemPresentationModel} implementation to prevent preinitialization of item's view.
 * In such case, the implementation should have change support and take care of calling
 * {@link PresentationModelChangeSupport#refreshPresentationModel()} when needed.
 *
 * @author Zbigniew Malinowski
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DoNotPreinitialize {
}
