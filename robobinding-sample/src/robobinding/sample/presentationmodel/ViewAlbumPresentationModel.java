/**
 * ViewAlbumPresentationModel.java
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

import robobinding.beans.PropertyAdapter;
import robobinding.sample.dao.AlbumDao;
import robobinding.sample.model.Album;
import robobinding.value.Converters;

/**
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 *
 */
public class ViewAlbumPresentationModel implements CustomPropertyProvider
{
	private Album album;
	
	public ViewAlbumPresentationModel(AlbumDao albumDao, long albumId)
	{
		this.album = albumDao.get(albumId);
	}

	public String getTitle()
	{
		return album.getTitle();
	}
	
	public String getArtist()
	{
		return album.getArtist();
	}
	
	public String getComposer()
	{
		return album.getComposer();
	}
	
	public boolean isComposerEnabled()
	{
		return album.isClassical();
	}
	
	@Override
	public PropertyAdapter<?> createCustomProperty(String propertyName)
	{
		if ("classicalDescription".equals(propertyName))
			Converters.createBooleanToStringConverter(album.isClassical(), "Classical", "Not classical");
		
		return null;
	}
}
