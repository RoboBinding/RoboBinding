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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.robobinding.viewattribute.AttributeBindingException;
import org.robobinding.viewattribute.AttributeGroupBindingException;
import static org.robobinding.CollectionUtils.*;

import android.view.View;

import com.google.common.collect.Lists;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@SuppressWarnings("serial")
public class ViewBindingException extends RuntimeException
{
	private View view;
	private List<AttributeBindingException> attributeErrors;
	private List<AttributeGroupBindingException> attributeGroupErrors;
	
	public ViewBindingException(View view)
	{
		this.view = view;
		attributeErrors = Lists.newArrayList();
		attributeGroupErrors = Lists.newArrayList();
	}
	
	public View getView()
	{
		return view;
	}

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

	public int numErrors()
	{
		return attributeErrors.size()+attributeGroupErrors.size();
	}

	void addAttributeGroupError(AttributeGroupBindingException e)
	{
		attributeGroupErrors.add(e);
	}

	void addAttributeError(AttributeBindingException e)
	{
		attributeErrors.add(e);
	}

	public Collection<AttributeBindingException> getAttributeErrors()
	{
		return Collections.unmodifiableCollection(attributeErrors);
	}
}
