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
public abstract class AbstractBindingProperty {
	protected final Object view;
	protected final ValueModelAttribute attribute;
	private final boolean withAlwaysPreInitializingView;

	public AbstractBindingProperty(Object view, ValueModelAttribute attribute, boolean withAlwaysPreInitializingView) {
		this.view = view;
		this.attribute = attribute;
		this.withAlwaysPreInitializingView = withAlwaysPreInitializingView;
	}
	
	protected static boolean isAlwaysPreInitializingView(Object viewAttribute) {
		return viewAttribute instanceof AlwaysPreInitializingView;
	}

	public abstract void performBind(PresentationModelAdapter presentationModelAdapter);

	public void preInitializeView(PresentationModelAdapter presentationModelAdapter) {
		ValueModel<Object> valueModel = getPropertyValueModel(presentationModelAdapter);
		updateView(valueModel);
	}

	public boolean isAlwaysPreInitializingView() {
		return withAlwaysPreInitializingView;
	}

	protected abstract void updateView(ValueModel<Object> valueModel);

	protected abstract ValueModel<Object> getPropertyValueModel(PresentationModelAdapter presentationModelAdapter);
}