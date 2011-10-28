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
package robobinding.value.experimental;

import java.beans.PropertyChangeListener;

import robobinding.utils.Validate;
import robobinding.value.ValueModel;

/**
 * Converts value from type {@code S} to type {@code D}. 
 * {@code S} - source type. 
 * {@code D} - destination type.
 */
public abstract class AbstractConverter<S, D> implements ValueModel<D>
{
	protected final ValueModel<S> source;
	public AbstractConverter(ValueModel<S> source)
	{
		Validate.notNull(source);
		this.source = source;
	}
	/**
	 * Converts a value from the source type to the destination type or format used by this converter.
	 */
	public abstract D convertFromSource(S sourceValue);
	public abstract S convertFromDestination(D destinationValue);
	@Override
	public D getValue()
	{
		return convertFromSource(source.getValue());
	}
	@Override
	public void setValue(D newValue)
	{
		source.setValue(convertFromDestination(newValue));
	}
	@Override
	public void addValueChangeListener(PropertyChangeListener listener)
	{
		source.addValueChangeListener(listener);
	}
	@Override
	public void removeValueChangeListener(PropertyChangeListener listener)
	{
		source.removeValueChangeListener(listener);
	}
}
