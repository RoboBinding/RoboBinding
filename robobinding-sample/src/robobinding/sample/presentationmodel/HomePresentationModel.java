/**
 * HomePresentationModel.java
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

import robobinding.sample.CursorBackedViewAlbumsActivity;
import robobinding.sample.ListViewAlbumsActivity;
import robobinding.sample.SpinnerViewAlbumsActivity;
import android.content.Context;
import android.content.Intent;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class HomePresentationModel
{
	private Context context;

	public HomePresentationModel(Context context)
	{
		this.context = context;
	}

	public void cursorBackedAlbums()
	{
		context.startActivity(new Intent(context, CursorBackedViewAlbumsActivity.class));
	}
	
	public void listBackedAlbums()
	{
		context.startActivity(new Intent(context, ListViewAlbumsActivity.class));
	}
	
	public void listBackedSpinnerAlbums()
	{
		context.startActivity(new Intent(context, SpinnerViewAlbumsActivity.class));
	}
}
