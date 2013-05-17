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
package org.robobinding.viewattribute.adapterview;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robobinding.viewattribute.RandomValues.anyInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.attribute.StaticResourceAttribute;

import android.content.Context;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(MockitoJUnitRunner.class)
public class StaticLayoutAttributeTest {
    @Mock
    StaticResourceAttribute staticResourceAttribute;
    @Mock
    RowLayoutUpdater rowLayoutUpdater;
    @InjectMocks
    StaticLayoutAttribute staticLayoutAttribute;

    @Mock
    Context context;
    @Mock
    BindingContext bindingContext;

    @Test
    public void whenBinding_thenSetRowLayout() {
	int resourceId = anyInteger();
	when(bindingContext.getContext()).thenReturn(context);
	when(staticResourceAttribute.getResourceId(context)).thenReturn(resourceId);

	staticLayoutAttribute.bindTo(bindingContext);

	verify(rowLayoutUpdater).updateRowLayout(resourceId);
    }
}
