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
package robobinding.binding.viewattribute;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import robobinding.value.ValueModel;
import android.view.View;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class IntegerVisibilityAttribute extends AbstractPropertyViewAttribute<Integer>
{
	private View view;
	
	public IntegerVisibilityAttribute(View view)
	{
		this.view = view;
	}

	@Override
	protected void initializeView(ValueModel<Integer> valueModel)
	{
		view.setVisibility(valueModel.getValue());
	}

	@Override
	protected void observeChangesOnTheValueModel(final ValueModel<Integer> valueModel)
	{
		valueModel.addValueChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				view.setVisibility(valueModel.getValue());
			}
		});
	}

	@Override
	protected void observeChangesOnTheView(ValueModel<Integer> valueModel)
	{
		throw new RuntimeException("Visibility only supports one-way binding");
	}

}
