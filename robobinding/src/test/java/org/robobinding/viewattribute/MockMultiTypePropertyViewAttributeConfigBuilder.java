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
public class MockMultiTypePropertyViewAttributeConfigBuilder<ViewType extends View> {
    private MultiTypePropertyViewAttributeConfig<ViewType> config;

    @SuppressWarnings("unchecked")
    private MockMultiTypePropertyViewAttributeConfigBuilder() {
	config = mock(MultiTypePropertyViewAttributeConfig.class);
    }

    public static <ViewType extends View> MultiTypePropertyViewAttributeConfig<ViewType> aMultiTypePropertyViewAttributeConfig(ViewType view,
	    ValueModelAttribute attribute) {
	MockMultiTypePropertyViewAttributeConfigBuilder<ViewType> builder = new MockMultiTypePropertyViewAttributeConfigBuilder<ViewType>();
	builder.withViewAndAttribute(view, attribute);
	return builder.build();
    }

    public static <ViewType extends View> MultiTypePropertyViewAttributeConfig<ViewType> aMultiTypePropertyViewAttributeConfig(ViewType view,
	    String propertyName) {
	return aMultiTypePropertyViewAttributeConfig(view, aValueModelAttribute(propertyName));
    }

    private MockMultiTypePropertyViewAttributeConfigBuilder<ViewType> withViewAndAttribute(ViewType view, ValueModelAttribute attribute) {
	when(config.getView()).thenReturn(view);
	when(config.getAttribute()).thenReturn(attribute);
	when(config.getViewListenersInjector()).thenReturn(mock(ViewListenersInjector.class));
	return this;
    }

    private MultiTypePropertyViewAttributeConfig<ViewType> build() {
	return config;
    }
}
