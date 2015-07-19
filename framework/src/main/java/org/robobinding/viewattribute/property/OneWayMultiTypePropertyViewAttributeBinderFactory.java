package org.robobinding.viewattribute.property;

import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinderFactory.Implementor;
import org.robobinding.widgetaddon.ViewAddOnInjector;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OneWayMultiTypePropertyViewAttributeBinderFactory implements Implementor {
	private final OneWayMultiTypePropertyViewAttributeFactory<Object> factory;
	private final ViewAddOnInjector viewAddOnInjector;

	@SuppressWarnings("unchecked")
	public OneWayMultiTypePropertyViewAttributeBinderFactory(
			OneWayMultiTypePropertyViewAttributeFactory<?> factory,
			ViewAddOnInjector viewAddOnInjector) {
		this.factory = (OneWayMultiTypePropertyViewAttributeFactory<Object>)factory;
		this.viewAddOnInjector = viewAddOnInjector;
	}

	@Override
	public MultiTypePropertyViewAttributeBinder create(Object view, ValueModelAttribute attribute) {
		OneWayMultiTypePropertyViewAttribute<Object> multiTypeViewAttribute = new OneWayMultiTypePropertyViewAttributeNoMatching<Object>(
				factory.create());
		PropertyViewAttributeBinderProvider binderProvider = new OneWayPropertyViewAttributeBinderProvider(
				view, multiTypeViewAttribute, attribute, viewAddOnInjector);
		return new MultiTypePropertyViewAttributeBinder(binderProvider, attribute);
	}
	
	private static class OneWayPropertyViewAttributeBinderProvider implements PropertyViewAttributeBinderProvider {
		private final Object view;
		private final OneWayMultiTypePropertyViewAttribute<Object> multiTypeViewAttribute;
		private final ValueModelAttribute attribute;
		private final ViewAddOnInjector viewAddOnInjector;
		
		public OneWayPropertyViewAttributeBinderProvider(
				Object view, OneWayMultiTypePropertyViewAttribute<Object> multiTypeViewAttribute,
				ValueModelAttribute attribute, ViewAddOnInjector viewAddOnInjector) {
			this.view = view;
			this.multiTypeViewAttribute = multiTypeViewAttribute;
			this.attribute = attribute;
			this.viewAddOnInjector = viewAddOnInjector;
		}

		@Override
		public PropertyViewAttributeBinder create(Class<?> propertyType) {
			OneWayPropertyViewAttribute<Object, ?> viewAttribute = multiTypeViewAttribute.create(view, propertyType);
			if(viewAttribute == null) {
				return null;
			}
			
			viewAddOnInjector.injectIfRequired(viewAttribute, view);
			AbstractBindingProperty bindingProperty = new OneWayBindingProperty(view, viewAttribute, attribute);
			return new PropertyViewAttributeBinder(bindingProperty, attribute.getName());
		}
	}
}