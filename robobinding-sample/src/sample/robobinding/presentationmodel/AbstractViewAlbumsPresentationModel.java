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
package sample.robobinding.presentationmodel;

import org.robobinding.binding.viewattribute.ItemClickEvent;

import sample.robobinding.CreateEditAlbumActivity;
import sample.robobinding.ViewAlbumActivity;
import sample.robobinding.store.AlbumStore;
import android.content.Context;
import android.content.Intent;

public abstract class AbstractViewAlbumsPresentationModel
{
	protected final Context context;
	private int selectedAlbumIndex;

	public AbstractViewAlbumsPresentationModel(Context context)
	{
		this.context = context;
	}

	public void createAlbum()
	{
		context.startActivity(new Intent(context, CreateEditAlbumActivity.class));
	}

	private void selectAlbum(int position)
	{
		selectedAlbumIndex = position;
	}
	
	public void albumSelected(ItemClickEvent event)
	{
		selectAlbum(event.getPosition());
	}
	
	public void viewAlbum(ItemClickEvent event)
	{
		selectAlbum(event.getPosition());
		viewAlbum();
	}
	
	public boolean isViewAlbumButtonEnabled()
	{
	    return selectedAlbumIndex >= 0;
	}
	
	public void viewAlbum()
	{
		Intent intent = new Intent(context, ViewAlbumActivity.class);
		intent.putExtra(ViewAlbumActivity.ALBUM_ID, AlbumStore.getByIndex(selectedAlbumIndex).getId());
		context.startActivity(intent);
	}
}