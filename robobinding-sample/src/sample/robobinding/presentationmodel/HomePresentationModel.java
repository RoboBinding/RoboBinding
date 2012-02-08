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

import org.robobinding.presentationmodel.PresentationModel;

import sample.robobinding.CursorBackedViewAlbumsActivity;
import sample.robobinding.DynamicDropdownLayoutSpinnerAlbumsActivity;
import sample.robobinding.DynamicItemLayoutListViewAlbumsActivity;
import sample.robobinding.ListViewAlbumsActivity;
import sample.robobinding.ListViewWithPredefinedViewsAlbumsActivity;
import sample.robobinding.SpinnerAlbumsActivity;
import sample.robobinding.SpinnerWithPredefinedViewsAlbumsActivity;
import android.content.Context;
import android.content.Intent;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@PresentationModel
public class HomePresentationModel
{
	private Context context;

	public HomePresentationModel(Context context)
	{
		this.context = context;
	}

	public void cursorBackedAlbumsListView()
	{
		context.startActivity(new Intent(context, CursorBackedViewAlbumsActivity.class));
	}
	
	public void listBackedAlbumsListView()
	{
		context.startActivity(new Intent(context, ListViewAlbumsActivity.class));
	}
	
	public void listBackedAlbumsListViewWithPredefinedViews()
	{
		context.startActivity(new Intent(context, ListViewWithPredefinedViewsAlbumsActivity.class));
	}
	
	public void listBackedAlbumsSpinner()
	{
		context.startActivity(new Intent(context, SpinnerAlbumsActivity.class));
	}
	
	public void listBackedAlbumsSpinnerWithPredefinedViews()
	{
		context.startActivity(new Intent(context, SpinnerWithPredefinedViewsAlbumsActivity.class));
	}
	
	public void dynamicItemLayoutListBackedAlbumsListView()
	{
		context.startActivity(new Intent(context, DynamicItemLayoutListViewAlbumsActivity.class));
	}
	
	public void dynamicDropdownLayoutListBackedAlbumsSpinner()
	{
		context.startActivity(new Intent(context, DynamicDropdownLayoutSpinnerAlbumsActivity.class));
	}
}
