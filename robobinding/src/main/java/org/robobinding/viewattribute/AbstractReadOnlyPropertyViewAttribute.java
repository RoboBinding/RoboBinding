package org.robobinding.viewattribute;

import org.robobinding.property.ValueModel;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class AbstractReadOnlyPropertyViewAttribute<ViewType extends View, PropertyType> extends
	AbstractPropertyViewAttribute<ViewType, PropertyType> {
    public AbstractReadOnlyPropertyViewAttribute() {
    }

    public AbstractReadOnlyPropertyViewAttribute(boolean withAlwaysPreInitializingView) {
	super(withAlwaysPreInitializingView);
    }

    @Override
    protected final void observeChangesOnTheView(ValueModel<PropertyType> valueModel) {
	throw new UnsupportedOperationException(getClass().getName() + " only supports one-way binding");
    }
}
