/**
 * AlbumItemPresentationModel.java
 * 17 Oct 2011 Copyright Cheng Wei and Robert Taylor
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

import robobinding.itempresentationmodel.ItemPresentationModel;
import robobinding.presentationmodel.AbstractPresentationModel;
import robobinding.sample.model.Album;

/**
 * 
 * @since 1.0
 * @author Robert Taylor
 */
public class AlbumItemPresentationModel extends AbstractPresentationModel implements ItemPresentationModel<Album>
{
	protected Album album;

	public String getTitle()
	{
		return album.getTitle();
	}
	
	public String getArtist()
	{
		return album.getArtist();
	}
	
	@Override
	public void setData(int index, Album bean)
	{
		this.album = bean;
		
		presentationModelChangeSupport.fireChangeAll();
	}
}
