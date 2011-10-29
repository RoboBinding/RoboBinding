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
package robobinding.presentationmodel;

import robobinding.function.Function;
import robobinding.property.AbstractDataSetProperty;
import robobinding.property.PropertyValueModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public interface PresentationModelAdapter
{
	Class<?> getPropertyType(String propertyName);

	<T> PropertyValueModel<T> getReadOnlyPropertyValueModel(String propertyName);

	<T> PropertyValueModel<T> getPropertyValueModel(String propertyName);

	AbstractDataSetProperty<?> getDataSetPropertyValueModel(String propertyName);

	Function findFunction(String functionName, Class<?>... parameterTypes);
}
