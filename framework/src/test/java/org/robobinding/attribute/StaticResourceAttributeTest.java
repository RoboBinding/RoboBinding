package org.robobinding.attribute;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robobinding.attribute.Attributes.aStaticResourceAttribute;
import static org.robobinding.attribute.MockResourcesBuilder.aContextOfResources;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.res.Resources;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class StaticResourceAttributeTest {
	private static final String RESOURCE_NAME = "resourceName";
	private static final String RESOURCE_TYPE = "resourceType";
	private static final String RESOURCE_PACKAGE = "resourcePackage";

	@DataPoints
	public static LegalStaticResourceAttributeValue[] legalAttributeValues = {
			new LegalStaticResourceAttributeValue("@layout/layoutX", "layoutX", "layout", null),
			new LegalStaticResourceAttributeValue("@android:layout/layoutY", "layoutY", "layout", "android"),
			new LegalStaticResourceAttributeValue("@com.somecompany.widget:layout/layoutY", "layoutY", "layout", "com.somecompany.widget") };

	@Theory
	public void whenCreatingWithLegalAttributeValue_thenAttributePointsToSameResource(LegalStaticResourceAttributeValue legalAttributeValue) {
		StaticResourceAttribute attribute = aStaticResourceAttribute(legalAttributeValue.value);

		legalAttributeValue.assertPointToSameResource(attribute);
	}

	@Test
	public void givenResourceNameTypeAndPackage_thenGetResourceIdFromContextResources() {
		MockResourcesBuilder aContextOfResources = aContextOfResources();
		int expectedResourceId = aContextOfResources.declareResource(RESOURCE_NAME, RESOURCE_TYPE, RESOURCE_PACKAGE);

		StaticResourceAttribute attribute = aStaticResourceAttribute(resourceAttributeValue(RESOURCE_NAME, RESOURCE_TYPE, RESOURCE_PACKAGE));

		assertThat(attribute.getResourceId(aContextOfResources.build()), equalTo(expectedResourceId));
	}

	@Test
	public void givenOnlyResourceNameAndType_thenUseContextPackageToGetResourceId() {
		MockResourcesBuilder aContextOfResources = aContextOfResources();
		int expectedResourceId = aContextOfResources.withDefaultPackage(RESOURCE_PACKAGE).declareResource(RESOURCE_NAME, RESOURCE_TYPE, RESOURCE_PACKAGE);

		StaticResourceAttribute attribute = aStaticResourceAttribute(resourceAttributeValue(RESOURCE_NAME, RESOURCE_TYPE));

		assertThat(attribute.getResourceId(aContextOfResources.build()), equalTo(expectedResourceId));
	}

	@Test(expected = RuntimeException.class)
	public void givenAResourceThatDoesNotExist_thenThrowRuntimeExceptionWhenGettingResourceId() {
		Resources resources = mock(Resources.class);
		when(resources.getIdentifier(anyString(), anyString(), anyString())).thenReturn(0);
		Context context = mock(Context.class);
		when(context.getResources()).thenReturn(resources);

		StaticResourceAttribute attribute = aStaticResourceAttribute("@layout/non_existent_resource");

		attribute.getResourceId(context);
	}

	private String resourceAttributeValue(String resourceName, String resourceType) {
		return "@" + resourceType + "/" + resourceName;
	}

	private String resourceAttributeValue(String resourceName, String resourceType, String resourcePackage) {
		return "@" + resourcePackage + ":" + resourceType + "/" + resourceName;
	}

	static class LegalStaticResourceAttributeValue {
		final String value;
		private final String expectedName;
		private final String expectedType;
		private final String expectedPackage;

		public LegalStaticResourceAttributeValue(String value, String expectedName, String expectedType, String expectedPackage) {
			this.value = value;
			this.expectedName = expectedName;
			this.expectedType = expectedType;
			this.expectedPackage = expectedPackage;
		}

		public void assertPointToSameResource(StaticResourceAttribute attribute) {
			MockResourcesBuilder aContextOfResources = aContextOfResources();
			int expectedResourceId = aContextOfResources.declareResource(expectedName, expectedType, expectedPackage);
			assertThat(attribute.getResourceId(aContextOfResources.build()), equalTo(expectedResourceId));
		}

	}

}
