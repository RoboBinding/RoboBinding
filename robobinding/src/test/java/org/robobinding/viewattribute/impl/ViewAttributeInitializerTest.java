/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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
package org.robobinding.viewattribute.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.attribute.PendingGroupAttributes;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.GroupedViewAttributeConfig;
import org.robobinding.viewattribute.ViewListenersInjector;

import android.view.View;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAttributeInitializerTest {
    private ViewAttributeInitializer viewAttributeInitializer;
    private ViewListenersInjector viewListenersProvider;

    @Before
    public void setUp() {
	viewListenersProvider = mock(ViewListenersInjector.class);
	viewAttributeInitializer = new ViewAttributeInitializer(viewListenersProvider);
    }

    @Test
    public void whenNewGroupedViewAttribute_thenNewInstanceShouldBeCorrectlyInitialized() {
	View view = mock(View.class);
	PendingGroupAttributes pendingGroupAttributes = newPendingGroupAttributes();
	@SuppressWarnings({ "unchecked" })
	AbstractGroupedViewAttribute<View> viewAttribute = mock(AbstractGroupedViewAttribute.class);

	viewAttribute = viewAttributeInitializer.initializeGroupedViewAttribute(view, viewAttribute, pendingGroupAttributes);

	verify(viewAttribute).initialize(new GroupedViewAttributeConfig<View>(view, pendingGroupAttributes, viewListenersProvider));
    }

    private PendingGroupAttributes newPendingGroupAttributes() {
	return new PendingGroupAttributes(Maps.<String, String> newHashMap());
    }
}
