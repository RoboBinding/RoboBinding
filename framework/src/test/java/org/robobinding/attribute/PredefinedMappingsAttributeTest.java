package org.robobinding.attribute;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.robobinding.widget.adapterview.AbstractAdaptedDataSetAttributes.ITEM_MAPPING;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.attribute.PredefinedMappingsAttribute.ViewMapping;

import android.content.Context;
import android.content.res.Resources;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(Theories.class)
public class PredefinedMappingsAttributeTest {
	private static final String MAPPING_ATTRIBUTE_VALUE = "[text1.text:{property}]";
	private static final int TEXT_1_ID = 10, TEXT_2_ID = 20;

	@DataPoints
	public static MappingExpectation[] mappingExpections = {
			attribute("[text1.text:{title}]").shouldMapTo(viewMapping("text1", TEXT_1_ID, "text", "{title}")),
			attribute("[text2.text:{artist}]").shouldMapTo(viewMapping("text2", TEXT_2_ID, "text", "{artist}")),

			attribute("[text1.text:{title}, text2.text:{artist}]").shouldMapTo(viewMapping("text1", TEXT_1_ID, "text", "{title}"),
					viewMapping("text2", TEXT_2_ID, "text", "{artist}")),

			attribute("[text1.visibility:{titleVisible},text2.enabled:{artistEnabled}]").shouldMapTo(
					viewMapping("text1", TEXT_1_ID, "visibility", "{titleVisible}"), viewMapping("text2", TEXT_2_ID, "enabled", "{artistEnabled}")) };

	@DataPoints
	public static String[] illegalAttributeValues = { "[text1.text: {title}", "text:{title}", "[text1.text:title]",
			"[text1.text:{title}],text2.text:{artist}]", "[text1.text:{title},text2..text:{artist}]" };

	@Mock
	Context context;
	@Mock
	Resources resources;

	@Before
	public void setUp() {
		initMocks(this);
		when(context.getResources()).thenReturn(resources);
	}

	@Theory
	@Test(expected = MalformedAttributeException.class)
	public void whenParsingAnIllegalAttributeValue_thenReject(String illegalAttributeValue) {
		new PredefinedMappingsAttribute(ITEM_MAPPING, illegalAttributeValue);
	}

	@Theory
	public void shouldParseLegalAttributeValuesCorrectly(MappingExpectation mappingExpectation) {
		PredefinedMappingsAttribute predefinedMappingsAttribute = new PredefinedMappingsAttribute(ITEM_MAPPING, mappingExpectation.attributeValue);
		for (ViewMappingData viewMappingData : mappingExpectation.viewMappingData) {
			when(resources.getIdentifier(viewMappingData.viewName, "id", "android")).thenReturn(viewMappingData.viewId);
		}

		Collection<PredefinedPendingAttributesForView> viewMappings = predefinedMappingsAttribute.getViewMappings(context);

		assertTrue(viewMappings.containsAll(mappingExpectation.expectedViewMappings));
	}

	@Test(expected = MalformedAttributeException.class)
	public void givenALegalAttributeValue_whenViewCantBeFound_thenThrowException() {
		PredefinedMappingsAttribute predefinedMappingsAttribute = new PredefinedMappingsAttribute(ITEM_MAPPING, MAPPING_ATTRIBUTE_VALUE);
		when(resources.getIdentifier("text1", "id", "android")).thenReturn(0);

		predefinedMappingsAttribute.getViewMappings(context);
	}

	private static Attribute attribute(String attributeValue) {
		return new Attribute(attributeValue);
	}

	private static ViewMappingData viewMapping(String viewName, int viewId, String attributeName, String attributeValue) {
		return new ViewMappingData(viewName, viewId, attributeName, attributeValue);
	}

	private static class MappingExpectation {
		final String attributeValue;
		final Collection<PredefinedPendingAttributesForView> expectedViewMappings;
		final ViewMappingData[] viewMappingData;

		public MappingExpectation(String attributeValue, ViewMappingData[] viewMappings) {
			this.attributeValue = attributeValue;
			viewMappingData = viewMappings;
			this.expectedViewMappings = Lists.newArrayList();

			for (ViewMappingData viewMappingData : viewMappings) {
				expectedViewMappings.add(new ViewMapping(viewMappingData.viewId, viewMappingData.attributeName, viewMappingData.attributeValue));
			}
		}
	}

	private static class Attribute {
		private final String attributeValue;

		public Attribute(String attributeValue) {
			this.attributeValue = attributeValue;
		}

		public MappingExpectation shouldMapTo(ViewMappingData... viewMappings) {
			return new MappingExpectation(attributeValue, viewMappings);
		}
	}

	private static class ViewMappingData {
		private final String viewName;
		private final int viewId;
		private final String attributeName;
		private final String attributeValue;

		public ViewMappingData(String viewName, int viewId, String attributeName, String attributeValue) {
			this.viewName = viewName;
			this.viewId = viewId;
			this.attributeName = attributeName;
			this.attributeValue = attributeValue;
		}

	}
}
