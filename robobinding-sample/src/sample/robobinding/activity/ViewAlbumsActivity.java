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
package sample.robobinding.activity;

import org.robobinding.binder.Binders;

import sample.robobinding.R;
import sample.robobinding.presentationmodel.ViewAlbumsPresentationModel;
import android.app.Activity;
import android.os.Bundle;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ViewAlbumsActivity extends Activity
{
	protected ViewAlbumsPresentationModel presentationModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		presentationModel = new ViewAlbumsPresentationModel(this);
		Binders.bindWithoutPreInitializingViews(this, R.layout.view_albums_activity, presentationModel);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		presentationModel.refreshPresentationModel();
	}

}