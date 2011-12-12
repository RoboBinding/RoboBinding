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
package sample.robobinding;

import robobinding.binding.ActivityBinder;
import sample.robobinding.presentationmodel.ListBackedViewAlbumsPresentationModel;
import android.app.Activity;
import android.os.Bundle;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public abstract class AbstractListBackedViewAlbumsActivity extends Activity
{
	private ListBackedViewAlbumsPresentationModel viewAlbumsPresentationModel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		ActivityBinder binder = new ActivityBinder(this, getLayoutId());
		viewAlbumsPresentationModel = new ListBackedViewAlbumsPresentationModel(this);
		binder.bindTo(viewAlbumsPresentationModel);
	}
	
	protected abstract int getLayoutId();

	@Override
	protected void onResume()
	{
		super.onResume();
		viewAlbumsPresentationModel.refreshPresentationModel();
	}

}
