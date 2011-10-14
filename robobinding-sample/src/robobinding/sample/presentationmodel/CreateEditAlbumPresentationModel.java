/**
 * CreateAlbumPresentationModel.java
 * 10 Oct 2011 Copyright Cheng Wei and Robert Taylor
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

import robobinding.beans.CustomPropertyDescriptor;
import robobinding.presentationmodel.AbstractPresentationModel;
import robobinding.presentationmodel.CustomPropertyProvider;
import robobinding.presentationmodel.DependentPropertyValueModelProvider;
import robobinding.sample.R;
import robobinding.sample.dao.AlbumDao;
import robobinding.sample.model.Album;
import robobinding.value.Converters;
import robobinding.value.ValueModel;
import android.content.Context;

/**
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 *
 */
public class CreateEditAlbumPresentationModel extends AbstractPresentationModel implements CustomPropertyProvider
{
	private Album.Builder albumBuilder;
	private AlbumDao albumDao;
	private Context context;
	
	public CreateEditAlbumPresentationModel(Context context, AlbumDao albumDao, long albumId)
	{
		initialize(context, albumDao);
		Album album = albumDao.get(albumId);
		albumBuilder = album.createBuilder();
	}
	
	public CreateEditAlbumPresentationModel(Context context, AlbumDao albumDao)
	{
		initialize(context, albumDao);
		albumBuilder = new Album.Builder();
	}
	
	private void initialize(Context context, AlbumDao albumDao)
	{
		this.context = context;
		this.albumDao = albumDao;
	}
	
	public String getWindowTitle()
	{
		if (albumBuilder.isNew())
			return context.getString(R.string.create_album);
		
		return context.getString(R.string.edit_album);
	}
	
	public boolean isComposerEnabled()
	{
		return isClassical();
	}
	
	public void save()
	{
		albumDao.save(albumBuilder.create());
	}
	
	public String getTitle()
	{
		return albumBuilder.getTitle();
	}

	public void setTitle(String title)
	{
		String oldValue = albumBuilder.getTitle();
		albumBuilder.setTitle(title);
		
		firePropertyChange("title", oldValue, title);
	}

	public String getArtist()
	{
		return albumBuilder.getArtist();
	}

	public void setArtist(String artist)
	{
		String oldValue = albumBuilder.getArtist();
		albumBuilder.setArtist(artist);
		
		firePropertyChange("artist", oldValue, artist);
	}

	public boolean isClassical()
	{
		return albumBuilder.isClassical();
	}

	public void setClassical(boolean classical)
	{
		boolean oldValue = albumBuilder.isClassical();
		albumBuilder.setClassical(classical);
		
		firePropertyChange("classical", oldValue, classical);
		firePropertyChange("composerEnabled", oldValue, classical);
	}

	public String getComposer()
	{
		return albumBuilder.getComposer();
	}

	public void setComposer(String composer)
	{
		String oldValue = albumBuilder.getComposer();
		albumBuilder.setComposer(composer);
		
		firePropertyChange("composer", oldValue, composer);
	}

	@Override
	public CustomPropertyDescriptor<?> createCustomProperty(String propertyName, DependentPropertyValueModelProvider propertyValueModelAccessor)
	{
		if ("windowTitle".equals(propertyName))
		{
			ValueModel<Boolean> dependentValueModel = propertyValueModelAccessor.getDependentPropertyValueModel("classical");
			ValueModel<String> valueModel = Converters.createBooleanToStringConverter(dependentValueModel, "Edit Classical Album", "Edit Album");
			return CustomPropertyDescriptor.createReadOnlyPropertyDescriptor(valueModel);
		}
		return null;
	}

	
}
