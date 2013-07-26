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

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.attribute.AbstractAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ChildViewAttributeInitializerTest {
    @Mock
    private AbstractAttribute attribute;
    
    @Test
    public void whenInitializeChildViewAttribute_thenReturnAsWrappedViewAttribute() {
	ChildViewAttribute childViewAttribute = mock(ChildViewAttribute.class);

	ChildViewAttributeInitializer viewAttributeInitializer = new ChildViewAttributeInitializer(null);
	ViewAttribute viewAttribute = viewAttributeInitializer.initializeChildViewAttribute(childViewAttribute, attribute);
	
	assertNotNull(viewAttribute);
    }
    
    @Test
    public void whenInitializeChildViewAttributeWithAttribute_thenTheAttributeIsSetOnTheChildViewAttribute() {
	@SuppressWarnings("unchecked")
	ChildViewAttributeWithAttribute<AbstractAttribute> childViewAttribute = mock(ChildViewAttributeWithAttribute.class);

	ChildViewAttributeInitializer viewAttributeInitializer = new ChildViewAttributeInitializer(null);
	viewAttributeInitializer.initializeChildViewAttribute(childViewAttribute, attribute);
	
	verify(childViewAttribute).setAttribute(attribute);
    }
}
