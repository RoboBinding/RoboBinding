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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.attribute.StaticResourceAttribute;

import android.content.Context;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class SubViewLayoutAttributeTest {
    @Mock
    private BindingContext bindingContext;
    @Mock
    private StaticResourceAttribute attribute;
    private int layoutId = 1;

    @Test
    public void givenAttribute_whenBindTo_thenLayoutIdIsCorrectlySet() {
	when(attribute.getResourceId(any(Context.class))).thenReturn(layoutId);
	SubViewLayoutAttribute subViewLayoutAttribute = new SubViewLayoutAttribute();
	subViewLayoutAttribute.setAttribute(attribute);

	subViewLayoutAttribute.bindTo(bindingContext);

	assertThat(subViewLayoutAttribute.getLayoutId(), is(layoutId));
    }
}
