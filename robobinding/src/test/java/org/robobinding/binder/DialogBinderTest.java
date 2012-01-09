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
package org.robobinding.binder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.binder.BindingAttributeProcessor.ViewAttributes;
import org.robobinding.binder.BindingViewFactory.InflatedView;
import org.robobinding.presentationmodel.DialogPresentationModel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class DialogBinderTest
{	
	private int layoutId = 0;
	private Dialog dialog = mock(Dialog.class);
	private BindingViewFactory bindingViewFactory;
	private View inflatedRootView;

	@Before
	public void setUp()
	{
		Context dialogContext = new Activity();
		when(dialog.getContext()).thenReturn(dialogContext);
		inflatedRootView = new View(dialogContext);
		InflatedView inflatedView = new InflatedView(inflatedRootView, new ArrayList<ViewAttributes>());
		bindingViewFactory = mock(BindingViewFactory.class);
		when(bindingViewFactory.inflateView(layoutId)).thenReturn(inflatedView);
	}
	
	@Test
	public void whenBindingToPresentationModel_thenSetContentViewReturnedFromBindingInflater()
	{
		Object presentationModel = new Object();
		
		initializeDialogBinderAndBindTo(presentationModel);
		
		verify(dialog).setContentView(inflatedRootView);
	}
	
	@Test
	public void givenADialogPresentationModel_whenBinding_thenTitleOfInflatedDialogShouldEqualTitleProperty()
	{
		MockDialogPresentationModelWithTitle dialogPresentationModelWithTitle = new MockDialogPresentationModelWithTitle();
		
		initializeDialogBinderAndBindTo(dialogPresentationModelWithTitle);
	
		verify(dialog).setTitle(dialogPresentationModelWithTitle.getTitle());
	}

	@Test
	public void givenADialogPresentationModelWithANullTitleProperty_whenBinding_thenTitleOfInflatedDialogShouldBeGone()
	{
		MockDialogPresentationModelWithNullTitle dialogPresentationModelWithNullTitle = new MockDialogPresentationModelWithNullTitle();
		
		initializeDialogBinderAndBindTo(dialogPresentationModelWithNullTitle);
		
		verify(dialog).requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	private void initializeDialogBinderAndBindTo(Object presentationModel)
	{
		DialogBinder dialogBinder = new DialogBinder(dialog, layoutId);
		dialogBinder.setBindingViewFactory(bindingViewFactory);
		dialogBinder.bindTo(presentationModel);
	}
	
	public static class MockDialogPresentationModelWithTitle implements DialogPresentationModel
	{
		public String getTitle()
		{
			return "dialogTitle";
		}
	}
	
	public static class MockDialogPresentationModelWithNullTitle implements DialogPresentationModel
	{
		public String getTitle()
		{
			return null;
		}
	}
}
