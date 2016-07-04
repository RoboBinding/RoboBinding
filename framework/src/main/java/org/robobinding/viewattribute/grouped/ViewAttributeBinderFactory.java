package org.robobinding.viewattribute.grouped;

import org.robobinding.attribute.EventAttribute;
import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeBinder;
import org.robobinding.viewattribute.event.EventViewAttributeBinderFactory;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttributeFactory;
import org.robobinding.widgetaddon.ViewAddOn;
import org.robobinding.widgetaddon.ViewAddOnInjector;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAttributeBinderFactory {
	private final Object view;
	private final PropertyAttributeParser propertyAttributeParser;
	private final GroupAttributesResolver groupAttributesResolver;
	private final ViewAddOnInjector viewAddOnInjector;

	public ViewAttributeBinderFactory(Object view, PropertyAttributeParser propertyAttributeParser, 
			GroupAttributesResolver groupAttributesResolver, ViewAddOnInjector viewAddOnInjector) {
		this.view = view;
		this.propertyAttributeParser = propertyAttributeParser;
		this.groupAttributesResolver = groupAttributesResolver;
		this.viewAddOnInjector = viewAddOnInjector;
	}
	
	public PropertyViewAttributeBinderFactory binderFactoryFor(OneWayPropertyViewAttributeFactory<?> factory) {
		return new PropertyViewAttributeBinderFactory(new OneWayPropertyViewAttributeBinderFactory(factory, viewAddOnInjector),
				propertyAttributeParser);
	}
	
	public PropertyViewAttributeBinderFactory binderFactoryFor(TwoWayPropertyViewAttributeFactory<?> factory) {
		return new PropertyViewAttributeBinderFactory(new TwoWayPropertyViewAttributeBinderFactory(factory, viewAddOnInjector),
				propertyAttributeParser);
	}
	
	public MultiTypePropertyViewAttributeBinderFactory binderFactoryFor(OneWayMultiTypePropertyViewAttributeFactory<?> factory) {
		return new MultiTypePropertyViewAttributeBinderFactory(
				new OneWayMultiTypePropertyViewAttributeBinderFactory(factory, viewAddOnInjector),
				propertyAttributeParser);
	}
	
	public MultiTypePropertyViewAttributeBinderFactory binderFactoryFor(TwoWayMultiTypePropertyViewAttributeFactory<?> factory) {
		return new MultiTypePropertyViewAttributeBinderFactory(
				new TwoWayMultiTypePropertyViewAttributeBinderFactory(factory, viewAddOnInjector), 
				propertyAttributeParser);
	}
	
	public EventViewAttributeBinderFactory binderFactoryFor(EventViewAttributeFactory<?> factory) {
		return new EventViewAttributeBinderFactory(viewAddOnInjector, factory);
	}
	
	public GroupedViewAttributeBinderFactory binderFactoryFor(GroupedViewAttributeFactory<?> factory) {
		return new GroupedViewAttributeBinderFactory(factory, groupAttributesResolver, this);
	}

	public PropertyViewAttributeBinder binderFor(final OneWayPropertyViewAttribute<?, ?> viewAttribute,
			ValueModelAttribute attribute) {
		return binderFor(new OneWayPropertyViewAttributeFactory<Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public OneWayPropertyViewAttribute<Object, ?> create() {
				return (OneWayPropertyViewAttribute<Object, ?>)viewAttribute;
			}
		}, attribute);
	}

	public PropertyViewAttributeBinder binderFor(OneWayPropertyViewAttributeFactory<?> factory,
			ValueModelAttribute attribute) {
		PropertyViewAttributeBinderFactory binderFactory = binderFactoryFor(factory);
		return binderFactory.create(view, attribute);
	}

	public PropertyViewAttributeBinder binderFor(final TwoWayPropertyViewAttribute<?, ?, ?> viewAttribute,
			ValueModelAttribute attribute) {
		return binderFor(new TwoWayPropertyViewAttributeFactory<Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public TwoWayPropertyViewAttribute<Object, ?, ?> create() {
				return (TwoWayPropertyViewAttribute<Object, ?, ?>)viewAttribute;
			}
		}, attribute);
	}

	public PropertyViewAttributeBinder binderFor(TwoWayPropertyViewAttributeFactory<?> factory,
			ValueModelAttribute attribute) {
		PropertyViewAttributeBinderFactory binderFactory = binderFactoryFor(factory);
		return binderFactory.create(view, attribute);
	}

	public MultiTypePropertyViewAttributeBinder binderFor(final OneWayMultiTypePropertyViewAttribute<?> viewAttribute,
			ValueModelAttribute attribute) {
		return binderFor(new OneWayMultiTypePropertyViewAttributeFactory<Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public OneWayMultiTypePropertyViewAttribute<Object> create() {
				return (OneWayMultiTypePropertyViewAttribute<Object>)viewAttribute;
			}
		}, attribute);
	}

	public MultiTypePropertyViewAttributeBinder binderFor(
			OneWayMultiTypePropertyViewAttributeFactory<?> factory, ValueModelAttribute attribute) {
		MultiTypePropertyViewAttributeBinderFactory binderFactory = binderFactoryFor(factory);
		return binderFactory.create(view, attribute);
	}

	public MultiTypePropertyViewAttributeBinder binderFor(final TwoWayMultiTypePropertyViewAttribute<?> viewAttribute,
			ValueModelAttribute attribute) {
		return binderFor(new TwoWayMultiTypePropertyViewAttributeFactory<Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public TwoWayMultiTypePropertyViewAttribute<Object> create() {
				return (TwoWayMultiTypePropertyViewAttribute<Object>)viewAttribute;
			}
		}, attribute);
	}

	public MultiTypePropertyViewAttributeBinder binderFor(
			TwoWayMultiTypePropertyViewAttributeFactory<?> factory, ValueModelAttribute attribute) {
		MultiTypePropertyViewAttributeBinderFactory binderFactory = binderFactoryFor(factory);
		return binderFactory.create(view, attribute);
	}

	public EventViewAttributeBinder binderFor(final EventViewAttribute<?, ?> viewAttribute, EventAttribute attribute) {
		return binderFor(new EventViewAttributeFactory<Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public EventViewAttribute<Object, ViewAddOn> create() {
				return (EventViewAttribute<Object, ViewAddOn>)viewAttribute;
			}
		}, attribute);
	}

	public EventViewAttributeBinder binderFor(EventViewAttributeFactory<?> factory, EventAttribute attribute) {
		EventViewAttributeBinderFactory binderFactory = binderFactoryFor(factory);
		return binderFactory.create(view, attribute);
	}
}
