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


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.robobinding.viewattribute.AttributeBindingException;
import static org.robobinding.CollectionUtils.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import android.view.View;

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

	void addAttributeError(String attribute, RuntimeException e)
	{
		if(e instanceof AttributeBindingException)
		{
			attributeErrors.add((AttributeResolutionException)e);
		}else
		{
			attributeErrors.add(new AttributeResolutionException(attribute, e.getMessage(), e));
		}
	}
	
	void addUnrecognizedAttributes(Collection<String> attributes)
	{
		for(String attribute : attributes)
		{
			addAttributeError(attribute, new UnrecognizedAttributeException(attribute));
		}
	}
	
	void addAttributeGroupError(String[] attributeGroup, RuntimeException e)
	{
		if(e instanceof AttributeGroupResolutionException)
		{
			attributeGroupErrors.put(attributeGroup, (AttributeGroupResolutionException)e);
		}else
		{
			attributeGroupErrors.put(attributeGroup, new AttributeGroupResolutionException(e.getMessage(), e));
		}
	}

	public Collection<AttributeResolutionException> getAttributeErrors()
	{
		return Collections.unmodifiableCollection(attributeErrors);
	}
}
