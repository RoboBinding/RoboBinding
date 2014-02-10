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

import java.util.Random;

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
public class ChildViewAttributesBuilderTest {
    @Mock
    ResolvedGroupAttributes resolvedGroupAttributes;
    @Mock
    ChildViewAttributeInitializer viewAttributeInitializer;
    @InjectMocks
    private ChildViewAttributesBuilder<View> childViewAttributesBuilder;
    @Mock
    private ChildViewAttribute childViewAttribute;
    @Mock
    private PropertyViewAttribute<View> propertyViewAttribute;
    @Mock
    private ChildViewAttributeFactory childViewAttributeFactory;
    @Mock
    private ViewAttributeFactory<? extends PropertyViewAttribute<? extends View>> viewAttributeFactory;

    @Test
    public void whenAddAChildViewAttribute_thenTheAttributeIsAdded() {
	String attributeName = "attributeName";

	addAChildViewAttribute(attributeName);

	assertThat(numOfChildViewAttributes(), is(1));
	assertTrue(hasChildViewAttribute(attributeName));
    }

    private void addAChildViewAttribute(String attributeName) {
	int selection = new Random().nextInt(4);
	switch (selection) {
	case 0:
	    childViewAttributesBuilder.add(attributeName, childViewAttribute);
	    break;
	case 1:
	    childViewAttributesBuilder.add(attributeName, propertyViewAttribute);
	    break;
	case 2:
	    childViewAttributesBuilder.add(attributeName, childViewAttributeFactory);
	    break;
	default:
	    childViewAttributesBuilder.add(attributeName, viewAttributeFactory);
	    break;
	}
    }

    private int numOfChildViewAttributes() {
	return childViewAttributesBuilder.childViewAttributeMap.size();
    }

    private boolean hasChildViewAttribute(String attributeName) {
	return childViewAttributesBuilder.childViewAttributeMap.containsKey(attributeName);
    }

    @Test
    public void whenAddDifferentChildViewAttributes_thenTheTwoAttributesAreAdded() {
	String attribute1 = "attribute1";
	String attribute2 = "attribute2";
	String attribute3 = "attribute3";
	String attribute4 = "attribute4";

	childViewAttributesBuilder.add(attribute1, childViewAttribute);
	childViewAttributesBuilder.add(attribute2, propertyViewAttribute);
	childViewAttributesBuilder.add(attribute3, childViewAttributeFactory);
	childViewAttributesBuilder.add(attribute4, viewAttributeFactory);

	assertThat(numOfChildViewAttributes(), is(4));
	assertTrue(hasChildViewAttribute(attribute1));
	assertTrue(hasChildViewAttribute(attribute2));
	assertTrue(hasChildViewAttribute(attribute3));
	assertTrue(hasChildViewAttribute(attribute4));
    }
}