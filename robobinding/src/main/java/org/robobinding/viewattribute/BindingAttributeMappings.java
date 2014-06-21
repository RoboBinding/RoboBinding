package org.robobinding.viewattribute;

import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttribute;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface BindingAttributeMappings<T extends View> {
    void mapProperty(Class<? extends PropertyViewAttribute<T, ?>> propertyViewAttributeClass, String attributeName);
    void mapProperty(
	    PropertyViewAttributeFactory<T> propertyViewAttributeFactory,
	    String attributeName);

    void mapMultiTypeProperty(Class<? extends MultiTypePropertyViewAttribute<T>> multiTypePropertyViewAttributeClass, String attributeName);
    void mapMultiTypeProperty(
	    MultiTypePropertyViewAttributeFactory<T> multiTypePropertyViewAttributeFactory,
	    String attributeName);

    void mapEvent(Class<? extends EventViewAttribute<T>> eventViewAttributeClass, String attributeName);
    void mapEvent(EventViewAttributeFactory<T> eventViewAttributeFactory, String attributeName);

    void mapGroupedAttribute(Class<? extends GroupedViewAttribute<T>> groupedViewAttributeClass, String... attributeNames);
    void mapGroupedAttribute(
	    GroupedViewAttributeFactory<T> groupedViewAttributeFactory,
	    String... attributeName);
}
