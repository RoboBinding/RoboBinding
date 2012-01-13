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
import org.robobinding.viewattribute.adapterview.AbstractSubViewAttributes;

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
	private static final String PRESENTATION_MODEL_PROPERTY = "presentationModel";
	private static final String VISIBILITY_PROPERTY = "visibility";
	
	private GroupedAttributeDetailsImpl groupedAttributeDetails;
	
	@Before
	public void setUp()
	{
		groupedAttributeDetails = createGroupedAttributeDetails();
	}
	
	@Test(expected=RuntimeException.class)
	public void createAttributeWithoutLayout_thenThrowException()
	{
		createAttribute();
	}
	
	@Test
	public void createAttributeWithoutPresentationModel_thenSuccessful()
	{
		addLayoutAsPresentAttribute();
		createAttribute();
	}

	@Test
	public void createAttributeWithPresentationModel_thenSuccessful()
	{
		addLayoutAsPresentAttribute();
		addPresentationModelAsPresentAttribute();
		createAttribute();
	}

	@Test
	public void createAttributeWithVisibility_thenSuccessful()
	{
		addLayoutAsPresentAttribute();
		addPresentationModelAsPresentAttribute();
		addVisibilityAsPresentAttribute();
		createAttribute();
	}

	private void addLayoutAsPresentAttribute()
	{
		groupedAttributeDetails.addPresentAttribute(HeaderOrFooterAttributes.LAYOUT, RandomValues.anyLayoutResource());
	}
	
	private void addPresentationModelAsPresentAttribute()
	{
		groupedAttributeDetails.addPresentAttribute(HeaderOrFooterAttributes.PRESENTATION_MODEL, BindingAttributeValueUtils.oneWay(PRESENTATION_MODEL_PROPERTY));
	}

	private void addVisibilityAsPresentAttribute()
	{
		groupedAttributeDetails.addPresentAttribute(HeaderOrFooterAttributes.VISIBILITY, BindingAttributeValueUtils.oneWay(VISIBILITY_PROPERTY));
	}
	
	@SuppressWarnings("unchecked")
	private HeaderOrFooterAttributes createAttribute()
	{
		PresentationModelAdapter mockPresentationModelAdapter = mock(PresentationModelAdapter.class);
		when(mockPresentationModelAdapter.getReadOnlyPropertyValueModel(PRESENTATION_MODEL_PROPERTY)).thenReturn(ValueModelUtils.create(new Object()));
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
		return new GroupedAttributeDetailsImpl(new String[]{HeaderOrFooterAttributes.LAYOUT, 
				HeaderOrFooterAttributes.PRESENTATION_MODEL, HeaderOrFooterAttributes.VISIBILITY});
	}
	
	private static class HeaderOrFooterAttributes extends AbstractSubViewAttributes
	{
		static final String LAYOUT = "layout";
		static final String PRESENTATION_MODEL = "presentationModel";
		static final String VISIBILITY = "visibility";
		
		@Override
		protected String layoutAttribute()
		{
			return LAYOUT;
		}

		@Override
		protected String subViewPresentationModelAttribute()
		{
			return PRESENTATION_MODEL;
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
