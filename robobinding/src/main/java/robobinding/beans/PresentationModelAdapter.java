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
package robobinding.beans;

import robobinding.presentationmodel.AbstractDataSetValueModel;
import robobinding.value.ValueModel;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public interface PresentationModelAdapter
{

	<T> ValueModel<T> getReadOnlyPropertyValueModel(String propertyName);

	<T> ValueModel<T> getPropertyValueModel(String propertyName);

	Object getPresentationModel();

	Class<?> getPropertyType(String propertyName);

	AbstractDataSetValueModel getDataSetPropertyValueModel(String propertyName);

	Command findCommand(String commandName, Class<?>... preferredCommandParameterTypes);

}
