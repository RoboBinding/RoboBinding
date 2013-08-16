/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.attribute.PredefinedMappingsAttribute;

import android.content.Context;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(MockitoJUnitRunner.class)
public class ItemMappingAttributeTest {
    @Mock
    private BindingContext bindingContext;
    @Mock
    private Context context;
    @Mock
    private PredefinedMappingsAttribute predefinedMappingsAttribute;
    @Mock
    private Collection<PredefinedPendingAttributesForView> viewMappings;
    @Mock
    private PredefinedMappingUpdater predefinedMappingUpdater;

    @Test
    public void whenBinding_thenUpdateViewMappingsOnPredefinedMappingUpdater() {
	when(bindingContext.getContext()).thenReturn(context);
	when(predefinedMappingsAttribute.getViewMappings(context)).thenReturn(viewMappings);
	
	ItemMappingAttribute itemMappingAttribute = new ItemMappingAttribute(predefinedMappingUpdater);
	itemMappingAttribute.setAttribute(predefinedMappingsAttribute);

	itemMappingAttribute.bindTo(bindingContext);

	verify(predefinedMappingUpdater).updateViewMappings(viewMappings);
    }

}
