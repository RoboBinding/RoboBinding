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

import java.util.Map;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface PendingAttributesForView
{
	View getView();
	void resolveAttributeIfExists(String attribute, AttributeResolver attributeResolver);
	void resolveAttributeGroupIfExists(String[] attributeGroup, AttributeGroupResolver attributeGroupResolver);
	boolean isEmpty();
	ViewResolutionErrors getResolutionErrors();

	public interface AttributeResolver
	{
		void resolve(View view, String attribute, String attributeValue);
	}
	
	public interface AttributeGroupResolver
	{
		void resolve(View view, String[] attributeGroup, Map<String, String> presentAttributeMappings);
	}
}
