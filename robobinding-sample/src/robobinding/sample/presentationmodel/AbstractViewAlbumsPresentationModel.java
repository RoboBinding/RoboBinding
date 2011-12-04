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
package robobinding.sample.presentationmodel;

import robobinding.binding.viewattribute.ItemClickEvent;
import robobinding.sample.CreateEditAlbumActivity;
import robobinding.sample.ViewAlbumActivity;
import robobinding.sample.model.Album;
import robobinding.sample.store.AlbumStore;
import android.content.Context;
import android.content.Intent;

public abstract class AbstractViewAlbumsPresentationModel
{
	protected static final String PROPERTY_ALBUMS = "albums";

	protected Context context;

	public AbstractViewAlbumsPresentationModel(Context context)
	{
		this.context = context;
	}

	public void createAlbum()
	{
		context.startActivity(new Intent(context, CreateEditAlbumActivity.class));
	}

	public void viewAlbum(ItemClickEvent event)
	{
		Album album = AlbumStore.getByIndex(event.getPosition());

		Intent intent = new Intent(context, ViewAlbumActivity.class);
		intent.putExtra(ViewAlbumActivity.ALBUM_ID, album.getId());
		context.startActivity(intent);
	}
	
}