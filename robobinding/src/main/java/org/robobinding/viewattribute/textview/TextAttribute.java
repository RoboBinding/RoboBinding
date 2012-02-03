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
package org.robobinding.viewattribute.textview;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;

import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TextAttribute extends AbstractTextAttribute<TextView>
{
	@Override
	protected AbstractPropertyViewAttribute<TextView, ?> createNewCharSequenceAttribute()
	{
		CharSequenceTextAttribute charSequenceTextAttribute = new CharSequenceTextAttribute();
		return charSequenceTextAttribute;
	}

	@Override
	protected AbstractPropertyViewAttribute<TextView, ?> createNewStringAttribute()
	{
		StringTextAttribute stringTextAttribute = new StringTextAttribute();
		return stringTextAttribute;
	}
	
	private abstract static class AbstractCharSequenceTextAttribute<PropertyType extends CharSequence> extends
			AbstractReadOnlyPropertyViewAttribute<TextView, PropertyType>
	{
		@Override
		protected void valueModelUpdated(PropertyType newValue)
		{
			view.setText(newValue);
		}

		protected abstract void updateValueModel(ValueModel<PropertyType> valueModel, CharSequence charSequence);
	}

	static class StringTextAttribute extends AbstractCharSequenceTextAttribute<String>
	{
		@Override
		protected void updateValueModel(ValueModel<String> valueModel, CharSequence charSequence)
		{
			valueModel.setValue(charSequence.toString());
		}
	}

	static class CharSequenceTextAttribute extends AbstractCharSequenceTextAttribute<CharSequence>
	{
		@Override
		protected void updateValueModel(ValueModel<CharSequence> valueModel, CharSequence charSequence)
		{
			valueModel.setValue(charSequence);
		}
	}
}
