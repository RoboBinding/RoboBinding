package org.robobinding.binder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.robobinding.binder.MockInitializedBindingAttributeMappingsBuilder.aBindingAttributeMappings;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.PendingAttributesForView;
import org.robobinding.ViewResolutionErrors;
import org.robobinding.viewattribute.ViewAttributeBinder;
import org.robobinding.viewattribute.event.EventViewAttributeBinder;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeBinder;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeFactory;
import org.robobinding.viewattribute.grouped.ViewAttributeBinderFactory;
import org.robobinding.viewattribute.impl.InitailizedBindingAttributeMappings;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ByBindingAttributeMappingsResolverTest {
	@Mock
	private ViewAttributeBinderFactory<Object> viewAttributeBinderFactory;
	private PendingAttributesForView pendingAttributesForView;

	@Before
	public void setUp() {
		pendingAttributesForView = new MockPendingAttributesForView();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenAPropertyAttribute_whenResolve_thenAResolvedPropertyViewAttributeShouldBeReturned() {
		String propertyAttribute = "propertyAttribute";
		PropertyViewAttributeBinder<Object, ?> viewAttributeBinder = Mockito.mock(PropertyViewAttributeBinder.class);
		Mockito.<PropertyViewAttributeBinder<Object, ?>> when(
				viewAttributeBinderFactory.createPropertyViewAttributeBinder(any(PropertyViewAttributeFactory.class), eq(propertyAttribute), anyString()))
				.thenReturn(viewAttributeBinder);

		ByBindingAttributeMappingsResolver byBindingAttributeMappingsResolver = newByBindingAttributeMappingsResolver(aBindingAttributeMappings()
				.withPropertyAttribute(propertyAttribute).build());

		Collection<ViewAttributeBinder> viewAttributes = byBindingAttributeMappingsResolver.resolve(pendingAttributesForView);

		assertThat(Sets.newHashSet(viewAttributes), equalTo(Sets.<ViewAttributeBinder> newHashSet(viewAttributeBinder)));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenAMultiTypePropertyAttribute_whenResolve_thenAResolvedMultiTypePropertyViewAttributeShouldBeReturned() {
		String propertyAttribute = "multiTypePropertyAttribute";
		MultiTypePropertyViewAttributeBinder<Object> viewAttributeBinder = Mockito.mock(MultiTypePropertyViewAttributeBinder.class);
		Mockito.<MultiTypePropertyViewAttributeBinder<Object>> when(
				viewAttributeBinderFactory.createMultiTypePropertyViewAttributeBinder(any(MultiTypePropertyViewAttributeFactory.class), eq(propertyAttribute),
						anyString())).thenReturn(viewAttributeBinder);

		ByBindingAttributeMappingsResolver byBindingAttributeMappingsResolver = newByBindingAttributeMappingsResolver(aBindingAttributeMappings()
				.withMultiTypePropertyAttribute(propertyAttribute).build());

		Collection<ViewAttributeBinder> viewAttributes = byBindingAttributeMappingsResolver.resolve(pendingAttributesForView);

		assertThat(Sets.newHashSet(viewAttributes), equalTo(Sets.<ViewAttributeBinder> newHashSet(viewAttributeBinder)));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenAnEventAttribute_whenResolve_thenAResolvedEventViewAttributeShouldBeReturned() {
		String eventAttribute = "eventAttribute";
		EventViewAttributeBinder<Object> viewAttributeBinder = Mockito.mock(EventViewAttributeBinder.class);
		Mockito.<EventViewAttributeBinder<Object>> when(
				viewAttributeBinderFactory.createEventViewAttributeBinder(any(EventViewAttributeFactory.class), eq(eventAttribute), anyString())).thenReturn(
				viewAttributeBinder);
		ByBindingAttributeMappingsResolver byBindingAttributeMappingsResolver = newByBindingAttributeMappingsResolver(aBindingAttributeMappings()
				.withEventAttribute(eventAttribute).build());

		Collection<ViewAttributeBinder> viewAttributes = byBindingAttributeMappingsResolver.resolve(pendingAttributesForView);

		assertThat(Sets.newHashSet(viewAttributes), equalTo(Sets.<ViewAttributeBinder> newHashSet(viewAttributeBinder)));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenAnAttributeGroup_whenResolve_thenAResolvedGroupedViewAttributeShouldBeReturned() {
		String[] attributeGroup = { "group_attribute1", "group_attribute2" };
		GroupedViewAttributeBinder<Object> viewAttributeBinder = Mockito.mock(GroupedViewAttributeBinder.class);
		Mockito.<GroupedViewAttributeBinder<Object>> when(
				viewAttributeBinderFactory.createGroupedViewAttributeBinder(any(GroupedViewAttributeFactory.class), eq(attributeGroup), anyMap())).thenReturn(
				viewAttributeBinder);
		ByBindingAttributeMappingsResolver byBindingAttributeMappingsResolver = newByBindingAttributeMappingsResolver(aBindingAttributeMappings()
				.withAttributeGroup(attributeGroup).build());

		Collection<ViewAttributeBinder> viewAttributes = byBindingAttributeMappingsResolver.resolve(pendingAttributesForView);

		assertThat(Sets.newHashSet(viewAttributes), equalTo(Sets.<ViewAttributeBinder> newHashSet(viewAttributeBinder)));
	}

	private ByBindingAttributeMappingsResolver newByBindingAttributeMappingsResolver(InitailizedBindingAttributeMappings<Object> bindingAttributeMappings) {
		return new ByBindingAttributeMappingsResolver(bindingAttributeMappings, viewAttributeBinderFactory);
	}

	private static class MockPendingAttributesForView implements PendingAttributesForView {
		@Override
		public void resolveAttributeGroupIfExists(String[] attributeGroup, AttributeGroupResolver attributeGroupResolver) {
			attributeGroupResolver.resolve(null, attributeGroup, null);
		}

		@Override
		public void resolveAttributeIfExists(String attribute, AttributeResolver attributeResolver) {
			attributeResolver.resolve(null, attribute, null);
		}

		@Override
		public Object getView() {
			throw new UnsupportedOperationException();
		}

		@Override
		public ViewResolutionErrors getResolutionErrors() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isEmpty() {
			throw new UnsupportedOperationException();
		}
	}

}
