/**
 * ViewAlbumsPresentationModel.java
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

import java.util.Collection;
import java.util.Collections;

import robobinding.beans.PropertyAdapter;
import robobinding.sample.CreateEditAlbumActivity;
import robobinding.sample.ViewAlbumActivity;
import robobinding.sample.contact.CollectionValueModel;
import robobinding.sample.dao.AlbumDao;
import robobinding.sample.event.ItemClickEvent;
import robobinding.sample.model.Album;
import android.content.Context;
import android.content.Intent;

/**
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 *
 */
public class ViewAlbumsPresentationModel implements CustomPropertyProvider
{
	private static final String PROPERTY_ALBUMS = "albums";
	
	private Context context;
	private AlbumDao albumDao;

	public ViewAlbumsPresentationModel(Context context, AlbumDao albumDao)
	{
		this.context = context;
		this.albumDao = albumDao;
	}
	
	public Collection<Album> getAlbums()
	{
		return albumDao.getAll();
	}
	
	public void createAlbum()
	{
		context.startActivity(new Intent(context, CreateEditAlbumActivity.class));
	}

	public void viewAlbum(ItemClickEvent event)
	{
		Album album = albumDao.get(event.getId());
		
		Intent intent = new Intent(context, ViewAlbumActivity.class);
		intent.putExtra(ViewAlbumActivity.ALBUM_ID, album.getId());
		context.startActivity(intent);
	}
	
	@Override
	public PropertyAdapter<?> createCustomProperty(String propertyName)
	{
		if(PROPERTY_ALBUMS.equals(propertyName))
		{
			return new CollectionValueModel<AlbumPresentationModel>(AlbumPresentationModel.class);
		}
		return null;
	}
	
//	private void scratchPad()
//	{
//		
//		Object presentationModel = null;
//		String propertyName;
//		
//		if (presentationModel instanceof CustomPropertyProvider)
//		{
//			CustomPropertyProvider customPropertyProvider = (CustomPropertyProvider)presentationModel;
//			if (customPropertyProvider.providesCustomPropertySupport(propertyName))
//			{
//				PropertyAdapter<?> propertyAdapter = customPropertyProvider.createProperty(propertyName);
//			}
//		}
//		else
//		{
//			PropertyAdapter<?> propertyAdapter = new PropertyAdapter<?>(propertyName, presentationModel);
//		}
//		
//	}
}
