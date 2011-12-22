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

import org.robobinding.binder.BindingViewFactory.InflatedView;
import org.robobinding.presentationmodel.PresentationModelAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractBinder
{
	protected final Context context;
	protected final BindingAttributesProcessor bindingAttributesProcessor;
	
	private BindingViewFactory bindingViewFactory;
	
	public AbstractBinder(Context context)
	{
		this(context, false);
	}
	
	public AbstractBinder(Context context, boolean preInitializeViews)
	{
		this.context = context;
		bindingAttributesProcessor = new BindingAttributesProcessor(new AttributeSetParser(), preInitializeViews);
	}

	protected InflatedView inflateAndBind(int layoutId, PresentationModelAdapter presentationModelAdapter)
	{
		ensureBindingFactoryInitialized();
		
		InflatedView inflatedView = bindingViewFactory.inflateView(layoutId, context);
		inflatedView.bindChildViews(presentationModelAdapter, context);
		
		return inflatedView;
	}
	
	protected InflatedView inflateAndBind_attachToRoot(int layoutId, PresentationModelAdapter presentationModelAdapter, ViewGroup viewGroup)
	{
		ensureBindingFactoryInitialized();
		
		InflatedView inflatedView = bindingViewFactory.inflateViewAndAttachToRoot(layoutId, viewGroup);
		inflatedView.bindChildViews(presentationModelAdapter, context);
		
		return inflatedView;
	}
	
	private void ensureBindingFactoryInitialized()
	{
		if (bindingViewFactory == null)
		{
			LayoutInflater layoutInflater = LayoutInflater.from(context).cloneInContext(context);
			bindingViewFactory = new BindingViewFactory(layoutInflater, bindingAttributesProcessor);
		}
	}
	
	void setBindingViewFactory(BindingViewFactory bindingViewFactory)
	{
		this.bindingViewFactory = bindingViewFactory;
	}
	
}
