package org.robobinding.viewattribute.property;

import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.PropertyChangeListener;
import org.robobinding.property.ValueModel;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OneWayBindingProperty<ViewType, PropertyType> extends AbstractBindingProperty<ViewType, PropertyType> {
    public OneWayBindingProperty(ViewType view,
	    PropertyViewAttribute<ViewType, PropertyType> viewAttribute,
	    ValueModelAttribute attribute) {
	super(view, viewAttribute, attribute);
    }

    @Override
    public void performBind(PresentationModelAdapter presentationModelAdapter) {
	final ValueModel<PropertyType> valueModel = getPropertyValueModel(presentationModelAdapter);
	valueModel.addPropertyChangeListener(new PropertyChangeListener() {
	    @Override
	    public void propertyChanged() {
		updateView(valueModel);
	    }
	});
    }

    @Override
    public ValueModel<PropertyType> getPropertyValueModel(PresentationModelAdapter presentationModelAdapter) {
	return presentationModelAdapter.getReadOnlyPropertyValueModel(attribute.getPropertyName());
    }
}