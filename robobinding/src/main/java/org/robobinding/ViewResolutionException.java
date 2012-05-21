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


import static org.robobinding.CollectionUtils.isNotEmpty;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.view.View;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@SuppressWarnings("serial")
public class ViewResolutionException extends RuntimeException implements ViewResolutionErrors
{
	private View view;
	private List<AttributeResolutionException> attributeErrors;
	private Map<String[], AttributeGroupResolutionException> attributeGroupErrors;
	public ViewResolutionException(View view)
	{
		this.view = view;
		attributeErrors = Lists.newArrayList();
		attributeGroupErrors = Maps.newHashMap();
	}
	
	@Override
	public View getView()
	{
		return view;
	}

	@Override
	public int numErrors()
	{
		return attributeErrors.size()+attributeGroupErrors.size();
	}
	
	@Override
	public void assertNoErrors()
	{
		if(hasErrors())
		{
			throw this;
		}
	}
	
	private boolean hasErrors()
	{
		return isNotEmpty(attributeErrors) || isNotEmpty(attributeGroupErrors);
	}

	void addAttributeError(AttributeResolutionException e)
	{
		attributeErrors.add((AttributeResolutionException)e);
	}
	
	void addUnrecognizedAttributes(Collection<String> attributes)
	{
		for(String attribute : attributes)
		{
			addAttributeError(new UnrecognizedAttributeException(attribute));
		}
	}
	
	void addAttributeGroupError(String[] attributeGroup, AttributeGroupResolutionException e)
	{
		attributeGroupErrors.put(attributeGroup, e);
	}

	public Collection<AttributeResolutionException> getAttributeErrors()
	{
		return Collections.unmodifiableCollection(attributeErrors);
	}
}
