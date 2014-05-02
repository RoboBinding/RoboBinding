package org.robobinding.viewattribute;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robobinding.attribute.Attributes.aValueModelAttribute;

import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockPropertyViewAttributeConfigBuilder<ViewType extends View> {
    private PropertyViewAttributeConfig<ViewType> config;

    @SuppressWarnings("unchecked")
    private MockPropertyViewAttributeConfigBuilder() {
	config = mock(PropertyViewAttributeConfig.class);
    }

    public static <ViewType extends View> PropertyViewAttributeConfig<ViewType> aPropertyViewAttributeConfig(ViewType view,
	    ValueModelAttribute attribute) {
	MockPropertyViewAttributeConfigBuilder<ViewType> builder = new MockPropertyViewAttributeConfigBuilder<ViewType>();
	builder.withViewAndAttribute(view, attribute);
	return builder.build();

    }

    public static <ViewType extends View> PropertyViewAttributeConfig<ViewType> aPropertyViewAttributeConfig(ViewType view, String propertyName) {
	return aPropertyViewAttributeConfig(view, aValueModelAttribute(propertyName));
    }

    public static <ViewType extends View> PropertyViewAttributeConfig<ViewType> aPropertyViewAttributeConfig(ViewType view) {
	return aPropertyViewAttributeConfig(view, aValueModelAttribute("someproperty"));
    }

    private MockPropertyViewAttributeConfigBuilder<ViewType> withViewAndAttribute(ViewType view, ValueModelAttribute attribute) {
	when(config.getView()).thenReturn(view);
	when(config.getAttribute()).thenReturn(attribute);
	return this;
    }

    private PropertyViewAttributeConfig<ViewType> build() {
	return config;
    }
}
