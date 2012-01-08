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
package org.robobinding.binder;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.R;
import org.robobinding.binder.BindingViewFactory;
import org.robobinding.binder.RowBinder;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.adapterview.DropdownMappingAttribute;
import org.robobinding.viewattribute.adapterview.ItemMappingAttribute;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

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
	private Object presentationModel = new Object();
	private Context context = new Activity();
	
	private View view;
	private RowBinder rowBinder;
	
	@Before
	public void setUp()
	{
		view = new View(context);
		rowBinder = mockRowBinderInflatingSimpleView(view);
	}
	
	@Test
	public void givenAnItemMappingAttribute_thenBindToItWhenInflatingView()
	{
		ItemMappingAttribute itemMappingAttribute = mock(ItemMappingAttribute.class);
		rowBinder.setItemMappingAttribute(itemMappingAttribute);
		rowBinder.setItemLayoutId(VIEW_ID);
		
		rowBinder.inflateItemAndBindTo(presentationModel);
		
		verify(itemMappingAttribute).bindToPredefined(eq(rowBinder.bindingAttributeProcessor), eq(view), any(PresentationModelAdapter.class), eq(context));
	}
	
	@Test
	public void givenADropdownMappingAttribute_thenBindToItWhenInflatingView()
	{
		DropdownMappingAttribute dropdownMappingAttribute = mock(DropdownMappingAttribute.class);
		rowBinder.setDropdownMappingAttribute(dropdownMappingAttribute);
		rowBinder.setDropdownLayoutId(VIEW_ID);
		
		rowBinder.inflateDropdownAndBindTo(presentationModel);
		
		verify(dropdownMappingAttribute).bindToPredefined(eq(rowBinder.bindingAttributeProcessor), eq(view), any(PresentationModelAdapter.class), eq(context));
	}
	
	private RowBinder mockRowBinderInflatingSimpleView(View view)
	{
		RowBinder rowBinder = new RowBinder(context);
		LayoutInflater mockLayoutInflater = mock(LayoutInflater.class);
		when(mockLayoutInflater.inflate(VIEW_ID, null)).thenReturn(view);
		when(mockLayoutInflater.inflate(VIEW_ID, null, false)).thenReturn(view);
		BindingViewFactory bindingViewFactory = new BindingViewFactory(mockLayoutInflater, rowBinder.bindingAttributeProcessor);
		rowBinder.setBindingViewFactory(bindingViewFactory);
		return rowBinder;
	}
}
