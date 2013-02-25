/**
 * Copyright 2013 Cheng Wei, Robert Taylor
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
package org.robobinding.viewattribute.listview;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class HeaderAttributesTest
{
	private HeaderAttributes headerAttributes = new HeaderAttributes();
	private Context context = new Activity();
	private ListView listView = new ListView(context);
	private View subView = new View(context);
	
	@Test
	public void testLayoutAttribute()
	{
		assertThat(headerAttributes.layoutAttribute(), is("headerLayout"));
	}
	
	@Test
	public void testSubViewPresentationModelAttribute()
	{
		assertThat(headerAttributes.subViewPresentationModelAttribute(), is("headerPresentationModel"));
	}
	
	@Test
	public void testVisibilityAttribute()
	{
		assertThat(headerAttributes.visibilityAttribute(), is("headerVisibility"));
	}
	
	@Test
	public void shouldNestSubViewWithinLinearLayoutContainerAndAddAsHeader()
	{
		headerAttributes.addSubView(listView, subView, context);
		
		List<View> headerViews = Robolectric.shadowOf(listView).getHeaderViews();
		assertThat(headerViews.size(), is(1));
		LinearLayout linearLayout = (LinearLayout)headerViews.get(0);
		assertThat(linearLayout.getChildAt(0), is(subView));
	}
	
	@Test
	public void shouldCreateNewHeaderVisibility()
	{
		assertNotNull(headerAttributes.createVisibility(listView, subView));
	}
}
