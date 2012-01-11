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
package sample.robobinding;

import org.robobinding.binder.Binder;

import sample.robobinding.presentationmodel.DynamicItemLayoutViewAlbumsPresentationModel;
import android.app.Activity;
import android.os.Bundle;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class DynamicDropdownLayoutSpinnerAlbumsActivity extends Activity
{
	private DynamicItemLayoutViewAlbumsPresentationModel viewAlbumsPresentationModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		viewAlbumsPresentationModel = new DynamicItemLayoutViewAlbumsPresentationModel(this);
		Binder.bindWithoutPreInitializingViews(this, R.layout.dynamic_dropdown_layout_view_albums_spinner_activity, viewAlbumsPresentationModel);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		viewAlbumsPresentationModel.refreshPresentationModel();
	}
}
