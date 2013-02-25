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
public class FooterAttributesTest
{
	private FooterAttributes footerAttributes = new FooterAttributes();
	private Context context = new Activity();
	private ListView listView = new ListView(context);
	private View subView = new View(context);
	
	@Test
	public void testLayoutAttribute()
	{
		assertThat(footerAttributes.layoutAttribute(), is("footerLayout"));
	}
	
	@Test
	public void testSubViewPresentationModelAttribute()
	{
		assertThat(footerAttributes.subViewPresentationModelAttribute(), is("footerPresentationModel"));
	}
	
	@Test
	public void testVisibilityAttribute()
	{
		assertThat(footerAttributes.visibilityAttribute(), is("footerVisibility"));
	}
	
	@Test
	public void shouldAddSubViewToListView()
	{
		footerAttributes.addSubView(listView, subView, context);
		
		List<View> footerViews = Robolectric.shadowOf(listView).getFooterViews();
		assertThat(footerViews.size(), is(1));
		assertThat(footerViews.get(0), is(subView));
	}
	
	@Test
	public void shouldCreateNewFooterVisibility()
	{
		assertNotNull(footerAttributes.createVisibility(listView, subView));
	}
}
