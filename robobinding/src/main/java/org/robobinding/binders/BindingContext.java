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
package org.robobinding.binders;

import org.robobinding.presentationmodel.PresentationModelAdapter;

import android.content.Context;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingContext
{
	private BinderImplementorFactory factory;
	public BindingContext(BinderImplementorFactory factory, Context context, boolean preInitializeViews, Object presentationModel)
	{
		// TODO Auto-generated constructor stub
	}
	
	public boolean shouldPreInitializeViews()
	{
		return false;
	}
	
	public PresentationModelAdapter getPresentationModelAdapter()
	{
		return null;
	}

	public Context getContext()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public ItemBinder1 createItemBinder()
	{
		return null;
	}
	
	public ViewBinder createViewBinder()
	{
		return null;
	}
}
