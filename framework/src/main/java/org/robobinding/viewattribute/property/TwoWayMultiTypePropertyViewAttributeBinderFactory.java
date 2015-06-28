package org.robobinding.viewattribute.property;

import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinderFactory.Implementor;
import org.robobinding.widgetaddon.ViewAddOns;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class TwoWayMultiTypePropertyViewAttributeBinderFactory implements Implementor {
	private final TwoWayMultiTypePropertyViewAttributeFactory<Object> factory;
	private final ViewAddOns viewAddOns;

	@SuppressWarnings("unchecked")
	public TwoWayMultiTypePropertyViewAttributeBinderFactory(
			TwoWayMultiTypePropertyViewAttributeFactory<?> multiTypeViewAttribute,
			ViewAddOns viewAddOns) {
		this.factory = (TwoWayMultiTypePropertyViewAttributeFactory<Object>)multiTypeViewAttribute;
		this.viewAddOns = viewAddOns;
	}

	@Override
	public MultiTypePropertyViewAttributeBinder create(Object view, ValueModelAttribute attribute) {
		PropertyViewAttributeBinderProvider binderProvider = new TwoWayPropertyViewAttributeBinderProvider(
				view, factory.create(), attribute, viewAddOns);
		return new MultiTypePropertyViewAttributeBinder(binderProvider, attribute);
	}
	
	private static class TwoWayPropertyViewAttributeBinderProvider extends AbstractTwoWayPropertyViewAttributeBinderFactory
		implements PropertyViewAttributeBinderProvider {
		private final Object view;
		private final TwoWayMultiTypePropertyViewAttribute<Object> multiTypeViewAttribute;
		private final ValueModelAttribute attribute;

		public TwoWayPropertyViewAttributeBinderProvider(Object view, 
				TwoWayMultiTypePropertyViewAttribute<Object> multiTypeViewAttribute,
				ValueModelAttribute attribute, 
				ViewAddOns viewAddOns) {
			super(viewAddOns);
			this.view = view;
			this.multiTypeViewAttribute = multiTypeViewAttribute;
			this.attribute = attribute;
		}

		@Override
		public PropertyViewAttributeBinder create(Class<?> propertyType) {
			TwoWayPropertyViewAttribute<Object, ?, ?> viewAttribute =  multiTypeViewAttribute.create(view, propertyType);
			if(viewAttribute == null) {
				return null;
			}
			
			return create(view, viewAttribute, attribute);
		}
	}
}