package org.robobinding.viewattribute.property;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.widgetaddon.ViewAddOns;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class TwoWayMultiTypePropertyViewAttributeBinderFactory extends AbstractMultiTypePropertyViewAttributeBinderFactory {
	private final TwoWayMultiTypePropertyViewAttribute<Object> multiTypeViewAttribute;
	private final ViewAddOns viewAddOns;

	@SuppressWarnings("unchecked")
	public TwoWayMultiTypePropertyViewAttributeBinderFactory(
			TwoWayMultiTypePropertyViewAttributeFactory<?> multiTypeViewAttribute,
			ViewAddOns viewAddOns,
			PropertyAttributeParser propertyAttributeParser) {
		super(propertyAttributeParser);
		
		this.multiTypeViewAttribute = (TwoWayMultiTypePropertyViewAttribute<Object>)multiTypeViewAttribute;
		this.viewAddOns = viewAddOns;
	}

	@Override
	public MultiTypePropertyViewAttributeBinder create(Object view, ValueModelAttribute attribute) {
		PropertyViewAttributeBinderProvider binderProvider = new TwoWayPropertyViewAttributeBinderProvider(
				view, multiTypeViewAttribute, attribute, viewAddOns);
		return new MultiTypePropertyViewAttributeBinder(binderProvider, attribute);
	}
	
	private static class TwoWayPropertyViewAttributeBinderProvider implements PropertyViewAttributeBinderProvider {
		private final Object view;
		private final TwoWayMultiTypePropertyViewAttribute<Object> multiTypeViewAttribute;
		private final ValueModelAttribute attribute;
		private final ViewAddOns viewAddOns;

		public TwoWayPropertyViewAttributeBinderProvider(Object view, 
				TwoWayMultiTypePropertyViewAttribute<Object> multiTypeViewAttribute,
				ValueModelAttribute attribute, 
				ViewAddOns viewAddOns) {
			this.view = view;
			this.multiTypeViewAttribute = multiTypeViewAttribute;
			this.attribute = attribute;
			this.viewAddOns = viewAddOns;
		}

		@Override
		public PropertyViewAttributeBinder create(Class<?> propertyType) {
			TwoWayPropertyViewAttribute<Object, ?, ?> viewAttribute =  multiTypeViewAttribute.create(view, propertyType);
			if(viewAttribute == null) {
				return null;
			}
			
			AbstractBindingProperty bindingProperty = new TwoWayBindingProperty(
					view, viewAddOns.getMostSuitable(view), 
					viewAttribute, attribute);
			return new PropertyViewAttributeBinder(bindingProperty, attribute.getName());
		}
	}
}