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
package robobinding.binding;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import robobinding.android.R;
import robobinding.binding.RowBinder.ViewType;
import robobinding.binding.viewattribute.ItemMappingAttribute;
import robobinding.presentationmodel.PresentationModelAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith (RobolectricTestRunner.class)
public class RowBinderTest
{
	private final static int VIEW_ID = R.layout.sample_item_layout;

	@Test
	public void givenAnItemMappingAttribute_ThenBindToItWhenInflatingView()
	{
		Context context = new Activity();
		View view = new View(context);
		RowBinder rowBinder = new RowBinder(context);
		LayoutInflater mockLayoutInflater = mock(LayoutInflater.class);
		when(mockLayoutInflater.inflate(VIEW_ID, null)).thenReturn(view);
		rowBinder.setLayoutInflater(mockLayoutInflater);
		
		rowBinder.setItemLayoutId(VIEW_ID);
		ItemMappingAttribute itemMappingAttribute = mock(ItemMappingAttribute.class);//new ItemMappingAttribute("[viewId.enabled:{property}]", false);
		rowBinder.setItemMappingAttribute(itemMappingAttribute);
		
		Object presentationModel = new Object();
		rowBinder.inflateAndBindTo(ViewType.ITEM_LAYOUT, presentationModel);
		
		verify(itemMappingAttribute).lateBindTo(null, eq(view), any(PresentationModelAdapter.class), eq(context));
	}
}
