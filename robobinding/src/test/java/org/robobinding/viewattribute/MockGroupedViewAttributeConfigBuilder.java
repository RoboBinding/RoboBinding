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

import java.util.Map;

import org.robobinding.attribute.PendingGroupAttributes;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockGroupedViewAttributeConfigBuilder<ViewType extends View>
{
	private GroupedViewAttributeConfig<ViewType> config;
	
	@SuppressWarnings("unchecked")
	private MockGroupedViewAttributeConfigBuilder()
	{
		config = mock(GroupedViewAttributeConfig.class);
	}

	public static <ViewType extends View> GroupedViewAttributeConfig<ViewType> aGroupedViewAttributeConfig(ViewType view, Map<String, String> presentAttributeMappings)
	{
		MockGroupedViewAttributeConfigBuilder<ViewType> builder = new MockGroupedViewAttributeConfigBuilder<ViewType>();
		builder.withViewAndPendingGroupAttributes(view, new PendingGroupAttributes(presentAttributeMappings));
		return builder.build();
		
	}

	private MockGroupedViewAttributeConfigBuilder<ViewType> withViewAndPendingGroupAttributes(ViewType view, PendingGroupAttributes pendingGroupAttributes)
	{
		when(config.getView()).thenReturn(view);
		when(config.getPendingGroupAttributes()).thenReturn(pendingGroupAttributes);
		when(config.getViewListenersProvider()).thenReturn(mock(ViewListenersProvider.class));
		return this;
	}
	
	private GroupedViewAttributeConfig<ViewType> build()
	{
		return config;
	}
}