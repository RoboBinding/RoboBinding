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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robobinding.attribute.Attributes.aValueModelAttribute;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.ViewBinder;
import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class SubViewPresentationModelAttributeTest {
    @Mock
    private SubViewHolder subViewHolder;
    @Mock
    private View boundSubView;

    private int layoutId = 2;
    private String presentationModelPropertyName = "propertyName";
    private ValueModelAttribute valueModelAttribute = aValueModelAttribute(presentationModelPropertyName);

    @Test
    public void whenBindTo_thenBoundSubViewIsSetOnHolder() {
	BindingContext bindingContext = mock(BindingContext.class);
	ViewBinder viewBinder = mock(ViewBinder.class);
	when(bindingContext.createViewBinder()).thenReturn(viewBinder);
	when(viewBinder.inflateAndBind(layoutId, presentationModelPropertyName)).thenReturn(boundSubView);
	SubViewPresentationModelAttribute subViewAttribute = new SubViewPresentationModelAttribute(layoutId, subViewHolder);
	subViewAttribute.setAttribute(valueModelAttribute);

	subViewAttribute.bindTo(bindingContext);

	verify(subViewHolder).setSubView(boundSubView);
    }
}
