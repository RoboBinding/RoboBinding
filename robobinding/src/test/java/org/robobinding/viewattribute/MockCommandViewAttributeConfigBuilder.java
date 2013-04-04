/**
 * Copyright 2013 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
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
public class MockCommandViewAttributeConfigBuilder<ViewType extends View>
{
	private CommandViewAttributeConfig<ViewType> config;
	
	@SuppressWarnings("unchecked")
	private MockCommandViewAttributeConfigBuilder()
	{
		config = mock(CommandViewAttributeConfig.class);
	}

	public static <ViewType extends View> CommandViewAttributeConfig<ViewType> aCommandViewAttributeConfig(ViewType view, String commandName)
	{
		MockCommandViewAttributeConfigBuilder<ViewType> builder = new MockCommandViewAttributeConfigBuilder<ViewType>();
		builder.withViewAndCommandName(view, commandName);
		return builder.build();
	}
	
	private MockCommandViewAttributeConfigBuilder<ViewType> withViewAndCommandName(ViewType view, String commandName)
	{
		when(config.getView()).thenReturn(view);
		when(config.getAttribute()).thenReturn(aCommandAttribute(commandName));
		return this;
	}
	
	private CommandViewAttributeConfig<ViewType> build()
	{
		return config;
	}
}
