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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.robobinding.presentationmodel.PresentationModelAdapter;

import android.content.Context;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockBindingContext
{
	private BindingContext bindingContext;
	private MockBindingContext()
	{
		bindingContext = mock(BindingContext.class);
	}
	
	private void declarePresentationModelAdapter(PresentationModelAdapter presentationModelAdapter)
	{
		when(bindingContext.getPresentationModelAdapter()).thenReturn(presentationModelAdapter);
	}
	
	private void declareContext(Context context)
	{
		when(bindingContext.getContext()).thenReturn(context);
	}
	
	private void declarePreInitializeViews(boolean preInitializeViews)
	{
		when(bindingContext.shouldPreInitializeViews()).thenReturn(preInitializeViews);
	}
	
	public static BindingContext create(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		MockBindingContext creator = new MockBindingContext();
		creator.declarePresentationModelAdapter(presentationModelAdapter);
		creator.declareContext(context);
		return creator.bindingContext;
	}
	
	public static BindingContext create(PresentationModelAdapter presentationModelAdapter, Context context, boolean preInitializeViews)
	{
		MockBindingContext creator = new MockBindingContext();
		creator.declarePresentationModelAdapter(presentationModelAdapter);
		creator.declareContext(context);
		creator.declarePreInitializeViews(preInitializeViews);
		return creator.bindingContext;
	}
	
	public static BindingContext create(Context context)
	{
		MockBindingContext creator = new MockBindingContext();
		creator.declareContext(context);
		return creator.bindingContext;
	}

	public static BindingContext create(Context context, boolean preInitializeViews)
	{
		MockBindingContext creator = new MockBindingContext();
		creator.declareContext(context);
		creator.declarePreInitializeViews(preInitializeViews);
		return creator.bindingContext;
	}

	public static BindingContext create(PresentationModelAdapter presentationModelAdapter)
	{
		MockBindingContext creator = new MockBindingContext();
		creator.declarePresentationModelAdapter(presentationModelAdapter);
		return creator.bindingContext;
	}
}
