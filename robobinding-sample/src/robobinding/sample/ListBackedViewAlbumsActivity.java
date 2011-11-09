/**
 * ViewAlbumsActivity.java
 * 7 Oct 2011 Copyright Cheng Wei and Robert Taylor
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

import robobinding.binding.Binder;
import robobinding.sample.dao.AlbumDao;
import robobinding.sample.presentationmodel.ListBackedViewAlbumsPresentationModel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 
 * @since 1.0
 * @author Cheng Wei, 
 * @author Robert Taylor
 */
public class ListBackedViewAlbumsActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		initViewAndBind();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		initViewAndBind();
	}

	protected void initViewAndBind()
	{
		ListBackedViewAlbumsPresentationModel viewAlbumsPresentationModel = new ListBackedViewAlbumsPresentationModel(this, new AlbumDao());
		Binder binder = new Binder();
		binder.setAndBindContentView(this, R.layout.view_albums_activity, viewAlbumsPresentationModel);
	}
}
