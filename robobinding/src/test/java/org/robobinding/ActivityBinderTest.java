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
package org.robobinding;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.ActivityBinder;
import org.robobinding.BinderImplementor;

import android.app.Activity;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class ActivityBinderTest
{
	private Activity activity;
	private BinderImplementor binderImplementor;
	private Object presentationModel;
	private int layoutId;

	@Before
	public void setUp()
	{
		activity = mock(Activity.class);
		binderImplementor = mock(BinderImplementor.class);
		presentationModel = new Object();
		layoutId = 0;
	}

	@Test
	public void whenInflateAndBind_thenContentViewShouldBeSetToResultView()
	{
		View resultView = mock(View.class);
		when(binderImplementor.inflateAndBind(layoutId, presentationModel)).thenReturn(resultView);

		inflateAndBind();

		verify(activity).setContentView(resultView);
	}

	private void inflateAndBind()
	{
		ActivityBinder activityBinder = new ActivityBinder(activity, binderImplementor);
		activityBinder.inflateAndBind(layoutId, presentationModel);
	}
}
