/**
 * CursorBackedViewAlbumsPresentationModel.java
 * 11 Oct 2011 Copyright Cheng Wei and Robert Taylor
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

import robobinding.ItemPresentationModel;
import robobinding.itempresentationmodel.TypedCursor;
import robobinding.sample.dao.AlbumDao;
import robobinding.sample.model.Album;
import robobinding.sample.model.PurchaseService;
import android.content.Context;

/**
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 *
 */
public class CursorBackedViewAlbumsPresentationModel extends AbstractViewAlbumsPresentationModel
{
	private PurchaseService purchaseService;

	public CursorBackedViewAlbumsPresentationModel(Context context, AlbumDao albumDao, PurchaseService purchaseService)
	{
		super(context, albumDao);
		this.purchaseService = purchaseService;
	}
	
	@ItemPresentationModel(value=PurchaseableAlbumItemPresentationModel.class, factoryMethod="createAlbumPresentationModel")
	public TypedCursor<Album> getAlbums()
	{
		return albumDao.getCursor();
	}

	public PurchaseableAlbumItemPresentationModel createAlbumPresentationModel()
	{
		return new PurchaseableAlbumItemPresentationModel(purchaseService);
	}
	
}
