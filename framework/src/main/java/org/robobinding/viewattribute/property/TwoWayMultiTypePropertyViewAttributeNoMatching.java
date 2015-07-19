package org.robobinding.viewattribute.property;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class TwoWayMultiTypePropertyViewAttributeNoMatching<ViewType> implements TwoWayMultiTypePropertyViewAttribute<ViewType> {
	private TwoWayMultiTypePropertyViewAttribute<ViewType> forwarding;
	public TwoWayMultiTypePropertyViewAttributeNoMatching(TwoWayMultiTypePropertyViewAttribute<ViewType> target) {
		this.forwarding = target;
	}
	
	@Override
	public TwoWayPropertyViewAttribute<ViewType, ?, ?> create(ViewType view, Class<?> propertyType) {
		TwoWayPropertyViewAttribute<ViewType, ?, ?> viewAttribute = forwarding.create(view, propertyType);
		if(viewAttribute == null) {
			Class<?> multiTypePropertyViewAttributeClass = forwarding.getClass();
			throw new RuntimeException("Could not find a suitable attribute in " + multiTypePropertyViewAttributeClass.getName() + " for property type: " + propertyType);
		}
		
		return viewAttribute;
	}
}
