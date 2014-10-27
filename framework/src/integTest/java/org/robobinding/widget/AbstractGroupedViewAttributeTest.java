package org.robobinding.widget;

import static com.google.common.collect.Maps.newHashMap;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.BindingContext;
import org.robobinding.attribute.EnumAttribute;
import org.robobinding.attribute.PendingGroupAttributes;
import org.robobinding.attribute.ResolvedGroupAttributes;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttributeFactory;
import org.robobinding.viewattribute.grouped.ChildViewAttributesBuilder;
import org.robobinding.viewattribute.grouped.GroupAttributesResolver;
import org.robobinding.viewattribute.grouped.GroupedViewAttribute;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;
import org.robolectric.RobolectricTestRunner;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractGroupedViewAttributeTest<ViewType extends View, ViewAttributeType extends GroupedViewAttribute<? super ViewType>> {
	private ViewAttributeType attribute;
	private ViewType view;

	private Map<String, String> presentAttributeMappings;
	private Map<String, Object> childViewAttributeMap;

	@Before
	public void initialize() {
		ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();

		view = ParameterizedTypeUtils.createTypeArgument(superclass, 0);
		attribute = createAttributeUnderTest();

		presentAttributeMappings = newHashMap();
		childViewAttributeMap = newHashMap();

	}

	protected ViewAttributeType createAttributeUnderTest() {
		try {
			ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
			return ParameterizedTypeUtils.createTypeArgument(superclass, 1);
		} catch (Exception e) {
			throw new RuntimeException("Error instantiating grouped attribute class: " + e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked" })
	protected void performInitialization() {
		PendingGroupAttributes pendingGroupAttributes = new PendingGroupAttributes(presentAttributeMappings);
		GroupAttributesResolver resolvedGroupAttributesFactory = new GroupAttributesResolver();
		ResolvedGroupAttributes resolvedGroupAttributes = resolvedGroupAttributesFactory.resolve(pendingGroupAttributes, attribute);
		ChildViewAttributesBuilder<View> childViewAttributesBuilder = new ChildViewAttributesBuilderForTest(childViewAttributeMap, resolvedGroupAttributes);

		BindingContext bindingContext = mock(BindingContext.class);

		((GroupedViewAttribute<View>) attribute).setupChildViewAttributes(view, childViewAttributesBuilder, bindingContext);
	}

	protected Attribute attribute(String attribute) {
		if (attribute.indexOf('=') == -1)
			throw new IllegalArgumentException("Expected attribute in the form '[attributeName]=[attributeValue]'");

		String[] components = attribute.split("=");
		return new Attribute(components[0], components[1]);
	}

	protected void givenAttributes(Attribute... attributes) {
		for (Attribute attribute : attributes) {
			givenAttribute(attribute);
		}
	}

	protected void givenAttribute(Attribute attribute) {
		presentAttributeMappings.put(attribute.name, attribute.value);
	}

	protected void assertThatAttributesWereCreated(Class<?>... attributeClasses) {
		for (Class<?> attributeClass : attributeClasses) {
			assertThatAttributeWasCreated(attributeClass);
		}
	}

	protected void assertThatAttributeWasCreated(Class<?> attributeClass) {
		Object childAttribute = findChildAttributeOfType(attributeClass);
		assertNotNull("Child attribute of type '" + attributeClass.getName() + "' not found", childAttribute);
	}

	private Object findChildAttributeOfType(Class<?> childViewAttributeClass) {
		for (Object childViewAttribute : childViewAttributeMap.values()) {
			if (childViewAttributeClass.isInstance(childViewAttribute)) {
				return childViewAttribute;
			}
		}
		return null;
	}

	protected void assertThatAttributesWereCreated(String... attributeNames) {
		for (String attributeName : attributeNames) {
			assertThatAttributeWasCreated(attributeName);
		}
	}

	protected void assertThatAttributeWasCreated(String attributeName) {
		assertTrue("Child attribute of '" + attributeName + "' not found", hasAttribute(attributeName));
	}

	private boolean hasAttribute(String attributeName) {
		return childViewAttributeMap.containsKey(attributeName);
	}

	protected Object childViewAttribute(String attributeName) {
		return childViewAttributeMap.get(attributeName);
	}

	public static class Attribute {
		private final String name;
		private final String value;

		private Attribute(String name, String value) {
			this.name = name;
			this.value = value;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	private static class ChildViewAttributesBuilderForTest implements ChildViewAttributesBuilder<View> {
		private final Map<String, Object> childViewAttributeMap;
		private final ResolvedGroupAttributes resolvedGroupAttributes;

		public ChildViewAttributesBuilderForTest(Map<String, Object> childViewAttributeMap, ResolvedGroupAttributes resolvedGroupAttributes) {
			this.childViewAttributeMap = childViewAttributeMap;
			this.resolvedGroupAttributes = resolvedGroupAttributes;
		}

		@Override
		public void add(String attributeName, ChildViewAttribute childAttribute) {
			childViewAttributeMap.put(attributeName, childAttribute);
		}

		@Override
		public void add(String attributeName, ChildViewAttributeFactory factory) {
			childViewAttributeMap.put(attributeName, factory.create());
		}

		@Override
		public void addDependent(String attributeName, ChildViewAttributeFactory factory) {
			add(attributeName, factory);
		}

		@Override
		public void add(String attributeName, MultiTypePropertyViewAttribute<View> viewAttribute) {
			childViewAttributeMap.put(attributeName, viewAttribute);
		}

		@Override
		public void add(String attributeName, MultiTypePropertyViewAttributeFactory<View> factory) {
			childViewAttributeMap.put(attributeName, mock(MultiTypePropertyViewAttributeBinder.class));
		}

		@Override
		public void add(String attributeName, PropertyViewAttribute<View, ?> viewAttribute) {
			childViewAttributeMap.put(attributeName, viewAttribute);
		}

		@Override
		public void add(String attributeName, PropertyViewAttributeFactory<View> factory) {
			childViewAttributeMap.put(attributeName, factory.create());
		}

		@Override
		public void failOnFirstBindingError() {
		}

		@Override
		public boolean hasAttribute(String attributeName) {
			return resolvedGroupAttributes.hasAttribute(attributeName);
		}

		@Override
		public ValueModelAttribute valueModelAttributeFor(String attributeName) {
			return resolvedGroupAttributes.valueModelAttributeFor(attributeName);
		}

		@Override
		public StaticResourceAttribute staticResourceAttributeFor(String attributeName) {
			return resolvedGroupAttributes.staticResourceAttributeFor(attributeName);
		}

		@Override
		public <E extends Enum<E>> EnumAttribute<E> enumAttributeFor(String attributeName) {
			return resolvedGroupAttributes.enumAttributeFor(attributeName);
		}

	}
}