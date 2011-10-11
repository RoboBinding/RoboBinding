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

import robobinding.beans.BeanAdapter;
import robobinding.binding.BindingType;
import robobinding.value.ValueModel;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public abstract class AbstractPropertyViewAttribute<T> implements ViewAttribute
{
	private final static Pattern BINDING_ATTRIBUTE_PATTERN = Pattern.compile("[$]{0,1}\\{[\\w]+\\}");
	private final static Pattern PROPERTY_NAME_PATTERN = Pattern.compile("\\w+");
	
	@Override
	public void bindOnto(BeanAdapter<?> beanAdapter, String attributeValue)
	{
		PropertyBindingDetails propertyBindingDetails = determinePropertyBindingDetails(attributeValue);
		
		ValueModel<T> valueModel = beanAdapter.getPropertyValueModel(propertyBindingDetails.propertyName);
		
		bindOnto(valueModel, propertyBindingDetails.bindingType);
	}
	
	protected abstract void bindOnto(ValueModel<T> valueModel, BindingType bindingType);

	public PropertyBindingDetails determinePropertyBindingDetails(String attributeValue)
	{
		validate(attributeValue);
		
		BindingType bindingType = determineBindingType(attributeValue);
		String propertyName = determinePropertyName(attributeValue);
		
		return new PropertyBindingDetails(propertyName, bindingType);
	}
	
	private String determinePropertyName(String attributeValue)
	{
		Matcher matcher = PROPERTY_NAME_PATTERN.matcher(attributeValue);
		matcher.find();
		return matcher.group();
	}

	private BindingType determineBindingType(String attributeValue)
	{
		if (attributeValue.startsWith("$"))
			return BindingType.TWO_WAY;
		
		return BindingType.ONE_WAY;
	}

	private void validate(String attributeValue)
	{
		Matcher matcher = BINDING_ATTRIBUTE_PATTERN.matcher(attributeValue);
		
		if (!matcher.matches())
			throw new RuntimeException("Invalid binding attribute value: " + attributeValue);
	}

	static class PropertyBindingDetails
	{
		String propertyName;
		BindingType bindingType;

		public PropertyBindingDetails(String propertyName, BindingType bindingType)
		{
			this.propertyName = propertyName;
			this.bindingType = bindingType;
		}
	}
}
