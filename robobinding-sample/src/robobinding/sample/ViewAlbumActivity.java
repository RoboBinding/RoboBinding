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
package robobinding.sample;

import robobinding.binding.ActivityBinder;
import robobinding.sample.presentationmodel.ViewAlbumPresentationModel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class ViewAlbumActivity extends Activity
{
	public static final String ALBUM_ID = "album_id";
	
	private ViewAlbumPresentationModel viewAlbumPresentationModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		long albumId = intent.getLongExtra(ALBUM_ID, 0);
		
		ActivityBinder binder = new ActivityBinder(this, R.layout.view_album_activity);
		viewAlbumPresentationModel = new ViewAlbumPresentationModel(this, albumId);
		binder.bindTo(viewAlbumPresentationModel);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		viewAlbumPresentationModel.refresh();
	}
}
