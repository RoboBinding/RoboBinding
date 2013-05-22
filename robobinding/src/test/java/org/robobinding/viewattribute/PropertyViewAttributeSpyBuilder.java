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

import static org.mockito.Mockito.mock;
import static org.robobinding.attribute.MockValueModelAttributeBuilder.aValueModelAttribute;
import static org.robobinding.viewattribute.MockPropertyViewAttributeConfigBuilder.aPropertyViewAttributeConfig;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyViewAttributeSpyBuilder
{
	private boolean preInitializeView;
	private boolean twoWayBinding;
	private String propertyName;
	public static PropertyViewAttributeSpyBuilder aPropertyViewAttributeSpy()
	{
		return new PropertyViewAttributeSpyBuilder();
	}
	
	private PropertyViewAttributeSpyBuilder()
	{
	}

	public PropertyViewAttributeSpyBuilder withPreInitializeView(boolean preInitializeView)
	{
		this.preInitializeView = preInitializeView;
		return this;
	}

	public PropertyViewAttributeSpyBuilder withTwoWaybinding(boolean twoWayBinding)
	{
		this.twoWayBinding = twoWayBinding;
		return this;
	}

	public PropertyViewAttributeSpyBuilder withPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
		return this;
	}

	public PropertyViewAttributeSpy build()
	{
		PropertyViewAttributeSpy viewAttribute = new PropertyViewAttributeSpy(preInitializeView);
		viewAttribute.initialize(aPropertyViewAttributeConfig(mock(View.class), aValueModelAttribute(propertyName, twoWayBinding)));
		return viewAttribute;
	}
}
