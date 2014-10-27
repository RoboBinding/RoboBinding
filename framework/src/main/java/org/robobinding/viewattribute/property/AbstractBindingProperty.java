package org.robobinding.viewattribute.property;

import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.ValueModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractBindingProperty<ViewType, PropertyType> {
	private final PropertyViewAttribute<ViewType, PropertyType> viewAttribute;
	protected final ViewType view;
	protected final ValueModelAttribute attribute;

	public AbstractBindingProperty(ViewType view, PropertyViewAttribute<ViewType, PropertyType> viewAttribute, ValueModelAttribute attribute) {
		this.viewAttribute = viewAttribute;
		this.view = view;
		this.attribute = attribute;
	}

	public void preInitializeView(PresentationModelAdapter presentationModelAdapter) {
		ValueModel<PropertyType> valueModel = getPropertyValueModel(presentationModelAdapter);
		updateView(valueModel);
	}

	public String getAttributeName() {
		return attribute.getName();
	}

	protected void updateView(ValueModel<PropertyType> valueModel) {
		viewAttribute.updateView(view, valueModel.getValue());
	}

	public abstract ValueModel<PropertyType> getPropertyValueModel(PresentationModelAdapter presentationModelAdapter);

	public abstract void performBind(PresentationModelAdapter presentationModelAdapter);
}