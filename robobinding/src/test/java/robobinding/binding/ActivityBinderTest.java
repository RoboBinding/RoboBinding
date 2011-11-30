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

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.binding.BindingAttributesProcessor.ViewBindingAttributes;
import robobinding.binding.BindingViewFactory.InflatedView;
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
	private Object presentationModel = new Object();
	private int layoutId = 0;
	private Activity activity = mock(Activity.class);
	
	@Test
	public void whenBindingToPresentationModel_ThenSetContentViewReturnedFromBindingInflater()
	{
		View rootView = new View(activity);
		InflatedView inflatedView = new InflatedView(rootView, new ArrayList<ViewBindingAttributes>());
		BindingViewFactory bindingViewFactory = mock(BindingViewFactory.class);
		when(bindingViewFactory.inflateView(layoutId, activity)).thenReturn(inflatedView);
		
		ActivityBinder activityBinder = new ActivityBinder(activity, layoutId);
		activityBinder.setBindingViewFactory(bindingViewFactory);
		activityBinder.bindTo(presentationModel);
		
		verify(activity).setContentView(rootView);
	}
}
