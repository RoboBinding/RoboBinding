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

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robobinding.attribute.Attributes.aValueModelAttribute;

import org.junit.Test;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.ViewAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class RowLayoutAttributeFactoryTest {
    @Test
    public void whenCreateWithStaticResourceAttribute_thenReturnStaticLayoutAttribute() {
	StaticResourceAttribute staticResourceAttribute = mock(StaticResourceAttribute.class);
	when(staticResourceAttribute.isStaticResource()).thenReturn(true);

	RowLayoutAttributeFactory rowLayoutAttributeFactory = new ItemLayoutAttributeFactory(null, null);
	ViewAttribute viewAttribute = rowLayoutAttributeFactory.createRowLayoutAttribute(staticResourceAttribute);

	assertThat(viewAttribute, instanceOf(StaticLayoutAttribute.class));
    }

    @Test
    public void whenCreateWithValueModelAttribute_thenReturnDynamicLayoutAttribute() {
	ValueModelAttribute valueModelAttribute = aValueModelAttribute("{itemLayout}");

	RowLayoutAttributeFactory rowLayoutAttributeFactory = new ItemLayoutAttributeFactory(null, null);
	ViewAttribute viewAttribute = rowLayoutAttributeFactory.createRowLayoutAttribute(valueModelAttribute);

	assertThat(viewAttribute, instanceOf(DynamicLayoutAttribute.class));
    }
}
