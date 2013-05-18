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
import static org.mockito.Mockito.mock;
import static org.robobinding.viewattribute.MockGroupedViewAttributeConfigBuilder.aGroupedViewAttributeConfig;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.BindingContext;
import org.robobinding.attribute.EnumAttribute;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.attribute.ValueModelAttribute;

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
 * @param <S>
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractGroupedViewAttributeTest<T extends AbstractGroupedViewAttribute<?>> {
    protected T attributeUnderTest;
    private Map<String, String> presentAttributeMappings;
    private ChildViewAttributesWrapperForTest childViewAttributes;

    @Before
    @SuppressWarnings("unchecked")
    public void initialize() {
	AbstractGroupedViewAttribute<View> attributeUnderTest = instantiateAttributeUnderTest();
	this.attributeUnderTest = (T) attributeUnderTest;
	presentAttributeMappings = Maps.newHashMap();
    }

    private AbstractGroupedViewAttribute<View> instantiateAttributeUnderTest() {
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void performInitialization() {
	GroupedViewAttributeConfig<View> config = aGroupedViewAttributeConfig(createViewFor(attributeUnderTest), presentAttributeMappings);
	attributeUnderTest.initialize((GroupedViewAttributeConfig) config);
	setupChildViewAttributes();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void setupChildViewAttributes() {
	BindingContext bindingContext = mock(BindingContext.class);
	childViewAttributes = new ChildViewAttributesWrapperForTest(attributeUnderTest.childViewAttributes);
	attributeUnderTest.setupChildViewAttributes((ChildViewAttributes) childViewAttributes, bindingContext);
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
	for (Class<?> attributeClass : attributeClasses)
	    assertThatAttributeWasCreated(attributeClass);
    }

    protected void assertThatAttributeWasCreated(Class<?> attributeClass) {
	Object childAttribute = childViewAttributes.findChildAttributeOfType(attributeClass);
	assertNotNull("Child attribute of type '" + attributeClass.getName() + " not found", childAttribute);
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

    private static class ChildViewAttributesWrapperForTest extends ChildViewAttributes<View> {
	private Map<Class<?>, Object> childViewAttributeMappings = Maps.newHashMap();
	private ChildViewAttributes<View> forwarding;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ChildViewAttributesWrapperForTest(ChildViewAttributes<?> target) {
	    super(null, null);
	    this.forwarding = (ChildViewAttributes) target;
	}

	@Override
	public <ChildViewAttributeType extends ChildViewAttribute> ChildViewAttributeType add(String attributeName,
		ChildViewAttributeType childAttribute) {
	    ChildViewAttributeType childViewAttribute = forwarding.add(attributeName, childAttribute);
	    childViewAttributeMappings.put(childViewAttribute.getClass(), childViewAttribute);
	    return childViewAttribute;
	}

	@Override
	public <PropertyViewAttributeType extends PropertyViewAttribute<View>> PropertyViewAttributeType add(String propertyAttribute,
		PropertyViewAttributeType propertyViewAttribute) {
	    PropertyViewAttributeType childViewAttribute = forwarding.add(propertyAttribute, propertyViewAttribute);
	    childViewAttributeMappings.put(childViewAttribute.getClass(), childViewAttribute);
	    return childViewAttribute;
	}

	public Object findChildAttributeOfType(Class<?> childViewAttributeClass) {
	    return childViewAttributeMappings.get(childViewAttributeClass);
	}

	@Override
	public void preInitializeView(BindingContext bindingContext) {
	    forwarding.preInitializeView(bindingContext);
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
	    forwarding.bindTo(bindingContext);
	}

	@Override
	public void failOnFirstBindingError() {
	    forwarding.failOnFirstBindingError();
	}

	@Override
	public boolean hasAttribute(String attributeName) {
	    return forwarding.hasAttribute(attributeName);
	}

	@Override
	public ValueModelAttribute valueModelAttributeFor(String attributeName) {
	    return forwarding.valueModelAttributeFor(attributeName);
	}

	@Override
	public StaticResourceAttribute staticResourceAttributeFor(String attributeName) {
	    return forwarding.staticResourceAttributeFor(attributeName);
	}

	@Override
	public <E extends Enum<E>> EnumAttribute<E> enumAttributeFor(String attributeName) {
	    return forwarding.enumAttributeFor(attributeName);
	}
    }
}