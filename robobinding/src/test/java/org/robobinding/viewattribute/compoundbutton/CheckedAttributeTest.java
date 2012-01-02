/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package org.robobinding.viewattribute.compoundbutton;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.property.PropertyValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.viewattribute.AbstractAttributeTest;

import android.widget.CheckBox;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class CheckedAttributeTest extends AbstractAttributeTest<CheckBox, CheckedAttribute>
{
	@Test
	public void whenValueModelUpdated_ThenViewShouldReflectChanges()
	{
		boolean checked = trueOrFalse();
		
		attribute.valueModelUpdated(checked);
		
		assertThat(view.isChecked(), equalTo(checked));
	}
	
	@Test
	public void whenViewIsChecked_ThenUpdateValueModel()
	{
		boolean initialValueModelValue = trueOrFalse();
		PropertyValueModel<Boolean> valueModel = ValueModelUtils.createBoolean(initialValueModelValue);
	
		attribute.observeChangesOnTheView(valueModel);
		view.setChecked(!initialValueModelValue);
		
		assertThat(valueModel.getValue(), equalTo(!initialValueModelValue));
	}
	
}
