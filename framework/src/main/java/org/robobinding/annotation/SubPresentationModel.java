package org.robobinding.annotation;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@interface SubPresentationModel {
	PreInitializingViews preInitializingViews() default PreInitializingViews.DEFAULT;
}
