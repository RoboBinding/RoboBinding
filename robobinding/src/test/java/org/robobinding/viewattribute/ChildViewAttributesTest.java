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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.attribute.ResolvedGroupAttributes;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ChildViewAttributesTest {
    @Mock
    ResolvedGroupAttributes resolvedGroupAttributes;
    @Mock
    ChildViewAttributeInitializer viewAttributeInitializer;
    @InjectMocks
    private ChildViewAttributes<View> childViewAttributes;
    @Mock
    private ChildViewAttribute childViewAttribute;
    @Mock
    private PropertyViewAttribute<View> propertyViewAttribute;

    @Test
    public void whenAddChildViewAttribute_thenTheAttributeIsAdded() {
	String attributeName = "attributeName";

	childViewAttributes.add(attributeName, childViewAttribute);

	assertThat(numOfChildViewAttributes(), is(1));
	assertTrue(hasChildViewAttribute(attributeName));
    }

    private int numOfChildViewAttributes() {
	return childViewAttributes.childAttributeMap.size();
    }

    private boolean hasChildViewAttribute(String attributeName) {
	return childViewAttributes.childAttributeMap.containsKey(attributeName);
    }

    @Test
    public void whenAddPropertyViewAttribute_thenTheAttributeIsAdded() {
	String attributeName = "attributeName";
	
	childViewAttributes.add(attributeName, propertyViewAttribute);

	assertThat(numOfChildViewAttributes(), is(1));
	assertTrue(hasChildViewAttribute(attributeName));
    }

    @Test
    public void whenAddBothChildViewAttributeAndPropertyViewAttribute_thenTheTwoAttributesAreAdded() {
	String childViewAttributeName = "childAttribute";
	String propertyViewAttributeName = "propertyAttribute";
	
	childViewAttributes.add(childViewAttributeName, childViewAttribute);
	childViewAttributes.add(propertyViewAttributeName, propertyViewAttribute);

	assertThat(numOfChildViewAttributes(), is(2));
	assertTrue(hasChildViewAttribute(childViewAttributeName));
	assertTrue(hasChildViewAttribute(propertyViewAttributeName));
    }
}