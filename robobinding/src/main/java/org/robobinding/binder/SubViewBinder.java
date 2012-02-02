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

import org.robobinding.binder.BindingViewFactory.InflatedView;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.presentationmodel.PresentationModelAdapterImpl;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class SubViewBinder extends AbstractBinder
{
	private final int layoutId;

	public SubViewBinder(Context context, int layoutId)
	{
		super(context, true);
		this.layoutId = layoutId;
	}

	public View bindTo(Object presentationModel)
	{
		PresentationModelAdapter presentationModelAdapter = new PresentationModelAdapterImpl(presentationModel);
		InflatedView inflatedView = inflateAndBind(layoutId, presentationModelAdapter);
		return inflatedView.getRootView();
	}

	public View bindToAndAttachToRoot(Object presentationModel, ViewGroup viewGroup)
	{
		PresentationModelAdapter presentationModelAdapter = new PresentationModelAdapterImpl(presentationModel);
		InflatedView inflatedView = inflateAttachToRootAndBind(layoutId, presentationModelAdapter, viewGroup);
		return inflatedView.getRootView();
	}
}