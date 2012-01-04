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
package org.robobinding.viewattribute.seekbar;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.PropertyValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.RandomValues;

import android.widget.SeekBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TwoWayProgressAttributeTest extends AbstractPropertyViewAttributeTest<SeekBar, TwoWayProgressAttribute>
{
	@Before
	public void setUp()
	{
		attribute.setOnSeekBarChangeListeners(new OnSeekBarChangeListeners());
	}
	
	@Test
	public void whenUpdatingValueModel_ThenSetProgressOnSeekBar()
	{
		int newProgressValue = RandomValues.anyInteger();
		
		attribute.valueModelUpdated(newProgressValue);
		
		assertThat(view.getProgress(), is(newProgressValue));
	}
	
	@Test
	public void whenUpdatingTheSeekBar_ThenUpdateValueModel()
	{
		int initialProgressValue = RandomValues.anyInteger();
		PropertyValueModel<Integer> valueModel = initializeForTwoWayBinding(initialProgressValue);
		
		int newProgressValue = RandomValues.anyInteger();
		view.setProgress(newProgressValue);
		
		assertThat(valueModel.getValue(), equalTo(newProgressValue));
	}	
}
