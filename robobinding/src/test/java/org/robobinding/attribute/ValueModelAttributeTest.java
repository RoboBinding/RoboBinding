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
package org.robobinding.attribute;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.robobinding.attribute.Attributes.aValueModelAttribute;
import static org.robobinding.attribute.BindingType.ONE_WAY;
import static org.robobinding.attribute.BindingType.TWO_WAY;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class ValueModelAttributeTest {
    @DataPoints
    public static LegalValueModelAttributeValue[] legalAttributeValues = { new LegalValueModelAttributeValue("{propertyX}", "propertyX", ONE_WAY),
	    new LegalValueModelAttributeValue("{propertyY}", "propertyY", ONE_WAY),
	    new LegalValueModelAttributeValue("${propertyZ}", "propertyZ", TWO_WAY) };

    @Theory
    public void whenCreateWithLegalAttributeValue_thenReturnAttributeWithCorrectContents(LegalValueModelAttributeValue legalAttributeValue) {
	ValueModelAttribute attribute = aValueModelAttribute(legalAttributeValue.value);

	assertThat(attribute.getPropertyName(), equalTo(legalAttributeValue.expectedPropertyName));
	assertThat(attribute.isTwoWayBinding(), equalTo(legalAttributeValue.expectedIsTwoWayBinding()));
    }

    static class LegalValueModelAttributeValue {
	final String value;
	final String expectedPropertyName;
	final BindingType expectedBindingType;

	public LegalValueModelAttributeValue(String value, String expectedPropertyName, BindingType expectedBindingType) {
	    this.value = value;
	    this.expectedPropertyName = expectedPropertyName;
	    this.expectedBindingType = expectedBindingType;
	}

	public boolean expectedIsTwoWayBinding() {
	    return expectedBindingType == TWO_WAY ? true : false;
	}
    }

}
