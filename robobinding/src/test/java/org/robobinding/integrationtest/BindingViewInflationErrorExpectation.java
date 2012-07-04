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
package org.robobinding.integrationtest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Set;

import org.robobinding.AttributeResolutionException;
import org.robobinding.ViewResolutionError;
import org.robobinding.attribute.MissingRequiredAttributesException;
import org.robobinding.binder.BindingViewInflationError;
import org.robobinding.binder.ViewBindingError;

import android.view.View;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingViewInflationErrorExpectation
{
	private View view;
	private Set<AttributeResolutionErrorExpectation> attributeResolutionErrorExpectations;
	private Set<MissingRequiredAttributesResolutionErrorExpectation> missingRequiredAttributesResolutionErrorExpectations;
	private Set<String> attributesOfBindingError;
	private BindingViewInflationErrorExpectation(BindingViewInflationErrorExpectation.Builder builder)
	{
		this.view = builder.view;
		this.attributeResolutionErrorExpectations = builder.attributeResolutionErrorExpectations;
		this.missingRequiredAttributesResolutionErrorExpectations = builder.missingRequiredAttributesResolutionErrorExpectations;
		this.attributesOfBindingError = builder.attributesOfBindingError;
	}
	
	public static Builder aBindingViewInflationErrorExpectationOf(View view)
	{
		return new Builder(view);
	}
	
	public void meet(BindingViewInflationError error)
	{
		assertThat(error.getView(), sameInstance(view));
		
		assertResolutionErrors(error.getResolutionError());
		assertBindingErrors(error.getBindingError());
	}

	private void assertResolutionErrors(ViewResolutionError error)
	{
		assertThat(error.numErrors(), is(numResolutionErrors()));
	}
	
	private int numResolutionErrors()
	{
		return attributeResolutionErrorExpectations.size()+missingRequiredAttributesResolutionErrorExpectations.size();
	}

	private void assertBindingErrors(ViewBindingError error)
	{
		assertThat(error.numErrors(), is(attributesOfBindingError.size()));
	}

	public static class Builder
	{
		private View view;
		private Set<AttributeResolutionErrorExpectation> attributeResolutionErrorExpectations;
		private Set<MissingRequiredAttributesResolutionErrorExpectation> missingRequiredAttributesResolutionErrorExpectations;
		private Set<String> attributesOfBindingError;
		private Builder(View view)
		{
			this.view = view;
			attributeResolutionErrorExpectations = Sets.newHashSet();
			missingRequiredAttributesResolutionErrorExpectations = Sets.newHashSet();
			attributesOfBindingError = Sets.newHashSet();
		}
		
		public Builder withAttributeResolutionErrorOf(String attributeName)
		{
			attributeResolutionErrorExpectations.add(new AttributeResolutionErrorExpectation(attributeName));
			return this;
		}
		
		public Builder withMissingRequiredAttributesResolutionErrorOf(String... attributeNames)
		{
			missingRequiredAttributesResolutionErrorExpectations.add(new MissingRequiredAttributesResolutionErrorExpectation(attributeNames));
			return this;
		}
		
		public Builder withAttributeBindingErrorOf(String attributeName)
		{
			attributesOfBindingError.add(attributeName);
			return this;
		}
		
		public BindingViewInflationErrorExpectation build()
		{
			return new BindingViewInflationErrorExpectation(this);
		}
	}
	
	public static class AttributeResolutionErrorExpectation
	{
		private String attributeName;
		public AttributeResolutionErrorExpectation(String attributeName)
		{
			this.attributeName = attributeName;
		}
		
		public void meet(AttributeResolutionException e)
		{
			assertThat(e.getAttribute(), is(attributeName));
		}
	}
	
	public static class MissingRequiredAttributesResolutionErrorExpectation
	{
		private Collection<String> missingAttributes;
		public MissingRequiredAttributesResolutionErrorExpectation(String[] missingAttributes)
		{
			this.missingAttributes = Lists.newArrayList(missingAttributes);
		}
		
		public void meet(MissingRequiredAttributesException e)
		{
			assertThat(e.getMissingAttributes(), is(missingAttributes));
		}
	}
}