/**
 * CursorBackedViewAlbumsActivity.java
 * 11 Oct 2011 Copyright Cheng Wei and Robert Taylor
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
import robobinding.sample.model.PurchaseService;
import robobinding.sample.presentationmodel.CursorBackedViewAlbumsPresentationModel;
import android.app.Activity;
import android.os.Bundle;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class CursorBackedViewAlbumsActivity extends Activity
{
	private CursorBackedViewAlbumsPresentationModel viewAlbumsPresentationModel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		ActivityBinder binder = new ActivityBinder(this, R.layout.view_purchasable_albums_activity);
		viewAlbumsPresentationModel = new CursorBackedViewAlbumsPresentationModel(this, new PurchaseService());
		binder.bindTo(viewAlbumsPresentationModel);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		viewAlbumsPresentationModel.refreshPresentationModel();
	}
	
}
