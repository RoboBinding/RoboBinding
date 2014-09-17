package org.robobinding;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.robobinding.PendingAttributesForView.AttributeGroupResolver;
import org.robobinding.PendingAttributesForView.AttributeResolver;
import org.robobinding.attribute.MalformedAttributeException;

import android.view.View;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PendingAttributesForViewImplTest {
	private AttributeResolverImpl attributeResolver;

	@Before
	public void setUp() {
		attributeResolver = new AttributeResolverImpl();
	}

	@Test
	public void givenAttributeInPendingList_whenResolveAttributeIfExists_thenAttributeShouldBeResolved() {
		String attribute = "existingAttribute";
		PendingAttributesForView pendingAttributesForView = createWithPendingList(attribute);

		pendingAttributesForView.resolveAttributeIfExists(attribute, attributeResolver);

		assertTrue(attributeResolver.isAttributeResolved(attribute));
	}

	@Test
	public void givenAttributeNotInPendingList_whenResolveAttributeIfExists_thenAttributeShouldBeIgnored() {
		String attribute = "nonExistingAttribute";
		PendingAttributesForView pendingAttributesForView = createWithPendingList();

		pendingAttributesForView.resolveAttributeIfExists(attribute, attributeResolver);

		assertFalse(attributeResolver.isAttributeResolved(attribute));
	}

	@Test
	public void whenResolveAttributeSuccessfully_thenAttributeShouldBeRemovedOffPendingList() {
		String attribute = "existingAttribute";
		PendingAttributesForView pendingAttributesForView = createWithPendingList(attribute);

		pendingAttributesForView.resolveAttributeIfExists(attribute, mock(AttributeResolver.class));

		assertTrue(pendingAttributesForView.isEmpty());
	}

	@Test
	public void whenResolveAttributeFailed_thenAttributeShouldBeRemovedOffPendingList() {
		String attribute = "existingAttribute";
		PendingAttributesForView pendingAttributesForView = createWithPendingList(attribute);
		AttributeResolver attributeResolver = mock(AttributeResolver.class);
		doThrow(new MalformedAttributeException(attribute, "error message")).when(attributeResolver).resolve(any(View.class), eq(attribute), anyString());

		pendingAttributesForView.resolveAttributeIfExists(attribute, attributeResolver);

		assertTrue(pendingAttributesForView.isEmpty());
	}

	@Test
	public void whenAttributeGroupResolvedSuccessfully_thenPresentAttributesShouldBeRemovedOffThePendingList() {
		String[] presentAttributes = { "group_attribute1", "group_attribute2" };
		String[] attributeGroup = ArrayUtils.add(presentAttributes, "group_attribute3");
		PendingAttributesForView pendingAttributesForView = createWithPendingList(presentAttributes);

		pendingAttributesForView.resolveAttributeGroupIfExists(attributeGroup, mock(AttributeGroupResolver.class));

		assertTrue(pendingAttributesForView.isEmpty());
	}

	private PendingAttributesForView createWithPendingList(String... pendingAttributes) {
		Map<String, String> pendingAttributeMappings = Maps.newHashMap();
		for (String attribute : pendingAttributes) {
			pendingAttributeMappings.put(attribute, "attributeValue");
		}

		return new PendingAttributesForViewImpl(mock(View.class), pendingAttributeMappings);
	}

	private class AttributeResolverImpl implements AttributeResolver {
		private boolean resolved;
		private String resolvedAttribute;

		@Override
		public void resolve(Object view, String attribute, String attributeValue) {
			resolved = true;
			resolvedAttribute = attribute;
		}

		public boolean isAttributeResolved(String attribute) {
			if (resolved) {
				return StringUtils.equals(resolvedAttribute, attribute);
			} else {
				return false;
			}
		}
	}

}
