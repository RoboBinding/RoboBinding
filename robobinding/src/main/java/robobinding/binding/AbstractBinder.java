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

import robobinding.binding.BindingViewFactory.InflatedView;
import robobinding.presentationmodel.PresentationModelAdapter;
import robobinding.presentationmodel.PresentationModelAdapterImpl;
import android.content.Context;
import android.view.LayoutInflater;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractBinder
{
	protected InflatedView inflateAndBind(Context context, int layoutId, Object presentationModel, BindingViewFactory bindingViewFactory)
	{
		PresentationModelAdapter presentationModelAdapter = new PresentationModelAdapterImpl(presentationModel);
		
		InflatedView inflatedView = bindingViewFactory.inflateView(layoutId, context);
		inflatedView.bindChildViews(presentationModelAdapter, context);
		
		return inflatedView;
	}
	
	protected BindingViewFactory createBindingViewFactory(Context context, boolean autoInitializeViews)
	{
		LayoutInflater layoutInflater = LayoutInflater.from(context).cloneInContext(context);
		BindingAttributesLoader bindingAttributesLoader = new BindingAttributesLoader(autoInitializeViews);
		return new BindingViewFactory(layoutInflater, bindingAttributesLoader);
	}
}
