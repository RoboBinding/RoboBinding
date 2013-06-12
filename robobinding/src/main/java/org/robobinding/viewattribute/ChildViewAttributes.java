/**
 * Copyright 2013 Cheng Wei, Robert Taylor
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

import java.util.Map;

import org.robobinding.BindingContext;
import org.robobinding.attribute.AbstractAttribute;
import org.robobinding.attribute.EnumAttribute;
import org.robobinding.attribute.GroupAttributes;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ChildViewAttributes<T extends View> {
    private AbstractChildViewAttributesState<T> state;

    public ChildViewAttributes(GroupAttributes groupAttributes, AbstractViewAttributeInitializer viewAttributeInitializer) {
	state = new Initializing<T>(groupAttributes, viewAttributeInitializer);
    }

    public <ChildViewAttributeType extends ChildViewAttribute> ChildViewAttributeType add(String attributeName, 
	    ChildViewAttributeType childAttribute) {
	return state.add(attributeName, childAttribute);
    }

    public <PropertyViewAttributeType extends PropertyViewAttribute<T>> PropertyViewAttributeType add(String propertyAttribute,
	    PropertyViewAttributeType propertyViewAttribute) {
	return state.add(propertyAttribute, propertyViewAttribute);
    }

    public void preInitializeView(BindingContext bindingContext) {
	state.preInitializeView(bindingContext);
    }

    public void bindTo(BindingContext bindingContext) {
	state.bindTo(bindingContext);
    }

    public void failOnFirstBindingError() {
	state.failOnFirstBindingError();
    }

    public void markSetupCompleted() {
	state = state.nextState();
    }

    public boolean hasAttribute(String attributeName) {
	return state.hasAttribute(attributeName);
    }

    public ValueModelAttribute valueModelAttributeFor(String attributeName) {
	return state.valueModelAttributeFor(attributeName);
    }

    public StaticResourceAttribute staticResourceAttributeFor(String attributeName) {
	return state.staticResourceAttributeFor(attributeName);
    }

    public <E extends Enum<E>> EnumAttribute<E> enumAttributeFor(String attributeName) {
	return state.enumAttributeFor(attributeName);
    }

    private abstract static class AbstractChildViewAttributesState<T extends View> {
	protected GroupAttributes groupAttributes;

	public AbstractChildViewAttributesState(GroupAttributes groupAttributes) {
	    this.groupAttributes = groupAttributes;
	}

	public abstract <ChildViewAttributeType extends ChildViewAttribute> ChildViewAttributeType add(String attributeName,
		ChildViewAttributeType childAttribute);

	public abstract <PropertyViewAttributeType extends PropertyViewAttribute<T>> PropertyViewAttributeType add(String propertyAttribute,
		PropertyViewAttributeType propertyViewAttribute);

	public abstract void preInitializeView(BindingContext bindingContext);

	public abstract void bindTo(BindingContext bindingContext);

	public abstract void failOnFirstBindingError();

	public abstract AbstractChildViewAttributesState<T> nextState();

	public final boolean hasAttribute(String attributeName) {
	    return groupAttributes.hasAttribute(attributeName);
	}

	public final ValueModelAttribute valueModelAttributeFor(String attributeName) {
	    return groupAttributes.valueModelAttributeFor(attributeName);
	}

	public final StaticResourceAttribute staticResourceAttributeFor(String attributeName) {
	    return groupAttributes.staticResourceAttributeFor(attributeName);
	}

	public final <E extends Enum<E>> EnumAttribute<E> enumAttributeFor(String attributeName) {
	    return groupAttributes.enumAttributeFor(attributeName);
	}
    }

    private static class Initializing<T extends View> extends AbstractChildViewAttributesState<T> {
	private AbstractViewAttributeInitializer viewAttributeInitializer;
	private Map<String, ViewAttribute> childAttributeMap;
	private boolean failOnFirstBindingError;

	public Initializing(GroupAttributes groupAttributes, AbstractViewAttributeInitializer viewAttributeInitializer) {
	    super(groupAttributes);

	    this.viewAttributeInitializer = viewAttributeInitializer;
	    childAttributeMap = Maps.newLinkedHashMap();
	    failOnFirstBindingError = false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <ChildViewAttributeType extends ChildViewAttribute> ChildViewAttributeType add(String attributeName,
		ChildViewAttributeType childAttribute) {
	    AbstractAttribute attribute = groupAttributes.attributeFor(attributeName);
	    if (childAttribute instanceof ChildViewAttributeWithAttribute<?>) {
		((ChildViewAttributeWithAttribute<AbstractAttribute>) childAttribute).setAttribute(attribute);
	    }
	    childAttributeMap.put(attributeName, new ViewAttributeAdapter(childAttribute));
	    return childAttribute;
	}

	@Override
	public <PropertyViewAttributeType extends PropertyViewAttribute<T>> PropertyViewAttributeType add(String propertyAttribute,
		PropertyViewAttributeType propertyViewAttribute) {
	    ValueModelAttribute attributeValue = groupAttributes.valueModelAttributeFor(propertyAttribute);
	    viewAttributeInitializer.initializePropertyViewAttribute(propertyViewAttribute, attributeValue);
	    childAttributeMap.put(propertyAttribute, propertyViewAttribute);
	    return propertyViewAttribute;
	}

	@Override
	public void preInitializeView(BindingContext bindingContext) {
	    throw newInitializationNotCompletedException();
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
	    throw newInitializationNotCompletedException();
	}

	@Override
	public void failOnFirstBindingError() {
	    failOnFirstBindingError = true;
	}

	private RuntimeException newInitializationNotCompletedException() {
	    throw new RuntimeException("ChildViewAttributes initialization not completed");
	}

	@Override
	public AbstractChildViewAttributesState<T> nextState() {
	    return new ReadyForBinding<T>(groupAttributes, childAttributeMap, failOnFirstBindingError);
	}
    }

    private static class ReadyForBinding<T extends View> extends AbstractChildViewAttributesState<T> {
	private Map<String, ViewAttribute> childAttributeMap;
	private boolean failOnFirstBindingError;

	private AttributeGroupBindingException bindingErrors;

	public ReadyForBinding(GroupAttributes groupAttributes, Map<String, ViewAttribute> childAttributeMap, boolean failOnFirstBindingError) {
	    super(groupAttributes);

	    this.childAttributeMap = childAttributeMap;
	    this.failOnFirstBindingError = failOnFirstBindingError;
	    bindingErrors = new AttributeGroupBindingException();
	}

	@Override
	public <ChildViewAttributeType extends ChildViewAttribute> ChildViewAttributeType add(String attributeName,
		ChildViewAttributeType childAttribute) {
	    throw new UnsupportedOperationException();
	}

	@Override
	public <PropertyViewAttributeType extends PropertyViewAttribute<T>> PropertyViewAttributeType add(String propertyAttribute,
		PropertyViewAttributeType propertyViewAttribute) {
	    throw new UnsupportedOperationException();
	}

	@Override
	public void preInitializeView(BindingContext bindingContext) {
	    for (Map.Entry<String, ViewAttribute> childAttributeEntry : childAttributeMap.entrySet()) {
		ViewAttribute childAttribute = childAttributeEntry.getValue();
		try {
		    childAttribute.preInitializeView(bindingContext);
		} catch (RuntimeException e) {
		    bindingErrors.addChildAttributeError(childAttributeEntry.getKey(), e);

		    if (failOnFirstBindingError)
			break;
		}
	    }

	    bindingErrors.assertNoErrors();
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
	    for (Map.Entry<String, ViewAttribute> childAttributeEntry : childAttributeMap.entrySet()) {
		ViewAttribute childAttribute = childAttributeEntry.getValue();

		try {
		    childAttribute.bindTo(bindingContext);
		} catch (RuntimeException e) {
		    bindingErrors.addChildAttributeError(childAttributeEntry.getKey(), e);

		    if (failOnFirstBindingError)
			break;
		}
	    }

	    bindingErrors.assertNoErrors();
	}

	@Override
	public void failOnFirstBindingError() {
	    throw newNoFurtherModificationAllowedException();
	}

	private RuntimeException newNoFurtherModificationAllowedException() {
	    throw new RuntimeException("ChildViewAttributes initialization completed, no further modification allowed");
	}

	@Override
	public AbstractChildViewAttributesState<T> nextState() {
	    throw new UnsupportedOperationException();
	}

    }

    private static class ViewAttributeAdapter implements ViewAttribute {
	private ChildViewAttribute childViewAttribute;

	public ViewAttributeAdapter(ChildViewAttribute childViewAttribute) {
	    this.childViewAttribute = childViewAttribute;
	}

	@Override
	public void preInitializeView(BindingContext bindingContext) {
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
	    childViewAttribute.bindTo(bindingContext);
	}
    }
}
