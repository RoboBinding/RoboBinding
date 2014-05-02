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
package org.robobinding.albumsample.presentationmodel;

import java.util.List;

import org.robobinding.albumsample.activity.CreateEditAlbumActivity;
import org.robobinding.albumsample.activity.ViewAlbumActivity;
import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.store.AlbumStore;
import org.robobinding.presentationmodel.ItemPresentationModel;
import org.robobinding.presentationmodel.PresentationModel;
import org.robobinding.viewattribute.adapterview.ItemClickEvent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@PresentationModel
public class ViewAlbumsPresentationModel {

	private final Context context;

	public ViewAlbumsPresentationModel(Activity activity) {
		this.context = activity;
	}

	@ItemPresentationModel(AlbumItemPresentationModel.class)
	public List<Album> getAlbums() {
		return Lists.newArrayList(AlbumStore.getAll());
	}

	public void createAlbum() {
		context.startActivity(new Intent(context, CreateEditAlbumActivity.class));
	}

	public void viewAlbum(ItemClickEvent event) {
		viewAlbum(event.getPosition());
	}

	private void viewAlbum(int selectedAlbumPosition) {
		Intent intent = new Intent(context, ViewAlbumActivity.class);
		intent.putExtra(ViewAlbumActivity.ALBUM_ID,
				AlbumStore.getByIndex(selectedAlbumPosition).getId());
		context.startActivity(intent);
	}
}
