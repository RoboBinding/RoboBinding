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
package org.robobinding.viewattribute;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.BindingContext;
import org.robobinding.attribute.PendingGroupAttributes;
import org.robobinding.attribute.ResolvedGroupAttributes;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.google.common.collect.Maps;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractGroupedViewAttributeTest<T extends AbstractGroupedViewAttribute<?>> {
    protected T attributeUnderTest;
    private Map<String, String> presentAttributeMappings;
    private ChildViewAttributes childViewAttributes;

    @Before
    public void initialize() {
	this.attributeUnderTest = createAttributeUnderTest();
	presentAttributeMappings = Maps.newHashMap();
    }

    protected T createAttributeUnderTest() {
	try {
	    ParameterizedType attributeType = (ParameterizedType) getClass().getGenericSuperclass();
	    return ParameterizedTypeUtils.createTypeArgument(attributeType, 0);
	} catch (Exception e) {
	    throw new RuntimeException("Error instantiating grouped attribute class: " + e.getMessage());
	}
    }

    private View createViewFor(T attributeUnderTest) {
	try {
	    Activity context = new Activity();

	    if (overrideViewClass() == null) {
		ParameterizedType viewType = (ParameterizedType) attributeUnderTest.getClass().getGenericSuperclass();
		return ParameterizedTypeUtils.createTypeArgument(viewType, 0, Context.class, context);
	    } else {
		return overrideViewClass().getConstructor(Context.class).newInstance(context);
	    }
	} catch (Exception e) {
	    throw new RuntimeException("Error instantiating view: " + e.getMessage());
	}
    }

    protected Class<? extends View> overrideViewClass() {
	return null;
    }

    @SuppressWarnings({ "unchecked" })
    protected void performInitialization() {
	PendingGroupAttributes pendingGroupAttributes = new PendingGroupAttributes(presentAttributeMappings);
	ResolvedGroupAttributesFactory resolvedGroupAttributesFactory = new ResolvedGroupAttributesFactory();
	ResolvedGroupAttributes resolvedGroupAttributes = resolvedGroupAttributesFactory.create(pendingGroupAttributes, attributeUnderTest);
	ChildViewAttributeInitializer childViewAttributeInitializer = new ChildViewAttributeInitializer(new StandaloneViewAttributeInitializer(
		mock(ViewListenersInjector.class)));
	ChildViewAttributesBuilder<View> childViewAttributesBuilder = new ChildViewAttributesBuilder<View>(resolvedGroupAttributes,
		childViewAttributeInitializer);
	((AbstractGroupedViewAttribute<View>) attributeUnderTest).initialize(createViewFor(attributeUnderTest), childViewAttributesBuilder);

	BindingContext bindingContext = mock(BindingContext.class);
	((AbstractGroupedViewAttribute<View>) attributeUnderTest).setupChildViewAttributes(childViewAttributesBuilder, bindingContext);
	childViewAttributes = childViewAttributesBuilder.build();
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
	Bindable childAttribute = findChildAttributeOfType(attributeClass);
	assertNotNull("Child attribute of type '" + attributeClass.getName() + "' not found", childAttribute);
    }

    private Bindable findChildAttributeOfType(Class<?> childViewAttributeClass) {
	for (Bindable childViewAttribute : childViewAttributes.childViewAttributeMap.values()) {
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
	return childViewAttributes.childViewAttributeMap.containsKey(attributeName);
    }

    protected Bindable childViewAttribute(String attributeName) {
	return childViewAttributes.childViewAttributeMap.get(attributeName);
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
}