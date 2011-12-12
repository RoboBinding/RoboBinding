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

import org.robobinding.ItemPresentationModel;
import org.robobinding.itempresentationmodel.TypedCursor;
import org.robobinding.presentationmodelaspects.PresentationModel;

import sample.robobinding.model.Album;
import sample.robobinding.model.PurchaseService;
import sample.robobinding.store.AlbumStore;
import android.app.Activity;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@PresentationModel
public class CursorBackedViewAlbumsPresentationModel extends AbstractViewAlbumsPresentationModel
{
	private PurchaseService purchaseService;

	public CursorBackedViewAlbumsPresentationModel(Activity activity, PurchaseService purchaseService)
	{
		super(activity);
		this.purchaseService = purchaseService;
	}
	
	@ItemPresentationModel(value=PurchasableAlbumItemPresentationModel.class, factoryMethod="createAlbumPresentationModel")
	public TypedCursor<Album> getAlbums()
	{
		return AlbumStore.getCursor();
	}

	public PurchasableAlbumItemPresentationModel createAlbumPresentationModel()
	{
		return new PurchasableAlbumItemPresentationModel(purchaseService);
	}

}
