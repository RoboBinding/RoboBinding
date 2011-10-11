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
package robobinding.binding;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.binding.BindingInflater.InflationResult;
import android.app.Activity;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
@RunWith(RobolectricTestRunner.class)
public class BinderTest
{	
	@Test
	public void whenSettingAndBindingToContentView_ThenSetContentViewReturnedFromBindingInflater()
	{
		int layoutId = 0;
		Activity activity = mock(Activity.class);
		View rootView = new View(activity);
		
		InflationResult inflationResult = new InflationResult(rootView, null);
		
		BindingInflater bindingInflater = mock(BindingInflater.class);
		when(bindingInflater.inflateView(activity, layoutId)).thenReturn(inflationResult);
		Binder binder = new Binder(bindingInflater);
		binder.setAndBindContentView(activity, layoutId, null);
		
		verify(activity).setContentView(rootView);
	}
}
