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
import robobinding.presentationmodel.DialogPresentationModel;
import robobinding.presentationmodel.PresentationModelAdapter;
import robobinding.presentationmodel.PresentationModelAdapterImpl;
import android.app.Dialog;
import android.view.Window;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class DialogBinder extends AbstractBinder
{
	private final Dialog dialog;
	private final int layoutId;

	public DialogBinder(Dialog dialog, int layoutId)
	{
		super(dialog.getContext(), true);
		this.dialog = dialog;
		this.layoutId = layoutId;
	}

	public void bindTo(Object presentationModel)
	{
		PresentationModelAdapter presentationModelAdapter = new PresentationModelAdapterImpl(presentationModel);
		
		if (presentationModel instanceof DialogPresentationModel)
		{
			DialogPresentationModel dialogPresentationModel = (DialogPresentationModel)presentationModel;
			
			if (dialogPresentationModel.getTitle() == null)
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			else
				dialog.setTitle(dialogPresentationModel.getTitle());
		}

		InflatedView inflatedView = inflateAndBind(layoutId, presentationModelAdapter);
		dialog.setContentView(inflatedView.getRootView());
	}
}
