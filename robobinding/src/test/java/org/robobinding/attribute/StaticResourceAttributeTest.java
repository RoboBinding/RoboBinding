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
package org.robobinding.attribute;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.robobinding.attribute.Attributes.aStaticResourceAttribute;
import static org.robobinding.attribute.MockResourcesBuilder.aContextOfResources;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class StaticResourceAttributeTest
{
	private static final String RESOURCE_NAME = "resourceName";
	private static final String RESOURCE_TYPE = "resourceType";
	private static final String RESOURCE_PACKAGE = "resourcePackage";
	
	@DataPoints
	public static LegalStaticResourceAttributeValue[] legalAttributeValues = {
		new LegalStaticResourceAttributeValue("@layout/layoutX", "layoutX", "layout", null),
		new LegalStaticResourceAttributeValue("@android:layout/layoutY", "layoutY", "layout", "android"),
		new LegalStaticResourceAttributeValue("@com.somecompany.widget:layout/layoutY", "layoutY", "layout", "com.somecompany.widget")
	};
	
	@Theory
	public void whenCreateWithLegalAttributeValue_thenAttributePointsToSameResource(LegalStaticResourceAttributeValue legalAttributeValue)
	{
		StaticResourceAttribute attribute = aStaticResourceAttribute(legalAttributeValue.value);

		legalAttributeValue.assertPointToSameResource(attribute);
	}
	
	@Test
	public void givenResourceNameTypeAndPackage_thenGetResourceIdFromContextResources()
	{
		MockResourcesBuilder aContextOfResources = aContextOfResources();
		int expectedResourceId = aContextOfResources.declareResource(RESOURCE_NAME, RESOURCE_TYPE, RESOURCE_PACKAGE);

		StaticResourceAttribute attribute = aStaticResourceAttribute(resourceAttributeValue(RESOURCE_NAME, RESOURCE_TYPE, RESOURCE_PACKAGE));
		
		assertThat(attribute.getResourceId(aContextOfResources.build()), equalTo(expectedResourceId));
	}

	@Test
	public void givenOnlyResourceNameAndType_thenUseContextPackageToGetResourceId()
	{
		MockResourcesBuilder aContextOfResources = aContextOfResources();
		int expectedResourceId = aContextOfResources.withDefaultPackage(RESOURCE_PACKAGE).declareResource(RESOURCE_NAME, RESOURCE_TYPE, RESOURCE_PACKAGE);
		
		StaticResourceAttribute attribute = aStaticResourceAttribute(resourceAttributeValue(RESOURCE_NAME, RESOURCE_TYPE));
		
		assertThat(attribute.getResourceId(aContextOfResources.build()), equalTo(expectedResourceId));
	}
	
	private String resourceAttributeValue(String resourceName, String resourceType)
	{
		return "@"+resourceType+"/"+resourceName;
	}
	
	private String resourceAttributeValue(String resourceName, String resourceType, String resourcePackage)
	{
		return "@"+resourcePackage+":"+resourceType+"/"+resourceName;
	}
	
	static class LegalStaticResourceAttributeValue
	{
		final String value;
		private final String expectedName;
		private final String expectedType;
		private final String expectedPackage;

		public LegalStaticResourceAttributeValue(String value, String expectedName, String expectedType, String expectedPackage)
		{
			this.value = value;
			this.expectedName = expectedName;
			this.expectedType = expectedType;
			this.expectedPackage = expectedPackage;
		}
		
		public void assertPointToSameResource(StaticResourceAttribute attribute)
		{
			MockResourcesBuilder aContextOfResources = aContextOfResources();
			int expectedResourceId = aContextOfResources.declareResource(expectedName, expectedType, expectedPackage);
			assertThat(attribute.getResourceId(aContextOfResources.build()), equalTo(expectedResourceId));
		}

	}

}
