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

import org.robobinding.binder.BinderImplementorImpl.BindingContextCreator;
import org.robobinding.binders.BinderImplementor;
import org.robobinding.binders.BinderImplementorFactory;
import org.robobinding.binders.BindingContext;

import android.content.Context;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BinderImplementorFactoryImpl implements BinderImplementorFactory, BindingContextCreator
{
	private Context context;
	private boolean preInitializeViews;
	public BinderImplementorFactoryImpl(Context context, boolean preInitializeViews)
	{
		this.context = context;
		this.preInitializeViews = preInitializeViews;
	}
	
	@Override
	public BinderImplementor create()
	{
		return new BinderImplementorImpl(context, this);
	}

	@Override
	public BindingContext create(Object presentationModel)
	{
		return new BindingContext(this, context, preInitializeViews, presentationModel);
	}
	
	public static BinderImplementor create(Context context, boolean preInitializeViews)
	{
		BinderImplementorFactory binderImplementorFactory = new BinderImplementorFactoryImpl(context, preInitializeViews);
		return binderImplementorFactory.create();
	}
}
