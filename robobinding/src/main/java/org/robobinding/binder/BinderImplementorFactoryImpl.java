<<<<<<< HEAD:robobinding/src/main/java/org/robobinding/binding/BinderImplementorFactoryImpl.java
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
package org.robobinding.binding;

<<<<<<< HEAD:robobinding/src/main/java/org/robobinding/binder/BinderImplementorFactoryImpl.java
import org.robobinding.BinderImplementor;
import org.robobinding.BinderImplementorFactory;
import org.robobinding.BindingContext;
import org.robobinding.binder.BinderImplementorImpl.BindingContextCreator;
import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;
=======
import org.robobinding.binder.BinderImplementor;
import org.robobinding.binder.BinderImplementorFactory;
import org.robobinding.binder.BindingContext;
import org.robobinding.binding.BinderImplementorImpl.BindingContextCreator;
>>>>>>> Apply PredefinedViewPendingAttributes and BindingContext ideas.:robobinding/src/main/java/org/robobinding/binding/BinderImplementorFactoryImpl.java

import android.content.Context;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class BinderImplementorFactoryImpl implements BinderImplementorFactory, BindingContextCreator
{
	private Context context;
	private boolean preInitializeViews;
	private ErrorFormatter errorFormatter;
	public BinderImplementorFactoryImpl(Context context, boolean preInitializeViews, ErrorFormatter errorFormatter)
	{
		this.context = context;
		this.preInitializeViews = preInitializeViews;
	}
	
	@Override
	public BinderImplementor create()
	{
		return new BinderImplementorImpl(context, this, errorFormatter);
	}

	@Override
	public BindingContext create(Object presentationModel)
	{
		return new BindingContext(this, context, preInitializeViews, presentationModel);
	}
	
	public static BinderImplementor create(Context context, boolean preInitializeViews)
	{
		BinderImplementorFactory binderImplementorFactory = new BinderImplementorFactoryImpl(context, preInitializeViews, new PlainTextErrorFormatter());
		return binderImplementorFactory.create();
	}
}
=======
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

import org.robobinding.BinderImplementor;
import org.robobinding.BinderImplementorFactory;
import org.robobinding.BindingContext;
import org.robobinding.binder.BinderImplementorImpl.BindingContextCreator;

import android.content.Context;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class BinderImplementorFactoryImpl implements BinderImplementorFactory, BindingContextCreator
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
		return new BinderImplementorImpl(context, this, preInitializeViews);
	}

	@Override
	public BindingContext create(Object presentationModel)
	{
		return new BindingContext(this, context, presentationModel, preInitializeViews);
	}
	
	public static BinderImplementor create(Context context, boolean preInitializeViews)
	{
		BinderImplementorFactory binderImplementorFactory = new BinderImplementorFactoryImpl(context, preInitializeViews);
		return binderImplementorFactory.create();
	}
}
>>>>>>> Refactoring package names as discussed:robobinding/src/main/java/org/robobinding/binder/BinderImplementorFactoryImpl.java
