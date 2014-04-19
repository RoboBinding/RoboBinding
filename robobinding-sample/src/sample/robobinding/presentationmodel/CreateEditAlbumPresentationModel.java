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

import org.robobinding.presentationmodel.DependsOnStateOf;
import org.robobinding.presentationmodel.PresentationModel;

import sample.robobinding.R;
import sample.robobinding.model.Album;
import sample.robobinding.store.AlbumStore;
import android.app.Activity;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@PresentationModel
public class CreateEditAlbumPresentationModel {
	private static final String CLASSICAL = "classical";

	private Album.Builder albumBuilder;
	private Activity activity;

	public CreateEditAlbumPresentationModel(Activity activity, long albumId) {
		this.activity = activity;

		if (Album.isNew(albumId))
			albumBuilder = new Album.Builder();
		else {
			Album album = AlbumStore.get(albumId);
			albumBuilder = album.createBuilder();
		}
	}

	public void save() {
		AlbumStore.save(albumBuilder.create());
		activity.finish();
	}

	public String getTitle() {
		return albumBuilder.getTitle();
	}

	public void setTitle(String title) {
		albumBuilder.setTitle(title);
	}

	public String getArtist() {
		return albumBuilder.getArtist();
	}

	public void setArtist(String artist) {
		albumBuilder.setArtist(artist);
	}

	public boolean isClassical() {
		return albumBuilder.isClassical();
	}

	public void setClassical(boolean classical) {
		albumBuilder.setClassical(classical);
	}

	@DependsOnStateOf(CLASSICAL)
	public boolean isComposerEnabled() {
		return isClassical();
	}

	public String getComposer() {
		return albumBuilder.getComposer();
	}

	public void setComposer(String composer) {
		albumBuilder.setComposer(composer);
	}

	@DependsOnStateOf(CLASSICAL)
	public String getWindowTitle() {
		if (albumBuilder.isNew())
			return activity.getString(R.string.create_album);

		return isClassical() ? "Edit Classical Album" : "Edit Album";
	}
}
