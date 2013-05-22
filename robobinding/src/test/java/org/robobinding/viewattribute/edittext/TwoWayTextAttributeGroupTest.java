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
package org.robobinding.viewattribute.edittext;

import static org.robobinding.viewattribute.RandomValues.either;

import org.junit.Test;
import org.robobinding.attribute.MalformedAttributeException;
import org.robobinding.attribute.MissingRequiredAttributesException;
import org.robobinding.viewattribute.AbstractGroupedViewAttributeTest;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TwoWayTextAttributeGroupTest extends AbstractGroupedViewAttributeTest<TwoWayTextAttributeGroup> {
    private final Attribute oneWayBindingText = attribute("text={name}");
    private final Attribute twoWayBindingText = attribute("text=${name}");
    private final Attribute valueCommitMode = attribute("valueCommitMode=onChange");

    @Test
    public void givenATextAttribute_thenCreateInstance() {
	givenAttribute(either(oneWayBindingText, twoWayBindingText));

	performInitialization();

	assertThatAttributeWasCreated(TwoWayTextAttribute.class);
    }

    // TODO This is too difficult to test at the moment
    // @Test
    // public void
    // givenATextAttribute_thenValueCommitModeShouldDefaultToOnChange()
    // {
    // givenAttribute(either(oneWayBindingText, twoWayBindingText));
    //
    // performInitialization();
    //
    // assertThat(attributeUnderTest.valueCommitMode,
    // equalTo(ValueCommitMode.ON_CHANGE));
    // }

    @Test
    public void givenTwoWayBindingTextAndValueCommitModeAttributes_thenCreateTextAttribute() {
	givenAttributes(twoWayBindingText, valueCommitMode);

	performInitialization();

	assertThatAttributeWasCreated(TwoWayTextAttribute.class);
    }

    // @Test
    // public void
    // givenValueCommitModeAttribute_thenSetValueCommitModeAccordingly()
    // {
    // String valueCommitModeValue = either("onChange", "onFocusLost");
    // Attribute valueCommitMode = attribute("valueCommitMode=" +
    // valueCommitModeValue);
    // givenAttributes(twoWayBindingText, valueCommitMode);
    //
    // performInitialization();
    //
    // assertTrue(attributeUnderTest.valueCommitMode ==
    // ValueCommitMode.from(valueCommitModeValue));
    // }

    @Test(expected = MissingRequiredAttributesException.class)
    public void givenValueCommitModeAttributeOnly_thenReject() {
	givenAttribute(valueCommitMode);

	performInitialization();
    }

    @Test(expected = MalformedAttributeException.class)
    public void givenOneWayBindingTextAndValueCommitModeAttributes_thenReject() {
	givenAttributes(oneWayBindingText, valueCommitMode);

	performInitialization();
    }

}
