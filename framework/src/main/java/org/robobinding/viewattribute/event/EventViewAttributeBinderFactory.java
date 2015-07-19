package org.robobinding.viewattribute.event;

import org.robobinding.attribute.EventAttribute;
import org.robobinding.widgetaddon.ViewAddOn;
import org.robobinding.widgetaddon.ViewAddOns;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class EventViewAttributeBinderFactory {
	private final ViewAddOns viewAddOns;
	private final EventViewAttributeFactory<?> factory;
	
	public EventViewAttributeBinderFactory(ViewAddOns viewAddOns, EventViewAttributeFactory<?> factory) {
		this.viewAddOns = viewAddOns;
		this.factory = factory;
	}
	
	public EventViewAttributeBinder create(Object view, String attributeName, String attributeValue) {
		return create(view, new EventAttribute(attributeName, attributeValue));
	}
	
	public EventViewAttributeBinder create(Object view, EventAttribute attribute) {
		@SuppressWarnings("unchecked")
		EventViewAttribute<Object, ViewAddOn> viewAttribute = (EventViewAttribute<Object, ViewAddOn>)factory.create();
		return new EventViewAttributeBinder(view, viewAddOns.getMostSuitable(view), viewAttribute, attribute);
	}
}
