package org.robobinding.viewattribute.grouped;

import org.robobinding.attribute.EnumAttribute;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttributeFactory;
import org.robobinding.widgetaddon.ViewAddOn;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface ChildViewAttributesBuilder<ViewType> {
	void add(String attributeName, ChildViewAttribute childAttribute);

	void add(String attributeName, ChildViewAttributeFactory factory);

	void addDependent(String attributeName, ChildViewAttributeFactory factory);

	void add(String attributeName, OneWayPropertyViewAttribute<ViewType, ?> viewAttribute);

	void add(String attributeName, OneWayPropertyViewAttributeFactory<ViewType> factory);
	
	void add(String attributeName, TwoWayPropertyViewAttribute<ViewType, ?, ?> viewAttribute);

	void add(String attributeName, TwoWayPropertyViewAttributeFactory<ViewType> factory);

	void add(String attributeName, OneWayMultiTypePropertyViewAttribute<ViewType> viewAttribute);

	void add(String attributeName, OneWayMultiTypePropertyViewAttributeFactory<ViewType> factory);

	void add(String attributeName, TwoWayMultiTypePropertyViewAttribute<ViewType> viewAttribute);

	void add(String attributeName, TwoWayMultiTypePropertyViewAttributeFactory<ViewType> factory);

	void add(String attributeName, EventViewAttribute<ViewType, ? extends ViewAddOn> viewAttribute);

	void add(String attributeName, EventViewAttributeFactory<ViewType> factory);

	void failOnFirstBindingError();

	boolean hasAttribute(String attributeName);

	ValueModelAttribute valueModelAttributeFor(String attributeName);

	StaticResourceAttribute staticResourceAttributeFor(String attributeName);

	<E extends Enum<E>> EnumAttribute<E> enumAttributeFor(String attributeName);
}
