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

import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import static org.robobinding.attribute.ChildAttributeResolvers.*;

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
	ValueCommitMode valueCommitMode;

	@Override
	protected String[] getCompulsoryAttributes()
	{
		return new String[] { TEXT };
	}
	
	@Override
	public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings)
	{
		resolverMappings.map(valueModelAttributeResolver(), TEXT);
		resolverMappings.map(plainAttributeResolver(), VALUE_COMMIT_MODE);
	}
	
	@Override
	protected void setupChildAttributeBindings(ChildAttributeBindings binding)
	{
		textAttribute = binding.addProperty(TwoWayTextAttribute.class, TEXT);
		
		determineValueCommitMode();
		textAttribute.setValueCommitMode(valueCommitMode);
	}

	private void determineValueCommitMode()
	{
		if (textAttribute.isTwoWayBinding() && valueCommitModeSpecified())
			throw new RuntimeException("The valueCommitMode attribute can only be used when a two-way binding text attribute is specified");

		if(valueCommitModeSpecified())
		{
			valueCommitMode = ValueCommitMode.from(valueCommitModeAttributeValue());
		}else
		{
			valueCommitMode = ValueCommitMode.ON_CHANGE;
		}
	}

	private String valueCommitModeAttributeValue()
	{
		return groupedAttribute.plainAttribute(VALUE_COMMIT_MODE).getValue();
	}

	private boolean valueCommitModeSpecified()
	{
		return groupedAttribute.hasAttribute(VALUE_COMMIT_MODE);
	}
}
