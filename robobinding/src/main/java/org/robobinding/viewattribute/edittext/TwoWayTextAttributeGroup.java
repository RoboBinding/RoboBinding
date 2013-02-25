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
package org.robobinding.viewattribute.edittext;

import static org.robobinding.attribute.ChildAttributeResolvers.enumChildAttributeResolver;
import static org.robobinding.attribute.ChildAttributeResolvers.valueModelAttributeResolver;

import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.EnumAttribute;
import org.robobinding.attribute.MalformedAttributeException;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;

import android.widget.EditText;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TwoWayTextAttributeGroup extends AbstractGroupedViewAttribute<EditText>
{
	public static final String TEXT = "text";
	public static final String VALUE_COMMIT_MODE = "valueCommitMode";

	TwoWayTextAttribute textAttribute;

	@Override
	protected String[] getCompulsoryAttributes()
	{
		return new String[] { TEXT };
	}
	
	@Override
	public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings)
	{
		resolverMappings.map(valueModelAttributeResolver(), TEXT);
		resolverMappings.map(enumChildAttributeResolver(ValueCommitMode.class), VALUE_COMMIT_MODE);
	}
	
	@Override
	public void validateResolvedChildAttributes() {
		if (valueCommitModeSpecified() && !textAttribute().isTwoWayBinding())
			throw new MalformedAttributeException(VALUE_COMMIT_MODE, "The valueCommitMode attribute can only be used when a two-way binding text attribute is specified");
	}
	
	@Override
	protected void setupChildAttributeBindings(ChildAttributeBindings binding)
	{
		textAttribute = binding.addProperty(TEXT, new TwoWayTextAttribute());
		textAttribute.setValueCommitMode(determineValueCommitMode());
	}

	private ValueCommitMode determineValueCommitMode()
	{
		if (valueCommitModeSpecified())
		{
			EnumAttribute<ValueCommitMode> enumAttribute = groupAttributes.enumAttributeFor(VALUE_COMMIT_MODE);
			return enumAttribute.getValue();
		}

		return ValueCommitMode.ON_CHANGE;
	}

	private boolean valueCommitModeSpecified()
	{
		return groupAttributes.hasAttribute(VALUE_COMMIT_MODE);
	}
	
	private ValueModelAttribute textAttribute()
	{
		return groupAttributes.valueModelAttributeFor(TEXT);
	}
}
