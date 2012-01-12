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
package org.robobinding.viewattribute.textview;

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.PropertyBindingDetails;

import android.content.Context;
import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TextAttributeGroup extends AbstractGroupedViewAttribute<TextView>
{
	public static final String TEXT = "text";
	public static final String VALUE_COMMIT_MODE = "valueCommitMode";
	
	private TextAttribute textAttribute;
	private ValueCommitMode valueCommitMode;
	private PropertyBindingDetails propertyBindingDetails;
	
	@Override
	protected void initializeChildViewAttributes()
	{
		assertAttributesArePresent(TEXT);
		
		propertyBindingDetails = PropertyBindingDetails.createFrom(groupedAttributeDetails.attributeValueFor(TEXT));
		
		determineValueCommitMode();
		
		textAttribute = new TextAttribute();
		textAttribute.setView(view);
		textAttribute.setPreInitializeView(preInitializeViews);
		textAttribute.setPropertyBindingDetails(propertyBindingDetails);
		textAttribute.setValueCommitMode(valueCommitMode);
	}

	private void determineValueCommitMode()
	{
		if (propertyBindingDetails.twoWayBinding && valueCommitModeSpecified())
			throw new RuntimeException("The valueCommitMode attribute can only be used when a two-way binding text attribute is specified");
		
		if (!valueCommitModeSpecified() || "onChange".equals(valueCommitModeAttributeValue()))
			valueCommitMode = ValueCommitMode.ON_CHANGE;
		else if ("onFocusLost".equals(valueCommitModeAttributeValue()))
			valueCommitMode = ValueCommitMode.ON_FOCUS_LOST;
	}
	
	private String valueCommitModeAttributeValue()
	{
		return groupedAttributeDetails.attributeValueFor(VALUE_COMMIT_MODE);
	}
	
	private boolean valueCommitModeSpecified()
	{
		return groupedAttributeDetails.hasAttribute(VALUE_COMMIT_MODE);
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		textAttribute.bind(presentationModelAdapter, context);
	}
	
	public ValueCommitMode getValueCommitMode()
	{
		return valueCommitMode;
	}
	
}
