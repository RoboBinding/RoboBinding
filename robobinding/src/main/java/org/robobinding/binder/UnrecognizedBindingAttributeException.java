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
package org.robobinding.binder;

import java.util.Collections;
import java.util.Map;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@SuppressWarnings("serial")
public class UnrecognizedBindingAttributeException extends RuntimeException
{
	private final Map<String, String> unrecognizedBindingAttributes;
	private final View view;

	public UnrecognizedBindingAttributeException(Map<String, String> unrecognizedBindingAttributes, View view)
	{
		this.unrecognizedBindingAttributes = unrecognizedBindingAttributes;
		this.view = view;
	}
	
	@Override
	public String toString()
	{
		return describeUnresolvedAttributes();
	}
	
	private String describeUnresolvedAttributes()
	{
		String unhandledAttributes = "Unrecognized binding attribute(s) for " + view.getClass().getName() + ": ";

		for (String attributeKey : unrecognizedBindingAttributes.keySet())
			unhandledAttributes += attributeKey + ": " + unrecognizedBindingAttributes.get(attributeKey) + "; ";

		return unhandledAttributes;
	}
	
	public Map<String, String> getUnrecognizedBindingAttributes()
	{
		return Collections.unmodifiableMap(unrecognizedBindingAttributes);
	}
}
