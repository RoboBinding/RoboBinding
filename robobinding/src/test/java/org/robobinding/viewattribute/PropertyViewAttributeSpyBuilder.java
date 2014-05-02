package org.robobinding.viewattribute;

import static org.mockito.Mockito.mock;
import static org.robobinding.attribute.MockValueModelAttributeBuilder.aValueModelAttribute;
import static org.robobinding.viewattribute.MockPropertyViewAttributeConfigBuilder.aPropertyViewAttributeConfig;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyViewAttributeSpyBuilder {
    private boolean preInitializeView;
    private boolean twoWayBinding;
    private String propertyName;

    public static PropertyViewAttributeSpyBuilder aPropertyViewAttributeSpy() {
	return new PropertyViewAttributeSpyBuilder();
    }

    private PropertyViewAttributeSpyBuilder() {
    }

    public PropertyViewAttributeSpyBuilder withPreInitializeView(boolean preInitializeView) {
	this.preInitializeView = preInitializeView;
	return this;
    }

    public PropertyViewAttributeSpyBuilder withTwoWaybinding(boolean twoWayBinding) {
	this.twoWayBinding = twoWayBinding;
	return this;
    }

    public PropertyViewAttributeSpyBuilder withPropertyName(String propertyName) {
	this.propertyName = propertyName;
	return this;
    }

    public PropertyViewAttributeSpy build() {
	PropertyViewAttributeSpy viewAttribute = new PropertyViewAttributeSpy(preInitializeView);
	viewAttribute.initialize(aPropertyViewAttributeConfig(mock(View.class), aValueModelAttribute(propertyName, twoWayBinding)));
	return viewAttribute;
    }
}
