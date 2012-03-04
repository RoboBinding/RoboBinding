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
package org.robobinding.binder;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.binder.BindingAttributeProcessor.ViewBindingAttributes;
import org.robobinding.binder.BindingViewFactory.InflatedView;
import org.robobinding.viewattribute.RandomValues;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class SubviewBinderTest
{
	private Object presentationModel = new Object();
	private int layoutId = RandomValues.anyInteger();
	private Activity context = mock(Activity.class);
	private View rootView = mock(View.class);
	private ViewGroup viewGroup = mock(ViewGroup.class);
	private BindingViewFactory bindingViewFactory = mock(BindingViewFactory.class);
	
	@Test
	public void whenBindingAndAttachingToRoot_thenShouldInteractCorrectlyWithCollaborator()
	{
		InflatedView inflatedView = new InflatedView(rootView, new ArrayList<ViewBindingAttributes>());
		when(bindingViewFactory.inflateViewAndAttachToRoot(layoutId, viewGroup)).thenReturn(inflatedView);
		
		SubViewBinder subviewBinder = new SubViewBinder(context, layoutId);
		subviewBinder.setBindingViewFactory(bindingViewFactory);
		View inflatedRootView = subviewBinder.bindToAndAttachToRoot(presentationModel, viewGroup);
		
		assertThat(rootView, sameInstance(inflatedRootView));
	}
}
