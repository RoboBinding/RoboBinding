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
package org.robobinding.viewattribute.adapterview;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class EmptyViewAttributesTest
{
	private EmptyViewAttributes emptyViewAttributes = new EmptyViewAttributes();
	private Context context = new Activity();
	private AdapterView<?> adapterView = new ListView(context);
	private View emptyView = new View(context);
	
	@Test
	public void testLayoutAttribute()
	{
		assertThat(emptyViewAttributes.layoutAttribute(), is("emptyViewLayout"));
	}
	
	@Test
	public void testSubViewPresentationModelAttribute()
	{
		assertThat(emptyViewAttributes.subViewPresentationModelAttribute(), is("emptyViewPresentationModel"));
	}
	
	@Test
	public void testVisibilityAttribute()
	{
		assertThat(emptyViewAttributes.visibilityAttribute(), is("emptyViewVisibility"));
	}
	
	@Test
	public void givenAddSubViewHasBeenCalled_thenReturnEmptyViewVisibility()
	{
		ViewGroup parentViewGroup = new LinearLayout(context);
		parentViewGroup.addView(adapterView);
		
		emptyViewAttributes.addSubView(adapterView, emptyView, context);
		
		assertNotNull(emptyViewAttributes.createVisibility(adapterView, emptyView));
	}
	
	@Test(expected = IllegalStateException.class)
	public void givenSubViewHasNotBeenAdded_thenThrowIllegalStateException()
	{
		emptyViewAttributes.createVisibility(adapterView, emptyView);
	}
}
