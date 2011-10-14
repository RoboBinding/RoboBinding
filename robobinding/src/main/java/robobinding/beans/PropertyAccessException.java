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

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
@SuppressWarnings("serial")
public final class PropertyAccessException extends PropertyException
{
	private PropertyAccessException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Creates and returns a new PropertyAccessException instance for a failed
	 * read access for the specified bean, property accessor and cause.
	 */
	static <T> PropertyAccessException createReadAccessException(Object bean, PropertyAccessor<T> propertyAccessor, Throwable cause)
	{
		String message = propertyAccessor.describePropertyGetter(bean);
		return new PropertyAccessException(message, cause);
	}

	/**
	 * Creates and returns a new PropertyAccessException instance for a failed
	 * write access for the specified bean, value, property accessor and cause.
	 */
	static <P> PropertyAccessException createWriteAccessException(Object bean, Object propertyValue, PropertyAccessor<P> propertyAccessor, Throwable cause)
	{

		String message = propertyAccessor.describePropertySetter(bean, propertyValue);
		return new PropertyAccessException(message, cause);
	}

}
