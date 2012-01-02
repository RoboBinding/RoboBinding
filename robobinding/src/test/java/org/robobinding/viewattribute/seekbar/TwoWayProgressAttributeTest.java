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
import org.junit.runner.RunWith;
import org.robobinding.property.PropertyValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.viewattribute.AbstractAttributeTest;
import org.robobinding.viewattribute.seekbar.OnSeekBarChangeListeners;
import org.robobinding.viewattribute.seekbar.TwoWayProgressAttribute;

import android.app.Activity;
import android.widget.SeekBar;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TwoWayProgressAttributeTest extends AbstractAttributeTest<SeekBar, TwoWayProgressAttribute>
{
	private SeekBar seekBar;

	@Before
	public void setUp()
	{
		OnSeekBarChangeListeners onSeekBarChangeListeners = new OnSeekBarChangeListeners();
		view.setOnSeekBarChangeListener(onSeekBarChangeListeners);
	}
	
	@Test
	public void whenUpdatingValueModel_ThenSetProgressOnSeekBar()
	{
		int NEW_PROGRESS_VALUE = anyInteger();
		
		attribute.valueModelUpdated(NEW_PROGRESS_VALUE);
		
		assertThat(seekBar.getProgress(), is(NEW_PROGRESS_VALUE));
	}
	
	@Test
	public void whenUpdatingTheSeekBar_ThenUpdateValueModel()
	{
		int NEW_PROGRESS_VALUE = anyInteger();
		
		attribute.observeChangesOnTheView(valueModel);
		
		seekBar.setProgress(NEW_PROGRESS_VALUE);
		
		assertThat(valueModel.getValue(), equalTo(NEW_PROGRESS_VALUE));
	}	
}
