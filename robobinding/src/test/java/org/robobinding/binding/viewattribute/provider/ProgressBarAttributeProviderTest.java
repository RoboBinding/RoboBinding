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
package org.robobinding.binding.viewattribute.provider;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.binding.BindingAttributeResolver;
import org.robobinding.binding.viewattribute.MaxAttribute;
import org.robobinding.binding.viewattribute.ProgressAttribute;
import org.robobinding.binding.viewattribute.SecondaryProgressAttribute;

import android.widget.ProgressBar;
import android.widget.SeekBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ProgressBarAttributeProviderTest extends AbstractBindingAttributeProviderTest<ProgressBar>
{
	@Override
	protected BindingAttributeProvider<ProgressBar> getBindingAttributeProvider()
	{
		return new ProgressBarAttributeProvider();
	}

	@Override
	protected ProgressBar createNewViewInstance()
	{
		return new ProgressBar(null);
	}

	@Override
	protected void populateAttributeClassMappings(AttributeClassMappings attributeClassMappings)
	{
		attributeClassMappings.add("progress", ProgressAttribute.class);
		attributeClassMappings.add("secondaryProgress", SecondaryProgressAttribute.class);
		attributeClassMappings.add("max", MaxAttribute.class);		
	}
	
	@Test
	public void givenAProgressAttribute_WhenResolvingBindingAttributesForASeekBar_ThenDoNotCreateABindingAttribute()
	{
		SeekBar seekBar = new SeekBar(null);
		ProgressBarAttributeProvider progressBarAttributeProvider = new ProgressBarAttributeProvider();
		BindingAttributeResolver bindingAttributeResolver = initialiseBindingAttributeResolverFromAttributeName("progress");
		
		progressBarAttributeProvider.resolveSupportedBindingAttributes(seekBar, bindingAttributeResolver, true);
		
		assertThat(bindingAttributeResolver.getResolvedBindingAttributes().size(), is(0));	
	}
}
