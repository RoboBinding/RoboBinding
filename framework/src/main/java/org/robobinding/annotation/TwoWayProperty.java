package org.robobinding.annotation;

import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dhu on 15/8/28.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.ANNOTATION_TYPE })
public @interface TwoWayProperty {
    String name();
    Class<? extends TwoWayPropertyViewAttribute<?, ?, ?>> type();
}
