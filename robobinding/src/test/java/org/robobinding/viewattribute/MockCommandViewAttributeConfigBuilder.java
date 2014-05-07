package org.robobinding.viewattribute;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robobinding.attribute.Attributes.aCommandAttribute;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockCommandViewAttributeConfigBuilder<ViewType extends View> {
    private CommandViewAttributeConfig<ViewType> config;

    @SuppressWarnings("unchecked")
    private MockCommandViewAttributeConfigBuilder() {
	config = mock(CommandViewAttributeConfig.class);
    }

    public static <ViewType extends View> CommandViewAttributeConfig<ViewType> aCommandViewAttributeConfig(ViewType view, String commandName) {
	MockCommandViewAttributeConfigBuilder<ViewType> builder = new MockCommandViewAttributeConfigBuilder<ViewType>();
	builder.withViewAndCommandName(view, commandName);
	return builder.build();
    }

    private MockCommandViewAttributeConfigBuilder<ViewType> withViewAndCommandName(ViewType view, String commandName) {
	when(config.getView()).thenReturn(view);
	when(config.getAttribute()).thenReturn(aCommandAttribute(commandName));
	return this;
    }

    private CommandViewAttributeConfig<ViewType> build() {
	return config;
    }
}
