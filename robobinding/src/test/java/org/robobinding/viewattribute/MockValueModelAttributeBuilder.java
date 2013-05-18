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
package org.robobinding.viewattribute;

import static org.mockito.Mockito.when;

import org.robobinding.attribute.ValueModelAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockValueModelAttributeBuilder extends AbstractAttributeBuilder<ValueModelAttribute> {
    private MockValueModelAttributeBuilder() {
	super(ValueModelAttribute.class);
    }

    private void declarePropertyName(String propertyName) {
	when(attribute.getPropertyName()).thenReturn(propertyName);
    }

    private void declareTwoWayBinding(boolean twoWayBinding) {
	when(attribute.isTwoWayBinding()).thenReturn(twoWayBinding);
    }

    public static ValueModelAttribute aValueModelAttribute(String propertyName, boolean twoWayBinding) {
	MockValueModelAttributeBuilder builder = new MockValueModelAttributeBuilder();
	builder.declarePropertyName(propertyName);
	builder.declareTwoWayBinding(twoWayBinding);
	return builder.attribute;
    }

    public static ValueModelAttribute aValueModelAttribute() {
	MockValueModelAttributeBuilder builder = new MockValueModelAttributeBuilder();
	return builder.attribute;
    }
}
