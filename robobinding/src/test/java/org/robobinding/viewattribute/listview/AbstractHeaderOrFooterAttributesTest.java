/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.binder.GroupedAttributeDetailsImpl;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.viewattribute.BindingAttributeValueUtils;
import org.robobinding.viewattribute.RandomValues;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class AbstractHeaderOrFooterAttributesTest
{
	private static final String SOURCE_PROPERTY = "headerSource";
	private static final String VISIBILITY_PROPERTY = "headerVisibility";
	
	private GroupedAttributeDetailsImpl groupedAttributeDetails;
	
	@Before
	public void setUp()
	{
		groupedAttributeDetails = createGroupedAttributeDetails();
	}
	
	@Test(expected=RuntimeException.class)
	public void createAttributeWithoutHeaderLayout_thenThrowException()
	{
		createAttribute();
	}
	
	@Test
	public void createAttributeWithoutHeaderSource_thenSuccessful()
	{
		addHeaderLayoutAsPresentAttribute();
		createAttribute();
	}

	@Test
	public void createAttributeWithHeaderSource_thenSuccessful()
	{
		addHeaderLayoutAsPresentAttribute();
		addHeaderSourceAsPresentAttribute();
		createAttribute();
	}

	@Test
	public void createAttributeWithHeaderVisibility_thenSuccessful()
	{
		addHeaderLayoutAsPresentAttribute();
		addHeaderSourceAsPresentAttribute();
		addHeaderVisibilityAsPresentAttribute();
		createAttribute();
	}

	private void addHeaderLayoutAsPresentAttribute()
	{
		groupedAttributeDetails.addPresentAttribute(HeaderOrFooterAttributes.LAYOUT, RandomValues.anyLayoutResource());
	}
	
	private void addHeaderSourceAsPresentAttribute()
	{
		groupedAttributeDetails.addPresentAttribute(HeaderOrFooterAttributes.SOURCE, BindingAttributeValueUtils.oneWay(SOURCE_PROPERTY));
	}

	private void addHeaderVisibilityAsPresentAttribute()
	{
		groupedAttributeDetails.addPresentAttribute(HeaderOrFooterAttributes.VISIBILITY, BindingAttributeValueUtils.oneWay(VISIBILITY_PROPERTY));
	}
	
	@SuppressWarnings("unchecked")
	private HeaderOrFooterAttributes createAttribute()
	{
		PresentationModelAdapter mockPresentationModelAdapter = mock(PresentationModelAdapter.class);
		when(mockPresentationModelAdapter.getReadOnlyPropertyValueModel(SOURCE_PROPERTY)).thenReturn(ValueModelUtils.create(new Object()));
		when((Class<Integer>)mockPresentationModelAdapter.getPropertyType(VISIBILITY_PROPERTY)).thenReturn(Integer.class);
		when(mockPresentationModelAdapter.getReadOnlyPropertyValueModel(VISIBILITY_PROPERTY)).thenReturn(ValueModelUtils.create());
		
		HeaderOrFooterAttributes headerOrFooterAttributes = new HeaderOrFooterAttributes();
		headerOrFooterAttributes.setView(new ListView(new Activity()));
		headerOrFooterAttributes.setGroupedAttributeDetails(groupedAttributeDetails);
		headerOrFooterAttributes.bind(mockPresentationModelAdapter, new Activity());
		return headerOrFooterAttributes;
	}
	
	private GroupedAttributeDetailsImpl createGroupedAttributeDetails()
	{
		return new GroupedAttributeDetailsImpl(new String[]{HeaderAttributes.HEADER_LAYOUT, 
				HeaderAttributes.HEADER_SOURCE, HeaderAttributes.HEADER_VISIBILITY});
	}
	
	private static class HeaderOrFooterAttributes extends AbstractHeaderOrFooterAttributes
	{
		static final String LAYOUT = "layout";
		static final String SOURCE = "source";
		static final String VISIBILITY = "visibility";
		
		@Override
		protected String layoutAttribute()
		{
			return LAYOUT;
		}

		@Override
		protected String sourceAttribute()
		{
			return SOURCE;
		}

		@Override
		protected String visibilityAttribute()
		{
			return VISIBILITY;
		}

		@Override
		protected void addSubView(View subView)
		{
			//as an always successful operation.
		}
		
	}
}
