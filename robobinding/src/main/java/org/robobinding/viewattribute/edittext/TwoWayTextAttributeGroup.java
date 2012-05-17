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
	ValueCommitMode valueCommitMode;

	@Override
	protected String[] getCompulsoryAttributes()
	{
		return new String[] { TEXT };
	}
	
	@Override
	protected void setupChildAttributesBinding(ChildAttributesBinding binding)
	{
		textAttribute = binding.addProperty(TwoWayTextAttribute.class, TEXT);
		
		determineValueCommitMode();
		textAttribute.setValueCommitMode(valueCommitMode);
	}

	private void determineValueCommitMode()
	{
		if (textAttribute.isTwoWayBinding() && valueCommitModeSpecified())
			throw new RuntimeException("The valueCommitMode attribute can only be used when a two-way binding text attribute is specified");

		valueCommitMode = ValueCommitMode.from(valueCommitModeAttributeValue());
	}

	private String valueCommitModeAttributeValue()
	{
		return groupedAttributeDetails.attributeValueFor(VALUE_COMMIT_MODE);
	}

	private boolean valueCommitModeSpecified()
	{
		return groupedAttributeDetails.hasAttribute(VALUE_COMMIT_MODE);
	}
}
