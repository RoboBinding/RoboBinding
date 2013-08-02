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
package org.robobinding.viewattribute.absspinner;

import static org.robobinding.viewattribute.absspinner.AdaptedAbsSpinnerDataSetAttributes.DROPDOWN_LAYOUT;
import static org.robobinding.viewattribute.absspinner.AdaptedAbsSpinnerDataSetAttributes.DROPDOWN_MAPPING;
import static org.robobinding.viewattribute.adapterview.AbstractAdaptedDataSetAttributes.ITEM_LAYOUT;
import static org.robobinding.viewattribute.adapterview.AbstractAdaptedDataSetAttributes.ITEM_MAPPING;
import static org.robobinding.viewattribute.adapterview.AbstractAdaptedDataSetAttributes.SOURCE;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractGroupedViewAttributeTest;

import android.view.View;
import android.widget.Spinner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class AdaptedAbsSpinnerDataSetAttributesTest extends AbstractGroupedViewAttributeTest<AdaptedAbsSpinnerDataSetAttributes> {
    private final Attribute source = attribute("source={source_property}");
    private final Attribute itemLayout = attribute("itemLayout=@layout/itemLayout");
    private final Attribute itemMapping = attribute("itemMapping=[text1.title:{title}]");
    private final Attribute dropdownLayout = attribute("dropdownLayout=@layout/dropdownLayout");
    private final Attribute dropdownMapping = attribute("dropdownMapping=[text1.title:{artist}]");

    @Test
    public void givenSourceItemLayoutAndDropdownLayout_thenCreateAllAttributes() {
	givenAttributes(source, itemLayout, dropdownLayout);

	performInitialization();

	assertThatAttributesWereCreated(SOURCE, ITEM_LAYOUT, DROPDOWN_LAYOUT);
    }

    @Test
    public void givenSourceItemLayoutDropdownLayoutAndDropdownMapping_thenCreateAllAttributes() {
	givenAttributes(source, itemLayout, dropdownLayout, dropdownMapping);

	performInitialization();

	assertThatAttributesWereCreated(SOURCE, ITEM_LAYOUT, DROPDOWN_LAYOUT, DROPDOWN_MAPPING);
    }

    @Test
    public void givenAllDataSetAttributes_thenCreateAllAttributes() {
	givenAttributes(source, itemLayout, itemMapping, dropdownLayout, dropdownMapping);

	performInitialization();

	assertThatAttributesWereCreated(SOURCE, ITEM_LAYOUT, ITEM_MAPPING, DROPDOWN_LAYOUT, DROPDOWN_MAPPING);
    }

    @Test(expected = RuntimeException.class)
    public void givenDropdownLayoutAttributeOnly_thenReject() {
	givenAttributes(dropdownLayout);

	performInitialization();
    }

    @Override
    protected Class<? extends View> overrideViewClass() {
	return Spinner.class;
    }
}
