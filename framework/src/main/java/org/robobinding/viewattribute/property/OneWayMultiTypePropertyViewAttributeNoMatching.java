package org.robobinding.viewattribute.property;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class OneWayMultiTypePropertyViewAttributeNoMatching<ViewType> implements OneWayMultiTypePropertyViewAttribute<ViewType> {
	private OneWayMultiTypePropertyViewAttribute<ViewType> forwarding;
	public OneWayMultiTypePropertyViewAttributeNoMatching(OneWayMultiTypePropertyViewAttribute<ViewType> target) {
		this.forwarding = target;
	}
	
	@Override
	public OneWayPropertyViewAttribute<ViewType, ?> create(ViewType view, Class<?> propertyType) {
		OneWayPropertyViewAttribute<ViewType, ?> viewAttribute = forwarding.create(view, propertyType);
		if(viewAttribute == null) {
			Class<?> multiTypePropertyViewAttributeClass = forwarding.getClass();
			throw new RuntimeException("Could not find a suitable attribute in " + multiTypePropertyViewAttributeClass.getName() + " for property type: " + propertyType);
		}
		
		return viewAttribute;
	}

}
