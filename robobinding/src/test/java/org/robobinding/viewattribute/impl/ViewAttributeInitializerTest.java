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

import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.ChildViewAttributesBuilder;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewAttributeInitializerTest {
    @Mock
    private View view;

    @SuppressWarnings("unchecked")
    @Test
    public void whenInitializeGroupedViewAttribute_thenTheAttributeIsCorrectlyInitialized() {
	AbstractGroupedViewAttribute<View> viewAttribute = mock(AbstractGroupedViewAttribute.class);

	ViewAttributeInitializer viewAttributeInitializer = new ViewAttributeInitializer(null);
	
	viewAttribute = viewAttributeInitializer.initializeGroupedViewAttribute(view, viewAttribute, null);

	verify(viewAttribute).initialize(same(view), isA(ChildViewAttributesBuilder.class));
    }
}
