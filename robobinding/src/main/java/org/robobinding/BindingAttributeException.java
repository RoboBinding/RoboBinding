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
package org.robobinding;

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
public class BindingAttributeException extends RuntimeException
{
	private final Map<String, String> unrecognizedBindingAttributes;
	private final Map<String, String> malformedBindingAttributes;
	private final String viewName;

	public BindingAttributeException(Map<String, String> unrecognizedBindingAttributes, Map<String, String> malformedBindingAttributes, View view)
	{
		this(unrecognizedBindingAttributes, malformedBindingAttributes, view.getClass().getName());
	}
	
	public BindingAttributeException(Map<String, String> unrecognizedBindingAttributes, Map<String, String> malformedBindingAttributes, String viewName)
	{
		this.unrecognizedBindingAttributes = unrecognizedBindingAttributes;
		this.malformedBindingAttributes = malformedBindingAttributes;
		this.viewName = viewName;
	}
	
	@Override
	public String toString()
	{
		return describeUnresolvedAttributes();
	}
	
	private String describeUnresolvedAttributes()
	{
		String attributeErrors = "";
		
		if (!unrecognizedBindingAttributes.isEmpty()) {
		
			String unhandledAttributes = "Unrecognized binding attribute(s) for " + viewName + ": ";
	
			for (String attributeKey : unrecognizedBindingAttributes.keySet())
				unhandledAttributes += attributeKey + ": " + unrecognizedBindingAttributes.get(attributeKey) + "; ";
			
			attributeErrors += unhandledAttributes;
			
			if (!malformedBindingAttributes.isEmpty())
				attributeErrors += "\n\n";
		}
		if (!malformedBindingAttributes.isEmpty()) {
			
			for (String attributeName : malformedBindingAttributes.keySet()) {
				attributeErrors += malformedBindingAttributes.get(attributeName);
			}
		}
		
		return attributeErrors;
	}
	
	public Map<String, String> getUnrecognizedBindingAttributes()
	{
		return Collections.unmodifiableMap(unrecognizedBindingAttributes);
	}
	
	public Map<String, String> getMalformedBindingAttributes()
	{
		return Collections.unmodifiableMap(malformedBindingAttributes);
	}
}
