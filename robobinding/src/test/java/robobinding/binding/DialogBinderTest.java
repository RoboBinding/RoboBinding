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

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.binding.BindingAttributesProcessor.ViewBindingAttributes;
import robobinding.binding.BindingViewFactory.InflatedView;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
@Ignore
public class DialogBinderTest
{	
	private Object presentationModel = new Object();
	private int layoutId = 0;
	private Dialog dialog = mock(Dialog.class);
	private BindingViewFactory bindingViewFactory;
	private View inflatedRootView;

	public void setUp()
	{
		Context dialogContext = new Activity();
		when(dialog.getContext()).thenReturn(dialogContext);
		inflatedRootView = new View(dialogContext);
		InflatedView inflatedView = new InflatedView(inflatedRootView, new ArrayList<ViewBindingAttributes>());
		bindingViewFactory = mock(BindingViewFactory.class);
		when(bindingViewFactory.inflateView(layoutId, dialogContext)).thenReturn(inflatedView);
	}
	
	@Test
	public void whenBindingToPresentationModel_ThenSetContentViewReturnedFromBindingInflater()
	{
		DialogBinder dialogBinder = new DialogBinder(dialog, layoutId);
		dialogBinder.setBindingViewFactory(bindingViewFactory);
		dialogBinder.bindTo(presentationModel);
		
		verify(dialog).setContentView(inflatedRootView);
	}
	
	@Test
	public void givenAPresentationModelWithTitleProperty_WhenBinding_ThenTitleOfInflatedDialogShouldEqualProperty()
	{
		MockPresentationModelWithDialogTitle presentationModelWithDialogTitle = new MockPresentationModelWithDialogTitle();
		
		DialogBinder dialogBinder = new DialogBinder(dialog, layoutId);
		dialogBinder.setBindingViewFactory(bindingViewFactory);
		dialogBinder.bindTo(presentationModelWithDialogTitle);
	
		verify(dialog).setTitle(presentationModelWithDialogTitle.getTitle());
	}
	
	
	public static class MockPresentationModelWithDialogTitle
	{
		public String getTitle()
		{
			return "dialogTitle";
		}
	}
}
