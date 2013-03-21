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
import static org.robobinding.attribute.Attributes.aValueModelAttribute;

import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockMultiTypePropertyViewAttributeConfigBuilder<ViewType extends View>
{
	private MultiTypePropertyViewAttributeConfig<ViewType> config;
	
	@SuppressWarnings("unchecked")
	private MockMultiTypePropertyViewAttributeConfigBuilder()
	{
		config = mock(MultiTypePropertyViewAttributeConfig.class);
	}

	public static <ViewType extends View> MultiTypePropertyViewAttributeConfig<ViewType> aMultiTypePropertyViewAttributeConfig(ViewType view, ValueModelAttribute attribute)
	{
		MockMultiTypePropertyViewAttributeConfigBuilder<ViewType> builder = new MockMultiTypePropertyViewAttributeConfigBuilder<ViewType>();
		builder.withViewAndAttribute(view, attribute);
		return builder.build();
	}

	public static <ViewType extends View> MultiTypePropertyViewAttributeConfig<ViewType> aMultiTypePropertyViewAttributeConfig(ViewType view, String propertyName)
	{
		return aMultiTypePropertyViewAttributeConfig(view, aValueModelAttribute(propertyName));		
	}
	
	private MockMultiTypePropertyViewAttributeConfigBuilder<ViewType> withViewAndAttribute(ViewType view, ValueModelAttribute attribute)
	{
		when(config.getView()).thenReturn(view);
		when(config.getAttribute()).thenReturn(attribute);
		when(config.getViewListenersProvider()).thenReturn(mock(ViewListenersProvider.class));
		return this;
	}
	
	private MultiTypePropertyViewAttributeConfig<ViewType> build()
	{
		return config;
	}
}
