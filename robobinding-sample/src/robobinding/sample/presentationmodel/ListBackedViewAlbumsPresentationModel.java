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

import java.util.List;

import robobinding.beans.PropertyAdapter;
import robobinding.presentationmodel.CustomPropertyProvider;
import robobinding.presentationmodel.ItemClickEvent;
import robobinding.presentationmodel.ListValueModel;
import robobinding.sample.CreateEditAlbumActivity;
import robobinding.sample.ViewAlbumActivity;
import robobinding.sample.dao.AlbumDao;
import robobinding.sample.model.Album;
import android.content.Context;
import android.content.Intent;
import android.view.ViewParent;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 *
 */
public class ListBackedViewAlbumsPresentationModel extends AbstractViewAlbumsPresentationModel implements CustomPropertyProvider
{
	public ListBackedViewAlbumsPresentationModel(Context context, AlbumDao albumDao)
	{
		super(context, albumDao);
	}

	@Override
	public PropertyAdapter<?> createCustomProperty(String propertyName)
	{
		if(PROPERTY_ALBUMS.equals(propertyName))
		{
			List<Album> albums = Lists.newArrayList(albumDao.getAll());
			return new ListValueModel<Album>(ViewAlbumPresentationModel.class, albums);
		}
		return null;
	}
}
