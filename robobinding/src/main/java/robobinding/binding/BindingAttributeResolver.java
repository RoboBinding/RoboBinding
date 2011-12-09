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
package robobinding.binding;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import robobinding.internal.com_google_common.collect.Lists;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingAttributeResolver
{
	private List<BindingAttribute> resolvedBindingAttributes;
	private Map<String, String> pendingAttributes;
	public BindingAttributeResolver(Map<String, String> pendingAttributes)
	{
		this.pendingAttributes = pendingAttributes;
		this.resolvedBindingAttributes = Lists.newArrayList();
	}
	public boolean hasAttribute(String attributeName)
	{
		return pendingAttributes.containsKey(attributeName);
	}
	public boolean hasOneOfAttributes(String[] attributeNames)
	{
		for(String attributeName : attributeNames)
		{
			if(pendingAttributes.containsKey(attributeName))
			{
				return true;
			}
		}
		return false;
	}
	public String findAttributeValue(String attributeName)
	{
		return pendingAttributes.get(attributeName);
	}
	public void resolveAttribute(String attributeName, ViewAttribute viewAttribute)
	{
		resolveAttributes(new String[]{attributeName}, viewAttribute);
	}
	public void resolveAttributes(String[] attributeNames, ViewAttribute viewAttribute)
	{
		List<String> attributeNameList = Lists.newArrayList(attributeNames);
		BindingAttribute bindingAttribute = new BindingAttribute(attributeNameList, viewAttribute);
		resolvedBindingAttributes.add(bindingAttribute);
		removeResolvedAttributes(attributeNameList);
	}
	private void removeResolvedAttributes(List<String> attributeNames)
	{
		for(String attributeName : attributeNames)
		{
			pendingAttributes.remove(attributeName);
		}
	}
	public boolean isDone()
	{
		return pendingAttributes.isEmpty();
	}
	public boolean hasUnresolvedAttributes()
	{
		return !pendingAttributes.isEmpty();
	}
	public String describeUnresolvedAttributes()
	{
		String unhandledAttributes = "";
		
		for (String attributeKey : pendingAttributes.keySet())
			unhandledAttributes += attributeKey + ": " + pendingAttributes.get(attributeKey) + "; ";
				
		return unhandledAttributes;
	}
	public List<BindingAttribute> getResolvedBindingAttributes()
	{
		return Collections.unmodifiableList(resolvedBindingAttributes);
	}
}
