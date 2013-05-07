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
package org.robobinding.viewattribute.adapterview;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
//@RunWith(RobolectricTestRunner.class)
public class SubViewAttributesTest
{
//	//private GroupedAttributeDetails mockGroupedAttributeDetails;
//	private SubViewCreator mockSubViewCreator;
//	
//	@Before
//	public void setUp()
//	{
//		//mockGroupedAttributeDetails = mock(GroupedAttributeDetails.class);
//		mockSubViewCreator = mock(SubViewCreator.class);
//	}
//	
//	@Test
//	public void createAttributeWithoutPresentationModel_thenSuccessful()
//	{
//		when(mockSubViewCreator.create()).thenReturn(new View(null));
//		
//		assertNotNull(createSubView());
//	}
//
//	@Test
//	public void createAttributeWithPresentationModel_thenSuccessful()
//	{
//		makePresentationModelAttributeAvailable();
//		when(mockSubViewCreator.createAndBindTo(anyString())).thenReturn(new View(null));
//		
//		assertNotNull(createSubView());
//	}
//	
//	private void makePresentationModelAttributeAvailable()
//	{
//		when(mockGroupedAttributeDetails.hasAttribute(SubViewAttributes.PRESENTATION_MODEL)).thenReturn(true);
//	}
//	
//	private View createSubView()
//	{
//		SubViewAttributes subViewAttributes = new SubViewAttributes();
//		return subViewAttributes.createSubView(null);
//	}
//	
//	private class SubViewAttributes extends AbstractSubViewAttributes<AdapterView<?>>
//	{
//		static final String LAYOUT = "layout";
//		static final String PRESENTATION_MODEL = "presentationModel";
//		public SubViewAttributes()
//		{
//			super.groupedAttributeDetails = mockGroupedAttributeDetails;
//		}
//		
//		@Override
//		SubViewCreator createSubViewCreator(BindingContext context, String layoutAttributeValue)
//		{
//			return mockSubViewCreator;
//		}
//		
//		@Override
//		protected String layoutAttribute()
//		{
//			return LAYOUT;
//		}
//
//		@Override
//		protected String subViewPresentationModelAttribute()
//		{
//			return PRESENTATION_MODEL;
//		}
//
//		@Override
//		protected String visibilityAttribute()
//		{
//			throw new RuntimeException();
//		}
//
//		@Override
//		protected void addSubView(View subView, Context context)
//		{
//			throw new RuntimeException();
//		}
//		
//		@Override
//		protected SubViewVisibilityAttribute createVisibilityAttribute(View subView)
//		{
//			throw new RuntimeException();
//		}
//	}
}
