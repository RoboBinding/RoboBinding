/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package robobinding.binding.viewattribute;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import robobinding.beans.PresentationModelAdapter;
import robobinding.binding.PropertyViewAttribute;
import robobinding.value.ValueModel;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public abstract class AbstractPropertyViewAttribute<T> implements PropertyViewAttribute
{
	private final static Pattern BINDING_ATTRIBUTE_PATTERN = Pattern.compile("[$]{0,1}\\{[\\w]+\\}");
	private final static Pattern PROPERTY_NAME_PATTERN = Pattern.compile("\\w+");
	
	private PresentationModelAdapter presentationModelAdapter;
	private String propertyName;
	
	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, String attributeValue)
	{
		validate(attributeValue);
		
		setPresentationModelAdapter(presentationModelAdapter);
		setPropertyName(determinePropertyName(attributeValue));
				
		performBind(attributeValue);
	}
	
	private void performBind(String attributeValue)
	{
		if (attributeValue.startsWith("$"))
			performTwoWayBinding();
		else
			performOneWayBinding();
	}

	private String determinePropertyName(String attributeValue)
	{
		Matcher matcher = PROPERTY_NAME_PATTERN.matcher(attributeValue);
		matcher.find();
		return matcher.group();
	}
	
	private void validate(String attributeValue)
	{
		Matcher matcher = BINDING_ATTRIBUTE_PATTERN.matcher(attributeValue);
		
		if (!matcher.matches())
			throw new RuntimeException("Invalid binding attribute value: " + attributeValue);
	}

	void performOneWayBinding()
	{
		new OneWayBinder().performBind();
	}
	
	void performTwoWayBinding()
	{
		new TwoWayBinder().performBind();
	}
	
	void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}

	void setPresentationModelAdapter(PresentationModelAdapter presentationModelAdapter)
	{
		this.presentationModelAdapter = presentationModelAdapter;
	}
	
	protected abstract void initializeView(ValueModel<T> valueModel);
	protected abstract void observeChangesOnTheValueModel(final ValueModel<T> valueModel);
	protected abstract void observeChangesOnTheView(final ValueModel<T> valueModel);
	
	abstract class PropertyBinder
	{
		public abstract void performBind();
	}
	
	class OneWayBinder extends PropertyBinder
	{
		@Override
		public void performBind()
		{
			ValueModel<T> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyName);
			initializeView(valueModel);
			observeChangesOnTheValueModel(valueModel);
		}
	}
	
	class TwoWayBinder extends PropertyBinder
	{
		@Override
		public void performBind()
		{
			ValueModel<T> valueModel = presentationModelAdapter.getPropertyValueModel(propertyName);
			initializeView(valueModel);
			observeChangesOnTheValueModel(valueModel);
			observeChangesOnTheView(valueModel);
		}
	}
}
