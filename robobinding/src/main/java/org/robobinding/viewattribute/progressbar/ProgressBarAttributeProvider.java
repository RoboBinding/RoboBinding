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
package org.robobinding.viewattribute.progressbar;

import org.robobinding.binder.BindingAttributeResolver;
import org.robobinding.viewattribute.BindingAttributeProvider;

import android.widget.ProgressBar;
import android.widget.SeekBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ProgressBarAttributeProvider implements BindingAttributeProvider<ProgressBar>
{
	private static final String PROGRESS = "progress";
	private static final String SECONDARY_PROGRESS = "secondaryProgress";
	private static final String MAX = "max";

	@Override
	public void resolveSupportedBindingAttributes(ProgressBar progressBar, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews)
	{
		if (bindingAttributeResolver.hasAttribute(PROGRESS) && !(progressBar instanceof SeekBar))
		{
			String attributeValue = bindingAttributeResolver.findAttributeValue(PROGRESS);
			bindingAttributeResolver.resolveAttribute(PROGRESS, new ProgressAttribute(progressBar, attributeValue, preInitializeViews));
		}
		if (bindingAttributeResolver.hasAttribute(SECONDARY_PROGRESS))
		{
			String attributeValue = bindingAttributeResolver.findAttributeValue(SECONDARY_PROGRESS);
			bindingAttributeResolver.resolveAttribute(SECONDARY_PROGRESS, new SecondaryProgressAttribute(progressBar, attributeValue, preInitializeViews));
		}
		if (bindingAttributeResolver.hasAttribute(MAX))
		{
			String attributeValue = bindingAttributeResolver.findAttributeValue(MAX);
			bindingAttributeResolver.resolveAttribute(MAX, new MaxAttribute(progressBar, attributeValue, preInitializeViews));
		}
	}

}