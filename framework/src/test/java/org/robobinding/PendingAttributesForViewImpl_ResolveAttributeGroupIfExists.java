package org.robobinding;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.PendingAttributesForView.AttributeGroupResolver;

import android.view.View;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class PendingAttributesForViewImpl_ResolveAttributeGroupIfExists {
	@DataPoints
	public static AttributeGroupAndPresentAttributes[] samples = {
			attributeGroup("group1_attribute1", "group1_attribute2").andPresentAttributes("group1_attribute1"),
			attributeGroup("group2_attribute1", "group2_attribute2", "group2_attribute3").andPresentAttributes("group2_attribute1", "group2_attribute3"),
			attributeGroup("group3_attribute1") };

	private PendingAttributesForView pendingAttributesForView;
	private AttributeGroupResolverImpl attributeGroupResolver;

	@Before
	public void setUp() {
		Map<String, String> presentAttributeMappings = createPresentAttributeMappings();
		pendingAttributesForView = new PendingAttributesForViewImpl(mock(View.class), presentAttributeMappings);

		attributeGroupResolver = new AttributeGroupResolverImpl();
	}

	private Map<String, String> createPresentAttributeMappings() {
		Map<String, String> presentAttributeMappings = Maps.newHashMap();
		for (AttributeGroupAndPresentAttributes attributeGroupAndPresentAttributes : samples) {
			for (String presentAttribute : attributeGroupAndPresentAttributes.presentAttributes) {
				presentAttributeMappings.put(presentAttribute, "attributeValue");
			}
		}
		return presentAttributeMappings;
	}

	@Theory
	public void whenResolveAttributeGroupIfExists_thenResolutionExpectationShouldMeet(AttributeGroupAndPresentAttributes attributeGroupAndPresentAttributes) {
		resolveAttributeGroupIfExists(attributeGroupAndPresentAttributes);

		resolutionExpectation(attributeGroupAndPresentAttributes).assertMeet();
	}

	private void resolveAttributeGroupIfExists(AttributeGroupAndPresentAttributes attributeGroupAndPresentAttributes) {
		pendingAttributesForView.resolveAttributeGroupIfExists(attributeGroupAndPresentAttributes.attributeGroup, attributeGroupResolver);
	}

	private ResolutionExpectation resolutionExpectation(AttributeGroupAndPresentAttributes attributeGroupAndPresentAttributes) {
		return new ResolutionExpectation(attributeGroupAndPresentAttributes);
	}

	private static AttributeGroupAndPresentAttributes attributeGroup(String... attributeGroup) {
		return new AttributeGroupAndPresentAttributes(attributeGroup);
	}

	private static class AttributeGroupAndPresentAttributes {
		private String[] attributeGroup;
		private String[] presentAttributes;

		public AttributeGroupAndPresentAttributes(String... attributeGroup) {
			this.attributeGroup = attributeGroup;
			this.presentAttributes = new String[0];
		}

		public AttributeGroupAndPresentAttributes andPresentAttributes(String... presentAttributes) {
			assertTrue(inAttributeGroup(presentAttributes));
			this.presentAttributes = presentAttributes;
			return this;
		}

		private boolean inAttributeGroup(String[] attributes) {
			return Lists.newArrayList(attributeGroup).containsAll(Lists.newArrayList(attributes));
		}
	}

	private class AttributeGroupResolverImpl implements AttributeGroupResolver {
		private boolean resolveInvoked;
		private String[] attributeGroup;
		private String[] presentAttributes;

		@Override
		public void resolve(Object view, String[] attributeGroup, Map<String, String> presentAttributeMappings) {
			resolveInvoked = true;
			this.attributeGroup = attributeGroup;
			this.presentAttributes = presentAttributeMappings.keySet().toArray(new String[0]);
		}
	}

	private class ResolutionExpectation {
		private AttributeGroupAndPresentAttributes attributeGroupAndPresentAttributes;

		public ResolutionExpectation(AttributeGroupAndPresentAttributes attributeGroupAndPresentAttributes) {
			this.attributeGroupAndPresentAttributes = attributeGroupAndPresentAttributes;
		}

		public void assertMeet() {
			if (shouldResolveExpected()) {
				assertResolveInvoked();
				assertCorrectParameters();
			} else {
				assertNoResolveInvoked();
			}
		}

		private boolean shouldResolveExpected() {
			return !ArrayUtils.isEmpty(attributeGroupAndPresentAttributes.presentAttributes);
		}

		private void assertResolveInvoked() {
			assertTrue(attributeGroupResolver.resolveInvoked);
		}

		private void assertCorrectParameters() {
			assertThat(attributeGroupResolver.attributeGroup, equalTo(attributeGroupAndPresentAttributes.attributeGroup));
			assertThat(Sets.newHashSet(attributeGroupResolver.presentAttributes),
					equalTo(Sets.newHashSet(attributeGroupAndPresentAttributes.presentAttributes)));
		}

		private void assertNoResolveInvoked() {
			assertFalse(attributeGroupResolver.resolveInvoked);
		}
	}

}
